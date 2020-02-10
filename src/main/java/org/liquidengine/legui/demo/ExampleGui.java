package org.liquidengine.legui.demo;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.animation.Animation;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.component.event.component.ChangeSizeEvent;
import org.liquidengine.legui.component.event.selectbox.SelectBoxChangeSelectionEventListener;
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
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.Style.DisplayType;
import org.liquidengine.legui.style.Style.PositionType;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.font.FontRegistry;
import org.liquidengine.legui.style.shadow.Shadow;
import org.liquidengine.legui.theme.Theme;
import org.liquidengine.legui.theme.Themes;
import org.liquidengine.legui.util.TextUtil;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.atomic.AtomicInteger;

import static org.liquidengine.legui.component.optional.align.HorizontalAlign.*;
import static org.liquidengine.legui.component.optional.align.VerticalAlign.*;
import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.*;
import static org.liquidengine.legui.input.Mouse.MouseButton.MOUSE_BUTTON_LEFT;


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
    private CheckBox generateEventsByLayoutManager;

    public ExampleGui() {
        this(800, 600);
    }

    public ExampleGui(int width, int height) {
        super(0, 0, width, height);
        //@formatter:off
        Panel p1 = new Panel(1 * 20, 10, 10, 10);
        this.add(p1);
        p1.getListenerMap().addListener(FocusEvent.class, (FocusEventListener) System.out::println);
        Panel p2 = new Panel(2 * 20, 10, 10, 10);
        this.add(p2);
        p2.getListenerMap().addListener(FocusEvent.class, (FocusEventListener) System.out::println);
        Panel p3 = new Panel(3 * 20, 10, 10, 10);
        this.add(p3);
        p3.getListenerMap().addListener(FocusEvent.class, (FocusEventListener) System.out::println);
        Panel p4 = new Panel(4 * 20, 10, 10, 10);
        this.add(p4);
        p4.getListenerMap().addListener(FocusEvent.class, (FocusEventListener) System.out::println);
        Panel p5 = new Panel(5 * 20, 10, 10, 10);
        this.add(p5);
        p5.getListenerMap().addListener(FocusEvent.class, (FocusEventListener) System.out::println);
        Panel p6 = new Panel(6 * 20, 10, 10, 10);
        this.add(p6);
        p6.getListenerMap().addListener(FocusEvent.class, (FocusEventListener) System.out::println);
        Panel p7 = new Panel(7 * 20, 10, 10, 10);
        this.add(p7);
        p7.getListenerMap().addListener(FocusEvent.class, (FocusEventListener) System.out::println);
        Panel p8 = new Panel(8 * 20, 10, 10, 10);
        this.add(p8);
        p8.getListenerMap().addListener(FocusEvent.class, (FocusEventListener) System.out::println);
        Panel p9 = new Panel(9 * 20, 10, 10, 10);
        this.add(p9);
        p9.getListenerMap().addListener(FocusEvent.class, (FocusEventListener) System.out::println);

        this.add(mouseTargetLabel = new Label("Hello Label 1", 10, height - 30, width - 20, 20));

        focusedGuiLabel = new Label("Hello Label 2", 10, height - 50, width - 20, 20);
        focusedGuiLabel.getStyle().setBorder(new SimpleLineBorder(ColorConstants.red(), 1));
        this.add(focusedGuiLabel);

        this.add(debugLabel = new Label("Debug Label", 10, height - 75, width - 20, 20));
        this.add(mouseLabel = new Label("Hello Label 3", 130, 30, 100, 20));
        this.add(upsLabel = new Label("Hello Label 4", 130, 60, 100, 20));

        this.add(createImageWrapperWidgetWithImage());
        this.add(createButtonWithTooltip());

        generateEventsByLayoutManager = new CheckBox("Generate events by layout manager", 20, 200, 200, 20);
        this.add(generateEventsByLayoutManager);
        this.add(createCheckboxWithAnimation(generateEventsByLayoutManager));

        ProgressBar progressBar = new ProgressBar(250, 10, 100, 10);
        progressBar.setValue(50);
        this.add(progressBar);

        {
            RadioButtonGroup radioButtonGroup = new RadioButtonGroup();

            RadioButton radioButton1 = new RadioButton(250, 30, 100, 20);
            RadioButton radioButton2 = new RadioButton(250, 60, 100, 20);

            radioButton1.setChecked(true);
            radioButton2.setChecked(false);

            radioButton1.setRadioButtonGroup(radioButtonGroup);
            radioButton2.setRadioButtonGroup(radioButtonGroup);

            this.add(radioButton1);
            this.add(radioButton2);
        }

        Slider slider1 = new Slider(250, 90, 100, 20);
        slider1.setMinValue(-1f);
        slider1.setMaxValue(1f);
        slider1.setStepSize(.1f);
        slider1.setValue(0f);
        final Tooltip slider1Tooltip = new Tooltip();
        slider1Tooltip.setSize(100, 20);
        slider1Tooltip.setPosition(slider1.getSize().x + 2, 0);
        slider1Tooltip.getTextState().setText("Value: " + String.format("%.2f", slider1.getValue()));
        slider1.addSliderChangeValueEventListener((SliderChangeValueEventListener) event -> {
            slider1Tooltip.getTextState().setText(String.format("Value: %.2f", event.getNewValue()));
            slider1Tooltip.setSize(100, 20);
        });
        slider1.setTooltip(slider1Tooltip);
        this.add(slider1);

        Slider slider2 = new Slider(220, 90, 20, 100, 50f);
        slider2.setOrientation(Orientation.VERTICAL);
        this.add(slider2);

        textInput = new TextInput(250, 130, 100, 30);
        textInput.getStyle().setHorizontalAlign(RIGHT);
        textInput.getListenerMap().addListener(KeyEvent.class, (KeyEventListener) event -> {
            if (event.getKey() == GLFW.GLFW_KEY_F1 && event.getAction() == GLFW.GLFW_RELEASE) {
                textInput.getStyle().setHorizontalAlign(LEFT);
            } else if (event.getKey() == GLFW.GLFW_KEY_F2 && event.getAction() == GLFW.GLFW_RELEASE) {
                textInput.getStyle().setHorizontalAlign(CENTER);
            } else if (event.getKey() == GLFW.GLFW_KEY_F3 && event.getAction() == GLFW.GLFW_RELEASE) {
                textInput.getStyle().setHorizontalAlign(RIGHT);
            }
        });
        this.add(textInput);

        Widget widget = new Widget("Hello widget", 250, 170, 100, 100);
        widget.setTitleHeight(20);
        widget.getTitleContainer().getStyle().getBackground().setColor(ColorConstants.lightGreen());
        widget.getStyle().setTextColor(ColorConstants.black());

        String innerText = "Inner Widget; Resize events: ";
        Widget inner = new Widget(innerText + 0);
        inner.setResizable(false);
        inner.getStyle().setPosition(PositionType.RELATIVE);
        inner.getStyle().getFlexStyle().setFlexGrow(1);
        inner.getStyle().setMargin(10f);
        inner.getContainer().getStyle().getBackground().setColor(ColorConstants.lightGreen());
        widget.getContainer().getStyle().setDisplay(DisplayType.FLEX);
        widget.getContainer().add(inner);

        AtomicInteger counter = new AtomicInteger();
        inner.getListenerMap().addListener(ChangeSizeEvent.class, e -> {
            counter.getAndIncrement();
            inner.getTitle().getTextState().setText(innerText + "; Resize events: " + counter.get());
        });

        this.add(widget);

        Button turnWidVisible = new Button("", 360, 280, 20, 20);
        turnWidVisible.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (CLICK == (event.getAction())) {
                widget.show();
            }
        });
        Icon bgIm = new ImageIcon(new BufferedImage("org/liquidengine/legui/demo/1.png"));
        bgIm.setSize(new Vector2f(20, 20));
        turnWidVisible.getStyle().getBackground().setIcon(bgIm);
        Icon hbgIm = new ImageIcon(new BufferedImage("org/liquidengine/legui/demo/2.png"));
        hbgIm.setSize(new Vector2f(20, 20));
