package com.example.marts.mobilelab4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FriendMessages extends AppCompatActivity {

    TextView title;
    ListView listView;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> messages;

    //Firebase Database reference
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_messages);

        messages = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.chat_item, messages);

        title = findViewById(R.id.friendName);
        listView = findViewById(R.id.listView2);
        listView.setAdapter(adapter);

        //Firebase Database
        myRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onStart() {
        super.onStart();
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    messageToDb message = ds.getValue(messageToDb.class);
                    //Log.d("TEST", "message = " + message.getMessage());
                    assert message != null;
                    messages.add(message.getUser() + ": " + message.getMessage());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //TODO
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //TODO
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //TODO
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO
            }
        });

    }
}
