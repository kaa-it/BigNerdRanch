package com.gmail.it.kruglov.geoquiz;

/**
 * Created by kruglov on 06.11.2017.
 */

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private int mAnswered;

    public int getAnswered() {
        return mAnswered;
    }

    public void setAnswered(int answered) {
        mAnswered = answered;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mAnswered = 0;
    }
}
