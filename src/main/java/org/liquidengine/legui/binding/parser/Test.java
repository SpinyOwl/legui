package org.liquidengine.legui.binding.parser;

import java.util.Map;
import java.util.Map.Entry;
import org.joml.Vector2f;
import org.liquidengine.legui.binding.model.ClassBinding;
import org.liquidengine.legui.binding.model.BindingBuilder;
import org.liquidengine.legui.binding.BindingRegistry;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.marshal.json.JsonMarshaller;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class Test {

    public static void main(String[] args) {
        BindingRegistry.getInstance().loadBindings("org/liquidengine/legui/binding/binding-list.xml");

        Vector2f c = new MyVec();
        c.set(10, 20);

        ClassBinding classBinding = BindingBuilder.createForClass(Vector2f.class, "vector", true, null).bind("x", "x_pos").bind("y", "y_pos").build();

        BindingRegistry.getInstance().setBinding(Vector2f.class, classBinding);
        BindingRegistry.getInstance().setBinding(MyVec.class, classBinding);
        BindingRegistry.getInstance().setBinding(MyVec2.class, classBinding);

        Map<Class, ClassBinding> bindingMap = BindingRegistry.getInstance().getBindingMap();

        for (Entry<Class, ClassBinding> entry : bindingMap.entrySet()) {
            System.out.println(entry.getKey() + " \n    " + entry.getValue());
        }

        String marshal = JsonMarshaller.marshal(c);

        System.out.println(marshal);

        Widget widgetToMarshal = new Widget();
        String widgetJson = JsonMarshaller.marshal(widgetToMarshal);
        System.out.println(widgetJson);

        Widget widget = JsonMarshaller.unmarshal(widgetJson, Widget.class);

        System.out.println(widget.equals(widgetToMarshal));

    }

    private static class MyVec extends Vector2f {


    }


    private static class MyVec2 {

    }

}
