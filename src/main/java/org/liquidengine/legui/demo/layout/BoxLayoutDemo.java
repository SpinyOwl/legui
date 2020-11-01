package org.liquidengine.legui.demo.layout;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.demo.Demo;
import org.liquidengine.legui.style.Style.DisplayType;
import org.liquidengine.legui.style.Style.PositionType;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.flex.FlexStyle.FlexDirection;

/**
 * @author ShchAlexander.
 */
public class BoxLayoutDemo extends Demo {

    private Frame frame;
    private Panel c1;
    private Panel c2;

    private Panel c11;
    private Panel c12;
    private Panel c13;

    public BoxLayoutDemo(int width, int height, String title) {
        super(width, height, title);
    }

    public static void main(String[] args) {
        Demo demo = new BoxLayoutDemo(600, 400, "Box Layout Demo");
        demo.run();
    }

    @Override
    protected void update() {
    }

    @Override
    protected void createGuiElements(Frame frame, int width, int height) {
        this.frame = frame;
        Component frameContainer = frame.getContainer();
        frameContainer.getStyle().setDisplay(DisplayType.FLEX);
        frameContainer.getStyle().getBackground().setColor(ColorConstants.lightGray());

        c1 = new Panel();
        frameContainer.add(c1);

        c2 = new Panel(0, 0, 100, 100);
        frameContainer.add(c2);

        c11 = new Panel();
        c1.add(c11);
        c12 = new Panel();
        c1.add(c12);


        c1.getStyle().getBackground().setColor(ColorConstants.lightGreen());
        c1.getStyle().setHeight(50f);
        c1.getStyle().setLeft(0f);
        c1.getStyle().setRight(0f);

        c2.getStyle().getBackground().setColor(ColorConstants.lightBlue());
        c2.getStyle().setHeight(50f);
//        c2.getStyle().setWidth(50f);
        c2.getStyle().setLeft(0f);
        c2.getStyle().setRight(0f);
        c2.getStyle().setBottom(0f);

        c1.getStyle().setDisplay(DisplayType.FLEX);
        c1.getStyle().getFlexStyle().setFlexDirection(FlexDirection.ROW);

        c11.getStyle().setPosition(PositionType.ABSOLUTE);
        c11.getStyle().setTop(10f);
        c11.getStyle().setRight(0f);
        c11.getStyle().setWidth(50f);
        c11.getStyle().setHeight(50f);
        c12.getStyle().setPosition(PositionType.ABSOLUTE);
        c12.getStyle().setTop(10f);
        c12.getStyle().setRight(50f);
        c12.getStyle().setWidth(50f);
        c12.getStyle().setHeight(50f);
    }
}
