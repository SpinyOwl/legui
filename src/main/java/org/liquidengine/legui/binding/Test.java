package org.liquidengine.legui.binding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.binding.model.Binding;
import org.liquidengine.legui.binding.model.ClassBinding;
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
import org.liquidengine.legui.marshal.json.JsonMarshaller2;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class Test {

    public static void main(String[] args) {
        BindingRegistry.getInstance().loadBindings("org/liquidengine/legui/binding/binding-list.xml");

//        t1();
        t2();
    }

    private static void t1() {
        ClassBinding<MyVec> b = new ClassBinding<>(MyVec.class, "MYVEC", true);
        b.putBinding(new Binding("vectors", "vectors"));
        b.putBinding(new Binding("vecMap", "vMap"));
        BindingRegistry.getInstance().addBinding(b);
        MyVec v1 = new MyVec();

        HashMap<String, MyVecTwo> vecMap = new HashMap<>();
        vecMap.put("v - 1", new MyVecTwo("WWW"));
        v1.setVecMap(vecMap);

        ArrayList<MyVecTwo> vectors = new ArrayList<>();
        vectors.add(new MyVecTwo());
        MyVecTwo e = new MyVecTwo();
        e.setText("MYVEC");
        vectors.add(e);

        v1.setVectors(vectors);

        String jsonVec = JsonMarshaller2.marshal(v1);
        System.out.println(jsonVec);

        MyVec v2 = JsonMarshaller2.unmarshal(jsonVec, MyVec.class);

        System.out.println(v2.equals(v1));
        System.out.println("----");
        System.out.println(v1);
        System.out.println("----");
        System.out.println(v2);
    }

    private static void t2() {
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

        String json = JsonMarshaller2.marshal(frame);
        System.out.println(json);
        System.out.println();

        Frame unmarshalled = JsonMarshaller2.unmarshal(json, Frame.class);
        System.out.println(unmarshalled.equals(frame));
//
        json = JsonMarshaller2.marshal(panel1);
        System.out.println(json);
        System.out.println();
        ScrollablePanel un = JsonMarshaller2.unmarshal(json, ScrollablePanel.class);
        System.out.println(un.equals(panel1));
//
        SelectBox sb = new SelectBox();
        sb.addElement("Hello");
        sb.addElement("World");

        json = JsonMarshaller2.marshal(sb);
        System.out.println(json);
        System.out.println();
        SelectBox unsb = JsonMarshaller2.unmarshal(json, SelectBox.class);
        System.out.println(sb.equals(unsb));
    }

    public static class MyVec {

        private List<? extends MyVecTwo> vectors;
        private Map<String, MyVecTwo> vecMap;
        private Map<MyVec, MyVecTwo> vecMapMap;

        public Map<MyVec, MyVecTwo> getVecMapMap() {
            return vecMapMap;
        }

        public void setVecMapMap(Map<MyVec, MyVecTwo> vecMapMap) {
            this.vecMapMap = vecMapMap;
        }

        public Map<String, MyVecTwo> getVecMap() {
            return vecMap;
        }

        public void setVecMap(Map<String, MyVecTwo> vecMap) {
            this.vecMap = vecMap;
        }

        public List<? extends MyVecTwo> getVectors() {
            return vectors;
        }

        public void setVectors(List<MyVecTwo> vectors) {
            this.vectors = vectors;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            MyVec myVec = (MyVec) o;

            return new EqualsBuilder()
                .append(getVectors(), myVec.getVectors())
                .append(getVecMap(), myVec.getVecMap())
                .append(getVecMapMap(), myVec.getVecMapMap())
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                .append(getVectors())
                .append(getVecMap())
                .append(getVecMapMap())
                .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .append("vectors", vectors)
                .append("vecMap", vecMap)
                .append("vecMapMap", vecMapMap)
                .toString();
        }
    }


    public static class MyVecTwo {

        private String text = "Hello";
        private float f = 1.0f;
        private double d = 2.0d;

        public MyVecTwo() {
        }

        public MyVecTwo(String www) {
            text = www;
        }

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

            MyVecTwo myVec2 = (MyVecTwo) o;

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
