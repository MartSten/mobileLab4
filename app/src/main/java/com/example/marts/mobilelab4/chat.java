package com.example.marts.mobilelab4;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by marts on 11.03.2018.
 */

public class chat extends android.support.v4.app.Fragment {

    Button chatSubmit;
    TextView chatInn;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.chat_layout, container, false);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReferenceFromUrl("https://mobilelab4-eae46.firebaseio.com/");

        chatInn = rootView.findViewById(R.id.chatInput);
        chatSubmit = rootView.findViewById(R.id.chatSubmitBtn);
        chatSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view ) {
                submitMessage(chatInn.getText().toString());
            }
        });

        //return inflater.inflate(R.layout.chat_layout, container, false);
        return rootView;
    }

    /**
     * submits a message to the db
     * @param message - the message to be submitted.
     */
    private void submitMessage(String message){
        // messageToDb mtd = createObject(message);
        // messageToDb mtd = new messageToDb();
        messageToDb mtDB = new messageToDb(message, MainActivity.username);
        myRef.setValue(mtDB);
        myRef.push();
    }
}
