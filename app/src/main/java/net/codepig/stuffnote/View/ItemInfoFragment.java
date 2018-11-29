package net.codepig.stuffnote.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.codepig.stuffnote.DataBean.ItemInfo;
import net.codepig.stuffnote.DataPresenter.BeanBox;
import net.codepig.stuffnote.DataPresenter.ImageLoader;
import net.codepig.stuffnote.R;
import net.codepig.stuffnote.common.BaseConfig;
import net.codepig.stuffnote.common.MessageCode;
import net.codepig.stuffnote.common.ThreadPoolUtils;

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
    private TextView item_name,item_loc,item_fun,item_des;
    private View itemColor;
    private AlertDialog alertDialog;

    private final String TAG="ItemInfoFragment LOGCAT";

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
        itemColor=thisView.findViewById(R.id.itemColor);
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
                    mFragmentDataCommunicate.SendData(_info,MessageCode.INFO_ITEM,false);
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
        if(_info.get_location()!=null && !_info.get_location().equals("")) {
            item_loc.setText(_info.get_location());
            item_loc.setVisibility(View.VISIBLE);
        }else{
            item_loc.setVisibility(View.GONE);
        }
        if(_info.get_function()!=null && !_info.get_function().equals("")) {
            item_fun.setText(_info.get_function());
            item_fun.setVisibility(View.VISIBLE);
        }else{
            item_fun.setVisibility(View.GONE);
        }
        item_des.setText(_info.get_description());
        switch (Integer.parseInt(_info.get_color())){
            case MessageCode.RED_TIP:
                itemColor.setBackgroundResource(R.drawable.pot_red);
                break;
            case MessageCode.ORANGE_TIP:
                itemColor.setBackgroundResource(R.drawable.pot_orange);
                break;
            case MessageCode.YELLOW_TIP:
                itemColor.setBackgroundResource(R.drawable.pot_yellow);
                break;
            case MessageCode.GREEN_TIP:
                itemColor.setBackgroundResource(R.drawable.pot_green);
                break;
            case MessageCode.CYAN_TIP:
                itemColor.setBackgroundResource(R.drawable.pot_cyan);
                break;
            case MessageCode.BLUE_TIP:
                itemColor.setBackgroundResource(R.drawable.pot_blue);
                break;
            case MessageCode.PURPLE_TIP:
                itemColor.setBackgroundResource(R.drawable.pot_purple);
                break;
            default:
                itemColor.setBackgroundResource(R.drawable.pot_cyan);
                break;
        }
        if(_info.get_imageUrl()!=null && !_info.get_imageUrl().equals("")) {
            final String _url=BaseConfig.SdCardRoot+BaseConfig.FilePath+_info.get_imageUrl()+".png";
//            Log.d(TAG,"_url:"+_url);
            Runnable bmpR = new Runnable() {
                @Override
                public void run() {
                    final Bitmap Bmp = ImageLoader.returnBitMapLocal(_url, 480, 360);
                    item_image.post(new Runnable() {
                        @Override
                        public void run() {
                            item_image.setImageBitmap(Bmp);
                        }
                    });
                }
            };
            ThreadPoolUtils.execute(bmpR);
            item_image.setVisibility(View.VISIBLE);
        }else{
            item_image.setVisibility(View.GONE);
        }
        //查询频次加1
        _info.set_fq(_info.get_fq()+1);
        BeanBox.UpdateItem(_info);
    }
}
