package net.codepig.stuffnote.View;

import net.codepig.stuffnote.DataBean.ItemInfo;

public interface FragmentDataCommunicate {
    void DeleteData(ItemInfo _info,int ViewCode);
    void SendData(ItemInfo _info,int ViewCode);
    void HideMe(int ViewCode);
}
