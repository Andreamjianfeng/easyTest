package com.jianfeng.jianfengdriving.FragmenView;

/**
 * Created by 吴剑锋 on 2017/4/29.
 *
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class BaseFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return container;
    }
    public void showSnackar(View view ,String string){
        Snackbar.make(view, string, Snackbar.LENGTH_LONG)
                .show();
    }



}