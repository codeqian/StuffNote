package net.codepig.stuffnote;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import net.codepig.stuffnote.DataBean.ItemInfo;
import net.codepig.stuffnote.DataBean.TipInfo;
import net.codepig.stuffnote.DataPresenter.BeanBox;
import net.codepig.stuffnote.View.Adapter.ItemAdapter;
import net.codepig.stuffnote.View.Adapter.ListItemClickListener;
import net.codepig.stuffnote.View.Adapter.TipAdapter;
import net.codepig.stuffnote.View.FragmentDataCommunicate;
import net.codepig.stuffnote.View.ItemInfoFragment;
import net.codepig.stuffnote.View.NewItemFragment;
import net.codepig.stuffnote.common.MessageCode;

import java.util.List;

import static net.codepig.stuffnote.DataPresenter.BeanBox.GetItemList;
import static net.codepig.stuffnote.DataPresenter.BeanBox.getColorTipList;
import static net.codepig.stuffnote.DataPresenter.BeanBox.getFunctionTipList;
import static net.codepig.stuffnote.common.MessageCode.GO_ALL;
import static net.codepig.stuffnote.common.MessageCode.GO_COLOR;
import static net.codepig.stuffnote.common.MessageCode.GO_FUNCTION;
import static net.codepig.stuffnote.common.MessageCode.GO_LIST;
import static net.codepig.stuffnote.common.MessageCode.GO_LOCAL;
import static net.codepig.stuffnote.DataPresenter.BeanBox.GetTipList;
import static net.codepig.stuffnote.DataPresenter.BeanBox.getItemList;
import static net.codepig.stuffnote.DataPresenter.BeanBox.getLocationTipList;

public class MainActivity extends AppCompatActivity implements FragmentDataCommunicate {
    private int _pageIndex=4;//4全部，0位置，1用途，2色彩，3清单
    private Context _context;

    //view
    private ImageView searchBtn,setBtn;
    private TextView localBtn,typeBtn,colorBtn,listBtn,allBtn;
    private Button newBtn;
    private RecyclerView mListView;
    private FrameLayout newItemView,newTodoView,itemInfoView;
    private TipAdapter tipAdapter;
    private ItemAdapter itemAdapter;

    //fragment
    private ItemInfoFragment itemInfoFragment;
    private NewItemFragment newItemFragment;

    //final TAG
    private final String TAG="MAIN PAGE LOGCAT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _context=this;
        //初始化sql
        BeanBox.set_context(this);
        BeanBox.initSqlManager();
        //初始化View
        initView();
    }

    private void initView(){
        searchBtn=findViewById(R.id.searchBtn);
        setBtn=findViewById(R.id.setBtn);
        localBtn=findViewById(R.id.localBtn);
        allBtn=findViewById(R.id.allBtn);
        typeBtn=findViewById(R.id.typeBtn);
        colorBtn=findViewById(R.id.colorBtn);
        listBtn=findViewById(R.id.listBtn);
        newBtn=findViewById(R.id.newBtn);
        mListView=findViewById(R.id.TipList);
        newItemView=findViewById(R.id.newItemView);
        itemInfoView=findViewById(R.id.itemInfoView);
        newTodoView=findViewById(R.id.newTodoView);

        mListView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
//        TipList.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于grid view
//        TipList.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流

        setBtn.setOnClickListener(btnClick);
        searchBtn.setOnClickListener(btnClick);
        localBtn.setOnClickListener(btnClick);
        allBtn.setOnClickListener(btnClick);
        typeBtn.setOnClickListener(btnClick);
        colorBtn.setOnClickListener(btnClick);
        listBtn.setOnClickListener(btnClick);
        newBtn.setOnClickListener(btnClick);

        FragmentManager fragmentManager = getSupportFragmentManager();
        itemInfoFragment=(ItemInfoFragment) fragmentManager.findFragmentById(R.id.ItemInfoFragment);
        newItemFragment=(NewItemFragment) fragmentManager.findFragmentById(R.id.newItemFragment);

        //获得当前有的标签和物品列表
        GetTipList();
        if(GetItemList()>0){
            CreateItemList();
        }
    }

    private View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent _intent;
