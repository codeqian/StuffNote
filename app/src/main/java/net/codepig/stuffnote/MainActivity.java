package net.codepig.stuffnote;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import net.codepig.stuffnote.DataBean.ItemInfo;
import net.codepig.stuffnote.DataBean.TipInfo;
import net.codepig.stuffnote.DataPresenter.DataBaseExecutive;
import net.codepig.stuffnote.View.Adapter.ItemAdapter;
import net.codepig.stuffnote.View.Adapter.ListItemClickListener;
import net.codepig.stuffnote.View.Adapter.TipAdapter;

import java.util.List;

import static net.codepig.stuffnote.DataPresenter.BeanBox.getColorTipList;
import static net.codepig.stuffnote.DataPresenter.BeanBox.getFunctionTipList;
import static net.codepig.stuffnote.DataPresenter.BeanBox.testTipList;
import static net.codepig.stuffnote.common.MessageCode.GO_ALL;
import static net.codepig.stuffnote.common.MessageCode.GO_COLOR;
import static net.codepig.stuffnote.common.MessageCode.GO_FUNCTION;
import static net.codepig.stuffnote.common.MessageCode.GO_LIST;
import static net.codepig.stuffnote.common.MessageCode.GO_LOCAL;
import static net.codepig.stuffnote.DataPresenter.BeanBox.GetItemList;
import static net.codepig.stuffnote.DataPresenter.BeanBox.GetTipList;
import static net.codepig.stuffnote.DataPresenter.BeanBox.getItemList;
import static net.codepig.stuffnote.DataPresenter.BeanBox.getLocationTipList;

public class MainActivity extends AppCompatActivity {
    private int _pageIndex=0;
    private Context _context;

    //data
    private static List<TipInfo> LocationTipList;
    private static List<ItemInfo> itemList;
    //view
    private ImageView searchBtn,setBtn;
    private TextView localBtn,typeBtn,colorBtn,listBtn,allBtn;
    private Button newBtn;
    private RecyclerView TipList;
    private FrameLayout newItemView,newTodoView,itemInfoView;
    private TipAdapter tipAdapter;
    private ItemAdapter itemAdapter;

    //fragment内的View
    private ImageView editItemBtn,closeItemBtn;
    private Button cancelNewTodo,enterNewTodo,cancelNewItem,enterNewItem;

    //Fragment
//    private Fragment newItemFragment;
//    private Fragment newTodoFragment;
//    private Fragment ItemInfoFragment;

    //final TAG
    private final String TAG="MAIN PAGE LOGCAT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _context=this;
        //初始化sql
        DataBaseExecutive.set_context(this);
        DataBaseExecutive.initSqlManager();
        //初始化View
        initView();
        //test
        testTipList();
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
        TipList=findViewById(R.id.TipList);
        newItemView=findViewById(R.id.newItemView);
        itemInfoView=findViewById(R.id.itemInfoView);
        newTodoView=findViewById(R.id.newTodoView);
        //fragment内
        cancelNewTodo=findViewById(R.id.cancelNewTodo);
        enterNewTodo=findViewById(R.id.enterNewTodo);
        cancelNewItem=findViewById(R.id.cancelNewItem);
        enterNewItem=findViewById(R.id.enterNewItem);
        editItemBtn=findViewById(R.id.editItemBtn);
        closeItemBtn=findViewById(R.id.closeItemBtn);

        TipList.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
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
        //fragment内
        enterNewTodo.setOnClickListener(btnClick);
        cancelNewTodo.setOnClickListener(btnClick);
        cancelNewItem.setOnClickListener(btnClick);
        enterNewItem.setOnClickListener(btnClick);
        editItemBtn.setOnClickListener(btnClick);
        closeItemBtn.setOnClickListener(btnClick);

        //获得当前有的标签和物品列表
        GetTipList();
//        if(GetItemList()>0){
//            //创建物品列表
//        }
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
                    }
                    break;
                case R.id.enterNewTodo:
                    newTodoView.setVisibility(View.GONE);
                    break;
                case R.id.cancelNewTodo:
                    newTodoView.setVisibility(View.GONE);
                    break;
                case R.id.enterNewItem:
                    newItemView.setVisibility(View.GONE);
                    break;
                case R.id.cancelNewItem:
                    newItemView.setVisibility(View.GONE);
                    break;
                case R.id.editItemBtn:
                    itemInfoView.setVisibility(View.GONE);
                    newItemView.setVisibility(View.VISIBLE);
                case R.id.closeItemBtn:
                    itemInfoView.setVisibility(View.GONE);
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
        _pageIndex=_index;
        allBtn.setTextColor(getResources().getColor(R.color.colorText));
        localBtn.setTextColor(getResources().getColor(R.color.colorText));
        typeBtn.setTextColor(getResources().getColor(R.color.colorText));
        colorBtn.setTextColor(getResources().getColor(R.color.colorText));
        listBtn.setTextColor(getResources().getColor(R.color.colorText));
        switch (_index){
            case GO_ALL:
//                CreateItemList();
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
        TipList.removeAllViews();
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
            return;
        }
        final List<TipInfo> _List=_TipList;
        tipAdapter=new TipAdapter(this,_List);
        TipList.setAdapter(tipAdapter);
        tipAdapter.setOnItemClickListener(new ListItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
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
        TipList.removeAllViews();

        itemList=getItemList();
        itemAdapter=new ItemAdapter(this,itemList);
        TipList.setAdapter(itemAdapter);
        itemAdapter.setOnItemClickListener(new ListItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                itemInfoView.setVisibility(View.VISIBLE);
            }
        });
    }
}
