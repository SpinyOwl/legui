package org.liquidengine.legui.icon;

import org.joml.Vector2f;

/**
 * Created by Aliaksandr_Shcherbin on 3/10/2017.
 */
public class CharIcon extends Icon {

    private String font;
    private int    charCode;

    public CharIcon(Vector2f size, String font, int charCode) {
        super(size);
        this.font = font;
        this.charCode = charCode;
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
