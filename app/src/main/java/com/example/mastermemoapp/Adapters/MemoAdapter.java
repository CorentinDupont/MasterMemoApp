package com.example.mastermemoapp.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mastermemoapp.Database.Schemas.MemoDTO;
import com.example.mastermemoapp.R;
import com.example.mastermemoapp.Webservices.HttpbinPostResponse;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

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

        private static final String TAG = "MemoViewHolder";
        private TextView memoTextTV = null;

        private MemoViewHolder(@NonNull View itemView) {
            super(itemView);

            this.memoTextTV = itemView.findViewById(R.id.memo_text_tv);

            // add on click listener to show content of the memo.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "Clicked Memo");
                    MemoDTO memo = listMemo.get(getAdapterPosition());
                    callWebservice(v.getContext(), memo);
                    saveClickedItemInSharedPref(v.getContext(), getAdapterPosition());
                }
            });
        }

        private TextView getMemoTextTV() {
            return memoTextTV;
        }

        /**
         * Call a webservice which return the sent memo.
         * A Toast display data.
         * @param memo memo to send to the webservice
         */
        private void callWebservice(final Context context, MemoDTO memo) {
            // create http client
            AsyncHttpClient client = new AsyncHttpClient();

            // set parameters
            RequestParams requestParams = new RequestParams();
            requestParams.put("memo", memo.getText());

            // call webservice
            client.post("http://httpbin.org/post", requestParams, new AsyncHttpResponseHandler()
            {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response)
                {
                    // Webservice response
                    String responseString = new String(response);

                    // Convert JSON to Object
                    Gson gson = new Gson();
                    HttpbinPostResponse responseObject = gson.fromJson(responseString, HttpbinPostResponse.class);

                    // Display memo content in a toast
                    Log.i(TAG, responseObject.getForm().getMemo());
                    Toast.makeText(context, responseObject.getForm().getMemo(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers,
                                      byte[] errorResponse, Throwable e)
                {
                    Log.e(TAG, e.toString());
                }
            });
        }

        /**
         * Save a position into Shared Preferences.
         * @param context context of the app
         * @param position memo position to store
         */
        private void saveClickedItemInSharedPref(Context context, int position) {
            SharedPreferences preferences =
                    PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(String.valueOf(R.string.shared_pref_key_item_pos), getAdapterPosition());
            editor.apply();
        }
    }
}


