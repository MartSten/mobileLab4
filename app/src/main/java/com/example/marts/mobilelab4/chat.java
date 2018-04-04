package com.example.marts.mobilelab4;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by marts on 11.03.2018.
 * This class handles the chat part of the assignment
 */

public class chat extends android.support.v4.app.Fragment {

    private static final String TAG = "PostDetailActivity";

    //The user's username
    private String username;

    //Chat submit button
    Button chatSubmit;
    //Chat input-field
    TextView chatInn;
    //Chat listView
    ListView listView;

    //Firebase Database
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseFirestore fireDb;

    //Array that holds received messages
    private ArrayList<String> messageItems;
    private ArrayAdapter<String> arrayAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.chat_layout, container, false);

        //Chat input-field
        chatInn = rootView.findViewById(R.id.chatInput);
        //Chat listView
        listView = rootView.findViewById(R.id.listView);

        messageItems = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.chat_item, messageItems);
        listView.setAdapter(arrayAdapter);

        //Firebase Database
        myRef = FirebaseDatabase.getInstance().getReference();
        fireDb = FirebaseFirestore.getInstance();

        //Gets the username from the MainActivity
        Bundle bundle = getArguments();
        username = bundle.getString("username");
        Log.d("TEST", "Got this username in the fragment: " + username);


        //Chat submit button
        chatSubmit = rootView.findViewById(R.id.chatSubmitBtn);
        chatSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view ) {
                //Log.d("TEST", "subBtn was pressed");
               if(!submitMessage(chatInn.getText().toString())){
                   Toast.makeText(getActivity(), "ERROR - faild to submit message", Toast.LENGTH_SHORT).show();
               }else {
                   chatInn.setText(null);   //Clears the input-field
                   Toast.makeText(getActivity(), "Message sent", Toast.LENGTH_SHORT).show();
               }
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("TEST", "Retrieving posted data");

                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    //messageToDb temp = ds.getValue(messageToDb.class);

                    for (DataSnapshot dsChild: ds.getChildren()) {
                        messageToDb messageFromDb = dsChild.getValue(messageToDb.class);
                        messageItems.add(messageFromDb.getUser() + ": " + messageFromDb.getMessage());
                        arrayAdapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                //Toast.makeText(PostDetailActivity.this, "Failed to load post.",
                        //Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        myRef.addValueEventListener(postListener);
    }

    /**
     * submits a message to the db
     * @param message - the message to be submitted.
     */
    private boolean submitMessage(String message){
        //Checks if the user have entered a message
        String msg = chatInn.getText().toString();
        if(msg == "" || msg == null){
            Log.d("TEST", "sub failed - empty message");
            return false;
        }else {
            Log.d("TEST", "message was not empty");
            messageToDb messages = new messageToDb(msg, username);
            myRef.push().child("messages").setValue(messages);
            /*
            Map<String, Object>cloudMesage = new HashMap<>();
            cloudMesage.put("m", msg);
            cloudMesage.put("u", username);
            cloudMesage.put("d", "27-03-2018 16:00:45");
            fireDb.collection("messages").add(cloudMesage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error adding document", e);
                }
            });*/
            return true;
        }
    }

}
