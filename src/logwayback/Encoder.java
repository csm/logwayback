package logwayback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;

public class Encoder extends LayoutWrappingEncoder<ILoggingEvent> {
    @Override
    public void start() {
        Layout layout = new Layout();
        layout.setContext(this.context);
        layout.start();
        setLayout(layout);
        super.start();
    }
}
