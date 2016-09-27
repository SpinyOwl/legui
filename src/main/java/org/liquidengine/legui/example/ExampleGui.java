package org.liquidengine.legui.example;

import org.joml.Vector4f;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.component.optional.Orientation;


/**
 * Created by Shcherbin Alexander on 9/19/2016.
 */
public class ExampleGui extends Panel {
    private final Label label;
    private final Label mouseLabel;
    private final Label upsLabel;
    private Image image;

    public ExampleGui(int width, int height) {
        super(0, 0, width, height);

        Panel p1 = new Panel(1 * 20, 10, 10, 10);
        this.getContainer().addComponent(p1);

        Panel p2 = new Panel(2 * 20, 10, 10, 10);
        this.getContainer().addComponent(p2);

        Panel p3 = new Panel(3 * 20, 10, 10, 10);
        this.getContainer().addComponent(p3);

        Panel p4 = new Panel(4 * 20, 10, 10, 10);
        this.getContainer().addComponent(p4);

        Panel p5 = new Panel(5 * 20, 10, 10, 10);
        this.getContainer().addComponent(p5);

        Panel p6 = new Panel(6 * 20, 10, 10, 10);
        this.getContainer().addComponent(p6);

        Panel p7 = new Panel(7 * 20, 10, 10, 10);
        this.getContainer().addComponent(p7);

        Panel p8 = new Panel(8 * 20, 10, 10, 10);
        this.getContainer().addComponent(p8);

        Panel p9 = new Panel(9 * 20, 10, 10, 10);
        this.getContainer().addComponent(p9);

        Panel p10 = new Panel(10 * 20, 10, 10, 10);
        this.getContainer().addComponent(p10);

        Panel p11 = new Panel(11 * 20, 10, 10, 10);
        this.getContainer().addComponent(p11);

        label = new Label(20, 30, 100, 20, "Hello Label");
        this.getContainer().addComponent(label);

        mouseLabel = new Label(130, 30, 100, 20, "Hello Label");
        this.getContainer().addComponent(mouseLabel);

        upsLabel = new Label(130, 60, 100, 20, "Hello Label");
        this.getContainer().addComponent(upsLabel);

        image = Image.createImage("org/liquidengine/legui/example/1.jpg");
        image.setPosition(20, 60);
        image.setSize(100, 100);
        this.getContainer().addComponent(image);

        Button button = new Button(20, 170, 50, 20);
        button.setBackgroundColor(new Vector4f(1));
        this.getContainer().addComponent(button);

        CheckBox checkBox1 = new CheckBox(20, 200, 50, 20);
        this.getContainer().addComponent(checkBox1);

        CheckBox checkBox2 = new CheckBox(20, 230, 50, 20);
        checkBox2.setBackgroundColor(new Vector4f(1));
        checkBox2.setChecked(true);
        this.getContainer().addComponent(checkBox2);

        ProgressBar progressBar = new ProgressBar(250, 10, 100, 10);
        progressBar.setValue(50);
        this.getContainer().addComponent(progressBar);

        RadioButton radioButton1 = new RadioButton(250, 30, 100, 20);
        this.getContainer().addComponent(radioButton1);
        radioButton1.setSelected(true);
        RadioButton radioButton2 = new RadioButton(250, 60, 100, 20);
        this.getContainer().addComponent(radioButton2);
        radioButton2.setSelected(false);

        Slider slider1 = new Slider(250, 90, 100, 20, 30);
        this.getContainer().addComponent(slider1);

        Slider slider2 = new Slider(220, 90, 20, 100, 50);
        slider2.setOrientation(Orientation.VERTICAL);
        this.getContainer().addComponent(slider2);

        TextInput textInput = new TextInput(250, 130, 100, 30);
        this.getContainer().addComponent(textInput);

        Widget widget = new Widget("Hello widget", 250,170,100,100);
        Panel component0 = new Panel(-5, -5, 10, 10); component0.getBackgroundColor().set(1,0,0,1); widget.getContainer().addComponent(component0);
        Panel component1 = new Panel(-5, 75, 10, 10); component1.getBackgroundColor().set(1,0,0,1); widget.getContainer().addComponent(component1);
        Panel component2 = new Panel(95, -5, 10, 10); component2.getBackgroundColor().set(1,0,0,1); widget.getContainer().addComponent(component2);
        Panel component3 = new Panel(95, 75, 10, 10); component3.getBackgroundColor().set(1,0,0,1); widget.getContainer().addComponent(component3);
        Panel component4 = new Panel(45, 35, 10, 10); component4.getBackgroundColor().set(1,0,0,1); widget.getContainer().addComponent(component4);
        widget.setTitleHeight(20);
        this.getContainer().addComponent(widget);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Label getLabel() {
        return label;
    }

    public Label getMouseLabel() {
        return mouseLabel;
    }

    public Label getUpsLabel() {
        return upsLabel;
    }
}
