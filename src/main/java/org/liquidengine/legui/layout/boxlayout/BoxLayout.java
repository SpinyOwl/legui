package org.liquidengine.legui.layout.boxlayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.layout.Layout;
import org.liquidengine.legui.layout.LayoutConstraint;
import org.lwjgl.util.yoga.Yoga;

/**
 * Created by ShchAlexander on 14.01.2018.
 */
public class BoxLayout implements Layout {

    /**
     * Box layout orientation.
     */
    private Orientation orientation;

    /**
     * Default constructor. Initialize orientation with {@link Orientation#HORIZONTAL}.
     */
    public BoxLayout() {
        this.orientation = Orientation.HORIZONTAL;
    }

    /**
     * Box layout constructor with orientation attribute.
     *
     * @param orientation orientation for box layout.
     */
    public BoxLayout(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * Returns layout orientation.
     *
     * @return layout orientation.
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Used to set layout orientation.
     *
     * @param orientation layout orientation to set.
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * Used to add component to layout.
     *
     * @param component component to add.
     * @param constraint layout constraint.
     */
    @Override
    public void addComponent(Component component, LayoutConstraint constraint) {
        // there is no specific logic on adding component to parent to layout.
    }

    /**
     * Used to remove component from layout.
     *
     * @param component component to remove.
     */
    @Override
    public void removeComponent(Component component) {
        // there is no specific logic on removing component from parent to layout.
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

        prepareRootNode(parent, rootNode);

        List<Long> yogaNodes = new ArrayList<>();
        List<Component> childComponents = parent.getChildComponents().stream().filter(Component::isVisible).collect(Collectors.toList());
        int index = 0;
        for (Component childComponent : childComponents) {
            long node = Yoga.YGNodeNew();
            prepareChildNode(childComponent, node);
            yogaNodes.add(node);
            Yoga.YGNodeInsertChild(rootNode, node, index++);
        }

        Yoga.nYGNodeCalculateLayout(rootNode, parent.getSize().x, parent.getSize().y, Yoga.YGDirectionLTR);

        for (int i = 0; i < childComponents.size(); i++) {
            Component childComponent = childComponents.get(i);
            Long yogaNode = yogaNodes.get(i);

            childComponent.setPosition(Yoga.YGNodeLayoutGetLeft(yogaNode), Yoga.YGNodeLayoutGetTop(yogaNode));
            childComponent.setSize(Yoga.YGNodeLayoutGetWidth(yogaNode), Yoga.YGNodeLayoutGetHeight(yogaNode));
        }

        // free native memory
        for (Long yogaNode : yogaNodes) {
            Yoga.YGNodeFree(yogaNode);
        }
        Yoga.YGNodeFree(rootNode);

    }

    /**
     * Used to prepare child yoga node.
     *
     * @param childComponent child gui component associated with child yoga node.
     * @param node child yoga node.
     */
    private void prepareChildNode(Component childComponent, long node) {
        Vector2f minimumSize = childComponent.getStyle().getMinimumSize();
        if (minimumSize != null) {
            Yoga.YGNodeStyleSetMinWidth(node, minimumSize.x);
            Yoga.YGNodeStyleSetMinHeight(node, minimumSize.y);
        }

        Vector2f maximumSize = childComponent.getStyle().getMaximumSize();
        if (maximumSize != null) {
            Yoga.YGNodeStyleSetMaxWidth(node, maximumSize.x);
            Yoga.YGNodeStyleSetMaxHeight(node, maximumSize.y);
        }

        Vector2f preferredSize = childComponent.getStyle().getSize();
        if (preferredSize != null) {
            Yoga.YGNodeStyleSetWidth(node, preferredSize.x);
            Yoga.YGNodeStyleSetHeight(node, preferredSize.y);
        }

        Yoga.YGNodeStyleSetFlexGrow(node, 1);
        Yoga.YGNodeStyleSetFlexShrink(node, 1);

        Vector4f margin = childComponent.getStyle().getMargin();
        if (margin != null) {
            Yoga.YGNodeStyleSetMargin(node, Yoga.YGEdgeLeft, margin.x);
            Yoga.YGNodeStyleSetMargin(node, Yoga.YGEdgeTop, margin.y);
            Yoga.YGNodeStyleSetMargin(node, Yoga.YGEdgeRight, margin.z);
            Yoga.YGNodeStyleSetMargin(node, Yoga.YGEdgeBottom, margin.w);
        }
    }

    /**
     * Used to prepare root yoga node.
     *
     * @param parent parent gui component associated with root yoga node.
     * @param rootNode root yoga node.
     */
    private void prepareRootNode(Component parent, long rootNode) {
        if (Orientation.HORIZONTAL == orientation) {
            Yoga.YGNodeStyleSetFlexDirection(rootNode, Yoga.YGFlexDirectionRow);
        } else {
            Yoga.YGNodeStyleSetFlexDirection(rootNode, Yoga.YGFlexDirectionColumn);
        }
        Yoga.YGNodeStyleSetAlignItems(rootNode, Yoga.YGAlignStretch);
        Yoga.YGNodeStyleSetJustifyContent(rootNode, Yoga.YGJustifySpaceBetween);

        Vector2f minimumSize = parent.getStyle().getMinimumSize();
        Yoga.YGNodeStyleSetMinWidth(rootNode, minimumSize == null ? 0 : minimumSize.x);
        Yoga.YGNodeStyleSetMinHeight(rootNode, minimumSize == null ? 0 : minimumSize.y);

        Vector2f maximumSize = parent.getStyle().getMaximumSize();
        Yoga.YGNodeStyleSetMaxWidth(rootNode, maximumSize == null ? Float.MAX_VALUE : maximumSize.x);
        Yoga.YGNodeStyleSetMaxHeight(rootNode, maximumSize == null ? Float.MAX_VALUE : maximumSize.y);

        Vector2f preferredSize = parent.getStyle().getSize();
        Vector2f size = parent.getSize();
        Yoga.YGNodeStyleSetWidth(rootNode, preferredSize == null ? size.x : preferredSize.x);
        Yoga.YGNodeStyleSetHeight(rootNode, preferredSize == null ? size.y : preferredSize.y);

        Vector4f padding = parent.getStyle().getPadding();
        if (padding != null) {
            Yoga.YGNodeStyleSetPadding(rootNode, Yoga.YGEdgeLeft, padding.x);
            Yoga.YGNodeStyleSetPadding(rootNode, Yoga.YGEdgeTop, padding.y);
            Yoga.YGNodeStyleSetPadding(rootNode, Yoga.YGEdgeRight, padding.z);
            Yoga.YGNodeStyleSetPadding(rootNode, Yoga.YGEdgeBottom, padding.w);
        }
    }
}
