package com.example.mastermemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MemoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_detail);

        // create fragment
        MemoDetailFragment fragment = new MemoDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MemoDetailFragment.MEMO_TEXT_PARAM, getIntent().getStringExtra(MemoDetailFragment.MEMO_TEXT_PARAM));
        fragment.setArguments(bundle);

        // display fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.detail_memo_frame_layout, fragment).commit();
    }
}
