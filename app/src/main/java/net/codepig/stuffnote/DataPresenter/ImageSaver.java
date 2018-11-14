package net.codepig.stuffnote.DataPresenter;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;

import static net.codepig.stuffnote.common.BaseConfig.FilePath;
import static net.codepig.stuffnote.common.BaseConfig.SdCardRoot;

/**
 * 处理图片存储
 */
public class ImageSaver {
    private static Boolean hadSdcard=false;

    /**
     * 判断sd卡是否存在
     * @return
     */
    public static boolean CheckSDCard() {
        String _path;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            hadSdcard=true;
            if(SdCardRoot.equals("")){
                SdCardRoot= android.os.Environment.getExternalStorageDirectory().toString();
            }
            _path=SdCardRoot+FilePath;
            CreateDir(_path);
        } else{
            hadSdcard=false;
        }
        return hadSdcard;
    }

    /**
     * 创建文件目录
     * @param _path
     * @return
     */
    private static boolean CreateDir(String _path){
        try{
            File file = new File(_path);
            if (!file.exists())
            {
                Log.d("LOGCAT","Create dir:"+_path);
                if (file.mkdirs())
                {
                    return true;
                }
            }else{
                return true;
            }
        }catch (Exception e){
        }
        return false;
    }

    /**
     * sd卡剩余大小
     * @return
     */
    public static long GetSDCardFreeSize(){
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSizeLong();
        //空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocksLong();
        //返回SD卡空闲大小
        return (freeBlocks * blockSize)/1024 /1024; //单位MB
    }

    /**
     * sd卡总容量
     * @return
     */
    public static long GetSDCardAllSize(){
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSizeLong();
        //获取所有数据块数
        long allBlocks = sf.getBlockCountLong();
        //返回SD卡大小
        return (allBlocks * blockSize)/1024/1024; //单位MB
    }
}
