package org.liquidengine.legui.processor.component.widget;

/**
 * Created by Alexander on 28.08.2016.
 */
//public class WidgetCursorPosProcessor implements IGuiProcessor<Widget, CursorPosEvent> {
//    @Override
//    public void process(Widget gui, CursorPosEvent event, GuiEventProcessorState processorState) {
//        if (gui.isVisible()) {
//            Panel titlePanel = gui.getTitlePanel();
//            Element focusedGui = processorState.getFocusedGui();
//            if (focusedGui == titlePanel || focusedGui == gui.getTitleText()) {
//                if (processorState.getMouseButtonStates()[GLFW.GLFW_MOUSE_BUTTON_LEFT]) {
//                    Vector2f delta = new Vector2f(processorState.getCursorPosition()).sub(processorState.getCursorPositionPrev());
//                    Vector2f widP = gui.getPosition();
//                    gui.setPosition(widP.add(delta));
//                }
//            }
//        }
//    }
//}
