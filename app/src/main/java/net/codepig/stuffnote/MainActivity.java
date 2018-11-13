package net.codepig.stuffnote;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import net.codepig.stuffnote.DataBean.ItemInfo;
import net.codepig.stuffnote.DataBean.TipInfo;
import net.codepig.stuffnote.View.Adapter.ItemAdapter;
import net.codepig.stuffnote.View.Adapter.ListItemClickListener;
import net.codepig.stuffnote.View.Adapter.TipAdapter;

import java.util.List;

import static net.codepig.stuffnote.DataBean.MessageCode.GO_ALL;
import static net.codepig.stuffnote.DataBean.MessageCode.GO_COLOR;
import static net.codepig.stuffnote.DataBean.MessageCode.GO_LIST;
import static net.codepig.stuffnote.DataBean.MessageCode.GO_LOCAL;
import static net.codepig.stuffnote.DataBean.MessageCode.GO_TYPE;
import static net.codepig.stuffnote.DataPresenter.BeanBox.getItemList;
import static net.codepig.stuffnote.DataPresenter.BeanBox.getLocationTipList;
import static net.codepig.stuffnote.DataPresenter.BeanBox.testItemList;
import static net.codepig.stuffnote.DataPresenter.BeanBox.testTipList;

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
                    changePage(GO_TYPE);
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
                CreateTestItemList();
                allBtn.setTextColor(getResources().getColor(R.color.colorTitle));
                break;
            case GO_LOCAL:
                CreateTestTipList();
                localBtn.setTextColor(getResources().getColor(R.color.colorTitle));
                break;
            case GO_TYPE:
                typeBtn.setTextColor(getResources().getColor(R.color.colorTitle));
                break;
            case GO_COLOR:
                colorBtn.setTextColor(getResources().getColor(R.color.colorTitle));
                break;
            case GO_LIST:
                listBtn.setTextColor(getResources().getColor(R.color.colorTitle));
                break;
                default:
                    break;
        }
    }

//    测试数据---------------------------------------------------------------------------------------
    //测试标签列表
    private void CreateTestTipList(){
        testTipList();//locationTip
        TipList.removeAllViews();

        LocationTipList=getLocationTipList();
        tipAdapter=new TipAdapter(this,LocationTipList);
        TipList.setAdapter(tipAdapter);
        tipAdapter.setOnItemClickListener(new ListItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent _intent=new Intent();
                _intent.putExtra("type", _pageIndex+"");
                _intent.putExtra("value", LocationTipList.get(position).get_value());
                _intent.setClass(_context, ItemListPage.class);
                startActivity(_intent);
            }
        });
    }

    //测试物品列表
    private void CreateTestItemList(){
        testItemList();
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
