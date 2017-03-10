package org.liquidengine.legui.component.optional.icon;

import org.joml.Vector2f;

/**
 * Created by Aliaksandr_Shcherbin on 3/10/2017.
 */
public class CharIcon implements Icon {

    private Vector2f size = new Vector2f();
    private String font;
    private int    charCode;

    public CharIcon(Vector2f size, String font, int charCode) {
        this.size = size;
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

    @Override
    public Vector2f getSize() {
        return size;
    }

    @Override
    public void setSize(Vector2f size) {
        this.size = size;
    }
}
