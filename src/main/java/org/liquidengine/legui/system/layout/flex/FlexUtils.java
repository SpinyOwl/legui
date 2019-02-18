package org.liquidengine.legui.system.layout.flex;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.flex.FlexStyle.AlignItems;
import org.liquidengine.legui.style.flex.FlexStyle.AlignSelf;
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
        for (Component lp = component; toUse == JustifyContent.INHERIT; lp = lp.getParent()) {
            if (lp != null) {
                toUse = lp.getStyle().getFlexStyle().getJustifyContent();
            } else {
                toUse = JustifyContent.FLEX_START;
                break;
            }
        }
        if (toUse == JustifyContent.INITIAL || toUse == JustifyContent.FLEX_START) {
            Yoga.YGNodeStyleSetJustifyContent(node, Yoga.YGJustifyFlexStart);
        } else if (toUse == JustifyContent.CENTER) {
            Yoga.YGNodeStyleSetJustifyContent(node, Yoga.YGJustifyCenter);
        } else if (toUse == JustifyContent.FLEX_END) {
            Yoga.YGNodeStyleSetJustifyContent(node, Yoga.YGJustifyFlexEnd);
        } else if (toUse == JustifyContent.SPACE_AROUND) {
            Yoga.YGNodeStyleSetJustifyContent(node, Yoga.YGJustifySpaceAround);
        } else if (toUse == JustifyContent.SPACE_BETWEEN) {
            Yoga.YGNodeStyleSetJustifyContent(node, Yoga.YGJustifySpaceBetween);
        } else if (toUse == JustifyContent.SPACE_EVENLY) {
            Yoga.YGNodeStyleSetJustifyContent(node, Yoga.YGJustifySpaceEvenly);
        }
    }

    public static void setFlexDirection(long rootNode, FlexDirection flexDirection) {
        if (flexDirection == FlexDirection.ROW) {
            Yoga.YGNodeStyleSetFlexDirection(rootNode, Yoga.YGFlexDirectionRow);
        } else if (flexDirection == FlexDirection.COLUMN) {
            Yoga.YGNodeStyleSetFlexDirection(rootNode, Yoga.YGFlexDirectionColumn);
        } else if (flexDirection == FlexDirection.ROW_REVERSE) {
            Yoga.YGNodeStyleSetFlexDirection(rootNode, Yoga.YGFlexDirectionRowReverse);
        } else if (flexDirection == FlexDirection.COLUMN_REVERSE) {
            Yoga.YGNodeStyleSetFlexDirection(rootNode, Yoga.YGFlexDirectionColumnReverse);
        }
    }

    public static void setAlignItems(long node, AlignItems alignItems, Component component) {
        AlignItems toUse = alignItems;
        for (Component lp = component; toUse == AlignItems.INHERIT; lp = lp.getParent()) {
            if (lp != null) {
                toUse = lp.getStyle().getFlexStyle().getAlignItems();
            } else {
                toUse = AlignItems.STRETCH;
                break;
            }
        }
        if (toUse == AlignItems.FLEX_END) {
            Yoga.YGNodeStyleSetAlignItems(node, Yoga.YGAlignFlexEnd);
        } else if (toUse == AlignItems.CENTER) {
            Yoga.YGNodeStyleSetAlignItems(node, Yoga.YGAlignCenter);
        } else if (toUse == AlignItems.FLEX_START) {
            Yoga.YGNodeStyleSetAlignItems(node, Yoga.YGAlignFlexStart);
        } else if (toUse == AlignItems.STRETCH) {
            Yoga.YGNodeStyleSetAlignItems(node, Yoga.YGAlignStretch);
        } else if (toUse == AlignItems.BASELINE) {
            Yoga.YGNodeStyleSetAlignItems(node, Yoga.YGAlignBaseline);
        } else if (toUse == AlignItems.AUTO) {
            Yoga.YGNodeStyleSetAlignItems(node, Yoga.YGAlignAuto);
        }
    }

    public static void setAlignSelf(long node, AlignSelf alignItems, Component component) {
        AlignSelf toUse = alignItems;
        for (Component lp = component; toUse == AlignSelf.INHERIT; lp = lp.getParent()) {
            if (lp != null) {
                toUse = lp.getStyle().getFlexStyle().getAlignSelf();
            } else {
                toUse = AlignSelf.STRETCH;
                break;
            }
        }
        if (toUse == AlignSelf.FLEX_END) {
            Yoga.YGNodeStyleSetAlignSelf(node, Yoga.YGAlignFlexEnd);
        } else if (toUse == AlignSelf.CENTER) {
            Yoga.YGNodeStyleSetAlignSelf(node, Yoga.YGAlignCenter);
        } else if (toUse == AlignSelf.FLEX_START) {
            Yoga.YGNodeStyleSetAlignSelf(node, Yoga.YGAlignFlexStart);
        } else if (toUse == AlignSelf.STRETCH) {
            Yoga.YGNodeStyleSetAlignSelf(node, Yoga.YGAlignStretch);
        } else if (toUse == AlignSelf.BASELINE) {
            Yoga.YGNodeStyleSetAlignSelf(node, Yoga.YGAlignBaseline);
        } else if (toUse == AlignSelf.AUTO) {
            Yoga.YGNodeStyleSetAlignSelf(node, Yoga.YGAlignAuto);
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
        Float marginLeft = style.getMarginLeft();
        if (marginLeft != null) {
            Yoga.YGNodeStyleSetMargin(node, Yoga.YGEdgeLeft, marginLeft);
        }
        Float marginTop = style.getMarginTop();
        if (marginTop != null) {
            Yoga.YGNodeStyleSetMargin(node, Yoga.YGEdgeTop, marginTop);
        }
        Float marginRight = style.getMarginRight();
        if (marginRight != null) {
            Yoga.YGNodeStyleSetMargin(node, Yoga.YGEdgeRight, marginRight);
        }
        Float marginBottom = style.getMarginBottom();
        if (marginBottom != null) {
            Yoga.YGNodeStyleSetMargin(node, Yoga.YGEdgeBottom, marginBottom);
        }
    }

}
