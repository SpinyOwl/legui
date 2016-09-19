package org.liquidengine.legui.processor.component;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.event.system.*;

/**
 * Created by Shcherbin Alexander on 9/19/2016.
 */
public class LeguiEventProcessorContainer {
    private LeguiComponentEventProcessor<?, CharEvent> charEventProcessor;
    private LeguiComponentEventProcessor<?, CharModsEvent> charModsEventProcessor;
    private LeguiComponentEventProcessor<?, CursorEnterEvent> cursorEnterEventProcessor;
    private LeguiComponentEventProcessor<?, CursorPosEvent> cursorPosEventProcessor;
    private LeguiComponentEventProcessor<?, DropEvent> dropEventProcessor;
    private LeguiComponentEventProcessor<?, FramebufferSizeEvent> framebufferSizeEventProcessor;
    private LeguiComponentEventProcessor<?, LeguiSystemEvent> iEventProcessor;
    private LeguiComponentEventProcessor<?, KeyEvent> keyEventProcessor;
    private LeguiComponentEventProcessor<?, MouseClickEvent> mouseClickEventProcessor;
    private LeguiComponentEventProcessor<?, ScrollEvent> scrollEventProcessor;
    private LeguiComponentEventProcessor<?, WindowCloseEvent> windowCloseEventProcessor;
    private LeguiComponentEventProcessor<?, WindowFocusEvent> windowFocusEventProcessor;
    private LeguiComponentEventProcessor<?, WindowIconifyEvent> windowIconifyEventProcessor;
    private LeguiComponentEventProcessor<?, WindowPosEvent> windowPosEventProcessor;
    private LeguiComponentEventProcessor<?, WindowRefreshEvent> windowRefreshEventProcessor;
    private LeguiComponentEventProcessor<?, WindowSizeEvent> windowSizeEventProcessor;

    public LeguiEventProcessorContainer(Component gui) {
        AbstractGuiProcessorProvider provider = LeguiComponentEventProcessorProvider.getProvider();
        Class<? extends Component> aClass = gui.getClass();
        charEventProcessor = provider.getGuiProcessor(aClass, CharEvent.class);
        charModsEventProcessor = provider.getGuiProcessor(aClass, CharModsEvent.class);
        cursorEnterEventProcessor = provider.getGuiProcessor(aClass, CursorEnterEvent.class);
        cursorPosEventProcessor = provider.getGuiProcessor(aClass, CursorPosEvent.class);
        dropEventProcessor = provider.getGuiProcessor(aClass, DropEvent.class);
        framebufferSizeEventProcessor = provider.getGuiProcessor(aClass, FramebufferSizeEvent.class);
        iEventProcessor = provider.getGuiProcessor(aClass, LeguiSystemEvent.class);
        keyEventProcessor = provider.getGuiProcessor(aClass, KeyEvent.class);
        mouseClickEventProcessor = provider.getGuiProcessor(aClass, MouseClickEvent.class);
        scrollEventProcessor = provider.getGuiProcessor(aClass, ScrollEvent.class);
        windowCloseEventProcessor = provider.getGuiProcessor(aClass, WindowCloseEvent.class);
        windowFocusEventProcessor = provider.getGuiProcessor(aClass, WindowFocusEvent.class);
        windowIconifyEventProcessor = provider.getGuiProcessor(aClass, WindowIconifyEvent.class);
        windowPosEventProcessor = provider.getGuiProcessor(aClass, WindowPosEvent.class);
        windowRefreshEventProcessor = provider.getGuiProcessor(aClass, WindowRefreshEvent.class);
        windowSizeEventProcessor = provider.getGuiProcessor(aClass, WindowSizeEvent.class);
    }

    public LeguiComponentEventProcessor getCharEventProcessor() {
        return charEventProcessor;
    }

    public void setCharEventProcessor(LeguiComponentEventProcessor<?, CharEvent> charEventProcessor) {
        this.charEventProcessor = charEventProcessor;
    }

    public LeguiComponentEventProcessor getCharModsEventProcessor() {
        return charModsEventProcessor;
    }

    public void setCharModsEventProcessor(LeguiComponentEventProcessor<?, CharModsEvent> charModsEventProcessor) {
        this.charModsEventProcessor = charModsEventProcessor;
    }

    public LeguiComponentEventProcessor getCursorEnterEventProcessor() {
        return cursorEnterEventProcessor;
    }

