package com.spinyowl.legui.demo;

import static com.spinyowl.legui.style.color.ColorUtil.half;
import static com.spinyowl.legui.style.color.ColorUtil.randomColor;

import com.spinyowl.legui.component.Button;
import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.Widget;
import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.style.Style;
import com.spinyowl.legui.style.color.ColorConstants;
import com.spinyowl.legui.style.flex.FlexStyle;

public class WidgetTreeExample extends Demo {

  public static final int WIDTH = 400;
  public static final int HEIGHT = 600;
  public static final float MARGIN = 6f;

  private boolean autoCloseWidgets = true;

  public WidgetTreeExample() {
    super(WIDTH, HEIGHT, "Widget tree demo");
  }

  public static void main(String[] args) {
    new WidgetTreeExample().run();
  }

  @Override
  protected void createGuiElements(Frame frame) {
    Button button = new Button("Toggle autoclose", 10, 10, WIDTH - 20, 20);
    button.getListenerMap().addListener(MouseClickEvent.class, e -> {
      if (e.getAction() == MouseClickEvent.MouseClickAction.CLICK) {
        autoCloseWidgets = !autoCloseWidgets;
      }
    });
    frame.getContainer().add(button);
    Widget root = createFlexWidget("Root");
    root.setPosition(10, 40);
    root.setSize(WIDTH - 20, HEIGHT - 50);
    root.setCloseable(false);

    Widget rootChild1 = createFlexChildWidget("C1", root);

    Widget c1C1 = createFlexChildWidget("c1 c1", root);
    c1C1.getContainer().add(createChildWidget("c1 c1 c1", root));
    c1C1.getContainer().add(createChildWidget("c1 c1 c2", root));
    c1C1.getContainer().add(createChildWidget("c1 c1 c3", root, true));
    rootChild1.getContainer().add(c1C1);
    rootChild1.getContainer().add(createChildWidget("c1 c2", root));
    rootChild1.getContainer().add(createChildWidget("c1 c3", root, true));

    Widget rootChild2 = createFlexChildWidget("C2", root, true);

    rootChild2.getContainer().add(createChildWidget("c2 c1", root));
    rootChild2.getContainer().add(createChildWidget("c2 c2", root));
    rootChild2.getContainer().add(createChildWidget("c2 c3", root, true));

    root.getContainer().add(rootChild1);
    root.getContainer().add(rootChild2);
    frame.getContainer().add(root);

  }

  private Widget createFlexWidget(String name) {
    Widget widget = new Widget(name);
    widget.getStyle().getBackground().setColor(ColorConstants.transparent());
    widget.getContainer().getStyle().setDisplay(Style.DisplayType.FLEX);
    widget.getContainer().getStyle().getFlexStyle()
        .setFlexDirection(FlexStyle.FlexDirection.COLUMN);
    return widget;
  }

  private Widget createFlexChildWidget(String name, Widget root) {
    return createFlexChildWidget(name, root, false);
  }

  private Widget createFlexChildWidget(String name, Widget root, boolean withBottomMargin) {
    Widget widget = createFlexWidget(name);
    setChildStylesAndCallbacks(root, widget, withBottomMargin);
    return widget;
  }

  private Widget createChildWidget(String name, Widget root) {
    return createChildWidget(name, root, false);
  }

  private Widget createChildWidget(String name, Widget root, boolean withBottomMargin) {
    Widget widget = new Widget(name);
    setChildStylesAndCallbacks(root, widget, withBottomMargin);
    return widget;
  }

  private void setChildStylesAndCallbacks(Widget root, Widget widget, boolean withBottomMargin) {
    widget.setMinimized(true);
    widget.setResizable(false);
    widget.setCloseable(false);
    widget.setResizable(false);
    widget.getStyle().getBackground().setColor(ColorConstants.transparent());
    widget.getStyle().setPosition(Style.PositionType.RELATIVE);
    widget.getStyle().getFlexStyle().setFlexGrow(1);
    widget.getStyle().setShadow(null);
    widget.getContainer().getStyle().getBackground().setColor(ColorConstants.transparent());
    widget.getStyle().getBackground().setColor(half(randomColor()));
    widget.getStyle().setMargin(MARGIN, MARGIN, withBottomMargin ? MARGIN : 0f, MARGIN);

    widget.getMinimizeButton().getListenerMap().addListener(MouseClickEvent.class, e -> {
      if (autoCloseWidgets && e.getAction() == MouseClickEvent.MouseClickAction.CLICK
          && !widget.isMinimized()) {
        // check and collapse sibling->parent->sibling
        checkAndCollapse(root, widget);
        collapseChildWidgets(widget);
      }
    });
  }

  private void checkAndCollapse(Widget root, Widget widget) {
    Component parent = widget.getParent();
    if (parent != null) {
      // collapse sibling widgets
      for (Component sibling : parent.getChildComponents()) {
        if (sibling instanceof Widget && sibling != widget) {
          Widget siblingWidget = (Widget) sibling;
          collapseChildWidgets(siblingWidget);
          siblingWidget.setMinimized(true);
        }
      }

      Component pp = parent.getParent();
      if (pp instanceof Widget) {
        Widget parentWidget = (Widget) pp;
        if (parentWidget != root) {
          checkAndCollapse(root, parentWidget);
        }
      }
    }
  }

  private void collapseChildWidgets(Widget widget) {
    for (Component child : widget.getContainer().getChildComponents()) {
      if (child instanceof Widget) {
        Widget childWidget = (Widget) child;
        collapseChildWidgets(childWidget);
        childWidget.setMinimized(true);
      }
    }
  }

}