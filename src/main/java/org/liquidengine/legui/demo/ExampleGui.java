package org.liquidengine.legui.demo;

import static org.liquidengine.legui.component.optional.align.HorizontalAlign.CENTER;
import static org.liquidengine.legui.component.optional.align.HorizontalAlign.LEFT;
import static org.liquidengine.legui.component.optional.align.HorizontalAlign.RIGHT;
import static org.liquidengine.legui.component.optional.align.VerticalAlign.BASELINE;
import static org.liquidengine.legui.component.optional.align.VerticalAlign.BOTTOM;
import static org.liquidengine.legui.component.optional.align.VerticalAlign.MIDDLE;
import static org.liquidengine.legui.component.optional.align.VerticalAlign.TOP;
import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.CLICK;
import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.PRESS;
import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.RELEASE;
import static org.liquidengine.legui.input.Mouse.MouseButton.MOUSE_BUTTON_LEFT;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.animation.Animation;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.CheckBox;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Dialog;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.PasswordInput;
import org.liquidengine.legui.component.ProgressBar;
import org.liquidengine.legui.component.RadioButton;
import org.liquidengine.legui.component.RadioButtonGroup;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.component.ToggleButton;
import org.liquidengine.legui.component.Tooltip;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.component.event.slider.SliderChangeValueEvent;
import org.liquidengine.legui.component.event.slider.SliderChangeValueEventListener;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.event.CursorEnterEvent;
import org.liquidengine.legui.event.FocusEvent;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.icon.ImageIcon;
import org.liquidengine.legui.image.BufferedImage;
import org.liquidengine.legui.listener.CursorEnterEventListener;
import org.liquidengine.legui.listener.FocusEventListener;
import org.liquidengine.legui.listener.KeyEventListener;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.font.FontRegistry;
import org.liquidengine.legui.theme.Theme;
import org.liquidengine.legui.theme.Themes;
import org.liquidengine.legui.theme.dark.DarkTheme;
import org.liquidengine.legui.theme.white.WhiteTheme;
import org.liquidengine.legui.util.TextUtil;
import org.lwjgl.glfw.GLFW;


/**
 * Created by Shcherbin Alexander on 9/19/2016.
 */
public class ExampleGui extends Panel {
    private final Label mouseTargetLabel;
    private final Label mouseLabel;
    private final Label upsLabel;
    private final Label focusedGuiLabel;
    private final TextArea textArea;
    private final TextInput textInput;
    private final Label debugLabel;
    private ImageView imageView;

    public ExampleGui() {
        this(800, 600);
    }

