package com.example.juanb.ubusiness.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.juanb.ubusiness.R;
import com.example.juanb.ubusiness.models.database.BusinessDatabase;
import com.example.juanb.ubusiness.models.objects.Cobros;
import com.example.juanb.ubusiness.models.objects.Inquilinos;
import com.example.juanb.ubusiness.models.objects.ListOfCobros;
import com.example.juanb.ubusiness.models.objects.ListOfInquilinos;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
 * Created by juanb on 02/02/17.
 */

public class LoadInitialInquilinosDatabase extends AsyncTask<Void,Void,Void> {
    private Context context;
    private OnDatabaseBuilt onDatabaseBuilt;

    public interface OnDatabaseBuilt {
        void buildComplete();
    }

    public LoadInitialInquilinosDatabase (Context context){
        this.context = context;
    }

    public void setOnDatabaseBuilt(OnDatabaseBuilt onDatabaseBuilt) {
        this.onDatabaseBuilt = onDatabaseBuilt;
    }

    @Override
    protected Void doInBackground(Void... params) {
        BusinessDatabase database = BusinessDatabase.getInstance(context);

        InputStream raw = context.getResources().openRawResource(R.raw.todos);
        Reader reader = new BufferedReader(new InputStreamReader(raw));

        InputStream raw2 = context.getResources().openRawResource(R.raw.cobro);
        Reader reader2 = new BufferedReader(new InputStreamReader(raw2));



        /*
         You might be wondering why I made the ListOfTodos class at all. The simple answer is I
          didn't know how to deserielize directly into a List without dicking around with
          TypeTokens. Feel free to give me shit about this as I'm pretty sure there's a better
          way to handle this situation.

         */



       ListOfCobros listOfCobros = new Gson().fromJson(reader2, ListOfCobros.class);
       List<Cobros> cobrosList = listOfCobros.getTodoArrayList2();

        ListOfInquilinos listOfTodos = new Gson().fromJson(reader, ListOfInquilinos.class);
        List<Inquilinos> todoList = listOfTodos.getTodoArrayList();




        for (Cobros item2: cobrosList){
          database.insertOrUpdateData2(item2);
        }

        for (Inquilinos item: todoList){
            database.insertOrUpdateData(item);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void param){
        onDatabaseBuilt.buildComplete();
    }
}
