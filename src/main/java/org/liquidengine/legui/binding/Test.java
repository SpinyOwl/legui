package org.liquidengine.legui.binding;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.binding.model.BindingBuilder;
import org.liquidengine.legui.binding.model.ClassBinding;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.Widget;
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

        Panel panelToMarshal = new Panel();
        panelToMarshal.add(new Panel(10,10,20,20));
        String panelJson = JsonMarshaller.marshal(panelToMarshal);
        System.out.println(panelJson);

        Panel widget = JsonMarshaller.unmarshal(panelJson, Panel.class);

        System.out.println(widget.equals(panelToMarshal));

        System.out.println("---------------------------------");

        MyVec2GUSH v = new MyVec2GUSH();
        String vJson = JsonMarshaller.marshal(v);
        System.out.println(vJson);

        MyVec2GUSH vm = JsonMarshaller.unmarshal(vJson, MyVec2GUSH.class);

        System.out.println(v.equals(vm));
        System.out.println(v);
        System.out.println(vm);

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
