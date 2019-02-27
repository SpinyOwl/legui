package org.liquidengine.legui.util;

import java.util.Arrays;

/**
 * Text utility.
 */
public class TextUtil {

    /**
     * Array of word breakers which used to split string into words.
     */
    private static char[] wordBreakers = {
            ' ', ',', '.', '!',
            '@', '#', '$', '%',
            '^', '&', '*', '(',
            ')', '-', '=', '+',
            '`', '~', ';', ':',
            '"', '\'', '/', '?',
            '\\', '[', ']', '{',
            '}', '<', '>', '|',
            '\n', '\r', '\t'
    };


    static {
        Arrays.sort(wordBreakers);
    }

    /**
     * Private constructor to avoid creation instances of utility class.
     */
    private TextUtil() {
    }

    /**
     * Used to find next word start index from current character index in provided string.
     *
     * @param text    string to search next word start index.
     * @param current current character index(current caret position in text).
     * @return character index where starts next word.
     */
    public static int findNextWord(String text, int current) {
        int length = text.length();
        if (current == length) {
            return length;
        }

        int next = current;
        boolean word;
        boolean startedWord = false;
        while (next < length) {
            int i = Arrays.binarySearch(wordBreakers, text.charAt(next));
            // if char at (next) is not word breaker
            if (i < 0) {
                startedWord = word = true;
            } else {
                word = false;
            }
            if (!word && startedWord) {
                break;
            }
            next++;
        }
        return next;
    }

    /**
     * Used to find previous word start index from current character index in provided string.
     *
     * @param text    string to search previous word start index.
     * @param current current character index(current caret position in text).
     * @return character index where starts previous word.
     */
    public static int findPrevWord(String text, int current) {
        if (current == 0) {
            return 0;
        }

        int prev = current;
        boolean word;
        boolean startedWord = false;
        while (prev > 0) {
            int i = Arrays.binarySearch(wordBreakers, text.charAt(prev - 1));
            // if char at (prev) is word breaker
            if (i < 0) {
                startedWord = word = true;
            } else {
                word = false;
            }
            if (!word && startedWord) {
                break;
            }
            prev--;
        }
        return prev;
    }

    /**
     * Used to convert character codepoint to string.
     *
     * @param cp codepoint.
     * @return string from codepoint.
     */
    public static String cpToStr(int cp) {
        return new String(Character.toChars(cp));
    }

}
