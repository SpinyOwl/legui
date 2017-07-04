package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.theme.Themes;

import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.CLICK;
import static org.liquidengine.legui.font.FontRegistry.MATERIAL_ICONS_REGULAR;

/**
 * An implementation of a check box -- an item that can be selected or
 * deselected, and which displays its state to the user.
 */
public class CheckBox extends Controller implements TextComponent {

    /**
     * Check box text state.
     */
    private TextState textState;
    /**
     * Checkbox state.
     */
    private boolean   checked;

    /**
     * Icon used to show checked state of checkbox.
     */
    private Icon iconChecked = new CharIcon(new Vector2f(16, 16), MATERIAL_ICONS_REGULAR, (char) 0xE834);

    /**
     * Icon used to show unchecked state of checkbox.
     */
    private Icon iconUnchecked = new CharIcon(new Vector2f(16, 16), MATERIAL_ICONS_REGULAR, (char) 0xE835);

    /**
     * Default constructor which initialize checkbox with "CheckBox" text.
     */
    public CheckBox() {
        this("CheckBox");
    }

    /**
     * Constructor with position and size parameters.
     * Initialize checkbox with "CheckBox" text.
     *
     * @param x      x position position in parent component.
     * @param y      y position position in parent component.
     * @param width  width of component.
     * @param height height of component.
     */
    public CheckBox(float x, float y, float width, float height) {
        this("CheckBox", x, y, width, height);
    }

    /**
     * Constructor with position and size parameters.
     * Initialize checkbox with "CheckBox" text.
     *
     * @param position position position in parent component.
     * @param size     size of component.
     */
    public CheckBox(Vector2f position, Vector2f size) {
        this("CheckBox", position, size);
    }

    /**
     * Default constructor which initialize checkbox with specified text.
     *
     * @param text specified text for checkbox.
     */
    public CheckBox(String text) {
        initialize(text);
    }

    /**
     * Constructor with position and size parameters.
     * Initialize checkbox with "CheckBox" text.
     *
     * @param text   specified text for checkbox.
     * @param x      x position position in parent component.
     * @param y      y position position in parent component.
     * @param width  width of component.
     * @param height height of component.
     */
    public CheckBox(String text, float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize(text);
    }

    /**
     * Constructor with position and size parameters.
     * Initialize checkbox with "CheckBox" text.
     *
     * @param text     specified text for checkbox.
     * @param position position position in parent component.
     * @param size     size of component.
     */
    public CheckBox(String text, Vector2f position, Vector2f size) {
        super(position, size);
        initialize(text);
    }

    /**
     * Used to initialize checkbox state.
     *
     * @param text text to set.
     */
    private void initialize(String text) {
        this.textState = new TextState(text);
        setBackgroundColor(ColorConstants.transparent());
        setBorder(null);

        getListenerMap().addListener(MouseClickEvent.class, new CheckBoxMouseClickEventListener());
        iconChecked.setHorizontalAlign(HorizontalAlign.LEFT);
        iconUnchecked.setHorizontalAlign(HorizontalAlign.LEFT);
        Themes.getDefaultTheme().getThemeManager().getComponentTheme(CheckBox.class).applyAll(this);
    }

    /**
     * Returns checkbox icon for non-selected state.
     *
     * @return checkbox icon for non-selected state.
     */
    public Icon getIconUnchecked() {
        return iconUnchecked;
    }

    /**
     * Used to set checkbox icon for non-selected state.
     *
     * @param iconUnchecked checkbox icon for non-selected state to set.
     */
    public void setIconUnchecked(Icon iconUnchecked) {
        this.iconUnchecked = iconUnchecked;
    }

    /**
     * Returns checkbox icon for selected state.
     *
     * @return checkbox icon for selected state.
     */
    public Icon getIconChecked() {
        return iconChecked;
    }

    /**
     * Used to set checkbox icon for selected state.
     *
     * @param iconChecked checkbox icon for selected state to set.
     */
    public void setIconChecked(Icon iconChecked) {
        if (iconChecked != null)
            this.iconChecked = iconChecked;
    }

    /**
     * Returns current text state.
     *
     * @return text state of component.
     */
    public TextState getTextState() {
        return textState;
    }

    /**
     * Returns true if checkbox is checked.
     *
     * @return true if checkbox is checked.
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * Used to change checked state.
     *
     * @param checked new checkbox state.
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CheckBox checkBox = (CheckBox) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(checked, checkBox.checked)
                .append(textState, checkBox.textState)
                .isEquals();
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(textState)
                .append(checked)
                .toHashCode();
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("textState", textState)
                .append("checked", checked)
                .toString();
    }

    /**
     * MouseClickEventListener for checkbox, used to toggle checkbox state on mouse click.
     */
    public static class CheckBoxMouseClickEventListener implements MouseClickEventListener {

        /**
         * Used to handle event.
         *
         * @param event event to handle.
         */
        @Override
        public void process(MouseClickEvent event) {
            CheckBox checkBox = (CheckBox) event.getComponent();
            if (event.getAction() == CLICK) {
                checkBox.setChecked(!checkBox.isChecked());
            }
        }

        /**
         * Used to compare instances of this event listener.
         *
         * @param obj object to compare.
         * @return true if equals.
         */
        @Override
        public boolean equals(Object obj) {
            return obj == this || obj instanceof CheckBoxMouseClickEventListener;
        }
    }
}
