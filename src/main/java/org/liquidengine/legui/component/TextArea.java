package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.liquidengine.legui.component.border.SimpleLineBorder;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.util.ColorConstants;

/**
 * Created by Alexander on 09.10.2016.
 */
public class TextArea extends Component {
    protected TextState textState;

    protected int caretPosition;
    protected int mouseCaretPosition;
    protected boolean editable = true;

    public TextArea() {
        initialize("");
    }

    public TextArea(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize("");
    }

    public TextArea(Vector2f position, Vector2f size) {
        super(position, size);
        initialize("");
    }

    private void initialize(String s) {
        backgroundColor.set(1);
        cornerRadius = 3;
        border = new SimpleLineBorder(ColorConstants.darkGray(), 1);
        textState = new TextState(s);
        textState.getPadding().set(5, 10, 5, 10);
    }

    public TextState getTextState() {
        return textState;
    }

    public int getCaretPosition() {
        return caretPosition;
    }

    public void setCaretPosition(int caretPosition) {
        int length = getTextState().getText().length();
        this.caretPosition = caretPosition < 0 ? 0 : caretPosition > length ? length : caretPosition;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public int getMouseCaretPosition() {
        return mouseCaretPosition;
    }

    public void setMouseCaretPosition(int mouseCaretPosition) {
        this.mouseCaretPosition = mouseCaretPosition;
    }
}
