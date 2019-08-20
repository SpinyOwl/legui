package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.animation.Animation;
import org.liquidengine.legui.component.event.textarea.TextAreaFieldUpdateEvent;
import org.liquidengine.legui.component.misc.animation.textarea.TextAreaScrollAnimation;
import org.liquidengine.legui.component.misc.listener.textarea.TextAreaFieldUpdateListener;
import org.liquidengine.legui.component.misc.listener.textarea.TextAreaViewportScrollListener;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.style.Style.DisplayType;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.length.Length;
import org.liquidengine.legui.theme.Themes;

import static org.liquidengine.legui.style.length.LengthType.pixel;

/**
 * Panel with scroll bars. Default container layout is null.
 */
public class TextArea extends Component implements TextComponent, Viewport {

    /**
     * Initial scrollbar width/height.
     */
    private static final float INITIAL_SCROLL_SIZE = 8f;

    /**
     * Used to scroll panel vertically.
     */
    private ScrollBar verticalScrollBar;

    /**
     * Used to scroll panel horizontally.
     */
    private ScrollBar horizontalScrollBar;

    /**
     * Base container which holds component container. Viewport size is limited by scroll bars and parent ScrollablePanel size.
     */
    private Component viewport;

    private TextAreaField textAreaField;

    /**
     * Scrollable panel animation. Updates container position in viewport.
     */
    private Animation animation;