//        turnWidVisible.setHoveredBackgroundIcon(hbgIm);
        Icon pbIm = new ImageIcon(new BufferedImage("org/liquidengine/legui/demo/3.png"));
        pbIm.setSize(new Vector2f(20, 20));
//        turnWidVisible.setPressedBackgroundIcon(pbIm);

        this.add(turnWidVisible);

        Widget widget2 = new Widget("Hello 2 widget", 250, 310, 100, 100);
        widget2.setTitleHeight(20);
        widget2.setCloseButtonColor(ColorConstants.white());
        widget2.getCloseButton().getStyle().getBackground().setColor(ColorConstants.black());
        widget2.getTitleContainer().getStyle().getBackground().setColor(ColorConstants.lightGreen());
        widget2.getStyle().setTextColor(ColorConstants.black());
        widget2.setDraggable(false);
        widget2.setMinimizable(true);

        Button turnDraggable = new Button("Draggable", 10, 10, 80, 20);
        turnDraggable.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == CLICK) {
                Dialog dialog = new Dialog("Question:", 300, 100);

                Label questionLabel = new Label("Are you sure want to turn " + (widget2.isDraggable() ? "off" : "on") + "this widget draggable?", 10, 10, 200,
                                                20);
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
                widget2.show();
            }
        });
        this.add(turnWidVisible2);

        Widget widget3 = new Widget("Hello 2 widget", 250, 420, 100, 100);
        widget3.setTitleHeight(20);
        widget3.setTitleEnabled(false);
        widget3.getTitleContainer().getStyle().getBackground().setColor(ColorConstants.lightGreen());
        widget3.setCloseable(false);
        widget3.setMinimizable(true);
        widget3.getStyle().setTextColor(ColorConstants.black());
        this.add(widget3);

        Button turnWidVisible3 = new Button("", 360, 340, 20, 20);
        turnWidVisible3.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (CLICK == event.getAction()) {
                widget3.show();
            }
        });
        this.add(turnWidVisible3);

        widget3.getContainer().add(new Panel(5, 5, 20, 20));
        widget3.getContainer().add(new Panel(30, 5, 20, 20));
        widget3.getContainer().add(new Panel(30, 30, 20, 20));
        widget3.getContainer().add(new Panel(5, 30, 20, 20));
        Button b = new Button(55, 5, 40, 20);
        b.getStyle().setFont(FontRegistry.MATERIAL_ICONS_REGULAR);
        b.getStyle().setVerticalAlign(MIDDLE);
        b.getStyle().setHorizontalAlign(CENTER);
        b.getStyle().setFontSize(20f);

        String up = TextUtil.cpToStr(0xE5D8);
        String down = TextUtil.cpToStr(0xE5DB);
        b.getTextState().setText(down);
        b.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == CLICK) {
                widget3.setTitleEnabled(!widget3.isTitleEnabled());
                b.getTextState().setText(widget3.isTitleEnabled() ? up : down);
//                widget3.setResizable(!widget3.isResizable());
            }
        });
        widget3.getContainer().add(b);

        Button b2 = new Button(55, 30, 40, 20);
        b2.getStyle().setVerticalAlign(MIDDLE);
        b2.getStyle().setHorizontalAlign(CENTER);
        b2.getStyle().setFontSize(20f);

        String up2 = "-";
        String down2 = "+";
        b2.getTextState().setText(down2);
        b2.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == CLICK) {
                widget3.setCloseable(!widget3.isCloseable());
                b2.getTextState().setText(widget3.isCloseable() ? up2 : down2);
            }
        });
        widget3.getContainer().add(b2);

        widget3.getStyle().setMinWidth(100f);
        widget3.getStyle().setMinHeight(50f);
        widget3.getStyle().setMaxWidth(400f);
        widget3.getStyle().setMaxHeight(150f);

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
        scrollBar11.getStyle().setBorder(new SimpleLineBorder(ColorConstants.darkGray(), 1));
        scrollBar11.getStyle().setBorderRadius(null);
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

        Panel panel1 = new Panel(420, 170, 100, 100);
        panel1.getStyle().getBackground().setColor(ColorConstants.blue());
        this.add(panel1);
        Panel panel2 = new Panel(470, 170, 100, 100);
        panel2.getStyle().getBackground().setColor(ColorConstants.green());
        this.add(panel2);

        createButtonWithTooltip().getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            MouseClickEvent.MouseClickAction action = event.getAction();
            if (CLICK == action) {
                mouseTargetLabel.getStyle().setDisplay(mouseTargetLabel.isVisible() ? Style.DisplayType.NONE : Style.DisplayType.MANUAL);
            }
            if (RELEASE == action) {
                System.out.println("RELEASE");
            }
            if (PRESS == action) {
                System.out.println("PRESS");
            }
        });

        ScrollablePanel scrollablePanel = new ScrollablePanel(420, 10, 250, 150);
        scrollablePanel.getStyle().getBackground().setColor(1, 1, 1, 1);
        scrollablePanel.getContainer().setSize(300, 200);

        ScrollablePanel scp = new ScrollablePanel(10, 10, 150, 100);
        scp.getContainer().setSize(300, 300);

        scp.getContainer().add(new TextInput("Hello Scrollable", 10, 10, 150, 20));

        scrollablePanel.getContainer().add(scp);
        this.add(scrollablePanel);

        slider2.getListenerMap().addListener(SliderChangeValueEvent.class,
                                             (SliderChangeValueEventListener) event -> scrollablePanel
                                                 .setHorizontalScrollBarHeight(event.getNewValue() / 2f + 10)
                                            );
        slider1.getListenerMap().addListener(SliderChangeValueEvent.class,
                                             (SliderChangeValueEventListener) event -> scrollablePanel.getHorizontalScrollBar()
                                                 .setArrowSize(event.getNewValue() / 4f + 10)
                                            );

        textArea = new TextArea(420, 280, 150, 100);
        textArea.getTextState().setText("ABC DEF GH\r\nI JKL MNO PQR\nSTU VWXYZ");
        textArea.setCaretPosition(12);
        textArea.getTextAreaField().getStyle().setHorizontalAlign(CENTER);
        textArea.getTextAreaField().getStyle().setVerticalAlign(BOTTOM);
        this.add(textArea);

        textArea.getTextAreaField().getListenerMap().addListener(KeyEvent.class, (KeyEventListener) event -> {
            if (event.getKey() == GLFW.GLFW_KEY_F1 && event.getAction() == GLFW.GLFW_RELEASE) {
                textArea.getTextAreaField().getStyle().setHorizontalAlign(LEFT);
            } else if (event.getKey() == GLFW.GLFW_KEY_F2 && event.getAction() == GLFW.GLFW_RELEASE) {
                textArea.getTextAreaField().getStyle().setHorizontalAlign(CENTER);
            } else if (event.getKey() == GLFW.GLFW_KEY_F3 && event.getAction() == GLFW.GLFW_RELEASE) {
                textArea.getTextAreaField().getStyle().setHorizontalAlign(RIGHT);
            } else if (event.getKey() == GLFW.GLFW_KEY_F5 && event.getAction() == GLFW.GLFW_RELEASE) {
                textArea.getTextAreaField().getStyle().setVerticalAlign(TOP);
            } else if (event.getKey() == GLFW.GLFW_KEY_F6 && event.getAction() == GLFW.GLFW_RELEASE) {
                textArea.getTextAreaField().getStyle().setVerticalAlign(MIDDLE);
            } else if (event.getKey() == GLFW.GLFW_KEY_F7 && event.getAction() == GLFW.GLFW_RELEASE) {
                textArea.getTextAreaField().getStyle().setVerticalAlign(BOTTOM);
            } else if (event.getKey() == GLFW.GLFW_KEY_F8 && event.getAction() == GLFW.GLFW_RELEASE) {
                textArea.getTextAreaField().getStyle().setVerticalAlign(BASELINE);
            }
        });

        Label passLabel = new Label("Password:", 420, 390, 150, 15);
        this.add(passLabel);
        PasswordInput caretp = new PasswordInput(420, 405, 150, 20);
        caretp.getStyle().setHorizontalAlign(CENTER);
        this.add(caretp);

        TextInput inpur = new TextInput(420, 430, 50, 35);
        inpur.getTextState().setText("00");
        inpur.getStyle().setFontSize(35f);
        inpur.getStyle().setHorizontalAlign(CENTER);
        inpur.getStyle().setVerticalAlign(MIDDLE);
        inpur.getStyle().getBackground().setColor(ColorConstants.white());
        this.add(inpur);

        SelectBox<Object> selectBox = new SelectBox<>(20, 260, 100, 20);
        selectBox.addElement(0.25f);
        selectBox.addElement(0.5d);
        selectBox.addElement(1);
        selectBox.addElement("MyText");
        selectBox.addElement(new Long(2L));
        selectBox.setVisibleCount(7);
        selectBox.setElementHeight(20);
        selectBox.addSelectBoxChangeSelectionEventListener((SelectBoxChangeSelectionEventListener<Object>) event -> {
            Dialog dialog = new Dialog("SelectBox clicked", 300, 100);
            Label valueLabel = new Label("Value: " + event.getNewValue().toString(), 10, 10, 300, 20);
            dialog.getContainer().add(valueLabel);
            Label classLabel = new Label("Class: " + event.getNewValue().getClass().getName(), 10, 30, 300, 20);
            dialog.getContainer().add(classLabel);
            dialog.show(event.getFrame());
        });
        this.add(selectBox);

        Button sbb = new Button("Add element", 130, 260, 70, 20);
        sbb.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == CLICK) {
                selectBox.addElement("Dynamic#" + selectBox.getElements().size());
            }
        });
        this.add(sbb);

        this.add(createToggleButtonWithLongTooltip());
        //@formatter:on

        this.add(createSwitchThemeButton());

        this.add(createShadowWidget());
    }

    public CheckBox getGenerateEventsByLayoutManager() {
        return generateEventsByLayoutManager;
    }

    private Widget createImageWrapperWidgetWithImage() {
        Widget imageWrapper = new Widget(20, 30, 100, 100);
        imageWrapper.setTitleEnabled(true);

        imageView = new ImageView(new BufferedImage("org/liquidengine/legui/demo/1.jpg"));
        imageView.setPosition(15, 5);
        imageView.setSize(70, 70);
        imageView.getStyle().setBorderRadius(10f);
        imageWrapper.getContainer().add(imageView);
        imageWrapper.setCloseable(false);
        return imageWrapper;
    }

    private Button createButtonWithTooltip() {
        Button button = new Button(20, 170, 50, 20); /*button.getStyle().getBackground().setColor(new Vector4f(1));*/
        button.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) System.out::println);

        button.setTooltip(new Tooltip("Just button"));
        button.getTooltip().setPosition(0, 25);
        button.getTooltip().getSize().set(50, 60);
        button.getTooltip().getStyle().setPadding(4f);

        int[] idv = {0};
        button.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) (MouseClickEvent event) -> {
            if (event.getAction().equals(CLICK)) {
                idv[0]++;
                HorizontalAlign h;
                VerticalAlign v;
                int hh = idv[0] % 3;
                int vv = (idv[0] / 3) % 3;
                switch (hh) {
                    case 0:
                        h = LEFT;
                        break;
                    case 1:
                        h = CENTER;
                        break;
                    case 2:
                    default:
                        h = RIGHT;
                        break;
                }
                switch (vv) {
                    case 0:
                        v = TOP;
                        break;
                    case 1:
                        v = MIDDLE;
                        break;
                    case 2:
                    default:
                        v = BOTTOM;
                        break;
                }
                System.out.println(h + " " + v);
                button.getTooltip().getStyle().setHorizontalAlign(h);
                button.getTooltip().getStyle().setVerticalAlign(v);
            }
        });
        return button;
    }

    private CheckBox createCheckboxWithAnimation(CheckBox checkBox1) {
        CheckBox checkBox2 = new CheckBox(20, 230, 100, 20);
        checkBox2.getStyle().getBackground().setColor(new Vector4f(1, 0, 0, 1));
        checkBox2.getStyle().setPadding(5f, 10f, 5f, 20f);
        checkBox2.setChecked(true);

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
        return checkBox2;
    }

    private ToggleButton createToggleButtonWithLongTooltip() {
        ToggleButton toggleButton = new ToggleButton("", 100, 170, 40, 20);
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
        toggleButton.getTooltip().getStyle().setTextColor(ColorConstants.white());
        toggleButton.getTooltip().getStyle().setPadding(4f);

        int[] id = {0};
        toggleButton.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction().equals(CLICK)) {
                id[0]++;
                HorizontalAlign h = LEFT;
                VerticalAlign v = TOP;
                int hh = id[0] % 3;
                int vv = (id[0] / 3) % 3;
                switch (hh) {
                    case 0:
                        h = LEFT;
                        break;
                    case 1:
                        h = CENTER;
                        break;
                    case 2:
                        h = RIGHT;
                        break;
                }
                switch (vv) {
                    case 0:
                        v = TOP;
                        break;
                    case 1:
                        v = MIDDLE;
                        break;
                    case 2:
                        v = BOTTOM;
                        break;
                }
                System.out.println(h + " " + v);
                toggleButton.getTooltip().getStyle().setHorizontalAlign(h);
                toggleButton.getTooltip().getStyle().setVerticalAlign(v);

            }
        });

        bgImageNormal.setSize(new Vector2f(100 * 40 / 60, 20));
        bgImageNormal.setPosition(new Vector2f(40 - 100 * 40 / 60, 0));
        toggleButton.getStyle().getBackground().setIcon(bgImageNormal);
        return toggleButton;
    }

    private Button createSwitchThemeButton() {
        final Theme[] current = {Themes.getDefaultTheme()};
        final Theme[] list = {Themes.FLAT_DARK, Themes.FLAT_PETERRIVER, Themes.FLAT_PETERRIVER_DARK, Themes.FLAT_WHITE};
        final int[] themeIndex = {0};

        String text = "Switch theme ";
        Button switchTheme = new Button(text, 600, 400, 120, 30);
        switchTheme.getListenerMap().addListener(MouseClickEvent.class, switchThemeClickListener(current, list, themeIndex, switchTheme));
        return switchTheme;
    }

    private Widget createShadowWidget() {
        Widget shadowWidget = new Widget(20, 310, 200, 120);

        shadowWidget.getTitleTextState().setText("Shadow test widget");
        shadowWidget.setCloseable(false);
        shadowWidget.setResizable(false);

        shadowWidget.getStyle().setShadow(new Shadow());
        shadowWidget.getStyle().getShadow().setColor(ColorConstants.red());

        Slider hOffsetSlider = new Slider(110, 5 + 20 * 0, 80, 10);
        shadowWidget.getContainer().add(hOffsetSlider);
        hOffsetSlider.setValue(50f);
        hOffsetSlider.addSliderChangeValueEventListener((e) ->
                                                            shadowWidget.getStyle().getShadow().sethOffset(hOffsetSlider.getValue() - 50f)
                                                       );
        Label hOffsetLabel = new Label(10, 5 + 20 * 0, 90, 10);
        hOffsetLabel.getTextState().setText("HOffset: ");
        shadowWidget.getContainer().add(hOffsetLabel);

        Slider vOffsetSlider = new Slider(110, 5 + 20 * 1, 80, 10);
        shadowWidget.getContainer().add(vOffsetSlider);
        vOffsetSlider.setValue(50f);
        vOffsetSlider.addSliderChangeValueEventListener((e) ->
                                                            shadowWidget.getStyle().getShadow().setvOffset(vOffsetSlider.getValue() - 50f)
                                                       );
        Label vOffsetLabel = new Label(10, 5 + 20 * 1, 90, 10);
        vOffsetLabel.getTextState().setText("VOffset: ");
        shadowWidget.getContainer().add(vOffsetLabel);

        Slider blurSlider = new Slider(110, 5 + 20 * 2, 80, 10);
        shadowWidget.getContainer().add(blurSlider);
        blurSlider.addSliderChangeValueEventListener((e) ->
                                                         shadowWidget.getStyle().getShadow().setBlur(blurSlider.getValue())
                                                    );
        Label blurLabel = new Label(10, 5 + 20 * 2, 90, 10);
        blurLabel.getTextState().setText("Blur: ");
        shadowWidget.getContainer().add(blurLabel);

        Slider spreadSlider = new Slider(110, 5 + 20 * 3, 80, 10);
        shadowWidget.getContainer().add(spreadSlider);
        spreadSlider.setValue(50f);
        spreadSlider.addSliderChangeValueEventListener((e) ->
                                                           shadowWidget.getStyle().getShadow().setSpread(spreadSlider.getValue() - 50f)
                                                      );
        Label spreadLabel = new Label(10, 5 + 20 * 3, 90, 10);
        spreadLabel.getTextState().setText("Spread: ");
        shadowWidget.getContainer().add(spreadLabel);

        Slider transparencySlider = new Slider(110, 5 + 20 * 4, 80, 10);
        shadowWidget.getContainer().add(transparencySlider);
        transparencySlider.addSliderChangeValueEventListener((e) -> {
            shadowWidget.getStyle().getBackground().getColor().w = 1 - transparencySlider.getValue() / 100f;
            shadowWidget.getContainer().getStyle().getBackground().getColor().w = 1 - transparencySlider.getValue() / 100f;
        });
        Label transparencyLabel = new Label(10, 5 + 20 * 4, 90, 10);
        transparencyLabel.getTextState().setText("W Transparency: ");
        shadowWidget.getContainer().add(transparencyLabel);
        return shadowWidget;
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

    private MouseClickEventListener switchThemeClickListener(Theme[] current, Theme[] list, int[] index, Button switchTheme) {
        return event -> {
            if (event.getAction().equals(CLICK) && event.getButton().equals(MOUSE_BUTTON_LEFT)) {
                Theme curr = current[0];
                if (index[0] < list.length && index[0] > -1) {
                    curr = list[index[0]];
                    index[0] = (index[0] + 1) % list.length;
                } else {
                    index[0] = 0;
                    curr = list[0];
                }
                Themes.setDefaultTheme(curr);
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
