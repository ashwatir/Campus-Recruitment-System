package com.example.campus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    DBHelper db;
    SharedPreferences pref;
    List<Stories> story;
    StoryAdapter storyAdapter;
    FloatingActionButton fab2;
    @SuppressLint("Range")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_profile,container,false);
        View v = inflater.inflate(R.layout.fragment_profile, container,false);
        fab2 = v.findViewById(R.id.storyFloatingActionButton);

        db = new DBHelper(getContext());
        pref = getContext().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        if (pref.getString("KEY_USER", null).equals("admin")) {
            fab2.hide();
        } else {
            fab2.show();
        }
        RecyclerView rv = v.findViewById(R.id.storyRecyclerView);
        story = new ArrayList<>();
        Cursor c = db.viewStory();
        c.moveToLast();
        while(!c.isBeforeFirst()) {
            story.add(new Stories(c.getString(c.getColumnIndex("Name")), c.getString(c.getColumnIndex("Company")), c.getString(c.getColumnIndex("Pack")), (c.getString(c.getColumnIndex("Story"))), c.getString(c.getColumnIndex("YOP")), c.getInt(c.getColumnIndex("Id"))));
            c.moveToPrevious();
        }

        storyAdapter = new StoryAdapter(getContext(), story);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(storyAdapter);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), InsertStory.class));
            }
        });
        return v;
    }
}
