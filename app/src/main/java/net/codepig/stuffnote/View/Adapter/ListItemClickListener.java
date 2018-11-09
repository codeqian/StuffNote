package net.codepig.stuffnote.View.Adapter;

import android.view.View;

/**
 * 列表内按钮点击事件
 * Created by Administrator on 2015/4/22.
 */
public  interface ListItemClickListener {
    void onClick(View item, View widget, String type, int position, int which);
}