    public void setCursorEnterEventProcessor(LeguiComponentEventProcessor<?, CursorEnterEvent> cursorEnterEventProcessor) {
        this.cursorEnterEventProcessor = cursorEnterEventProcessor;
    }

    public LeguiComponentEventProcessor getCursorPosEventProcessor() {
        return cursorPosEventProcessor;
    }

    public void setCursorPosEventProcessor(LeguiComponentEventProcessor<?, CursorPosEvent> cursorPosEventProcessor) {
        this.cursorPosEventProcessor = cursorPosEventProcessor;
    }

    public LeguiComponentEventProcessor getDropEventProcessor() {
        return dropEventProcessor;
    }

    public void setDropEventProcessor(LeguiComponentEventProcessor<?, DropEvent> dropEventProcessor) {
        this.dropEventProcessor = dropEventProcessor;
    }

    public LeguiComponentEventProcessor getFramebufferSizeEventProcessor() {
        return framebufferSizeEventProcessor;
    }

    public void setFramebufferSizeEventProcessor(LeguiComponentEventProcessor<?, FramebufferSizeEvent> framebufferSizeEventProcessor) {
        this.framebufferSizeEventProcessor = framebufferSizeEventProcessor;
    }

    public LeguiComponentEventProcessor getiEventProcessor() {
        return iEventProcessor;
    }

    public void setiEventProcessor(LeguiComponentEventProcessor<?, LeguiSystemEvent> iEventProcessor) {
        this.iEventProcessor = iEventProcessor;
    }

    public LeguiComponentEventProcessor getKeyEventProcessor() {
        return keyEventProcessor;
    }

    public void setKeyEventProcessor(LeguiComponentEventProcessor<?, KeyEvent> keyEventProcessor) {
        this.keyEventProcessor = keyEventProcessor;
    }

    public LeguiComponentEventProcessor getMouseClickEventProcessor() {
        return mouseClickEventProcessor;
    }

    public void setMouseClickEventProcessor(LeguiComponentEventProcessor<?, MouseClickEvent> mouseClickEventProcessor) {
        this.mouseClickEventProcessor = mouseClickEventProcessor;
    }

    public LeguiComponentEventProcessor getScrollEventProcessor() {
        return scrollEventProcessor;
    }

    public void setScrollEventProcessor(LeguiComponentEventProcessor<?, ScrollEvent> scrollEventProcessor) {
        this.scrollEventProcessor = scrollEventProcessor;
    }

    public LeguiComponentEventProcessor getWindowCloseEventProcessor() {
        return windowCloseEventProcessor;
    }

    public void setWindowCloseEventProcessor(LeguiComponentEventProcessor<?, WindowCloseEvent> windowCloseEventProcessor) {
        this.windowCloseEventProcessor = windowCloseEventProcessor;
    }

    public LeguiComponentEventProcessor getWindowFocusEventProcessor() {
        return windowFocusEventProcessor;
    }

    public void setWindowFocusEventProcessor(LeguiComponentEventProcessor<?, WindowFocusEvent> windowFocusEventProcessor) {
        this.windowFocusEventProcessor = windowFocusEventProcessor;
    }

    public LeguiComponentEventProcessor getWindowIconifyEventProcessor() {
        return windowIconifyEventProcessor;
    }

    public void setWindowIconifyEventProcessor(LeguiComponentEventProcessor<?, WindowIconifyEvent> windowIconifyEventProcessor) {
        this.windowIconifyEventProcessor = windowIconifyEventProcessor;
    }

    public LeguiComponentEventProcessor getWindowPosEventProcessor() {
        return windowPosEventProcessor;
    }

    public void setWindowPosEventProcessor(LeguiComponentEventProcessor<?, WindowPosEvent> windowPosEventProcessor) {
        this.windowPosEventProcessor = windowPosEventProcessor;
    }

    public LeguiComponentEventProcessor getWindowRefreshEventProcessor() {
        return windowRefreshEventProcessor;
    }

    public void setWindowRefreshEventProcessor(LeguiComponentEventProcessor<?, WindowRefreshEvent> windowRefreshEventProcessor) {
        this.windowRefreshEventProcessor = windowRefreshEventProcessor;
    }

    public LeguiComponentEventProcessor getWindowSizeEventProcessor() {
        return windowSizeEventProcessor;
    }

    public void setWindowSizeEventProcessor(LeguiComponentEventProcessor<?, WindowSizeEvent> windowSizeEventProcessor) {
        this.windowSizeEventProcessor = windowSizeEventProcessor;
    }
}
