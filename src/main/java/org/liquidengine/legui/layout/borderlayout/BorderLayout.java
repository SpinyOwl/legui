package org.liquidengine.legui.layout.borderlayout;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.layout.Layout;
import org.liquidengine.legui.layout.LayoutConstraint;
import org.lwjgl.util.yoga.Yoga;

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

    /**
     * Default constructor without gaps.
     */
    public BorderLayout() {
    }

    /**
     * Constructor allows to set gaps between components.
     *
     * @param horizontalGap horizontal gap.
     * @param verticalGap vertical gap.
     */
    public BorderLayout(float horizontalGap, float verticalGap) {

        this.horizontalGap = horizontalGap;
        this.verticalGap = verticalGap;
    }

    @Override
    public void addComponent(Component component, LayoutConstraint constraint) {
        if (constraint == null || constraint instanceof BorderLayoutConstraint) {
            if (constraint == null) {
                centerComponent = component;
            } else {
                BorderLayoutConstraint r = (BorderLayoutConstraint) constraint;
                switch (r) {
                    case TOP:
                        topComponent = component;
                        break;
                    case LEFT:
                        leftComponent = component;
                        break;
                    case CENTER:
                        centerComponent = component;
                        break;
                    case RIGHT:
                        rightComponent = component;
                        break;
                    case BOTTOM:
                        bottomComponent = component;
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
            case TOP:
                return topComponent;
            case LEFT:
                return leftComponent;
            case CENTER:
                return centerComponent;
            case RIGHT:
                return rightComponent;
            case BOTTOM:
                return bottomComponent;
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
        if (parent == null) {
            return;
        }
        long rootNode = Yoga.YGNodeNew();

        long mNode = Yoga.YGNodeNew();

        long tNode = Yoga.YGNodeNew();
        long lNode = Yoga.YGNodeNew();
        long cNode = Yoga.YGNodeNew();
        long rNode = Yoga.YGNodeNew();
        long bNode = Yoga.YGNodeNew();

        int row = 0;
        if (topComponent != null) {
            Yoga.YGNodeInsertChild(rootNode, tNode, row++);
        }
        if (leftComponent != null || centerComponent != null || rightComponent != null) {
            Yoga.YGNodeInsertChild(rootNode, mNode, row++);
        }
        if (bottomComponent != null) {
            Yoga.YGNodeInsertChild(rootNode, bNode, row++);
        }

        int column = 0;
        if (leftComponent != null) {
            Yoga.YGNodeInsertChild(mNode, lNode, column++);
        }
        if (centerComponent != null) {
            Yoga.YGNodeInsertChild(mNode, cNode, column++);
        }
        if (rightComponent != null) {
            Yoga.YGNodeInsertChild(mNode, rNode, column++);
        }

        Yoga.YGNodeStyleSetFlexDirection(rootNode, Yoga.YGFlexDirectionColumn);
        Yoga.YGNodeStyleSetAlignItems(rootNode, Yoga.YGAlignStretch);
        Yoga.YGNodeStyleSetJustifyContent(rootNode, Yoga.YGJustifySpaceBetween);

        Yoga.YGNodeStyleSetFlexDirection(mNode, Yoga.YGFlexDirectionRow);
        Yoga.YGNodeStyleSetAlignItems(mNode, Yoga.YGAlignStretch);
        Yoga.YGNodeStyleSetJustifyContent(mNode, Yoga.YGJustifySpaceBetween);

        Vector2f minLeft = getMinSize(leftComponent);
        Vector2f minCenter = getMinSize(centerComponent);
        Vector2f minRight = getMinSize(rightComponent);
        float midMaxOfMin = Math.max(Math.max(minLeft.y, minCenter.y), minRight.y);
        Yoga.YGNodeStyleSetMinHeight(mNode, midMaxOfMin);
        Yoga.YGNodeStyleSetMaxHeight(mNode, Float.MAX_VALUE);

        Vector2f size = parent.getSize();
        Yoga.YGNodeStyleSetWidth(mNode, size.x);

        Yoga.YGNodeStyleSetFlexGrow(mNode, 1);
        Yoga.YGNodeStyleSetFlexShrink(mNode, 1);

        Vector2f maxTop = getMaxSize(topComponent);
        Yoga.YGNodeStyleSetMaxWidth(tNode, maxTop.x);
        Yoga.YGNodeStyleSetMaxHeight(tNode, maxTop.y);
        Vector2f minTop = getMinSize(topComponent);
        Yoga.YGNodeStyleSetMinWidth(tNode, minTop.x);
        Yoga.YGNodeStyleSetMinHeight(tNode, minTop.y);
        Yoga.YGNodeStyleSetFlexGrow(tNode, 1);
        Yoga.YGNodeStyleSetFlexShrink(tNode, 1);
        Yoga.YGNodeStyleSetWidth(tNode, size.x);

        Vector2f maxBottom = getMaxSize(bottomComponent);
        Yoga.YGNodeStyleSetMaxWidth(bNode, maxBottom.x);
        Yoga.YGNodeStyleSetMaxHeight(bNode, maxBottom.y);
        Vector2f minBottom = getMinSize(bottomComponent);
        Yoga.YGNodeStyleSetMinWidth(bNode, minBottom.x);
        Yoga.YGNodeStyleSetMinHeight(bNode, minBottom.y);
        Yoga.YGNodeStyleSetFlexGrow(bNode, 1);
        Yoga.YGNodeStyleSetFlexShrink(bNode, 1);
        Yoga.YGNodeStyleSetWidth(bNode, size.x);

        Vector2f maxLeft = getMaxSize(leftComponent);
        Yoga.YGNodeStyleSetMaxWidth(lNode, maxLeft.x);
        Yoga.YGNodeStyleSetMaxHeight(lNode, maxLeft.y);
        Yoga.YGNodeStyleSetMinWidth(lNode, minLeft.x);
        Yoga.YGNodeStyleSetMinHeight(lNode, minLeft.y);
        Yoga.YGNodeStyleSetFlexGrow(lNode, 1);
        Yoga.YGNodeStyleSetFlexShrink(lNode, 1);
        Yoga.YGNodeStyleSetMinHeight(lNode, midMaxOfMin);

        Vector2f maxCenter = getMaxSize(centerComponent);
        Yoga.YGNodeStyleSetMaxWidth(cNode, maxCenter.x);
        Yoga.YGNodeStyleSetMaxHeight(cNode, maxCenter.y);
        Yoga.YGNodeStyleSetMinWidth(cNode, minCenter.x);
        Yoga.YGNodeStyleSetMinHeight(cNode, minCenter.y);
        Yoga.YGNodeStyleSetFlexGrow(cNode, 1);
        Yoga.YGNodeStyleSetFlexShrink(cNode, 1);
        Yoga.YGNodeStyleSetMinHeight(cNode, midMaxOfMin);

        Vector2f maxRight = getMaxSize(rightComponent);
        Yoga.YGNodeStyleSetMaxWidth(rNode, maxRight.x);
        Yoga.YGNodeStyleSetMaxHeight(rNode, maxRight.y);
        Yoga.YGNodeStyleSetMinWidth(rNode, minRight.x);
        Yoga.YGNodeStyleSetMinHeight(rNode, minRight.y);
        Yoga.YGNodeStyleSetFlexGrow(rNode, 1);
        Yoga.YGNodeStyleSetFlexShrink(rNode, 1);
        Yoga.YGNodeStyleSetMinHeight(rNode, midMaxOfMin);

        Yoga.nYGNodeCalculateLayout(rootNode, size.x, size.y, Yoga.YGDirectionLTR);

        Vector2f d = new Vector2f(getX(mNode), getY(mNode));

        setSizeAndPos(getW(tNode), getH(tNode), getX(tNode), getY(tNode), topComponent);
        setSizeAndPos(getW(bNode), getH(bNode), getX(bNode), getY(bNode), bottomComponent);
        setSizeAndPos(getW(lNode), getH(lNode), getX(lNode) + d.x, getY(lNode) + d.y, leftComponent);
        setSizeAndPos(getW(rNode), getH(rNode), getX(rNode) + d.x, getY(rNode) + d.y, rightComponent);
        setSizeAndPos(getW(cNode), getH(cNode), getX(cNode) + d.x, getY(cNode) + d.y, centerComponent);

        Yoga.YGNodeFree(rootNode);
        Yoga.YGNodeFree(mNode);
        Yoga.YGNodeFree(tNode);
        Yoga.YGNodeFree(lNode);
        Yoga.YGNodeFree(cNode);
        Yoga.YGNodeFree(rNode);
        Yoga.YGNodeFree(bNode);
    }

    private float getY(long tNode) {
        return Yoga.YGNodeLayoutGetTop(tNode);
    }

    private float getX(long tNode) {
        return Yoga.YGNodeLayoutGetLeft(tNode);
    }

    private float getH(long tNode) {
        return Yoga.YGNodeLayoutGetHeight(tNode);
    }

    private float getW(long tNode) {
        return Yoga.YGNodeLayoutGetWidth(tNode);
    }


    private Vector2f getMinSize(Component bottomComponent) {
        return bottomComponent != null ? bottomComponent.getMinimumSize() : new Vector2f();
    }

    private Vector2f getMaxSize(Component centerComponent) {
        return centerComponent != null ? centerComponent.getMaximumSize() : new Vector2f(Float.MAX_VALUE);
    }

    private void setSizeAndPos(float w, float h, float x, float y, Component component) {
        if (component != null) {
            component.getSize().set(w, h);
            component.getPosition().set(x, y);
        }
    }

}
