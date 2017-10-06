package org.liquidengine.legui.animation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.lwjgl.glfw.GLFW;

/**
 * Animation processor.
 *
 * @author Aliaksandr_Shcherbin.
 */
public abstract class Animator {

    /**
     * Default instance of animator.
     */
    private static Animator instance = new AnimatorImpl();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Animator getInstance() {
        return Animator.instance;
    }

    /**
     * Sets instance.
     *
     * @param animator the instance.
     */
    public static void setInstance(Animator animator) {
        if (animator != null) {
            instance = animator;
        }
    }

    /**
     * This method used to process animations.
     */
    public abstract void runAnimations();

    /**
     * Used to add animation to animator.
     *
     * @param animation animation to add.
     */
    protected abstract void pushAnimation(Animation animation);

    private static class AnimatorImpl extends Animator {

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
                animation.initialize();
                animationsToInitialize.remove(animation);
                animations.add(animation);
            }

            List<Animation> processList = new ArrayList<>(animations);
            for (Animation animation : processList) {
                if (animation.animate(delta)) {
                    animations.remove(animation);
                    animationsToDestroy.add(animation);
                }
            }

            List<Animation> destroyList = new ArrayList<>(animationsToDestroy);
            for (Animation animation : destroyList) {
                animation.destroy();
                animationsToDestroy.remove(animation);
            }

            previousTime = currentTime;
        }

        /**
         * Used to add animation to animator.
         *
         * @param animation animation to add.
         */
        protected void pushAnimation(Animation animation) {
            animationsToInitialize.add(animation);
        }

    }

}
