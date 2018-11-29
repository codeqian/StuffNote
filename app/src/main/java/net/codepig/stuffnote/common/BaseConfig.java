package net.codepig.stuffnote.common;

/**
 * 一些基本设定
 */
public class BaseConfig {
    public static String _DbName="StuffNoteDataBase";
    public static String _ItemListTableName="ItemList";
    public static String _TipListTableName="TipList";
    public static String _TodoListTableName="TodoList";

    public static String SdCardRoot="";//SDCard根目录
    public static String FilePath="/StuffNote/Images/";
    public static String PhotoName="shoot.png";//拍摄的原始照片名(每次拍照覆盖)
    public static String CropName="crop.png";//剪裁后的照片名(每次覆盖)

    public static boolean OrderByFrequency=true;
}
