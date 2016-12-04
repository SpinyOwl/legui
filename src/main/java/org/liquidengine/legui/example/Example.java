package org.liquidengine.legui.example;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Image;
import org.liquidengine.legui.component.TextArea;

/**
 * Created by Alexander on 13.10.2016.
 */
public class Example extends Demo {

    private static ExampleGui exampleGui;

    public Example(int width, int height, String title, Component component) {
        super(width, height, title, component);
    }

    public static void main(String[] args) {
        int width = 800;
        int height = 600;
        exampleGui = new ExampleGui(width, height);
        Example demo = new Example(width, height, "Demo", exampleGui);
        demo.start();
    }

    @Override
    public void update() {

        if (secondsFromStart > 5) {
            Image image = exampleGui.getImage();
            if (image != null && image.getPath().equals("org/liquidengine/legui/example/11.jpg")) {
                exampleGui.removeComponent(image);
                System.out.println("update img");
                Image image1 = new Image("org/liquidengine/legui/example/2.jpg");
                image1.setSize(image.getSize());
                image1.setPosition(image.getPosition());
                exampleGui.addComponent(image1);
                exampleGui.setImage(image1);
            }
        }
        if (secondsFromStart > 15) {
            Image image = exampleGui.getImage();
            if (image != null && image.getPath().equals("org/liquidengine/legui/example/2.jpg")) {
                System.out.println("update img");
                image.setPath("org/liquidengine/legui/example/1.jpg");
            }
        }

        exampleGui.getMouseTargetLabel().getTextState().setText("M.TARGET: " + leguiContext.getMouseTargetGui());

        Vector2f mousePosition = leguiContext.getMousePosition();
        exampleGui.getMouseLabel().getTextState().setText(String.format("X:%4s, Y:%4s", mousePosition.x, mousePosition.y));

        exampleGui.getUpsLabel().getTextState().setText("UPS: " + currentUps);

        exampleGui.getFocusedGuiLabel().getTextState().setText("FOCUSED: " + leguiContext.getFocusedGui());

        TextArea textArea = exampleGui.getTextArea();
        int caretPosition = textArea.getCaretPosition();
        String text = textArea.getTextState().getText();
        int left = caretPosition > 0 ? caretPosition - 1 : 0;
        int right = text.length() > 0 ? (caretPosition < text.length() - 1 ? caretPosition + 1 : text.length() - 1) : 0;
        String t = text.substring(left, right);
        exampleGui.getCaretp().getTextState().setText(caretPosition + " " + t);
    }



}
