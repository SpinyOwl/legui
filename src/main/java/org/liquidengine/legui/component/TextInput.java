package org.liquidengine.legui.component;

import org.liquidengine.legui.component.border.SimpleLineBorder;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.util.ColorConstants;

/**
 * Created by Shcherbin Alexander on 4/25/2016.
 */
public class TextInput extends Component {

    protected TextState textState;

    protected int caretPosition;
    protected int mouseCaretPosition;

    protected int leftSelectionIndex;
    protected int rightSelectionIndex;

    protected boolean editable = true;

    public TextInput() {
        initialize("TextInput");
    }

    public TextInput(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize("TextInput");
    }

    public TextInput(String text, float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize(text);
    }

    public TextInput(String text) {
        initialize(text);
    }

    public int getMouseCaretPosition() {
        return mouseCaretPosition;
    }

    public void setMouseCaretPosition(int mouseCaretPosition) {
        this.mouseCaretPosition = mouseCaretPosition;
    }

    private void initialize(String text) {
        textState = new TextState(text);
        textState.getPadding().set(10,5,10,5);
        backgroundColor.set(1, 1, 1, 0.8f);
        border = new SimpleLineBorder(this, ColorConstants.black(), 1);
        cornerRadius = 2;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public int getCaretPosition() {
        return caretPosition;
    }

    public void setCaretPosition(int caretPosition) {
        this.caretPosition = caretPosition;
    }

    public int getLeftSelectionIndex() {
        return leftSelectionIndex;
    }

    public void setLeftSelectionIndex(int leftSelectionIndex) {
        this.leftSelectionIndex = leftSelectionIndex;
    }

    public int getRightSelectionIndex() {
        return rightSelectionIndex;
    }

    public void setRightSelectionIndex(int rightSelectionIndex) {
        this.rightSelectionIndex = rightSelectionIndex;
    }

    public TextState getTextState() {
        return textState;
    }
}