    public ExampleGui(int width, int height) {
        super(0, 0, width, height);
        //@formatter:off
        Panel p1 = new Panel(1 * 20, 10, 10, 10); this.add(p1); p1.getListenerMap().addListener(FocusEvent.class, (FocusEventListener) System.out::println);
        Panel p2 = new Panel(2 * 20, 10, 10, 10); this.add(p2); p2.getListenerMap().addListener(FocusEvent.class, (FocusEventListener) System.out::println);
        Panel p3 = new Panel(3 * 20, 10, 10, 10); this.add(p3); p3.getListenerMap().addListener(FocusEvent.class, (FocusEventListener) System.out::println);
        Panel p4 = new Panel(4 * 20, 10, 10, 10); this.add(p4); p4.getListenerMap().addListener(FocusEvent.class, (FocusEventListener) System.out::println);
        Panel p5 = new Panel(5 * 20, 10, 10, 10); this.add(p5); p5.getListenerMap().addListener(FocusEvent.class, (FocusEventListener) System.out::println);
        Panel p6 = new Panel(6 * 20, 10, 10, 10); this.add(p6); p6.getListenerMap().addListener(FocusEvent.class, (FocusEventListener) System.out::println);
        Panel p7 = new Panel(7 * 20, 10, 10, 10); this.add(p7); p7.getListenerMap().addListener(FocusEvent.class, (FocusEventListener) System.out::println);
        Panel p8 = new Panel(8 * 20, 10, 10, 10); this.add(p8); p8.getListenerMap().addListener(FocusEvent.class, (FocusEventListener) System.out::println);
        Panel p9 = new Panel(9 * 20, 10, 10, 10); this.add(p9); p9.getListenerMap().addListener(FocusEvent.class, (FocusEventListener) System.out::println);

        mouseTargetLabel = new Label("Hello Label 1", 10, height - 30, width - 20, 20);
        this.add(mouseTargetLabel);

        focusedGuiLabel = new Label("Hello Label 2", 10, height - 50, width - 20, 20);
        focusedGuiLabel.getStyle().setBorder(new SimpleLineBorder(ColorConstants.red(), 1)); this.add(focusedGuiLabel);

        debugLabel = new Label("Debug Label", 10, height - 75, width - 20, 20); this.add(debugLabel);

        mouseLabel = new Label("Hello Label 3", 130, 30, 100, 20); this.add(mouseLabel);

        upsLabel = new Label("Hello Label 4", 130, 60, 100, 20); this.add(upsLabel);

        Widget imageWrapper = new Widget(20, 30, 100, 100);
        imageWrapper.setTitleEnabled(true);
        this.add(imageWrapper);

        imageView = new ImageView(new BufferedImage("org/liquidengine/legui/demo/1.jpg"));
        imageView.setPosition(15, 5); imageView.setSize(70, 70); imageView.getStyle().setCornerRadius(10);
        imageWrapper.getContainer().add(imageView);

        Button button = new Button(20, 170, 50, 20); /*button.getStyle().getBackground().setColor(new Vector4f(1));*/
        this.add(button);
        button.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) System.out::println);

        button.setTooltip(new Tooltip("Just button"));
        button.getTooltip().setPosition(0, 25);
        button.getTooltip().getSize().set(50, 60);
        button.getTooltip().getTextState().setPadding(4, 4, 4, 4);

        int idv[] = {0};
        button.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) (MouseClickEvent event) -> {
            if (event.getAction().equals(CLICK)) {
                idv[0]++;
                HorizontalAlign h;
                VerticalAlign v;
                int hh = idv[0] % 3;
                int vv = (idv[0] / 3) % 3;
                switch (hh) {
                    case 0: h = LEFT; break;
                    case 1: h = CENTER; break;
                    case 2:
                    default: h = RIGHT; break;
                }
                switch (vv) {
                    case 0: v = TOP; break;
                    case 1: v = MIDDLE; break;
                    case 2:
                    default: v = BOTTOM; break;
                }
                System.out.println(h + " " + v);
                button.getTooltip().getTextState().setHorizontalAlign(h);
                button.getTooltip().getTextState().setVerticalAlign(v);
            }
        });

        CheckBox checkBox1 = new CheckBox(20, 200, 50, 20); this.add(checkBox1);
        CheckBox checkBox2 = new CheckBox(20, 230, 50, 20); checkBox2.getStyle().getBackground().setColor(new Vector4f(1, 0, 0, 1)); checkBox2.setChecked(true); this.add(checkBox2);

        checkBox2.getListenerMap().addListener(CursorEnterEvent.class, (CursorEnterEventListener) event -> {
            boolean entered = event.isEntered();
            Vector4f newColor = ColorConstants.green();
            if (entered) {
              createColorAnimationOnHover(event.getTargetComponent(), newColor, checkBox2).startAnimation();
            }
        });


        checkBox2.getListenerMap().addListener(CursorEnterEvent.class, (CursorEnterEventListener) event -> {
            boolean entered = event.isEntered();
            Vector4f newColor = ColorConstants.red();
            if (entered) {
              createColorAnimationOnHover(event.getTargetComponent(), newColor, checkBox1).startAnimation();
            }
        });

        ProgressBar progressBar = new ProgressBar(250, 10, 100, 10); progressBar.setValue(50); this.add(progressBar);

        RadioButtonGroup radioButtonGroup = new RadioButtonGroup();
        RadioButton radioButton1 = new RadioButton(250, 30, 100, 20); this.add(radioButton1);
        radioButton1.setChecked(true); radioButton1.setRadioButtonGroup(radioButtonGroup);
        RadioButton radioButton2 = new RadioButton(250, 60, 100, 20); this.add(radioButton2);
        radioButton2.setChecked(false); radioButton2.setRadioButtonGroup(radioButtonGroup);

        Slider slider1 = new Slider(250, 90, 100, 20, 30); this.add(slider1);

        Slider slider2 = new Slider(220, 90, 20, 100, 50); slider2.setOrientation(Orientation.VERTICAL); this.add(slider2);

        textInput = new TextInput(250, 130, 100, 30);
        textInput.getTextState().setHorizontalAlign(RIGHT);
        textInput.getListenerMap().addListener(KeyEvent.class, (KeyEventListener) event -> {
            if (event.getKey() == GLFW.GLFW_KEY_F1 && event.getAction() == GLFW.GLFW_RELEASE) {
                textInput.getTextState().setHorizontalAlign(LEFT);
            } else if (event.getKey() == GLFW.GLFW_KEY_F2 && event.getAction() == GLFW.GLFW_RELEASE) {
                textInput.getTextState().setHorizontalAlign(CENTER);
            } else if (event.getKey() == GLFW.GLFW_KEY_F3 && event.getAction() == GLFW.GLFW_RELEASE) {
                textInput.getTextState().setHorizontalAlign(RIGHT);
            }
        });
        this.add(textInput);

        Widget widget = new Widget("Hello widget", 250, 170, 100, 100);
        widget.setTitleHeight(20);
        widget.getTitleContainer().getStyle().getBackground().setColor(ColorConstants.lightGreen());
        widget.getTitleTextState().setTextColor(ColorConstants.black());

        Button turnWidVisible = new Button("", 360, 280, 20, 20);
        turnWidVisible.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (CLICK == (event.getAction())) {
                widget.setVisible(true);
            }
        });
        Icon bgIm = new ImageIcon(new BufferedImage("org/liquidengine/legui/demo/1.png")); bgIm.setSize(new Vector2f(20, 20)); turnWidVisible.setBackgroundIcon(bgIm);
        Icon hbgIm = new ImageIcon(new BufferedImage("org/liquidengine/legui/demo/2.png")); hbgIm.setSize(new Vector2f(20, 20)); turnWidVisible.setHoveredBackgroundIcon(hbgIm);
        Icon pbIm = new ImageIcon(new BufferedImage("org/liquidengine/legui/demo/3.png")); pbIm.setSize(new Vector2f(20, 20)); turnWidVisible.setPressedBackgroundIcon(pbIm);

        this.add(turnWidVisible);

        Panel c0 = new Panel(-5, -5, 10, 10); c0.getStyle().getBackground().setColor(1, 0, 0, 1); widget.getContainer().add(c0);
        Panel c1 = new Panel(-5, 75, 10, 10); c1.getStyle().getBackground().setColor(1, 0, 0, 1); widget.getContainer().add(c1);
        Panel c2 = new Panel(95, -5, 10, 10); c2.getStyle().getBackground().setColor(1, 0, 0, 1); widget.getContainer().add(c2); c2.setVisible(false);
        Panel c3 = new Panel(95, 75, 10, 10); c3.getStyle().getBackground().setColor(1, 0, 0, 1); widget.getContainer().add(c3);
        Panel c4 = new Panel(45, 35, 10, 10); c4.getStyle().getBackground().setColor(1, 0, 0, 1); widget.getContainer().add(c4);
        this.add(widget);

        Widget widget2 = new Widget("Hello 2 widget", 250, 310, 100, 100);
        widget2.setTitleHeight(20);
        widget2.setCloseButtonColor(ColorConstants.white());
        widget2.getCloseButton().getStyle().getBackground().setColor(ColorConstants.black());
        widget2.getTitleContainer().getStyle().getBackground().setColor(ColorConstants.lightGreen());
        widget2.getTitleTextState().setTextColor(ColorConstants.black());
        widget2.setDraggable(false);
        widget2.setMinimizable(true);

        Button turnDraggable = new Button("Draggable", 10, 10, 80, 20);
        turnDraggable.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == CLICK) {
                Dialog dialog = new Dialog("Question:", 300, 100);

                Label questionLabel = new Label("Are you sure want to turn " + (widget2.isDraggable() ? "off" : "on") + "this widget draggable?", 10, 10, 200, 20);
                Button yesButton = new Button("Yes", 10, 50, 50, 20);
                Button noButton = new Button("No", 70, 50, 50, 20);
                yesButton.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) e -> {
                    if (CLICK == e.getAction()) {
                        widget2.setDraggable(!widget2.isDraggable());
                        dialog.close();
                    }
                });
                noButton.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) e -> {
                    if (CLICK == e.getAction()) {
                        dialog.close();
                    }
                });

                dialog.getContainer().add(questionLabel);
                dialog.getContainer().add(yesButton);
                dialog.getContainer().add(noButton);

                dialog.show(event.getFrame());
            }
        });
        widget2.getContainer().add(turnDraggable);
        this.add(widget2);

        Button turnWidVisible2 = new Button("", 360, 310, 20, 20);
        turnWidVisible2.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (CLICK == event.getAction()) {
                widget2.setVisible(true);
            }
        });
        this.add(turnWidVisible2);

        Widget widget3 = new Widget("Hello 2 widget", 250, 420, 100, 100);
        widget3.setTitleHeight(20);
        widget3.setTitleEnabled(false);
        widget3.getTitleContainer().getStyle().getBackground().setColor(ColorConstants.lightGreen());
        widget3.setCloseable(false);
        widget3.setMinimizable(true);
        widget3.getTitleTextState().setTextColor(ColorConstants.black());
        this.add(widget3);

        Button turnWidVisible3 = new Button("", 360, 340, 20, 20);
        turnWidVisible3.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (CLICK == event.getAction()) {
                widget3.setVisible(true);
            }
        });
        this.add(turnWidVisible3);

        widget3.getContainer().add(new Panel(5, 5, 20, 20));
        widget3.getContainer().add(new Panel(30, 5, 20, 20));
        widget3.getContainer().add(new Panel(30, 30, 20, 20));
        widget3.getContainer().add(new Panel(5, 30, 20, 20));
        widget3.getContainer().add(new Panel(5, 30, 20, 20));
        Button b = new Button(55, 5, 40, 45);
        b.getTextState().setFont(FontRegistry.MATERIAL_ICONS_REGULAR);
        b.getTextState().setVerticalAlign(MIDDLE);
        b.getTextState().setHorizontalAlign(CENTER);
        b.getTextState().setFontSize(20);

        String up = TextUtil.cpToStr(0xE5D8);
        String down = TextUtil.cpToStr(0xE5DB);
        b.getTextState().setText(down);
        b.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == CLICK) {
                widget3.setTitleEnabled(!widget3.isTitleEnabled());
                b.getTextState().setText(widget3.isTitleEnabled() ? up : down);
            }
        });
        widget3.getContainer().add(b);

        ScrollBar scrollBar1 = new ScrollBar(360, 170, 20, 100, 20);
        scrollBar1.setOrientation(Orientation.VERTICAL);
        scrollBar1.setVisibleAmount(20);
        scrollBar1.setArrowsEnabled(true);
        scrollBar1.getStyle().getBackground().setColor(ColorConstants.white());
        scrollBar1.setScrollColor(ColorConstants.darkGray());
        scrollBar1.setArrowColor(ColorConstants.darkGray());
        scrollBar1.getStyle().setBorder(new SimpleLineBorder(ColorConstants.red(), 1));
        this.add(scrollBar1);

        ScrollBar scrollBar11 = new ScrollBar(385, 170, 7, 100, 20);
        scrollBar11.setOrientation(Orientation.VERTICAL);
        scrollBar11.setVisibleAmount(20);
        scrollBar11.setArrowsEnabled(false);
        scrollBar11.getStyle().getBackground().setColor(ColorConstants.white());
        scrollBar11.setScrollColor(ColorConstants.darkGray());
        scrollBar11.getStyle().setBorder(new SimpleLineBorder(ColorConstants.red(), 1));
        scrollBar11.getStyle().setCornerRadius(3);
        this.add(scrollBar11);

        ScrollBar scrollBar2 = new ScrollBar(250, 280, 100, 20, 20);
        scrollBar2.setOrientation(Orientation.HORIZONTAL);
        scrollBar2.setVisibleAmount(20);
        scrollBar2.setArrowsEnabled(true);
        scrollBar2.getStyle().setBorder(new SimpleLineBorder(ColorConstants.black(), 1));
        scrollBar2.getStyle().getBackground().setColor(ColorConstants.darkGray());
        scrollBar2.setScrollColor(ColorConstants.white());
        scrollBar2.setArrowColor(ColorConstants.white());
        this.add(scrollBar2);

        Panel panel1 = new Panel(420, 170, 100, 100); panel1.getStyle().getBackground().setColor(ColorConstants.blue()); this.add(panel1);
        Panel panel2 = new Panel(470, 170, 100, 100); panel2.getStyle().getBackground().setColor(ColorConstants.green()); this.add(panel2);

        button.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            MouseClickEvent.MouseClickAction action = event.getAction();
            if (CLICK == action) mouseTargetLabel.setVisible(!mouseTargetLabel.isVisible());
            if (RELEASE == action) System.out.println("RELEASE");
            if (PRESS == action) System.out.println("PRESS");
        });

        ScrollablePanel scrollablePanel = new ScrollablePanel(420, 10, 250, 150);
        scrollablePanel.getStyle().getBackground().setColor(1, 1, 1, 1);
        scrollablePanel.getContainer().setSize(300, 200);
        scrollablePanel.resize();

        ScrollablePanel scp = new ScrollablePanel(10, 10, 150, 100);
        scp.getContainer().setSize(300, 300);
        scp.resize();

        scp.getContainer().add(new TextInput("Hello Scrollable", 10, 10, 150, 20));

        scrollablePanel.getContainer().add(scp);
        this.add(scrollablePanel);

        slider2.getListenerMap().addListener(SliderChangeValueEvent.class, (SliderChangeValueEventListener) event -> {
            scrollablePanel.getHorizontalScrollBar().getSize().y = event.getNewValue() / 2f + 10;
            scrollablePanel.resize();
        });
        slider1.getListenerMap().addListener(SliderChangeValueEvent.class, (SliderChangeValueEventListener) event -> {
            scrollablePanel.getHorizontalScrollBar().setArrowSize(event.getNewValue() / 4f + 10);
            scrollablePanel.resize();
        });

        textArea = new TextArea(420, 280, 150, 100);
        textArea.getTextState().setText("ABC DEF GH\r\nI JKL MNO PQR\nSTU VWXYZ");
        textArea.setCaretPosition(12);
        textArea.getTextState().setHorizontalAlign(CENTER);
        textArea.getTextState().setVerticalAlign(BOTTOM);
        this.add(textArea);

        textArea.getListenerMap().addListener(KeyEvent.class, (KeyEventListener) event -> {
            if (event.getKey() == GLFW.GLFW_KEY_F1 && event.getAction() == GLFW.GLFW_RELEASE) {
                textArea.getTextState().setHorizontalAlign(LEFT);
            } else if (event.getKey() == GLFW.GLFW_KEY_F2 && event.getAction() == GLFW.GLFW_RELEASE) {
                textArea.getTextState().setHorizontalAlign(CENTER);
            } else if (event.getKey() == GLFW.GLFW_KEY_F3 && event.getAction() == GLFW.GLFW_RELEASE) {
                textArea.getTextState().setHorizontalAlign(RIGHT);
            } else if (event.getKey() == GLFW.GLFW_KEY_F5 && event.getAction() == GLFW.GLFW_RELEASE) {
                textArea.getTextState().setVerticalAlign(TOP);
            } else if (event.getKey() == GLFW.GLFW_KEY_F6 && event.getAction() == GLFW.GLFW_RELEASE) {
                textArea.getTextState().setVerticalAlign(MIDDLE);
            } else if (event.getKey() == GLFW.GLFW_KEY_F7 && event.getAction() == GLFW.GLFW_RELEASE) {
                textArea.getTextState().setVerticalAlign(BOTTOM);
            } else if (event.getKey() == GLFW.GLFW_KEY_F8 && event.getAction() == GLFW.GLFW_RELEASE) {
                textArea.getTextState().setVerticalAlign(BASELINE);
            }
        });

        Label passLabel = new Label("Password:", 420, 390, 150, 15);
        this.add(passLabel);
        PasswordInput caretp = new PasswordInput(420, 405, 150, 20);
        caretp.getTextState().setHorizontalAlign(CENTER);
        this.add(caretp);

        TextInput inpur = new TextInput(420, 430, 50, 35);
        inpur.getTextState().setText("00");
        inpur.getTextState().setFontSize(35);
        inpur.getTextState().setHorizontalAlign(CENTER);
        inpur.getTextState().setVerticalAlign(MIDDLE);
        inpur.getStyle().getBackground().setColor(ColorConstants.white());
        this.add(inpur);

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
        this.add(selectBox);

        Button sbb = new Button("Add element", 130, 260, 70, 20);
        sbb.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == CLICK) {
                selectBox.addElement("WorlD " + i[0]++);
            }
        });
        this.add(sbb);

        ToggleButton toggleButton = new ToggleButton("", 100, 170, 40, 20);
        this.add(toggleButton);
        Icon bgImageNormal = new ImageIcon(new BufferedImage("org/liquidengine/legui/demo/toggle.png"));

        toggleButton.getListenerMap().addListener(CursorEnterEvent.class, (CursorEnterEventListener) System.out::println);

        toggleButton.setTooltip(new Tooltip("Just toggle button with long tooltipText text"));
        toggleButton.getListenerMap().addListener(CursorEnterEvent.class, (CursorEnterEventListener) event -> {
            if (event.isEntered()) {
               getColorAnimation(toggleButton, ColorConstants.blue()).startAnimation();
            } else {
                getColorAnimation(toggleButton, ColorConstants.red()).startAnimation();
            }
        });
        toggleButton.getListenerMap().addListener(MouseClickEvent.class,
            (MouseClickEventListener) event -> getSlideImageOnClick(toggleButton, bgImageNormal).startAnimation());

        toggleButton.getTooltip().setPosition(45, 0);
        toggleButton.getTooltip().getSize().set(140, 40);
        toggleButton.getTooltip().getStyle().getBackground().setColor(ColorConstants.darkGray());
        toggleButton.getTooltip().getTextState().setTextColor(ColorConstants.white());
        toggleButton.getTooltip().getTextState().setPadding(4, 4, 4, 4);

        int id[] = {0};
        toggleButton.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction().equals(CLICK)) {
                id[0]++;
                HorizontalAlign h = LEFT;
                VerticalAlign v = TOP;
                int hh = id[0] % 3;
                int vv = (id[0] / 3) % 3;
                switch (hh) {
                    case 0: h = LEFT; break;
                    case 1: h = CENTER; break;
                    case 2: h = RIGHT; break;
                }
                switch (vv) {
                    case 0: v = TOP; break;
                    case 1: v = MIDDLE; break;
                    case 2: v = BOTTOM; break;
                }
                System.out.println(h + " " + v);
                toggleButton.getTooltip().getTextState().setHorizontalAlign(h);
                toggleButton.getTooltip().getTextState().setVerticalAlign(v);

            }
        });


        bgImageNormal.setSize(new Vector2f(100 * 40 / 60, 20));
        bgImageNormal.setPosition(new Vector2f(40 - 100 * 40 / 60, 0));
        toggleButton.setBackgroundIcon(bgImageNormal);
