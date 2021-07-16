package com.example.med2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class chat extends AppCompatActivity {
    private RecyclerView recyclerView2;
    //messageAdapter adapter4;
    messageAdapter adapter2;
    messageAdapter adapter3;
    //int group_flag=36;

    DatabaseReference myref;
    DatabaseReference useruser_group;
    DatabaseReference useruser_ref_from;
    DatabaseReference useruser_ref_to;
    DatabaseReference message_ref;
    DatabaseReference message_ref2;
    DatabaseReference message_ref_group;
    DatabaseReference message_ref_gchat;
    String group_id;

    //private ChildEventListener mChildEventLister;
    //private ChildEventListener mChildEventLister2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().setTitle("Techster Texter");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        Bundle b =i.getExtras();
        if(b.get("group_id")!=null) {
            group_id = (String) b.get("group_id");
        }else{group_id="not_group";}
        //FieldValue.serverTimestamp();
        //Bundle b=i.getExtras();
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
        String name = sharedPreferences.getString("name","No Name");
        String sender=(String) b.get("Anyname");

        String fromto="Chat35/"+name+"/"+b.get("Anyname");
        String tofrom="Chat35/"+b.get("Anyname")+"/"+name;
        String str_group="userlist/"+b.get("Anyname")+"/group_flag";

        //Toast.makeText(chat.this,group_id,Toast.LENGTH_SHORT).show();

        message_ref_group=FirebaseDatabase.getInstance().getReference();
        //String group_id_new="";
        //Toast.makeText(chat.this,group_id_new,Toast.LENGTH_SHORT).show();
        message_ref_group.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(str_group).exists()){
                    //Toast.makeText(chat.this,str_group,Toast.LENGTH_SHORT).show();
                    Toast.makeText(chat.this,"This is a group",Toast.LENGTH_SHORT).show();
                    int group_flag=111;
                    //Toast.makeText(chat.this,String.valueOf(group_flag),Toast.LENGTH_SHORT).show();

                }
                else {
                    //Toast.makeText(chat.this,str_group,Toast.LENGTH_SHORT).show();
                    Toast.makeText(chat.this,"This NOT a group",Toast.LENGTH_SHORT).show();
                    int group_flag=999;
                   // Toast.makeText(chat.this,String.valueOf(group_flag),Toast.LENGTH_SHORT).show();

                }
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });

        if(group_id.equals("group")){
            Toast.makeText(chat.this,"Group flag 1",Toast.LENGTH_SHORT).show();
            String group_chat_path="Chat35/"+b.get("Anyname");
            message_ref_gchat = FirebaseDatabase.getInstance().getReference().child(group_chat_path);
            recyclerView2 = findViewById(R.id.recycler2);
            recyclerView2.setLayoutManager(new LinearLayoutManager(this));
            FirebaseRecyclerOptions<message> options
                    = new FirebaseRecyclerOptions.Builder<message>()
                    .setQuery(message_ref_gchat, message.class)
                    .build();
            adapter2 = new messageAdapter(options);
            recyclerView2.setAdapter(adapter2);
            //String j =(String) "Chat35"+"/"+name+"/"+b.get("Anyname");
            myref = message_ref_gchat;

        }
        else {
            Toast.makeText(chat.this,String.valueOf(group_id),Toast.LENGTH_SHORT).show();
            message_ref = FirebaseDatabase.getInstance().getReference().child(fromto);
            message_ref2= FirebaseDatabase.getInstance().getReference().child(tofrom);
            recyclerView2 = findViewById(R.id.recycler2);
            recyclerView2.setLayoutManager(new LinearLayoutManager(this));
            FirebaseRecyclerOptions<message> options
                    = new FirebaseRecyclerOptions.Builder<message>()
                    .setQuery(message_ref, message.class)
                    .build();

            FirebaseRecyclerOptions<message> options3
                    = new FirebaseRecyclerOptions.Builder<message>()
                    .setQuery(message_ref2, message.class)
                    .build();

            adapter2 = new messageAdapter(options);
            adapter3 = new messageAdapter(options3);

            ConcatAdapter concatAdapter = new ConcatAdapter(adapter2, adapter3);
            recyclerView2.setAdapter(concatAdapter);
            String j =(String) "Chat35"+"/"+name+"/"+b.get("Anyname");
            myref = FirebaseDatabase.getInstance().getReference().child(j);
            String str_useruserfrom=(String) "useruserlist"+"/"+name;
            String str_useruserto=(String) "useruserlist"+"/"+b.get("Anyname");
            useruser_ref_from = FirebaseDatabase.getInstance().getReference().child(str_useruserfrom);
            useruser_ref_to = FirebaseDatabase.getInstance().getReference().child(str_useruserto);

            //useruser_group = FirebaseDatabase.getInstance().getReference().child(group_path);




        }
        View mButton = findViewById(R.id.button2);
        EditText mEdit   = findViewById(R.id.chat_text_box);
        TextView tv = (TextView) mButton.getRootView().findViewById(R.id.chat_user);
        tv.setText((String) b.get("Anyname"));

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //myref.push().child("message_text").setValue(mEdit.getText().toString());

                if(group_id.equals("group")){
                    myref.push().setValue(new message(mEdit.getText().toString(),name));
                    Toast.makeText(chat.this,"We hare",Toast.LENGTH_SHORT).show();
                    //getdata();
                    String group_path="grouplist/"+b.get("Anyname")+"/participants";
                    useruser_group = FirebaseDatabase.getInstance().getReference().child(group_path);
                    ValueEventListener postListener;
                    ChildEventListener postListener2;

                     postListener = new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Get Post object and use the values to update the UI


                            String post = dataSnapshot.getValue().toString();
                            Toast.makeText(chat.this,post,Toast.LENGTH_SHORT).show();
                            //String path3="useruserlist/"+post+"/"+"sender";
                            //FirebaseDatabase.getInstance().getReference().child(path3).setValue(new message(mEdit.getText().toString(),sender,false,"group"));
                            // ..
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }

                    };
                    useruser_group.addValueEventListener(postListener);
                    postListener2=new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                            person post = snapshot.getValue(person.class);
                            Toast.makeText(chat.this,post.getFirstname(),Toast.LENGTH_SHORT).show();
                            String path3="useruserlist/"+post.getFirstname()+"/"+sender;
                            FirebaseDatabase.getInstance().getReference().child(path3).setValue(new message(mEdit.getText().toString(),name,false,"group",sender));
                            // ..
                        }

                        @Override
                        public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    };
                    useruser_group.addChildEventListener(postListener2);






                    /*
                    useruser_group.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            Toast.makeText(chat.this,"We hare 3",Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                    */


                }
                else{
                    myref.push().setValue(new message(mEdit.getText().toString()));
                    useruser_ref_from.child((String) b.get("Anyname")).setValue(new message(mEdit.getText().toString(),"You",true,"notgroup",sender));
                    useruser_ref_to.child(name).setValue(new message(mEdit.getText().toString(),name,false,"notgroup",name));
                }
            }
            //public void onClick(View view){myref.setValue(mEdit.getText().toString());}
        });


    }
    @Override protected void onStart()
    {
        super.onStart();
        adapter2.startListening();
        if(group_id.equals("group"))
        {

        }
        else{adapter3.startListening();}


    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        adapter2.stopListening();
        if(group_id.equals("group")){}
        else{
            adapter3.stopListening();
        }

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void getdata(){
        Toast.makeText(chat.this,"We hare",Toast.LENGTH_SHORT).show();
    }
}


