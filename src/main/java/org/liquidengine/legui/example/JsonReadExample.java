package org.liquidengine.legui.example;

import org.liquidengine.legui.util.IOUtil;

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
