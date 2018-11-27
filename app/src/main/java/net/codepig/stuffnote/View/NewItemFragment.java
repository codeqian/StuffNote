package net.codepig.stuffnote.View;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import net.codepig.stuffnote.DataBean.ItemInfo;
import net.codepig.stuffnote.DataPresenter.ImageLoader;
import net.codepig.stuffnote.R;
import net.codepig.stuffnote.common.BaseConfig;
import net.codepig.stuffnote.common.GetImagePath;
import net.codepig.stuffnote.common.MessageCode;
import net.codepig.stuffnote.common.ThreadPoolUtils;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
import static net.codepig.stuffnote.DataPresenter.ImageSaver.CheckSDCard;
import static net.codepig.stuffnote.DataPresenter.ImageSaver.GetSDCardFreeSize;
import static net.codepig.stuffnote.DataPresenter.ImageSaver.SaveMyBitmap;
import static net.codepig.stuffnote.DataPresenter.TimeBox.getCurrentTime;

/**
 * 新建物品
 */
public class NewItemFragment extends Fragment {
    private Context _context;
    private Activity _MainActivity;
    private FragmentDataCommunicate mFragmentDataCommunicate;
    private ItemInfo _info;
    private String _imageUrl="";
    private int _colorTip=MessageCode.RED_TIP;
    private Uri mCutUri;
    private boolean reEdit=false;

    private View thisView;
    private Button enterItem,cancelItem;
    private EditText item_name,item_loc,item_fun,item_des;
    private RadioGroup radioGroup;
    private ImageView ImageBtn;

