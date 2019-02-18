package org.liquidengine.legui.animation;

import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AnimatorImpl implements Animator {

    /**
     * List of animations to initialize.
     */
    private List<Animation> animationsToInitialize = new CopyOnWriteArrayList<>();
    /**
     * List of animations to animate.
     */
    private List<Animation> animations = new CopyOnWriteArrayList<>();
    /**
     * List of animation to destroy.
     */
    private List<Animation> animationsToDestroy = new CopyOnWriteArrayList<>();
    /**
     * List of animation to destroy.
     */
    private List<Animation> animationsToRemove = new CopyOnWriteArrayList<>();
    /**
     * Used to store previous time.
     */
    private double previousTime;

    /**
     * This method used to process animations.
     */
    public void runAnimations() {
        double currentTime = GLFW.glfwGetTime();
        double delta = currentTime - previousTime;

        List<Animation> initializeList = new ArrayList<>(animationsToInitialize);
        for (Animation animation : initializeList) {
            animation.beforeAnimation();
            animationsToInitialize.remove(animation);
            animations.add(animation);
        }

        List<Animation> processList = new ArrayList<>(animations);
        for (Animation animation : processList) {
            if (animationsToRemove.contains(animation)) {
                animationsToRemove.remove(animation);
                animations.remove(animation);
                animationsToDestroy.add(animation);
            } else if (animation.animate(delta)) {
                animations.remove(animation);
                animationsToDestroy.add(animation);
            }
        }

        List<Animation> destroyList = new ArrayList<>(animationsToDestroy);
        for (Animation animation : destroyList) {
            animation.afterAnimation();
            animationsToDestroy.remove(animation);
        }

        previousTime = currentTime;
    }

    /**
     * Used to add animation to animator.
     *
     * @param animation animation to add.
     */
    public void pushAnimation(Animation animation) {
        animationsToInitialize.add(animation);
    }

    /**
     * Used to remove animation from animator. In case if animation is not finished animation still should be removed and terminated.
     *
     * @param animation animation to remove.
     */
    @Override
    public void removeAnimation(Animation animation) {
        animationsToRemove.add(animation);
    }

}