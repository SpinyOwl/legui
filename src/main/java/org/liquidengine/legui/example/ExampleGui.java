package org.liquidengine.legui.example;

import org.joml.Vector4f;
import org.liquidengine.legui.component.*;


/**
 * Created by Shcherbin Alexander on 9/19/2016.
 */
public class ExampleGui extends ComponentContainer {
    private Image image;

    public ExampleGui(int width, int height) {
        super(0, 0, width, height);

        Panel p1 = new Panel(1 * 20, 10, 10, 10);
        this.addComponent(p1);

        Panel p2 = new Panel(2 * 20, 10, 10, 10);
        this.addComponent(p2);

        Panel p3 = new Panel(3 * 20, 10, 10, 10);
        this.addComponent(p3);

        Panel p4 = new Panel(4 * 20, 10, 10, 10);
        this.addComponent(p4);

        Panel p5 = new Panel(5 * 20, 10, 10, 10);
        this.addComponent(p5);

        Panel p6 = new Panel(6 * 20, 10, 10, 10);
        this.addComponent(p6);

        Panel p7 = new Panel(7 * 20, 10, 10, 10);
        this.addComponent(p7);

        Panel p8 = new Panel(8 * 20, 10, 10, 10);
        this.addComponent(p8);

        Panel p9 = new Panel(9 * 20, 10, 10, 10);
        this.addComponent(p9);

        Panel p10 = new Panel(10 * 20, 10, 10, 10);
        this.addComponent(p10);

        Panel p11 = new Panel(11 * 20, 10, 10, 10);
        this.addComponent(p11);

        Label label = new Label(1 * 20, 30, 100, 20, "Hello Label");
        this.addComponent(label);

        image = Image.createImage("org/liquidengine/legui/example/1.jpg");
        image.setPosition(20, 60);
        image.setSize(100, 100);
        this.addComponent(image);

        Button button = new Button(20, 170, 50, 20);
        button.setBackgroundColor(new Vector4f(1));
        this.addComponent(button);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.removeComponent(this.image);
        this.image = image;
        if (image != null) {
            this.addComponent(image);
        }
    }

}
