package net.codepig.stuffnote.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.codepig.stuffnote.DataBean.ItemInfo;
import net.codepig.stuffnote.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    private Context context;
    private List<ItemInfo> myData;
    private LayoutInflater inflater;
    private ViewGroup _parent;
    //声明接口
    private ListItemClickListener mOnItemClickListener;

    public ItemAdapter(Context context, List<ItemInfo> data) {
        super();
        this.context = context;
        this.myData = data;
        inflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName;
        private TextView itemFunction;
        private TextView itemLocation;
        private TextView itemDescription;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemFunction = itemView.findViewById(R.id.itemFunction);
            itemLocation = itemView.findViewById(R.id.itemLocation);
            itemDescription = itemView.findViewById(R.id.itemDescription);
        }
    }

    /**
     * 设置供外部调用的Item点击监听
     */
    public void setOnItemClickListener(ListItemClickListener listener){
        mOnItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_adapter, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, (int) view.getTag());//注意这里使用getTag方法获取position
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (myData != null) {
            holder.itemName.setText(myData.get(position).get_name());
            holder.itemFunction.setText(myData.get(position).get_function());
            holder.itemLocation.setText(myData.get(position).get_location());
            holder.itemDescription.setText(myData.get(position).get_description());
        }
        holder.itemView.setTag(position);//将position保存在itemView的tag中
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }
}
