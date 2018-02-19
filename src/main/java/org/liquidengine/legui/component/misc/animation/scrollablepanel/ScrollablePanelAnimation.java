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
    private double deltaSum;
    private float lastx;
    private float lasty;

    public ScrollablePanelAnimation(ScrollablePanel scrollablePanel) {
        this.scrollablePanel = new WeakReference<>(scrollablePanel);
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
            if (deltaSum >= 0.01d) {
                updateViewport(scrollablePanel);
                deltaSum = 0;
            }
            return false;
        }
        return true;
    }

    private void updateViewport(ScrollablePanel scrollablePanel) {
        Component viewport = scrollablePanel.getViewport();
        Component container = scrollablePanel.getContainer();
        ScrollBar verticalScrollBar = scrollablePanel.getVerticalScrollBar();
        ScrollBar horizontalScrollBar = scrollablePanel.getHorizontalScrollBar();
        if (
            (lastx == horizontalScrollBar.getCurValue()) &&
                (lasty == verticalScrollBar.getCurValue())
            ) {
            return;
        }
        float vh = viewport.getSize().y;
        float ch = container.getSize().y;
        float newPosY;
        if (vh > ch) {
            newPosY = 0;
        } else {
            lasty = verticalScrollBar.getCurValue();
            newPosY = (vh - ch) * lasty / (verticalScrollBar.getMaxValue() - verticalScrollBar.getMinValue());
        }

        float vw = viewport.getSize().x;
        float cw = container.getSize().x;
        float newPosX;
        if (vw > cw) {
            newPosX = 0;
        } else {
            lastx = horizontalScrollBar.getCurValue();
            newPosX = (vw - cw) * lastx / (horizontalScrollBar.getMaxValue() - horizontalScrollBar.getMinValue());
        }
        container.setPosition(new Vector2f(newPosX, newPosY));
    }
}
