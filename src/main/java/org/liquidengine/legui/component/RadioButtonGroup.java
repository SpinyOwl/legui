package org.liquidengine.legui.component;

import org.apache.commons.collections4.list.SetUniqueList;

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

}
