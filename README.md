# logwayback

An encoder for logback that can colorize output, and pretty
print clojure data if that's what you're logging.

## Usage

```xml
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="logwayback.Encoder"/>
    </appender>
```

Set `COLORIZE_LOGGING=true` in your environment variables
to enable colorized logging to the console.

Pretty-printing clojure data is naive; it looks if the first
character of your log message is `{`, and if so, tries parsing
your log message as EDN. If it can, it pretty-prints that
value. Otherwise, it just logs your message as-is. This is
mainly useful if you use [pedestal](http://pedestal.io) style
logging, where you log a data structure, not a free-form string.

## WHY THO

1. Colorful error logs and exceptions in the console.
2. Pretty-printing clojure data.

## License

Copyright © 2018 Casey Marshall

Distributed under the MIT license.
