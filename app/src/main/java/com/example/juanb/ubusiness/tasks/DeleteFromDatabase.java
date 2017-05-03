package com.example.juanb.ubusiness.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.juanb.ubusiness.models.database.BusinessDatabase;
import com.example.juanb.ubusiness.models.objects.Inquilinos;

/**
 * Created by juanb on 02/02/17.
 */

public class DeleteFromDatabase extends AsyncTask<Void, Void, Long> {
    private Inquilinos inqui;
    private Context context;

    private OnDeleteComplete onDeleteComplete;

    public interface OnDeleteComplete {
        void setQueryComplete(Long result);
    }

    public DeleteFromDatabase(Context context, Inquilinos inqui) {
        this.inqui = inqui;
        this.context = context;
    }

    public void setDeleteCompleteListener(OnDeleteComplete onDeleteComplete) {
        this.onDeleteComplete = onDeleteComplete;
    }

    @Override
    protected Long doInBackground(Void... params) {
        BusinessDatabase database = BusinessDatabase.getInstance(context);
        return database.deleteTodo(inqui);
    }

    //Value returned from doInBackground is passed to onPostExecute
    @Override
    protected void onPostExecute(Long result) {
        onDeleteComplete.setQueryComplete(result);
    }
}
