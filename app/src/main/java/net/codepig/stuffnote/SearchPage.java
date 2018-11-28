package net.codepig.stuffnote;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import net.codepig.stuffnote.DataBean.ItemInfo;
import net.codepig.stuffnote.DataPresenter.BeanBox;
import net.codepig.stuffnote.View.Adapter.ItemAdapter;
import net.codepig.stuffnote.View.Adapter.ListItemClickListener;
import net.codepig.stuffnote.View.FragmentDataCommunicate;
import net.codepig.stuffnote.View.ItemInfoFragment;
import net.codepig.stuffnote.View.NewItemFragment;
import net.codepig.stuffnote.common.MessageCode;

import java.util.List;

import static net.codepig.stuffnote.DataPresenter.BeanBox.GetTheItemByNameList;
import static net.codepig.stuffnote.DataPresenter.BeanBox.GetTipList;
import static net.codepig.stuffnote.DataPresenter.BeanBox.getTheItemList;

/**
 * 搜索
 */
public class SearchPage extends AppCompatActivity implements FragmentDataCommunicate {
    private AppCompatActivity _context;
    private ImageView backBtn,SearchBtn;
    private EditText searchText;
    private FrameLayout newItemView,itemInfoView;
    //fragment
    private ItemInfoFragment itemInfoFragment;
    private NewItemFragment newItemFragment;
    private ItemAdapter itemAdapter;
    private RecyclerView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        _context=this;

        initView();
    }

    private void initView(){
        backBtn=findViewById(R.id.backBtn);
        SearchBtn=findViewById(R.id.SearchBtn);
        searchText=findViewById(R.id.searchText);
        mListView=findViewById(R.id.ItemList);
        itemInfoView=findViewById(R.id.itemInfoView);
        newItemView=findViewById(R.id.newItemView);
        FragmentManager fragmentManager = getSupportFragmentManager();
        itemInfoFragment=(ItemInfoFragment) fragmentManager.findFragmentById(R.id.ItemInfoFragment);
        newItemFragment=(NewItemFragment) fragmentManager.findFragmentById(R.id.newItemFragment);

        mListView.setLayoutManager(new LinearLayoutManager(this));

        backBtn.setOnClickListener(btnClick);
        SearchBtn.setOnClickListener(btnClick);
//        if(GetTheItemByNameList(searchText.getText().toString())>0){
//            CreateItemList();
//        }
    }

    private View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.backBtn:
                    finish();
                    break;
                case R.id.SearchBtn:
                    //搜索
                    final InputMethodManager mInputMethodManager = (InputMethodManager) _context.getSystemService(INPUT_METHOD_SERVICE);
                    mInputMethodManager.hideSoftInputFromWindow(_context.getCurrentFocus().getWindowToken(), 0);
                    if(GetTheItemByNameList(searchText.getText().toString())>0){
                        CreateItemList();
                    }
                    break;
            }
        }
    };

    /**
     * 创建物品列表
     */
    private void CreateItemList(){
        final List<ItemInfo> _List=getTheItemList();
        Log.d("LOGCAT","getTheItemList:"+_List.size());
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
                if(GetTheItemByNameList(searchText.getText().toString())>0){
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
                        if (GetTheItemByNameList(searchText.getText().toString()) > 0) {
                            CreateItemList();
                        }
                    }
                }else {
                    if (BeanBox.InsertNewItem(_info) > 0) {
                        //保存后更新整个列表
                        if (GetTheItemByNameList(searchText.getText().toString()) > 0) {
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
//        Log.d(TAG,"send view");
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
