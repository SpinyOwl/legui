package org.liquidengine.legui.component.misc.animation.textarea;

import java.lang.ref.WeakReference;
import org.joml.Vector2f;
import org.liquidengine.legui.animation.Animation;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.TextArea;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class TextAreaScrollAnimation extends Animation {

    private WeakReference<TextArea> textAreaWeakReference;
    private double updateTime;
    private double deltaSum;

    private float targetX;
    private float targetY;
    private double deltaT;
    private Vector2f initialPosition = new Vector2f();

    public TextAreaScrollAnimation(TextArea textArea, double updateTime) {
        this.textAreaWeakReference = new WeakReference<>(textArea);
        this.updateTime = updateTime;
    }

    public TextAreaScrollAnimation(TextArea textArea) {
        this(textArea, 0.2d);
    }

    /**
     * This method used to update animated object. Called by animator every frame. Removed from animator and stops when this method returns true. <p> Returns
     * true if animation is finished and could be removed from animator.
     *
     * @param delta delta time (from previous call).
     *
     * @return true if animation is finished and could be removed from animator.
     */
    @Override
    protected boolean animate(double delta) {
        TextArea scrollablePanel = this.textAreaWeakReference.get();
        if (scrollablePanel != null) {
            deltaSum += delta;
            if (deltaSum >= 0.001d) {
                updateViewport(scrollablePanel, deltaSum);
                deltaSum = 0;
            }
            return false;
        }
        return true;
    }

    private void updateViewport(TextArea scrollablePanel, double delta) {
        Component viewport = scrollablePanel.getViewport();
        Component textAreaField = scrollablePanel.getTextAreaField();
        ScrollBar verticalScrollBar = scrollablePanel.getVerticalScrollBar();
        ScrollBar horizontalScrollBar = scrollablePanel.getHorizontalScrollBar();
        float vh = viewport.getSize().y;
        float ch = textAreaField.getSize().y;
        if (vh > ch) {
            targetY = 0;
        } else {
            float curValue = verticalScrollBar.getCurValue();
            float maxValue = verticalScrollBar.getMaxValue();
            float minValue = verticalScrollBar.getMinValue();
            targetY = (vh - ch) * curValue / (maxValue - minValue);
        }

        float vw = viewport.getSize().x;
        float cw = textAreaField.getSize().x;
        if (vw > cw) {
            targetX = 0;
        } else {
            float curValue = horizontalScrollBar.getCurValue();
            float maxValue = horizontalScrollBar.getMaxValue();
            float minValue = horizontalScrollBar.getMinValue();
            targetX = (vw - cw) * curValue / (maxValue - minValue);
        }

        if (Math.abs(targetX - initialPosition.x) < 0.001 && Math.abs(targetY - initialPosition.y) < 0.001) {
            deltaT = 0;
            initialPosition = new Vector2f(textAreaField.getPosition());
            return;
        }

        // else calculate target position and update it using

        Vector2f targetPosition = new Vector2f(targetX, targetY);

        if (deltaT < updateTime) {
            if (deltaT == 0) {
                initialPosition = new Vector2f(textAreaField.getPosition());
            }
            deltaT += delta;

            double tt = deltaT / updateTime;
            double modifier = easeInQuad(tt);
            Vector2f position = new Vector2f(targetPosition).sub(initialPosition).mul((float) modifier).add(initialPosition);
            textAreaField.setPosition(position);
        } else {
            Vector2f position = new Vector2f(targetX, targetY);
            initialPosition = new Vector2f(position);
            textAreaField.setPosition(position);
        }
    }

    private double easeInQuad(double t) {
        return t * t;
    }
}
