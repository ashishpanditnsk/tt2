package com.example.med2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.io.Serializable;

//import com.firebase.ui.database.FirebaseRecyclerAdapter;

// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View
public class messageAdapter extends FirebaseRecyclerAdapter<message, messageAdapter.messageViewholder> {

    public messageAdapter(@NonNull FirebaseRecyclerOptions<message> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull messageViewholder holder,
                     int position, @NonNull message model)
    {
        Context context = holder.itemView.getContext();
        /*
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), chat.class);
                intent.putExtra("Anyname",model.getMessage_text() );
                //intent.putExtra("group_id",model.getMessage_text() );
                //intent.putExtra("Group_Flag",model.getMessage_text() );
                context.startActivity(intent);
            }
        });
        */

        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.message_text.setText(model.getMessage_text());
        if(model.getsender()!=null){
            //holder.setsender(model.getMessage_text());
            holder.sender.setText(model.getsender());
        }


    }

    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public messageViewholder onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message, parent, false);
                //View view2  = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent,  false);
                //return new messageAdapter.messageViewholder(view1);

        return new messageAdapter.messageViewholder(view);
    }


    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class messageViewholder extends RecyclerView.ViewHolder {
        TextView message_text;
        TextView sender;
        //TextView sent_flag;
        public messageViewholder(@NonNull View itemView)
        {
            super(itemView);
            message_text = itemView.findViewById(R.id.message_text);
            sender=itemView.findViewById(R.id.sender);
            //sent_flag=itemView.findViewById(R.id.chatter_flag);
        }

    }
}


