package org.liquidengine.legui.component.misc.animation.selectbox;

import org.joml.Vector2f;
import org.liquidengine.legui.animation.Animation;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.component.SelectBox.SelectBoxScrollablePanel;

import java.lang.ref.WeakReference;

/**
 * @author ShchAlexander.
 */
public class SelectBoxAnimation extends Animation {

    private final WeakReference<SelectBox> selectBox;
    private final WeakReference<SelectBoxScrollablePanel> selectionListPanel;

    private double deltaSum = 0d;

    public SelectBoxAnimation(SelectBox selectBox, SelectBoxScrollablePanel selectionListPanel) {
        this.selectBox = new WeakReference<>(selectBox);
        this.selectionListPanel = new WeakReference<>(selectionListPanel);
    }

    /**
     * This method used to update animated object. Called by animator every frame. Removed from animator and stops when this method returns true. <p> Returns
     * true if animation is finished and could be removed from animator.
     *
     * @param delta delta time (from previous call).
     *
     * @return true if animation is finished and could be removed from animator.
     */
    @Override
    protected boolean animate(double delta) {
        SelectBox selectBox = this.selectBox.get();
        SelectBoxScrollablePanel selectionListPanel = this.selectionListPanel.get();
        if (selectBox == null || selectionListPanel == null) {
            return true;
        }
        if (selectBox.isCollapsed()) {
        	return false;
        }
        deltaSum += delta;

        if (deltaSum < 0.01d) {
            return false;
        }
        float buttonWidth = selectBox.getButtonWidth();

        selectionListPanel.getVerticalScrollBar().getStyle().setMaxWidth(buttonWidth);
        selectionListPanel.getVerticalScrollBar().getStyle().setMaxWidth(buttonWidth);

        int visibleCount = Math.min(selectBox.getVisibleCount(), selectBox.getElements().size());
        float elementHeight = selectBox.getElementHeight();

        Vector2f size = selectBox.getSize();
        Vector2f wsize = new Vector2f(size.x, visibleCount * elementHeight);
        Vector2f wpos = new Vector2f();
        Vector2f sbPos = selectBox.getAbsolutePosition();
        Vector2f pSize = selectBox.getSelectBoxLayer().getSize();

        if (sbPos.y + wsize.y + size.y > pSize.y && sbPos.y - wsize.y > 0) {
            wpos.set(sbPos.x, sbPos.y - wsize.y);
        } else {
            wpos.set(sbPos.x, sbPos.y + size.y);
        }

        selectionListPanel.setSize(wsize);
        selectionListPanel.setPosition(wpos);
        selectionListPanel.getContainer().getSize().y = selectionListPanel.getContainer().count() * elementHeight;
        selectionListPanel.getContainer().getSize().x = size.x - selectionListPanel.getVerticalScrollBar().getSize().x;

        deltaSum = 0;
        return false;
    }
}
