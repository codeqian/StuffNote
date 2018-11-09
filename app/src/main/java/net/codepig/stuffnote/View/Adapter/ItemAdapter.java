package net.codepig.stuffnote.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.codepig.stuffnote.DataBean.ItemInfo;
import net.codepig.stuffnote.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    private Context context;
    private List<ItemInfo> myData;
    private LayoutInflater inflater;
    private ListItemClickListener callback;//按钮点击
    private String _uid="";
    private ViewGroup _parent;

    public ItemAdapter(Context context, List<ItemInfo> data,ListItemClickListener callback) {
        super();
        this.context = context;
        this.myData = data;
        this.callback=callback;
        inflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
//        private TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
//            title = (TextView) itemView.findViewById(R.id.rv_main_item_title);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(inflater.inflate(R.layout.item_adapter, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.title.setText(myData.get(position));
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }
}
