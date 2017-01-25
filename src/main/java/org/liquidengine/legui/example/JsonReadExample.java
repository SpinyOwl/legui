package org.liquidengine.legui.example;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeUtil;
import org.liquidengine.legui.util.IOUtil;
import org.lwjgl.glfw.GLFW;

/**
 * Created by Shcherbin Alexander on 12/1/2016.
 */
public class JsonReadExample {
    private static final String json = IOUtil.loadResourceAsString("org/liquidengine/legui/example/json.json", 1);

    public static void main(String[] args) {
//        System.out.println(json);
//        Frame deserialize = GsonSerializeUtil.deserialize(json);
//
//        Vector2f size = deserialize.getSize();
//        Demo demo = new Demo(100, 100, (int)size.x, (int)size.y, "test", deserialize, true){
//            @Override
//            public void update() {
//                Component mouseTargetGui = leguiContext.getMouseTargetGui();
//                GLFW.glfwSetWindowTitle(leguiContext.getGlfwWindow(), mouseTargetGui==null?"null":mouseTargetGui.toString());
//            }
//        };
//        demo.start();
    }
}
