package org.liquidengine.legui.layout.borderlayout;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.layout.Layout;
import org.liquidengine.legui.layout.LayoutConstraint;
import org.lwjgl.util.yoga.Yoga;

/**
 * A border layout lays out a container, arranging and resizing its components to fit in five regions: TOP, LEFT, CENTER, RIGHT, and BOTTOM.
 * Each region may contain no more than one component, and is identified by a corresponding constant:
 * <ul>
 * <li><code>TOP</code></li>
 * <li><code>LEFT</code></li>
 * <li><code>CENTER</code></li>
 * <li><code>RIGHT</code></li>
 * <li><code>BOTTOM</code></li>
 * </ul>.
 * When adding a component to a container with a border layout, use one of these five constants, for example:
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
     * Used to apply component margins to yoga nodes.
     *
     * @param node node to apply margin.
     * @param component component associated to node.
     */
    private static void applyMargin(long node, Component component) {
        if (component != null) {
            Vector4f margin = component.getStyle().getMargin();
            if (margin != null) {
                Yoga.YGNodeStyleSetMargin(node, Yoga.YGEdgeLeft, margin.x);
                Yoga.YGNodeStyleSetMargin(node, Yoga.YGEdgeTop, margin.y);
                Yoga.YGNodeStyleSetMargin(node, Yoga.YGEdgeRight, margin.z);
                Yoga.YGNodeStyleSetMargin(node, Yoga.YGEdgeBottom, margin.w);
            }
        }
    }

    /**
     * Used to add component to layout.
     *
     * @param component component to add.
     * @param constraint layout constraint (must be instance of {@link BorderLayoutConstraint}.
     *
     * @throws IllegalArgumentException if provided constraint is not instance of {@link BorderLayoutConstraint}.
     */
    @Override
    public void addComponent(Component component, LayoutConstraint constraint) {
        if (constraint == null || constraint instanceof BorderLayoutConstraint) {
            if (constraint == null) {
                centerComponent = component;
            } else {
                BorderLayoutConstraint layoutConstraint = (BorderLayoutConstraint) constraint;
                switch (layoutConstraint) {
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
                    default:
                        centerComponent = component;
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
     *
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
            default:
                throw new IllegalArgumentException("Cannot get component: unknown constraint: " + constraint);
        }
    }

    /**
     * Used to calculate minimum size for parent component.
     *
     * @param parent component to calculate minimum size.
     *
     * @return calculated minimum size for specified component.
     */
    @Override
    public Vector2f getMinimumSize(Component parent) {
        Vector2f minimumSize = new Vector2f(0);
        if (leftComponent != null) {
            Vector2f min = leftComponent.getStyle().getMinimumSize();
            minimumSize.x += min.x;
            minimumSize.y = Math.max(min.y, minimumSize.y);
        }
        if (centerComponent != null) {
            Vector2f min = centerComponent.getStyle().getMinimumSize();
            minimumSize.x += min.x;
            minimumSize.y = Math.max(min.y, minimumSize.y);

        }
        if (rightComponent != null) {
            Vector2f min = rightComponent.getStyle().getMinimumSize();
            minimumSize.x += min.x;
            minimumSize.y = Math.max(min.y, minimumSize.y);

        }
        if (topComponent != null) {
            Vector2f min = topComponent.getStyle().getMinimumSize();
            minimumSize.x = Math.max(min.x, minimumSize.x);
            minimumSize.y += min.y;
        }
        if (bottomComponent != null) {
            Vector2f min = bottomComponent.getStyle().getMinimumSize();
            minimumSize.x = Math.max(min.x, minimumSize.x);
            minimumSize.y += min.y;
        }
        return minimumSize;
    }

    /**
     * Used to calculate preferred size for parent component.
     *
     * @param parent component to calculate preferred size.
     *
     * @return calculated preferred size for specified component.
     */
    @Override
    public Vector2f getPreferredSize(Component parent) {
        Vector2f prefSize = new Vector2f(0);
        if (leftComponent != null) {
            Vector2f pref = leftComponent.getStyle().getPreferredSize();
            prefSize.x += pref.x;
            prefSize.y = Math.max(pref.y, prefSize.y);
        }
        if (centerComponent != null) {
            Vector2f pref = centerComponent.getStyle().getPreferredSize();
            prefSize.x += pref.x;
            prefSize.y = Math.max(pref.y, prefSize.y);

        }
        if (rightComponent != null) {
            Vector2f pref = rightComponent.getStyle().getPreferredSize();
            prefSize.x += pref.x;
            prefSize.y = Math.max(pref.y, prefSize.y);

        }
        if (topComponent != null) {
            Vector2f pref = topComponent.getStyle().getPreferredSize();
            prefSize.x = Math.max(pref.x, prefSize.x);
            prefSize.y += pref.y;
        }
        if (bottomComponent != null) {
            Vector2f pref = bottomComponent.getStyle().getPreferredSize();
            prefSize.x = Math.max(pref.x, prefSize.x);
            prefSize.y += pref.y;
        }
        return prefSize;
    }

    /**
     * Used to calculate maximum size for parent component.
     *
     * @param parent component to calculate maximum size.
     *
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
        long baseNode = Yoga.YGNodeNew();

        long mNode = Yoga.YGNodeNew();

        long tNode = Yoga.YGNodeNew();
        long lNode = Yoga.YGNodeNew();
        long cNode = Yoga.YGNodeNew();
        long rNode = Yoga.YGNodeNew();
        long bNode = Yoga.YGNodeNew();
        Vector2f size = parent.getSize();

        prepareNodesStructure(baseNode, mNode, tNode, lNode, cNode, rNode, bNode);

        prepareBaseNode(parent, baseNode, size);

        Vector2f minLeft = getMinSize(leftComponent);
        Vector2f minCenter = getMinSize(centerComponent);
        Vector2f minRight = getMinSize(rightComponent);
        float minHeightForMidRow = Math.max(Math.max(minLeft.y, minCenter.y), minRight.y);

        prepareTopOrBottomNode(tNode, topComponent);
        prepareTopOrBottomNode(bNode, bottomComponent);

        prepareMidRowNode(mNode, minHeightForMidRow);

        prepareLeftMidRightNode(lNode, minLeft, minHeightForMidRow, leftComponent);
        prepareLeftMidRightNode(cNode, minCenter, minHeightForMidRow, centerComponent);
        prepareLeftMidRightNode(rNode, minRight, minHeightForMidRow, rightComponent);

        applyMargin(tNode, topComponent);
        applyMargin(lNode, leftComponent);
        applyMargin(cNode, centerComponent);
        applyMargin(rNode, rightComponent);
        applyMargin(bNode, bottomComponent);

        Yoga.nYGNodeCalculateLayout(baseNode, size.x, size.y, Yoga.YGDirectionLTR);

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

        freeMem(baseNode, mNode, tNode, lNode, cNode, rNode, bNode);
    }

    private void prepareMidRowNode(long mNode, float midMaxOfMin) {
        Yoga.YGNodeStyleSetFlexDirection(mNode, Yoga.YGFlexDirectionRow);
        Yoga.YGNodeStyleSetAlignItems(mNode, Yoga.YGAlignStretch);
        Yoga.YGNodeStyleSetJustifyContent(mNode, Yoga.YGJustifySpaceBetween);

        Yoga.YGNodeStyleSetMinHeight(mNode, midMaxOfMin);
        Yoga.YGNodeStyleSetMaxHeight(mNode, Float.MAX_VALUE);

        Yoga.YGNodeStyleSetFlexGrow(mNode, 1);
        Yoga.YGNodeStyleSetFlexShrink(mNode, 1);
    }

    private void prepareBaseNode(Component parent, long baseNode, Vector2f size) {
        Yoga.YGNodeStyleSetFlexDirection(baseNode, Yoga.YGFlexDirectionColumn);
        Yoga.YGNodeStyleSetAlignItems(baseNode, Yoga.YGAlignStretch);
        Yoga.YGNodeStyleSetJustifyContent(baseNode, Yoga.YGJustifySpaceBetween);

        Vector2f maximumSize = parent.getStyle().getMaximumSize();
        Vector2f minimumSize = parent.getStyle().getMinimumSize();
        Vector2f preferredSize = parent.getStyle().getPreferredSize();
        Yoga.YGNodeStyleSetMinWidth(baseNode, minimumSize == null ? 0 : minimumSize.x);
        Yoga.YGNodeStyleSetMinHeight(baseNode, minimumSize == null ? 0 : minimumSize.y);
        Yoga.YGNodeStyleSetMaxWidth(baseNode, maximumSize == null ? Float.MAX_VALUE : maximumSize.x);
        Yoga.YGNodeStyleSetMaxHeight(baseNode, maximumSize == null ? Float.MAX_VALUE : maximumSize.y);
        Yoga.YGNodeStyleSetWidth(baseNode, preferredSize == null ? size.x : preferredSize.x);
        Yoga.YGNodeStyleSetHeight(baseNode, preferredSize == null ? size.y : preferredSize.y);

        Vector4f padding = parent.getStyle().getPadding();
        if (padding != null) {
            Yoga.YGNodeStyleSetPadding(baseNode, Yoga.YGEdgeLeft, padding.x);
            Yoga.YGNodeStyleSetPadding(baseNode, Yoga.YGEdgeTop, padding.y);
            Yoga.YGNodeStyleSetPadding(baseNode, Yoga.YGEdgeRight, padding.z);
            Yoga.YGNodeStyleSetPadding(baseNode, Yoga.YGEdgeBottom, padding.w);
        }
    }

    private void prepareLeftMidRightNode(long yogaNode, Vector2f minSize, float midMaxOfMin, Component component) {
        if (component != null) {
            Vector2f maxSize = getMaxSize(component);
            Yoga.YGNodeStyleSetMaxWidth(yogaNode, maxSize.x);
            Yoga.YGNodeStyleSetMaxHeight(yogaNode, maxSize.y);
            Yoga.YGNodeStyleSetMinWidth(yogaNode, minSize.x);
            Yoga.YGNodeStyleSetMinHeight(yogaNode, minSize.y);
            Yoga.YGNodeStyleSetFlexGrow(yogaNode, 1);
            Yoga.YGNodeStyleSetFlexShrink(yogaNode, 1);
            Yoga.YGNodeStyleSetMinHeight(yogaNode, midMaxOfMin);
            Vector2f preferredSize = component.getStyle().getPreferredSize();
            if (preferredSize != null) {
                Yoga.YGNodeStyleSetWidth(yogaNode, preferredSize.x);
                Yoga.YGNodeStyleSetHeight(yogaNode, preferredSize.y);
            }
        }
    }

    private void prepareTopOrBottomNode(long bNode, Component component) {
        if (component != null) {
            Vector2f maxTop = getMaxSize(component);
            Yoga.YGNodeStyleSetMaxWidth(bNode, maxTop.x);
            Yoga.YGNodeStyleSetMaxHeight(bNode, maxTop.y);

            Vector2f minTop = getMinSize(component);
            Yoga.YGNodeStyleSetMinWidth(bNode, minTop.x);
            Yoga.YGNodeStyleSetMinHeight(bNode, minTop.y);

            Yoga.YGNodeStyleSetFlexGrow(bNode, 1);
            Yoga.YGNodeStyleSetFlexShrink(bNode, 1);

            Vector2f preferredSize = component.getStyle().getPreferredSize();
            if (preferredSize != null) {
                Yoga.YGNodeStyleSetWidth(bNode, preferredSize.x);
                Yoga.YGNodeStyleSetHeight(bNode, preferredSize.y);
            }
        }
    }

    /**
     * Used to free native memory.
     *
     * @param baseNode base node.
     * @param mNode mid row node.
     * @param tNode top node.
     * @param lNode left node.
     * @param cNode center node.
     * @param rNode right node.
     * @param bNode bottom node.
     */
    private void freeMem(long baseNode, long mNode, long tNode, long lNode, long cNode, long rNode, long bNode) {
        Yoga.YGNodeFree(baseNode);
        Yoga.YGNodeFree(mNode);
        Yoga.YGNodeFree(tNode);
        Yoga.YGNodeFree(lNode);
        Yoga.YGNodeFree(cNode);
        Yoga.YGNodeFree(rNode);
        Yoga.YGNodeFree(bNode);
    }

    /**
     * Used to prepare nodes for calculations.
     *
     * @param rootNode root node.
     * @param mNode mid row node.
     * @param tNode top node.
     * @param lNode left node.
     * @param cNode center node.
     * @param rNode right node.
     * @param bNode bottom node.
     */
    private void prepareNodesStructure(long rootNode, long mNode, long tNode, long lNode, long cNode, long rNode, long bNode) {
        int rowIndex = 0;
        int colIndex = 0;
        if (topComponent != null) {
            Yoga.YGNodeInsertChild(rootNode, tNode, rowIndex++);
        }
        if (leftComponent != null || centerComponent != null || rightComponent != null) {
            Yoga.YGNodeInsertChild(rootNode, mNode, rowIndex++);
        }
        if (bottomComponent != null) {
            Yoga.YGNodeInsertChild(rootNode, bNode, rowIndex++);
        }

        if (leftComponent != null) {
            Yoga.YGNodeInsertChild(mNode, lNode, colIndex++);
        }
        if (centerComponent != null) {
            Yoga.YGNodeInsertChild(mNode, cNode, colIndex++);
        }
        if (rightComponent != null) {
            Yoga.YGNodeInsertChild(mNode, rNode, colIndex++);
        }
    }


    /**
     * Used to get minimum size for component.
     *
     * @param component component to get minimum size.
     *
     * @return minimum size of component.
     */
    private Vector2f getMinSize(Component component) {
        Vector2f vector2f = null;
        if (component != null) {
            vector2f = component.getStyle().getMinimumSize();
        }
        if (vector2f == null) {
            vector2f = new Vector2f(0, 0);
        }
        return vector2f;
    }

    /**
     * Used to get maximum size for component.
     *
     * @param component component to get maximum size.
     *
     * @return maximum size of component.
     */
    private Vector2f getMaxSize(Component component) {
        Vector2f vector2f = null;
        if (component != null) {
            vector2f = component.getStyle().getMaximumSize();
        }
        if (vector2f == null) {
            vector2f = new Vector2f(Float.MAX_VALUE);
        }
        return vector2f;
    }

    /**
     * Used to set size and position of component.
     *
     * @param component component to fill.
     * @param x x pos.
     * @param y y pos.
     * @param w width.
     * @param h height.
     */
    private void setSizeAndPos(Component component, float x, float y, float w, float h) {
        if (component != null) {
            component.getSize().set(w, h);
            component.getPosition().set(x, y);
        }
    }

}
