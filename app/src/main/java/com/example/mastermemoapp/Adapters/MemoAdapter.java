package com.example.mastermemoapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mastermemoapp.Database.Schemas.MemoDTO;
import com.example.mastermemoapp.Entities.Memo;
import com.example.mastermemoapp.R;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.MemoViewHolder> {

    private List<MemoDTO> listMemo = null;

    public MemoAdapter(List<MemoDTO> listMemo) {
        this.listMemo = listMemo;
    }

    @NonNull
    @Override
    public MemoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewMemo = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_memo, viewGroup, false);
        return new MemoViewHolder(viewMemo);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoViewHolder memoViewHolder, int i) {
        memoViewHolder.getMemoTextTV().setText(listMemo.get(i).getText());
    }

    @Override
    public int getItemCount() {
        return listMemo.size();
    }

    // Called on each position change, during an item move.
    public boolean onItemMove(int startPosition, int endPosition)
    {
        Collections.swap(listMemo, startPosition, endPosition);
        notifyItemMoved(startPosition, endPosition);
        return true;
    }
    // Called one time on item deletion
    public void onItemDismiss(int position)
    {
        if (position > -1)
        {
            listMemo.remove(position);
            notifyItemRemoved(position);
        }
    }

    class MemoViewHolder extends RecyclerView.ViewHolder {

        private TextView memoTextTV = null;

        private MemoViewHolder(@NonNull View itemView) {
            super(itemView);

            this.memoTextTV = itemView.findViewById(R.id.memo_text_tv);

            // add on click listener to show position and content of the memo.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MemoDTO memo = listMemo.get(getAdapterPosition());
                    Toast.makeText(v.getContext(), memo.getText() + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        private TextView getMemoTextTV() {
            return memoTextTV;
        }

    }
}


