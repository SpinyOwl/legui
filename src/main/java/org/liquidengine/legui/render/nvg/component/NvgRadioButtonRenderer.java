package org.liquidengine.legui.render.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.RadioButton;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.render.nvg.NvgLeguiComponentRenderer;
import org.liquidengine.legui.util.NvgRenderUtils;
import org.liquidengine.legui.util.Util;
import org.lwjgl.nanovg.NVGColor;

import static org.liquidengine.legui.component.theme.Theme.DEFAULT_THEME;
import static org.liquidengine.legui.util.NvgRenderUtils.*;

/**
 * Created by Shcherbin Alexander on 8/22/2016.
 */
public class NvgRadioButtonRenderer extends NvgLeguiComponentRenderer {
    private static final String ICON_CHECKED = Util.cpToStr(0xE837);
    private static final String ICON_UNCHECKED = Util.cpToStr(0xE836);
    private final int iconOffset = 2;
    private final float hoff = 1.5f;
    private NVGColor colorA = NVGColor.create();

    @Override
    public void initialize() {

    }

    @Override
    public void render(Component component, LeguiContext leguiContext, long context) {
        createScissor(context, component);
        {
            // default renderer used
            RadioButton agui = (RadioButton) component;
            Vector2f pos = Util.calculatePosition(component);
            Vector2f size = component.getSize();

            TextState textState = agui.getTextState();
            float fontSize = textState.getFontSize();
            Vector4f textColor = textState.getTextColor();
            Vector4f pad = textState.getPadding();

            float iconWid = fontSize + 5;

            // renderNvg text
            float y1 = pos.y + pad.y;
            float h1 = size.y - (pad.y + pad.w);
            float x1 = pos.x - iconOffset + iconWid;
            float w1 = size.x - iconWid - pad.z;

            renderTextStateToBounds(context, new Vector2f(x1, y1), new Vector2f(w1, h1), textState);

            renderIcon(agui, pos.x, fontSize, textColor, iconWid, y1, h1, context);

        }
        resetScissor(context);
    }


    private void renderIcon(RadioButton agui, float x, float fontSize, Vector4f textColor, float iconWid, float y1, float h1, long context) {
        // renderNvg check symbol
        String icon = agui.isSelected() ? ICON_CHECKED : ICON_UNCHECKED;
        if (agui.isFocused()) {
            float x1 = x - iconOffset + hoff;
            float y = y1 + hoff;
            NvgRenderUtils.renderTextLineToBounds(context, x1, y, iconWid, h1, fontSize, FontRegister.MATERIAL_ICONS_REGULAR,
                    DEFAULT_THEME.getFocusedStrokeColorLight(), colorA, icon, HorizontalAlign.CENTER, VerticalAlign.MIDDLE, false);
        }
        float x1 = x - iconOffset;
        NvgRenderUtils.renderTextLineToBounds(context, x1, y1, iconWid, h1, fontSize, FontRegister.MATERIAL_ICONS_REGULAR,
                textColor, colorA, icon, HorizontalAlign.CENTER, VerticalAlign.MIDDLE, false);
    }

    @Override
    public void destroy() {

    }
}