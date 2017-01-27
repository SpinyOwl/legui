package org.liquidengine.legui.component.theme;

import org.joml.Vector4f;
import org.liquidengine.legui.font.FontRegister;

/**
 * Created by Shcherbin Alexander on 9/26/2016.
 */
public class Theme {
    public static Theme DEFAULT_THEME = new Theme();

    static {
        DEFAULT_THEME.strokeColorDark = new Vector4f(0, 0, 0, 0.5f);
        DEFAULT_THEME.StrokeColorLight = new Vector4f(1, 1, 1, 0.5f);
        DEFAULT_THEME.focusedStrokeColorDark = new Vector4f(0, 0, 0, 1);
        DEFAULT_THEME.focusedStrokeColorLight = new Vector4f(0.5f, 0.7f, 1, 1);
        DEFAULT_THEME.fontSize = 16f;
        DEFAULT_THEME.font = FontRegister.DEFAULT;
    }

    private Vector4f strokeColorDark;
    private Vector4f StrokeColorLight;

    private Vector4f focusedStrokeColorDark;
    private Vector4f focusedStrokeColorLight;

    private float  fontSize;
    private String font;

    public Vector4f getStrokeColorDark() {
        return strokeColorDark;
    }

    public void setStrokeColorDark(Vector4f strokeColorDark) {
        this.strokeColorDark = strokeColorDark;
    }

    public Vector4f getStrokeColorLight() {
        return StrokeColorLight;
    }

    public void setStrokeColorLight(Vector4f strokeColorLight) {
        StrokeColorLight = strokeColorLight;
    }

    public Vector4f getFocusedStrokeColorDark() {
        return focusedStrokeColorDark;
    }

    public void setFocusedStrokeColorDark(Vector4f focusedStrokeColorDark) {
        this.focusedStrokeColorDark = focusedStrokeColorDark;
    }

    public Vector4f getFocusedStrokeColorLight() {
        return focusedStrokeColorLight;
    }

    public void setFocusedStrokeColorLight(Vector4f focusedStrokeColorLight) {
        this.focusedStrokeColorLight = focusedStrokeColorLight;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }
}
