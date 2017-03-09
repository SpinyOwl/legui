package org.liquidengine.legui.theme;

import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.Component;

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
        return manager;
    }

    private static class DefaultComponentTheme<T extends Component> implements IComponentTheme<T> {
        @Override
        public void apply(Component component) {
            component.setBorder(new SimpleLineBorder(ColorConstants.darkGray(), .7f));
            component.setCornerRadius(2);
            component.setBackgroundColor(ColorConstants.white());
        }
    }

    private static class
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
