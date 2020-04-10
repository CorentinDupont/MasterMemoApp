package com.example.mastermemoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amitshekhar.utils.DatabaseHelper;
import com.example.mastermemoapp.Adapters.MemoAdapter;
import com.example.mastermemoapp.Database.AppDatabaseHelper;
import com.example.mastermemoapp.Database.Schemas.MemoDTO;
import com.example.mastermemoapp.Entities.Memo;
import com.example.mastermemoapp.ItemTouchHelpers.MemoItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MemoAdapter.OnMemoListener {

    // memo list
    List<MemoDTO> listMemo = new ArrayList<>();

    // memo list adapter
    MemoAdapter memoAdapter;

    // memo recycler view
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // display last clicked position from shared preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int lastClickedPosition = preferences.getInt(String.valueOf(R.string.shared_pref_key_item_pos), 0);
        Toast.makeText(this, "Last clicked position : " + lastClickedPosition, Toast.LENGTH_SHORT).show();

        // get recycler view
        recyclerView = findViewById(R.id.memo_rv);
        recyclerView.setHasFixedSize(true);

        // item disposition
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        // get memos from database
        listMemo = AppDatabaseHelper.getDatabase(this).memosDAO().getMemoList();

        // create and set recycler view adapter
        memoAdapter = new MemoAdapter(listMemo, this);
        recyclerView.setAdapter(memoAdapter);

        // add item touch helper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
            new MemoItemTouchHelperCallback(memoAdapter)
        );
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // handle adding memos
        Button addMemoButton = findViewById(R.id.memo_ok_button);
        addMemoButton.setOnClickListener(this);
    }

    /**
     * Build a list of memo with fake data taking a length
     * @param length length of the generated list
     * @return a fake memo list
     */
    private List<Memo> buildFakeMemoList(int length) {
        List<Memo> listMemo = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listMemo.add(new Memo("Memo " + i));
        }
        return listMemo;
    }

    @Override
    public void onClick(View v) {

        // handle OK button click to insert a new memo in the recycler view
        if (v.getId() == R.id.memo_ok_button) {

            // create memo and insert it in the database
            EditText memoET = findViewById(R.id.memo_text_et);
            MemoDTO memo = new MemoDTO(memoET.getText().toString());
            AppDatabaseHelper.getDatabase(this).memosDAO().insert(memo);

            // refresh list
            listMemo.clear();
            List<MemoDTO> newMemoList = AppDatabaseHelper.getDatabase(this).memosDAO().getMemoList();
            listMemo.addAll(newMemoList);
            memoAdapter.notifyItemInserted(listMemo.size() - 1);
            recyclerView.smoothScrollToPosition(listMemo.size() - 1);
        }
    }

    @Override
    public void onMemoClick(int position, MemoDTO memo) {
        showMemoDetails(memo);
    }

    /**
     * Open the memo details fragment, as a new activity if portrait, or make appearing
     * fragment in current activity.
     * @param memo memo to display the details from.
     */
    private void showMemoDetails(MemoDTO memo) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // create fragment
            MemoDetailFragment fragment = new MemoDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString(MemoDetailFragment.MEMO_TEXT_PARAM, memo.getText());
            fragment.setArguments(bundle);

            // display fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.main_memo_detail_frame_layout, fragment).commit();
        } else {
            Intent intent = new Intent(this, MemoDetailActivity.class);
            intent.putExtra(MemoDetailFragment.MEMO_TEXT_PARAM, memo.getText());
            startActivity(intent);
        }
    }


}
