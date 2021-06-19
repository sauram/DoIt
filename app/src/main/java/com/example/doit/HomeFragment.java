package com.example.doit;

import android.app.ActionBar;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements newTask.newTaskDialogListener {
    ArrayList<Pair<Integer, Pair<String, String> >> CurrentNotes = new ArrayList<>();
    private FloatingActionButton floatingActionButton;
    private TemplateViewAdapter templateViewAdapter;
    private ViewSwitcher viewSwitcher;
    DatabaseClass databaseClass;
    private static SQLiteDatabase rdb;
    private  static SQLiteDatabase wdb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseClass= new DatabaseClass(getContext());
        rdb = databaseClass.getReadableDatabase();
        wdb = databaseClass.getWritableDatabase();
        readDB();
    }

    private void readDB() {
        String[] Projection = {
                DatabaseClass.DATA_COLUMN_ID,
                DatabaseClass.DATA_COLUMN_TITLE,
                DatabaseClass.DATA_COLUMN_DESCRIPTION,
        };
        Cursor cursor = rdb.query(DatabaseClass.DATA_TABLE_NAME, null, null, null,null,null, null);
        while (cursor.moveToNext()){
            Integer post_id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseClass.DATA_COLUMN_ID));
            String post_title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseClass.DATA_COLUMN_TITLE));
            String post_desc = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseClass.DATA_COLUMN_DESCRIPTION));
            Pair<String ,String> pt = new Pair<String, String>(post_title, post_desc);
            CurrentNotes.add(0, new Pair<Integer, Pair<String, String>>(post_id,pt));
        }
        cursor.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.home_fragment_recycle_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        templateViewAdapter = new TemplateViewAdapter(CurrentNotes);
        recyclerView.setAdapter(templateViewAdapter);
        floatingActionButton = view.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewTask();
            }
        });

        return view;
    }
    public void addNewTask(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        newTask nt = newTask.newInstance("title");
        nt.setTargetFragment(HomeFragment.this, 300);
        nt.show(fragmentManager,"fragment_new_task");
    }

    @Override
    public void ondone(String title, String description) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseClass.DATA_COLUMN_TITLE, title);
        contentValues.put(DatabaseClass.DATA_COLUMN_DESCRIPTION, description);
        long rowID = wdb.insert(DatabaseClass.DATA_TABLE_NAME, null,contentValues);
        Pair<String, String> pt= new Pair<String, String>(title, description);
        CurrentNotes.add(0,new Pair<Integer, Pair<String, String>>((int) rowID,pt));
        templateViewAdapter.notifyItemInserted(0);
        Toast.makeText(getContext(), "Added",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        databaseClass.close();
        super.onDestroy();
    }
}