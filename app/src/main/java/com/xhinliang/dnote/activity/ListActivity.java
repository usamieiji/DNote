package com.xhinliang.dnote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.avos.avoscloud.AVUser;
import com.xhinliang.dnote.R;
import com.xhinliang.dnote.adpter.NoteAdapter;
import com.xhinliang.dnote.global.NoteFactory;
import com.xhinliang.dnote.model.Note;

/**
 * Class ListActivity
 *
 * @author XhinLiang
 */
public class ListActivity extends AppCompatActivity {
    public static final String KEY_EXTRA_NOTE = "note";
    public static final int REQUEST_FOR_EDIT_NOTE = 100;
    private static final int REQUEST_FOR_CREATE_NOTE = 101;

    private NoteAdapter adapter;
    private ListView listView;
    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefreshLayout;

    private NoteFactory noteFactory = NoteFactory.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();
        initData();
        initListView();
        initEvents();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.listview_content);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_refresh);
        listView.setEmptyView(findViewById(R.id.empty_view));
        setSupportActionBar(toolbar);
    }

    private void initData() {
        adapter = new NoteAdapter(this, noteFactory.getNotes());
    }

    private void initListView() {
        listView.setNestedScrollingEnabled(true);
        listView.setAdapter(adapter);
    }

    private void initEvents() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                intent.putExtra(KEY_EXTRA_NOTE, position);
                startActivityForResult(intent, REQUEST_FOR_EDIT_NOTE);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                intent.putExtra(KEY_EXTRA_NOTE, -1);
                startActivityForResult(intent, REQUEST_FOR_CREATE_NOTE);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NoteFactory.getInstance().getNotes().add(0, new Note("New Title", "New Content"));
                        adapter.notifyDataSetChanged();
                        // 刷新完毕，关闭下拉刷新的组件
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FOR_EDIT_NOTE) {
            Snackbar.make(fab, R.string.edit_note_success, Snackbar.LENGTH_LONG).show();
            adapter.notifyDataSetChanged();
        }
        if (requestCode == REQUEST_FOR_CREATE_NOTE) {
            Snackbar.make(fab, R.string.create_note_success, Snackbar.LENGTH_LONG)
                    .setAction(R.string.revert, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            noteFactory.getNotes().remove(noteFactory.getNotes().size() - 1);
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .show();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                AVUser.logOut();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
