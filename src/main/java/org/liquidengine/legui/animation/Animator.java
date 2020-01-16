package org.liquidengine.legui.animation;

/**
 * Animation processor.
 *
 * @author ShchAlexander.
 */
public interface Animator {

    /**
     * This method used to process animations.
     */
    void runAnimations();

    /**
     * Used to add animation to animator.
     *
     * @param animation animation to add.
     */
    void pushAnimation(Animation animation);

    /**
     * Used to remove animation from animator. In case if animation is not finished animation still should be removed and terminated.
     *
     * @param animation animation to remove.
     */
    void removeAnimation(Animation animation);


}
