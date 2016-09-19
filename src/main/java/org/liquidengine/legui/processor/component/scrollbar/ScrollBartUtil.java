//package org.liquidengine.legui.processor.component.scrollbar;
//
//import org.joml.Vector2f;
//import org.liquidengine.legui.element.Orientation;
//import org.liquidengine.legui.element.ScrollBar;
//import org.liquidengine.legui.element.Viewport;
//
///**
// * Created by Shcherbin Alexander on 9/2/2016.
// */
//public final class ScrollBartUtil {
//    private ScrollBartUtil() {
//    }
//
//    public static void updateViewport(ScrollBar gui, float newVal) {
//        Viewport viewport = gui.getViewport();
//        float maxValue = gui.getMaxValue();
//        float minValue = gui.getMinValue();
//        float valueRange = maxValue - minValue;
//        boolean vertical = Orientation.VERTICAL.equals(gui.getOrientation());
//        if (viewport != null) {
//            Vector2f visibleSize = viewport.getVisibleSize();
//            Vector2f wholeSize = viewport.getWholeSize();
//            Vector2f currentPosition = viewport.getCurrentPosition();
//            if (vertical) {
//                float vs = visibleSize.y;
//                float whs = wholeSize.y;
//                if (vs > whs) return;
//                float newPos = (vs - whs) * newVal / valueRange;
//                viewport.moveTo(new Vector2f(currentPosition.x, newPos));
//            } else {
//                float vs = visibleSize.x;
//                float whs = wholeSize.x;
//                if (vs > whs) return;
//                float newPos = (vs - whs) * newVal / valueRange;
//                viewport.moveTo(new Vector2f(newPos, currentPosition.y));
//            }
//        }
//    }
//}
