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

7.  **Commit Strategy**

    ![Fix strategy](docs/images/commit-strategy.png)

    Choose how to apply copyright and license updates to the repository. The
    following options are available:

    -   **Raise pull request for default branch; commit to other branches** -
        with this option, fixes on the default branch will be submitted via a
        pull request; fixes on other branches will be committed straight onto
        the branch
    -   **Raise pull request for default branch only** - with this option, fixes
        on the default branch will be submitted via a pull request; fixes on
        other branches will not be persisted
    -   **Raise pull request for any branch** - with this option, fixes on all
        branches will be submitted via a pull request
    -   **Commit to default branch only** - with this option, fixes on the
        default branch will be committed straight to the branch; fixes on other
        branches will not be persisted
    -   **Commit to any branch** - with this option, fixes on all branches will
        be committed straight to the branch

    Pull requests that get raised by this skill will automatically have a
    reviewer assigned based on the person who pushed code. Pull requests that
    are not needed any longer, i.e., because all lint violations were fixed
    manually, are closed automatically.