    /**
     * Default constructor. Used to create component instance without any parameters. <p> Also if you want to make it easy to use with Json
     * marshaller/unmarshaller component should contain empty constructor.
     */
    public TextArea() {
        initialize();
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param x x position position in parent component.
     * @param y y position position in parent component.
     * @param width width of component.
     * @param height height of component.
     */
    public TextArea(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize();
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component.
     * @param size size of component.
     */
    public TextArea(Vector2f position, Vector2f size) {
        super(position, size);
        initialize();
    }

    /**
     * Returns animation of scrollable panel.
     *
     * @return animation.
     */
    public Animation getAnimation() {
        return animation;
    }

    /**
     * Used to set scrollable panel animation. Automatically starts animation.
     *
     * @param animation scroll bar animation to set.
     */
    public void setAnimation(Animation animation) {
        if (this.animation != null) {
            this.animation.stopAnimation();
        }
        this.animation = animation;
        if (animation != null) {
            this.animation.startAnimation();
        }
    }

    private void initialize() {
        this.getStyle().setDisplay(DisplayType.FLEX);

        float viewportWidth = getSize().x - INITIAL_SCROLL_SIZE;
        float viewportHeight = getSize().y - INITIAL_SCROLL_SIZE;

        verticalScrollBar = new ScrollBar();
        verticalScrollBar.getStyle().setWidth(INITIAL_SCROLL_SIZE);
        verticalScrollBar.getStyle().setTop(0f);
        verticalScrollBar.getStyle().setRight(0f);
        verticalScrollBar.getStyle().setBottom(INITIAL_SCROLL_SIZE);
        verticalScrollBar.setOrientation(Orientation.VERTICAL);
        verticalScrollBar.setViewport(this);
        verticalScrollBar.setTabFocusable(false);

        horizontalScrollBar = new ScrollBar();
        horizontalScrollBar.getStyle().setHeight(INITIAL_SCROLL_SIZE);
        horizontalScrollBar.getStyle().setLeft(0f);
        horizontalScrollBar.getStyle().setRight(INITIAL_SCROLL_SIZE);
        horizontalScrollBar.getStyle().setBottom(0f);
        horizontalScrollBar.setOrientation(Orientation.HORIZONTAL);
        horizontalScrollBar.setViewport(this);
        horizontalScrollBar.setTabFocusable(false);

        viewport = new TextAreaViewport(0, 0, viewportWidth, viewportHeight);

        viewport.getStyle().getBackground().setColor(1, 1, 1, 0);
        viewport.getStyle().setBorder(null);
        viewport.getStyle().setTop(0f);
        viewport.getStyle().setLeft(0f);
        viewport.getStyle().setBottom(INITIAL_SCROLL_SIZE);
        viewport.getStyle().setRight(INITIAL_SCROLL_SIZE);
        viewport.getListenerMap().addListener(ScrollEvent.class, new TextAreaViewportScrollListener());
        viewport.setTabFocusable(false);

        textAreaField = new TextAreaField(0, 0, viewportWidth, viewportHeight);

        textAreaField.getStyle().setBorder(null);
        textAreaField.setTabFocusable(false);
        textAreaField.getListenerMap().addListener(TextAreaFieldUpdateEvent.class, new TextAreaFieldUpdateListener(this));
        viewport.add(textAreaField);

        this.add(viewport);
        this.add(verticalScrollBar);
        this.add(horizontalScrollBar);
        this.getStyle().getBackground().setColor(ColorConstants.transparent());

        Themes.getDefaultTheme().getThemeManager().getComponentTheme(TextArea.class).applyAll(this);

        animation = new TextAreaScrollAnimation(this);
        animation.startAnimation();
    }

    /**
     * Returns vertical scrollbar.
     *
     * @return vertical scrollbar.
     */
    public ScrollBar getVerticalScrollBar() {
        return verticalScrollBar;
    }

    /**
     * Used to set vertical scroll bar.
     *
     * @param verticalScrollBar vertical scroll bar to set.
     */
    public void setVerticalScrollBar(ScrollBar verticalScrollBar) {
        this.verticalScrollBar.setViewport(null);
        this.remove(this.verticalScrollBar);
        this.verticalScrollBar = verticalScrollBar;
        this.add(verticalScrollBar);
        this.verticalScrollBar.setViewport(this);
    }

    /**
     * Returns horizontal scrollbar.
     *
     * @return horizontal scrollbar.
     */
    public ScrollBar getHorizontalScrollBar() {
        return horizontalScrollBar;
    }

    /**
     * Used to set horizontal scroll bar.
     *
     * @param horizontalScrollBar horizontal scroll bar to set.
     */
    public void setHorizontalScrollBar(ScrollBar horizontalScrollBar) {
        this.horizontalScrollBar.setViewport(null);
        this.remove(this.horizontalScrollBar);
        this.horizontalScrollBar = horizontalScrollBar;
        this.add(horizontalScrollBar);
        this.horizontalScrollBar.setViewport(this);
    }

    public void setHorizontalScrollBarVisible(boolean enabled) {
        if (enabled) {
            Length height = this.horizontalScrollBar.getStyle().getHeight();
            if (height == null) {
                height = pixel(this.horizontalScrollBar.getSize().y);
                this.horizontalScrollBar.getStyle().setHeight(height);
            }
            this.viewport.getStyle().setBottom(height);
            this.verticalScrollBar.getStyle().setBottom(height);
        } else {
            this.viewport.getStyle().setBottom(0f);
            this.verticalScrollBar.getStyle().setBottom(0f);
        }
        this.horizontalScrollBar.getStyle().setDisplay(enabled ? DisplayType.MANUAL : DisplayType.NONE);
    }

    public void setVerticalScrollBarVisible(boolean enabled) {
        if (enabled) {
            Length width = this.verticalScrollBar.getStyle().getWidth();
            if (width == null) {
                width = pixel(this.verticalScrollBar.getSize().x);
                this.verticalScrollBar.getStyle().setWidth(width);
            }
            this.viewport.getStyle().setRight(width);
            this.horizontalScrollBar.getStyle().setRight(width);
        } else {
            this.viewport.getStyle().setRight(0f);
            this.horizontalScrollBar.getStyle().setRight(0f);
        }
        this.verticalScrollBar.getStyle().setDisplay(enabled ? DisplayType.MANUAL : DisplayType.NONE);
    }

    public void setHorizontalScrollBarHeight(float height) {
        this.horizontalScrollBar.getStyle().setHeight(height);
        this.viewport.getStyle().setBottom(height);
        this.verticalScrollBar.getStyle().setBottom(height);
    }

    public void setVerticalScrollBarWidth(float width) {
        this.verticalScrollBar.getStyle().setWidth(width);
        this.viewport.getStyle().setRight(width);
        this.horizontalScrollBar.getStyle().setRight(width);
    }

    public TextAreaField getTextAreaField() {
        return textAreaField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof TextArea)) {
            return false;
        }

        TextArea panel = (TextArea) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(verticalScrollBar, panel.verticalScrollBar)
            .append(horizontalScrollBar, panel.horizontalScrollBar)
//            .append(viewport, panel.viewport)
            .append(textAreaField, panel.textAreaField)
            .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("verticalScrollBar", verticalScrollBar)
            .append("horizontalScrollBar", horizontalScrollBar)
            .append("viewport", viewport)
            .append("textAreaField", textAreaField)
            .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(verticalScrollBar)
            .append(horizontalScrollBar)
            .append(viewport)
            .append(textAreaField)
            .toHashCode();
    }

