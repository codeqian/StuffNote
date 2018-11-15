package net.codepig.stuffnote.DataBean;

/**
 * 物品信息
 */
public class ItemInfo {
    private String _id;
    private String _name;
    private String _location;
    private String _color;
    private String _function;
    private String _description;
    private String _imageUrl;
    private String _time;

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_id() {
        return _id;
    }

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

    public void set_color(String _color) {
        this._color = _color;
    }
    public String get_color() {
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

    public void set_time(String _time) {
        this._time = _time;
    }
    public String get_time() {
        return _time;
    }

    public void set_imageUrl(String _imageUrl) {
        this._imageUrl = _imageUrl;
    }

    public String get_imageUrl() {
        return _imageUrl;
    }
}
