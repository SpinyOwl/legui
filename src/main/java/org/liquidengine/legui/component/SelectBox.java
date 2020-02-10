package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.event.selectbox.SelectBoxChangeSelectionEvent;
import org.liquidengine.legui.component.event.selectbox.SelectBoxChangeSelectionEventListener;
import org.liquidengine.legui.component.misc.animation.selectbox.SelectBoxAnimation;
import org.liquidengine.legui.component.misc.listener.selectbox.SelectBoxClickListener;
import org.liquidengine.legui.component.misc.listener.selectbox.SelectBoxElementClickListener;
import org.liquidengine.legui.component.misc.listener.selectbox.SelectBoxFocusListener;
import org.liquidengine.legui.component.misc.listener.selectbox.SelectBoxScrollListener;
import org.liquidengine.legui.event.FocusEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.listener.EventListener;
import org.liquidengine.legui.listener.FocusEventListener;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.style.Style.DisplayType;
import org.liquidengine.legui.style.Style.PositionType;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.flex.FlexStyle.FlexDirection;
import org.liquidengine.legui.theme.Themes;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.liquidengine.legui.style.font.FontRegistry.MATERIAL_ICONS_REGULAR;

/**
 * Creates drop-down list with select options. <p> TODO: REIMPLEMENT THIS COMPONENT ACCORDING TO NEW LAYOUT SYSTEM
 */
public class SelectBox<T> extends Component {

    public static final int EXPAND_ICON_CHAR = 0xE5C5;
    public static final int COLLAPSE_ICON_CHAR = 0xE5C7;
    public static final String DEFAULT_ICON_FONT = MATERIAL_ICONS_REGULAR;
    /**
     * Default value for null element.
     */
    private static final String NULL = "null";
    /**
     * Expand button icon (Char icon).
     */
    private Icon expandIcon;
    private Icon collapseIcon;

    private List<SelectBoxElement<T>> selectBoxElements = new CopyOnWriteArrayList<>();
    private List<T> elements = new CopyOnWriteArrayList<>();

    private SelectBoxScrollablePanel selectionListPanel = new SelectBoxScrollablePanel();
    private final SelectBoxScrollListener selectBoxScrollListener = new SelectBoxScrollListener(selectionListPanel.getVerticalScrollBar());
    private Button selectionButton = new Button(NULL);
    private T selectedElement = null;
    private float elementHeight = 16;
    private float buttonWidth = 15f;
    private int visibleCount = 3;
    private Button expandButton = new Button("");
    private boolean collapsed = true;
    private Lock lock = new ReentrantLock(false);

    private SelectBoxLayer selectBoxLayer = new SelectBoxLayer();

