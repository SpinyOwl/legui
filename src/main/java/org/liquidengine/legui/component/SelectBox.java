package org.liquidengine.legui.component;

import com.google.common.base.Objects;
import org.apache.commons.collections4.list.SetUniqueList;
import org.joml.Vector2f;
import org.liquidengine.legui.component.border.SimpleRectangleLineBorder;
import org.liquidengine.legui.event.component.FocusEvent;
import org.liquidengine.legui.event.component.MouseClickEvent;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.listener.component.FocusEventListener;
import org.liquidengine.legui.listener.component.MouseClickEventListener;
import org.liquidengine.legui.util.ColorConstants;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.liquidengine.legui.event.component.MouseClickEvent.MouseClickAction.CLICK;
import static org.liquidengine.legui.util.Util.cpToStr;

/**
 * Creates drop-down list with select options
 * Created by Shcherbin Alexander on 10/21/2016.
 */
public class SelectBox<T> extends ComponentContainer {
    public static final String NULL = "null";
    private static final String EXPANDED = cpToStr(0xE5C7);
    private static final String COLLAPSED = cpToStr(0xE5C5);
    protected List<ListBoxElement<T>> listBoxElements = SetUniqueList.setUniqueList(new CopyOnWriteArrayList<>());
    protected List<T> elements = SetUniqueList.setUniqueList(new CopyOnWriteArrayList<>());

    protected SBWidget selectionListWidget = new SBWidget();
    protected ScrollablePanel selectionListPanel = new ScrollablePanel();
    protected Button selectionButton = new Button(NULL);
    protected T selectedElement = null;
    protected float elementHeight = 16;

    protected Button expandButton = new Button(COLLAPSED);

    protected float buttonWidth = 15f;
    protected Lock lock = new ReentrantLock(false);
    protected boolean collapsed = true;

    protected int visibleCount = 3;

    public SelectBox() {
        initialize();
    }

    public SelectBox(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize();
    }

    public SelectBox(Vector2f position, Vector2f size) {
        super(position, size);
        initialize();
    }

    public float getButtonWidth() {
        return buttonWidth;
    }

    public void setButtonWidth(float buttonWidth) {
        this.buttonWidth = buttonWidth;
    }

    public T getSelection() {
        return selectedElement;
    }

    private void initialize() {
        selectionListWidget.setTitleEnabled(false);
        selectionListWidget.setCloseable(false);
        selectionListWidget.setContainer(selectionListPanel);

        selectionListPanel.getHorizontalScrollBar().setVisible(false);

        this.setBackgroundColor(ColorConstants.white());
        this.setBorder(new SimpleRectangleLineBorder(ColorConstants.darkGray(), 1));

        this.addComponent(expandButton);
        this.addComponent(selectionButton);

        expandButton.getTextState().setFont(FontRegister.MATERIAL_ICONS_REGULAR);
        MouseClickEventListener mouseClickEventListener = event -> {
            if (event.getAction() == CLICK) {
                setCollapsed(!isCollapsed());
            }
        };
        selectionButton.getLeguiEventListeners().addListener(MouseClickEvent.class, mouseClickEventListener);
        expandButton.getLeguiEventListeners().addListener(MouseClickEvent.class, mouseClickEventListener);

        FocusEventListener focusEventListener = event -> {
            if (!event.focusGained && !collapsed) {
                boolean collapse = true;
                for (ListBoxElement<T> listBoxElement : listBoxElements) {
                    if (event.focusTarget == listBoxElement) {
                        collapse = false;
                    }
                }
                if (event.focusTarget == selectionListWidget ||
                        event.focusTarget == expandButton ||
                        event.focusTarget == selectionButton ||
                        event.focusTarget == selectionListPanel ||
                        event.focusTarget == selectionListPanel.getVerticalScrollBar()
                        ) collapse = false;
                if (collapse) setCollapsed(true);
            }
        };
        selectionListPanel.getVerticalScrollBar().getLeguiEventListeners().getListeners(FocusEvent.class).add(focusEventListener);
        selectionButton.getLeguiEventListeners().getListeners(FocusEvent.class).add(focusEventListener);
        expandButton.getLeguiEventListeners().getListeners(FocusEvent.class).add(focusEventListener);

        resize();
    }

    public int getVisibleCount() {
        return visibleCount;
    }

    public void setVisibleCount(int visibleCount) {
        this.visibleCount = visibleCount;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
        if (collapsed) {
            parent.removeComponent(selectionListWidget);
        } else {
            parent.addComponent(selectionListWidget);
            selectionListPanel.getVerticalScrollBar().setCurValue(0);
        }
        resize();
    }

