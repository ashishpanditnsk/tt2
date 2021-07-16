package com.example.med2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class create_group extends AppCompatActivity {
    private RecyclerView recyclerView;
    personAdapter create_g_adapter;
    DatabaseReference mbase;
    DatabaseReference ref_group_list;
    DatabaseReference ref_group_list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
        String name = sharedPreferences.getString("name","No Name");
        mbase = FirebaseDatabase.getInstance().getReference().child("userlist");
        ref_group_list = FirebaseDatabase.getInstance().getReference().child("grouplist");
        ref_group_list2 = FirebaseDatabase.getInstance().getReference().child("useruserlist");
        recyclerView = findViewById(R.id.recycler3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<person> options
                = new FirebaseRecyclerOptions.Builder<person>()
                .setQuery(mbase, person.class)
                .build();
        ArrayList<String> participant_list=new ArrayList<String>();//Creating arraylist
        create_g_adapter = new personAdapter(options,1,this,participant_list);
        recyclerView.setAdapter(create_g_adapter);
        //TextView n_selected = findViewById(R.id.rate);
        EditText group_name   = findViewById(R.id.create_g_text_box);
        String g_name=group_name.getText().toString();
        Button create_group=(Button) findViewById(R.id.create_group);
/*
        participant_list.add("Man go");//Adding object in arraylist
        participant_list.add("A ppl e");
        participant_list.add("Ba n a na");
        participant_list.add("Grapes");

 */
        create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(create_group.getContext(),g_name,Toast.LENGTH_SHORT).show();
                String g_name=group_name.getText().toString();
                String path=group_name.getText().toString()+"/groupname";

                ref_group_list.child(path).setValue(group_name.getText().toString());
                String str_group="userlist/"+g_name+"/group_flag";
                //FirebaseDatabase.getInstance().getReference().child(str_group).setValue(1);
                String str_group2="userlist/"+g_name+"/lastname";
                //FirebaseDatabase.getInstance().getReference().child(str_group2).setValue("group");

                for (int counter = 0; counter < participant_list.size(); counter++) {
                    String path2=g_name+"/participants";
                    ref_group_list.child(path2).push().child("firstname").setValue(participant_list.get(counter));

                    //String str_useruserto=(String) "useruserlist"+"/"+b.get("Anyname");
                    //useruser_ref_from = FirebaseDatabase.getInstance().getReference().child(str_useruserfrom);
                    //useruser_ref_to = FirebaseDatabase.getInstance().getReference().child(str_useruserto);
                    String path3=participant_list.get(counter)+"/"+g_name;
                    ref_group_list2.child(path3).setValue(new message("You have been added to this Group",name,false,"group",g_name));

                }
finish();

            }
            //public void onClick(View view){myref.setValue(mEdit.getText().toString());}
        });

    }
    @Override protected void onStart()
    {
        super.onStart();
        create_g_adapter.startListening();
    }

    @Override protected void onStop()
    {
        super.onStop();
        create_g_adapter.stopListening();
    }

    @Override
    protected void onPause(){
        super.onPause();}

    @Override
    protected void onResume(){
        super.onResume();}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}