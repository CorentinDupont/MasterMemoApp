package com.example.mastermemoapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mastermemoapp.Database.Schemas.MemoDTO;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MemoDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemoDetailFragment extends Fragment {
    public static final String MEMO_TEXT_PARAM = "memo_text_param";

    private String memoText;

    public MemoDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param memo Memo object to display details of it.
     * @return A new instance of fragment MemoDetailFragment.
     */
    public static MemoDetailFragment newInstance(MemoDTO memo) {
        MemoDetailFragment fragment = new MemoDetailFragment();
        Bundle args = new Bundle();
        args.putString(MEMO_TEXT_PARAM, memo.getText());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            memoText = getArguments().getString(MEMO_TEXT_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_memo_detail, container, false);
        TextView memoTextTV = view.findViewById(R.id.memo_detail_text_tv);
        memoTextTV.setText(memoText);

        // return view
        return view;
    }
}
