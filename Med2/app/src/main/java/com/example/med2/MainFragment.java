package com.example.med2;

import android.os.Bundle;
import androidx.fragment.app.Fragment;



import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
//package com.example.med2;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    private float x1,x2;
    static final int MIN_DISTANCE = 250;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    personAdapter adapter;
    DatabaseReference mbase;




    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contact_frag=inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView;
        //recyclerView = getActivity().findViewById(R.id.recycler1);
        recyclerView = contact_frag.findViewById(R.id.recycler1);
        DatabaseReference muser;
        muser = FirebaseDatabase.getInstance().getReference().child("userlist");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<person> options
        = new FirebaseRecyclerOptions.Builder<person>()
        .setQuery(muser, person.class)
        .build();
         adapter = new personAdapter(options);
        recyclerView.setAdapter(adapter);
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
                        getActivity().findViewById(R.id.chat_button).callOnClick();
                    }

                    // Right to left swipe action
                    else
                    {
                        //Toast.makeText(getContext(), "Right to Left swipe [Previous]", Toast.LENGTH_SHORT).show ();

                    }
                    }
                }
                return true;
            }
        });
        return contact_frag;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }


}