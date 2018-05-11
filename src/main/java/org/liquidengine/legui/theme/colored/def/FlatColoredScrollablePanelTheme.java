package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.color.ColorUtil;
import org.liquidengine.legui.theme.Themes;

/**
 * Dark ScrollablePanel Theme for all scrollable panels. Used to make scrollable panel dark.
 *
 * @param <T> {@link ScrollablePanel} subclasses.
 */
public class FlatColoredScrollablePanelTheme<T extends ScrollablePanel> extends FlatColoredComponentTheme<T> {

    private final Vector4f backgroundColor;
    private final Vector4f backgroundColor2;

    public FlatColoredScrollablePanelTheme(Vector4f backgroundColor, Vector4f backgroundColor2, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
        super(backgroundColor, backgroundColor2, strokeColor, allowColor, denyColor);
        this.backgroundColor = backgroundColor;
        this.backgroundColor2 = backgroundColor2;
    }

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);

        Vector4f bgc = ColorUtil.oppositeBlackOrWhite(new Vector4f(backgroundColor).mul(3)).add(new Vector4f(backgroundColor).mul(3)).div(4);
        component.getStyle().getBackground().setColor(bgc);

        Component viewport = component.getViewport();
        Themes.getDefaultTheme().apply(viewport);
        Themes.getDefaultTheme().applyAll(component.getVerticalScrollBar());
        Themes.getDefaultTheme().applyAll(component.getHorizontalScrollBar());
        viewport.getStyle().getBackground().setColor(ColorConstants.transparent());
        component.getContainer().getStyle().getBackground().setColor(new Vector4f(bgc));

        component.getStyle().setBorder(new SimpleLineBorder(new Vector4f(backgroundColor2), 1));
        component.getViewport().getStyle().setBorder(new SimpleLineBorder(new Vector4f(backgroundColor2), 1));
//        component.getVerticalScrollBar().getStyle().setBorder(new SimpleLineBorder(backgroundColor2, 1));
//        component.getHorizontalScrollBar().getStyle().setBorder(new SimpleLineBorder(backgroundColor2, 1));
    }

}
