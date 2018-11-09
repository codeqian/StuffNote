package net.codepig.stuffnote.DataBean;

/**
 * 清单信息
 */
public class ToDoInfo {
    private String _name;
    private String _content;
    private String _date;

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_name() {
        return _name;
    }

    public void set_content(String _content) {
        this._content = _content;
    }
    public String get_content() {
        return _content;
    }

    public void set_date(String _date) {
        this._date = _date;
    }
    public String get_date() {
        return _date;
    }
}
