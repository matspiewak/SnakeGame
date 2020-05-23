# SnakeGame
Android game with scoreboard

## Table of contents

* [Techonogies](#Technologies)
* [Features](#Features)
* [Setup](#Setup)
* [Code examples](#Code-examples)
* [Screenshots](#Screenshots)


## Technologies

* Android Studio 3.5.3
* Android SDK Ver. 29.0.5
* Google Firebase Auth 16.0.5
* Google Firebase database 16.0.4
* Google Services

## Features

* Signing in
* Signing Out
* Signing up
* Resetting password
* Playable snake game 
* Saving player's score to scoreboard
* Adding nickname to Scoreboard
* Display of players' scores

## Setup
To setup this project you need to download content of this repository and open it up in Android Studio ver. 3.5.3 or newer.

## Code examples

'''
public class ScoreboardActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        listView = findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(Users.class).toString();

                arrayList.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
'''

## Screenshots
<img src ="/Screenshots/MenuSignedOut.jpg" width="250px"><img src ="/Screenshots/SignIn.jpg" width="250px">
<img src ="/Screenshots/Game.jpg" width="250px"><img src ="/Screenshots/NewGameAlert.jpg" width="250px">
<img src ="/Screenshots/Scoreboard.jpg" width="250px">
