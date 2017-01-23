package org.liquidengine.legui.processor;

/**
 * Event processor which should translate all system events to  events.
 */
public abstract class LeguiSystemEventProcessor {

    public abstract void initialize();

    public void processEvent() {

    }

    public abstract void destroy();
}
