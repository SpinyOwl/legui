package org.liquidengine.legui.theme;

import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.font.FontRegister;

/**
 * Created by ShchAlexander on 06.03.2017.
 */
public class DarkTheme extends Theme {
    public DarkTheme() {
        super(createThemeManager());
    }

    private static ThemeManager createThemeManager() {
        ThemeManager manager = new DefaultThemeManager();
        manager.setComponentTheme(Component.class, new ComponentTheme<>());
        manager.setComponentTheme(Button.class, new ButtonTheme<>());
        return manager;
    }

    private static void applyForTextState(TextState textState) {
        textState.setTextColor(ColorConstants.white());
    }

    private static class ComponentTheme<T extends Component> implements IComponentTheme<T> {
        @Override
        public void apply(Component component) {
            component.setBorder(new SimpleLineBorder(ColorConstants.lightGray(), 1.2f));
            component.setCornerRadius(2);
            component.setBackgroundColor(ColorConstants.darkGray());
        }
    }

    private static class ButtonTheme<T extends Button> implements IComponentTheme<T> {
        @Override
        public void apply(T component) {
            applyForTextState(component.getTextState());
        }
    }
//
//    @Override
//    public String font() {
//        return FontRegister.ROBOTO_REGULAR;
//    }
//
//    @Override
//    public Vector4f fontColor() {
//        return ColorConstants.white();
//    }
//
//    @Override
//    public Vector4f highlightColor() {
//        return ColorConstants.darkBlue();
//    }
//
//    @Override
//    public Vector4f textPadding() {
//        return new Vector4f(3, 5, 3, 5);
//    }
//
//    @Override
//    public float fontSize() {
//        return 16f;
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
//    public float cornerRadius() {
//        return 2;
//    }
//
//    @Override
//    public Vector4f scrollBarArrowColor() {
//        return ColorConstants.white();
//    }
//
//    @Override
//    public Vector4f scrollBarColor() {
//        return ColorConstants.lightGray();
//    }
//
//    @Override
//    public float scrollBarArrowSize() {
//        return 10f;
//    }
//
//    @Override
//    public boolean scrollBarArrowsEnabled() {
//        return false;
//    }
//
//    @Override
//    public Vector4f getFocusedStrokeColorLight() {
//        return ColorConstants.blue();
//    }
}
