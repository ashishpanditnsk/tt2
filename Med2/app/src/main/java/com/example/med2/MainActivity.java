package com.example.med2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

Button firstFragment,secondFragment;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    FirebaseAuth mfirebaseauth;
    FirebaseAuth.AuthStateListener mAuthSL;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Techster Texter1");

        final int RC_SIGN_IN=1;

        DatabaseReference museruser;
        DatabaseReference muser;
        muser = FirebaseDatabase.getInstance().getReference().child("userlist");
        museruser = FirebaseDatabase.getInstance().getReference().child("useruserlist");

        firstFragment = (Button) findViewById(R.id.chat_button);
        secondFragment = (Button) findViewById(R.id.button);

        firstFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// load First Fragment
                //loadFragment(new Fragment());
                load_chat_list_frag(new chat_list_fragment());
                //Toast.makeText(MainActivity.this,"Item 1 seelected",Toast.LENGTH_SHORT).show();

            }
        });

        secondFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFrag(new MainFragment());

            }
        });
        firstFragment.callOnClick();
        mfirebaseauth=FirebaseAuth.getInstance();
        Toast.makeText(this,"Exist",Toast.LENGTH_SHORT).show();

        mAuthSL=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull @NotNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user != null){
                    String name = user.getDisplayName();

                    SharedPreferences sharedPref = getSharedPreferences("MyData",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("name",name);
                    //editor.putString("password",password.getText().toString());
                    editor.commit();


                    // Test for the existence of certain keys within a DataSnapshot
                    //Firebase userRef= new Firebase(USERS_LOCATION);
                    muser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.child(name).exists()) {
                                //Toast.makeText(MainActivity.this,"Exist",Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(MainActivity.this,"Not Exist",Toast.LENGTH_SHORT).show();
                                //muser.child("contacts").child(name).setValue("NA");
                                //myref.push().setValue(mEdit.getText().toString());
                                person user = new person(name,"NA","NA");
                                muser.child(name).setValue(user);
                                museruser.child(name).setValue(user);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }

                    });
                }

                //Toast.makeText(MainActivity.this,"Logged In",Toast.LENGTH_SHORT).show();

                else{
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);

                }


            }
        };



    }
    private void load_chat_list_frag(chat_list_fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
       FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.linlay, fragment);
        fragmentTransaction.commit(); // save the changes
    }
    private void loadFrag(MainFragment fragment) {
        Toast.makeText(MainActivity.this,"loadfragcalled",Toast.LENGTH_SHORT).show();
        FragmentManager fm = getSupportFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.linlay, fragment);
        fragmentTransaction.commit(); // save the changes
    }
    //@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=this.getMenuInflater();
        inflater.inflate(R.menu.three_dot_f,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(this,"Item 1 seelected",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, create_group.class);
                //EditText editText = (EditText) findViewById(R.id.editText);
                //String message = editText.getText().toString();
                //intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
                return true;
            case R.id.item2:
                Toast.makeText(this,"Item 2 seelected",Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, web.class);
                startActivity(intent2);
                return true;
            case R.id.item3:
                Toast.makeText(this,"Item 3 seelected",Toast.LENGTH_SHORT).show();
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    /*
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    // Left to Right swipe action
                    if (x2 > x1)
                    {
                        Toast.makeText(this, "Left to Right swipe [Next]", Toast.LENGTH_SHORT).show ();
                        View myView = findViewById(R.id.chat_button);
                        myView.performClick();
                    }

                    // Right to left swipe action
                    else
                    {
                        Toast.makeText(this, "Right to Left swipe [Previous]", Toast.LENGTH_SHORT).show ();
                    }

                }
                else
                {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }
    */
    @Override
    public void onPause(){
        super.onPause();
        mfirebaseauth.removeAuthStateListener(mAuthSL);}

    @Override
    public void onResume(){
        super.onResume();
        mfirebaseauth.addAuthStateListener(mAuthSL);}
}
