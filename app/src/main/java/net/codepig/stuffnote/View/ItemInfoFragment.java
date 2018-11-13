package net.codepig.stuffnote.View;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.codepig.stuffnote.R;

/**
 * 物品详情
 */
public class ItemInfoFragment extends Fragment {
    private View thisView;

    public ItemInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        thisView=inflater.inflate(R.layout.fragment_item_info, container, false);
        return thisView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
