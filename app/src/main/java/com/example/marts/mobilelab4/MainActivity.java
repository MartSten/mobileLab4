package com.example.marts.mobilelab4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    /**
     * Used to check if this is the first time the app is opened.
     */
    private boolean firstTime = true;

    /**
     * The resultcode used for the login activity.
     */
    private int myReslutCode = 123;

    /**
     * the user's username.
     */
    public static String username;

    //TAG
    private String TAG = "mainActivity";

    //Firebase stuff
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;

    Button chatSubmit;
    TextView chatInn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkIfFirstTime();
        username = getUsername();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        mAuth = FirebaseAuth.getInstance();

        /*database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");*/

        //Authenticates the user
        signInAnonymously();

       /* chatInn = findViewById(R.id.chatInput);
        chatSubmit = findViewById(R.id.chatSubmitBtn);
         chatSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view ) {
                //submitMessage(chatInn.getText().toString());
            }
        }); */

    }

    /**
     * submits a message to the db
     * @param message - the message to be submitted.
     */
   /* private void submitMessage(String message){
      // messageToDb mtd = createObject(message);
      // messageToDb mtd = new messageToDb();
        messageToDb mtDB = new messageToDb(message, username);
        myRef.setValue(mtDB);
    }*/

    /**
     * gets the user's username from the preferences.
     * @return
     */
    private String getUsername(){
        String uname;
        SharedPreferences unamePref = this.getSharedPreferences("username", Context.MODE_PRIVATE);
        uname = unamePref.getString("username", "BOB");
        return uname;
    }

    /**
     * Checks if the user has used the app before.
     */
    private void checkIfFirstTime(){
        if(firstTime){
            //SharedPreferences fTimePref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences fTimePref = this.getSharedPreferences("firstTime", Context.MODE_PRIVATE);
            firstTime = fTimePref.getBoolean("firstTime", true);    //true = default value
            if(firstTime){
                Intent i = new Intent(this, login.class);
                startActivityForResult(i, myReslutCode);
            }
        }
    }

    /**
     * Signs the user inn as an anon.
     * Copied from Firebase documentation.
     */
    private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Checks if the user is logged in
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(myReslutCode == requestCode){
            if(resultCode == RESULT_OK){
                //FIRST TIME
                SharedPreferences fTimePref = this.getSharedPreferences("firstTime", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = fTimePref.edit();
                editor.putBoolean("firstTime", false);
                editor.apply();

                //USERNAME
                String uNameFromActivity = data.getStringExtra("usernameFromIntent");
                SharedPreferences saveUname = this.getSharedPreferences("username", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = saveUname.edit();
                editor2.putString("username", uNameFromActivity);
                editor2.apply();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return ChatFragment.newInstance(position + 1);
            switch (position){
                case 0:
                    chat chatFragment = new chat();
                    return chatFragment;
                case 1:
                    friends firendsFragment = new friends();
                    return firendsFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

       /* @Override
        public CharSequence getPageTitle(int position) {
            //Returns the page title
            return mFragmentTitleList.get(position);
        }
    */
    }
}
