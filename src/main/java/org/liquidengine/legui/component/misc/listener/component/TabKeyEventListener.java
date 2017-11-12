package org.liquidengine.legui.component.misc.listener.component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.listener.EventListener;
import org.liquidengine.legui.system.context.Context;
import org.lwjgl.glfw.GLFW;

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
        if (event.getComponent() == null) {
            return;
        }
        if (event.getKey() == GLFW.GLFW_KEY_TAB && event.getAction() != GLFW.GLFW_PRESS) {
            System.out.println(event.getComponent());
            if (event.getMods() == 0) {
                Component next = findNext(event.getComponent());
                // if ((next == null || next == component) && cycled) next = event.getContext().getFrame().getContainer();
                moveToNext(event.getContext(), event.getComponent(), next);
            } else if (event.getMods() == GLFW.GLFW_MOD_SHIFT) {
                Component prev = findPrev(event.getComponent());
                moveToNext(event.getContext(), event.getComponent(), prev);
            }
        }
    }

    private Component findPrev(Component component) {
        if (!component.isVisible()) {
            return null;
        }

        Component prev = null;
        if (component.isTabFocusable()) {
            prev = component;
        }
        prev = findPrevInParent(component, component.getParent(), prev);
        return prev;

    }

    private Component findPrevInParent(Component component, Component parent, Component prev) {
        if (parent == null) {
            return prev;
        }

        List<Component> childs = parent.getChilds();
        childs.sort(comparator);
        Collections.reverse(childs);

        int index = childs.indexOf(component);
        if (index != childs.size() - 1) {
            for (int i = index + 1; i < childs.size(); i++) {
                Component child = childs.get(i);
                if (child.isVisible()) {
                    if (child.isTabFocusable()) {
                        prev = child;
                        if (!child.isEmpty()) {
                            prev = findPrevInChilds(child.getChilds(), prev);
                        }
                        return prev;
                    } else {
                        if (!child.isEmpty()) {
                            Component cprev = findPrevInChilds(child.getChilds(), prev);
                            if (prev != cprev) {
                                return cprev;
                            }
                        }
                    }
                }
            }
            prev = findPrevInParent(parent, parent.getParent(), prev);
        } else {
            if (parent.isTabFocusable()) {
                prev = parent;
                return prev;
            } else {
                prev = findPrevInParent(parent, parent.getParent(), prev);
            }
        }
        return prev;
    }

    private Component findPrevInChilds(List<Component> childs, Component prev) {
        childs.sort(comparator);
        Collections.reverse(childs);
        for (Component child : childs) {
            if (child.isVisible()) {
                if (child.isTabFocusable()) {
                    prev = child;
                    if (!child.isEmpty()) {
                        prev = findPrevInChilds(child.getChilds(), prev);
                    }
                    return prev;
                } else {
                    if (!child.isEmpty()) {
                        Component cprev = findPrevInChilds(child.getChilds(), prev);
                        if (prev != cprev) {
                            return cprev;
                        }
                    }
                }
            }
        }

        return prev;
    }

    private Component findNext(Component component) {
        if (!component.isVisible()) {
            return null;
        }

        Component next = null;
        if (component.isTabFocusable()) {
            next = component;
        }
        if (component.isEmpty()) {
            next = findNextInParent(component, component.getParent(), next);
        } else {
            next = findNextInChilds(component.getChilds(), next);
            if (next == component) {
                next = findNextInParent(component, component.getParent(), next);
            }
        }
        return next;
    }

    private Component findNextInChilds(List<Component> childs, Component next) {
        if (childs.isEmpty()) {
            return next;
        }

        childs.sort(comparator);

        for (Component child : childs) {
            if (child.isVisible()) {
                if (child.isTabFocusable()) {
                    next = child;
                    return next;
                } else {
                    if (!child.isEmpty()) {
                        Component cnext = findNextInChilds(child.getChilds(), next);
                        if (next != cnext) {
                            return cnext;
                        }
                    }
                }
            }
        }

        return next;
    }

    private Component findNextInParent(Component component, Component parent, Component next) {
        if (parent == null) {
            return next;
        }

        List<Component> childs = parent.getChilds();
        childs.sort(comparator);

        int index = childs.indexOf(component);
        if (index != childs.size() - 1) {
            for (int i = index + 1; i < childs.size(); i++) {
                Component child = childs.get(i);
                if (child.isVisible()) {
                    if (child.isTabFocusable()) {
                        next = child;
                        return next;
                    } else {
                        if (!child.isEmpty()) {
                            Component cnext = findNextInChilds(child.getChilds(), next);
                            if (next != cnext) {
                                return cnext;
                            }
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

    private void moveToNext(Context context, Component component, Component next) {
        if (component != null) {
            component.setFocused(false);
        }
        if (next != null) {
            Component focusedGui = context.getFocusedGui();
            if (focusedGui != null) {
                focusedGui.setFocused(false);
            }
            next.setFocused(true);
            context.setFocusedGui(next);
        }
    }

//    private Component findPrev(Component component) {
//        Component prev = component;
//        Component parent = component.getParent();
//        if (parent != null) {
//            prev = findPrevInParent(component, parent, prev);
//        } else {
//            prev = findPrevInChilds(component, component.getChilds(), prev);
//        }
//        return prev;
//    }
//
//    private Component findPrevInChilds(Component component, List<Component> childs, Component prev) {
//        childs.sort(comparator);
//        Collections.reverse(childs);
//        for (Component child : childs) {
//            if (child.isVisible()) {
//                if (child.isTabFocusable()) {
//                    if (!child.isEmpty()) {
//                        prev = findPrevInChilds(component, child.getChilds(), prev);
//                        if (prev == component) {
//                            prev = child;
//                        }
//                    } else {
//                        prev = child;
//                    }
//                    break;
//                } else {
//                    if (!child.isEmpty()) {
//                        prev = findPrevInChilds(component, child.getChilds(), prev);
//                    }
//                    if (component != prev) {
//                        break;
//                    }
//                }
//            }
//        }
//        return prev;
//    }
//
//    private Component findPrevInParent(Component component, Component parent, Component prev) {
//        if (parent == null) {
//            return prev;
//        }
//        List<Component> neighbors = parent.getChilds();
//        neighbors.sort(comparator);
//        int indexInParent = neighbors.indexOf(component);
//        for (int i = indexInParent - 1; i >= 0; i--) {
//            Component neighbor = neighbors.get(i);
//            if (neighbor.isVisible()) {
//                if (neighbor.isTabFocusable()) {
//                    if (!neighbor.isEmpty()) {
//                        prev = findPrevInChilds(component, neighbor.getChilds(), prev);
//                        break;
//                    }
//                    prev = neighbor;
//                    break;
//                } else {
//                    if (!neighbor.isEmpty()) {
//                        prev = findNextInChilds(neighbor, prev);
//                    }
//                }
//            }
//        }
////        if (prev == component) {
////            prev = findPrevInParentNeighbors(parent, prev);
////        }
//        return prev;
//    }
//
//    private Component findNext(Component component) {
//        Component next = component;
//        if (component.isEmpty()) {
//            if ((next = findNextInParent(component, component.getParent(), next)) == component) {
//                Component parent = component;
//                while ((parent = parent.getParent()) != null) {
//                    next = parent;
//                }
//            }
//        } else {
//            next = findNextInChilds(component, next);
//            if (next == component) {
//                next = findNextInParent(component, component.getParent(), next);
//            }
//        }
//
//        return next;
//    }
//
//    private Component findNextInParent(Component component, Component parent, Component next) {
//        if (parent == null) {
//            return next;
//        }
//        List<Component> neighbors = parent.getChilds();
//        neighbors.sort(comparator);
//        int indexInParent = neighbors.indexOf(component);
//        for (int i = indexInParent + 1; i < neighbors.size(); i++) {
//            Component neighbor = neighbors.get(i);
//            if (neighbor.isVisible()) {
//                if (neighbor.isTabFocusable()) {
//                    next = neighbor;
//                    break;
//                } else {
//                    if (!neighbor.isEmpty()) {
//                        next = findNextInChilds(neighbor, next);
//                    }
//                }
//            }
//        }
//        if (next == component) {
//            next = findNextInParentNeighbors(parent, next);
//        }
//        return next;
//    }
//
//    private Component findNextInParentNeighbors(Component parent, Component next) {
//        Component parentOfParent = parent.getParent();
//        if (parentOfParent != null) {
//            List<Component> parentNeighbors = parentOfParent.getChilds();
//            parentNeighbors.sort(comparator);
//            int parentIndex = parentNeighbors.indexOf(parent);
//            int neighborsCount = parentNeighbors.size();
//            if (parentIndex == neighborsCount - 1) {
//                return findNextInParentNeighbors(parentOfParent, next);
//            } else {
//                for (int i = parentIndex + 1; i < neighborsCount; i++) {
//                    Component neighbor = parentNeighbors.get(i);
//                    if (neighbor.isVisible()) {
//                        if (neighbor.isTabFocusable()) {
//                            return neighbor;
//                        } else {
//                            if (!neighbor.isEmpty()) {
//                                return findNextInChilds(neighbor, next);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return findNextInParentNeighbors(parentOfParent, next);
//    }
//
//    private Component findNextInChilds(Component component, Component next) {
//        List<Component> childs = component.getChilds();
//        childs.sort(comparator);
//        for (Component child : childs) {
//            if (child.isVisible()) {
//                if (child.isTabFocusable()) {
//                    next = child;
//                    break;
//                } else {
//                    if (!child.isEmpty()) {
//                        next = findNextInChilds(child, next);
//                    }
//                }
//            }
//        }
//        return next;
//    }
}