    private void resize() {
        selectionButton.setPosition(0, 0);
        selectionButton.setSize(size.x - buttonWidth, size.y);

        expandButton.getTextState().setText(collapsed ? COLLAPSED : EXPANDED);
        expandButton.setPosition(size.x - buttonWidth, 0);
        expandButton.setSize(buttonWidth, size.y);

        for (int i = 0; i < listBoxElements.size(); i++) {
            ListBoxElement<T> listBoxElement = listBoxElements.get(i);
            listBoxElement.setSize(new Vector2f(selectionListPanel.getContainer().getSize().x, elementHeight));
            listBoxElement.setPosition(0, i * elementHeight);
        }


        Vector2f psize = new Vector2f();
        if (parent != null) psize.set(parent.getSize());
        Vector2f wsize = new Vector2f(this.size.x, visibleCount * elementHeight);
        Vector2f wpos = new Vector2f();
        if (parent != null && position.y + wsize.y > psize.y) {
            wpos.set(position.x, position.y - wsize.y);
        } else {
            wpos.set(position.x, position.y + size.y);
        }
        selectionListWidget.setSize(wsize);
        selectionListWidget.setPosition(wpos);
        selectionListWidget.resize();
        selectionListPanel.getContainer().getSize().y = selectionListPanel.getContainer().componentsCount() * elementHeight;
        selectionListPanel.resize();

    }

    public void addElement(T element) {
        if (!elements.contains(element)) {
            lock.lock();
            try {

                ListBoxElement<T> boxElement = createListBoxElement(element);
                if (elements.isEmpty()) selectionButton.getTextState().setText(element.toString());
                elements.add(element);
                listBoxElements.add(boxElement);
                addListBoxComponent(boxElement);
            } finally {
                lock.unlock();
            }
        }
        resize();
    }

    private ListBoxElement<T> createListBoxElement(T element) {
        ListBoxElement<T> boxElement = new ListBoxElement<>(element, false);
        boxElement.setSize(new Vector2f(selectionListPanel.getContainer().getSize().x, elementHeight));
        boxElement.setPosition(0, selectionListPanel.getContainer().componentsCount() * elementHeight);
        boxElement.getLeguiEventListeners().getListeners(MouseClickEvent.class).add((MouseClickEventListener) event -> {
            if (event.getAction() == CLICK && event.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                setSelected(element, true);
                setCollapsed(true);
            }
        });
        return boxElement;
    }

    private void addListBoxComponent(ListBoxElement<T> element) {
        selectionListPanel.getContainer().addComponent(element);
        selectionListPanel.getContainer().getSize().y = selectionListPanel.getContainer().componentsCount() * elementHeight;
        selectionListPanel.resize();
    }

    public int getElementIndex(T element) {
        return elements.indexOf(element);
    }

    public void removeElement(T element) {
        elements.remove(element);
    }

    public void removeElement(int index) {
        lock.lock();
        try {
            elements.remove(index);
            listBoxElements.remove(index);
        } finally {
            lock.unlock();
        }
    }

    public void setSelected(T element, boolean selected) {
        int index = elements.indexOf(element);
        setSelected(element, selected, index);
    }

    public void setSelected(int index, boolean selected) {
        T element = elements.get(index);
        setSelected(element, selected, index);
    }

    private void setSelected(T element, boolean selected, int index) {
        if (selected) {
            if (index != -1) {
                ListBoxElement<T> tListBoxElement = listBoxElements.get(index);
                tListBoxElement.selected = true;
                int selectedIndex = elements.indexOf(selectedElement);
                if (selectedIndex != -1) {
                    listBoxElements.get(index).selected = false;
                }
                selectedElement = element;
                selectionButton.getTextState().setText(element.toString());
            } else {
                addElement(element);
                setSelected(element, true);
            }
        } else {
            if (index != -1) {
                listBoxElements.get(index).selected = false;
                if (element == selectedElement) selectionButton.getTextState().setText(NULL);
            } else {
                addElement(element);
            }
        }
    }

    public float getElementHeight() {
        return elementHeight;
    }

    public void setElementHeight(float elementHeight) {
        this.elementHeight = elementHeight;
        resize();
    }

    public static class SBWidget extends Widget {
    }

    public class ListBoxElement<T> extends Button {
        private T element;
        private boolean selected;

        private ListBoxElement(T element, boolean selected) {
            super(element == null ? "null" : element.toString());
            this.element = element;
            this.selected = selected;
            this.border = null;
        }

        public T getElement() {
            return element;
        }

        public boolean isSelected() {
            return selected;
        }

        private void setSelected(boolean selected) {
            this.selected = selected;
        }

        private void set(T element, boolean selected) {
            this.element = element;
            this.selected = selected;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SelectBox.ListBoxElement)) return false;
            ListBoxElement that = (SelectBox.ListBoxElement) o;
            return Objects.equal(element, that.element);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(element);
        }
    }


}
