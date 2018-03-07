package org.liquidengine.legui.layout.flex;

import static org.liquidengine.legui.layout.flex.FlexUtils.setAlignItems;
import static org.liquidengine.legui.layout.flex.FlexUtils.setFlexDirection;
import static org.liquidengine.legui.layout.flex.FlexUtils.setJustifyContent;
import static org.liquidengine.legui.layout.flex.FlexUtils.setPadding;

import org.joml.Vector2f;
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
        long rootNode = Yoga.YGNodeNew();

        Yoga.YGNodeFree(rootNode);
    }

    /**
     * Used to prepare root node.
     *
     * @param parent parent component associated to root node.
     * @param rootNode root yoga node.
     */
    private void prepareRootNode(Component parent, long rootNode) {
        Style style = parent.getStyle();
        FlexStyle flexStyle = style.getFlexStyle();
        setFlexDirection(rootNode, flexStyle.getFlexDirection());
        setJustifyContent(rootNode, flexStyle.getJustifyContent(), parent);
        setAlignItems(rootNode, flexStyle.getAlignItems(), parent);

        Vector2f maximumSize = style.getMaximumSize();
        Vector2f minimumSize = style.getMinimumSize();
        Vector2f preferredSize = style.getSize();
        Vector2f size = parent.getSize();
        Yoga.YGNodeStyleSetMinWidth(rootNode, minimumSize == null ? 0 : minimumSize.x);
        Yoga.YGNodeStyleSetMinHeight(rootNode, minimumSize == null ? 0 : minimumSize.y);
        Yoga.YGNodeStyleSetMaxWidth(rootNode, maximumSize == null ? Float.MAX_VALUE : maximumSize.x);
        Yoga.YGNodeStyleSetMaxHeight(rootNode, maximumSize == null ? Float.MAX_VALUE : maximumSize.y);
        Yoga.YGNodeStyleSetWidth(rootNode, preferredSize == null ? size.x : preferredSize.x);
        Yoga.YGNodeStyleSetHeight(rootNode, preferredSize == null ? size.y : preferredSize.y);

        Yoga.YGNodeStyleSetPositionType(rootNode, style.getPosition() == PositionType.RELATIVE ? Yoga.YGPositionTypeRelative : Yoga.YGPositionTypeAbsolute);
        setPadding(rootNode, style.getPadding());
    }


}
