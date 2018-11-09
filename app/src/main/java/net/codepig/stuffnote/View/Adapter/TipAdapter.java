package net.codepig.stuffnote.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.codepig.stuffnote.DataBean.TipInfo;
import net.codepig.stuffnote.R;

import java.util.List;

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.MyViewHolder> {
    private Context context;
    private List<TipInfo> myData;
    private LayoutInflater inflater;
    private String _uid="";
    private ViewGroup _parent;

    public TipAdapter(Context context, List<TipInfo> data) {
        super();
        this.context = context;
        this.myData = data;
        inflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView TipName;
        public MyViewHolder(View itemView) {
            super(itemView);
            TipName = itemView.findViewById(R.id.TipName);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(inflater.inflate(R.layout.tip_adapter, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.TipName.setText(myData.get(position).get_value());
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }
}
