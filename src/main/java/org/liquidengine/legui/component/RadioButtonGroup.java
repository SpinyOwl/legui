package org.liquidengine.legui.component;

import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Shcherbin Alexander on 8/22/2016.
 */
public class RadioButtonGroup implements Serializable {

    private SetUniqueList<RadioButton> radioButtons = SetUniqueList.setUniqueList(Collections.emptyList());
    private RadioButton selection;

    public void add(RadioButton radioButton) {
        radioButtons.add(radioButton);
        if (radioButton.isSelected()) {
            if (selection != null) {
                selection.setSelected(false);
            }
            selection = radioButton;
        }
    }

    public void remove(RadioButton radioButton) {
        radioButtons.remove(radioButton);
        if (radioButton == selection) {
            selection = null;
        }
    }

    public RadioButton getSelection() {
        return selection;
    }

    public void clearSelection() {
        if (selection != null) {
            RadioButton oldSelection = selection;
            selection = null;
            oldSelection.setSelected(false);
        }
    }

    public void setSelection(RadioButton radioButton, boolean selected) {
        if (selected && radioButton != null && radioButton != selection) {
            RadioButton oldSelection = selection;
            selection = radioButton;
            if (oldSelection != null) {
                oldSelection.setSelected(false);
            }
            radioButton.setSelected(true);
        }
    }

    public boolean isSelected(RadioButton radioButton) {
        return radioButton == selection;
    }

    public SetUniqueList<RadioButton> getRadioButtons() {
        return (SetUniqueList<RadioButton>) Arrays.asList((RadioButton[]) radioButtons.toArray());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RadioButtonGroup that = (RadioButtonGroup) o;

        return new EqualsBuilder()
                .append(radioButtons, that.radioButtons)
                .append(selection, that.selection)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(radioButtons)
                .append(selection)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("radioButtons", radioButtons)
                .append("selection", selection)
                .toString();
    }
}
