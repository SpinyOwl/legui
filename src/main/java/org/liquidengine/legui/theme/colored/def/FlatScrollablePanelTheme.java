package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.color.ColorUtil;
import org.liquidengine.legui.style.shadow.Shadow;
import org.liquidengine.legui.theme.Themes;

/**
 * Dark ScrollablePanel Theme for all scrollable panels. Used to make scrollable panel dark.
 *
 * @param <T> {@link ScrollablePanel} subclasses.
 */
public class FlatScrollablePanelTheme<T extends ScrollablePanel> extends FlatComponentTheme<T> {

    private final Vector4f backgroundColor;
    private final Vector4f borderColor;

    public FlatScrollablePanelTheme(Vector4f backgroundColor, Vector4f borderColor, Vector4f strokeColor, Vector4f allowColor,
        Vector4f denyColor) {
        super(backgroundColor, borderColor, strokeColor, allowColor, denyColor);
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
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
        component.getStyle().setShadow(new Shadow(-4, 4, 17, -7, ColorUtil.oppositeBlackOrWhite(backgroundColor).mul(0.8f)));

        Component viewport = component.getViewport();
        Themes.getDefaultTheme().apply(viewport);
        Themes.getDefaultTheme().applyAll(component.getVerticalScrollBar());
        Themes.getDefaultTheme().applyAll(component.getHorizontalScrollBar());
        viewport.getStyle().getBackground().setColor(ColorConstants.transparent());
        component.getContainer().getStyle().getBackground().setColor(new Vector4f(bgc));

        component.getStyle().setBorder(new SimpleLineBorder(new Vector4f(borderColor), 1));
        component.getViewport().getStyle().setBorder(new SimpleLineBorder(new Vector4f(borderColor), 1));
    }

}
