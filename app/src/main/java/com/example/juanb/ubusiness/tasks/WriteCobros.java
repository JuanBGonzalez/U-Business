package com.example.juanb.ubusiness.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.juanb.ubusiness.models.database.BusinessDatabase;
import com.example.juanb.ubusiness.models.objects.Cobros;

/**
 * Created by juanb on 02/09/17.
 */

public class WriteCobros extends AsyncTask<Void, Void, Long> {
    private Cobros cobros;
    private Context context;

    private OnWriteComplete onWriteComplete;

    public interface OnWriteComplete {
        void setWriteComplete(long result);
    }

    public void setWriteCompleteListener(OnWriteComplete onWriteComplete) {
        this.onWriteComplete = onWriteComplete;
    }

    public WriteCobros (Context context, Cobros cobros){
        this.cobros = cobros;
        this.context = context;
    }


    protected Long doInBackground(Void... params) {
        BusinessDatabase database = BusinessDatabase.getInstance(context);
        return database.insertOrUpdateData2(cobros);
    }

    /**
     *If param ends up as -1, then our To do has failed to be entered into the Database
     * @param param - Result of database operation
     */

    protected void onPostExecute(Long param) {
        onWriteComplete.setWriteComplete(param);
    }

}
