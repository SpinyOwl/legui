package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.liquidengine.legui.theme.Themes;

/**
 * This component used to enter passwords securely by users.
 */
public class PasswordInput extends TextInput {
    /**
     * Used to mask password.
     */
    private int maskCharacter = 0x002A;

    /**
     * Default constructor. Used to create component instance without any parameters.
     * <p>
     * Also if you want to make it easy to use with
     * Json marshaller/unmarshaller component should contain empty constructor.
     */
    public PasswordInput() {
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param x      x position position in parent component.
     * @param y      y position position in parent component.
     * @param width  width of component.
     * @param height height of component.
     */
    public PasswordInput(float x, float y, float width, float height) {
        super(x, y, width, height);
        Themes.getDefaultTheme().getThemeManager().applyAll(this);
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component.
     * @param size     size of component.
     */
    public PasswordInput(Vector2f position, Vector2f size) {
        super(position, size);
        Themes.getDefaultTheme().getThemeManager().applyAll(this);
    }

    /**
     * Default constructor with text to set.
     * <p>
     * Also if you want to make it easy to use with
     * Json marshaller/unmarshaller component should contain empty constructor.
     *
     * @param text text to set.
     */
    public PasswordInput(String text) {
        super(text);
        Themes.getDefaultTheme().getThemeManager().applyAll(this);
    }

    /**
     * Constructor with text, position and size parameters.
     *
     * @param text   text to set.
     * @param x      x position position in parent component.
     * @param y      y position position in parent component.
     * @param width  width of component.
     * @param height height of component.
     */
    public PasswordInput(String text, float x, float y, float width, float height) {
        super(text, x, y, width, height);
    }

    /**
     * Constructor with text, position and size parameters.
     *
     * @param text     text to set.
     * @param position position position in parent component.
     * @param size     size of component.
     */
    public PasswordInput(String text, Vector2f position, Vector2f size) {
        super(text, position, size);
    }

    /**
     * Returns mask character.
     *
     * @return mask character.
     */
    public int getMaskCharacter() {
        return maskCharacter;
    }

    /**
     * Used to set mask character.
     *
     * @param maskCharacter mask character to set.
     */
    public void setMaskCharacter(int maskCharacter) {
        this.maskCharacter = maskCharacter;
    }
}
