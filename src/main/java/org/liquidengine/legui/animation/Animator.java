package org.liquidengine.legui.animation;

import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Animation processor.
 *
 * @author Aliaksandr_Shcherbin.
 */
public class Animator {
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
     * Private constructor.
     */
    private Animator() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Animator getInstance() {
        return AnimatorHolder.INSTANCE;
    }

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

    /**
     * Instance holder.
     */
    private static class AnimatorHolder {
        private static final Animator INSTANCE = new Animator();
    }
}
