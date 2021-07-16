package com.example.med2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.io.Serializable;
import java.util.ArrayList;

//import com.firebase.ui.database.FirebaseRecyclerAdapter;

// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View
public class personAdapter extends FirebaseRecyclerAdapter<person, personAdapter.personsViewholder> {
    private int act_id;
    private Context mContext;
    private ArrayList<String> participant_list;

    public personAdapter(@NonNull FirebaseRecyclerOptions<person> options) {
        super(options);
    }

    public personAdapter(@NonNull FirebaseRecyclerOptions<person> options, int act_id, Context context,ArrayList<String> participant_list ) {
        super(options);
        this.act_id = act_id;
        this.mContext = context;
        this.participant_list=participant_list;

    }

    public int getact_id() {
        return act_id;
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    public static int num_selected = 0;

    @Override
    protected void onBindViewHolder(@NonNull personsViewholder holder, int position, @NonNull person model) {
        num_selected = 0;

        if (getact_id() == 1) {
            //Toast.makeText(holder.itemView.getContext(), "on binder wrong", Toast.LENGTH_SHORT).show();
        /*
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // yourMethod();

         */
            Context context = holder.itemView.getContext();
            //Drawable background = holder.itemView.getBackground();
            //final int base_color = ((ColorDrawable) background).getColor();
            holder.itemView.setBackgroundColor(Color.WHITE);

            //TextView n_selected = mContext.findViewById(R.id.rate);
            TextView n_selected = ((create_group) mContext).findViewById(R.id.create_g_n);
            n_selected.setText(String.valueOf(num_selected) + " participants Selected");


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int color = Color.TRANSPARENT;
                    Drawable background = holder.itemView.getBackground();
                    color = ((ColorDrawable) background).getColor();

                    holder.itemView.setTag(position);
                    //holder.itemView.getTag();
                   
                    if (color == Color.WHITE) {
                        holder.itemView.setBackgroundColor(0x00000000);
                        num_selected = num_selected + 1;
                        n_selected.setText(String.valueOf(num_selected) + " participants Selected");
                        participant_list.add(model.getFirstname());
                    } else {
                        holder.itemView.setBackgroundColor(Color.WHITE);
                        num_selected = num_selected - 1;
                        n_selected.setText(String.valueOf(num_selected) + " participants Selected");
                        participant_list.remove(model.getFirstname());
                    }


                }
            });

            holder.firstname.setText(model.getFirstname());
                /*
            }
        }, 5000);   //5 seconds
*/

        } else {
            //Toast.makeText(holder.itemView.getContext(), "on binder right", Toast.LENGTH_SHORT).show();
            Context context = holder.itemView.getContext();
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(view.getContext(), chat.class);
                    intent.putExtra("Anyname", model.getFirstname());
                    intent.putExtra("group_id", model.getLastname());
                    context.startActivity(intent);


                }
            });
            holder.firstname.setText(model.getFirstname());
            /*
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // yourMethod();



                    //holder.lastname.setText(model.getLastname());
                    //holder.age.setText(model.getAge());

                }
            }, 5000);   //5 seconds
            */
        }

    }

    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public personsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (act_id == 1) {
            View view
                    = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.create_g, parent, false);
            return new personAdapter.personsViewholder(view);
        } else {
            View view
                    = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.create_g, parent, false);
            return new personAdapter.personsViewholder(view);
        }

    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class personsViewholder extends RecyclerView.ViewHolder {
        TextView firstname, lastname, age;

        public personsViewholder(@NonNull View itemView) {
            super(itemView);
            //firstname = itemView.findViewById(R.id.textView1);

            if (act_id == 1) {
                firstname = itemView.findViewById(R.id.textView1);
            } else {
                firstname = itemView.findViewById(R.id.textView1);
                //lastname = itemView.findViewById(R.id.lastname);
                //age = itemView.findViewById(R.id.age);}

            }
        }
    }


}