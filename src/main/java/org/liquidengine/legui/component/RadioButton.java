package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.theme.Theme;

import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.CLICK;
import static org.liquidengine.legui.font.FontRegister.MATERIAL_ICONS_REGULAR;

/**
 * RadioButtons create a series of items where only one item can be
 * selected.
 * <p>
 * By default all created radio buttons have no group
 * so all of them can be selected.
 * <p>Usage example:</p>
 * <pre>{@code
 * RadioButtonGroup rbg = new RadioButtonGroup();
 * RadioButton      rb1 = new RadioButton();
 * RadioButton      rb2 = new RadioButton();
 * radioButton1.setRadioButtonGroup(radioButtonGroup);
 * radioButton2.setRadioButtonGroup(radioButtonGroup);
 * }</pre>
 */
public class RadioButton extends Controller implements TextComponent {
    /**
     * Used to represent text state of radio button.
     */
    protected TextState textState;

    private String    iconFont           = MATERIAL_ICONS_REGULAR;
    private int       iconUnchecked      = 0xE836;
    private int       iconChecked        = 0xE837;
    private ImageView iconImageUnchecked = null;
    private ImageView iconImageChecked   = null;
    /**
     * Used to represent if radio button selected or not.
     */
    private boolean          selected;
    /**
     * Used to determine group of radio buttons where only one can be selected.
     */
    private RadioButtonGroup radioButtonGroup;

    /**
     * Default constructor. Used to create component instance without any parameters.
     * <p>
     * Also if you want to make it easy to use with
     * Json marshaller/unmarshaller component should contain empty constructor.
     */
    public RadioButton() {
        this("RadioButton");
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param x      x position position in parent component.
     * @param y      y position position in parent component.
     * @param width  width of component.
     * @param height height of component.
     */
    public RadioButton(float x, float y, float width, float height) {
        this("RadioButton", x, y, width, height);
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component.
     * @param size     size of component.
     */
    public RadioButton(Vector2f position, Vector2f size) {
        this("RadioButton", position, size);
    }

    /**
     * Default constructor. Used to create component instance without any parameters.
     * <p>
     * Also if you want to make it easy to use with
     * Json marshaller/unmarshaller component should contain empty constructor.
     *
     * @param text text to set.
     */
    public RadioButton(String text) {
        initialize(text);
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param text   text to set.
     * @param x      x position position in parent component.
     * @param y      y position position in parent component.
     * @param width  width of component.
     * @param height height of component.
     */
    public RadioButton(String text, float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize(text);
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param text     text to set.
     * @param position position position in parent component.
     * @param size     size of component.
     */
    public RadioButton(String text, Vector2f position, Vector2f size) {
        super(position, size);
        initialize(text);
    }

    /**
     * Used to initialize radio button.
     *
     * @param text text to set.
     */
    private void initialize(String text) {
        textState = new TextState(text);
        setBorder(null);
        getListenerMap().addListener(MouseClickEvent.class, new RadioButtonClickEventListener());
        Theme.getDefaultTheme().getThemeManager().getComponentTheme(RadioButton.class).apply(this);
    }

    /**
     * Returns true if radio button is selected.
     *
     * @return true if radio button is selected.
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Used to set radio button selected or not.
     *
     * @param selected true if it should be selected.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
        if (radioButtonGroup != null) {
            radioButtonGroup.setSelection(this, selected);
        }
    }

    /**
     * Returns current radio button group.
     *
     * @return current radio button group.
     */
    public RadioButtonGroup getRadioButtonGroup() {
        return radioButtonGroup;
    }

    /**
     * Used to set  radio button group.
     *
     * @param radioButtonGroup radio button group to set.
     */
    public void setRadioButtonGroup(RadioButtonGroup radioButtonGroup) {
        if (this.radioButtonGroup != null) {
            this.radioButtonGroup.remove(this);
        }
        this.radioButtonGroup = radioButtonGroup;
        this.radioButtonGroup.add(this);
        if (selected) {
            if (radioButtonGroup.getSelection() != null) {
                selected = false;
            } else {
                radioButtonGroup.setSelection(this, true);
            }
        }
    }

    /**
     * Returns font used for drawing radio icon.
     *
     * @return font used for drawing radio icon.
     */
    public String getIconFont() {
        return iconFont;
    }

    /**
     * Used to set font for drawing radio icon.
     *
     * @param iconFont font to set.
     */
    public void setIconFont(String iconFont) {
        this.iconFont = iconFont;
    }

    /**
     * Returns radio icon for non-selected state.
     *
     * @return radio icon for non-selected state.
     */
    public int getIconUnchecked() {
        return iconUnchecked;
    }

    /**
     * Used to set radio icon for non-selected state.
     *
     * @param iconUnchecked radio icon for non-selected state to set.
     */
    public void setIconUnchecked(int iconUnchecked) {
        this.iconUnchecked = iconUnchecked;
    }

    /**
     * Returns radio icon for selected state.
     *
     * @return radio icon for selected state.
     */
    public int getIconChecked() {
        return iconChecked;
    }

    /**
     * Used to set radio icon for selected state.
     *
     * @param iconChecked radio icon for selected state to set.
     */
    public void setIconChecked(int iconChecked) {
        this.iconChecked = iconChecked;
    }

    /**
     * Returns radio image for non-selected state.
     *
     * @return radio image for non-selected state.
     */
    public ImageView getIconImageUnchecked() {
        return iconImageUnchecked;
    }

    /**
     * Used to set radio image for non-selected state.
     *
     * @param iconImageUnchecked radio image for non-selected state to set.
     */
    public void setIconImageUnchecked(ImageView iconImageUnchecked) {
        this.iconImageUnchecked = iconImageUnchecked;
    }

    /**
     * Returns radio image for selected state.
     *
     * @return radio image for selected state.
     */
    public ImageView getIconImageChecked() {
        return iconImageChecked;
    }

    /**
     * Used to set radio image for selected state.
     *
     * @param iconImageChecked radio image for selected state to set.
     */
    public void setIconImageChecked(ImageView iconImageChecked) {
        this.iconImageChecked = iconImageChecked;
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
     * (non-Javadoc)
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RadioButton that = (RadioButton) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(selected, that.selected)
                .append(textState, that.textState)
                .append(radioButtonGroup, that.radioButtonGroup)
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
                .append(selected)
                .append(radioButtonGroup)
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
                .append("selected", selected)
                .append("radioButtonGroup", radioButtonGroup)
                .toString();
    }

    public static class RadioButtonClickEventListener implements MouseClickEventListener {

        @Override
        public void process(MouseClickEvent event) {
            if (event.getAction() == CLICK) {
                RadioButton component = (RadioButton) event.getComponent();
                component.setSelected(true);
            }
        }

        @Override
        public boolean equals(Object obj) {
            return true;
        }
    }
}
