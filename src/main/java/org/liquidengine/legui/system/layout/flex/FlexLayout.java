package org.liquidengine.legui.system.layout.flex;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.event.component.ChangePositionEvent;
import org.liquidengine.legui.component.event.component.ChangeSizeEvent;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.Style.PositionType;
import org.liquidengine.legui.style.flex.FlexStyle;
import org.liquidengine.legui.style.length.Length;
import org.liquidengine.legui.style.length.LengthType;
import org.liquidengine.legui.style.length.Unit;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.layout.Layout;
import org.lwjgl.util.yoga.Yoga;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.liquidengine.legui.system.layout.flex.FlexUtils.*;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class FlexLayout implements Layout {


    public static final float THRESHOLD = 0.0001f;

    /**
     * Used to lay out child components for parent component.
     *
     * @param parent component to lay out.
     */
    @Override
    public void layout(Component parent) {
        layout(parent, null, null);
    }

    /**
     * Used to lay out child components for parent component.
     *
     * @param parent component to lay out.
     * @param frame component frame (for event generation).
     * @param context context (used for event generation).
     */
    @Override
    public void layout(Component parent, Frame frame, Context context) {
        // initialize
        long rootNode = Yoga.YGNodeNew();
        prepareParentNode(parent, rootNode);
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

            Vector2f newPos = new Vector2f(Yoga.YGNodeLayoutGetLeft(yogaNode), Yoga.YGNodeLayoutGetTop(yogaNode));
            Vector2f oldPos = childComponent.getPosition();
            childComponent.setPosition(newPos);

            Vector2f newSize = new Vector2f(Yoga.YGNodeLayoutGetWidth(yogaNode), Yoga.YGNodeLayoutGetHeight(yogaNode));
            Vector2f oldSize = childComponent.getSize();
            childComponent.setSize(newSize);

            if (frame != null && context != null) {
                if (!oldPos.equals(newPos, THRESHOLD)) {
                    EventProcessorProvider.getInstance().pushEvent(new ChangePositionEvent(childComponent, context, frame, oldPos, newPos));
                }
                if (!oldSize.equals(newSize, THRESHOLD)) {
                    EventProcessorProvider.getInstance().pushEvent(new ChangeSizeEvent(childComponent, context, frame, oldSize, newSize));
                }
            }
        }

        // free mem
        for (Long childNode : childNodes) {
            Yoga.YGNodeFree(childNode);
        }

        Yoga.YGNodeFree(rootNode);
    }

    private void prepareParentNode(Component parent, long rootNode) {
        prepareNode(parent, rootNode);
        Yoga.YGNodeStyleSetWidth(rootNode, parent.getSize().x);
        Yoga.YGNodeStyleSetHeight(rootNode, parent.getSize().y);
    }

    /**
     * Used to prepare root node.
     *
     * @param component parent component associated to root node.
     * @param node      root yoga node.
     */
    private void prepareNode(Component component, long node) {
        Style style = component.getStyle();
        FlexStyle flexStyle = style.getFlexStyle();
        setFlexDirection(node, flexStyle.getFlexDirection());
        setJustifyContent(node, flexStyle.getJustifyContent(), component);
        setAlignItems(node, flexStyle.getAlignItems(), component);
        setAlignSelf(node, flexStyle.getAlignSelf(), component);

        setMinWidth(node, style);
        setMinHeight(node, style);

        setMaxWidth(node, style);
        setMaxHeight(node, style);

        setWidth(node, style);
        setHeight(node, style);

        setPosition(node, style.getTop(), Yoga.YGEdgeTop);
        setPosition(node, style.getBottom(), Yoga.YGEdgeBottom);
        setPosition(node, style.getRight(), Yoga.YGEdgeRight);
        setPosition(node, style.getLeft(), Yoga.YGEdgeLeft);

        Yoga.YGNodeStyleSetFlexBasis(node, flexStyle.getFlexBasis());

        setPadding(node, style);
        setMargin(node, style);

        Yoga.YGNodeStyleSetPositionType(node, style.getPosition() == PositionType.RELATIVE ? Yoga.YGPositionTypeRelative : Yoga.YGPositionTypeAbsolute);

        Yoga.YGNodeStyleSetFlexGrow(node, flexStyle.getFlexGrow());
        Yoga.YGNodeStyleSetFlexShrink(node, flexStyle.getFlexShrink());
    }

    private void setPosition(long node, Length distance, int edge) {
        if (distance != null) {
            if (LengthType.PIXEL.equals(distance.type())) {
                Yoga.YGNodeStyleSetPosition(node, edge, LengthType.PIXEL.type().cast(distance.get()));
            } else if (LengthType.PERCENT.equals(distance.type())) {
                Yoga.YGNodeStyleSetPositionPercent(node, edge, LengthType.PERCENT.type().cast(distance.get()));
            }
        }
    }

    private void setHeight(long node, Style style) {
        Unit height = style.getHeight();
        if (height != null) {
            if (height.isAuto()) {
                Yoga.YGNodeStyleSetHeightAuto(node);
            } else {
                Length length = height.asLength();
                if (LengthType.PIXEL.equals(length.type())) {
                    Yoga.YGNodeStyleSetHeight(node, LengthType.PIXEL.type().cast(length.get()));
                } else if (LengthType.PERCENT.equals(length.type())) {
                    Yoga.YGNodeStyleSetHeightPercent(node, LengthType.PERCENT.type().cast(length.get()));
                }
            }
        }
    }

    private void setWidth(long node, Style style) {
        Unit width = style.getWidth();
        if (width != null) {
            if (width.isAuto()) {
                Yoga.YGNodeStyleSetWidthAuto(node);
            } else {
                Length length = width.asLength();
                if (LengthType.PIXEL.equals(length.type())) {
                    Yoga.YGNodeStyleSetWidth(node, LengthType.PIXEL.type().cast(length.get()));
                } else if (LengthType.PERCENT.equals(length.type())) {
                    Yoga.YGNodeStyleSetWidthPercent(node, LengthType.PERCENT.type().cast(length.get()));
                }
            }
        }
    }

    private void setMaxHeight(long node, Style style) {
        Length maxHeight = style.getMaxHeight();
        if (maxHeight != null) {
            if (LengthType.PIXEL.equals(maxHeight.type())) {
                Yoga.YGNodeStyleSetMaxHeight(node, LengthType.PIXEL.type().cast(maxHeight.get()));
            } else if (LengthType.PERCENT.equals(maxHeight.type())) {
                Yoga.YGNodeStyleSetMaxHeightPercent(node, LengthType.PERCENT.type().cast(maxHeight.get()));
            }
        }
    }

    private void setMaxWidth(long node, Style style) {
        Length maxWidth = style.getMaxWidth();
        if (maxWidth != null) {
            if (LengthType.PIXEL.equals(maxWidth.type())) {
                Yoga.YGNodeStyleSetMaxWidth(node, LengthType.PIXEL.type().cast(maxWidth.get()));
            } else if (LengthType.PERCENT.equals(maxWidth.type())) {
                Yoga.YGNodeStyleSetMaxWidthPercent(node, LengthType.PERCENT.type().cast(maxWidth.get()));
            }
        }
    }

    private void setMinHeight(long node, Style style) {
        Length minHeight = style.getMinHeight();
        if (minHeight != null) {
            if (LengthType.PIXEL.equals(minHeight.type())) {
                Yoga.YGNodeStyleSetMinHeight(node, LengthType.PIXEL.type().cast(minHeight.get()));
            } else if (LengthType.PERCENT.equals(minHeight.type())) {
                Yoga.YGNodeStyleSetMinHeightPercent(node, LengthType.PERCENT.type().cast(minHeight.get()));
            }
        }
    }

    private void setMinWidth(long node, Style style) {
        Length minWidth = style.getMinWidth();
        if (minWidth != null) {
            if (LengthType.PIXEL.equals(minWidth.type())) {
                Yoga.YGNodeStyleSetMinWidth(node, LengthType.PIXEL.type().cast(minWidth.get()));
            } else if (LengthType.PERCENT.equals(minWidth.type())) {
                Yoga.YGNodeStyleSetMinWidthPercent(node, LengthType.PERCENT.type().cast(minWidth.get()));
            }
        }
    }


}
