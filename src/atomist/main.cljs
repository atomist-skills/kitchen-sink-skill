(ns atomist.main
  (:require [cljs.pprint :refer [pprint]]
            [cljs.core.async :refer [<! timeout chan]]
            [goog.string.format]
            [atomist.json :as json]
            [atomist.api :as api])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn middleware [handler]
  (fn [request]
    (go
     (<! (api/snippet-message request (json/->str (:configuration request)) "application/json" "configuration"))
     (handler request))))

(defn add-slack-source-to-event
  [handler team-id channel-name]
  (fn [request]
    (handler (-> request
                 (api/default-destination)
                 (update-in [:destinations 0 :slack] assoc :team {:id team-id} :channel {:name channel-name})))))

(defn command-handler
  "process the request pipeline for any events arriving in this skill"
  [request]
  ((-> (api/finished :message "Command Handler" :success "successfully ran the kitchen sink")
       (middleware)
       (add-slack-source-to-event "TDDAK8WKT" "kitchen-sink-skill")) request))

(defn ^:export handler
  "handler
    must return a Promise - we don't do anything with the value
    params
      data - Incoming Request #js object
      sendreponse - callback ([obj]) puts an outgoing message on the response topic"
  [data sendreponse]
  (api/make-request data sendreponse command-handler))

(handler #js {:configuration {:crazy "crazy"}} (fn [& args] (println "go" args)))
