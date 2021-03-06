package com.example.juanb.ubusiness.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.juanb.ubusiness.models.database.BusinessDatabase;

import java.util.ArrayList;

/**
 * Created by juanb on 02/08/17.
 */

public class ReadCobros extends AsyncTask<Void, Void, ArrayList> {
    private Context context;

    private OnQueryComplete onQueryComplete;

    public interface OnQueryComplete {
        void setQueryComplete(ArrayList result);
    }

    public void setQueryCompleteListener(OnQueryComplete onQueryComplete) {
        this.onQueryComplete = onQueryComplete;
    }

    public ReadCobros(Context context) {
        this.context = context;
    }


    protected ArrayList doInBackground(Void... params) {
        BusinessDatabase database = BusinessDatabase.getInstance(context);
        return database.getAllData2();
    }

    protected void onPostExecute(ArrayList result) {
        onQueryComplete.setQueryComplete(result);
    }
}
