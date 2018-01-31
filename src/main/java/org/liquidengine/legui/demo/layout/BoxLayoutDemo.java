package org.liquidengine.legui.demo.layout;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.Tooltip;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.demo.Demo;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.event.MouseClickEvent.MouseClickAction;
import org.liquidengine.legui.layout.LayoutManager;
import org.liquidengine.legui.layout.borderlayout.BorderLayout;
import org.liquidengine.legui.layout.borderlayout.BorderLayoutConstraint;
import org.liquidengine.legui.layout.boxlayout.BoxLayout;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.color.ColorUtil;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class BoxLayoutDemo extends Demo {

  private Frame frame;

  public BoxLayoutDemo(int width, int height, String title) {
    super(width, height, title);
  }

  public static void main(String[] args) {
    Demo demo = new BoxLayoutDemo(600, 400, "Border layout demo");
    demo.run();
  }

  @Override
  protected void update() {
    LayoutManager.getInstance().layout(frame);
  }

  @Override
  protected void createGuiElements(Frame frame, int width, int height) {
    this.frame = frame;
    Component frameContainer = frame.getContainer();
    frameContainer.setLayout(new BorderLayout());

    Component topPanel = new Panel();

    topPanel.getStyle().setMinimumSize(330, 50);
    topPanel.getStyle().setMaximumSize(Float.MAX_VALUE, 50);

    Component centerPanel = new Panel();
    Component bottomPanel = new Panel();

    Button addToVertical = new Button("Add to vertical", 10, 10, 150, 30);
    addToVertical.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
      if (event.getAction() == MouseClickAction.CLICK) {
        Panel component = new Panel();
        component.getStyle().getBackground().setColor(ColorUtil.randomColor());
        centerPanel.add(component);
      }
    });
    topPanel.add(addToVertical);

    Button addToHorizontal = new Button("Add to horizontal", 170, 10, 150, 30);
    addToHorizontal.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
      if (event.getAction() == MouseClickAction.CLICK) {
        Panel component = new Panel();
        component.getStyle().getBackground().setColor(ColorUtil.randomColor());
        bottomPanel.add(component);
      }
    });
    topPanel.add(addToHorizontal);
    frameContainer.add(topPanel, BorderLayoutConstraint.TOP);

    centerPanel.getStyle().getBackground().setColor(ColorConstants.white());
    centerPanel.getStyle().setBorder(new SimpleLineBorder(ColorConstants.black(), 1));
    centerPanel.setLayout(new BoxLayout(Orientation.VERTICAL));
    centerPanel.getStyle().setPadding(new Vector4f(10, 10, 10, 10));

    for (int i = 0; i < 10; i++) {
      Component c = new Panel();
      c.getStyle().getBackground().setColor(ColorUtil.randomColor().add(0.1f, 0.1f, 0.1f, 0));
      centerPanel.add(c);
    }
    Component component = centerPanel.getChildComponents().get(0);
    Tooltip hello = new Tooltip("Hello");
    hello.setSize(100, 20);
    component.setTooltip(hello);
    frameContainer.add(centerPanel, BorderLayoutConstraint.CENTER);

    bottomPanel.getStyle().setMaximumSize(Float.MAX_VALUE, 200);
    bottomPanel.getStyle().getBackground().setColor(ColorConstants.white());
    bottomPanel.setLayout(new BoxLayout(Orientation.HORIZONTAL));
    bottomPanel.getStyle().setPadding(new Vector4f(10, 10, 10, 10));

    for (int i = 0; i < 10; i++) {
      Component c = new Panel();
      c.getStyle().getBackground().setColor(ColorUtil.randomColor().add(0.1f, 0.1f, 0.1f, 0));
      bottomPanel.add(c);
    }
    frameContainer.add(bottomPanel, BorderLayoutConstraint.BOTTOM);
    LayoutManager.getInstance().layout(frame);
  }
}
