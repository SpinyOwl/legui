package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joml.Vector4f;
import org.liquidengine.legui.component.border.SimpleRectangleLineBorder;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.util.ColorConstants;

/**
 * Created by Shcherbin Alexander on 4/25/2016.
 */
public class TextInput extends Component {

    protected TextState textState;

    protected int caretPosition;
    protected int mouseCaretPosition;

    protected int startSelectionIndex;
    protected int endSelectionIndex;

    protected Vector4f selectionColor = ColorConstants.lightBlue();
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
        textState.getPadding().set(5, 1, 5, 1);
        backgroundColor.set(1, 1, 1, 0.8f);
        border = new SimpleRectangleLineBorder(ColorConstants.black(), 1);
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

    public int getStartSelectionIndex() {
        return startSelectionIndex;
    }

    public void setStartSelectionIndex(int startSelectionIndex) {
        this.startSelectionIndex = startSelectionIndex;
    }

    public int getEndSelectionIndex() {
        return endSelectionIndex;
    }

    public void setEndSelectionIndex(int endSelectionIndex) {
        this.endSelectionIndex = endSelectionIndex;
    }

    public String getSelection() {
        if (startSelectionIndex < 0 || endSelectionIndex < 0) return null;
        String selection;
        if (startSelectionIndex > endSelectionIndex) {
            selection = textState.substring(endSelectionIndex, startSelectionIndex);
        } else {
            selection = textState.substring(startSelectionIndex, endSelectionIndex);
        }
        return selection;
    }

    public TextState getTextState() {
        return textState;
    }

    public Vector4f getSelectionColor() {
        return selectionColor;
    }

    public void setSelectionColor(Vector4f selectionColor) {
        this.selectionColor = selectionColor;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("textState", textState)
                .append("caretPosition", caretPosition)
                .append("mouseCaretPosition", mouseCaretPosition)
                .append("startSelectionIndex", startSelectionIndex)
                .append("endSelectionIndex", endSelectionIndex)
                .append("selectionColor", selectionColor)
                .append("editable", editable)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TextInput input = (TextInput) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(caretPosition, input.caretPosition)
                .append(mouseCaretPosition, input.mouseCaretPosition)
                .append(startSelectionIndex, input.startSelectionIndex)
                .append(endSelectionIndex, input.endSelectionIndex)
                .append(editable, input.editable)
                .append(textState, input.textState)
                .append(selectionColor, input.selectionColor)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(textState)
                .append(caretPosition)
                .append(mouseCaretPosition)
                .append(startSelectionIndex)
                .append(endSelectionIndex)
                .append(selectionColor)
                .append(editable)
                .toHashCode();
    }
}
