package com.example.johanneshummel.checklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by johanneshummel on 31.05.17.
 */

public class CloseUpExercise extends Activity {
    private TextView exerciseTitle;
    private TextView exerciseDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closeup_exercise);

        exerciseTitle = (TextView)findViewById(R.id.ExerciseTitle);
        exerciseDescription = (TextView)findViewById(R.id.ExerciseDescription);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        Element element;
        if(b!=null) {
            element = (Element) b.get("Element");
            exerciseTitle.setText(element.getTitle());
            exerciseDescription.setText(element.getDescription());
        }
    }
}

