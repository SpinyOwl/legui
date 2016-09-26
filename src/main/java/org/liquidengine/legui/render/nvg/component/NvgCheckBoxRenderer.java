package org.liquidengine.legui.render.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.CheckBox;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.TextState;
import org.liquidengine.legui.component.align.HorizontalAlign;
import org.liquidengine.legui.component.align.VerticalAlign;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.render.nvg.NvgDefaultRenderer;
import org.liquidengine.legui.util.NvgRenderUtils;
import org.liquidengine.legui.util.Util;
import org.lwjgl.nanovg.NVGColor;

import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.util.NvgRenderUtils.resetScissor;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by Shcherbin Alexander on 5/19/2016.
 */
public class NvgCheckBoxRenderer extends NvgDefaultRenderer {
    private static final String ICON_CHECKED = Util.cpToStr(0xE834);
    private static final String ICON_UNCHECKED = Util.cpToStr(0xE835);
    private final int iconOffset = 2;
    private final float hoff = 1.5f;
    private NVGColor colorA = NVGColor.create();

    @Override
    public void initialize() {

    }

    @Override
    public void render(Component component, LeguiContext leguiContext, long nvgContext) {
        createScissor(nvgContext, component);
        {
            CheckBox checkBox = (CheckBox) component;
            Vector2f pos = Util.calculatePosition(component);
            Vector2f size = component.getSize();

            float px = pos.x;
            float py = pos.y;
            float sw = size.x;
            float sh = size.y;
            /*Draw background rectangle*/
            {
                nvgBeginPath(nvgContext);
                nvgRoundedRect(nvgContext, px, py, sw, sh, 0);
                nvgFillColor(nvgContext, rgba(component.getBackgroundColor(), colorA));
                nvgFill(nvgContext);
            }

            TextState textState = checkBox.getTextState();
            float fontSize = textState.getFontSize();
            float iconWid = fontSize + 5;

            Vector4f pad = textState.getPadding();

            float h = sh - (pad.y + pad.w);
            float y = py + pad.y;
            float x1 = px - iconOffset;
            float x = x1 + iconWid;
            float w = sw - iconWid - pad.z;
            NvgRenderUtils.renderTextStateToBounds(nvgContext, new Vector2f(x, y), new Vector2f(w, h), checkBox.getTextState());

            Vector4f textColor = textState.getTextColor();
            if (checkBox.isChecked()) {
                // renderNvg check symbol
//                if (component.isFocused()) {
//                    NvgRenderUtils.renderTextLineToBounds(nvgContext, x1 + hoff, y + hoff, iconWid, h, fontSize, FontRegister.MATERIAL_ICONS_REGULAR,
//                            DEFAULT_THEME.getFocusedStrokeColorLight(), colorA, ICON_CHECKED, HorizontalAlign.CENTER, VerticalAlign.MIDDLE, false);
//                }
                NvgRenderUtils.renderTextLineToBounds(nvgContext, x1, y, iconWid, h, fontSize, FontRegister.MATERIAL_ICONS_REGULAR,
                        textColor, colorA, ICON_CHECKED, HorizontalAlign.CENTER, VerticalAlign.MIDDLE, false);
            } else {
                // renderNvg box
//                if (component.isFocused()) {
//                    NvgRenderUtils.renderTextLineToBounds(nvgContext, x1 + hoff, y + hoff, iconWid, h, fontSize, FontRegister.MATERIAL_ICONS_REGULAR,
//                            DEFAULT_THEME.getFocusedStrokeColorLight(), colorA, ICON_UNCHECKED, HorizontalAlign.CENTER, VerticalAlign.MIDDLE, false);
//                }
                NvgRenderUtils.renderTextLineToBounds(nvgContext, x1, y, iconWid, h, fontSize,
                        FontRegister.MATERIAL_ICONS_REGULAR, textColor, colorA, ICON_UNCHECKED, HorizontalAlign.CENTER, VerticalAlign.MIDDLE, false);
            }
        }
        resetScissor(nvgContext);
    }

    @Override
    public void destroy() {
    }

}