    public Component getViewport() {
        return viewport;
    }

    @Override
    public Vector2f getViewportSize() {
        return new Vector2f(viewport.getSize());
    }

    @Override
    public Vector2f getViewportViewSize() {
        return new Vector2f(textAreaField.getSize());
    }

    @Override
    public TextState getTextState() {
        return textAreaField.getTextState();
    }

    /**
     * Returns caret position.
     *
     * @return caret position.
     */
    public int getCaretPosition() {
        return textAreaField.getCaretPosition();
    }

    /**
     * Used to set caret position.
     *
     * @param caretPosition caret position to set.
     */
    public void setCaretPosition(int caretPosition) {
        textAreaField.setCaretPosition(caretPosition);
    }

    /**
     * Returns true if text is editable.
     *
     * @return true if text is editable.
     */
    public boolean isEditable() {
        return textAreaField.isEditable();
    }

    /**
     * Used to set editable text or not.
     *
     * @param editable editable text or not.
     */
    public void setEditable(boolean editable) {
        textAreaField.setEditable(editable);
    }

    /**
     * Returns mouse caret position.
     *
     * @return mouse caret position.
     */
    public int getMouseCaretPosition() {
        return textAreaField.getMouseCaretPosition();
    }

    /**
     * Used to set mouse caret position.
     *
     * @param mouseCaretPosition mouse caret position to set.
     */
    public void setMouseCaretPosition(int mouseCaretPosition) {
        textAreaField.setMouseCaretPosition(mouseCaretPosition);
    }

    /**
     * Returns start selection index.
     *
     * @return start selection index.
     */
    public int getStartSelectionIndex() {
        return textAreaField.getStartSelectionIndex();
    }

    /**
     * Used to set start selection index.
     *
     * @param startSelectionIndex start selection index to set.
     */
    public void setStartSelectionIndex(int startSelectionIndex) {
        textAreaField.setStartSelectionIndex(startSelectionIndex);
    }

    /**
     * Returns end selection index.
     *
     * @return end selection index.
     */
    public int getEndSelectionIndex() {
        return textAreaField.getEndSelectionIndex();
    }

    /**
     * Used to set end selection index.
     *
     * @param endSelectionIndex end selection index to set.
     */
    public void setEndSelectionIndex(int endSelectionIndex) {
        textAreaField.setEndSelectionIndex(endSelectionIndex);
    }

    /**
     * Returns selected text.
     *
     * @return selected text.
     */
    public String getSelection() {
        return textAreaField.getSelection();
    }

    /**
     * Returns tab size in spaces.
     *
     * @return tab size in spaces.
     */
    public int getTabSize() {
        return textAreaField.getTabSize();
    }

    /**
     * Used to set tab size in spaces.
     *
     * @param tabSize tab size in spaces.
     */
    public void setTabSize(int tabSize) {
        textAreaField.setTabSize(tabSize);
    }

    public static class TextAreaViewport extends Panel {

        public TextAreaViewport() {
        }

        public TextAreaViewport(float x, float y, float width, float height) {
            super(x, y, width, height);
        }

        public TextAreaViewport(Vector2f position, Vector2f size) {
            super(position, size);
        }
    }

}
