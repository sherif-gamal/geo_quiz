    package com.learning.geoquiz;

    import android.app.Activity;
    import android.content.Intent;
    import android.os.PersistableBundle;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageButton;
    import android.widget.TextView;
    import android.widget.Toast;

    public class QuizActivity extends AppCompatActivity {

        private static final String TAG = "QuizActivity";
        private static final String KEY_INDEX = "index";
        private static final int REQUEST_CODE_CHEAT = 0;

        private Button mTrueButton;
        private Button mFalseButton;
        private ImageButton mPreviousButton;
        private ImageButton mNextButton;
        private TextView mQuestionTextView;
        private Button mCheatButton;
        private boolean mCheated;

        private Question[] mQuestions = new Question[] {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)};

        private int mCurrentIndex = 0;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.d(TAG, "onCreate(Bundle) called");
            setContentView(R.layout.activity_quiz);

            mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
            if (savedInstanceState != null)
                mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            updateQuestion();

            mTrueButton = (Button) findViewById(R.id.true_button);
            mFalseButton = (Button) findViewById(R.id.false_button);

            mTrueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(true);
                }
            });

            mFalseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(false);
                }
            });

            mNextButton = (ImageButton) findViewById(R.id.next_button);
            View.OnClickListener nextListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCheated = false;
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                    updateQuestion();
                }
            };
            mNextButton.setOnClickListener(nextListener);
            mQuestionTextView.setOnClickListener(nextListener);

            mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
            mPreviousButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCheated = false;
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestions.length;
                    updateQuestion();
                }
            });

            mCheatButton = (Button) findViewById(R.id.cheat_button);
            mCheatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean answer = mQuestions[mCurrentIndex].getCorrectAnswer();
                    Intent i = CheatActivity.newIntent(QuizActivity.this, answer);
                    startActivityForResult(i, REQUEST_CODE_CHEAT);
                }
            });
        }

        @Override
        protected void onStart() {
            super.onStart();
            Log.d(TAG, "onStart() called");
        }

        @Override
        protected void onPause() {
            super.onPause();
            Log.d(TAG, "onResume() called");
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
            Log.d(TAG, "onSaveInstanceState() called");
            outState.putInt(KEY_INDEX, mCurrentIndex);
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
                mCheated = CheatActivity.wasAnswerShown(data);
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_quiz, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        private void updateQuestion() {
            int question = mQuestions[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);
        }

        private void checkAnswer(boolean answer) {
            boolean correctAnswer = mQuestions[mCurrentIndex].getCorrectAnswer();
            int messageResId = 0;
            if (mCheated)
                messageResId = R.string.judgment_toast;
            else if (answer == correctAnswer)
                messageResId = R.string.correct_toast;
            else
                messageResId = R.string.incorrect_toast;
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        }
    }
