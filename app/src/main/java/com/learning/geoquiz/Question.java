package com.learning.geoquiz;

/**
 * Created by sherif on 9/12/15.
 */
public class Question {

    private int mTextResId;
    private boolean mCorrectAnswer;

    public Question(int textResId, boolean correctAnswer) {
        mTextResId = textResId;
        mCorrectAnswer = correctAnswer;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean getCorrectAnswer() {
        return mCorrectAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        mCorrectAnswer = correctAnswer;
    }
}
