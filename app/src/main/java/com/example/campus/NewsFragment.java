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

public class NewsFragment extends Fragment {
    DBHelper db;
    SharedPreferences pref;
    List<Newsfeeds> newsfeeds;
    NewsfeedAdapter newsfeedAdapter;
    FloatingActionButton fab;
    @SuppressLint("Range")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news, container,false);
        fab = v.findViewById(R.id.newsfeedFloatingActionButton);

        db = new DBHelper(getContext());
        pref = getContext().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        if (!pref.getString("KEY_USER", null).equals("admin")) {
            fab.hide();
        } else {
            fab.show();
        }

        RecyclerView rv = v.findViewById(R.id.newsfeedRecyclerView);
        newsfeeds = new ArrayList<>();
        Cursor c = db.viewNewsfeed();
        c.moveToLast();
        while(!c.isBeforeFirst()) {
            newsfeeds.add(new Newsfeeds(c.getString(c.getColumnIndex("Heading")), c.getString(c.getColumnIndex("JobTitle")), c.getString(c.getColumnIndex("Subject")), (c.getString(c.getColumnIndex("Date"))), c.getString(c.getColumnIndex("Type")), c.getString(c.getColumnIndex("Year")), c.getString(c.getColumnIndex("Description")), c.getInt(c.getColumnIndex("Id"))));
            c.moveToPrevious();
        }
        newsfeedAdapter = new NewsfeedAdapter(getContext(), newsfeeds);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(newsfeedAdapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), InsertNewsfeed.class));
            }
        });
        return v;



    }
}
