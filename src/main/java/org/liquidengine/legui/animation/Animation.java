package org.liquidengine.legui.animation;

/**
 * Created by ShchAlexander on 31.07.2017.
 */
public abstract class Animation<T extends Animated> {

    public T target;

    public void startAnimation() {
        Animator.getInstance().pushAnimation(this);
    }

    protected void beforeAnimation(){}

    /**
     * Returns true if animation is finished and could be removed from animator.
     *
     * @return true if animation is finished and could be removed from animator.
     */
    protected abstract boolean animate(double delta);

    protected void afterAnimation(){}

    /**
     * Gets target.
     *
     * @return the target
     */
    public T getTarget() {
        return target;
    }

    /**
     * Sets target.
     *
     * @param target the target
     */
    public void setTarget(T target) {
        this.target = target;
    }
}
