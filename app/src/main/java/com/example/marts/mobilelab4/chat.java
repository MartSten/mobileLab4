package com.example.marts.mobilelab4;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by marts on 11.03.2018.
 */

public class chat extends android.support.v4.app.Fragment {

    private static final String TAG = "PostDetailActivity";

    Context context;

    private String username;

    Button chatSubmit;
    TextView chatInn;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseFirestore fireDb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.chat_layout, container, false);

       // database = FirebaseDatabase.getInstance();
       // myRef = database.getReferenceFromUrl("https://mobilelab4-eae46.firebaseio.com/");

        myRef = FirebaseDatabase.getInstance().getReference();
        fireDb = FirebaseFirestore.getInstance();

        //Gets the username from the MainActivity
        Bundle bundle = getArguments();
        username = bundle.getString("username");
        Log.d("TEST", "Got this username in the fragment: " + username);


        chatInn = rootView.findViewById(R.id.chatInput);
        chatSubmit = rootView.findViewById(R.id.chatSubmitBtn);
        chatSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view ) {
                Log.d("TEST", "subBtn was pressed");
                submitMessage(chatInn.getText().toString());
            }
        });

        //return inflater.inflate(R.layout.chat_layout, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
               // Post post = dataSnapshot.getValue(Post.class);
                // [START_EXCLUDE]
                //mBodyView.setText(post.body);
                // [END_EXCLUDE]
                Log.d("TEST", "Data was posted");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                //Toast.makeText(PostDetailActivity.this, "Failed to load post.",
                        //Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
    }

    /**
     * submits a message to the db
     * @param message - the message to be submitted.
     */
    private boolean submitMessage(String message){
        // messageToDb mtd = createObject(message);
        // messageToDb mtd = new messageToDb();
       //messageToDb mtDB = new messageToDb(message, MainActivity.username);
        //myRef.setValue(mtDB);
        //myRef.push();

        //Checks if the user have entered a message
        String msg = chatInn.getText().toString();
        if(msg == "" || msg == null){
            Log.d("TEST", "sub failed - empty message");
            return false;
        }else {
            Log.d("TEST", "message was not empty");
            messageToDb messages = new messageToDb(msg, username);
            myRef.push().child("messages").setValue(messages);

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
            });
            return true;
        }
    }

}
