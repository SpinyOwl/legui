package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;

/**
 * Created by Shcherbin Alexander on 9/27/2016.
 */
public class Widget extends Component implements ContainerHolder {
    protected TextState textState;
    protected ComponentContainer container = new ComponentContainer();

    protected float titleHeight = 20;
    protected boolean titleEnabled = true;

    protected boolean closeable = true;
    protected boolean resizable = false;

    public Widget() {
        initialize("Widget");
    }

    public Widget(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize("Widget");
    }

    public Widget(Vector2f position, Vector2f size) {
        super(position, size);
        initialize("Widget");
    }

    public Widget(String title) {
        initialize(title);
    }

    public Widget(String title, float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize(title);
    }

    public Widget(String title, Vector2f position, Vector2f size) {
        super(position, size);
        initialize(title);
    }

    private void initialize(String title) {
        textState = new TextState(title);
        textState.setHorizontalAlign(HorizontalAlign.LEFT);
        textState.getPadding().set(10, 5, 10, 5);
        backgroundColor.set(1);
    }

    public float getTitleHeight() {
        return titleHeight;
    }

    public void setTitleHeight(float titleHeight) {
        this.titleHeight = titleHeight;
    }

    public boolean isTitleEnabled() {
        return titleEnabled;
    }

    public void setTitleEnabled(boolean titleEnabled) {
        this.titleEnabled = titleEnabled;
    }

    public boolean isCloseable() {
        return closeable;
    }

    public void setCloseable(boolean closeable) {
        this.closeable = closeable;
    }

    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    @Override
    public ComponentContainer getContainer() {
        return container;
    }

    @Override
    public Vector2f getContainerPosition() {
        return new Vector2f(titleEnabled ? titleHeight : 0, 0);
    }

    public TextState getTitleTextState() {
        return textState;
    }
}