//        bgImageToggled.setSize(new Vector2f(36, 36));
//        toggleButton.setTogglededBackgroundIcon(bgImageToggled);
        //@formatter:on

        radioButton1.setRadioButtonGroup(radioButtonGroup);
        radioButton2.setRadioButtonGroup(radioButtonGroup);

        final Theme[] current = {Themes.getDefaultTheme()};
        DarkTheme darkTheme = new DarkTheme();
        WhiteTheme defaultTheme = new WhiteTheme();

        String text = "Switch theme to ";
        String dark = "dark";
        String light = "light";
        Button switchTheme = new Button(text + dark, 600, 400, 120, 30);
        switchTheme.getListenerMap().addListener(MouseClickEvent.class,
                switchThemeClickListener(current, darkTheme, defaultTheme, text, dark, light, switchTheme));
        this.add(switchTheme);
    }

    private Animation createColorAnimationOnHover(Component component, Vector4f newColor, Component targetComponent) {
        return new Animation() {
            private Vector4f initialColor;
            private Vector4f colorRange;
            private double time;

            @Override
            protected void beforeAnimation() {
                initialColor = new Vector4f(targetComponent.getStyle().getBackground().getColor());
                colorRange = newColor.sub(initialColor);
            }

            @Override
            protected boolean animate(double delta) {
                time += delta;
                targetComponent.getStyle().getBackground().getColor().set(new Vector4f(initialColor)
                                                    .add(new Vector4f(colorRange)
                                                                 .mul((float) Math.abs(Math.sin(time * 2)))));
                return !component.isHovered();
            }

            @Override
            protected void afterAnimation() {
                targetComponent.getStyle().getBackground().getColor().set(initialColor);
            }
        };
    }

    private MouseClickEventListener switchThemeClickListener(Theme[] current, DarkTheme darkTheme, WhiteTheme defaultTheme, String text,
                                                             String dark, String light, Button switchTheme) {
        return event -> {
            if (event.getAction().equals(CLICK) && event.getButton().equals(MOUSE_BUTTON_LEFT)) {
                if (current[0] instanceof DarkTheme) {
                    current[0] = defaultTheme;
                    switchTheme.getTextState().setText(text + dark);
                } else {
                    current[0] = darkTheme;
                    switchTheme.getTextState().setText(text + light);
                }
                Themes.setDefaultTheme(current[0]);
                Themes.getDefaultTheme().applyAll(event.getFrame());
            }
        };
    }

    private Animation getSlideImageOnClick(ToggleButton toggleButton, Icon bgImageNormal) {
        return new Animation() {
            private float initialPosition;
            private double time;
            private double spentTime;
            private float endPosition;

            @Override
            protected void beforeAnimation() {
                time = 0.5d;
                spentTime = 0;
                initialPosition = bgImageNormal.getPosition().x;

                if (toggleButton.isToggled()) {
                    endPosition = 0;
                } else {
                    endPosition = toggleButton.getSize().x - bgImageNormal.getSize().x;
                }
            }

            @Override
            protected boolean animate(double delta) {
                spentTime += delta;
                float percentage = (float) (spentTime / time);

                bgImageNormal.getPosition().x = initialPosition + (endPosition - initialPosition) * percentage;
                return spentTime >= time;
            }

            @Override
            protected void afterAnimation() {
                bgImageNormal.getPosition().x = endPosition;
            }
        };
    }

    private Animation getColorAnimation(ToggleButton toggleButton, Vector4f targetColor) {
        return new Animation() {
            private Vector4f baseColor;
            private Vector4f endColor;
            private Vector4f colorVector;
            private double time;
            private double spentTime;

            @Override
            protected void beforeAnimation() {
                time = 1.5d;
                spentTime = 0;
                baseColor = new Vector4f(toggleButton.getStyle().getBackground().getColor());
                endColor = targetColor;
                colorVector = new Vector4f(endColor).sub(baseColor);
            }

            @Override
            protected boolean animate(double delta) {
                spentTime += delta;
                float percentage = (float) (spentTime / time);

                Vector4f newColor = new Vector4f(baseColor).add(new Vector4f(colorVector).mul(percentage));
                toggleButton.getStyle().getBackground().setColor(newColor);
                return spentTime >= time;
            }

            @Override
            protected void afterAnimation() {
                toggleButton.getStyle().getBackground().setColor(endColor);
            }
        };
    }

    public TextArea getTextArea() {
        return textArea;
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
