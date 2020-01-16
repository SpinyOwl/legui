package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.misc.listener.togglebutton.ToggleButtonMouseClickListener;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.theme.Themes;

/**
 * An implementation of "toggle" button. Behavior the same as checkbox but have not any text.
 */
public class ToggleButton extends Button {

    private Icon togglededBackgroundIcon;
    private boolean toggled;
    private Vector4f toggledBackgroundColor;

    /**
     * Creates a button with specified text and specified position and size.
     *
     * @param text button text.
     * @param x x position in parent.
     * @param y y position in parent.
     * @param width width of component.
     * @param height height of component.
     */
    public ToggleButton(String text, float x, float y, float width, float height) {
        super(text, x, y, width, height);
        initialize();
    }

    /**
     * Creates toggle button with default bg color and default toggled bg color.
     */
    public ToggleButton() {
        initialize();
    }

    /**
     * Creates toggle button with default bg color, toggled bg color and specified text.
     *
     * @param text button text.
     */
    public ToggleButton(String text) {
        super(text);
        initialize();
    }

    /**
     * Creates a button with specified position and size and default bg color and default toggled bg color.
     *
     * @param x x position in parent.
     * @param y y position in parent.
     * @param width width of component.
     * @param height height of component.
     */
    public ToggleButton(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize();
    }

    /**
     * Creates a button with specified position and size and default bg color and default toggled bg color.
     *
     * @param position position in parent.
     * @param size size of component.
     */
    public ToggleButton(Vector2f position, Vector2f size) {
        super(position, size);
        initialize();
    }

    /**
     * Initialize toggle button with default states.
     */
    private void initialize() {
        getStyle().getBackground().setColor(ColorConstants.red());
        toggledBackgroundColor = ColorConstants.green();
        MouseClickEventListener toggleButtonClickListener = new ToggleButtonMouseClickListener();
        getListenerMap().addListener(MouseClickEvent.class, toggleButtonClickListener);

        Themes.getDefaultTheme().getThemeManager().getComponentTheme(ToggleButton.class).applyAll(this);
    }

    /**
     * Returns true if toggle button is toggled.
     *
     * @return true if toggle button is toggled.
     */
    public boolean isToggled() {
        return toggled;
    }

    /**
     * Used to change toggled state.
     *
     * @param toggled new value.
     */
    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    /**
     * Returns background color which will be used as background color if button toggled.
     *
     * @return toggled button color.
     */
    public Vector4f getToggledBackgroundColor() {
        return toggledBackgroundColor;
    }

    /**
     * Used to change background color which will be used as background color if button toggled.
     *
     * @param toggledBackgroundColor new value.
     */
    public void setToggledBackgroundColor(Vector4f toggledBackgroundColor) {
        this.toggledBackgroundColor = toggledBackgroundColor;
    }

    /**
     * Returns toggled background icon.
     *
     * @return toggled background icon.
     */
    public Icon getTogglededBackgroundIcon() {
        return togglededBackgroundIcon;
    }

    /**
     * Used to change toggled background icon.
     *
     * @param togglededBackgroundIcon toggled background icon.
     */
    public void setTogglededBackgroundIcon(Icon togglededBackgroundIcon) {
        this.togglededBackgroundIcon = togglededBackgroundIcon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ToggleButton button = (ToggleButton) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(toggledBackgroundColor, button.toggledBackgroundColor)
            .append(toggled, button.toggled)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(toggledBackgroundColor)
            .append(toggled)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("toggledBackgroundColor", toggledBackgroundColor)
            .append("toggled", toggled)
            .toString();
    }

}
