# What does it do for you?

Well, nothing. But it can be good for testing!

Javascript block

```javascript
Math.random();
```

Ruby

```ruby
Facter.add(:libcheck) do
  require 'digest'
  require 'yaml'

  begin
    digests = YAML.load(File.open('/Users/ipcrm/adhoc/fim/digest.yaml'))
  rescue
    exists = false
    digests = {}
  end

  new = {}

  begin
    files = Dir['/Users/ipcrm/adhoc/fim/lib/*']
  rescue
    exit 1
  end
```

> Should not highlight

```nohighlight
new Thing();
```

Tight list

-   a
-   b
-   c
-   d

Loose list

-   a

    bbb

-   c

    ddd
