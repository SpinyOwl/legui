package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.event.FocusEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.FocusEventListener;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.listener.ScrollEventListener;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.CLICK;
import static org.liquidengine.legui.util.TextUtil.cpToStr;

/**
 * Creates drop-down list with select options
 */
public class SelectBox extends Container {
    /**
     * Default value for null element
     */
    public static final  String                 NULL              = "null";
    private static final String                 EXPANDED          = cpToStr(0xE5C7);
    private static final String                 COLLAPSED         = cpToStr(0xE5C5);
    private              List<SelectBoxElement> selectBoxElements = new CopyOnWriteArrayList<>();
    private              List<String>           elements          = new CopyOnWriteArrayList<>();

    private       SelectBoxScrollablePanel selectionListPanel      = new SelectBoxScrollablePanel();
    private final SelectBoxScrollListener  selectBoxScrollListener = new SelectBoxScrollListener(selectionListPanel.getVerticalScrollBar());
    private       Button                   selectionButton         = new Button(NULL);
    private       String                   selectedElement         = null;
    private       float                    elementHeight           = 16;
    private       float                    buttonWidth             = 15f;
    private       int                      visibleCount            = 3;
    private       Button                   expandButton            = new Button(COLLAPSED);
    private       boolean                  collapsed               = true;
    private       Lock                     lock                    = new ReentrantLock(false);

