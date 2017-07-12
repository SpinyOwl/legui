package org.liquidengine.legui.component.misc.selectbox;

import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.MouseClickEventListener;

import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.CLICK;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class SelectBoxElementClickEventListener implements MouseClickEventListener {
    private SelectBox selectBox;

    public SelectBoxElementClickEventListener(SelectBox selectBox) {
        this.selectBox = selectBox;
    }

    @Override
    public void process(MouseClickEvent event) {
        SelectBox.SelectBoxElement component = (SelectBox.SelectBoxElement) event.getComponent();
        if (event.getAction() == CLICK && event.getButton().equals(Mouse.MouseButton.MOUSE_BUTTON_1)) {
            selectBox.setSelected(component.getText(), true);
            selectBox.setCollapsed(true);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }
}
