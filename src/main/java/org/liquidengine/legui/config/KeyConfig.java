package org.liquidengine.legui.config;

public class KeyConfig {
    private Integer copy = 67;
    private Integer paste = 86;
    private Integer selectAll = 65;
    private Integer cut = 88;

    /**
     * Creates key config in QWERTY layout
     */
    public KeyConfig() {
    }

    /**
     * Creates key config in any specified layout
     *
     * @param copy      key code for copy key
     * @param paste     key code for paste key
     * @param selectAll key code for selectAll key
     * @param cut       key code for cut key
     */
    public KeyConfig(Integer copy, Integer paste, Integer selectAll, Integer cut) {
        this.copy = copy;
        this.paste = paste;
        this.selectAll = selectAll;
        this.cut = cut;
    }

    public Integer getCopy() {
        return copy;
    }

    public void setCopy(Integer copy) {
        this.copy = copy;
    }

    public Integer getPaste() {
        return paste;
    }

    public void setPaste(Integer paste) {
        this.paste = paste;
    }

    public Integer getSelectAll() {
        return selectAll;
    }

    public void setSelectAll(Integer selectAll) {
        this.selectAll = selectAll;
    }

    public Integer getCut() {
        return cut;
    }

    public void setCut(Integer cut) {
        this.cut = cut;
    }
}
