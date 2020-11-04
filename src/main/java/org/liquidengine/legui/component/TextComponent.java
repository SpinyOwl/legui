package org.liquidengine.legui.component;

import org.liquidengine.legui.component.optional.TextState;

/**
 * Created by ShchAlexander on 07.03.2017.
 */
public interface TextComponent {

    /**
     * Returns current text state.
     *
     * @return text state of component.
     */
    TextState getTextState();

    /**
     * Used to set text state
     *
     * @param textState new text state to set.
     * @throws NullPointerException in case if textState is null.
     */
    void setTextState(TextState textState) throws NullPointerException;
}
