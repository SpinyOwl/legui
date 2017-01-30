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

import static org.liquidengine.legui.component.optional.align.HorizontalAlign.CENTER;
import static org.liquidengine.legui.component.optional.align.HorizontalAlign.LEFT;
import static org.liquidengine.legui.component.optional.align.HorizontalAlign.RIGHT;
import static org.liquidengine.legui.component.optional.align.VerticalAlign.BOTTOM;
import static org.liquidengine.legui.component.optional.align.VerticalAlign.MIDDLE;
import static org.liquidengine.legui.component.optional.align.VerticalAlign.TOP;
import static org.liquidengine.legui.event.component.MouseClickEvent.MouseClickAction.*;


/**
 * Created by Shcherbin Alexander on 9/19/2016.
 */
public class ExampleGui extends Frame {
    private final Label     mouseTargetLabel;
    private final Label     mouseLabel;
    private final Label     upsLabel;
    private final Label     focusedGuiLabel;
    private final TextInput caretp;
    private final TextArea  textArea;
    private final TextInput textInput;
    private final Label     debugLabel;
    private       ImageView imageView;

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

        mouseTargetLabel = new Label("Hello Label", 10, height - 30, width - 20, 20);
        this.addComponent(mouseTargetLabel);

        focusedGuiLabel = new Label("Hello Label", 10, height - 50, width - 20, 20);
        focusedGuiLabel.setBorder(new SimpleRectangleLineBorder(ColorConstants.red(), 1));
        this.addComponent(focusedGuiLabel);

        debugLabel = new Label("Debug Label", 10, height - 75, width - 20, 20);
        this.addComponent(debugLabel);

        mouseLabel = new Label("Hello Label", 130, 30, 100, 20);
        this.addComponent(mouseLabel);

        upsLabel = new Label("Hello Label", 130, 60, 100, 20);
        this.addComponent(upsLabel);

        imageView = new ImageView(new Image("org/liquidengine/legui/example/11.jpg"));
        imageView.setPosition(20, 30);
        imageView.setSize(100, 100);
        this.addComponent(imageView);

        Button button = new Button(20, 170, 50, 20);
        button.setBackgroundColor(new Vector4f(1));
        this.addComponent(button);

        button.setTooltipText("Just button");
        button.getTooltip().setPosition(0, 25);
        button.getTooltip().getSize().set(50, 40);
        button.getTooltip().setBackgroundColor(ColorConstants.darkGray());
        button.getTooltip().getTextState().setTextColor(ColorConstants.white());
        button.getTooltip().getTextState().setPadding(4, 4, 4, 4);

