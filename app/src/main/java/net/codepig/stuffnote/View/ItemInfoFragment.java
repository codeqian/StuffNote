package net.codepig.stuffnote.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.codepig.stuffnote.DataBean.ItemInfo;
import net.codepig.stuffnote.R;
import net.codepig.stuffnote.common.MessageCode;

/**
 * 物品详情
 */
public class ItemInfoFragment extends Fragment {
    private View thisView;
    private Context _context;
    private Activity _MainActivity;
    private FragmentDataCommunicate mFragmentDataCommunicate;
    private ItemInfo _info;

    private ImageView closeBtn,editItemBtn,item_image,deleteItemBtn;
    private TextView item_name,item_loc,item_fun,item_des,item_color;
    private AlertDialog alertDialog;

    final String TAG="ItemInfoFragment LOGCAT";

    public ItemInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        thisView=inflater.inflate(R.layout.fragment_item_info, container, false);
        closeBtn=thisView.findViewById(R.id.closeBtn);
        editItemBtn=thisView.findViewById(R.id.editItemBtn);
        deleteItemBtn=thisView.findViewById(R.id.deleteItemBtn);
        item_image=thisView.findViewById(R.id.item_image);
        item_name=thisView.findViewById(R.id.item_name);
        item_loc=thisView.findViewById(R.id.item_loc);
        item_fun=thisView.findViewById(R.id.item_fun);
        item_des=thisView.findViewById(R.id.item_des);
        item_color=thisView.findViewById(R.id.item_color);
        alertDialog = new AlertDialog.Builder(_context).create();

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "是啊", alertListener);
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "不了", alertListener);

        closeBtn.setOnClickListener(btnClick);
        editItemBtn.setOnClickListener(btnClick);
        deleteItemBtn.setOnClickListener(btnClick);
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

    /**
     * 按钮监听
     */
    private View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.closeBtn:
                    mFragmentDataCommunicate.HideMe(MessageCode.INFO_ITEM);
                    break;
                case R.id.editItemBtn:
                    mFragmentDataCommunicate.SendData(null,MessageCode.INFO_ITEM);
                    break;
                case R.id.deleteItemBtn:
                    showAlert("确定要删除这条记录吗？");
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 警告框监听
     */
    DialogInterface.OnClickListener alertListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //删除记录
                    mFragmentDataCommunicate.DeleteData(_info,MessageCode.INFO_ITEM);
                    hideAlert();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    hideAlert();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 显示警告消息框
     * @param msg
     */
    private void showAlert(String msg){
        alertDialog.setMessage(msg);
        alertDialog.show();
    }

    /**
     * 隐藏警告消息框
     */
    private void hideAlert(){
        alertDialog.hide();
    }

    public void setInfo(ItemInfo _v){
        _info=_v;
        item_name.setText(_info.get_name());
        item_loc.setText(_info.get_location());
        item_fun.setText(_info.get_function());
        item_des.setText(_info.get_description());
        item_color.setText(_info.get_color());
        //还缺加载图片
    }
}
