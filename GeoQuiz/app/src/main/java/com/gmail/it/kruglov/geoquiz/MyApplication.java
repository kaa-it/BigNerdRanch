package com.gmail.it.kruglov.geoquiz;

import android.app.Application;

/**
 * Created by kaa-i on 10.11.2017.
 */

public class MyApplication extends Application {

    private static QuestionBank sBank;

    @Override
    public void onCreate() {
        super.onCreate();
        sBank = new QuestionBank();
    }

    public static QuestionBank getQuestionBank() {
        return sBank;
    }
}
