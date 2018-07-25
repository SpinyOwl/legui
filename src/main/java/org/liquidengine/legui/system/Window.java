package org.liquidengine.legui.system;

import org.joml.Vector2i;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.event.WindowCloseEvent;
import org.liquidengine.legui.listener.WindowCloseEventListener;
import org.liquidengine.legui.system.context.CallbackKeeper;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.handler.processor.SystemEventProcessor;
import org.liquidengine.legui.system.renderer.Renderer;

public class Window {

    private long pointer;

    private Frame frame;
    private Context context;
    private CallbackKeeper callbackKeeper;
    private SystemEventProcessor systemEventProcessor;
    private Renderer renderer;

    protected Window(long pointer) {
        this.pointer = pointer;
    }

    public void setVisible(boolean visible) {
        LeguiSystem.setVisible(pointer);
    }

    public Vector2i getSize() {
        return LeguiSystem.getWindowSize(pointer);
    }

    protected long getPointer() {
        return pointer;
    }

    protected void setPointer(long pointer) {
        this.pointer = pointer;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        if (frame != null) {
            this.frame = frame;
        }
    }

    protected Context getContext() {
        return context;
    }

    protected void setContext(Context context) {
        this.context = context;
    }

    protected CallbackKeeper getCallbackKeeper() {
        return callbackKeeper;
    }

    protected void setCallbackKeeper(CallbackKeeper callbackKeeper) {
        this.callbackKeeper = callbackKeeper;
    }

    protected SystemEventProcessor getSystemEventProcessor() {
        return systemEventProcessor;
    }

    protected void setSystemEventProcessor(SystemEventProcessor systemEventProcessor) {
        this.systemEventProcessor = systemEventProcessor;
    }

    protected Renderer getRenderer() {
        return renderer;
    }

    protected void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public void setCloseEventListener(WindowCloseEventListener closeEvent) {
        frame.getContainer().getListenerMap().addListener(WindowCloseEvent.class, closeEvent);
    }
}
