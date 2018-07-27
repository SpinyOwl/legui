package org.liquidengine.legui.demo;

import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.impl.AbstractMouseClickEventListener;
import org.liquidengine.legui.system.LeguiSystem;
import org.liquidengine.legui.system.Window;

public class LeguiSystemUsageDemo {

    public static void main(String[] args) {
        System.setProperty("joml.nounsafe", Boolean.TRUE.toString());
        System.setProperty("java.awt.headless", Boolean.TRUE.toString());

        LeguiSystem.initialize();

        Window window = LeguiSystem.createWindow(800, 600, "HELLO");
        window.setVisible(true);

        createGUI(window);

        Window finalWindow = window;
        finalWindow.addCloseEventListener(event -> {
            System.out.println("CLOSE EVENT");
            LeguiSystem.destroyWindow(finalWindow);
            LeguiSystem.destroy();
        });
    }

    private static void createGUI(Window window) {
        final Integer[] clickedTimes = {0};

        Button button = new Button("Button", 10, 20, 100, 30);
        Label l = new Label(getText(clickedTimes[0]), 10, 60, 100, 30);

        button.getListenerMap().addListener(MouseClickEvent.class, new AbstractMouseClickEventListener() {
            public void onClick(MouseClickEvent event) {
                clickedTimes[0]++;
                l.getTextState().setText(getText(clickedTimes[0]));
            }
        });

        window.getFrame().getContainer().add(button);
        window.getFrame().getContainer().add(l);
    }

    private static String getText(Integer clickedTimes) {
        return "Button clicked: " + clickedTimes;
    }

}
