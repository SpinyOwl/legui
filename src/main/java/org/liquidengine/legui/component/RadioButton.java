package org.liquidengine.legui.component;

import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;

/**
 * Created by Shcherbin Alexander on 4/25/2016.
 */
public class RadioButton extends Component {

    protected TextState textState;
    protected boolean selected;
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
        textState.setHorizontalAlign(HorizontalAlign.LEFT);
        textState.setVerticalAlign(VerticalAlign.MIDDLE);
        textState.getPadding().set(0);

        backgroundColor.set(0);
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
    }

    public TextState getTextState() {
        return textState;
    }
}
