package org.liquidengine.legui.component.misc.animation.textarea;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.misc.animation.ViewportAnimation;

/**
 * @author ShchAlexander.
 */
public class TextAreaScrollAnimation extends ViewportAnimation<TextArea> {

    public TextAreaScrollAnimation(TextArea textArea, double updateTime) {
        super(textArea, updateTime);
    }

    public TextAreaScrollAnimation(TextArea textArea) {
        this(textArea, 0.2d);
    }


    protected void updateViewport(TextArea scrollablePanel, double delta) {
        Component viewport = scrollablePanel.getViewport();
        Component textAreaField = scrollablePanel.getTextAreaField();
        ScrollBar verticalScrollBar = scrollablePanel.getVerticalScrollBar();
        ScrollBar horizontalScrollBar = scrollablePanel.getHorizontalScrollBar();

        super.updateViewport(viewport, textAreaField, verticalScrollBar, horizontalScrollBar, delta);
    }

}
