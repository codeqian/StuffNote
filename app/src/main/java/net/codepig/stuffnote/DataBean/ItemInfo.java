package net.codepig.stuffnote.DataBean;

/**
 * 物品信息
 */
public class ItemInfo {
    private String _name;
    private String _location;
    private int _color;
    private String _function;
    private String _description;

    public void set_name(String _name) {
        this._name = _name;
    }
    public String get_name() {
        return _name;
    }

    public void set_location(String _location) {
        this._location = _location;
    }
    public String get_location() {
        return _location;
    }

    public void set_color(int _color) {
        this._color = _color;
    }
    public int get_color() {
        return _color;
    }

    public void set_function(String _function) {
        this._function = _function;
    }
    public String get_function() {
        return _function;
    }

    public void set_description(String _description) {
        this._description = _description;
    }
    public String get_description() {
        return _description;
    }
}
