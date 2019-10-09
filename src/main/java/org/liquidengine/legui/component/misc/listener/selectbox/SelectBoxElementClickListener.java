package org.liquidengine.legui.component.misc.listener.selectbox;

import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.CLICK;

import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.component.event.selectbox.SelectBoxChangeSelectionEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;

/**
 * @author ShchAlexander.
 */
public class SelectBoxElementClickListener<T> implements MouseClickEventListener {

    private SelectBox<T> selectBox;

    public SelectBoxElementClickListener(SelectBox<T> selectBox) {
        this.selectBox = selectBox;
    }

    @Override
    public void process(MouseClickEvent event) {
        SelectBox<T>.SelectBoxElement<T> component = (SelectBox<T>.SelectBoxElement<T>) event.getTargetComponent();
        if (event.getAction() == CLICK && event.getButton().equals(Mouse.MouseButton.MOUSE_BUTTON_1)) {
            T selection = selectBox.getSelection();
            T newValue = component.getObject();
            selectBox.setSelected(newValue, true);
            EventProcessorProvider.getInstance().pushEvent(new SelectBoxChangeSelectionEvent<>(selectBox, event.getContext(), event.getFrame(), selection, newValue));
            selectBox.setCollapsed(true);
            event.getFrame().removeLayer(selectBox.getSelectBoxLayer());
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj == this || obj.getClass() == this.getClass());
    }
}
