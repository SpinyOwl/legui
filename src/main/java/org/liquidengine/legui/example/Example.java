package org.liquidengine.legui.example;

import org.liquidengine.legui.component.*;
import org.liquidengine.legui.image.Image;
import org.lwjgl.glfw.GLFW;

import java.util.Map;

/**
 * Created by Alexander on 13.10.2016.
 */
public class Example extends Demo {

    private static ExampleGui exampleGui;

    public Example(int width, int height, String title, Frame frame) {
        super(width, height, title, frame);
    }

    public static void main(String[] args) {
        int width  = 800;
        int height = 600;
        exampleGui = new ExampleGui(width, height);
        Example demo = new Example(width, height, "Demo", exampleGui);
        demo.start();
    }

    @Override
    public void update() {

        if (secondsFromStart > 5 && secondsFromStart < 7) {
            ImageView imageView = exampleGui.getImageView();
            if (imageView != null && imageView.getImage() != null && imageView.getImage().getPath() != null
                    && imageView.getImage().getPath().equals("org/liquidengine/legui/example/11.jpg")) {
                exampleGui.removeComponent(imageView);
                System.out.println("update img");
                ImageView imageView1 = new ImageView(new Image("org/liquidengine/legui/example/2.jpg"));
                imageView1.setSize(imageView.getSize());
                imageView1.setPosition(imageView.getPosition());
                exampleGui.addComponent(imageView1);
                exampleGui.setImageView(imageView1);
            }
        }
        if (secondsFromStart > 15 && secondsFromStart < 17) {
            ImageView imageView = exampleGui.getImageView();
            if (imageView != null && imageView.getImage() != null && imageView.getImage().getPath() != null
                    && imageView.getImage().getPath().equals("org/liquidengine/legui/example/2.jpg")) {
                System.out.println("update img");
                ImageView view = new ImageView(new Image("org/liquidengine/legui/example/1.jpg"));
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

        exampleGui.getUpsLabel().getTextState().setText("UPS: " + currentUps + "; fixed: " + fixedStep);
        GLFW.glfwSetWindowTitle(windowPointer, "UPS: " + currentUps + "; fixed: " + fixedStep);

        Component focusedGui = leguiContext.getFocusedGui();
        if (focusedGui != exampleGui.getMouseTargetLabel() && focusedGui != exampleGui.getFocusedGuiLabel()) {
            exampleGui.getFocusedGuiLabel().getTextState().setText("FOCUSED: " + focusedGui);
        } else {
            exampleGui.getFocusedGuiLabel().getTextState().setText("FOCUSED: ");
        }

        TextArea textArea      = exampleGui.getTextArea();
        int      caretPosition = textArea.getCaretPosition();
        String   text          = textArea.getTextState().getText();
        int      length        = text.length();
        int      left          = caretPosition > 0 ? caretPosition - 1 : 0;
        int      right         = length > 0 ? (caretPosition < length - 1 ? caretPosition + 1 : length - 1) : 0;
        String   t             = text.substring(left, right);
        exampleGui.getCaretp().getTextState().setText(caretPosition + " " + t + " " + textArea.getStartSelectionIndex() + " " + textArea.getEndSelectionIndex());


        TextInput           textInput = exampleGui.getTextInput();
        Map<String, Object> metadata  = textInput.getMetadata();
        Float               lastcx    = (Float) metadata.get("lastcx");
        Float               caretx    = (Float) metadata.get("caretx");
        Float               d         = (caretx == null ? 0 : caretx) - (lastcx == null ? 0 : lastcx);
        Float               co        = (Float) metadata.get("coffsetx");
        Float               lo        = (Float) metadata.get("loffsetx");
        float               lb        = textInput.getPosition().x + textInput.getTextState().getPadding().x;
        float               rb        = textInput.getPosition().x + textInput.getSize().x - textInput.getTextState().getPadding().z;
        exampleGui.getDebugLabel().getTextState().setText("c: " + caretx + "; l: " + lastcx + "; d: " + d + "; co: " + co + "; lo: " + lo + "; lb: " + lb + "; rb: " + rb);
    }


}
