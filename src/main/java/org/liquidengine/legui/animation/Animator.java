package org.liquidengine.legui.animation;

import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class Animator {

    private List<Animation> animationsToInitialize = new CopyOnWriteArrayList<>();
    private List<Animation> animations             = new CopyOnWriteArrayList<>();
    private List<Animation> animationsToDestroy    = new CopyOnWriteArrayList<>();
    private double previousTime;

    private Animator() {
    }

    public static Animator getInstance() {
        return AnimatorHolder.INSTANCE;
    }

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
            if (animation.animate(delta)) {
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

    public void pushAnimation(Animation animation) {
        animationsToInitialize.add(animation);
    }

    private static class AnimatorHolder {
        private static final Animator INSTANCE = new Animator();
    }
}
