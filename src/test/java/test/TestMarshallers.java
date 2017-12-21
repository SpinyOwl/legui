package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.liquidengine.legui.binding.BindingRegistry;
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
import org.liquidengine.legui.marshal.json.JsonMarshaller;

/**
 * Test of marshaller. Checks that object is the same after marshal -> unmarshal operations.
 *
 * @author Aliaksandr_Shcherbin.
 */
public class TestMarshallers {

    /**
     * Used to initialize bindings.
     */
    @BeforeClass
    public static void intialize() {
        BindingRegistry.getInstance().loadBindings("org/liquidengine/legui/binding/binding-list.xml");
    }

    /**
     * Used to check custom types marshalling.
     */
    @Test
    public void customTypesTest() {

        // Creating binding for specific type.
        ClassBinding<MyVec> b = new ClassBinding<>(MyVec.class, "MYVEC", true);
        b.putBinding(new Binding("vectors", "vectors"));
        b.putBinding(new Binding("vecMap", "vMap"));
        BindingRegistry.getInstance().addBinding(b);

        // creating instance to test.
        MyVec v1 = new MyVec();

        // adding vecMap
        HashMap<String, MyVecTwo> vecMap = new HashMap<>();
        vecMap.put("v - 1", new MyVecTwo("WWW"));
        v1.setVecMap(vecMap);

        // adding vectors
        ArrayList<MyVecTwo> vectors = new ArrayList<>();
        vectors.add(new MyVecTwo());
        vectors.add(new MyVecTwo("MYVEC"));
        v1.setVectors(vectors);

        // adding non-binded vecMapMap
        HashMap<MyVec, MyVecTwo> vecMapMap = new HashMap<>();
        vecMapMap.put(new MyVec(), new MyVecTwo("MY VEC WWW"));
        v1.setVecMapMap(vecMapMap);

        String jsonVec = JsonMarshaller.marshal(v1); // marshal
        MyVec unmarshalled = JsonMarshaller.unmarshal(jsonVec, MyVec.class); //unmarshal

        // checking only those fields that was mapped.
        Assert.assertEquals(v1.getVectors(), unmarshalled.getVectors());
        Assert.assertEquals(v1.getVecMap(), unmarshalled.getVecMap());
    }

    /**
     * Used to test marshalling - unmarshalling on complex hierarchical structure as frame.
     */
    @Test
    public void testFrame() {
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

        String json = JsonMarshaller.marshal(frame);
        Frame unmarshalled = JsonMarshaller.unmarshal(json, Frame.class);
        Assert.assertEquals(frame, unmarshalled);
    }

    /**
     * Used to test marshalling - unmarshalling of component with custom field accessor.
     */
    @Test
    public void testSelectbox() {
        SelectBox sb = new SelectBox();
        sb.addElement("Hello");
        sb.addElement("World");

        String json = JsonMarshaller.marshal(sb);
        SelectBox unsb = JsonMarshaller.unmarshal(json, SelectBox.class);
        Assert.assertEquals(sb, unsb);
    }

    /**
     * Test type.
     */
    public static class MyVec {

        /**
         * List to test.
         */
        private List<? extends MyVecTwo> vectors;
        /**
         * Map of elements to test.
         */
        private Map<String, MyVecTwo> vecMap;
        /**
         * Complex element map.
         */
        private Map<MyVec, MyVecTwo> vecMapMap;

        /**
         * Gets vec map map.
         *
         * @return the vec map map
         */
        public Map<MyVec, MyVecTwo> getVecMapMap() {
            return vecMapMap;
        }

        /**
         * Sets vec map map.
         *
         * @param vecMapMap the vec map map
         */
        public void setVecMapMap(Map<MyVec, MyVecTwo> vecMapMap) {
            this.vecMapMap = vecMapMap;
        }

        /**
         * Gets vec map.
         *
         * @return the vec map
         */
        public Map<String, MyVecTwo> getVecMap() {
            return vecMap;
        }

        /**
         * Sets vec map.
         *
         * @param vecMap the vec map
         */
        public void setVecMap(Map<String, MyVecTwo> vecMap) {
            this.vecMap = vecMap;
        }

        /**
         * Gets vectors.
         *
         * @return the vectors
         */
        public List<? extends MyVecTwo> getVectors() {
            return vectors;
        }

        /**
         * Sets vectors.
         *
         * @param vectors the vectors
         */
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


    /**
     * The type My vec two.
     */
    public static class MyVecTwo {

        private String text = "Hello";
        private float f = 1.0f;
        private double d = 2.0d;

        /**
         * Instantiates a new My vec two.
         */
        public MyVecTwo() {
        }

        /**
         * Instantiates a new My vec two.
         *
         * @param www the www
         */
        public MyVecTwo(String www) {
            text = www;
        }

        /**
         * Gets text.
         *
         * @return the text
         */
        public String getText() {
            return text;
        }

        /**
         * Sets text.
         *
         * @param text the text
         */
        public void setText(String text) {
            this.text = text;
        }

        /**
         * Gets f.
         *
         * @return the f
         */
        public float getF() {
            return f;
        }

        /**
         * Sets f.
         *
         * @param f the f
         */
        public void setF(float f) {
            this.f = f;
        }

        /**
         * Gets d.
         *
         * @return the d
         */
        public double getD() {
            return d;
        }

        /**
         * Sets d.
         *
         * @param d the d
         */
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
