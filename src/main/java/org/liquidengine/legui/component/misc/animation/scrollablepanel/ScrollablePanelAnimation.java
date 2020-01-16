package org.liquidengine.legui.component.misc.animation.scrollablepanel;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.component.misc.animation.ViewportAnimation;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class ScrollablePanelAnimation extends ViewportAnimation<ScrollablePanel> {

    public ScrollablePanelAnimation(ScrollablePanel scrollablePanel, double updateTime) {
        super(scrollablePanel, updateTime);
    }

    public ScrollablePanelAnimation(ScrollablePanel scrollablePanel) {
        this(scrollablePanel, 0.1d);
    }


    protected void updateViewport(ScrollablePanel scrollablePanel, double delta) {
        Component viewport = scrollablePanel.getViewport();
        Component container = scrollablePanel.getContainer();
        ScrollBar verticalScrollBar = scrollablePanel.getVerticalScrollBar();
        ScrollBar horizontalScrollBar = scrollablePanel.getHorizontalScrollBar();

        super.updateViewport(viewport, container, verticalScrollBar, horizontalScrollBar, delta);
    }

}
