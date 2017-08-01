package org.liquidengine.legui.animation;

/**
 * Created by ShchAlexander on 31.07.2017.
 */
public abstract class Animation {

    /**
     * Adds animation to animator.
     */
    public void startAnimation() {
        Animator.getInstance().pushAnimation(this);
    }

    /**
     * Called one time before animate loop.
     */
    protected void initialize() {
        // Could be implemented later.
    }

    /**
     * This method used to update animated object. Called by animator every frame. Removed from animator and stops when this method returns
     * true.
     * <p>
     * Returns true if animation is finished and could be removed from animator.
     *
     * @return true if animation is finished and could be removed from animator.
     */
    protected abstract boolean animate(double delta);

    /**
     * Called one time when animation ended.
     */
    protected void destroy() {
        // Could be implemented later.
    }

}
