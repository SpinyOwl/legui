package org.liquidengine.legui.icon;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.font.FontRegister;

/**
 * Created by Aliaksandr_Shcherbin on 3/10/2017.
 */
public class CharIcon extends Icon {

    private Vector4f color;
    private String   font;
    private int      charCode;

    public CharIcon() {
        this.font = FontRegister.MATERIAL_DESIGN_ICONS;
        this.color = ColorConstants.black();
    }

    public CharIcon(Vector2f size, String font, int charCode) {
        super(size);
        this.font = font;
        this.charCode = charCode;
        this.color = ColorConstants.black();
    }

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public int getCharCode() {
        return charCode;
    }

    public void setCharCode(int charCode) {
        this.charCode = charCode;
    }
}
