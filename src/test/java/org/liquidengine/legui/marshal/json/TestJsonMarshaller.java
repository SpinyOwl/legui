package org.liquidengine.legui.marshal.json;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.icon.ImageIcon;
import org.liquidengine.legui.image.DummyImage;
import org.liquidengine.legui.image.DummyImageLoader;
import org.liquidengine.legui.image.loader.ImageLoader;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalUtil;
import org.liquidengine.legui.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

import static org.liquidengine.legui.component.optional.align.HorizontalAlign.CENTER;
import static org.liquidengine.legui.component.optional.align.HorizontalAlign.RIGHT;
import static org.liquidengine.legui.component.optional.align.VerticalAlign.BOTTOM;
import static org.liquidengine.legui.component.optional.align.VerticalAlign.MIDDLE;

public class TestJsonMarshaller {

    @Before
    public void initialize() {
        ImageLoader.setLoader(new DummyImageLoader());
    }

    @Test
    public void testGsonImpl() {
        Object toMarshal;
        Object unMarshalled;

        Frame frame = new Frame();
        frame.getContainer().addAll(createComponents(0, 0));

        String marshalled   = GsonMarshalUtil.marshal(frame);
        Frame  unmarshaled  = GsonMarshalUtil.unmarshal(marshalled);
        String remarshalled = GsonMarshalUtil.marshal(unmarshaled);


        toMarshal = frame;
        unMarshalled = unmarshaled;


        String jsonOne = marshalled;
        String jsonTwo = remarshalled;


        System.out.println(jsonOne);
        System.out.println(jsonTwo);
        System.out.println(toMarshal.equals(unMarshalled));

        Assert.assertEquals(toMarshal, unMarshalled);
    }