//            Log.d(TAG,"click:"+view.getId());
            switch (view.getId()){
                case R.id.searchBtn:
                    _intent=new Intent();
                    _intent.setClass(_context, SearchPage.class);
                    startActivity(_intent);
                    break;
                case R.id.setBtn:
                    _intent=new Intent();
                    _intent.setClass(_context, SettingPage.class);
                    startActivity(_intent);
                    break;
                case R.id.allBtn:
                    changePage(GO_ALL);
                    break;
                case R.id.localBtn:
                    changePage(GO_LOCAL);
                    break;
                case R.id.typeBtn:
                    changePage(GO_FUNCTION);
                    break;
                case R.id.colorBtn:
                    changePage(GO_COLOR);
                    break;
                case R.id.listBtn:
                    changePage(GO_LIST);
                    break;
                case R.id.newBtn:
                    if(_pageIndex==GO_LIST){
                        //新建清单
                        newTodoView.setVisibility(View.VISIBLE);
                    }else{
                        //新建物品
                        newItemView.setVisibility(View.VISIBLE);
                        newBtn.setVisibility(View.GONE);
                    }
                    break;
                    default:
                        break;
            }
        }
    };

    /**
     * 切换列表
     * @param _index
     */
    private void changePage(int _index){
//        Log.d(TAG,"page "+_pageIndex+" to page "+_index);
        if(_pageIndex==_index){
            return;
        }
        mListView.removeAllViews();
//        Log.d(TAG,"removeAllViews");
        _pageIndex=_index;
        allBtn.setTextColor(getResources().getColor(R.color.colorText));
        localBtn.setTextColor(getResources().getColor(R.color.colorText));
        typeBtn.setTextColor(getResources().getColor(R.color.colorText));
        colorBtn.setTextColor(getResources().getColor(R.color.colorText));
        listBtn.setTextColor(getResources().getColor(R.color.colorText));
        switch (_index){
            case GO_ALL:
                CreateItemList();
                allBtn.setTextColor(getResources().getColor(R.color.colorTitle));
                break;
            case GO_LOCAL:
                CreateTipList(_index);
                localBtn.setTextColor(getResources().getColor(R.color.colorTitle));
                break;
            case GO_FUNCTION:
                CreateTipList(_index);
                typeBtn.setTextColor(getResources().getColor(R.color.colorTitle));
                break;
            case GO_COLOR:
                CreateTipList(_index);
                colorBtn.setTextColor(getResources().getColor(R.color.colorTitle));
                break;
            case GO_LIST:
                listBtn.setTextColor(getResources().getColor(R.color.colorTitle));
                break;
                default:
                    break;
        }
    }

    /**
     * 创建标签列表
     * @param _index
     */
    private void CreateTipList(int _index){
        List<TipInfo> _TipList=null;
        switch (_index){
            case GO_LOCAL:
                _TipList=getLocationTipList();
                break;
            case GO_FUNCTION:
                _TipList=getFunctionTipList();
                break;
            case GO_COLOR:
                _TipList=getColorTipList();
                break;
        }
        if(_TipList==null){
//            Log.d(TAG,"_TipList==null");
            return;
        }
        final List<TipInfo> _List=_TipList;
//        Log.d(TAG,"List:"+_List.size());
        tipAdapter=new TipAdapter(this,_List,_index);
        mListView.setAdapter(tipAdapter);
        tipAdapter.setOnItemClickListener(new ListItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(itemInfoView.getVisibility()==View.VISIBLE || newItemView.getVisibility()==View.VISIBLE) return;
                Intent _intent=new Intent();
                _intent.putExtra("type", _pageIndex+"");
                _intent.putExtra("value", _List.get(position).get_value());
                _intent.setClass(_context, ItemListPage.class);
                startActivity(_intent);
            }
        });
    }

    /**
     * 创建物品列表
     */
    private void CreateItemList(){
        final List<ItemInfo> _List=getItemList();
        itemAdapter=new ItemAdapter(this,_List);
        mListView.setAdapter(itemAdapter);
        itemAdapter.setOnItemClickListener(new ListItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(itemInfoView.getVisibility()==View.VISIBLE || newItemView.getVisibility()==View.VISIBLE) return;
                itemInfoView.setVisibility(View.VISIBLE);
                //设置详情data
                itemInfoFragment.setInfo(_List.get(position));
            }
        });
    }

    //与Fragment通信的方法 (FragmentDataCommunicate接口中定义)>>---------------------------------------------------------------
    /**
     * 删除条目(暂时只用于详情fragment)
     */
    public void DeleteData(ItemInfo _info,int ViewCode){
        switch (ViewCode){
            case MessageCode.INFO_ITEM:
                itemInfoView.setVisibility(View.GONE);
                BeanBox.DeleteTheItem(_info);
                //更新列表
                if(_pageIndex==GO_ALL){
                    if(GetItemList()>=0) {
                        Log.d(TAG,"BeanBox.getItemList().size()"+BeanBox.getItemList().size());
                        CreateItemList();
                    }
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
                newBtn.setVisibility(View.GONE);
                break;
            case MessageCode.NEW_ITEM:
                newItemView.setVisibility(View.GONE);
                newBtn.setVisibility(View.VISIBLE);
                //保存信息到数据库
                //_EditFlag为true时时更新数据
                if(_EditFlag){
                    long _id=BeanBox.UpdateItem(_info);
                    if(_id>0){
                        //保存后更新整个列表
                        if (_pageIndex == GO_ALL && GetItemList() > 0) {
                            CreateItemList();
                        }
                    }
                }else {
                    if (BeanBox.InsertNewItem(_info) > 0) {
                        //保存后更新整个列表
                        if (_pageIndex == GO_ALL && GetItemList() > 0) {
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
//        view.setVisibility(View.GONE);
        switch (ViewCode){
            case MessageCode.INFO_ITEM:
                itemInfoView.setVisibility(View.GONE);
                break;
            case MessageCode.NEW_ITEM:
                newItemView.setVisibility(View.GONE);
                newBtn.setVisibility(View.VISIBLE);
                break;
        }
//        Log.d(TAG,"hide view");
    }
}
