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
    private EditText item_name,item_loc,item_fun,item_des,item_color;

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
        item_color=thisView.findViewById(R.id.item_color);

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
                    _info.set_color(item_color.getText().toString());
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
