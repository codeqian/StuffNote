package net.codepig.stuffnote;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import net.codepig.stuffnote.DataBean.TipInfo;
import net.codepig.stuffnote.View.Adapter.TipAdapter;

import java.util.List;

import static net.codepig.stuffnote.DataPresenter.BeanBox.getLocationTipList;
import static net.codepig.stuffnote.DataPresenter.BeanBox.testTipList;

public class MainActivity extends AppCompatActivity {
    private int _pageIndex=0;
    private Context _context;

    //data
    private static List<TipInfo> LocationTipList;
    //view
    private ImageView searchBtn,setBtn;
    private TextView localBtn,typeBtn,colorBtn,listBtn;
    private Button newBtn;
    private RecyclerView TipList;

    private final int GO_LOCAL=0;
    private final int GO_TYPE=1;
    private final int GO_COLOR=2;
    private final int GO_LIST=3;
    private final String TAG="MAIN PAGE LOGCAT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _context=this;
        initView();
        createTipList();
    }

    private void initView(){
        searchBtn=findViewById(R.id.searchBtn);
        setBtn=findViewById(R.id.setBtn);
        localBtn=findViewById(R.id.localBtn);
        typeBtn=findViewById(R.id.typeBtn);
        colorBtn=findViewById(R.id.colorBtn);
        listBtn=findViewById(R.id.listBtn);
        newBtn=findViewById(R.id.newBtn);
        TipList=findViewById(R.id.TipList);

        setBtn.setOnClickListener(btnClick);
        searchBtn.setOnClickListener(btnClick);
        localBtn.setOnClickListener(btnClick);
        typeBtn.setOnClickListener(btnClick);
        colorBtn.setOnClickListener(btnClick);
        listBtn.setOnClickListener(btnClick);
        newBtn.setOnClickListener(btnClick);
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
                    }else{
                        //新建物品
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
        _pageIndex=_index;
        localBtn.setTextColor(getResources().getColor(R.color.colorText));
        typeBtn.setTextColor(getResources().getColor(R.color.colorText));
        colorBtn.setTextColor(getResources().getColor(R.color.colorText));
        listBtn.setTextColor(getResources().getColor(R.color.colorText));
        switch (_index){
            case GO_LOCAL:
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

    private void createTipList(){
        testTipList();//locationTip
        TipList.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
//        TipList.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于grid view
//        TipList.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
        LocationTipList=getLocationTipList();
        Log.d(TAG,"list size:"+LocationTipList.size());
        TipList.setAdapter(new TipAdapter(this,LocationTipList));
    }
}
