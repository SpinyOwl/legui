package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Group of radio buttons which determines that only one radio button can be selected in group.
 */
public class RadioButtonGroup implements Serializable {

    /**
     * Used to hold radio buttons.
     */
    private List<RadioButton> radioButtons = new CopyOnWriteArrayList<>();
    /**
     * Currently selected radio button.
     */
    private RadioButton selection;

    /**
     * Used to add radio button to group.
     *
     * @param radioButton radio button to add.
     */
    protected void add(RadioButton radioButton) {
        if (radioButtons.contains(radioButton)) return;
        radioButtons.add(radioButton);
        if (radioButton.isChecked()) {
            if (selection != null) {
                selection.setChecked(false);
            }
            selection = radioButton;
        }
    }

    /**
     * Used to remove radio button to group.
     *
     * @param radioButton radio button to remove.
     */
    protected void remove(RadioButton radioButton) {
        radioButtons.remove(radioButton);
        if (radioButton == selection) {
            selection = null;
        }
    }

    /**
     * Used to get selected radio button.
     *
     * @return selected radio button.
     */
    public RadioButton getSelection() {
        return selection;
    }


    /**
     * Used to deselect all of radio buttons.
     */
    public void clearSelection() {
        if (selection != null) {
            RadioButton oldSelection = selection;
            selection = null;
            oldSelection.setChecked(false);
        }
    }

    /**
     * Used to change selected radio button.
     *
     * @param radioButton radio button to select.
     * @param selected    flag to set for provider radio button.
     */
    public void setSelection(RadioButton radioButton, boolean selected) {
        if (selected && radioButton != null && radioButton != selection) {
            RadioButton oldSelection = selection;
            selection = radioButton;
            if (oldSelection != null) {
                oldSelection.setChecked(false);
            }
            radioButton.setChecked(true);
        }
    }

    /**
     * Returns true if provided radio button selected.
     *
     * @param radioButton radio button to check.
     * @return true if provided radio button selected.
     */
    public boolean isSelected(RadioButton radioButton) {
        return radioButton == selection;
    }

    /**
     * Returns all radio buttons in group.
     *
     * @return all radio buttons in group.
     */
    public List<RadioButton> getRadioButtons() {
        return new ArrayList<>(radioButtons);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RadioButtonGroup that = (RadioButtonGroup) o;

        return new EqualsBuilder()
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .toHashCode();
    }
}