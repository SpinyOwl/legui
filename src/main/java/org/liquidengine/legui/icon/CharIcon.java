package org.liquidengine.legui.icon;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.font.FontRegistry;

/**
 * Icon. Used to draw component icons based on characters and fonts.
 */
public class CharIcon extends Icon {

    /**
     * Used to hold icon color.
     */
    private Vector4f color;

    /**
     * Used to hold icon font.
     */
    private String font;

    /**
     * Used to hold icon character.
     */
    private char charCode;

    /**
     * Used to create char icon with default font and color. Default font is {@link FontRegistry#MATERIAL_DESIGN_ICONS}.
     */
    public CharIcon() {
        this.font = FontRegistry.MATERIAL_DESIGN_ICONS;
        this.color = ColorConstants.black();
    }

    public CharIcon(Vector2f size, String font, char charCode) {
        super(size);
        this.font = font;
        this.charCode = charCode;
        this.color = ColorConstants.black();
    }

    public CharIcon(String font, char charCode) {
        super(new Vector2f(16));
        this.font = font;
        this.charCode = charCode;
        this.color = ColorConstants.black();
    }

    public CharIcon(Vector2f size, String font, char charCode, Vector4f color) {
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

    public char getCharCode() {
        return charCode;
    }

    public void setCharCode(char charCode) {
        this.charCode = charCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

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
