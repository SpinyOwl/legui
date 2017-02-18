package org.liquidengine.legui.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Group of radio buttons which determines that only one radio button can be selected in group.
 */
public class RadioButtonGroup implements Serializable {

    /**
     * Used to hold radio buttons.
     */
    private Set<RadioButton> radioButtons = new CopyOnWriteArraySet<>();
    /**
     * Currently selected radio button.
     */
    private RadioButton selection;

    /**
     * Used to add radio button to group.
     *
     * @param radioButton radio button to add.
     */
    void add(RadioButton radioButton) {
        radioButtons.add(radioButton);
        if (radioButton.isSelected()) {
            if (selection != null) {
                selection.setSelected(false);
            }
            selection = radioButton;
        }
    }

    /**
     * Used to remove radio button to group.
     *
     * @param radioButton radio button to remove.
     */
    void remove(RadioButton radioButton) {
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
            oldSelection.setSelected(false);
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
                oldSelection.setSelected(false);
            }
            radioButton.setSelected(true);
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

}