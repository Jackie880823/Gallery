package com.jackie.gallery;

import com.madxstudio.libs.BaseApp;

/**
 * Created on 01/12/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public class App extends BaseApp {

    private static App appContext;
    public static App getContextInstance() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }
}
