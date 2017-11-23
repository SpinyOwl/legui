package org.liquidengine.legui.layout.borderlayout;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.layout.Layout;
import org.liquidengine.legui.layout.LayoutConstraint;

/**
 * Created by ShchAlexander on 23.11.2017.
 */
public class BorderLayout implements Layout {

    private float horizontalGap;
    private float verticalGap;

    private Component topComponent;
    private Component leftComponent;
    private Component centerComponent;
    private Component rightComponent;
    private Component bottomComponent;

    @Override
    public void addComponent(Component component, LayoutConstraint constraint) {
        if (constraint == null || constraint instanceof BorderLayoutConstraint) {
            if (constraint == null) {
                centerComponent = component;
            } else {
                BorderLayoutConstraint r = (BorderLayoutConstraint) constraint;
                switch (r) {
                    case TOP: topComponent = component;
                        break;
                    case LEFT: leftComponent = component;
                        break;
                    case CENTER: centerComponent = component;
                        break;
                    case RIGHT: rightComponent = component;
                        break;
                    case BOTTOM: bottomComponent = component;
                        break;
                }
            }
        } else {
            throw new IllegalArgumentException("Layout constraint for BorderLayout should be instance of BorderLayoutConstraint.");
        }
    }

    @Override
    public void removeComponent(Component component) {
        if (component == topComponent) {
            topComponent = null;
        } else if (component == leftComponent) {
            leftComponent = null;
        } else if (component == centerComponent) {
            centerComponent = null;
        } else if (component == rightComponent) {
            rightComponent = null;
        } else if (component == bottomComponent) {
            bottomComponent = null;
        }
    }

    public Component getComponent(BorderLayoutConstraint constraint) {
        if (constraint == null) {
            throw new IllegalArgumentException("Cannot get component: constraint is null");
        }
        switch (constraint) {
            case TOP: return topComponent;
            case LEFT: return leftComponent;
            case CENTER: return centerComponent;
            case RIGHT: return rightComponent;
            case BOTTOM: return bottomComponent;
        }
        throw new IllegalArgumentException("Cannot get component: unknown constraint: " + constraint);
    }

    @Override
    public Vector2f getMinimumSize(Component parent) {
        Vector2f minimumSize = new Vector2f(0);
        if (leftComponent != null) {
            Vector2f min = leftComponent.getMinimumSize();
            minimumSize.x += min.x + horizontalGap;
            minimumSize.y = Math.max(min.y, minimumSize.y);
        }
        if (centerComponent != null) {
            Vector2f min = centerComponent.getMinimumSize();
            minimumSize.x += min.x + horizontalGap;
            minimumSize.y = Math.max(min.y, minimumSize.y);

        }
        if (rightComponent != null) {
            Vector2f min = rightComponent.getMinimumSize();
            minimumSize.x += min.x + horizontalGap;
            minimumSize.y = Math.max(min.y, minimumSize.y);

        }
        if (topComponent != null) {
            Vector2f min = topComponent.getMinimumSize();
            minimumSize.x = Math.max(min.x, minimumSize.x);
            minimumSize.y += min.y + verticalGap;
        }
        if (bottomComponent != null) {
            Vector2f min = bottomComponent.getMinimumSize();
            minimumSize.x = Math.max(min.x, minimumSize.x);
            minimumSize.y += min.y + verticalGap;
        }
        return minimumSize;
    }

    @Override
    public Vector2f getPreferredSize(Component parent) {
        Vector2f prefSize = new Vector2f(0);
        if (leftComponent != null) {
            Vector2f pref = leftComponent.getPreferredSize();
            prefSize.x += pref.x + horizontalGap;
            prefSize.y = Math.max(pref.y, prefSize.y);
        }
        if (centerComponent != null) {
            Vector2f pref = centerComponent.getPreferredSize();
            prefSize.x += pref.x + horizontalGap;
            prefSize.y = Math.max(pref.y, prefSize.y);

        }
        if (rightComponent != null) {
            Vector2f pref = rightComponent.getPreferredSize();
            prefSize.x += pref.x + horizontalGap;
            prefSize.y = Math.max(pref.y, prefSize.y);

        }
        if (topComponent != null) {
            Vector2f pref = topComponent.getPreferredSize();
            prefSize.x = Math.max(pref.x, prefSize.x);
            prefSize.y += pref.y + verticalGap;
        }
        if (bottomComponent != null) {
            Vector2f pref = bottomComponent.getPreferredSize();
            prefSize.x = Math.max(pref.x, prefSize.x);
            prefSize.y += pref.y + verticalGap;
        }
        return prefSize;
    }

    @Override
    public Vector2f getMaximumSize(Component parent) {
        return new Vector2f(Float.MAX_VALUE);
    }

    @Override
    public void layout(Component parent) {

    }
}
