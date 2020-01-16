package org.liquidengine.legui.animation;

/**
 * Draft animation realization.
 * <p>
 * Created by ShchAlexander on 31.07.2017.
 *
 * @author ShchAlexander
 */
public abstract class Animation {

    /**
     * Flag that indicates if animation was started.
     */
    private boolean animationStarted = false;

    /**
     * Adds animation to animator.
     */
    public void startAnimation() {
        if (!animationStarted) {
            AnimatorProvider.getAnimator().pushAnimation(this);
            animationStarted = true;
        }
    }

    /**
     * Called one time before animate loop.
     */
    protected void beforeAnimation() {
        // Could be implemented later.
    }

    /**
     * This method used to update animated object. Called by animator every frame. Removed from animator and stops when this method returns true. <p> Returns
     * true if animation is finished and could be removed from animator.
     *
     * @param delta delta time (from previous call).
     * @return true if animation is finished and could be removed from animator.
     */
    protected abstract boolean animate(double delta);

    /**
     * Called one time when animation ended.
     */
    protected void afterAnimation() {
        // Could be implemented later.
    }

    /**
     * Used to stop animation. Removes animation from animator.
     */
    public void stopAnimation() {
        AnimatorProvider.getAnimator().removeAnimation(this);
    }

    /**
     * Returns the flag that indicates if animation was started.
     *
     * @return the flag that indicates if animation was started.
     */
    public boolean isAnimationStarted() {
        return animationStarted;
    }

}
