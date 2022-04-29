package com.example.escapebrides;

import android.app.Application;

import com.example.escapebrides.tools.GameServices;
import com.example.escapebrides.tools.MySP;

public class App  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MySP.initHelper(this);
        GameServices.initHelper(this);
    }


}
