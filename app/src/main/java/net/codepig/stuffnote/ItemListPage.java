package net.codepig.stuffnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import net.codepig.stuffnote.DataBean.ItemInfo;
import net.codepig.stuffnote.DataPresenter.BeanBox;
import net.codepig.stuffnote.View.Adapter.ItemAdapter;
import net.codepig.stuffnote.View.Adapter.ListItemClickListener;
import net.codepig.stuffnote.View.FragmentDataCommunicate;
import net.codepig.stuffnote.View.ItemInfoFragment;
import net.codepig.stuffnote.View.NewItemFragment;
import net.codepig.stuffnote.common.MessageCode;

import java.util.List;

import static net.codepig.stuffnote.DataPresenter.BeanBox.GetItem4TipList;
import static net.codepig.stuffnote.DataPresenter.BeanBox.GetTipList;
import static net.codepig.stuffnote.DataPresenter.BeanBox.getItem4TipList;

/**
 * 分维度物品列表
 */
public class ItemListPage extends AppCompatActivity implements FragmentDataCommunicate {
    private ImageView backBtn;
    private TextView tipTitle;
    private FrameLayout newItemView,itemInfoView;
    //fragment
    private ItemInfoFragment itemInfoFragment;
    private NewItemFragment newItemFragment;
    private ItemAdapter itemAdapter;
    private RecyclerView mListView;

    private int _type;
    private String _key;

    private final String TAG="ItemList LOGCAT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_page);
        Intent intent=getIntent();
        try {
            _type = Integer.parseInt(intent.getStringExtra("type"));
            _key = intent.getStringExtra("value");
        }catch (Exception e){
            finish();
        }
        Log.d(TAG,_type+"_"+_key);
        initView();
    }

    private void initView(){
        backBtn=findViewById(R.id.backBtn);
        itemInfoView=findViewById(R.id.itemInfoView);
        newItemView=findViewById(R.id.newItemView);
        tipTitle=findViewById(R.id.tipTitle);
        mListView=findViewById(R.id.TipList);
        FragmentManager fragmentManager = getSupportFragmentManager();
        itemInfoFragment=(ItemInfoFragment) fragmentManager.findFragmentById(R.id.itemInfoFragment);
        newItemFragment=(NewItemFragment) fragmentManager.findFragmentById(R.id.newItemFragment);

        mListView.setLayoutManager(new LinearLayoutManager(this));

        if(_type==MessageCode.GO_COLOR){
            switch (Integer.parseInt(_key)){
                case MessageCode.RED_TIP:
                    tipTitle.setText("红");
                    break;
                case MessageCode.ORANGE_TIP:
                    tipTitle.setText("橙");
                    break;
                case MessageCode.YELLOW_TIP:
                    tipTitle.setText("黄");
                    break;
                case MessageCode.GREEN_TIP:
                    tipTitle.setText("绿");
                    break;
                case MessageCode.CYAN_TIP:
                    tipTitle.setText("靛");
                    break;
                case MessageCode.BLUE_TIP:
                    tipTitle.setText("蓝");
                    break;
                case MessageCode.PURPLE_TIP:
                    tipTitle.setText("紫");
                    break;
            }
        }else {
            tipTitle.setText(_key);
        }

        backBtn.setOnClickListener(btnClick);
        if(GetItem4TipList(_type,_key)>0){
            CreateItemList();
        }
    }

    private View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.backBtn:
                    finish();
                    break;
            }
        }
    };

    /**
     * 创建物品列表
     */
    private void CreateItemList(){
        final List<ItemInfo> _List=getItem4TipList();
        itemAdapter=new ItemAdapter(this,_List);
        mListView.setAdapter(itemAdapter);
        itemAdapter.setOnItemClickListener(new ListItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(itemInfoView.getVisibility()==View.VISIBLE || newItemView.getVisibility()==View.VISIBLE) return;
                itemInfoView.setVisibility(View.VISIBLE);
//                //设置详情data
                itemInfoFragment.setInfo(_List.get(position));
            }
        });
    }

    //与Fragment通信的方法 (FragmentDataCommunicate接口中定义)>>---------------------------------------------------------------
    /**
     * 删除条目(暂时只用于详情fragment)
     */
    public void DeleteData(ItemInfo _info, int ViewCode){
        switch (ViewCode){
            case MessageCode.INFO_ITEM:
                itemInfoView.setVisibility(View.GONE);
                BeanBox.DeleteTheItem(_info);
                //更新列表
                if(GetItem4TipList(_type,_key)>0){
                    CreateItemList();
                }
                break;
        }
    }

    /**
     * 详情fragment页用来打开新建fragment页，fragment页用来发送新建命令
     * @param _info
     * @param ViewCode
     */
    public void SendData(ItemInfo _info,int ViewCode,boolean _EditFlag){
        switch (ViewCode){
            case MessageCode.INFO_ITEM:
                newItemView.setVisibility(View.VISIBLE);
                newItemFragment.setInfo(_info);
                itemInfoView.setVisibility(View.GONE);
                break;
            case MessageCode.NEW_ITEM:
                newItemView.setVisibility(View.GONE);
                //保存信息到数据库
                //_EditFlag为true时时更新数据
                if(_EditFlag){
                    long _id=BeanBox.UpdateItem(_info);
                    if(_id>0){
                        if (GetItem4TipList(_type, _key) > 0) {
                            CreateItemList();
                        }
                    }
                }else {
                    if (BeanBox.InsertNewItem(_info) > 0) {
                        //保存后更新整个列表
                        if (GetItem4TipList(_type, _key) > 0) {
                            CreateItemList();
                        }
                    }
                }
                //保存新标签
                int _count=BeanBox.InsertNewTip(_info);
                if(_count>0){
                    GetTipList();
                }
                break;
        }
    }

    /**
     * 隐藏fragment
     * @param ViewCode
     */
    public void HideMe(int ViewCode){
        switch (ViewCode){
            case MessageCode.INFO_ITEM:
                itemInfoView.setVisibility(View.GONE);
                break;
            case MessageCode.NEW_ITEM:
                newItemView.setVisibility(View.GONE);
                break;
        }
    }
}
