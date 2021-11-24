package com.spinyowl.legui.animation;

/**
 * Animation processor..
 */
public final class AnimatorProvider {

  /**
   * Default animator of animator.
   */
  private static Animator animator = new AnimatorImpl();

  private AnimatorProvider() {
  }

  /**
   * Gets animator.
   *
   * @return the animator
   */
  public static Animator getAnimator() {
    return AnimatorProvider.animator;
  }

  /**
   * Sets animator.
   *
   * @param animator the animator.
   */
  public static void setAnimator(Animator animator) {
    if (animator != null) {
      AnimatorProvider.animator = animator;
    }
  }

}
