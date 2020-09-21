;; Copyright © 2020 Atomist, Inc.
;;
;; Licensed under the Apache License, Version 2.0 (the "License");
;; you may not use this file except in compliance with the License.
;; You may obtain a copy of the License at
;;
;;     http://www.apache.org/licenses/LICENSE-2.0
;;
;; Unless required by applicable law or agreed to in writing, software
;; distributed under the License is distributed on an "AS IS" BASIS,
;; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
;; See the License for the specific language governing permissions and
;; limitations under the License.

(ns atomist.main
  (:require [cljs.pprint :refer [pprint]]
            [cljs.core.async :refer [<! timeout chan close!]]
            [goog.string.format]
            [atomist.json :as json]
            [atomist.api :as api])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn middleware [handler config-key]
  (fn [request]
    (go
      (<! (api/snippet-message request (json/->str (config-key request)) "application/json" "configuration"))
      (<! (handler request)))))

(defn sleep [handler]
  (fn [request]
    (go
      (<! (timeout 30000))
      (<! (handler request)))))

(defn command-handler
  "process the request pipeline for any events arriving in this skill"
  [request]
  ((-> (api/finished :message "Command Handler" :success "successfully ran the kitchen sink")
       (middleware :skill)
       (api/log-event)
       (api/status)) request))

(defn scheduled-event-handler [request]
  ((-> (api/finished :message "Scheduled Event Handler" :success "kitchen sink schedule fired")
       (sleep)
       (middleware :configuration)
       (api/add-slack-source-to-event :team-id "TDDAK8WKT" :channel "kitchen-sink-skill")
       (api/log-event)
       (api/status)) request))

(defn ^:export handler
  "handler
    must return a Promise - we don't do anything with the value
    params
      data - Incoming Request #js object
      sendreponse - callback ([obj]) puts an outgoing message on the response topic"
  [data sendreponse]
  (api/make-request
   data sendreponse
   (fn [request]
     (cond
       (= "KitchenSink" (:command request))
       (command-handler request)
       :else
       (scheduled-event-handler request)))))
