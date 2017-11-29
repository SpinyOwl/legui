package org.liquidengine.legui.binding.parser;

import org.joml.Vector2f;
import org.liquidengine.legui.binding.Binding;
import org.liquidengine.legui.binding.Binding.Bind;
import org.liquidengine.legui.binding.BindingRegistry;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.marshal.j.JsonMarshaller;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class Test {

    public static void main(String[] args) {
//        BindingParserService.getInstance().parseList("org/liquidengine/legui/binding/binding-list.xml");

        Vector2f c = new MyVec();
        c.set(10, 20);

        Binding binding = new Binding(Vector2f.class);
        binding.bind("x", new Bind("x", "x_pos"));
        binding.bind("y", new Bind("y", "y_pos"));

        BindingRegistry.getInstance().setBinding(Vector2f.class, binding);
        BindingRegistry.getInstance().setBinding(MyVec.class, binding);
        BindingRegistry.getInstance().setBinding(MyVec2.class, binding);

        String marshal = JsonMarshaller.marshal(c);

        System.out.println(marshal);

    }

    private static class MyVec extends Vector2f {

    }


    private static class MyVec2 {

    }

}
