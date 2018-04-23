package org.liquidengine.legui.system.layout.flex;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.flex.FlexStyle.AlignItems;
import org.liquidengine.legui.style.flex.FlexStyle.FlexDirection;
import org.liquidengine.legui.style.flex.FlexStyle.JustifyContent;
import org.lwjgl.util.yoga.Yoga;

/**
 * @author Aliaksandr_Shcherbin.
 */
final class FlexUtils {

    private FlexUtils() {
    }

    //@formatter:off

    public static void setJustifyContent(long node, JustifyContent justifyContent, Component component) {
        JustifyContent toUse = justifyContent;
        for (Component lp = component; toUse == JustifyContent.INHERIT; lp = component.getParent()) {
            if (lp != null) {
                toUse = lp.getStyle().getFlexStyle().getJustifyContent();
            } else {
                toUse = JustifyContent.FLEX_START;
            }
        }
        switch (toUse) {
            case INITIAL:
            case FLEX_START:
                Yoga.YGNodeStyleSetJustifyContent(node, Yoga.YGJustifyFlexStart);
                break;
            case CENTER:
                Yoga.YGNodeStyleSetJustifyContent(node, Yoga.YGJustifyCenter);
                break;
            case FLEX_END:
                Yoga.YGNodeStyleSetJustifyContent(node, Yoga.YGJustifyFlexEnd);
                break;
            case SPACE_AROUND:
                Yoga.YGNodeStyleSetJustifyContent(node, Yoga.YGJustifySpaceAround);
                break;
            case SPACE_BETWEEN:
                Yoga.YGNodeStyleSetJustifyContent(node, Yoga.YGJustifySpaceBetween);
                break;
            case SPACE_EVENLY:
                Yoga.YGNodeStyleSetJustifyContent(node, Yoga.YGJustifySpaceEvenly);
                break;
        }
    }

    public static void setFlexDirection(long rootNode, FlexDirection flexDirection) {
        switch (flexDirection) {
            case ROW:
                Yoga.YGNodeStyleSetFlexDirection(rootNode, Yoga.YGFlexDirectionRow);
                break;
            case COLUMN:
                Yoga.YGNodeStyleSetFlexDirection(rootNode, Yoga.YGFlexDirectionColumn);
                break;
            case ROW_REVERSE:
                Yoga.YGNodeStyleSetFlexDirection(rootNode, Yoga.YGFlexDirectionRowReverse);
                break;
            case COLUMN_REVERSE:
                Yoga.YGNodeStyleSetFlexDirection(rootNode, Yoga.YGFlexDirectionColumnReverse);
                break;
        }
    }

    public static void setAlignItems(long node, AlignItems alignItems, Component component) {
        AlignItems toUse = alignItems;
        for (Component lp = component; toUse == AlignItems.INHERIT; lp = component.getParent()) {
            if (lp != null) {
                toUse = lp.getStyle().getFlexStyle().getAlignItems();
            } else {
                toUse = AlignItems.STRETCH;
            }
        }
        switch (toUse) {
            case FLEX_END:
                Yoga.YGNodeStyleSetAlignItems(node, Yoga.YGAlignFlexEnd);
                break;
            case CENTER:
                Yoga.YGNodeStyleSetAlignItems(node, Yoga.YGAlignCenter);
                break;
            case FLEX_START:
                Yoga.YGNodeStyleSetAlignItems(node, Yoga.YGAlignFlexStart);
                break;
            case STRETCH:
                Yoga.YGNodeStyleSetAlignItems(node, Yoga.YGAlignStretch);
                break;
            case BASELINE:
                Yoga.YGNodeStyleSetAlignItems(node, Yoga.YGAlignBaseline);
                break;
            case AUTO:
                Yoga.YGNodeStyleSetAlignItems(node, Yoga.YGAlignAuto);
                break;
        }
    }

    public static void setPadding(long node, Style style) {
        Float paddingLeft = style.getPaddingLeft();
        if (paddingLeft != null) {
            Yoga.YGNodeStyleSetPadding(node, Yoga.YGEdgeLeft, paddingLeft);
        }
        Float paddingTop = style.getPaddingTop();
        if (paddingTop != null) {
            Yoga.YGNodeStyleSetPadding(node, Yoga.YGEdgeTop, paddingTop);
        }
        Float paddingRight = style.getPaddingRight();
        if (paddingRight != null) {
            Yoga.YGNodeStyleSetPadding(node, Yoga.YGEdgeRight, paddingRight);
        }
        Float paddingBottom = style.getPaddingBottom();
        if (paddingBottom != null) {
            Yoga.YGNodeStyleSetPadding(node, Yoga.YGEdgeBottom, paddingBottom);
        }
    }

    public static void setMargin(long node, Style style) {
        Float paddingLeft = style.getMarginLeft();
        if (paddingLeft != null) {
            Yoga.YGNodeStyleSetMargin(node, Yoga.YGEdgeLeft, paddingLeft);
        }
        Float paddingTop = style.getMarginTop();
        if (paddingTop != null) {
            Yoga.YGNodeStyleSetMargin(node, Yoga.YGEdgeTop, paddingTop);
        }
        Float paddingRight = style.getMarginRight();
        if (paddingRight != null) {
            Yoga.YGNodeStyleSetMargin(node, Yoga.YGEdgeRight, paddingRight);
        }
        Float paddingBottom = style.getMarginBottom();
        if (paddingBottom != null) {
            Yoga.YGNodeStyleSetMargin(node, Yoga.YGEdgeBottom, paddingBottom);
        }
    }

}
