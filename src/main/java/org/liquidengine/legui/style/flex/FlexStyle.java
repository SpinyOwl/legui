package org.liquidengine.legui.style.flex;

public class FlexStyle {

    private FlexDirection flexDirection;
    private JustifyContent justifyContent;
    private AlignItems alignItems;
    private FlexWrap flexWrap;
    private AlignContent alignContent;
    private AlignSelf alignSelf;
    private int flexGrow;
    private int flexShrink;
    private float flexBasis;

    public FlexStyle() {
        this.flexDirection = FlexDirection.ROW;
        this.justifyContent = JustifyContent.FLEX_START;
        this.alignItems = AlignItems.STRETCH;
        this.flexWrap = FlexWrap.NOWRAP;
        this.alignContent = AlignContent.STRETCH;
        this.alignSelf = AlignSelf.AUTO;
    }

    public void setFlex(int flexGrow, int flexShrink, float flexBasis) {
        setFlexGrow(flexGrow);
        setFlexShrink(flexShrink);
        setFlexBasis(flexBasis);
    }

    public void setFlexFlow(FlexDirection flexDirection, FlexWrap flexWrap) {
        setFlexDirection(flexDirection);
        setFlexWrap(flexWrap);
    }

    public AlignSelf getAlignSelf() {
        return alignSelf;
    }

    public void setAlignSelf(AlignSelf alignSelf) {
        this.alignSelf = alignSelf;
    }

    public int getFlexGrow() {
        return flexGrow;
    }

    public void setFlexGrow(int flexGrow) {
        this.flexGrow = flexGrow;
    }

    public int getFlexShrink() {
        return flexShrink;
    }

    public void setFlexShrink(int flexShrink) {
        this.flexShrink = flexShrink;
    }

    public float getFlexBasis() {
        return flexBasis;
    }

    public void setFlexBasis(float flexBasis) {
        this.flexBasis = flexBasis;
    }

    public FlexDirection getFlexDirection() {
        return flexDirection;
    }

    public void setFlexDirection(FlexDirection flexDirection) {
        if (flexDirection != null) {
            this.flexDirection = flexDirection;
        }
    }

    public JustifyContent getJustifyContent() {
        return justifyContent;
    }

    public void setJustifyContent(JustifyContent justifyContent) {
        if (justifyContent != null) {
            this.justifyContent = justifyContent;
        }
    }

    public AlignItems getAlignItems() {
        return alignItems;
    }

    public void setAlignItems(AlignItems alignItems) {
        if (alignItems != null) {
            this.alignItems = alignItems;
        }
    }

    public FlexWrap getFlexWrap() {
        return flexWrap;
    }

    public void setFlexWrap(FlexWrap flexWrap) {
        if (flexWrap != null) {
            this.flexWrap = flexWrap;
        }
    }

    public AlignContent getAlignContent() {
        return alignContent;
    }

    public void setAlignContent(AlignContent alignContent) {
        if (alignContent != null) {
            this.alignContent = alignContent;
        }
    }

    public enum AlignSelf {
        AUTO,
        STRETCH,
        CENTER,
        FLEX_START,
        FLEX_END,
        BASELINE,
        INITIAL,
        INHERIT
    }

    public enum AlignContent {
        STRETCH,
        CENTER,
        FLEX_START,
        FLEX_END,
        SPACE_BETWEEN,
        SPACE_AROUND,
        INITIAL,
        INHERIT
    }

    public enum FlexWrap {
        NOWRAP,
        WRAP,
        WRAP_REVERSE,
        INITIAL,
        INHERIT
    }

    public enum AlignItems {
        AUTO,
        STRETCH,
        CENTER,
        FLEX_START,
        FLEX_END,
        BASELINE,
        INITIAL,
        INHERIT
    }

    public enum JustifyContent {
        FLEX_START,
        FLEX_END,
        CENTER,
        SPACE_BETWEEN,
        SPACE_AROUND,
        SPACE_EVENLY,
        INITIAL,
        INHERIT
    }

    public enum FlexDirection {
        ROW,
        ROW_REVERSE,
        COLUMN,
        COLUMN_REVERSE
    }
}