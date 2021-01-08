package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class FinishScreen extends AppCompatActivity {
    private MainActivity obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_screen);

        TextView trueTV = findViewById(R.id.trueCounter_fs);
        TextView falseTV = findViewById(R.id.falseCounter_fs);
        TextView congTV = findViewById(R.id.wellDone);

        if(obj.trueAnswers <= 4)
            congTV.setText("Daha çok çalışmalısın.");
        else if(obj.trueAnswers <= 9)
            congTV.setText("Ha gayret.");
        else
            congTV.setText("Aferin, başarılı.");

        trueTV.setText("Doğru: " + obj.trueAnswers);
        falseTV.setText("Yanlış: " + obj.falseAnswers);

        Button retryButton = findViewById(R.id.retryButton);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry();
                finish();
            }
        });


        Button quitButton = findViewById(R.id.quitButton_fs);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quit();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FinishScreen.this, StartScreen.class);
        startActivity(intent);
    }

    private void retry(){
        Intent intent = new Intent(FinishScreen.this, MainActivity.class);
        startActivity(intent);
    }

    private void quit(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}