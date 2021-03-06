package com.gmail.it.kruglov.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "QuizActivity.index";
    private static final String KEY_CHEAT_TRIES = "QuizActivity.cheat_tries";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static final int MAX_CHEAT_TRIES = 3;

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private TextView mCheatTriesTextView;

    private QuestionBank mQuestionBank = MyApplication.getQuestionBank();

    private int mCurrentIndex = 0;
    private int mCheatTries = MAX_CHEAT_TRIES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mCheatTries = savedInstanceState.getInt(KEY_CHEAT_TRIES, MAX_CHEAT_TRIES);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatTriesTextView = (TextView) findViewById(R.id.cheat_tries_text_view);

        updateQuestion();

        class NextListener implements View.OnClickListener {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length();
                updateQuestion();
            }
        };

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        NextListener nextListener = new NextListener();

        mQuestionTextView.setOnClickListener(nextListener);

        mNextButton.setOnClickListener(nextListener);

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length();
                if (mCurrentIndex < 0) {
                    mCurrentIndex = mQuestionBank.length() - 1;
                }
                Log.v(TAG, String.format("current index = %d", mCurrentIndex));
                updateQuestion();
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start CheatActivity
                boolean answerIsTrue = mQuestionBank.getQuestion(mCurrentIndex).isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        updateCheatTriesTextView();
    }

    private void updateCheatTriesTextView() {
        String text = getResources().getString(R.string.cheat_tries, mCheatTries);
        mCheatTriesTextView.setText(text);
        if (mCheatTries == 0) {
            hideCheatButton();
        }
    }

    private void hideCheatButton() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = mCheatButton.getWidth() / 2;
            int cy = mCheatButton.getHeight() / 2;
            float radius = mCheatButton.getWidth();
            Animator anim = ViewAnimationUtils
                    .createCircularReveal(mCheatButton, cx, cy, radius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mCheatButton.setVisibility(View.INVISIBLE);
                }
            });
            anim.start();
        } else {
            mCheatButton.setVisibility(View.INVISIBLE);
        }
    }

    private void updateQuestion() {
        Question currentQuestion = mQuestionBank.getQuestion(mCurrentIndex);
        int question = currentQuestion.getTextResId();
        mQuestionTextView.setText(question);
        mTrueButton.setEnabled(currentQuestion.getAnswered() == 0);
        mFalseButton.setEnabled(currentQuestion.getAnswered() == 0);
    }

    private void checkAnswer(boolean userPressedTrue) {
        Question currentQuestion = mQuestionBank.getQuestion(mCurrentIndex);

        boolean answerIsTrue = currentQuestion.isAnswerTrue();

        int messageResId = 0;

        if (currentQuestion.isCheated()) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                currentQuestion.setAnswered(1);
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);

        if (userPressedTrue == answerIsTrue) {
            toast.setGravity(Gravity.TOP, 0, getSupportActionBar().getHeight() + 50);
        }

        toast.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putInt(KEY_CHEAT_TRIES, mCheatTries);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }

            mQuestionBank.getQuestion(mCurrentIndex).setCheated(CheatActivity.wasAnswerShown(data));
            mCheatTries--;
            updateCheatTriesTextView();
        }
    }
}
