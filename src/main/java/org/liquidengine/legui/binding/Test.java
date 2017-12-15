package org.liquidengine.legui.binding;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.DialogLayer;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.RadioButton;
import org.liquidengine.legui.component.RadioButtonGroup;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.icon.ImageIcon;
import org.liquidengine.legui.image.BufferedImage;
import org.liquidengine.legui.marshal.json.JsonMarshaller;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class Test {

    public static void main(String[] args) {
        BindingRegistry.getInstance().loadBindings("org/liquidengine/legui/binding/binding-list.xml");

//        Vector2f c = new MyVec();
//        c.set(10, 20);
//
//        ClassBinding classBinding = BindingBuilder.createForClass(Vector2f.class, "vector", true, null).bind("x", "x_pos").bind("y", "y_pos").build();
//        BindingRegistry.getInstance().setBinding(Vector2f.class, classBinding);
//        String marshal = JsonMarshaller.marshal(c);
//        System.out.println(marshal);
//        MyVec v = JsonMarshaller.unmarshal(marshal, MyVec.class);
//        System.out.println(c.equals(v));

        // -------------------

        Frame frame = new Frame();
        frame.setSize(new Vector2f(100, 200));

        Layer test = new DialogLayer();
        frame.addLayer(test);

        Panel panel = new Panel();
        panel.add(new Panel(10, 10, 20, 20));

        Button component = new Button();
        BufferedImage image = new BufferedImage("org/liquidengine/legui/example/1.jpg");
        ImageIcon backgroundIcon = new ImageIcon(new Vector2f(100, 200), image);
        component.setBackgroundIcon(backgroundIcon);
        panel.add(component);

        RadioButtonGroup g1 = new RadioButtonGroup();
        RadioButtonGroup g2 = new RadioButtonGroup();

        RadioButton r1 = new RadioButton("1");
        RadioButton r2 = new RadioButton("2");
        RadioButton r3 = new RadioButton("3");
        r1.setRadioButtonGroup(g1);
        r2.setRadioButtonGroup(g1);
        r3.setRadioButtonGroup(g1);

        RadioButton r4 = new RadioButton("4");
        RadioButton r5 = new RadioButton("5");

        r4.setRadioButtonGroup(g2);
        r5.setRadioButtonGroup(g2);

        ScrollablePanel panel1 = new ScrollablePanel();
        frame.getContainer().add(panel1);

        panel.add(r1);
        panel.add(r2);
        panel.add(r3);
        panel.add(r4);
        panel.add(r5);

        ImageView view = new ImageView(image);
        frame.getContainer().add(view);

        frame.getContainer().add(panel);

        // --

        String json = JsonMarshaller.marshal(frame);
        System.out.println(json);

        Frame unmarshalled = JsonMarshaller.unmarshal(json, Frame.class);
        System.out.println(unmarshalled.equals(frame));

        json = JsonMarshaller.marshal(panel1);
        System.out.println(json);
        ScrollablePanel un = JsonMarshaller.unmarshal(json, ScrollablePanel.class);
        System.out.println(un.equals(panel1));

        SelectBox sb = new SelectBox();
        sb.addElement("Hello");
        sb.addElement("World");

        json = JsonMarshaller.marshal(sb);
        System.out.println(json);
        SelectBox unsb = JsonMarshaller.unmarshal(json, SelectBox.class);
        System.out.println(sb.equals(unsb));
    }

    public static class MyVec extends Vector2f {


    }


    private static class MyVec2GUSH {

        private String text = "Hello";
        private float f = 1.0f;
        private double d = 2.0d;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public float getF() {
            return f;
        }

        public void setF(float f) {
            this.f = f;
        }

        public double getD() {
            return d;
        }

        public void setD(double d) {
            this.d = d;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("text", text)
                .append("f", f)
                .append("d", d)
                .toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            MyVec2GUSH myVec2 = (MyVec2GUSH) o;

            return new EqualsBuilder()
                .append(f, myVec2.f)
                .append(d, myVec2.d)
                .append(text, myVec2.text)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                .append(text)
                .append(f)
                .append(d)
                .toHashCode();
        }
    }

}
