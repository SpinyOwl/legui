package org.liquidengine.legui.component.misc.animation.scrollbar;

import org.joml.Vector2f;
import org.liquidengine.legui.animation.Animation;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.Viewport;
import org.liquidengine.legui.component.optional.Orientation;

import java.lang.ref.WeakReference;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class ScrollBarAnimation extends Animation {

    private WeakReference<ScrollBar> scrollBar;

    public ScrollBarAnimation(ScrollBar scrollBar) {
        this.scrollBar = new WeakReference<>(scrollBar);
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
        ScrollBar scrollBar = this.scrollBar.get();
        if (scrollBar == null) {
            return true;
        }

        Viewport viewport = scrollBar.getViewport();
        if (scrollBar.isVisible() && viewport != null) {

            Vector2f viewportSize = viewport.getViewportSize();
            Vector2f viewportViewSize = viewport.getViewportViewSize();

            if (viewportSize == null || viewportViewSize == null) {
                return true;
            }

            float range = scrollBar.getMaxValue() - scrollBar.getMinValue();
            float allSize = Orientation.HORIZONTAL.equals(scrollBar.getOrientation()) ? viewportViewSize.x : viewportViewSize.y;
            float viewSize = Orientation.HORIZONTAL.equals(scrollBar.getOrientation()) ? viewportSize.x : viewportSize.y;

            scrollBar.setVisibleAmount(allSize >= viewSize ? (range * viewSize / allSize) : range);
        }

        return false;
    }
}
