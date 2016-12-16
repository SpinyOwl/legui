package org.liquidengine.legui.example;

import org.joml.Vector4f;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.component.border.SimpleRectangleLineBorder;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.event.component.KeyboardKeyEvent;
import org.liquidengine.legui.event.component.MouseClickEvent;
import org.liquidengine.legui.event.component.SliderChangeEvent;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.image.Image;
import org.liquidengine.legui.listener.component.KeyboardKeyEventListener;
import org.liquidengine.legui.listener.component.MouseClickEventListener;
import org.liquidengine.legui.listener.component.SliderChangeEventListener;
import org.liquidengine.legui.util.ColorConstants;
import org.liquidengine.legui.util.Util;
import org.lwjgl.glfw.GLFW;

import static org.liquidengine.legui.event.component.MouseClickEvent.MouseClickAction.*;


/**
 * Created by Shcherbin Alexander on 9/19/2016.
 */
public class ExampleGui extends Frame {
    private final Label mouseTargetLabel;
    private final Label mouseLabel;
    private final Label upsLabel;
    private final Label focusedGuiLabel;
    private final TextInput caretp;
    private final TextArea textArea;
    private ImageView imageView;

    public ExampleGui(int width, int height) {
        super(width, height);

        //@formatter:off
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
        //@formatter:on

        mouseTargetLabel = new Label(10, height - 30, width - 20, 20, "Hello Label");
        this.addComponent(mouseTargetLabel);

        focusedGuiLabel = new Label(10, height - 50, width - 20, 20, "Hello Label");
        focusedGuiLabel.setBorder(new SimpleRectangleLineBorder(ColorConstants.red(), 1));
        this.addComponent(focusedGuiLabel);

        mouseLabel = new Label(130, 30, 100, 20, "Hello Label");
        this.addComponent(mouseLabel);

        upsLabel = new Label(130, 60, 100, 20, "Hello Label");
        this.addComponent(upsLabel);

        imageView = new ImageView(new Image("org/liquidengine/legui/example/11.jpg"));
        imageView.setPosition(20, 30);
        imageView.setSize(100, 100);
        this.addComponent(imageView);

        Button button = new Button(20, 170, 50, 20);
        button.setBackgroundColor(new Vector4f(1));
        this.addComponent(button);

        CheckBox checkBox1 = new CheckBox(20, 200, 50, 20);
        this.addComponent(checkBox1);

        CheckBox checkBox2 = new CheckBox(20, 230, 50, 20);
        checkBox2.setBackgroundColor(new Vector4f(1));
        checkBox2.setChecked(true);
        this.addComponent(checkBox2);

        ProgressBar progressBar = new ProgressBar(250, 10, 100, 10);
        progressBar.setValue(50);
        this.addComponent(progressBar);

        RadioButtonGroup radioButtonGroup = new RadioButtonGroup();
        RadioButton radioButton1 = new RadioButton(250, 30, 100, 20);
        this.addComponent(radioButton1);
        radioButton1.setSelected(true);
        radioButton1.setRadioButtonGroup(radioButtonGroup);
        RadioButton radioButton2 = new RadioButton(250, 60, 100, 20);
        this.addComponent(radioButton2);
        radioButton2.setSelected(false);
        radioButton2.setRadioButtonGroup(radioButtonGroup);

        Slider slider1 = new Slider(250, 90, 100, 20, 30);
        this.addComponent(slider1);

        Slider slider2 = new Slider(220, 90, 20, 100, 50);
        slider2.setOrientation(Orientation.VERTICAL);
        this.addComponent(slider2);

        TextInput textInput = new TextInput(250, 130, 100, 30);
        textInput.getTextState().setHorizontalAlign(HorizontalAlign.RIGHT);
        this.addComponent(textInput);

        Widget widget = new Widget("Hello widget", 250, 170, 100, 100);
        widget.setTitleHeight(20);
        widget.setCloseButtonColor(ColorConstants.red());
        widget.setTitleBackgroundColor(ColorConstants.lightGreen());

        Button turnWidVisible = new Button(360, 280, 20, 20, "");
        ImageView bgIm = new ImageView(new Image("org/liquidengine/legui/example/1.jpg"));
        ImageView hbgIm = new ImageView(new Image("org/liquidengine/legui/example/2.jpg"));
        bgIm.setPosition(2, 2);
        hbgIm.setPosition(1, 1);
        bgIm.setSize(16, 16);
        hbgIm.setSize(18, 18);
        turnWidVisible.setBackgroundImage(bgIm);
        turnWidVisible.setHoveredBackgroundImage(hbgIm);
        turnWidVisible.getLeguiEventListeners().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (CLICK.equals(event.getAction())) widget.setVisible(true);
        });
        this.addComponent(turnWidVisible);

        Panel c0 = new Panel(-5, -5, 10, 10);
        c0.setBackgroundColor(1, 0, 0, 1);
        widget.getContainer().addComponent(c0);
        Panel c1 = new Panel(-5, 75, 10, 10);
        c1.setBackgroundColor(1, 0, 0, 1);
        widget.getContainer().addComponent(c1);
        Panel c2 = new Panel(95, -5, 10, 10);
        c2.setBackgroundColor(1, 0, 0, 1);
        widget.getContainer().addComponent(c2);
        c2.setVisible(false);
        Panel c3 = new Panel(95, 75, 10, 10);
        c3.setBackgroundColor(1, 0, 0, 1);
        widget.getContainer().addComponent(c3);
        Panel c4 = new Panel(45, 35, 10, 10);
        c4.setBackgroundColor(1, 0, 0, 1);
        widget.getContainer().addComponent(c4);
        this.addComponent(widget);

        Widget widget2 = new Widget("Hello 2 widget", 250, 310, 100, 100);
        widget2.setTitleHeight(20);
        widget2.setCloseButtonColor(ColorConstants.red());
        widget2.setTitleBackgroundColor(ColorConstants.lightGreen());
        this.addComponent(widget2);

        Widget widget3 = new Widget("Hello 2 widget", 250, 420, 100, 100);
        widget3.setTitleHeight(20);
        widget3.setCloseButtonColor(ColorConstants.red());
        widget3.setTitleBackgroundColor(ColorConstants.lightGreen());
        widget3.setTitleEnabled(false);
        widget3.setCloseable(false);
        this.addComponent(widget3);

        widget3.getContainer().addComponent(new Panel(5, 5, 20, 20));
        widget3.getContainer().addComponent(new Panel(30, 5, 20, 20));
        widget3.getContainer().addComponent(new Panel(30, 30, 20, 20));
        widget3.getContainer().addComponent(new Panel(5, 30, 20, 20));
        widget3.getContainer().addComponent(new Panel(5, 30, 20, 20));
        Button b = new Button(55, 5, 40, 45);
        b.getTextState().setFont(FontRegister.MATERIAL_ICONS_REGULAR);
        b.getTextState().setVerticalAlign(VerticalAlign.MIDDLE);
        b.getTextState().setHorizontalAlign(HorizontalAlign.CENTER);
        b.getTextState().setFontSize(20);

        String up = Util.cpToStr(0xE5D8);
        String down = Util.cpToStr(0xE5DB);
        b.getTextState().setText(down);
        b.getLeguiEventListeners().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == CLICK) {
                widget3.setTitleEnabled(!widget3.isTitleEnabled());
                b.getTextState().setText(widget3.isTitleEnabled() ? up : down);
            }
        });
        widget3.getContainer().addComponent(b);

        ScrollBar scrollBar1 = new ScrollBar(360, 170, 20, 100, 20);
        scrollBar1.setOrientation(Orientation.VERTICAL);
        scrollBar1.setVisibleAmount(20);
        scrollBar1.setArrowsEnabled(true);
        scrollBar1.setBackgroundColor(ColorConstants.white());
        scrollBar1.setScrollColor(ColorConstants.darkGray());
        scrollBar1.setArrowColor(ColorConstants.darkGray());
        scrollBar1.setBorder(new SimpleRectangleLineBorder(ColorConstants.red(), 1));
        this.addComponent(scrollBar1);

        ScrollBar scrollBar11 = new ScrollBar(385, 170, 7, 100, 20);
        scrollBar11.setOrientation(Orientation.VERTICAL);
        scrollBar11.setVisibleAmount(20);
        scrollBar11.setArrowsEnabled(false);
        scrollBar11.setBackgroundColor(ColorConstants.white());
        scrollBar11.setScrollColor(ColorConstants.darkGray());
        scrollBar11.setBorder(new SimpleRectangleLineBorder(ColorConstants.red(), 1));
        scrollBar11.setCornerRadius(3);
        this.addComponent(scrollBar11);

        ScrollBar scrollBar2 = new ScrollBar(250, 280, 100, 20, 20);
        scrollBar2.setOrientation(Orientation.HORIZONTAL);
        scrollBar2.setVisibleAmount(20);
        scrollBar2.setArrowsEnabled(true);
        scrollBar2.setBorder(new SimpleRectangleLineBorder(ColorConstants.black(), 1));
        scrollBar2.setBackgroundColor(ColorConstants.darkGray());
        scrollBar2.setScrollColor(ColorConstants.white());
        scrollBar2.setArrowColor(ColorConstants.white());
        this.addComponent(scrollBar2);

        Panel panel1 = new Panel(420, 170, 100, 100);
        panel1.setBackgroundColor(ColorConstants.blue());
        this.addComponent(panel1);
