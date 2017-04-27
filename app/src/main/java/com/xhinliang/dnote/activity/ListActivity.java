package com.xhinliang.dnote.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.xhinliang.dnote.R;
import com.xhinliang.dnote.adpter.NoteAdapter;
import com.xhinliang.dnote.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Class ListActivity
 *
 * @author XhinLiang
 */
public class ListActivity extends AppCompatActivity {

    List<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.add_note_success, Snackbar.LENGTH_LONG)
                        .setAction(R.string.revert, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(ListActivity.this, "hehe", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        })
                        .show();
            }
        });

        for (int i = 0; i < 30; i++) {
            notes.add(new Note("title" + i, "content" + i));
        }
        NoteAdapter adapter = new NoteAdapter(this, notes);

        ListView listView = (ListView) findViewById(R.id.listview_content);
        listView.setNestedScrollingEnabled(true);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, notes.get(position).getTitle() + " click", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }

}
