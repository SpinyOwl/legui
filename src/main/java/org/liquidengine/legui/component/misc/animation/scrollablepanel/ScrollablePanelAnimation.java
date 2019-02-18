package org.liquidengine.legui.component.misc.animation.scrollablepanel;

import java.lang.ref.WeakReference;
import org.joml.Vector2f;
import org.liquidengine.legui.animation.Animation;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.ScrollablePanel;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class ScrollablePanelAnimation extends Animation {

    private WeakReference<ScrollablePanel> scrollablePanel;
    private double updateTime;
    private double deltaSum;

    private double deltaT;
    private Vector2f initialPosition = new Vector2f();

    public ScrollablePanelAnimation(ScrollablePanel scrollablePanel, double updateTime) {
        this.scrollablePanel = new WeakReference<>(scrollablePanel);
        this.updateTime = updateTime;
    }

    public ScrollablePanelAnimation(ScrollablePanel scrollablePanel) {
        this(scrollablePanel, 0.2d);
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
        ScrollablePanel scrollablePanel = this.scrollablePanel.get();
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

    private void updateViewport(ScrollablePanel scrollablePanel, double delta) {
        Component viewport = scrollablePanel.getViewport();
        Component container = scrollablePanel.getContainer();
        ScrollBar verticalScrollBar = scrollablePanel.getVerticalScrollBar();
        ScrollBar horizontalScrollBar = scrollablePanel.getHorizontalScrollBar();
        float vh = viewport.getSize().y;
        float ch = container.getSize().y;
        float targetY;
        if (vh > ch) {
            targetY = 0;
        } else {
            float curValue = verticalScrollBar.getCurValue();
            float maxValue = verticalScrollBar.getMaxValue();
            float minValue = verticalScrollBar.getMinValue();
            targetY = (vh - ch) * curValue / (maxValue - minValue);
        }

        float vw = viewport.getSize().x;
        float cw = container.getSize().x;
        float targetX;
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
            initialPosition = new Vector2f(container.getPosition());
            return;
        }

        // else calculate target position and update it using

        Vector2f targetPosition = new Vector2f(targetX, targetY);

        if (deltaT < updateTime) {
            if (deltaT == 0) {
                initialPosition = new Vector2f(container.getPosition());
            }
            deltaT += delta;

            double tt = deltaT / updateTime;
            double modifier = easeInQuad(tt);
            Vector2f position = new Vector2f(targetPosition).sub(initialPosition).mul((float) modifier).add(initialPosition);
            container.setPosition(position);
        } else {
            Vector2f position = new Vector2f(targetX, targetY);
            initialPosition = new Vector2f(position);
            container.setPosition(position);
        }
    }

    private double easeInQuad(double t) {
        return t * t;
    }
}