    //    resultCode，一些用于标识来源的特殊值
    private static final int TAKE_PICTURE=1024;
    private static final int CROP_PICTURE=2048;
    private static final int PHOTO_REQUEST_PERMISSIONS=4096;
    private final String TAG="NewItemFragment LOGCAT";

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
        ImageBtn=thisView.findViewById(R.id.ImageBtn);
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
        ImageBtn.setOnClickListener(btnClick);
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
     * 处理拍照后的数据
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"resultCode:"+resultCode+"_"+requestCode);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case TAKE_PICTURE: //相机返回的 uri
                    File temp = new File(BaseConfig.SdCardRoot+BaseConfig.FilePath,BaseConfig.PhotoName);
                    if(temp.exists()) {
                        //启动裁剪
                        //这里也要根据系统版本选择使用FileProvider
                        Uri dataUri;
                        if (Build.VERSION.SDK_INT >= 24) {
                            dataUri = FileProvider.getUriForFile(_context, "net.codepig", temp);
                        }else{
                            dataUri=Uri.fromFile(temp);
                        }
                        CutForCamera(dataUri);
                    }
                    break;
                case CROP_PICTURE:
                    /**
                     * 非空判断一定要验证，在剪裁之后如果发现不满意，data为空
                     */
                    if(data != null) {
                        try {
                            //获取裁剪后的图片，并显示出来
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(_context.getContentResolver(), mCutUri);
                            if(bitmap!=null) {
                                ImageBtn.setImageBitmap(bitmap);
                                String _name=getCurrentTime();
                                String _url = SaveMyBitmap(bitmap, _name);
                                Log.d(TAG,"url:"+_url);
                                _imageUrl=_name;
                            }
                        } catch (Exception e) {
                            Log.d(TAG,"Exception:"+e.toString());
                        }
                    }
                    break;
            }
        }
    }

    private View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            Log.d(TAG,"click:"+view.getId());
            final InputMethodManager mInputMethodManager = (InputMethodManager) _MainActivity.getSystemService(INPUT_METHOD_SERVICE);
            switch (view.getId()){
                case R.id.enterItem:
                    if(item_name.getText().toString()==null || item_name.getText().toString().equals("") || item_des.getText().toString()==null || item_des.getText().toString().equals("")){
                        Toast.makeText(_context, "必须要输入名称与描述哦!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(_info==null){
                        _info=new ItemInfo();
                    }
                    _info.set_name(item_name.getText().toString());
                    _info.set_description(item_des.getText().toString());
                    _info.set_location(item_loc.getText().toString());
                    _info.set_function(item_fun.getText().toString());
                    _info.set_color(_colorTip+"");
                    _info.set_imageUrl(_imageUrl);
                    _info.set_time(getCurrentTime());
                    mFragmentDataCommunicate.SendData(_info,MessageCode.NEW_ITEM,reEdit);
                    reEdit=false;
                    mInputMethodManager.hideSoftInputFromWindow(_MainActivity.getCurrentFocus().getWindowToken(), 0);
//                    Log.d(TAG,"info:"+_info.get_location()+"_"+_info.get_function());
                    break;
                case R.id.cancelItem:
                    mFragmentDataCommunicate.HideMe(MessageCode.NEW_ITEM);
                    mInputMethodManager.hideSoftInputFromWindow(_MainActivity.getCurrentFocus().getWindowToken(), 0);
                    break;
                case R.id.ImageBtn:
                    TakePicture();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 从详情页跳转这里时设置内容
     * @param _v
     */
    public void setInfo(ItemInfo _v){
        reEdit=true;
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

        int _id=-1;
        switch (Integer.parseInt(_info.get_color())){
            case MessageCode.RED_TIP:
                _id=R.id.redBtn;
                break;
            case MessageCode.ORANGE_TIP:
                _id=R.id.orangeBtn;
                break;
            case MessageCode.YELLOW_TIP:
                _id=R.id.yellowBtn;
                break;
            case MessageCode.GREEN_TIP:
                _id=R.id.greenBtn;
                break;
            case MessageCode.CYAN_TIP:
                _id=R.id.cyanBtn;
                break;
            case MessageCode.BLUE_TIP:
                _id=R.id.blueBtn;
                break;
            case MessageCode.PURPLE_TIP:
                _id=R.id.purpleBtn;
                break;
        }
        if(_id>0) {
            RadioButton _currentRadio = thisView.findViewById(_id);
            _currentRadio.setChecked(true);
        }

        if(_info.get_imageUrl()!=null && !_info.get_imageUrl().equals("")) {
            final String _url=BaseConfig.SdCardRoot+BaseConfig.FilePath+_info.get_imageUrl()+".png";
//            Log.d(TAG,"_url:"+_url);
            Runnable bmpR = new Runnable() {
                @Override
                public void run() {
                    final Bitmap Bmp = ImageLoader.returnBitMapLocal(_url, 480, 360);
                    ImageBtn.post(new Runnable() {
                        @Override
                        public void run() {
                            ImageBtn.setImageBitmap(Bmp);
                        }
                    });
                }
            };
            ThreadPoolUtils.execute(bmpR);
            ImageBtn.setVisibility(View.VISIBLE);
        }else{
            ImageBtn.setImageDrawable(_context.getResources().getDrawable(R.mipmap.camera));
        }
    }

    /**
     * 拍照
     */
    private void TakePicture(){
        if(CheckSDCard() && GetSDCardFreeSize()>10){
            //保存照片到外部地址
            File outImage = new File(BaseConfig.SdCardRoot+BaseConfig.FilePath,BaseConfig.PhotoName);
            Uri imageUri;
            //如果是7.0(Build.VERSION_CODES.N)以上 那么就把Uir包装（这里注意用到Provider，所以需要在AndroidManifest里注册）
            if (Build.VERSION.SDK_INT >= 24) {
                imageUri = FileProvider.getUriForFile(_context, "net.codepig", outImage);
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, PHOTO_REQUEST_PERMISSIONS);
            } else {
                //否则就用老系统的默认模式
                imageUri = Uri.fromFile(outImage);
            }
            final Intent takePictureImIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureImIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            /**
             * startActivityForResult的第二个参数：如果> = 0,当Activity结束时requestCode将归还在onActivityResult()中。以便确定返回的数据是从哪个Activity中返回，用来标识目标activity,所以最好定一个特殊好辨识的值。
             */
            startActivityForResult(takePictureImIntent,TAKE_PICTURE);
        }else{
            Toast.makeText(_context,"好像没有地方存照片呀！",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 拍照之后，启动裁剪
     * 卓系统自带图片裁剪功能 com.android.camera.action.CROP
     * @return
     */
    private void CutForCamera(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setDataAndType(uri, "image/*");
            intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            if (Build.VERSION.SDK_INT >= 19) {//android.os.Build.VERSION_CODES.KITKAT
                String url = GetImagePath.getPath(_context, uri);//这个方法是处理4.4以上图片返回的Uri对象不同的处理方法
                intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
            } else {
                intent.setDataAndType(uri, "image/*");
            }
        }
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 4);
        intent.putExtra("aspectY", 3);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 480);
        intent.putExtra("outputY", 360);
        //裁剪时是否保留图片的比例
        intent.putExtra("scale",true);
        //设置输出的格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true);
        //稍不留神就报图片太大的错误(OOM)，所以我们这里用false，不在这里传递图像
        intent.putExtra("return-data", false);

        mCutUri = Uri.fromFile(new File(BaseConfig.SdCardRoot+BaseConfig.FilePath,BaseConfig.CropName));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCutUri);//将图像输出到这个Uri中
        startActivityForResult(intent, CROP_PICTURE);
    }
}
