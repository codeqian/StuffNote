package net.codepig.stuffnote.DataBean;

/**
 * 标签信息
 */
public class TipInfo {
    private int _type;
    private String _value;

    public static final int LOCATION_TIP=0;
    public static final int FUNCTION_TIP=1;
    public static final int COLOR_TIP=2;
    public static final int ALL_TIP=4;

    public void set_type(int _type) {
        this._type = _type;
    }
    public int get_type() {
        return _type;
    }

    public void set_value(String _value) {
        this._value = _value;
    }
    public String get_value() {
        return _value;
    }
}
