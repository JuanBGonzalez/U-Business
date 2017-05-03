package com.example.juanb.ubusiness.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.juanb.ubusiness.models.database.BusinessDatabase;
import com.example.juanb.ubusiness.models.objects.Cobros;

/**
 * Created by juanb on 02/09/17.
 */

public class DeleteCobros extends AsyncTask<Void, Void, Long> {
    private Cobros cobros;
    private Context context;

    private OnDeleteComplete onDeleteComplete;

    public interface OnDeleteComplete {
        void setQueryComplete(Long result);
    }

    public DeleteCobros(Context context, Cobros cobros) {
        this.cobros = cobros;
        this.context = context;
    }

    public void setDeleteCompleteListener(OnDeleteComplete onDeleteComplete) {
        this.onDeleteComplete = onDeleteComplete;
    }

    @Override
    protected Long doInBackground(Void... params) {
        BusinessDatabase database = BusinessDatabase.getInstance(context);
        return database.deleteTodo2(cobros);
    }

    //Value returned from doInBackground is passed to onPostExecute
    @Override
    protected void onPostExecute(Long result) {
        onDeleteComplete.setQueryComplete(result);
    }

}
