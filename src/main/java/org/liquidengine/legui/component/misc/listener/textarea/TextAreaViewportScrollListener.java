package org.liquidengine.legui.component.misc.listener.textarea;

import java.util.ArrayList;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.component.event.scrollbar.ScrollBarChangeValueEvent;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.EventListener;
import org.liquidengine.legui.listener.processor.EventProcessor;
import org.liquidengine.legui.system.handler.SehUtil;

import static org.liquidengine.legui.component.misc.listener.scrollbar.ScrollBarHelper.updateScrollBarValue;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.getPaddingV4;

/**
 * Created by ShchAlexander on 23.07.2017.
 */
public class TextAreaViewportScrollListener implements EventListener<ScrollEvent> {


    /**
     * Used to handle specific event.
     *
     * @param event event to handle.
     */
    @Override
    public void process(ScrollEvent event) {
        ArrayList<Component> targetList = new ArrayList<>();
        SehUtil.recursiveTargetComponentListSearch(Mouse.getCursorPosition(), event.getTargetComponent(), targetList);
        for (Component component : targetList) {
            if ((component instanceof TextArea) || (component instanceof ScrollablePanel)) {
                return;
            }
        }

        TextArea textArea = (TextArea) event.getTargetComponent().getParent();

        TextAreaField textAreaField = textArea.getTextAreaField();
        Vector4f p = getPaddingV4(textAreaField, textAreaField.getStyle());
        float maxTextWidth = Math.max(
            textAreaField.getMaxTextWidth() + p.x + p.z,
            textArea.getViewportSize().x
        );
        float maxTextHeight = Math.max(
            textAreaField.getMaxTextHeight() + p.y + p.w,
            textArea.getViewportSize().y
        );
        textAreaField.setSize(maxTextWidth, maxTextHeight);

        updateScrollBarValue(event, textArea.getVerticalScrollBar());
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj == this || obj.getClass() == this.getClass());
    }
}
