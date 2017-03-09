package org.liquidengine.legui.theme;

import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.*;

/**
 * Created by Aliaksandr_Shcherbin on 3/9/2017.
 */
public class DefaultTheme extends Theme {

    public DefaultTheme() {
        super(createThemeManager());
    }

    private static ThemeManager createThemeManager() {
        ThemeManager manager = new DefaultThemeManager();
        manager.setComponentTheme(Component.class, new DefaultComponentTheme());
        manager.setComponentTheme(Button.class, new DefaultButtonTheme());
        manager.setComponentTheme(RadioButton.class, new DefaultRadioButtonTheme());
        manager.setComponentTheme(CheckBox.class, new DefaultCheckBoxTheme());
        manager.setComponentTheme(Label.class, new DefaultLabelTheme());
        manager.setComponentTheme(Slider.class, new DefaultSliderTheme());
        return manager;
    }

    private static class DefaultComponentTheme<T extends Component> extends AbstractTheme<T> {
        @Override
        public void apply(T component) {
            component.setBorder(new SimpleLineBorder(ColorConstants.darkGray(), .7f));
            component.setCornerRadius(2);
            component.setBackgroundColor(ColorConstants.white());
        }
    }

    private static class DefaultRadioButtonTheme<T extends RadioButton> extends DefaultComponentTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBorder(null);
            component.getTextState().setTextColor(ColorConstants.black());
        }
    }

    private static class DefaultButtonTheme<T extends Button> extends DefaultComponentTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBorder(new SimpleLineBorder(ColorConstants.lightGray(), 1.2f));
            component.getTextState().setTextColor(ColorConstants.black());
        }
    }

    private static class DefaultCheckBoxTheme<T extends CheckBox> extends DefaultComponentTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBorder(null);
            component.getTextState().setTextColor(ColorConstants.black());
        }
    }

    private static class DefaultLabelTheme<T extends Label> extends DefaultComponentTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBorder(null);
            component.getTextState().setTextColor(ColorConstants.black());
        }
    }

    private static class DefaultSliderTheme<T extends Slider> extends DefaultComponentTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBorder(null);
        }
    }

//    @Override
//    public String font() {
//        return FontRegister.DEFAULT;
//    }
//
//    @Override
//    public Vector4f fontColor() {
//        return ColorConstants.black();
//    }
//
//    @Override
//    public Vector4f highlightColor() {
//        return ColorConstants.lightBlue();
//    }
//
//    @Override
//    public Vector4f textPadding() {
//        return new Vector4f(3);
//    }
//
//    @Override
//    public float fontSize() {
//        return 16;
//    }
//
//    @Override
//    public HorizontalAlign horizontalAlign() {
//        return HorizontalAlign.LEFT;
//    }
//
//    @Override
//    public VerticalAlign verticalAlign() {
//        return VerticalAlign.MIDDLE;
//    }

//
//    @Override
//    public Vector4f scrollBarArrowColor() {
//        return ColorConstants.darkGray();
//    }
//
//    @Override
//    public Vector4f scrollBarColor() {
//        return ColorConstants.darkGray();
//    }
//
//    @Override
//    public float scrollBarArrowSize() {
//        return 30;
//    }
//
//    @Override
//    public boolean scrollBarArrowsEnabled() {
//        return true;
//    }
//
//    @Override
//    public Vector4f getFocusedStrokeColorLight() {
//        return new Vector4f(ColorConstants.lightBlue);
//    }
//
//    @Override
//    public Vector4f backgroundColor() {
//        return ColorConstants.white();
//    }
}
