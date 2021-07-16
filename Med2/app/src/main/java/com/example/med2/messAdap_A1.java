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


public class messAdap_A1 extends FirebaseRecyclerAdapter<message, messAdap_A1.messageViewholder> {

    public messAdap_A1(@NonNull FirebaseRecyclerOptions<message> options)
    {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull messageViewholder holder,
                     int position, @NonNull message model)
    {
        Context context = holder.itemView.getContext();


        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), chat.class);
                intent.putExtra("Anyname",model.getTitle());
                if(model.getgroup_flag()!=null){
                    intent.putExtra("group_id",model.getgroup_flag());}
                context.startActivity(intent);


            }
        });


        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.message_text.setText(model.getMessage_text());
        holder.chatter.setText(model.getTitle());
        if(model.getsent_flag()){
            holder.sent_flag.setText("<-");
        }
            else{
                holder.sent_flag.setText("->");}

    }
    @NonNull
    @Override
    public messageViewholder onCreateViewHolder(@NonNull ViewGroup parent,
                                                int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list, parent, false);
        return new messAdap_A1.messageViewholder(view);
    }



    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class messageViewholder extends RecyclerView.ViewHolder {
        TextView message_text;
        TextView chatter;
        TextView sent_flag;
        public messageViewholder(@NonNull View itemView)
        {
            super(itemView);
            message_text = itemView.findViewById(R.id.message);
            chatter=itemView.findViewById(R.id.chatter);
            sent_flag=itemView.findViewById(R.id.chatter_flag);
        }

    }
}


