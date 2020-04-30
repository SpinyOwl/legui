package org.liquidengine.legui.component.misc.listener;

import org.liquidengine.legui.component.TextComponent;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.system.Clipboard;

import java.util.function.BiConsumer;

public final class TextComponentShortcutUtil {
    private TextComponentShortcutUtil() {
    }

    public static void paste(TextComponent textComponent, BiConsumer<String, String> contentChangeEventGenerator) {
        TextState textState = textComponent.getTextState();
        if (textState.isEditable()) {
            int caretPosition = textState.getCaretPosition();
            String s = Clipboard.getInstance().getClipboardString();
            if (s != null) {

                int start = textState.getStartSelectionIndex();
                int end = textState.getEndSelectionIndex();
                if (start > end) {
                    start = textState.getEndSelectionIndex();
                    end = textState.getStartSelectionIndex();
                }
                if (start != end) {
                    StringBuilder t = new StringBuilder(textState.getText());
                    t.delete(start, end);
                    textState.setText(t.toString());
                    textState.setCaretPosition(start);
                    textState.setStartSelectionIndex(start);
                    textState.setEndSelectionIndex(start);
                    caretPosition = start;
                }

                String oldText = textState.getText();
                StringBuilder builder = new StringBuilder(textState.getText());
                builder.insert(caretPosition, s);
                textState.setText(builder.toString());
                int newIndex = caretPosition + s.length();
                textState.setCaretPosition(newIndex);
                textState.setStartSelectionIndex(newIndex);
                textState.setEndSelectionIndex(newIndex);
                String newText = textState.getText();
                if (contentChangeEventGenerator != null) {
                    contentChangeEventGenerator.accept(oldText, newText);
                }
            }
        }

    }

    public static void copy(TextComponent textComponent) {
        String s = textComponent.getTextState().getSelection();
        if (s != null) {
            Clipboard.getInstance().setClipboardString(s);
        }
    }


    public static void cut(TextComponent textComponent, BiConsumer<String, String> contentChangeEventGenerator) {
        TextState textState = textComponent.getTextState();
        String s = textState.getSelection();
        if (s != null) {
            if (textState.isEditable()) {
                int start = textState.getStartSelectionIndex();
                int end = textState.getEndSelectionIndex();
                if (start > end) {
                    int swap = start;
                    start = end;
                    end = swap;
                }
                String oldText = textState.getText();
                StringBuilder builder = new StringBuilder(textState.getText());
                builder.delete(start, end);
                textState.setText(builder.toString());
                textState.setCaretPosition(start);
                textState.setStartSelectionIndex(start);
                textState.setEndSelectionIndex(start);
                String newText = textState.getText();
                if (contentChangeEventGenerator != null) {
                    contentChangeEventGenerator.accept(oldText, newText);
                }
            }
            Clipboard.getInstance().setClipboardString(s);
        }
    }

}
