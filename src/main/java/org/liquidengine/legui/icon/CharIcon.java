package org.liquidengine.legui.icon;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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

    public CharIcon(String font, int charCode) {
        super(new Vector2f(16));
        this.font = font;
        this.charCode = charCode;
        this.color = ColorConstants.black();
    }

    public CharIcon(Vector2f size, String font, int charCode, Vector4f color) {
        super(size);
        this.font = font;
        this.charCode = charCode;
        this.color = color;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CharIcon charIcon = (CharIcon) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getCharCode(), charIcon.getCharCode())
                .append(getColor(), charIcon.getColor())
                .append(getFont(), charIcon.getFont())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(getColor())
                .append(getFont())
                .append(getCharCode())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("color", color)
                .append("font", font)
                .append("charCode", charCode)
                .toString();
    }
}
