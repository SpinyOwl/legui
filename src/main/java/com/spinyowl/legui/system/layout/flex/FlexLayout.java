package com.spinyowl.legui.system.layout.flex;

import static com.spinyowl.legui.system.layout.flex.FlexUtils.setAlignItems;
import static com.spinyowl.legui.system.layout.flex.FlexUtils.setAlignSelf;
import static com.spinyowl.legui.system.layout.flex.FlexUtils.setFlexDirection;
import static com.spinyowl.legui.system.layout.flex.FlexUtils.setFlexWrap;
import static com.spinyowl.legui.system.layout.flex.FlexUtils.setJustifyContent;
import static com.spinyowl.legui.system.layout.flex.FlexUtils.setMargin;
import static com.spinyowl.legui.system.layout.flex.FlexUtils.setPadding;
import static org.lwjgl.util.yoga.Yoga.YGConfigFree;
import static org.lwjgl.util.yoga.Yoga.YGConfigNew;
import static org.lwjgl.util.yoga.Yoga.YGDirectionLTR;
import static org.lwjgl.util.yoga.Yoga.YGDisplayFlex;
import static org.lwjgl.util.yoga.Yoga.YGEdgeBottom;
import static org.lwjgl.util.yoga.Yoga.YGEdgeLeft;
import static org.lwjgl.util.yoga.Yoga.YGEdgeRight;
import static org.lwjgl.util.yoga.Yoga.YGEdgeTop;
import static org.lwjgl.util.yoga.Yoga.YGNodeCalculateLayout;
import static org.lwjgl.util.yoga.Yoga.YGNodeFree;
import static org.lwjgl.util.yoga.Yoga.YGNodeInsertChild;
import static org.lwjgl.util.yoga.Yoga.YGNodeLayoutGetHeight;
import static org.lwjgl.util.yoga.Yoga.YGNodeLayoutGetLeft;
import static org.lwjgl.util.yoga.Yoga.YGNodeLayoutGetTop;
import static org.lwjgl.util.yoga.Yoga.YGNodeLayoutGetWidth;
import static org.lwjgl.util.yoga.Yoga.YGNodeNewWithConfig;
import static org.lwjgl.util.yoga.Yoga.YGNodeStyleSetDisplay;
import static org.lwjgl.util.yoga.Yoga.YGNodeStyleSetFlexBasis;
import static org.lwjgl.util.yoga.Yoga.YGNodeStyleSetFlexGrow;
import static org.lwjgl.util.yoga.Yoga.YGNodeStyleSetFlexShrink;
import static org.lwjgl.util.yoga.Yoga.YGNodeStyleSetHeight;
import static org.lwjgl.util.yoga.Yoga.YGNodeStyleSetHeightAuto;
import static org.lwjgl.util.yoga.Yoga.YGNodeStyleSetHeightPercent;
import static org.lwjgl.util.yoga.Yoga.YGNodeStyleSetMaxHeight;
import static org.lwjgl.util.yoga.Yoga.YGNodeStyleSetMaxHeightPercent;
import static org.lwjgl.util.yoga.Yoga.YGNodeStyleSetMaxWidth;
import static org.lwjgl.util.yoga.Yoga.YGNodeStyleSetMaxWidthPercent;
import static org.lwjgl.util.yoga.Yoga.YGNodeStyleSetMinHeight;
import static org.lwjgl.util.yoga.Yoga.YGNodeStyleSetMinHeightPercent;
import static org.lwjgl.util.yoga.Yoga.YGNodeStyleSetMinWidth;
import static org.lwjgl.util.yoga.Yoga.YGNodeStyleSetMinWidthPercent;
import static org.lwjgl.util.yoga.Yoga.YGNodeStyleSetPosition;
import static org.lwjgl.util.yoga.Yoga.YGNodeStyleSetPositionPercent;
import static org.lwjgl.util.yoga.Yoga.YGNodeStyleSetPositionType;
import static org.lwjgl.util.yoga.Yoga.YGNodeStyleSetWidth;
import static org.lwjgl.util.yoga.Yoga.YGNodeStyleSetWidthAuto;
import static org.lwjgl.util.yoga.Yoga.YGNodeStyleSetWidthPercent;
import static org.lwjgl.util.yoga.Yoga.YGPositionTypeAbsolute;
import static org.lwjgl.util.yoga.Yoga.YGPositionTypeRelative;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.event.component.ChangePositionEvent;
import com.spinyowl.legui.component.event.component.ChangeSizeEvent;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.style.Style;
import com.spinyowl.legui.style.Style.PositionType;
import com.spinyowl.legui.style.flex.FlexStyle;
import com.spinyowl.legui.style.length.Length;
import com.spinyowl.legui.style.length.LengthType;
import com.spinyowl.legui.style.length.Unit;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.layout.Layout;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.joml.Vector2f;

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
//    if (parent.isEmpty()) {
//      return;
//    }
    // initialize
    long yogaConfig = YGConfigNew();
    long rootNode = YGNodeNewWithConfig(yogaConfig);

    prepareParentNode(parent, rootNode);
    YGNodeStyleSetDisplay(rootNode, YGDisplayFlex);

    List<Long> childNodes = new ArrayList<>();
    List<Component> components =
        parent.getChildComponents().stream()
            .filter(Component::isVisible)
            .collect(Collectors.toList());
    for (Component component : components) {
      long childNode = YGNodeNewWithConfig(yogaConfig);
      prepareNode(component, childNode);
      YGNodeInsertChild(rootNode, childNode, childNodes.size());
      childNodes.add(childNode);
    }

    // calculate
    YGNodeCalculateLayout(rootNode, parent.getSize().x, parent.getSize().y, YGDirectionLTR);

    // apply to components
    for (int i = 0; i < components.size(); i++) {
      Component childComponent = components.get(i);
      Long yogaNode = childNodes.get(i);

      updateComponent(frame, context, childComponent, yogaNode);
    }

    // free mem
    for (Long childNode : childNodes) {
      YGNodeFree(childNode);
    }

    YGNodeFree(rootNode);
    YGConfigFree(yogaConfig);
  }

  private void updateComponent(Frame frame, Context context, Component component, Long yogaNode) {
    Vector2f newPos = new Vector2f(YGNodeLayoutGetLeft(yogaNode), YGNodeLayoutGetTop(yogaNode));
    Vector2f oldPos = component.getPosition();
    component.setPosition(newPos);

    Vector2f newSize =
        new Vector2f(YGNodeLayoutGetWidth(yogaNode), YGNodeLayoutGetHeight(yogaNode));
    Vector2f oldSize = component.getSize();
    component.setSize(newSize);

    if (frame != null && context != null) {
      if (!oldPos.equals(newPos, THRESHOLD)) {
        EventProcessorProvider.getInstance()
            .pushEvent(new ChangePositionEvent<>(component, context, frame, oldPos, newPos));
      }
      if (!oldSize.equals(newSize, THRESHOLD)) {
        EventProcessorProvider.getInstance()
            .pushEvent(new ChangeSizeEvent<>(component, context, frame, oldSize, newSize));
      }
    }
  }

  private void prepareParentNode(Component parent, long rootNode) {
    prepareNode(parent, rootNode);
    YGNodeStyleSetWidth(rootNode, parent.getSize().x);
    YGNodeStyleSetHeight(rootNode, parent.getSize().y);
  }

  /**
   * Used to prepare root node.
   *
   * @param component parent component associated to root node.
   * @param node root yoga node.
   */
  private void prepareNode(Component component, long node) {
    Style style = component.getStyle();
    FlexStyle flexStyle = style.getFlexStyle();
    setFlexDirection(node, flexStyle.getFlexDirection());
    setJustifyContent(node, flexStyle.getJustifyContent(), component);
    setAlignItems(node, flexStyle.getAlignItems(), component);
    setAlignSelf(node, flexStyle.getAlignSelf(), component);
    setFlexWrap(node, flexStyle.getFlexWrap());

    setMinWidth(node, style);
    setMinHeight(node, style);

    setMaxWidth(node, style);
    setMaxHeight(node, style);

    setWidth(node, style);
    setHeight(node, style);

    setPosition(node, style.getTop(), YGEdgeTop);
    setPosition(node, style.getBottom(), YGEdgeBottom);
    setPosition(node, style.getRight(), YGEdgeRight);
    setPosition(node, style.getLeft(), YGEdgeLeft);

    YGNodeStyleSetFlexBasis(node, flexStyle.getFlexBasis());

    setPadding(node, style);
    setMargin(node, style);

    YGNodeStyleSetPositionType(
        node,
        style.getPosition() == PositionType.RELATIVE
            ? YGPositionTypeRelative
            : YGPositionTypeAbsolute);

    YGNodeStyleSetFlexGrow(node, flexStyle.getFlexGrow());
    YGNodeStyleSetFlexShrink(node, flexStyle.getFlexShrink());
  }

  private void setPosition(long node, Unit distance, int edge) {
    if (distance != null && distance.isLength()) {
      Length<?> length = distance.asLength();
      if (LengthType.PIXEL.equals(length.type())) {
        YGNodeStyleSetPosition(node, edge, LengthType.PIXEL.type().cast(length.get()));
      } else if (LengthType.PERCENT.equals(length.type())) {
        YGNodeStyleSetPositionPercent(node, edge, LengthType.PERCENT.type().cast(length.get()));
      }
    }
  }

  private void setHeight(long node, Style style) {
    Unit height = style.getHeight();
    if (height != null) {
      if (height.isAuto()) {
        YGNodeStyleSetHeightAuto(node);
      } else {
        Length<?> length = height.asLength();
        if (LengthType.PIXEL.equals(length.type())) {
          YGNodeStyleSetHeight(node, LengthType.PIXEL.type().cast(length.get()));
        } else if (LengthType.PERCENT.equals(length.type())) {
          YGNodeStyleSetHeightPercent(node, LengthType.PERCENT.type().cast(length.get()));
        }
      }
    }
  }

  private void setWidth(long node, Style style) {
    Unit width = style.getWidth();
    if (width != null) {
      if (width.isAuto()) {
        YGNodeStyleSetWidthAuto(node);
      } else {
        Length<?> length = width.asLength();
        if (LengthType.PIXEL.equals(length.type())) {
          YGNodeStyleSetWidth(node, LengthType.PIXEL.type().cast(length.get()));
        } else if (LengthType.PERCENT.equals(length.type())) {
          YGNodeStyleSetWidthPercent(node, LengthType.PERCENT.type().cast(length.get()));
        }
      }
    }
  }

  private void setMaxHeight(long node, Style style) {
    Length<?> maxHeight = style.getMaxHeight();
    if (maxHeight != null) {
      if (LengthType.PIXEL.equals(maxHeight.type())) {
        YGNodeStyleSetMaxHeight(node, LengthType.PIXEL.type().cast(maxHeight.get()));
      } else if (LengthType.PERCENT.equals(maxHeight.type())) {
        YGNodeStyleSetMaxHeightPercent(node, LengthType.PERCENT.type().cast(maxHeight.get()));
      }
    }
  }

  private void setMaxWidth(long node, Style style) {
    Length<?> maxWidth = style.getMaxWidth();
    if (maxWidth != null) {
      if (LengthType.PIXEL.equals(maxWidth.type())) {
        YGNodeStyleSetMaxWidth(node, LengthType.PIXEL.type().cast(maxWidth.get()));
      } else if (LengthType.PERCENT.equals(maxWidth.type())) {
        YGNodeStyleSetMaxWidthPercent(node, LengthType.PERCENT.type().cast(maxWidth.get()));
      }
    }
  }

  private void setMinHeight(long node, Style style) {
    Length<?> minHeight = style.getMinHeight();
    if (minHeight != null) {
      if (LengthType.PIXEL.equals(minHeight.type())) {
        YGNodeStyleSetMinHeight(node, LengthType.PIXEL.type().cast(minHeight.get()));
      } else if (LengthType.PERCENT.equals(minHeight.type())) {
        YGNodeStyleSetMinHeightPercent(node, LengthType.PERCENT.type().cast(minHeight.get()));
      }
    }
  }

  private void setMinWidth(long node, Style style) {
    Length<?> minWidth = style.getMinWidth();
    if (minWidth != null) {
      if (LengthType.PIXEL.equals(minWidth.type())) {
        YGNodeStyleSetMinWidth(node, LengthType.PIXEL.type().cast(minWidth.get()));
      } else if (LengthType.PERCENT.equals(minWidth.type())) {
        YGNodeStyleSetMinWidthPercent(node, LengthType.PERCENT.type().cast(minWidth.get()));
      }
    }
  }
}