    /**
     * Default constructor. Used to create component instance without any parameters.
     * <p>
     * Also if you want to make it easy to use with
     * Json marshaller/unmarshaller component should contain empty constructor.
     */
    public SelectBox() {
        initialize();
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param x      x position position in parent component.
     * @param y      y position position in parent component.
     * @param width  width of component.
     * @param height height of component.
     */
    public SelectBox(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize();
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component.
     * @param size     size of component.
     */
    public SelectBox(Vector2f position, Vector2f size) {
        super(position, size);
        initialize();
    }

    /**
     * Returns all elements of list.
     *
     * @return all elements of list.
     */
    public List<String> getElements() {
        return elements;
    }

    /**
     * Returns button width.
     *
     * @return button width.
     */
    public float getButtonWidth() {
        return buttonWidth;
    }

    /**
     * Used to set button width.
     *
     * @param buttonWidth button width to set.
     */
    public void setButtonWidth(float buttonWidth) {
        this.buttonWidth = buttonWidth;
    }

    /**
     * Returns selected element.
     *
     * @return selected element.
     */
    public String getSelection() {
        return selectedElement;
    }

    /**
     * Used to initialize selectbox.
     */
    private void initialize() {
        selectionListPanel.getHorizontalScrollBar().setVisible(false);

        this.setBackgroundColor(ColorConstants.white());
        this.setBorder(new SimpleLineBorder(ColorConstants.darkGray(), 1));

        this.add(expandButton);
        this.add(selectionButton);

        expandButton.getTextState().setFont(FontRegister.MATERIAL_ICONS_REGULAR);
        MouseClickEventListener mouseClickEventListener = new SelectBoxClickListener();
        selectionButton.getListenerMap().addListener(MouseClickEvent.class, mouseClickEventListener);
        expandButton.getListenerMap().addListener(MouseClickEvent.class, mouseClickEventListener);

        FocusEventListener focusEventListener = new SelectBoxFocusListener();
        selectionListPanel.getVerticalScrollBar().getListenerMap().getListeners(FocusEvent.class).add(focusEventListener);
        selectionButton.getListenerMap().getListeners(FocusEvent.class).add(focusEventListener);
        expandButton.getListenerMap().getListeners(FocusEvent.class).add(focusEventListener);

        resize();
    }

    /**
     * Returns count of visible elements in expanded state.
     *
     * @return count of visible elements in expanded state.
     */
    public int getVisibleCount() {
        return visibleCount;
    }

    /**
     * Used to set count of visible elements.
     *
     * @param visibleCount count of visible elements to set.
     */
    public void setVisibleCount(int visibleCount) {
        this.visibleCount = visibleCount;
    }

    /**
     * Returns true if selectbox is collapsed and false if expanded.
     *
     * @return true if selectbox is collapsed and false if expanded.
     */
    public boolean isCollapsed() {
        return collapsed;
    }

    /**
     * Used to set selectbox to collapsed or expanded state.
     *
     * @param collapsed collapsed state to set.
     */
    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
        if (collapsed) {
            getParent().remove(selectionListPanel);
        } else {
            getParent().add(selectionListPanel);
            selectionListPanel.getVerticalScrollBar().setCurValue(0);
        }
        resize();
    }

    /**
     * Used to resize selectbox.
     */
    private void resize() {
        selectionButton.setPosition(0, 0);
        selectionButton.setSize(getSize().x - buttonWidth, getSize().y);

        expandButton.getTextState().setText(collapsed ? COLLAPSED : EXPANDED);
        expandButton.setPosition(getSize().x - buttonWidth, 0);
        expandButton.setSize(buttonWidth, getSize().y);
        selectionListPanel.getVerticalScrollBar().getSize().x = buttonWidth;

        for (int i = 0; i < selectBoxElements.size(); i++) {
            SelectBoxElement selectBoxElement = selectBoxElements.get(i);
            selectBoxElement.setSize(new Vector2f(selectionListPanel.getContainer().getSize().x, elementHeight));
            selectBoxElement.setPosition(0, i * elementHeight);
        }


        Vector2f  psize  = new Vector2f();
        Container parent = getParent();
        if (parent != null) psize.set(getParent().getSize());
        Vector2f wsize = new Vector2f(this.getSize().x, visibleCount * elementHeight);
        Vector2f wpos  = new Vector2f();
        if (parent != null && getPosition().y + wsize.y > psize.y) {
            wpos.set(getPosition().x, getPosition().y - wsize.y);
        } else {
            wpos.set(getPosition().x, getPosition().y + getSize().y);
        }
        selectionListPanel.setPosition(wpos);
        selectionListPanel.getContainer().getSize().y = selectionListPanel.getContainer().count() * elementHeight;
        selectionListPanel.resize();

    }

    /**
     * Used to add element to selectbox.
     *
     * @param element element to add.
     */
    public void addElement(String element) {
        lock.lock();
        try {
            if (!elements.contains(element)) {

                SelectBoxElement boxElement = createSelectBoxElement(element);
                if (elements.isEmpty()) selectionButton.getTextState().setText(element);
                boxElement.getListenerMap().addListener(ScrollEvent.class, selectBoxScrollListener);
                elements.add(element);
                selectBoxElements.add(boxElement);
                addSelectBoxComponent(boxElement);
                resize();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Used to create {@link SelectBoxElement}.
     *
     * @param element element.
     * @return {@link SelectBoxElement} created on base of element.
     */
    private SelectBoxElement createSelectBoxElement(String element) {
        SelectBoxElement boxElement = new SelectBoxElement(element, false);
        boxElement.setSize(new Vector2f(selectionListPanel.getContainer().getSize().x, elementHeight));
        boxElement.setPosition(0, selectionListPanel.getContainer().count() * elementHeight);
        boxElement.getListenerMap().getListeners(MouseClickEvent.class).add((MouseClickEventListener) event -> {
            if (event.getAction() == CLICK && event.getButton().equals(Mouse.MouseButton.MOUSE_BUTTON_1)) {
                setSelected(element, true);
                setCollapsed(true);
            }
        });
        return boxElement;
    }

    /**
     * Used to add {@link SelectBoxElement} to selectbox.
     *
     * @param element element to add.
     */
    private void addSelectBoxComponent(SelectBoxElement element) {
        selectionListPanel.getContainer().add(element);
        selectionListPanel.getContainer().getSize().y = selectionListPanel.getContainer().count() * elementHeight;
        selectionListPanel.resize();
    }

    /**
     * Used to get element index.
     *
     * @param element element to find index.
     * @return index of element or -1 if no such element in selectbox.
     */
    public int getElementIndex(String element) {
        return elements.indexOf(element);
    }

    /**
     * Used to remove element from selectbox.
     *
     * @param element element to remove from selectbox.
     */
    public void removeElement(String element) {
        elements.remove(element);
    }

    /**
     * Used to remove element on specified index from selectbox.
     *
     * @param index index of element to remove from selectbox.
     */
    public void removeElement(int index) {
        lock.lock();
        try {
            elements.remove(index);
            selectBoxElements.remove(index);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Used to set selected state of element.
     *
     * @param element  element to set state.
     * @param selected state of element to set.
     */
    public void setSelected(String element, boolean selected) {
        int index = elements.indexOf(element);
        setSelected(element, selected, index);
    }

    /**
     * Used to set selected state of element on specified index.
     *
     * @param index    index of element to set state.
     * @param selected state of element to set.
     */
    public void setSelected(int index, boolean selected) {
        String element = elements.get(index);
        setSelected(element, selected, index);
    }

    private void setSelected(String element, boolean selected, int index) {
        if (selected) {
            if (index != -1) {
                SelectBoxElement tSelectBoxElement = selectBoxElements.get(index);
                tSelectBoxElement.selected = true;
                int selectedIndex = elements.indexOf(selectedElement);
                if (selectedIndex != -1) {
                    selectBoxElements.get(index).selected = false;
                }
                selectedElement = element;
                selectionButton.getTextState().setText(element);
            } else {
                addElement(element);
                setSelected(element, true);
            }
        } else {
            if (index != -1) {
                selectBoxElements.get(index).selected = false;
                if (Objects.equals(element, selectedElement)) selectionButton.getTextState().setText(NULL);
            } else {
                addElement(element);
            }
        }
    }

    /**
     * Returns element height.
     *
     * @return element height.
     */
    public float getElementHeight() {
        return elementHeight;
    }

    /**
     * Used to set element height for all elements in selectbox.
     *
     * @param elementHeight element height to set.
     */
    public void setElementHeight(float elementHeight) {
        this.elementHeight = elementHeight;
        resize();
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("elements", elements)
                .append("selectedElement", selectedElement)
                .append("elementHeight", elementHeight)
                .append("buttonWidth", buttonWidth)
                .append("visibleCount", visibleCount)
                .append("collapsed", collapsed)
                .toString();
    }

    /**
     * (non-Javadoc)
     *
     * @param o
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SelectBox selectBox = (SelectBox) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(elementHeight, selectBox.elementHeight)
                .append(buttonWidth, selectBox.buttonWidth)
                .append(visibleCount, selectBox.visibleCount)
                .append(collapsed, selectBox.collapsed)
                .append(selectBoxElements, selectBox.selectBoxElements)
                .append(elements, selectBox.elements)
                .append(selectionListPanel, selectBox.selectionListPanel)
                .append(selectBoxScrollListener, selectBox.selectBoxScrollListener)
                .append(selectionButton, selectBox.selectionButton)
                .append(selectedElement, selectBox.selectedElement)
                .append(expandButton, selectBox.expandButton)
                .isEquals();
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(selectBoxElements)
                .append(elements)
                .append(selectionListPanel)
                .append(selectBoxScrollListener)
                .append(selectionButton)
                .append(selectedElement)
                .append(elementHeight)
                .append(buttonWidth)
                .append(visibleCount)
                .append(expandButton)
                .append(collapsed)
                .toHashCode();
    }

    /**
     * Default focus listener for selectbox. Used to collapse selectbox if it loses focus.
     */
    public static class SelectBoxFocusListener implements FocusEventListener {

        @Override
        public void process(FocusEvent event) {
            SelectBox box = (SelectBox) event.getComponent();
            if (!event.isFocused() && !box.isCollapsed()) {
                boolean   collapse  = true;
                Component nextFocus = event.getNextFocus();
                for (SelectBoxElement selectBoxElement : box.selectBoxElements) {
                    if (nextFocus == selectBoxElement) {
                        collapse = false;
                    }
                }
                if (nextFocus == box.expandButton ||
                        nextFocus == box.selectionButton ||
                        nextFocus == box.selectionListPanel ||
                        nextFocus == box.selectionListPanel.getVerticalScrollBar()) {
                    collapse = false;
                }
                if (box.collapsed != collapse) {
                    box.setCollapsed(collapse);
                }
            }
        }

        @Override
        public boolean equals(Object obj) {
            return true;
        }
    }

    /**
     * Default mouse click listener for selectbox. Used to collapse selectbox if it loses focus and to expand/collapse if clicked on it.
     */
    public static class SelectBoxClickListener implements MouseClickEventListener {

        @Override
        public void process(MouseClickEvent event) {
            SelectBox box = (SelectBox) event.getComponent();
            if (event.getAction() == CLICK) {
                box.setCollapsed(!box.isCollapsed());
            }
        }

        @Override
        public boolean equals(Object obj) {
            return true;
        }
    }

    /**
     * Selectbox element which is subclass of button.
     */
    public class SelectBoxElement extends Button {
        private boolean selected;

        private SelectBoxElement(String text, boolean selected) {
            super(text == null ? "null" : text);
            this.selected = selected;
            this.setBorder(null);
        }

        /**
         * Returns true if this select box element is currently selected.
         *
         * @return true if this select box element is currently selected.
         */
        public boolean isSelected() {
            return selected;
        }

        /**
         * Used to set this element selected.
         *
         * @param selected
         */
        private void setSelected(boolean selected) {
            this.selected = selected;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                    .append("selected", selected)
                    .toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            SelectBoxElement that = (SelectBoxElement) o;

            return new EqualsBuilder()
                    .appendSuper(super.equals(o))
                    .append(selected, that.selected)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .appendSuper(super.hashCode())
                    .append(selected)
                    .toHashCode();
        }
    }

    /**
     * Scrollable panel of selectbox.
     */
    public class SelectBoxScrollablePanel extends ScrollablePanel<SelectBoxElement> {
        /**
         * Default constructor. Used to create component instance without any parameters.
         * <p>
         * Also if you want to make it easy to use with
         * Json marshaller/unmarshaller component should contain empty constructor.
         */
        public SelectBoxScrollablePanel() {
        }

        /**
         * Constructor with position and size parameters.
         *
         * @param x      x position position in parent component.
         * @param y      y position position in parent component.
         * @param width  width of component.
         * @param height height of component.
         */
        public SelectBoxScrollablePanel(float x, float y, float width, float height) {
            super(x, y, width, height);
        }

        /**
         * Constructor with position and size parameters.
         *
         * @param position position position in parent component.
         * @param size     size of component.
         */
        public SelectBoxScrollablePanel(Vector2f position, Vector2f size) {
            super(position, size);
        }
    }

    /**
     * Listener of scroll action which used to scroll expanded selectbox.
     */
    public class SelectBoxScrollListener implements ScrollEventListener {
        private final ScrollBar bar;

        public SelectBoxScrollListener(ScrollBar bar) {
            this.bar = bar;
        }

        @Override
        public void process(ScrollEvent event) {
            event.getContext().getEventProcessor().pushEvent(new ScrollEvent(bar, event.getContext(), event.getXoffset(), event.getYoffset()));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            SelectBoxScrollListener that = (SelectBoxScrollListener) o;

            return new EqualsBuilder()
                    .append(bar, that.bar)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(bar)
                    .toHashCode();
        }
    }
}
