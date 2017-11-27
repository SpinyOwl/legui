package org.liquidengine.legui.layout.borderlayout;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.layout.Layout;
import org.liquidengine.legui.layout.LayoutConstraint;
import org.lwjgl.util.yoga.Yoga;

/**
 * A border layout lays out a container, arranging and resizing its components to fit in five regions: TOP, LEFT, CENTER, RIGHT, and BOTTOM. Each region may
 * contain no more than one component, and is identified by a corresponding constant: <code>TOP</code>, <code>LEFT</code>, <code>CENTER</code>,
 * <code>RIGHT</code>, and <code>BOTTOM</code>.  When adding a component to a container with a border layout, use one of these five constants, for example:
 * <pre>
 *    {@link org.liquidengine.legui.component.Panel} p = new {@link org.liquidengine.legui.component.Panel}();
 *    p.setLayout(new {@link BorderLayout}());
 *    p.add(new Button("Okay"), {@link BorderLayoutConstraint#TOP});
 * </pre>
 * As a convenience, <code>BorderLayout</code> interprets the absence of a string specification the same as the constant <code>CENTER</code>:
 * <pre>
 *    {@link org.liquidengine.legui.component.Panel} p2 = new {@link org.liquidengine.legui.component.Panel}();
 *    p2.setLayout(new {@link BorderLayout}());
 *    p2.add(new {@link org.liquidengine.legui.component.TextArea}());  // Same as p.add(new {@link org.liquidengine.legui.component.TextArea}(), {@link
 * BorderLayoutConstraint#CENTER});
 * </pre>
 * <p>. Created by ShchAlexander on 23.11.2017.
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

    /**
     * Used to add component to layout.
     *
     * @param component component to add.
     * @param constraint layout constraint (must be instance of {@link BorderLayoutConstraint}.
     * @throws IllegalArgumentException if provided constraint is not instance of {@link BorderLayoutConstraint}.
     */
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

    /**
     * Used to remove component from layout.
     *
     * @param component component to remove.
     */
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

    /**
     * Used to retrieve component attached to one of regions.
     *
     * @param constraint region constraint.
     * @return component or null.
     */
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

    /**
     * Used to calculate minimum size for parent component.
     *
     * @param parent component to calculate minimum size.
     * @return calculated minimum size for specified component.
     */
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

    /**
     * Used to calculate preferred size for parent component.
     *
     * @param parent component to calculate preferred size.
     * @return calculated preferred size for specified component.
     */
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

    /**
     * Used to calculate maximum size for parent component.
     *
     * @param parent component to calculate maximum size.
     * @return calculated maximum size for specified component.
     */
    @Override
    public Vector2f getMaximumSize(Component parent) {
        return new Vector2f(Float.MAX_VALUE);
    }

    /**
     * Used to lay out child components for parent component.
     *
     * @param parent component to lay out.
     */
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

        Vector2f d = new Vector2f(Yoga.YGNodeLayoutGetLeft(mNode), Yoga.YGNodeLayoutGetTop(mNode));

        setSizeAndPos(topComponent,
            Yoga.YGNodeLayoutGetLeft(tNode), Yoga.YGNodeLayoutGetTop(tNode),
            Yoga.YGNodeLayoutGetWidth(tNode), Yoga.YGNodeLayoutGetHeight(tNode)
        );
        setSizeAndPos(bottomComponent,
            Yoga.YGNodeLayoutGetLeft(bNode), Yoga.YGNodeLayoutGetTop(bNode),
            Yoga.YGNodeLayoutGetWidth(bNode), Yoga.YGNodeLayoutGetHeight(bNode)
        );
        setSizeAndPos(leftComponent,
            Yoga.YGNodeLayoutGetLeft(lNode) + d.x, Yoga.YGNodeLayoutGetTop(lNode) + d.y,
            Yoga.YGNodeLayoutGetWidth(lNode), Yoga.YGNodeLayoutGetHeight(lNode)
        );
        setSizeAndPos(rightComponent,
            Yoga.YGNodeLayoutGetLeft(rNode) + d.x, Yoga.YGNodeLayoutGetTop(rNode) + d.y,
            Yoga.YGNodeLayoutGetWidth(rNode), Yoga.YGNodeLayoutGetHeight(rNode)
        );
        setSizeAndPos(centerComponent,
            Yoga.YGNodeLayoutGetLeft(cNode) + d.x, Yoga.YGNodeLayoutGetTop(cNode) + d.y,
            Yoga.YGNodeLayoutGetWidth(cNode), Yoga.YGNodeLayoutGetHeight(cNode)
        );

        Yoga.YGNodeFree(rootNode);
        Yoga.YGNodeFree(mNode);
        Yoga.YGNodeFree(tNode);
        Yoga.YGNodeFree(lNode);
        Yoga.YGNodeFree(cNode);
        Yoga.YGNodeFree(rNode);
        Yoga.YGNodeFree(bNode);
    }


    private Vector2f getMinSize(Component bottomComponent) {
        return bottomComponent != null ? bottomComponent.getMinimumSize() : new Vector2f();
    }

    private Vector2f getMaxSize(Component centerComponent) {
        return centerComponent != null ? centerComponent.getMaximumSize() : new Vector2f(Float.MAX_VALUE);
    }

    private void setSizeAndPos(Component component, float x, float y, float w, float h) {
        if (component != null) {
            component.getSize().set(w, h);
            component.getPosition().set(x, y);
        }
    }

}
