package org.liquidengine.legui.component;

import static org.liquidengine.legui.style.font.FontRegistry.MATERIAL_ICONS_REGULAR;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.misc.listener.radiobutton.RadioButtonClickEventListener;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.theme.Themes;

/**
 * RadioButtons create a series of items where only one item can be checked. <p> By default all created radio buttons have no group so all of them can be
 * checked. <p>Usage example:</p>
 * <pre>{@code
 * RadioButtonGroup rbg = new RadioButtonGroup();
 * RadioButton      rb1 = new RadioButton();
 * RadioButton      rb2 = new RadioButton();
 * radioButton1.setRadioButtonGroup(radioButtonGroup);
 * radioButton2.setRadioButtonGroup(radioButtonGroup);
 * }</pre>
 */
public class RadioButton extends Component implements TextComponent {

    /**
     * Default text for radiobutton.
     */
    public static final String DEFAULT_RADIO_BUTTON_TEXT = "RadioButton";
    /**
     * Used to represent text state of radio button.
     */
    protected TextState textState;

    /**
     * Used to render unchecked state of radio button.
     */
    private Icon iconUnchecked = new CharIcon(new Vector2f(14, 14), MATERIAL_ICONS_REGULAR, (char) 0xE836);

    /**
     * Used to render checked state of radio button.
     */
    private Icon iconChecked = new CharIcon(new Vector2f(14, 14), MATERIAL_ICONS_REGULAR, (char) 0xE837);

    /**
     * Used to represent if radio button checked or not.
     */
    private boolean checked;
    /**
     * Used to determine group of radio buttons where only one can be checked.
     */
    private RadioButtonGroup radioButtonGroup;

    /**
     * Default constructor. Used to create component instance without any parameters. <p> Also if you want to make it easy to use with Json
     * marshaller/unmarshaller component should contain empty constructor.
     */
    public RadioButton() {
        this(DEFAULT_RADIO_BUTTON_TEXT);
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
        this(DEFAULT_RADIO_BUTTON_TEXT, x, y, width, height);
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component.
     * @param size     size of component.
     */
    public RadioButton(Vector2f position, Vector2f size) {
        this(DEFAULT_RADIO_BUTTON_TEXT, position, size);
    }

    /**
     * Default constructor. Used to create component instance without any parameters. <p> Also if you want to make it easy to use with Json
     * marshaller/unmarshaller component should contain empty constructor.
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
        getStyle().setBorder(null);
        getListenerMap().addListener(MouseClickEvent.class, new RadioButtonClickEventListener());
        iconUnchecked.setHorizontalAlign(HorizontalAlign.LEFT);
        iconChecked.setHorizontalAlign(HorizontalAlign.LEFT);
        Themes.getDefaultTheme().getThemeManager().getComponentTheme(RadioButton.class).applyAll(this);
    }

    /**
     * Returns true if radio button is checked.
     *
     * @return true if radio button is checked.
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * Used to set radio button checked or not.
     *
     * @param checked true if it should be checked.
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
        if (radioButtonGroup != null) {
            radioButtonGroup.setSelection(this, checked);
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
        if (checked) {
            if (radioButtonGroup.getSelection() != null) {
                checked = false;
            } else {
                radioButtonGroup.setSelection(this, true);
            }
        }
    }

    /**
     * Returns radio image for non-checked state.
     *
     * @return radio image for non-checked state.
     */
    public Icon getIconUnchecked() {
        return iconUnchecked;
    }

    /**
     * Used to set radio image for non-checked state.
     *
     * @param iconUnchecked radio image for non-checked state to set.
     */
    public void setIconUnchecked(Icon iconUnchecked) {
        this.iconUnchecked = iconUnchecked;
    }

    /**
     * Returns radio image for checked state.
     *
     * @return radio image for checked state.
     */
    public Icon getIconChecked() {
        return iconChecked;
    }

    /**
     * Used to set radio image for checked state.
     *
     * @param iconChecked radio image for checked state to set.
     */
    public void setIconChecked(Icon iconChecked) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RadioButton that = (RadioButton) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(checked, that.checked)
                .append(textState, that.textState)
                .append(radioButtonGroup, that.radioButtonGroup)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(textState)
                .append(checked)
                .append(radioButtonGroup)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("textState", textState)
                .append("checked", checked)
                .append("radioButtonGroup", radioButtonGroup)
                .toString();
    }

}
