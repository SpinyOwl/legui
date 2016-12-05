package org.liquidengine.legui.util;

import java.util.Arrays;

/**
 * Created by Alexander on 05.12.2016.
 */
public final class TextUtil {
    public static final char wordBreakers[] = {
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

    private TextUtil() {
    }

    public static int findNextWord(String text, int current) {
        int length = text.length();
        if (current == length) return length;

        int next = current + 1;
        boolean word;
        boolean startedWord = false;
        while (next < length) {
            int i = Arrays.binarySearch(wordBreakers, text.charAt(next));
            // if char at (next) is word breaker
            if (i < 0) {
                startedWord = word = true;
            } else {
                word = false;
            }
            if (!word && startedWord) break;
            next++;
        }
        return next;
    }

    public static int findPrevWord(String text, int current) {
        if (current == 0) return 0;

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
            if (!word && startedWord) break;
            prev--;
        }
        return prev;
    }

}
