package org.liquidengine.legui.style.css;

import org.joml.Vector4fc;

/**
 * @author Aliaksandr_Shcherbin.
 */
public interface ICSSStyle {

    CSSProperty getBackground();
    void setBackground(Vector4fc color);
}
