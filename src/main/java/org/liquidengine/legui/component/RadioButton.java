package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;

import static org.liquidengine.legui.event.MouseClickEvent.CLICK;

/**
 * Created by Aliaksandr_Shcherbin on 2/6/2017.
 */
public class RadioButton extends Controller {
    protected TextState        textState;
    protected boolean          selected;
    protected RadioButtonGroup radioButtonGroup;

    public RadioButton(float x, float y, float width, float height) {
        this("RadioButton", x, y, width, height);
    }

    public RadioButton(String text, float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize(text);
    }

    public RadioButton(String text) {
        initialize(text);
    }

    public RadioButton() {
        this("RadioButton");
    }

    private void initialize(String text) {
        textState = new TextState(text);
        setBorder(null);
        getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == CLICK) {
                setSelected(true);
            }
        });
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if (radioButtonGroup != null) {
            radioButtonGroup.setSelection(this, selected);
        }
    }

    public RadioButtonGroup getRadioButtonGroup() {
        return radioButtonGroup;
    }

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

    public TextState getTextState() {
        return textState;
    }

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

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(textState)
                .append(selected)
                .append(radioButtonGroup)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("textState", textState)
                .append("selected", selected)
                .append("radioButtonGroup", radioButtonGroup)
                .toString();
    }
}
