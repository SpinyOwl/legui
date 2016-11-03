package org.liquidengine.legui.component;

import com.google.common.base.Objects;
import org.apache.commons.collections4.list.SetUniqueList;
import org.joml.Vector2f;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.liquidengine.legui.util.Util.cpToStr;

/**
 * Created by Shcherbin Alexander on 10/21/2016.
 */
public class SelectBox<T> extends ComponentContainer {
    private static final String EXPANDED = cpToStr(0xE5C5);
    private static final String COLLAPSED = cpToStr(0xE5C7);

    protected List<ListBoxElement<T>> listBoxElements = SetUniqueList.setUniqueList(new CopyOnWriteArrayList<>());
    protected List<T> elements = SetUniqueList.setUniqueList(new CopyOnWriteArrayList<>());

    protected ScrollablePanel selectionListPanel = new ScrollablePanel();
    protected ListBoxElement<T> selection = new ListBoxElement<>(null, false);
    protected Button expandButton = new Button(COLLAPSED);

    protected Lock lock = new ReentrantLock(false);
    protected boolean collapsed;
    protected int visibleCount;

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

    public ListBoxElement<T> getSelection() {
        return selection;
    }

    private void initialize() {
        this.addComponent(expandButton);
        this.addComponent(selection);

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
        resize();
    }

    private void resize() {
        expandButton.getTextState().setText(collapsed ? COLLAPSED : EXPANDED);
    }

    public void addElement(T element) {
        if (!elements.contains(element)) {
            lock.lock();
            try {
                elements.add(element);
                ListBoxElement<T> boxElement = new ListBoxElement<>(element, false);
                listBoxElements.add(boxElement);
                addListBoxComponent(boxElement);
            } finally {
                lock.unlock();
            }
        }
    }

    private void addListBoxComponent(ListBoxElement<T> element) {
        components.add(element);
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
        if (selected) {
            if (index != -1) {
                ListBoxElement<T> tListBoxElement = listBoxElements.get(index);
                selection.selected = false;
                tListBoxElement.selected = true;
                selection = tListBoxElement;
            } else {
                ListBoxElement<T> listBoxElement = new ListBoxElement<T>(element, true);
                lock.lock();
                try {
                    elements.add(element);
                    listBoxElements.add(listBoxElement);
                } finally {
                    lock.unlock();
                }
                selection.selected = false;
                selection = listBoxElement;
            }
        } else {
            if (index != -1) {
                listBoxElements.get(index).selected = false;
            } else {
                addElement(element);
            }
        }
    }

    public void setSelected(int index, boolean selected) {
        ListBoxElement<T> tListBoxElement = listBoxElements.get(index);
        if (selected) {
            selection.selected = false;
            tListBoxElement.selected = true;
            selection = tListBoxElement;
        } else {
            tListBoxElement.selected = false;
        }
    }

    public class ListBoxElement<T> extends Label {
        private T element;
        private boolean selected;

        private ListBoxElement(T element, boolean selected) {
            super(element == null ? "null" : element.toString());
            this.element = element;
            this.selected = selected;
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