//        panel1.getLeguiEventListeners().addListener(CursorEnterEvent.class, System.out::println);
        Panel panel2 = new Panel(470, 170, 100, 100);
        panel2.setBackgroundColor(ColorConstants.green());
        this.addComponent(panel2);

        button.getLeguiEventListeners().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            MouseClickEvent.MouseClickAction action = event.getAction();
            if (CLICK.equals(action)) mouseTargetLabel.setVisible(!mouseTargetLabel.isVisible());
            if (RELEASE.equals(action)) System.out.println("RELEASE");
            if (PRESS.equals(action)) System.out.println("PRESS");
        });

        ScrollablePanel scrollablePanel = new ScrollablePanel(420, 10, 250, 150);
        scrollablePanel.setBackgroundColor(1, 1, 1, 1);
        scrollablePanel.getContainer().setSize(300, 200);
        scrollablePanel.resize();

        ScrollablePanel scp = new ScrollablePanel(10, 10, 150, 100);
        scp.getContainer().setSize(300, 300);
        scp.resize();

        scp.getContainer().addComponent(new TextInput("Hello Scrollable", 10, 10, 150, 20));

        scrollablePanel.getContainer().addComponent(scp);
        this.addComponent(scrollablePanel);

        slider2.getLeguiEventListeners().addListener(SliderChangeEvent.class, (SliderChangeEventListener) event -> {
            scrollablePanel.getHorizontalScrollBar().getSize().y = event.getSlider().getValue() / 2f + 10;
            scrollablePanel.resize();
        });
        slider1.getLeguiEventListeners().addListener(SliderChangeEvent.class, (SliderChangeEventListener) event -> {
            scrollablePanel.getHorizontalScrollBar().setArrowSize(event.getSlider().getValue() / 4f + 10);
            scrollablePanel.resize();
        });

        textArea = new TextArea(420, 280, 150, 100);
        textArea.getTextState().setText("ABC DEF GH\r\nI JKL MNO PQR\nSTU VWXYZ");
        textArea.setCaretPosition(12);
        textArea.getTextState().setHorizontalAlign(HorizontalAlign.CENTER);
        this.addComponent(textArea);

        textArea.getLeguiEventListeners().addListener(KeyboardKeyEvent.class, (KeyboardKeyEventListener) event -> {
            if (event.getKey() == GLFW.GLFW_KEY_F1 && event.getAction() == GLFW.GLFW_RELEASE)
                textArea.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
            else if (event.getKey() == GLFW.GLFW_KEY_F2 && event.getAction() == GLFW.GLFW_RELEASE)
                textArea.getTextState().setHorizontalAlign(HorizontalAlign.CENTER);
            else if (event.getKey() == GLFW.GLFW_KEY_F3 && event.getAction() == GLFW.GLFW_RELEASE)
                textArea.getTextState().setHorizontalAlign(HorizontalAlign.RIGHT);
        });

        caretp = new TextInput(420, 400, 150, 20);
        caretp.getTextState().setHorizontalAlign(HorizontalAlign.CENTER);
        this.addComponent(caretp);

        TextInput inpur = new TextInput(420, 430, 50, 35);
        inpur.getTextState().setText("00");
        inpur.getTextState().setFontSize(35);
        inpur.getTextState().setHorizontalAlign(HorizontalAlign.CENTER);
        inpur.getTextState().setVerticalAlign(VerticalAlign.MIDDLE);
        inpur.setBackgroundColor(ColorConstants.white());
        this.addComponent(inpur);

        SelectBox selectBox = new SelectBox(20, 260, 100, 20);
        selectBox.addElement("Just");
        selectBox.addElement("Hello");
        selectBox.addElement("World");
        final int[] i = {1};
        selectBox.addElement("World" + i[0]++);
        selectBox.addElement("World" + i[0]++);
        selectBox.addElement("World" + i[0]++);
        selectBox.addElement("World" + i[0]++);
        selectBox.addElement("World" + i[0]++);
        selectBox.addElement("World" + i[0]++);
        selectBox.addElement("World" + i[0]++);
        selectBox.addElement("World" + i[0]++);
        selectBox.addElement("World" + i[0]++);
        selectBox.addElement("World" + i[0]++);
        selectBox.addElement("World" + i[0]++);
        selectBox.addElement("World" + i[0]++);
        selectBox.setVisibleCount(5);
        selectBox.setElementHeight(20);
        this.addComponent(selectBox);

        Button sbb = new Button(130, 260, 70, 20, "Add element");
        sbb.getLeguiEventListeners().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == CLICK) {
                selectBox.addElement("WorlD " + i[0]++);
            }
        });
        this.addComponent(sbb);

    }

    public TextArea getTextArea() {
        return textArea;
    }

    public TextInput getCaretp() {
        return caretp;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Label getMouseTargetLabel() {
        return mouseTargetLabel;
    }

    public Label getMouseLabel() {
        return mouseLabel;
    }

    public Label getUpsLabel() {
        return upsLabel;
    }

    public Label getFocusedGuiLabel() {
        return focusedGuiLabel;
    }
}
