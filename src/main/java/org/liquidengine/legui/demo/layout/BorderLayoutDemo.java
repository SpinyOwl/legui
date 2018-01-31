package org.liquidengine.legui.demo.layout;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.demo.Demo;
import org.liquidengine.legui.layout.LayoutManager;
import org.liquidengine.legui.layout.borderlayout.BorderLayout;
import org.liquidengine.legui.layout.borderlayout.BorderLayoutConstraint;
import org.liquidengine.legui.style.color.ColorConstants;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class BorderLayoutDemo extends Demo {

  private Frame frame;

  private Label topLabel = new Label(10, 10, 100, 20);
  private Label leftLabel = new Label(10, 10, 100, 20);
  private Label rightLabel = new Label(10, 10, 100, 20);
  private Label centerLabel = new Label(10, 10, 100, 20);
  private Label inCenterLabel = new Label(10, 10, 100, 20);
  private Label bottomLabel = new Label(10, 10, 100, 20);

  private Panel top;
  private Panel bottom;
  private Panel left;
  private Panel right;
  private Panel center;
  private Panel inCenter;

  public BorderLayoutDemo(int width, int height, String title) {
    super(width, height, title);
  }

  public static void main(String[] args) {
    Demo demo = new BorderLayoutDemo(600, 400, "Border layout demo");
    demo.run();
  }

  @Override
  protected void update() {
    LayoutManager.getInstance().layout(frame);

    topLabel.getTextState().setText("" + top.getSize().x + " " + top.getSize().y);
    bottomLabel.getTextState().setText("" + bottom.getSize().x + " " + bottom.getSize().y);
    leftLabel.getTextState().setText("" + left.getSize().x + " " + left.getSize().y);
    rightLabel.getTextState().setText("" + right.getSize().x + " " + right.getSize().y);
    centerLabel.getTextState().setText("" + center.getSize().x + " " + center.getSize().y);
    inCenterLabel.getTextState().setText("" + inCenter.getSize().x + " " + inCenter.getSize().y);
  }

  @Override
  protected void createGuiElements(Frame frame, int width, int height) {
    this.frame = frame;
    Component container = frame.getContainer();
    container.getStyle().getBackground().setColor(ColorConstants.white());
    BorderLayout layout = new BorderLayout();
    container.setLayout(layout);
    container.getStyle().setPadding(new Vector4f(10, 10, 10, 10));

    top = new Panel();
//        top.getStyle().setPreferredSize(new Vector2f(130, 50));
    top.getStyle().setMinimumSize(new Vector2f(120, 10));
    top.getStyle().setMaximumSize(new Vector2f(Float.MAX_VALUE, 80));
    top.getStyle().getBackground().setColor(ColorConstants.lightBlue().mul(1, 1, 1, .5f));
    container.add(top, BorderLayoutConstraint.TOP);

    top.getStyle().setMargin(new Vector4f(0, 20, 0, 10));

    bottom = new Panel();
//        bottom.getStyle().setPreferredSize(new Vector2f(130, 50));
    bottom.getStyle().setMinimumSize(new Vector2f(120, 10));
    bottom.getStyle().setMaximumSize(new Vector2f(Float.MAX_VALUE, 80));
    bottom.getStyle().getBackground().setColor(ColorConstants.blue().mul(1, 1, 1, .5f));
    container.add(bottom, BorderLayoutConstraint.BOTTOM);

    bottom.getStyle().setMargin(new Vector4f(0, 5, 0, 10));

    left = new Panel();
//        left.getStyle().setPreferredSize(new Vector2f(100, 50));
    left.getStyle().setMinimumSize(new Vector2f(20, 10));
    left.getStyle().setMaximumSize(new Vector2f(200, Float.MAX_VALUE));
    left.getStyle().getBackground().setColor(ColorConstants.lightGreen().mul(1, 1, 1, .5f));
    container.add(left, BorderLayoutConstraint.LEFT);

    right = new Panel();
//        right.getStyle().setPreferredSize(new Vector2f(100, 50));
    right.getStyle().setMinimumSize(new Vector2f(20, 10));
    right.getStyle().setMaximumSize(new Vector2f(300, Float.MAX_VALUE));
    right.getStyle().getBackground().setColor(ColorConstants.green().mul(1, 1, 1, .5f));
    container.add(right, BorderLayoutConstraint.RIGHT);

    center = new Panel();
//        center.getStyle().setPreferredSize(new Vector2f(200, 200));
    center.getStyle().setMinimumSize(new Vector2f(100, 50));
    center.getStyle().getBackground().setColor(ColorConstants.red().mul(1, 1, 1, .5f));
    center.getStyle().setMargin(new Vector4f(20, 10, 30, 40));
    container.add(center, BorderLayoutConstraint.CENTER);

    inCenter = new Panel();
//        inCenter.getStyle().setPreferredSize(new Vector2f(200, 200));
    inCenter.getStyle().setMinimumSize(new Vector2f(50, 50));
    inCenter.getStyle().getBackground().setColor(ColorConstants.blue().mul(1, 1, 1, .5f));
    inCenter.getStyle().setMargin(new Vector4f(10));
    center.add(inCenter);

    top.add(topLabel);
    bottom.add(bottomLabel);
    left.add(leftLabel);
    right.add(rightLabel);
    center.add(centerLabel, BorderLayoutConstraint.TOP);
    inCenter.add(inCenterLabel, BorderLayoutConstraint.TOP);

    LayoutManager.getInstance().layout(frame);

    Panel component = new Panel();
    component.getStyle().setMinimumSize(new Vector2f(30, 30));
    component.getStyle().getBackground().setColor(ColorConstants.green().mul(1, 1, 1, .5f));
    component.getStyle().setMargin(new Vector4f(30));
    container.add(component);
  }
}
