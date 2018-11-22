package net.codepig.stuffnote.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.codepig.stuffnote.DataBean.TipInfo;
import net.codepig.stuffnote.R;
import net.codepig.stuffnote.common.MessageCode;

import java.util.List;

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.MyViewHolder> {
    private Context context;
    private List<TipInfo> myData;
    private LayoutInflater inflater;
    private ViewGroup _parent;
    private int _TipType= MessageCode.GO_LOCAL;
    //声明接口
    private ListItemClickListener mOnItemClickListener;

    public TipAdapter(Context context, List<TipInfo> data,int _type) {
        super();
        _TipType=_type;
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

    /**
     * 设置供外部调用的Item点击监听
     */
    public void setOnItemClickListener(ListItemClickListener listener){
        mOnItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tip_adapter, parent, false);
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
        holder.TipName.setText(myData.get(position).get_value());
        if (myData != null) {
            if(_TipType==MessageCode.GO_COLOR){
                switch (Integer.parseInt(myData.get(position).get_value())){
                    case MessageCode.RED_TIP:
                        holder.TipName.setText("红");
                        holder.TipName.setTextColor(context.getResources().getColor(R.color.red));
                        break;
                    case MessageCode.ORANGE_TIP:
                        holder.TipName.setText("橙");
                        holder.TipName.setTextColor(context.getResources().getColor(R.color.orange));
                        break;
                    case MessageCode.YELLOW_TIP:
                        holder.TipName.setText("黄");
                        holder.TipName.setTextColor(context.getResources().getColor(R.color.yellow));
                        break;
                    case MessageCode.GREEN_TIP:
                        holder.TipName.setText("绿");
                        holder.TipName.setTextColor(context.getResources().getColor(R.color.green));
                        break;
                    case MessageCode.BLUE_TIP:
                        holder.TipName.setText("蓝");
                        holder.TipName.setTextColor(context.getResources().getColor(R.color.blue));
                        break;
                    case MessageCode.CYAN_TIP:
                        holder.TipName.setText("靛");
                        holder.TipName.setTextColor(context.getResources().getColor(R.color.cyan));
                        break;
                    case MessageCode.PURPLE_TIP:
                        holder.TipName.setText("紫");
                        holder.TipName.setTextColor(context.getResources().getColor(R.color.purple));
                        break;
                }
            }
        }
        holder.itemView.setTag(position);//将position保存在itemView的tag中
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }
}
