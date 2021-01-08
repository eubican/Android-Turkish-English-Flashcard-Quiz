package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AnimatorSet frontAnim;
    private AnimatorSet backAnim;
    private boolean isBack = false;
    private View cardFront;
    private View cardBack;
    private Button nextButton;
    private EditText inputBox;

    private TextView trueCounter;
    private TextView falseCounter;
    private TextView frontText;
    private TextView backText;

    private List<Flashcard> wordList;
    private int total;
    private Flashcard currentFC;
    private int counter = 0;
    public static int trueAnswers;
    public static int falseAnswers;
    private boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trueAnswers = 0;
        falseAnswers = 0;

        addButtons();
        loadTextViews();
        loadCards();

        loadAnimations();
        changeCameraDistance();

        DataAccessHelper dbHelper = new DataAccessHelper(this); // database access helper
        wordList = dbHelper.getAllWords(); // database içindeki tüm değerleri döndürür
        total = wordList.size();
        Collections.shuffle(wordList); // her çalışmada kelimeleri farklı sırayla getirmek için

        //currentAnswer = String.valueOf(wordList.get(0));
        //Toast.makeText(getApplicationContext(), currentAnswer, Toast.LENGTH_SHORT).show();

        getNextWord();

        inputBox = (EditText) findViewById(R.id.textInput);
        inputBox.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if(!answered){
                        if (inputBox.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(), "Kelimeyi giriniz.", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        else{
                            checkAnswer(v);
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answered){
                    if (inputBox.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Kelimeyi giriniz.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(isBack)
                            flipCard(v);
                        inputBox.setText("");
                        getNextWord();
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, StartScreen.class);
        startActivity(intent);
    }

    private void addButtons() {
        nextButton = (Button) this.findViewById(R.id.buttonNext);
    }

    private void loadTextViews() {
        trueCounter = findViewById(R.id.trueCounter);
        falseCounter = findViewById(R.id.falseCounter);
        frontText = findViewById(R.id.cardFront);
        backText = findViewById(R.id.cardBack);
    }

    private void loadCards() {
        cardFront = findViewById(R.id.cardFront);
        cardBack = findViewById(R.id.cardBack);
    }

    private void loadAnimations() {
        frontAnim = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_1);
        backAnim = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_2);
    }

    private void changeCameraDistance() {
        float scale = getResources().getDisplayMetrics().density * 8000;
        cardFront.setCameraDistance(scale);
        cardBack.setCameraDistance(scale);
    }

    public void flipCard(View view) {
        if (!isBack) {
            frontAnim.setTarget(cardFront);
            backAnim.setTarget(cardBack);
            frontAnim.start();
            backAnim.start();
            isBack = true;
        } else {
            frontAnim.setTarget(cardBack);
            backAnim.setTarget(cardFront);
            frontAnim.start();
            backAnim.start();
            isBack = false;
        }
    }

    private void getNextWord() {
        if (counter < total) {
            currentFC = wordList.get(counter);

            frontText.setText(currentFC.getFlashcardEN());
            backText.setText(currentFC.getFlashcardTR());

            counter++;
            answered = false;
        } else {
            quit();
        }
    }

    private boolean checkAnswer(View v) {
        answered = true;
        String answer = inputBox.getText().toString();
        if (!isBack)
            flipCard(v);
        if (answer.equals(currentFC.getFlashcardTR()))
            trueAnswers++;
        else
            falseAnswers++;
        trueCounter.setText("Doğru: " + trueAnswers);
        falseCounter.setText("Yanlış: " + falseAnswers);
        return answer.equals(currentFC.getFlashcardTR());
    }


    private void quit() {
        Intent intent = new Intent(MainActivity.this, FinishScreen.class);
        startActivity(intent);
    }

}