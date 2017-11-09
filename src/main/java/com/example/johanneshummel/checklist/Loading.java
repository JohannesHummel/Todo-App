package com.example.johanneshummel.checklist;


        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;

        import java.io.FileInputStream;
        import java.io.IOException;
        import java.io.ObjectInput;
        import java.io.ObjectInputStream;
        import java.util.ArrayList;


public class Loading extends AppCompatActivity {

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Element> items = new ArrayList<>();

        try {
            FileInputStream fileInputStream = openFileInput("saveFile");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            items = (ArrayList<Element>)objectInputStream.readObject();

        } catch(IOException | ClassNotFoundException e){
            Log.d("Error",e.getMessage());
        }

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("exerciseData", items);
        startActivity(intent);
    }
}
