package com.xhinliang.dnote.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xhinliang.dnote.R;
import com.xhinliang.dnote.global.NoteFactory;
import com.xhinliang.dnote.model.Note;


public class DetailActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private TextView tvContent;
    private EditText etContent;
    private EditText etTitle;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private NoteFactory noteFactory = NoteFactory.getInstance();
    private Note note;
    private boolean editMode = true;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        tvContent = (TextView) findViewById(R.id.tv_content);
        etContent = (EditText) findViewById(R.id.et_content);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        etTitle = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        etTitle.setLayoutParams(lp);

        dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.input_new_title)
                .setView(etTitle)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        note.setTitle(etTitle.getText().toString());
                        collapsingToolbarLayout.setTitle(note.getTitle());
                    }
                })
                .create();
    }

    private void initData() {
        Intent intent = getIntent();
        int noteIndex = intent.getIntExtra(ListActivity.KEY_EXTRA_NOTE, -1);
        if (noteIndex == -1) {
            noteFactory.addNote(new Note(getString(R.string.edit_title), getString(R.string.edit_content)));
            noteIndex = noteFactory.getNotes().size() - 1;
        }
        note = noteFactory.getNotes().get(noteIndex);
        setTitle(note.getTitle());
        tvContent.setText(note.getContent());
        etContent.setText(note.getContent());
    }

    private void initEvent() {
        setEditMode();

        collapsingToolbarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTitle.setText(note.getTitle());
                dialog.show();
            }
        });
    }

    private void setEditMode() {
        editMode = !editMode;
        etContent.setVisibility(editMode ? View.VISIBLE : View.GONE);
        tvContent.setVisibility(editMode ? View.GONE : View.VISIBLE);
        fab.setImageResource(editMode ? android.R.drawable.ic_menu_save : android.R.drawable.ic_menu_edit);
        if (editMode) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    note.setTitle(getTitle().toString());
                    note.setContent(etContent.getText().toString());
                    tvContent.setText(note.getContent());
                    Snackbar.make(v, R.string.save_success, Snackbar.LENGTH_LONG).show();
                    setEditMode();
                }
            });
            return;
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditMode();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
