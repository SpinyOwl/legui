package org.liquidengine.legui.example;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.component.TextArea;
import org.lwjgl.glfw.GLFW;

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

        if (secondsFromStart > 5 && secondsFromStart < 7) {
            ImageView imageView = exampleGui.getImageView();
            if (imageView != null && imageView.getPath()!= null && imageView.getPath().equals("org/liquidengine/legui/example/11.jpg")) {
                exampleGui.removeComponent(imageView);
                System.out.println("update img");
                ImageView imageView1 = new ImageView("org/liquidengine/legui/example/2.jpg");
                imageView1.setSize(imageView.getSize());
                imageView1.setPosition(imageView.getPosition());
                exampleGui.addComponent(imageView1);
                exampleGui.setImageView(imageView1);
            }
        }
        if (secondsFromStart > 15&& secondsFromStart < 17) {
            ImageView imageView = exampleGui.getImageView();
            if (imageView != null && imageView.getPath()!= null && imageView.getPath().equals("org/liquidengine/legui/example/2.jpg")) {
                System.out.println("update img");
                ImageView view = new ImageView();
                view.setSize(imageView.getSize());
                view.setPosition(imageView.getPosition());
                exampleGui.removeComponent(imageView);
                exampleGui.addComponent(view);
                exampleGui.setImageView(view);
            }
        }

        Component mouseTargetGui = leguiContext.getMouseTargetGui();
        if (mouseTargetGui != exampleGui.getMouseTargetLabel() && mouseTargetGui != exampleGui.getFocusedGuiLabel()) {
            exampleGui.getMouseTargetLabel().getTextState().setText("M.TARGET: " + mouseTargetGui);
        } else {
            exampleGui.getMouseTargetLabel().getTextState().setText("M.TARGET: ");
        }

//        Vector2f mousePosition = leguiContext.getMousePosition();
//        exampleGui.getMouseLabel().getTextState().setText(String.format("X:%4s, Y:%4s", mousePosition.x, mousePosition.y));

        exampleGui.getUpsLabel().getTextState().setText("UPS: " + currentUps + "; fixed: " + fixedStep);
        GLFW.glfwSetWindowTitle(windowPointer, "UPS: " + currentUps + "; fixed: " + fixedStep);

        Component focusedGui = leguiContext.getFocusedGui();
        if (focusedGui != exampleGui.getMouseTargetLabel() && focusedGui != exampleGui.getFocusedGuiLabel()) {
            exampleGui.getFocusedGuiLabel().getTextState().setText("FOCUSED: " + focusedGui);
        } else {
            exampleGui.getFocusedGuiLabel().getTextState().setText("FOCUSED: ");
        }

        TextArea textArea = exampleGui.getTextArea();
        int caretPosition = textArea.getCaretPosition();
        String text = textArea.getTextState().getText();
        int left = caretPosition > 0 ? caretPosition - 1 : 0;
        int right = text.length() > 0 ? (caretPosition < text.length() - 1 ? caretPosition + 1 : text.length() - 1) : 0;
        String t = text.substring(left, right);
        exampleGui.getCaretp().getTextState().setText(caretPosition + " " + t + " " + textArea.getStartSelectionIndex() + " " + textArea.getEndSelectionIndex());
    }


}
