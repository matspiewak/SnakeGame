package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewGameActivity extends Activity {
    private final static String TAG = NewGameActivity.class.getName();
    Button startNewGame,mainMenu;
    EditText userName;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        final Intent intent = getIntent();

        startNewGame = findViewById(R.id.newGameButtonm);
        mainMenu = findViewById(R.id.menuButton);
        userName = findViewById(R.id.userName);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef =
                database.getReferenceFromUrl("https://flagquiz-8c4ae.firebaseio.com/Users/");

        final String score = intent.getStringExtra("points");

        startNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userName.getText().toString().equals(""))
                    insertData(userName.getText().toString(),score,myRef);
                Intent intent = new Intent(NewGameActivity.this, SnakeActivity.class);
                startActivity(intent);
            }
        });
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userName.getText().toString().equals(""))
                    insertData(userName.getText().toString(),score,myRef);
                Log.i(TAG,userName.getText().toString());
                Intent intent = new Intent(NewGameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    public void insertData(String user, String score, DatabaseReference reference){
        reference.child(user).child("userName").setValue(user);
        reference.child(user).child("userScore").setValue(score);
    }

}
