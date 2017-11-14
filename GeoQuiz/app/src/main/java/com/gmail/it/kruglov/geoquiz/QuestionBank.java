package com.gmail.it.kruglov.geoquiz;

import android.util.Log;

/**
 * Created by kaa-i on 10.11.2017.
 */

public class QuestionBank {

    private static QuestionBank mBank;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    public QuestionBank() {
        Log.d("QuestionBank", "Constructor");
    }

    public Question getQuestion(int index) {
        if (mQuestionBank.length <= index) {
            throw new IllegalArgumentException();
        }

        return mQuestionBank[index];
    }

    public int length() {
        return mQuestionBank.length;
    }
}
