package net.codepig.stuffnote.View;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import net.codepig.stuffnote.DataBean.ItemInfo;
import net.codepig.stuffnote.DataPresenter.TimeBox;
import net.codepig.stuffnote.R;
import net.codepig.stuffnote.common.MessageCode;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * 新建物品
 */
public class NewItemFragment extends Fragment {
    private Context _context;
    private Activity _MainActivity;
    private FragmentDataCommunicate mFragmentDataCommunicate;
    private ItemInfo _info;
    private String _imageUrl="";

    private View thisView;
    private Button enterItem,cancelItem;
    private EditText item_name,item_loc,item_fun,item_des;
    private RadioButton redBtn,orangeBtn,yellowBtn,greenBtn,blueBtn,cyanBtn,purpleBtn;
    private RadioGroup radioGroup;
    private int _colorTip=MessageCode.RED_TIP;

    final String TAG="NewItemFragment LOGCAT";
    public NewItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        thisView=inflater.inflate(R.layout.fragment_new_item, container, false);
        cancelItem=thisView.findViewById(R.id.cancelItem);
        enterItem=thisView.findViewById(R.id.enterItem);
        item_name=thisView.findViewById(R.id.item_name);
        item_loc=thisView.findViewById(R.id.item_loc);
        item_fun=thisView.findViewById(R.id.item_fun);
        item_des=thisView.findViewById(R.id.item_des);
        redBtn=thisView.findViewById(R.id.redBtn);
        orangeBtn=thisView.findViewById(R.id.orangeBtn);
        yellowBtn=thisView.findViewById(R.id.yellowBtn);
        greenBtn=thisView.findViewById(R.id.greenBtn);
        blueBtn=thisView.findViewById(R.id.blueBtn);
        cyanBtn=thisView.findViewById(R.id.cyanBtn);
        purpleBtn=thisView.findViewById(R.id.purpleBtn);
        radioGroup = thisView.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                //获取变更后的选中项的ID
                int radioButtonId = arg0.getCheckedRadioButtonId();
                switch (radioButtonId){
                    case R.id.redBtn:
                        _colorTip=MessageCode.RED_TIP;
                        break;
                    case R.id.orangeBtn:
                        _colorTip=MessageCode.ORANGE_TIP;
                        break;
                    case R.id.yellowBtn:
                        _colorTip=MessageCode.YELLOW_TIP;
                        break;
                    case R.id.greenBtn:
                        _colorTip=MessageCode.GREEN_TIP;
                        break;
                    case R.id.blueBtn:
                        _colorTip=MessageCode.BLUE_TIP;
                        break;
                    case R.id.cyanBtn:
                        _colorTip=MessageCode.CYAN_TIP;
                        break;
                    case R.id.purpleBtn:
                        _colorTip=MessageCode.PURPLE_TIP;
                        break;
                        default:
                            break;
                }
            }
        });

        cancelItem.setOnClickListener(btnClick);
        enterItem.setOnClickListener(btnClick);
        return thisView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _context=context;
        _MainActivity=getActivity();
        if (context instanceof FragmentDataCommunicate){
            mFragmentDataCommunicate = (FragmentDataCommunicate)context;
        }else{
            Log.d(TAG," Must implement FragmentDataCommunicate");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            Log.d(TAG,"click:"+view.getId());
            final InputMethodManager mInputMethodManager = (InputMethodManager) _MainActivity.getSystemService(INPUT_METHOD_SERVICE);
            switch (view.getId()){
                case R.id.enterItem:
                    if(item_name.getText().toString()==null || item_name.getText().toString().equals("") || item_des.getText().toString()==null || item_des.getText().toString().equals("")){
                        Toast.makeText(_context, "必须输入名称与描述", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    _info=new ItemInfo();
                    _info.set_name(item_name.getText().toString());
                    _info.set_description(item_des.getText().toString());
                    _info.set_location(item_loc.getText().toString());
                    _info.set_function(item_fun.getText().toString());
                    _info.set_color(_colorTip+"");
                    _info.set_imageUrl(_imageUrl);
                    _info.set_time(TimeBox.getCurrentTime());
//                    还缺保存图片
                    mFragmentDataCommunicate.SendData(_info,MessageCode.NEW_ITEM);
                    mInputMethodManager.hideSoftInputFromWindow(_MainActivity.getCurrentFocus().getWindowToken(), 0);
//                    Log.d(TAG,"info:"+_info.get_location()+"_"+_info.get_function());
                    break;
                case R.id.cancelItem:
                    mFragmentDataCommunicate.HideMe(MessageCode.NEW_ITEM);
                    mInputMethodManager.hideSoftInputFromWindow(_MainActivity.getCurrentFocus().getWindowToken(), 0);
                    break;
                default:
                    break;
            }
        }
    };
}
