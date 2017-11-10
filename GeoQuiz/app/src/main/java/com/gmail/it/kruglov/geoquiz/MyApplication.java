package com.gmail.it.kruglov.geoquiz;

import android.app.Application;

/**
 * Created by kaa-i on 10.11.2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        QuestionBank.initInstance();
    }
}
