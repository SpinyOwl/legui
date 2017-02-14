package org.liquidengine.legui.component;

import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
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
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.CLICK;
import static org.liquidengine.legui.util.TextUtil.cpToStr;

/**
 * Creates drop-down list with select options
 */
public class SelectBox extends Container {
    public static final  String               NULL            = "null";
    private static final String               EXPANDED        = cpToStr(0xE5C7);
    private static final String               COLLAPSED       = cpToStr(0xE5C5);
    private              List<ListBoxElement> listBoxElements = SetUniqueList.setUniqueList(new CopyOnWriteArrayList<>());
    private              List<String>         elements        = SetUniqueList.setUniqueList(new CopyOnWriteArrayList<>());

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

    public List<String> getElements() {
        return elements;
    }

    public float getButtonWidth() {
        return buttonWidth;
    }

    public void setButtonWidth(float buttonWidth) {
        this.buttonWidth = buttonWidth;
    }

    public String getSelection() {
        return selectedElement;
    }

    private void initialize() {
        selectionListPanel.getHorizontalScrollBar().setVisible(false);

        this.setBackgroundColor(ColorConstants.white());
        this.setBorder(new SimpleLineBorder(ColorConstants.darkGray(), 1));

        this.add(expandButton);
        this.add(selectionButton);

        expandButton.getTextState().setFont(FontRegister.MATERIAL_ICONS_REGULAR);
        MouseClickEventListener mouseClickEventListener = new SelectBoxClickListener(this);
        selectionButton.getListenerMap().addListener(MouseClickEvent.class, mouseClickEventListener);
        expandButton.getListenerMap().addListener(MouseClickEvent.class, mouseClickEventListener);

        FocusEventListener focusEventListener = new SelectBoxFocusListener(this);
        selectionListPanel.getVerticalScrollBar().getListenerMap().getListeners(FocusEvent.class).add(focusEventListener);
        selectionButton.getListenerMap().getListeners(FocusEvent.class).add(focusEventListener);
        expandButton.getListenerMap().getListeners(FocusEvent.class).add(focusEventListener);

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
            getParent().remove(selectionListPanel);
        } else {
            getParent().add(selectionListPanel);
            selectionListPanel.getVerticalScrollBar().setCurValue(0);
        }
        resize();
    }

    private void resize() {
        selectionButton.setPosition(0, 0);
        selectionButton.setSize(getSize().x - buttonWidth, getSize().y);

        expandButton.getTextState().setText(collapsed ? COLLAPSED : EXPANDED);
        expandButton.setPosition(getSize().x - buttonWidth, 0);
        expandButton.setSize(buttonWidth, getSize().y);
        selectionListPanel.getVerticalScrollBar().getSize().x = buttonWidth;

        for (int i = 0; i < listBoxElements.size(); i++) {
            ListBoxElement listBoxElement = listBoxElements.get(i);
            listBoxElement.setSize(new Vector2f(selectionListPanel.getContainer().getSize().x, elementHeight));
            listBoxElement.setPosition(0, i * elementHeight);
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

    public void addElement(String element) {
        if (!elements.contains(element)) {
            lock.lock();
            try {

                ListBoxElement boxElement = createListBoxElement(element);
                if (elements.isEmpty()) selectionButton.getTextState().setText(element);
                boxElement.getListenerMap().addListener(ScrollEvent.class, selectBoxScrollListener);
                elements.add(element);
                listBoxElements.add(boxElement);
                addListBoxComponent(boxElement);
            } finally {
                lock.unlock();
            }
        }
        resize();
    }

    private ListBoxElement createListBoxElement(String element) {
        ListBoxElement boxElement = new ListBoxElement(element, false);
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

    private void addListBoxComponent(ListBoxElement element) {
        selectionListPanel.getContainer().add(element);
        selectionListPanel.getContainer().getSize().y = selectionListPanel.getContainer().count() * elementHeight;
        selectionListPanel.resize();
    }

    public int getElementIndex(String element) {
        return elements.indexOf(element);
    }

    public void removeElement(String element) {
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

    public void setSelected(String element, boolean selected) {
        int index = elements.indexOf(element);
        setSelected(element, selected, index);
    }

    public void setSelected(int index, boolean selected) {
        String element = elements.get(index);
        setSelected(element, selected, index);
    }

    private void setSelected(String element, boolean selected, int index) {
        if (selected) {
            if (index != -1) {
                ListBoxElement tListBoxElement = listBoxElements.get(index);
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

    public static class SelectBoxFocusListener implements FocusEventListener {
        private final SelectBox box;

        public SelectBoxFocusListener(SelectBox box) {
            if (box == null) throw new NullPointerException("SelectBox for this listener cannot be null");
            this.box = box;
        }

        @Override
        public void process(FocusEvent event) {
            if (!event.isFocused() && !box.isCollapsed()) {
                boolean   collapse  = true;
                Component nextFocus = event.getNextFocus();
                for (ListBoxElement listBoxElement : box.listBoxElements) {
                    if (nextFocus == listBoxElement) {
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
    }

    public static class SelectBoxClickListener implements MouseClickEventListener {
        private final SelectBox box;

        public SelectBoxClickListener(SelectBox box) {
            if (box == null) throw new NullPointerException("SelectBox for this listener cannot be null");
            this.box = box;
        }

        @Override
        public void process(MouseClickEvent event) {
            if (event.getAction() == CLICK) {
                box.setCollapsed(!box.isCollapsed());
            }
        }
    }

    public class ListBoxElement extends Button {
        private boolean selected;

        private ListBoxElement(String text, boolean selected) {
            super(text == null ? "null" : text);
            this.selected = selected;
            this.setBorder(null);
        }

        public boolean isSelected() {
            return selected;
        }

        private void setSelected(boolean selected) {
            this.selected = selected;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("selected", selected)
                    .toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            ListBoxElement that = (ListBoxElement) o;

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

    public class SelectBoxScrollablePanel extends ScrollablePanel {
        public SelectBoxScrollablePanel() {
        }

        public SelectBoxScrollablePanel(float x, float y, float width, float height) {
            super(x, y, width, height);
        }

        public SelectBoxScrollablePanel(Vector2f position, Vector2f size) {
            super(position, size);
        }
    }

    public class SelectBoxScrollListener implements ScrollEventListener {
        private final ScrollBar bar;

        public SelectBoxScrollListener(ScrollBar bar) {
            this.bar = bar;
        }

        @Override
        public void process(ScrollEvent event) {
            event.getContext().getEventProcessor().pushEvent(new ScrollEvent(bar, event.getContext(), event.getXoffset(), event.getYoffset()));
        }
    }
}
