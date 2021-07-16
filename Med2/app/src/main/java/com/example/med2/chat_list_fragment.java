package com.example.med2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link chat_list_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class chat_list_fragment extends Fragment {
    private float x1,x2;
    static final int MIN_DISTANCE = 250;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public chat_list_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static chat_list_fragment newInstance(String param1, String param2) {
        chat_list_fragment fragment = new chat_list_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    messAdap_A1 adapter;
    DatabaseReference museruser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name","No Name");
        String user_node="useruserlist"+"/"+name;
        museruser = FirebaseDatabase.getInstance().getReference().child(user_node);


    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_main, container, false);
        //View contact_frag=inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView;
        //recyclerView = getActivity().findViewById(R.id.recycler1);
        recyclerView = view.findViewById(R.id.recycler1);


        // To display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<message> options
                = new FirebaseRecyclerOptions.Builder<message>()
                .setQuery(museruser, message.class)
                .build();
        adapter = new messAdap_A1(options);
        recyclerView.setAdapter(adapter);
        //View viewbutton=getActivity().findViewById(R.id.button2);

        ///////////
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    x1 = event.getX();
                    //Toast.makeText(getContext(),"hellooooooooooo",Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity(),"Item 1 seelected",Toast.LENGTH_SHORT).show();
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    x2= event.getX();
                    float deltaX = x2 - x1;
                    if(Math.abs(deltaX) > MIN_DISTANCE){if (x2 > x1)
                    {
                        //Toast.makeText(getActivity(), "Left to Right swipe [Next]", Toast.LENGTH_SHORT).show ();
                        //View myView = findViewById(R.id.chat_button);
                        //myView.performClick();
                    }

                    // Right to left swipe action
                    else
                    {
                        //Toast.makeText(getContext(), "Right to Left swipe [Previous]", Toast.LENGTH_SHORT).show ();
                        getActivity().findViewById(R.id.button).callOnClick();
                    }
                    }
                }
                return true;
            }
        });
        return view;
    }
    @Override
    public void onStart()
    {
        super.onStart();
        adapter.startListening();



    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override
    public void onStop()
    {
        super.onStop();
        adapter.stopListening();


    }
    //@Override


}