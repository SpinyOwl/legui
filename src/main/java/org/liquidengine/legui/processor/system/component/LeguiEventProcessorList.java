//package org.liquidengine.legui.processor.system.component;
//
//import org.liquidengine.legui.component.Component;
//import org.liquidengine.legui.event.SystemEvent;
//import org.liquidengine.legui.event.system.*;
//
///**
// * Created by Shcherbin Alexander on 9/19/2016.
// */
//public class LeguiEventProcessorList {
//    private LeguiComponentEventProcessor<?, SystemCharEvent> charEventProcessor;
//    private LeguiComponentEventProcessor<?, SystemCharModsEvent> charModsEventProcessor;
//    private LeguiComponentEventProcessor<?, SystemCursorEnterEvent> cursorEnterEventProcessor;
//    private LeguiComponentEventProcessor<?, SystemCursorPosEvent> cursorPosEventProcessor;
//    private LeguiComponentEventProcessor<?, SystemDropEvent> dropEventProcessor;
//    private LeguiComponentEventProcessor<?, SystemFramebufferSizeEvent> framebufferSizeEventProcessor;
//    private LeguiComponentEventProcessor<?, SystemEvent> iEventProcessor;
//    private LeguiComponentEventProcessor<?, SystemKeyEvent> keyEventProcessor;
//    private LeguiComponentEventProcessor<?, SystemMouseClickEvent> mouseClickEventProcessor;
//    private LeguiComponentEventProcessor<?, SystemScrollEvent> scrollEventProcessor;
//    private LeguiComponentEventProcessor<?, SystemWindowCloseEvent> windowCloseEventProcessor;
//    private LeguiComponentEventProcessor<?, SystemWindowFocusEvent> windowFocusEventProcessor;
//    private LeguiComponentEventProcessor<?, SystemWindowIconifyEvent> windowIconifyEventProcessor;
//    private LeguiComponentEventProcessor<?, SystemWindowPosEvent> windowPosEventProcessor;
//    private LeguiComponentEventProcessor<?, SystemWindowRefreshEvent> windowRefreshEventProcessor;
//    private LeguiComponentEventProcessor<?, SystemWindowSizeEvent> windowSizeEventProcessor;
//
//    public LeguiEventProcessorList(Component gui) {
//        AbstractGuiProcessorProvider provider = LeguiComponentEventProcessorProvider.getProvider();
//        Class<? extends Component> aClass = gui.getClass();
//        charEventProcessor = provider.getGuiProcessor(aClass, SystemCharEvent.class);
//        charModsEventProcessor = provider.getGuiProcessor(aClass, SystemCharModsEvent.class);
//        cursorEnterEventProcessor = provider.getGuiProcessor(aClass, SystemCursorEnterEvent.class);
//        cursorPosEventProcessor = provider.getGuiProcessor(aClass, SystemCursorPosEvent.class);
//        dropEventProcessor = provider.getGuiProcessor(aClass, SystemDropEvent.class);
//        framebufferSizeEventProcessor = provider.getGuiProcessor(aClass, SystemFramebufferSizeEvent.class);
//        iEventProcessor = provider.getGuiProcessor(aClass, SystemEvent.class);
//        keyEventProcessor = provider.getGuiProcessor(aClass, SystemKeyEvent.class);
//        mouseClickEventProcessor = provider.getGuiProcessor(aClass, SystemMouseClickEvent.class);
//        scrollEventProcessor = provider.getGuiProcessor(aClass, SystemScrollEvent.class);
//        windowCloseEventProcessor = provider.getGuiProcessor(aClass, SystemWindowCloseEvent.class);
//        windowFocusEventProcessor = provider.getGuiProcessor(aClass, SystemWindowFocusEvent.class);
//        windowIconifyEventProcessor = provider.getGuiProcessor(aClass, SystemWindowIconifyEvent.class);
//        windowPosEventProcessor = provider.getGuiProcessor(aClass, SystemWindowPosEvent.class);
//        windowRefreshEventProcessor = provider.getGuiProcessor(aClass, SystemWindowRefreshEvent.class);
//        windowSizeEventProcessor = provider.getGuiProcessor(aClass, SystemWindowSizeEvent.class);
//    }
//
//    public LeguiComponentEventProcessor getCharEventProcessor() {
//        return charEventProcessor;
//    }
//
//    public void setCharEventProcessor(LeguiComponentEventProcessor<?, SystemCharEvent> charEventProcessor) {
//        this.charEventProcessor = charEventProcessor;
//    }
//
//    public LeguiComponentEventProcessor getCharModsEventProcessor() {
//        return charModsEventProcessor;
//    }
//
//    public void setCharModsEventProcessor(LeguiComponentEventProcessor<?, SystemCharModsEvent> charModsEventProcessor) {
//        this.charModsEventProcessor = charModsEventProcessor;
//    }
//
//    public LeguiComponentEventProcessor getCursorEnterEventProcessor() {
//        return cursorEnterEventProcessor;
//    }
//
//    public void setCursorEnterEventProcessor(LeguiComponentEventProcessor<?, SystemCursorEnterEvent> cursorEnterEventProcessor) {
//        this.cursorEnterEventProcessor = cursorEnterEventProcessor;
//    }
//
//    public LeguiComponentEventProcessor getCursorPosEventProcessor() {
//        return cursorPosEventProcessor;
//    }
//
//    public void setCursorPosEventProcessor(LeguiComponentEventProcessor<?, SystemCursorPosEvent> cursorPosEventProcessor) {
//        this.cursorPosEventProcessor = cursorPosEventProcessor;
//    }
//
//    public LeguiComponentEventProcessor getDropEventProcessor() {
//        return dropEventProcessor;
//    }
//
//    public void setDropEventProcessor(LeguiComponentEventProcessor<?, SystemDropEvent> dropEventProcessor) {
//        this.dropEventProcessor = dropEventProcessor;
//    }
//
//    public LeguiComponentEventProcessor getFramebufferSizeEventProcessor() {
//        return framebufferSizeEventProcessor;
//    }
//
//    public void setFramebufferSizeEventProcessor(LeguiComponentEventProcessor<?, SystemFramebufferSizeEvent> framebufferSizeEventProcessor) {
//        this.framebufferSizeEventProcessor = framebufferSizeEventProcessor;
//    }
//
//    public LeguiComponentEventProcessor getiEventProcessor() {
//        return iEventProcessor;
//    }
//
//    public void setiEventProcessor(LeguiComponentEventProcessor<?, SystemEvent> iEventProcessor) {
//        this.iEventProcessor = iEventProcessor;
//    }
//
//    public LeguiComponentEventProcessor getKeyEventProcessor() {
//        return keyEventProcessor;
//    }
//
//    public void setKeyEventProcessor(LeguiComponentEventProcessor<?, SystemKeyEvent> keyEventProcessor) {
//        this.keyEventProcessor = keyEventProcessor;
//    }
//
//    public LeguiComponentEventProcessor getMouseClickEventProcessor() {
//        return mouseClickEventProcessor;
//    }
//
//    public void setMouseClickEventProcessor(LeguiComponentEventProcessor<?, SystemMouseClickEvent> mouseClickEventProcessor) {
//        this.mouseClickEventProcessor = mouseClickEventProcessor;
//    }
//
//    public LeguiComponentEventProcessor getScrollEventProcessor() {
//        return scrollEventProcessor;
//    }
//
//    public void setScrollEventProcessor(LeguiComponentEventProcessor<?, SystemScrollEvent> scrollEventProcessor) {
//        this.scrollEventProcessor = scrollEventProcessor;
//    }
//
//    public LeguiComponentEventProcessor getWindowCloseEventProcessor() {
//        return windowCloseEventProcessor;
//    }
//
//    public void setWindowCloseEventProcessor(LeguiComponentEventProcessor<?, SystemWindowCloseEvent> windowCloseEventProcessor) {
//        this.windowCloseEventProcessor = windowCloseEventProcessor;
//    }
//
//    public LeguiComponentEventProcessor getWindowFocusEventProcessor() {
//        return windowFocusEventProcessor;
//    }
//
//    public void setWindowFocusEventProcessor(LeguiComponentEventProcessor<?, SystemWindowFocusEvent> windowFocusEventProcessor) {
//        this.windowFocusEventProcessor = windowFocusEventProcessor;
//    }
//
//    public LeguiComponentEventProcessor getWindowIconifyEventProcessor() {
//        return windowIconifyEventProcessor;
//    }
//
//    public void setWindowIconifyEventProcessor(LeguiComponentEventProcessor<?, SystemWindowIconifyEvent> windowIconifyEventProcessor) {
//        this.windowIconifyEventProcessor = windowIconifyEventProcessor;
//    }
//
//    public LeguiComponentEventProcessor getWindowPosEventProcessor() {
//        return windowPosEventProcessor;
//    }
//
//    public void setWindowPosEventProcessor(LeguiComponentEventProcessor<?, SystemWindowPosEvent> windowPosEventProcessor) {
//        this.windowPosEventProcessor = windowPosEventProcessor;
//    }
//
//    public LeguiComponentEventProcessor getWindowRefreshEventProcessor() {
//        return windowRefreshEventProcessor;
//    }
//
//    public void setWindowRefreshEventProcessor(LeguiComponentEventProcessor<?, SystemWindowRefreshEvent> windowRefreshEventProcessor) {
//        this.windowRefreshEventProcessor = windowRefreshEventProcessor;
//    }
//
//    public LeguiComponentEventProcessor getWindowSizeEventProcessor() {
//        return windowSizeEventProcessor;
//    }
//
//    public void setWindowSizeEventProcessor(LeguiComponentEventProcessor<?, SystemWindowSizeEvent> windowSizeEventProcessor) {
//        this.windowSizeEventProcessor = windowSizeEventProcessor;
//    }
//}