        int idv[] = {0};
        button.getLeguiEventListeners().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction().equals(CLICK)) {
                idv[0]++;
                HorizontalAlign h = LEFT;
                VerticalAlign   v = TOP;
                int             hh = idv[0] % 3;
                int             vv = (idv[0] / 3) % 3;
                switch (hh){
                    case 0: h = LEFT; break;
                    case 1: h = CENTER; break;
                    case 2: h = RIGHT; break;
                }
                switch (vv){
                    case 0: v = TOP; break;
                    case 1: v = MIDDLE; break;
                    case 2: v = BOTTOM; break;
                }
                System.out.println(h + " " + v);
                button.getTooltip().getTextState().setHorizontalAlign(h);
                button.getTooltip().getTextState().setVerticalAlign(v);
            }
        });

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
        RadioButton      radioButton1     = new RadioButton(250, 30, 100, 20);
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

        textInput = new TextInput(250, 130, 100, 30);
        textInput.getTextState().setHorizontalAlign(RIGHT);
        textInput.getLeguiEventListeners().addListener(KeyboardKeyEvent.class, (KeyboardKeyEventListener) event -> {
            if (event.getKey() == GLFW.GLFW_KEY_F1 && event.getAction() == GLFW.GLFW_RELEASE)
                textInput.getTextState().setHorizontalAlign(LEFT);
            else if (event.getKey() == GLFW.GLFW_KEY_F2 && event.getAction() == GLFW.GLFW_RELEASE)
                textInput.getTextState().setHorizontalAlign(CENTER);
            else if (event.getKey() == GLFW.GLFW_KEY_F3 && event.getAction() == GLFW.GLFW_RELEASE)
                textInput.getTextState().setHorizontalAlign(RIGHT);
        });
        this.addComponent(textInput);

        Widget widget = new Widget("Hello widget", 250, 170, 100, 100);
        widget.setTitleHeight(20);
        widget.setTitleBackgroundColor(ColorConstants.lightGreen());

        Button turnWidVisible = new Button("", 360, 280, 20, 20);
        turnWidVisible.getLeguiEventListeners().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (CLICK.equals(event.getAction())) widget.setVisible(true);
        });
        ImageView bgIm  = new ImageView(new Image("org/liquidengine/legui/example/1.png"));
        ImageView hbgIm = new ImageView(new Image("org/liquidengine/legui/example/2.png"));
        ImageView pbIm  = new ImageView(new Image("org/liquidengine/legui/example/3.png"));
        bgIm.setSize(20, 20);
        hbgIm.setSize(20, 20);
        pbIm.setSize(20, 20);
        turnWidVisible.setBackgroundImage(bgIm);
        turnWidVisible.setHoveredBackgroundImage(hbgIm);
        turnWidVisible.setPressedBackgroundImage(pbIm);
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
        widget2.setCloseButtonColor(ColorConstants.white());
        widget2.setCloseButtonBackgroundColor(ColorConstants.black());
        widget2.setTitleBackgroundColor(ColorConstants.lightGreen());
        widget2.setDraggable(false);

        Button turnDraggable = new Button("Draggable", 10, 10, 80, 20);
        turnDraggable.getLeguiEventListeners().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (CLICK.equals(event.getAction())) widget2.setDraggable(!widget2.isDraggable());
        });
        widget2.getContainer().addComponent(turnDraggable);
        this.addComponent(widget2);

        Button turnWidVisible2 = new Button("", 360, 310, 20, 20);
        turnWidVisible2.getLeguiEventListeners().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (CLICK.equals(event.getAction())) widget2.setVisible(true);
        });
        this.addComponent(turnWidVisible2);

        Widget widget3 = new Widget("Hello 2 widget", 250, 420, 100, 100);
        widget3.setTitleHeight(20);
        widget3.setTitleEnabled(false);
        widget3.setTitleBackgroundColor(ColorConstants.lightGreen());
        widget3.setCloseable(true);
        widget3.setMinimizeable(false);
        this.addComponent(widget3);

        Button turnWidVisible3 = new Button("", 360, 340, 20, 20);
        turnWidVisible3.getLeguiEventListeners().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (CLICK.equals(event.getAction())) widget3.setVisible(true);
        });
        this.addComponent(turnWidVisible3);

        widget3.getContainer().addComponent(new Panel(5, 5, 20, 20));
        widget3.getContainer().addComponent(new Panel(30, 5, 20, 20));
        widget3.getContainer().addComponent(new Panel(30, 30, 20, 20));
        widget3.getContainer().addComponent(new Panel(5, 30, 20, 20));
        widget3.getContainer().addComponent(new Panel(5, 30, 20, 20));
        Button b = new Button(55, 5, 40, 45);
        b.getTextState().setFont(FontRegister.MATERIAL_ICONS_REGULAR);
        b.getTextState().setVerticalAlign(MIDDLE);
        b.getTextState().setHorizontalAlign(CENTER);
        b.getTextState().setFontSize(20);

        String up   = Util.cpToStr(0xE5D8);
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
        textArea.getTextState().setHorizontalAlign(CENTER);
        textArea.getTextState().setVerticalAlign(BOTTOM);
        this.addComponent(textArea);

        textArea.getLeguiEventListeners().addListener(KeyboardKeyEvent.class, (KeyboardKeyEventListener) event -> {
            if (event.getKey() == GLFW.GLFW_KEY_F1 && event.getAction() == GLFW.GLFW_RELEASE)
                textArea.getTextState().setHorizontalAlign(LEFT);
            else if (event.getKey() == GLFW.GLFW_KEY_F2 && event.getAction() == GLFW.GLFW_RELEASE)
                textArea.getTextState().setHorizontalAlign(CENTER);
            else if (event.getKey() == GLFW.GLFW_KEY_F3 && event.getAction() == GLFW.GLFW_RELEASE)
                textArea.getTextState().setHorizontalAlign(RIGHT);
            else if (event.getKey() == GLFW.GLFW_KEY_F5 && event.getAction() == GLFW.GLFW_RELEASE)
                textArea.getTextState().setVerticalAlign(TOP);
            else if (event.getKey() == GLFW.GLFW_KEY_F6 && event.getAction() == GLFW.GLFW_RELEASE)
                textArea.getTextState().setVerticalAlign(MIDDLE);
            else if (event.getKey() == GLFW.GLFW_KEY_F7 && event.getAction() == GLFW.GLFW_RELEASE)
                textArea.getTextState().setVerticalAlign(BOTTOM);
            else if (event.getKey() == GLFW.GLFW_KEY_F8 && event.getAction() == GLFW.GLFW_RELEASE)
                textArea.getTextState().setVerticalAlign(VerticalAlign.BASELINE);
        });

        caretp = new TextInput(420, 400, 150, 20);
        caretp.getTextState().setHorizontalAlign(CENTER);
        this.addComponent(caretp);

        TextInput inpur = new TextInput(420, 430, 50, 35);
        inpur.getTextState().setText("00");
        inpur.getTextState().setFontSize(35);
        inpur.getTextState().setHorizontalAlign(CENTER);
        inpur.getTextState().setVerticalAlign(MIDDLE);
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

        Button sbb = new Button("Add element", 130, 260, 70, 20);
        sbb.getLeguiEventListeners().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == CLICK) {
                selectBox.addElement("WorlD " + i[0]++);
            }
        });
        this.addComponent(sbb);

        ToggleButton toggleButton    = new ToggleButton(100, 170, 40, 40);
        this.addComponent(toggleButton);
        ImageView    bgImageNormal   = new ImageView(new Image("org/liquidengine/legui/example/normal.png"));
        ImageView    bgImageToggled  = new ImageView(new Image("org/liquidengine/legui/example/toggled.png"));

        toggleButton.setTooltipText("Just toggle button with long tooltipText text");
        toggleButton.getTooltip().setPosition(45, 0);
        toggleButton.getTooltip().getSize().set(140, 40);
        toggleButton.getTooltip().setBackgroundColor(ColorConstants.darkGray());
        toggleButton.getTooltip().getTextState().setTextColor(ColorConstants.white());
        toggleButton.getTooltip().getTextState().setPadding(4, 4, 4, 4);

        int id[] = {0};
        toggleButton.getLeguiEventListeners().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction().equals(CLICK)) {
                id[0]++;
                HorizontalAlign h = LEFT;
                VerticalAlign   v = TOP;
                int             hh = id[0] % 3;
                int             vv = (id[0] / 3) % 3;
                switch (hh){
                    case 0: h = LEFT; break;
                    case 1: h = CENTER; break;
                    case 2: h = RIGHT; break;
                }
                switch (vv){
                    case 0: v = TOP; break;
                    case 1: v = MIDDLE; break;
                    case 2: v = BOTTOM; break;
                }
                System.out.println(h + " " + v);
                toggleButton.getTooltip().getTextState().setHorizontalAlign(h);
                toggleButton.getTooltip().getTextState().setVerticalAlign(v);

            }
        });

        bgImageNormal.setSize(36, 36);
        bgImageToggled.setSize(36, 36);

        bgImageNormal.setPosition(2, 2);
        bgImageToggled.setPosition(2, 2);

        toggleButton.setBackgroundImage(bgImageNormal);
        toggleButton.setTogglededBackgroundImage(bgImageToggled);

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

    public TextInput getTextInput() {
        return textInput;
    }

    public Label getDebugLabel() {
        return debugLabel;
    }
}
