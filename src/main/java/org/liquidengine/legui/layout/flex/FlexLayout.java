package org.liquidengine.legui.layout.flex;

import static org.liquidengine.legui.layout.flex.FlexUtils.setAlignItems;
import static org.liquidengine.legui.layout.flex.FlexUtils.setFlexDirection;
import static org.liquidengine.legui.layout.flex.FlexUtils.setJustifyContent;
import static org.liquidengine.legui.layout.flex.FlexUtils.setPadding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.layout.Layout;
import org.liquidengine.legui.layout.LayoutConstraint;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.Style.PositionType;
import org.liquidengine.legui.style.flex.FlexStyle;
import org.lwjgl.util.yoga.Yoga;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class FlexLayout implements Layout {

    /**
     * Used to add component to layout.
     *
     * @param component component to add.
     * @param constraint layout constraint.
     *
     * @throws IllegalArgumentException if provided constraint is not supported by this layout.
     */
    @Override
    public void addComponent(Component component, LayoutConstraint constraint) throws IllegalArgumentException {

    }

    /**
     * Used to remove component from layout.
     *
     * @param component component to remove.
     */
    @Override
    public void removeComponent(Component component) {

    }

    /**
     * Used to lay out child components for parent component.
     *
     * @param parent component to lay out.
     */
    @Override
    public void layout(Component parent) {
        // initialize
        long rootNode = Yoga.YGNodeNew();
        prepareNode(parent, rootNode);
        Yoga.YGNodeStyleSetDisplay(rootNode, Yoga.YGDisplayFlex);

        List<Long> childNodes = new ArrayList<>();
        List<Component> components = parent.getChildComponents().stream().filter(Component::isVisible).collect(Collectors.toList());
        for (Component component : components) {
            long childNode = Yoga.YGNodeNew();
            prepareNode(component, childNode);
            Yoga.YGNodeInsertChild(rootNode, childNode, childNodes.size());
            childNodes.add(childNode);
        }

        // calculate
        Yoga.nYGNodeCalculateLayout(rootNode, parent.getSize().x, parent.getSize().y, Yoga.YGDirectionLTR);

        // apply to components
        for (int i = 0; i < components.size(); i++) {
            Component childComponent = components.get(i);
            Long yogaNode = childNodes.get(i);

            childComponent.setPosition(Yoga.YGNodeLayoutGetLeft(yogaNode), Yoga.YGNodeLayoutGetTop(yogaNode));
            childComponent.setSize(Yoga.YGNodeLayoutGetWidth(yogaNode), Yoga.YGNodeLayoutGetHeight(yogaNode));
        }

        // free mem
        for (Long childNode : childNodes) {
            Yoga.YGNodeFree(childNode);
        }

        Yoga.YGNodeFree(rootNode);
    }

    /**
     * Used to prepare root node.
     *
     * @param parent parent component associated to root node.
     * @param node root yoga node.
     */
    private void prepareNode(Component parent, long node) {
        Style style = parent.getStyle();
        FlexStyle flexStyle = style.getFlexStyle();
        setFlexDirection(node, flexStyle.getFlexDirection());
        setJustifyContent(node, flexStyle.getJustifyContent(), parent);
        setAlignItems(node, flexStyle.getAlignItems(), parent);

        Float minWidth = style.getMinWidth();
        if (minWidth != null) {
            Yoga.YGNodeStyleSetMinWidth(node, minWidth);
        }
        Float minHeight = style.getMinHeight();
        if (minHeight != null) {
            Yoga.YGNodeStyleSetMinHeight(node, minHeight);
        }

        Float maxWidth = style.getMaxWidth();
        if (maxWidth != null) {
            Yoga.YGNodeStyleSetMaxWidth(node, maxWidth);
        }
        Float maxHeight = style.getMaxHeight();
        if (maxHeight != null) {
            Yoga.YGNodeStyleSetMaxHeight(node, maxHeight);
        }

        Float width = style.getWidth();
        if (width != null) {
            Yoga.YGNodeStyleSetWidth(node, width);
        }
        Float height = style.getHeight();
        if (height != null) {
            Yoga.YGNodeStyleSetHeight(node, height);
        }

        if (style.getTop() != null) {
            Yoga.YGNodeStyleSetPosition(node, Yoga.YGEdgeTop, style.getTop());
        }
        if (style.getBottom() != null) {
            Yoga.YGNodeStyleSetPosition(node, Yoga.YGEdgeBottom, style.getBottom());
        }
        if (style.getRight() != null) {
            Yoga.YGNodeStyleSetPosition(node, Yoga.YGEdgeRight, style.getRight());
        }
        if (style.getLeft() != null) {
            Yoga.YGNodeStyleSetPosition(node, Yoga.YGEdgeLeft, style.getLeft());
        }

        setPadding(node, style);

        Yoga.YGNodeStyleSetPositionType(node, style.getPosition() == PositionType.RELATIVE ? Yoga.YGPositionTypeRelative : Yoga.YGPositionTypeAbsolute);

        Yoga.YGNodeStyleSetFlexGrow(node, flexStyle.getFlexGrow());
        Yoga.YGNodeStyleSetFlexShrink(node, flexStyle.getFlexShrink());
    }


}
