package org.liquidengine.legui.component;

import com.google.common.base.Objects;
import org.apache.commons.collections4.list.SetUniqueList;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Shcherbin Alexander on 10/21/2016.
 */
public class ListBox<T> extends ComponentContainer {
    private List<ListBoxElement<T>> elements = SetUniqueList.setUniqueList(new CopyOnWriteArrayList<>());
    private ListBoxElement<T> selection;

    public void addElement(T element) {
        elements.add(new ListBoxElement<>(element, false));
    }

    public int getElementIndex(T element) {
        return elements.indexOf(new ListBoxElement<>(element, false));
    }

    public void removeElement(T element) {
        elements.remove(new ListBoxElement<>(element, false));
    }

    public void removeElement(int index) {
        elements.remove(index);
    }

    public void setSelected(T element, boolean selected) {
        ListBoxElement<T> newSelection = new ListBoxElement<>(element, true);
        if (elements.contains(newSelection)) {
            newSelection = elements.get(elements.indexOf(newSelection));
            if (selected) {
                selection.selected = false;
                selection = newSelection;
            }
        } else {
            if (selection != null) {
                selection.selected = false;
                elements.add(selection = newSelection);
            }
        }
    }

    public void setSelected(int index, boolean selected) {

    }

    public class ListBoxElement<T> {
        private T element;
        private boolean selected;

        private ListBoxElement(T element, boolean selected) {
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
            if (!(o instanceof ListBox.ListBoxElement)) return false;
            ListBoxElement that = (ListBox.ListBoxElement) o;
            return Objects.equal(element, that.element);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(element);
        }
    }

}
