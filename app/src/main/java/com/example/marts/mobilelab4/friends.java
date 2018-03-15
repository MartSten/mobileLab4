package com.example.marts.mobilelab4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by marts on 11.03.2018.
 */

public class friends extends android.support.v4.app.Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.friends_layout, container, false);
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
