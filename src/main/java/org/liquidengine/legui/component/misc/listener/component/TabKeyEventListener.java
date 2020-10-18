package org.liquidengine.legui.component.misc.listener.component;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.listener.EventListener;
import org.liquidengine.legui.system.context.Context;
import org.lwjgl.glfw.GLFW;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ShchAlexander on 11.11.2017.
 */
public class TabKeyEventListener implements EventListener<KeyEvent> {

    private Comparator<? super Component> comparator = Comparator.comparingInt((Component::getTabIndex));

    /**
     * Used to handle specific event.
     *
     * @param event event to handle.
     */
    @Override
    public void process(KeyEvent event) {
        if (event.getTargetComponent() == null) {
            return;
        }
        if (event.getKey() == GLFW.GLFW_KEY_TAB && event.getAction() != GLFW.GLFW_PRESS) {
            boolean controlPressed = (event.getMods() & GLFW.GLFW_MOD_CONTROL) != 0;
            boolean shiftPressed = (event.getMods() & GLFW.GLFW_MOD_SHIFT) != 0;
            if (controlPressed && !shiftPressed) {
                Component next = findNext(event.getTargetComponent());
                Context.setFocusedGui(next, event.getContext(), event.getFrame());
            } else if (controlPressed) {
                Component prev = findPrev(event.getTargetComponent());
                Context.setFocusedGui(prev, event.getContext(), event.getFrame());
            }
        }
    }

    /**
     * Used to find previous component (components sorted by tab index).
     *
     * @param component current component.
     * @return previous component.
     */
    private Component findPrev(Component component) {
        if (!component.isVisible()) {
            return null;
        }

        Component prev = null;
        if (isFF(component)) {
            prev = component;
        }
        prev = findPrevInParent(component, component.getParent(), prev);
        return prev;

    }

    /**
     * Used to find previous component in parent (in neighbors).
     *
     * @param component current component.
     * @param parent    parent component.
     * @param prev      current previous component.
     * @return previous component.
     */
    private Component findPrevInParent(Component component, Component parent, Component prev) {
        if (parent == null) {
            return prev;
        }

        List<Component> childComponents = parent.getChildComponents();
        childComponents.sort(comparator);
        Collections.reverse(childComponents);

        int index = childComponents.indexOf(component);
        if (index != childComponents.size() - 1) {
            for (int i = index + 1; i < childComponents.size(); i++) {
                Component child = childComponents.get(i);
                if (!child.isVisible()) {
                    continue;
                }
                if (!child.isEmpty()) {
                    Component cprev = findPrevInChildComponents(child.getChildComponents(), prev);
                    if (prev != cprev) {
                        return cprev;
                    }
                } else if (isFF(child)) {
                    return child;
                }
            }
            prev = findPrevInParent(parent, parent.getParent(), prev);
        } else if (isFF(parent)) {
            return parent;
        } else {
            prev = findPrevInParent(parent, parent.getParent(), prev);
        }
        return prev;
    }

    private boolean isFF(Component component) {
        return component.isTabFocusable() && component.isFocusable();
    }

    /**
     * Used to find previous component in child components.
     *
     * @param childComponents child components.
     * @param previous        current previous component.
     * @return previous component.
     */
    private Component findPrevInChildComponents(List<Component> childComponents, Component previous) {
        Component previousComponent = previous;
        childComponents.sort(comparator);
        Collections.reverse(childComponents);
        for (Component child : childComponents) {
            if (!child.isVisible()) {
                continue;
            }
            if (!child.isEmpty()) {
                Component cprev = findPrevInChildComponents(child.getChildComponents(), previousComponent);
                if (previousComponent != cprev) {
                    return cprev;
                }
            } else if (isFF(child)) {
                return child;
            }
        }

        return previousComponent;
    }

    /**
     * Used to find next component (components sorted by tab index).
     *
     * @param component current component.
     * @return next component.
     */
    private Component findNext(Component component) {
        if (!component.isVisible()) {
            return null;
        }

        Component next = null;
        if (isFF(component)) {
            next = component;
        }
        if (component.isEmpty()) {
            next = findNextInParent(component, component.getParent(), next);
        } else {
            next = findNextInChildComponents(component.getChildComponents(), next);
            if (next == component) {
                next = findNextInParent(component, component.getParent(), next);
            }
        }
        return next;
    }

    /**
     * Used to find next component in child components.
     *
     * @param childComponents child components.
     * @param next            current next component.
     * @return next component.
     */
    private Component findNextInChildComponents(List<Component> childComponents, Component next) {
        if (childComponents.isEmpty()) {
            return next;
        }

        childComponents.sort(comparator);

        for (Component child : childComponents) {
            if (!child.isVisible()) {
                continue;
            }
            if (isFF(child)) {
                return child;
            } else if (!child.isEmpty()) {
                Component cnext = findNextInChildComponents(child.getChildComponents(), next);
                if (next != cnext) {
                    return cnext;
                }
            }
        }

        return next;
    }

    /**
     * Used to find next component in parent (in neighbors).
     *
     * @param component current component.
     * @param parent    parent component.
     * @param next      current next component.
     * @return next component.
     */
    private Component findNextInParent(Component component, Component parent, Component next) {
        if (parent == null) {
            return next;
        }

        List<Component> childComponents = parent.getChildComponents();
        childComponents.sort(comparator);

        int index = childComponents.indexOf(component);
        if (index != childComponents.size() - 1) {
            for (int i = index + 1; i < childComponents.size(); i++) {
                Component child = childComponents.get(i);
                if (child.isVisible()) {
                    if (isFF(child)) {
                        return child;
                    } else if (!child.isEmpty()) {
                        Component cnext = findNextInChildComponents(child.getChildComponents(), next);
                        if (next != cnext) {
                            return cnext;
                        }
                    }
                }
            }
            next = findNextInParent(parent, parent.getParent(), next);
        } else {
            next = findNextInParent(parent, parent.getParent(), next);
        }

        return next;
    }
}
