package logwayback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;
import clojure.lang.IFn;
import clojure.lang.Symbol;

import static clojure.java.api.Clojure.var;

public class Layout extends LayoutBase<ILoggingEvent> {
    private static final IFn encodeFn;

    static {
        Symbol reyTesting = (Symbol) var("clojure.core", "symbol").invoke("logwayback.core");
        var("clojure.core", "require").invoke(reyTesting);
        encodeFn = var("logwayback.core", "encode-log-event");
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        return (String) encodeFn.invoke(event);
    }
}