    private List<Component> createComponents(int width, int height) {
        List<Component> toReturn = new ArrayList<>();
        Panel           p1       = new Panel(1 * 20, 10, 10, 10);
        toReturn.add(p1);
        Panel p2 = new Panel(2 * 20, 10, 10, 10);
        toReturn.add(p2);
        Panel p3 = new Panel(3 * 20, 10, 10, 10);
        toReturn.add(p3);
        Panel p4 = new Panel(4 * 20, 10, 10, 10);
        toReturn.add(p4);
        Panel p5 = new Panel(5 * 20, 10, 10, 10);
        toReturn.add(p5);
        Panel p6 = new Panel(6 * 20, 10, 10, 10);
        toReturn.add(p6);
        Panel p7 = new Panel(7 * 20, 10, 10, 10);
        toReturn.add(p7);
        Panel p8 = new Panel(8 * 20, 10, 10, 10);
        toReturn.add(p8);
        Panel p9 = new Panel(9 * 20, 10, 10, 10);
        toReturn.add(p9);

        Label mouseTargetLabel = new Label("Hello Label 1", 10, height - 30, width - 20, 20);
        toReturn.add(mouseTargetLabel);

        Label focusedGuiLabel = new Label("Hello Label 2", 10, height - 50, width - 20, 20);
        focusedGuiLabel.setBorder(new SimpleLineBorder(ColorConstants.red(), 1));
        toReturn.add(focusedGuiLabel);

        Label debugLabel = new Label("Debug Label", 10, height - 75, width - 20, 20);
        toReturn.add(debugLabel);

        Label mouseLabel = new Label("Hello Label 3", 130, 30, 100, 20);
        toReturn.add(mouseLabel);

        Label upsLabel = new Label("Hello Label 4", 130, 60, 100, 20);
        toReturn.add(upsLabel);

        ImageView imageView = new ImageView(new DummyImage("org/liquidengine/legui/example/1.jpg"));
        imageView.setPosition(20, 30);
        imageView.setSize(100, 100);
        toReturn.add(imageView);

        Button button = new Button(20, 170, 50, 20);
        button.setBackgroundColor(new Vector4f(1));
        toReturn.add(button);

        button.setTooltip("Just button");
        button.getTooltip().setPosition(0, 25);
        button.getTooltip().getSize().set(50, 40);
        button.getTooltip().setBackgroundColor(ColorConstants.darkGray());
        button.getTooltip().getTextState().setTextColor(ColorConstants.white());
        button.getTooltip().getTextState().setPadding(4, 4, 4, 4);

//        int idv[] = {0};

        CheckBox checkBox1 = new CheckBox(20, 200, 50, 20);
        toReturn.add(checkBox1);
        CheckBox checkBox2 = new CheckBox(20, 230, 50, 20);
        checkBox2.setBackgroundColor(new Vector4f(1));
        checkBox2.setChecked(true);
        toReturn.add(checkBox2);

        ProgressBar progressBar = new ProgressBar(250, 10, 100, 10);
        progressBar.setValue(50);
        toReturn.add(progressBar);

        RadioButtonGroup radioButtonGroup = new RadioButtonGroup();
        RadioButton      radioButton1     = new RadioButton(250, 30, 100, 20);
        toReturn.add(radioButton1);
        radioButton1.setChecked(true);
        radioButton1.setRadioButtonGroup(radioButtonGroup);
        RadioButton radioButton2 = new RadioButton(250, 60, 100, 20);
        toReturn.add(radioButton2);
        radioButton2.setChecked(false);
        radioButton2.setRadioButtonGroup(radioButtonGroup);

        Slider slider1 = new Slider(250, 90, 100, 20, 30);
        toReturn.add(slider1);

        Slider slider2 = new Slider(220, 90, 20, 100, 50);
        slider2.setOrientation(Orientation.VERTICAL);
        toReturn.add(slider2);

        TextInput textInput = new TextInput(250, 130, 100, 30);
        textInput.getTextState().setHorizontalAlign(RIGHT);
        toReturn.add(textInput);

        Widget widget = new Widget("Hello widget", 250, 170, 100, 100);
        widget.setTitleHeight(20);
        widget.setTitleBackgroundColor(ColorConstants.lightGreen());

        Button turnWidVisible = new Button("", 360, 280, 20, 20);
        Icon   bgIm           = new ImageIcon(new DummyImage("org/liquidengine/legui/example/1.png"));
        bgIm.setSize(new Vector2f(20, 20));
        turnWidVisible.setBackgroundIcon(bgIm);
        Icon hbgIm = new ImageIcon(new DummyImage("org/liquidengine/legui/example/2.png"));
        hbgIm.setSize(new Vector2f(20, 20));
        turnWidVisible.setHoveredBackgroundIcon(hbgIm);
        Icon pbIm = new ImageIcon(new DummyImage("org/liquidengine/legui/example/3.png"));
        pbIm.setSize(new Vector2f(20, 20));
        turnWidVisible.setPressedBackgroundIcon(pbIm);

        toReturn.add(turnWidVisible);

        Panel c0 = new Panel(-5, -5, 10, 10);
        c0.setBackgroundColor(1, 0, 0, 1);
        widget.getContainer().add(c0);
        Panel c1 = new Panel(-5, 75, 10, 10);
        c1.setBackgroundColor(1, 0, 0, 1);
        widget.getContainer().add(c1);
        Panel c2 = new Panel(95, -5, 10, 10);
        c2.setBackgroundColor(1, 0, 0, 1);
        widget.getContainer().add(c2);
        c2.setVisible(false);
        Panel c3 = new Panel(95, 75, 10, 10);
        c3.setBackgroundColor(1, 0, 0, 1);
        widget.getContainer().add(c3);
        Panel c4 = new Panel(45, 35, 10, 10);
        c4.setBackgroundColor(1, 0, 0, 1);
        widget.getContainer().add(c4);
        toReturn.add(widget);

        Widget widget2 = new Widget("Hello 2 widget", 250, 310, 100, 100);
        widget2.setTitleHeight(20);
        widget2.setCloseButtonColor(ColorConstants.white());
        widget2.setCloseButtonBackgroundColor(ColorConstants.black());
        widget2.setTitleBackgroundColor(ColorConstants.lightGreen());
        widget2.setDraggable(false);

        Button turnDraggable = new Button("Draggable", 10, 10, 80, 20);
        widget2.getContainer().add(turnDraggable);
        toReturn.add(widget2);

        Button turnWidVisible2 = new Button("", 360, 310, 20, 20);
        toReturn.add(turnWidVisible2);

        Widget widget3 = new Widget("Hello 2 widget", 250, 420, 100, 100);
        widget3.setTitleHeight(20);
        widget3.setTitleEnabled(false);
        widget3.setTitleBackgroundColor(ColorConstants.lightGreen());
        widget3.setCloseable(true);
        widget3.setMinimizable(false);
        toReturn.add(widget3);

        Button turnWidVisible3 = new Button("", 360, 340, 20, 20);
        toReturn.add(turnWidVisible3);

        widget3.getContainer().add(new Panel(5, 5, 20, 20));
        widget3.getContainer().add(new Panel(30, 5, 20, 20));
        widget3.getContainer().add(new Panel(30, 30, 20, 20));
        widget3.getContainer().add(new Panel(5, 30, 20, 20));
        widget3.getContainer().add(new Panel(5, 30, 20, 20));
        Button b = new Button(55, 5, 40, 45);
        b.getTextState().setFont(FontRegister.MATERIAL_ICONS_REGULAR);
        b.getTextState().setVerticalAlign(MIDDLE);
        b.getTextState().setHorizontalAlign(CENTER);
        b.getTextState().setFontSize(20);

//        String up   = TextUtil.cpToStr(0xE5D8);
        String down = TextUtil.cpToStr(0xE5DB);
        b.getTextState().setText(down);
        widget3.getContainer().add(b);

        ScrollBar scrollBar1 = new ScrollBar(360, 170, 20, 100, 20);
        scrollBar1.setOrientation(Orientation.VERTICAL);
        scrollBar1.setVisibleAmount(20);
        scrollBar1.setArrowsEnabled(true);
        scrollBar1.setBackgroundColor(ColorConstants.white());
        scrollBar1.setScrollColor(ColorConstants.darkGray());
        scrollBar1.setArrowColor(ColorConstants.darkGray());
        scrollBar1.setBorder(new SimpleLineBorder(ColorConstants.red(), 1));
        toReturn.add(scrollBar1);

        ScrollBar scrollBar11 = new ScrollBar(385, 170, 7, 100, 20);
        scrollBar11.setOrientation(Orientation.VERTICAL);
        scrollBar11.setVisibleAmount(20);
        scrollBar11.setArrowsEnabled(false);
        scrollBar11.setBackgroundColor(ColorConstants.white());
        scrollBar11.setScrollColor(ColorConstants.darkGray());
        scrollBar11.setBorder(new SimpleLineBorder(ColorConstants.red(), 1));
        scrollBar11.setCornerRadius(3);
        toReturn.add(scrollBar11);

        ScrollBar scrollBar2 = new ScrollBar(250, 280, 100, 20, 20);
        scrollBar2.setOrientation(Orientation.HORIZONTAL);
        scrollBar2.setVisibleAmount(20);
        scrollBar2.setArrowsEnabled(true);
        scrollBar2.setBorder(new SimpleLineBorder(ColorConstants.black(), 1));
        scrollBar2.setBackgroundColor(ColorConstants.darkGray());
        scrollBar2.setScrollColor(ColorConstants.white());
        scrollBar2.setArrowColor(ColorConstants.white());
        toReturn.add(scrollBar2);

        Panel panel1 = new Panel(420, 170, 100, 100);
        panel1.setBackgroundColor(ColorConstants.blue());
        toReturn.add(panel1);
        Panel panel2 = new Panel(470, 170, 100, 100);
        panel2.setBackgroundColor(ColorConstants.green());
        toReturn.add(panel2);

        ScrollablePanel scrollablePanel = new ScrollablePanel(420, 10, 250, 150);
        scrollablePanel.setBackgroundColor(1, 1, 1, 1);
        scrollablePanel.getContainer().setSize(300, 200);
        scrollablePanel.resize();

        ScrollablePanel scp = new ScrollablePanel(10, 10, 150, 100);
        scp.getContainer().setSize(300, 300);
        scp.resize();

        scp.getContainer().add(new TextInput("Hello Scrollable", 10, 10, 150, 20));

        scrollablePanel.getContainer().add(scp);
        toReturn.add(scrollablePanel);

        TextArea textArea = new TextArea(420, 280, 150, 100);
        textArea.getTextState().setText("ABC DEF GH\r\nI JKL MNO PQR\nSTU VWXYZ");
        textArea.setCaretPosition(12);
        textArea.getTextState().setHorizontalAlign(CENTER);
        textArea.getTextState().setVerticalAlign(BOTTOM);
        toReturn.add(textArea);

        TextInput caretp = new TextInput(420, 400, 150, 20);
        caretp.getTextState().setHorizontalAlign(CENTER);
        toReturn.add(caretp);

        TextInput inpur = new TextInput(420, 430, 50, 35);
        inpur.getTextState().setText("00");
        inpur.getTextState().setFontSize(35);
        inpur.getTextState().setHorizontalAlign(CENTER);
        inpur.getTextState().setVerticalAlign(MIDDLE);
        inpur.setBackgroundColor(ColorConstants.white());
        toReturn.add(inpur);

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
        toReturn.add(selectBox);

        Button sbb = new Button("Add element", 130, 260, 70, 20);
        toReturn.add(sbb);

        ToggleButton toggleButton = new ToggleButton("", 100, 170, 40, 40);
        toReturn.add(toggleButton);
        Icon bgImageNormal  = new ImageIcon(new DummyImage("org/liquidengine/legui/example/normal.png"));
        Icon bgImageToggled = new ImageIcon(new DummyImage("org/liquidengine/legui/example/toggled.png"));


        toggleButton.setTooltip("Just toggle button with long tooltipText text");


        toggleButton.getTooltip().setPosition(45, 0);
        toggleButton.getTooltip().getSize().set(140, 40);
        toggleButton.getTooltip().setBackgroundColor(ColorConstants.darkGray());
        toggleButton.getTooltip().getTextState().setTextColor(ColorConstants.white());
        toggleButton.getTooltip().getTextState().setPadding(4, 4, 4, 4);

        bgImageNormal.setSize(new Vector2f(36, 36));
        toggleButton.setBackgroundIcon(bgImageNormal);
        bgImageToggled.setSize(new Vector2f(36, 36));
        toggleButton.setTogglededBackgroundIcon(bgImageToggled);
        //@formatter:on

        return toReturn;
    }
}
