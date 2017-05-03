package com.example.juanb.ubusiness.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.juanb.ubusiness.models.database.BusinessDatabase;
import com.example.juanb.ubusiness.models.objects.Inquilinos;

/**
 * Created by juanb on 02/02/17.
 */

public class WriteToDatabase extends AsyncTask<Void, Void, Long> {
    private Inquilinos inqui;
    private Context context;

    private OnWriteComplete onWriteComplete;

    public interface OnWriteComplete {
        void setWriteComplete(long result);
    }

    public void setWriteCompleteListener(OnWriteComplete onWriteComplete) {
        this.onWriteComplete = onWriteComplete;
    }

    public WriteToDatabase (Context context, Inquilinos inqui){
        this.inqui = inqui;
        this.context = context;
    }


    protected Long doInBackground(Void... params) {
            BusinessDatabase database = BusinessDatabase.getInstance(context);
            return database.insertOrUpdateData(inqui);
    }

    /**
     *If param ends up as -1, then our To do has failed to be entered into the Database
     * @param param - Result of database operation
     */

    protected void onPostExecute(Long param) {
        onWriteComplete.setWriteComplete(param);
    }

}
