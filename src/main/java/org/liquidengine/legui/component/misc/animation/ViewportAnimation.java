package org.liquidengine.legui.component.misc.animation;

import org.joml.Vector2f;
import org.liquidengine.legui.animation.Animation;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ScrollBar;

import java.lang.ref.WeakReference;

public abstract class ViewportAnimation<T extends Component> extends Animation {
    private static final double DOUBLE_THRESHOLD = 0.001;

    private double updateTime;
    private double deltaSum;

    private double deltaT;
    private Vector2f initialPosition = new Vector2f();

    private WeakReference<T> componentReference;

    public ViewportAnimation(T component, double updateTime) {
        this.componentReference = new WeakReference<>(component);
        this.updateTime = updateTime;
    }

    public WeakReference<T> getComponentReference() {
        return componentReference;
    }


    /**
     * This method used to update animated object. Called by animator every frame. Removed from animator and stops when this method returns true. <p> Returns
     * true if animation is finished and could be removed from animator.
     *
     * @param delta delta time (from previous call).
     * @return true if animation is finished and could be removed from animator.
     */
    @Override
    protected boolean animate(double delta) {
        T component = this.getComponentReference().get();
        if (component != null) {
            deltaSum += delta;
            if (deltaSum >= 0.001d) {
                updateViewport(component, deltaSum);
                deltaSum = 0;
            }
            return false;
        }
        return true;
    }

    protected abstract void updateViewport(T component, double delta);


    protected void updateViewport(Component viewport,
                                  Component container,
                                  ScrollBar verticalScrollBar,
                                  ScrollBar horizontalScrollBar,
                                  double delta) {
        float vh = viewport.getSize().y;
        float ch = container.getSize().y;
        float targetY = calculateTargetPosition(verticalScrollBar, vh, ch);

        float vw = viewport.getSize().x;
        float cw = container.getSize().x;
        float targetX = calculateTargetPosition(horizontalScrollBar, vw, cw);

        if (Math.abs(targetX - initialPosition.x) < DOUBLE_THRESHOLD && Math.abs(targetY - initialPosition.y) < DOUBLE_THRESHOLD) {
            deltaT = 0;
            initialPosition.set(container.getPosition());
            return;
        }

        // else calculate target position and update it using

        Vector2f targetPosition = new Vector2f(targetX, targetY);

        if (deltaT < updateTime) {
            if (deltaT == 0) {
                initialPosition.set(container.getPosition());
            }
            deltaT += delta;

            double tt = deltaT / updateTime;
            double modifier = easeInQuad(tt);
            Vector2f position = new Vector2f(targetPosition).sub(initialPosition).mul((float) modifier).add(initialPosition);
            container.setPosition(position);
        } else {
            Vector2f position = new Vector2f(targetX, targetY);
            initialPosition.set(position);
            container.setPosition(position);
        }
    }

    private float calculateTargetPosition(ScrollBar horizontalScrollBar, float vw, float cw) {
        float targetX;
        if (vw > cw) {
            targetX = 0;
        } else {
            float curValue = horizontalScrollBar.getCurValue();
            float maxValue = horizontalScrollBar.getMaxValue();
            float minValue = horizontalScrollBar.getMinValue();
            targetX = (vw - cw) * curValue / (maxValue - minValue);
        }
        return targetX;
    }

    private double easeInQuad(double t) {
        return t * t;
    }
}