    /**
     * Default constructor. Used to create component instance without any parameters. <p> Also if you want to make it easy to use with Json
     * marshaller/unmarshaller component should contain empty constructor.
     */
    public SelectBox() {
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
    public SelectBox(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize();
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component.
     * @param size size of component.
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
    public List<T> getElements() {
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
    public T getSelection() {
        return selectedElement;
    }

    /**
     * Used to initialize selectbox.
     */
    private void initialize() {
        selectionListPanel.setHorizontalScrollBarVisible(false);

        selectionListPanel.getContainer().getStyle().setDisplay(DisplayType.FLEX);
        selectionListPanel.getContainer().getStyle().getFlexStyle().setFlexDirection(FlexDirection.COLUMN);
        selectionListPanel.getContainer().getStyle().getBackground().setColor(ColorConstants.lightBlue());

        this.getStyle().setDisplay(DisplayType.FLEX);

        expandIcon = new CharIcon(new Vector2f(expandButton.getSize()), DEFAULT_ICON_FONT, (char) EXPAND_ICON_CHAR, ColorConstants.black());
        collapseIcon = new CharIcon(new Vector2f(expandButton.getSize()), DEFAULT_ICON_FONT, (char) COLLAPSE_ICON_CHAR, ColorConstants.black());
        expandButton.getStyle().getBackground().setIcon(expandIcon);

        expandButton.getStyle().setMinimumSize(buttonWidth, 0);
        expandButton.getStyle().setMaximumSize(buttonWidth, Float.MAX_VALUE);
        expandButton.getStyle().setRight(0f);
        expandButton.getStyle().setTop(0f);
        expandButton.getStyle().setBottom(0f);

        selectionButton.getStyle().setTop(0f);
        selectionButton.getStyle().setLeft(0f);
        selectionButton.getStyle().setBottom(0f);
        selectionButton.getStyle().setRight(buttonWidth);

        this.add(expandButton);
        this.add(selectionButton);

        MouseClickEventListener mouseClickEventListener = new SelectBoxClickListener<>(this);
        selectionButton.getListenerMap().addListener(MouseClickEvent.class, mouseClickEventListener);
        expandButton.getListenerMap().addListener(MouseClickEvent.class, mouseClickEventListener);
        selectBoxLayer.getContainer().getListenerMap().addListener(MouseClickEvent.class, mouseClickEventListener);

        FocusEventListener focusEventListener = new SelectBoxFocusListener<>(this);
        selectionListPanel.getVerticalScrollBar().getListenerMap().getListeners(FocusEvent.class).add(focusEventListener);
        selectionButton.getListenerMap().getListeners(FocusEvent.class).add(focusEventListener);
        expandButton.getListenerMap().getListeners(FocusEvent.class).add(focusEventListener);

        selectBoxLayer.add(selectionListPanel);

        SelectBoxAnimation animation = new SelectBoxAnimation(this, selectionListPanel);
        animation.startAnimation();

        Themes.getDefaultTheme().getThemeManager().getComponentTheme(SelectBox.class).applyAll(this);
    }

    public Icon getCollapseIcon() {
        return collapseIcon;
    }

    public void setCollapseIcon(Icon collapseIcon) {
        this.collapseIcon = collapseIcon;
        updateIcons();
    }

    public Icon getExpandIcon() {
        return expandIcon;
    }

    public void setExpandIcon(Icon expandIcon) {
        this.expandIcon = expandIcon;
        updateIcons();
    }

    private void updateIcons() {
        expandButton.getStyle().getBackground().setIcon(collapsed ? expandIcon : collapseIcon);
    }

    public Button getExpandButton() {
        return expandButton;
    }

    public Button getSelectionButton() {
        return selectionButton;
    }

    public SelectBoxScrollablePanel getSelectionListPanel() {
        return selectionListPanel;
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
        if (!collapsed) {
            selectionListPanel.getVerticalScrollBar().setCurValue(0);
        }
    }

    /**
     * Used to resize selectbox.
     */
    private void resize() {
        updateIcons();
        for (SelectBoxElement<T> selectBoxElement : selectBoxElements) {
            selectBoxElement.getStyle().setMinimumSize(0, elementHeight);
        }
    }


    /**
     * Used to add element to selectbox.
     *
     * @param element element to add.
     */
    public void addElement(T element) {
        lock.lock();
        try {
            if (!elements.contains(element)) {

                SelectBoxElement<T> boxElement = createSelectBoxElement(element);
                if (elements.isEmpty()) {
                    selectedElement = element;
                    selectionButton.getTextState().setText(element == null ? NULL : element.toString());
                }
                elements.add(element);
                selectBoxElements.add(boxElement);
                selectionListPanel.getContainer().add(boxElement);
                selectionListPanel.getContainer().getSize().y = selectBoxElements.size() * elementHeight;
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
    private SelectBoxElement<T> createSelectBoxElement(T element) {
        SelectBoxElement<T> boxElement = new SelectBoxElement<>(element, false);
        boxElement.getStyle().setHeight(elementHeight);
        boxElement.getStyle().setMinHeight(elementHeight);
        boxElement.getStyle().setPosition(PositionType.RELATIVE);
        boxElement.getListenerMap().getListeners(MouseClickEvent.class).add(new SelectBoxElementClickListener<>(this));
        return boxElement;
    }

    /**
     * Used to get element index.
     *
     * @param element element to find index.
     * @return index of element or -1 if no such element in selectbox.
     */
    public int getElementIndex(T element) {
        return elements.indexOf(element);
    }

    /**
     * Used to remove element from selectbox.
     *
     * @param element element to remove from selectbox.
     */
    public void removeElement(T element) {
        removeElement(elements.indexOf(element));
    }

    /**
     * Used to remove element on specified index from selectbox.
     *
     * @param index index of element to remove from selectbox.
     */
    public void removeElement(int index) {
        lock.lock();
        try {
            if (!elements.isEmpty()) {
                T s = elements.get(index);
                elements.remove(index);
                SelectBoxElement<T> element = selectBoxElements.get(index);
                selectBoxElements.remove(index);
                selectionListPanel.getContainer().remove(element);
                for (int i = index; i < selectBoxElements.size(); i++) {
                    selectBoxElements.get(i).getPosition().y -= elementHeight;
                }
                if (selectedElement == s) {
                    setSelected(0, true);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Used to set selected state of element.
     *
     * @param element element to set state.
     * @param selected state of element to set.
     */
    public void setSelected(T element, boolean selected) {
        int index = elements.indexOf(element);
        setSelected(element, selected, index);
    }

    /**
     * Used to set selected state of element on specified index.
     *
     * @param index index of element to set state.
     * @param selected state of element to set.
     */
    public void setSelected(int index, boolean selected) {
        if (elements.size() > 0) {
            T element = elements.get(index);
            setSelected(element, selected, index);
        } else {
            selectedElement = null;
            selectionButton.getTextState().setText(NULL);
        }
    }

    private void setSelected(T element, boolean selected, int index) {
        if (selected) {
            if (index != -1) {
                SelectBoxElement<T> tSelectBoxElement = selectBoxElements.get(index);
                tSelectBoxElement.selected = true;
                int selectedIndex = elements.indexOf(selectedElement);
                if (selectedIndex != -1) {
                    selectBoxElements.get(index).selected = false;
                }
                selectedElement = element;
                selectionButton.getTextState().setText(element == null ? NULL : element.toString());
            } else {
                addElement(element);
                setSelected(element, true);
            }
        } else {
            if (index != -1) {
                selectBoxElements.get(index).selected = false;
                if (Objects.equals(element, selectedElement)) {
                    selectionButton.getTextState().setText(NULL);
                }
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
     * Used to add event listener for select box change selection event.
     *
     * @param eventListener event listener to add.
     */
    public void addSelectBoxChangeSelectionEventListener(EventListener<SelectBoxChangeSelectionEvent<T>> eventListener) {
        this.getListenerMap().addListener(SelectBoxChangeSelectionEvent.class, (EventListener) eventListener);
    }

    /**
     * Returns all event listeners for select box change selection event.
     *
     * @return all event listeners for select box change selection event.
     */
    public List<EventListener<SelectBoxChangeSelectionEvent<T>>> getSelectBoxChangeSelectionEvents() {
        return List.class.cast(this.getListenerMap().getListeners(SelectBoxChangeSelectionEvent.class));
    }

    /**
     * Used to remove event listener for select box change selection event.
     *
     * @param eventListener event listener to remove.
     */
    public void removeSelectBoxChangeSelectionEventListener(SelectBoxChangeSelectionEventListener<T> eventListener) {
        this.getListenerMap().removeListener(SelectBoxChangeSelectionEvent.class, (EventListener) eventListener);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("elements", elements)
            .append("selectedElement", selectedElement)
            .append("elementHeight", elementHeight)
            .append("buttonWidth", buttonWidth)
            .append("visibleCount", visibleCount)
            .append("collapsed", collapsed)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

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
     * Gets select box elements.
     *
     * @return the select box elements
     */
    public List<SelectBoxElement<T>> getSelectBoxElements() {
        return selectBoxElements;
    }

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

    public SelectBoxLayer getSelectBoxLayer() {
        return selectBoxLayer;
    }

    /**
     * Selectbox element which is subclass of button.
     */
    public class SelectBoxElement<T> extends Button {

        private boolean selected;
        private T object;

        private SelectBoxElement(T object, boolean selected) {
            super(object == null ? "null" : object.toString());
            this.selected = selected;
            this.object = object;
            this.getStyle().setBorder(null);

            Themes.getDefaultTheme().apply(this);
        }

        public T getObject() {
            return object;
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
         * @param selected new selected state of select box.
         */
        private void setSelected(boolean selected) {
            this.selected = selected;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("selected", selected)
                .toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            SelectBoxElement that = (SelectBoxElement) o;

            return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(selected, that.selected)
                .append(object, that.object)
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
    public class SelectBoxScrollablePanel extends ScrollablePanel {

    }

}
