package com.example.juanb.ubusiness.models.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.juanb.ubusiness.models.objects.Cobros;
import com.example.juanb.ubusiness.models.objects.Inquilinos;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by juanb on 02/02/17.
 */

public class BusinessDatabase {

    /**
     * These variables describe what our Database will look like, what the table will be called,
     * and what each column will be called. See the nested SQLiteOpenHelper class below to see them
     * in action. These variables may also be placed in a seperate "contract" class, as described in
     * the Android Developer guide "Saving Data in SQL Databases"
     *
     * @see <a href="https://developer.android.com/training/basics/data-storage/databases.html#DbHelper"</a>
     */
    private static final String TABLE_NAME = "todos";
    private static final String TABLE_NAME2 = "facturas";
    private static final String COLUMN_ENTRY_ID = "entry_id";
    private static final String COLUMN_TODO_CONTENT = "content";
    private static final String COLUMN_CREATION_DATE = "creation";
    private static final String COLUMN_INQUILINOS_NOMBRE = "nombre";
    private static final String COLUMN_INQUILINOS_APELLIDO = "apellido";
    private static final String COLUMN_INQUILINOS_CEDULA = "cedula";
    private static final String COLUMN_INQUILINOS_CUARTO = "cuarto";
    private static final String COLUMN_INQUILINOS_MONTO = "monto";
    private static final String COLUMN_INQUILINOS_DIAPAGO = "diapago";
    private static final String COLUMN_FACTURA = "Factura";
    private static final String COLUMN_DESCRIPCION = "Descripcion";


    private static final String COLUMN_TODO_DATA2 = "dataFact";
    private static final String COLUMN_TODO_DATA = "data";

    /**
     * - DATABASE_VERSION is to be incremented up by 1 (we'll, I think it just needs to be a larger
     * number...but I don't see the point in testing that theory), each time we change the schema,
     * or structure of the Database.
     * - DATABASE_NAME is simply the file name of our Database. We can use the file name in our
     * Java code to check if a Databse exists or not. This will come in handy if you want to
     * "pre-load" data into your database.
     */
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todos.db";
    private static final String TEXT_TYPE = " TEXT",INT_TYPE = " INTEGER",REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";

    private TodoDatabaseHelper helper;
    private static BusinessDatabase database;

    public static BusinessDatabase getInstance(Context c) {
        if (database == null) {
            database = new BusinessDatabase(c);
        }
        return database;
    }


    private BusinessDatabase(Context c) {
        helper = new TodoDatabaseHelper(c);
    }

    /**
     * Method which grabs all of the to do data in our database. This gets into some interesting
     * water, as you may be wondering what we can do once the number of to dos we return gets
     * large enough to cause some issues. I plan on revisiting this issue at some point.
     *
     * @return
     */
    public ArrayList getAllData() {
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<Inquilinos> result = new ArrayList<>();
        Gson gson = new Gson();

        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                Inquilinos inquilinos = gson.fromJson(
                        c.getString(
                                c.getColumnIndex(COLUMN_TODO_DATA)
                        ),
                        Inquilinos.class);
                result.add(inquilinos);
            }
            while (c.moveToNext());
        }

        c.close();
        db.close();
        return result;
    }

    public ArrayList getAllData2() {
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<Cobros> result = new ArrayList<>();
        Gson gson = new Gson();

        Cursor c = db.query(TABLE_NAME2, null, null, null, null, null, null);

        if (c.moveToLast()) {
            do {

                Cobros cobros = gson.fromJson(
                        c.getString(
                                c.getColumnIndex(COLUMN_TODO_DATA2)
                        ), Cobros.class);
                result.add(cobros);
            }
            while (c.moveToPrevious());
        }

        c.close();
        db.close();
        return result;
    }

    /**
     * Why wouldn't we make two seperate methods to handle inserting or updating the data?
     * There's probably pros to that approach as well (for example, if we are certain that the new
     * to do is going to be added and not updated, we are wasting time parsing the database first),
     * but we need to consider that this approach allows us to have fewer methods and classes in
     * the rest of the program. I'm happy to hear opinions on this :)
     * @param inqui either an existing to do which the user wishes to update, or a new to do which
     *             should be added to the database.
     * @return the result of our database update or insert operation
     */
    public long insertOrUpdateData(Inquilinos inqui) {
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor c = db.query(TABLE_NAME, null, null, null, null, null,null);
            ContentValues val = new ContentValues();

            Gson gson = new Gson();

            if (c.moveToFirst()) {
                do {
                    //In English: If the current Cursor's item Creation Date matches the date of the
                    // to do object we are trying to write, update that entry instead of creating a new
                    //one.
                    if (c.getString(c.getColumnIndex(COLUMN_CREATION_DATE))
                            .equals(inqui.getTodoCreationDate())) {
                        val.put(COLUMN_TODO_CONTENT, inqui.getTodoContent());
                        val.put(COLUMN_CREATION_DATE, inqui.getTodoCreationDate());
                        val.put(COLUMN_INQUILINOS_NOMBRE, inqui.getNombre());
                        val.put(COLUMN_INQUILINOS_APELLIDO, inqui.getApellido());
                        val.put(COLUMN_INQUILINOS_CEDULA, inqui.getCedula());
                        val.put(COLUMN_INQUILINOS_CUARTO, inqui.getCuarto());
                        val.put(COLUMN_INQUILINOS_MONTO, inqui.getMonto());
                        val.put(COLUMN_INQUILINOS_DIAPAGO, inqui.getDiapago());
                        val.put(COLUMN_TODO_DATA, gson.toJson(inqui, Inquilinos.class));
                        //selection and selectionArgs simple tell our db which rows we
                        //want to update. Notice how selectionArgs is an array. We could supply
                        //multiple column ids if we wish to.
                        String selection = COLUMN_ENTRY_ID + " LIKE ?";
                        String[] selectionArgs = {
                                String.valueOf(c.getString(c.getColumnIndex(COLUMN_ENTRY_ID)))
                        };
                        long id = db.update(TABLE_NAME, val, selection, selectionArgs);
                        c.close();
                        db.close();
                        return id;
                    }
                }
                while (c.moveToNext());
            }

            //no match was found, therefore we should create a new entry.
            val.put(COLUMN_TODO_CONTENT, inqui.getTodoContent());
            val.put(COLUMN_CREATION_DATE, inqui.getTodoCreationDate());
            val.put(COLUMN_INQUILINOS_NOMBRE, inqui.getNombre());
            val.put(COLUMN_INQUILINOS_APELLIDO, inqui.getApellido());
            val.put(COLUMN_INQUILINOS_CEDULA, inqui.getCedula());
            val.put(COLUMN_INQUILINOS_CUARTO, inqui.getCuarto());
            val.put(COLUMN_INQUILINOS_MONTO, inqui.getMonto());
            val.put(COLUMN_INQUILINOS_DIAPAGO, inqui.getDiapago());
            val.put(COLUMN_TODO_DATA, gson.toJson(inqui, Inquilinos.class));

            long id = db.insert(TABLE_NAME, null, val);
            c.close();
            db.close();
            return id;
    }


    public long insertOrUpdateData2(Cobros cobros) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME2, null, null, null, null, null,null);
        ContentValues val2 = new ContentValues();

        Gson gson = new Gson();

        if (c.moveToFirst()) {
            do {
                //In English: If the current Cursor's item Creation Date matches the date of the
                // to do object we are trying to write, update that entry instead of creating a new
                //one.
                if (c.getString(c.getColumnIndex(COLUMN_CREATION_DATE))
                        .equals(cobros.getTodoCreationDate())) {
                    val2.put(COLUMN_TODO_CONTENT, cobros.gettodoContent());
                    val2.put(COLUMN_CREATION_DATE, cobros.getTodoCreationDate());
                    val2.put(COLUMN_INQUILINOS_NOMBRE, cobros.getNombre());
                    val2.put(COLUMN_INQUILINOS_APELLIDO, cobros.getApellido());
                    val2.put(COLUMN_INQUILINOS_CEDULA, cobros.getCedula());
                    val2.put(COLUMN_INQUILINOS_CUARTO, cobros.getCuarto());
                    val2.put(COLUMN_INQUILINOS_MONTO, cobros.getMonto());
                    val2.put(COLUMN_TODO_DATA2, gson.toJson(cobros, Cobros.class));
                    //selection and selectionArgs simple tell our db which rows we
                    //want to update. Notice how selectionArgs is an array. We could supply
                    //multiple column ids if we wish to.
                    String selection = COLUMN_FACTURA + " LIKE ?";
                    String[] selectionArgs = {
                            String.valueOf(c.getString(c.getColumnIndex(COLUMN_FACTURA)))
                    };
                    long id = db.update(TABLE_NAME2, val2, selection, selectionArgs);
                    c.close();
                    db.close();
                    return id;
                }
            }
            while (c.moveToNext());
        }

        //no match was found, therefore we should create a new entry.
        val2.put(COLUMN_TODO_CONTENT, cobros.gettodoContent());
        val2.put(COLUMN_CREATION_DATE, cobros.getTodoCreationDate());
        val2.put(COLUMN_INQUILINOS_NOMBRE, cobros.getNombre());
        val2.put(COLUMN_INQUILINOS_APELLIDO, cobros.getApellido());
        val2.put(COLUMN_INQUILINOS_CEDULA, cobros.getCedula());
        val2.put(COLUMN_INQUILINOS_CUARTO, cobros.getCuarto());
        val2.put(COLUMN_INQUILINOS_MONTO, cobros.getMonto());
        val2.put(COLUMN_TODO_DATA2, gson.toJson(cobros, Cobros.class));

        long id = db.insert(TABLE_NAME2, null, val2);
        c.close();
        db.close();
        return id;
    }


    /**
     * pretty self-explanatory.
     * @param todo
     * @return
     */
    public long deleteTodo(Inquilinos todo) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                if (c.getString(c.getColumnIndex(COLUMN_CREATION_DATE))
                        .equals(todo.getTodoCreationDate())
                        ) {
                    String selection = COLUMN_ENTRY_ID + " LIKE ?";
                    String[] selectionArgs = {
                            String.valueOf(c.getString(c.getColumnIndex(COLUMN_ENTRY_ID)))
                    };

                    long id = db.delete(TABLE_NAME, selection, selectionArgs);
                    c.close();
                    db.close();
                    return id;
                }
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return -1;
    }

    public long deleteTodo2(Cobros cobro) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME2, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                if (c.getString(c.getColumnIndex(COLUMN_CREATION_DATE))
                        .equals(cobro.getTodoCreationDate())
                        ) {
                    String selection = COLUMN_FACTURA + " LIKE ?";
                    String[] selectionArgs = {
                            String.valueOf(c.getString(c.getColumnIndex(COLUMN_CREATION_DATE)))
                    };

                    long id = db.delete(TABLE_NAME2, selection, selectionArgs);
                    c.close();
                    db.close();
                    return id;
                }
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return -1;
    }

        /*------------------------------Database Helper------------------------*/

    private static class TodoDatabaseHelper extends SQLiteOpenHelper {
        private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ENTRY_ID + " INTEGER PRIMARY KEY," +
                COLUMN_TODO_CONTENT + TEXT_TYPE + COMMA_SEP +
                COLUMN_CREATION_DATE + TEXT_TYPE + COMMA_SEP +
                COLUMN_INQUILINOS_NOMBRE + TEXT_TYPE + COMMA_SEP +
                COLUMN_INQUILINOS_APELLIDO + TEXT_TYPE + COMMA_SEP +
                COLUMN_INQUILINOS_CEDULA + " INTEGER NOT NULL UNIQUE," +
                COLUMN_INQUILINOS_CUARTO + " INTEGER NOT NULL UNIQUE," +
                COLUMN_INQUILINOS_MONTO + TEXT_TYPE + COMMA_SEP +
                COLUMN_INQUILINOS_DIAPAGO + TEXT_TYPE + COMMA_SEP +
                COLUMN_TODO_DATA + TEXT_TYPE +
                " )";



        private static final String SQL_CREATE_ENTRIES2 = "CREATE TABLE " + TABLE_NAME2 + " (" +
                COLUMN_FACTURA + " INTEGER PRIMARY KEY," +
                COLUMN_TODO_CONTENT + TEXT_TYPE + COMMA_SEP +
                COLUMN_CREATION_DATE + TEXT_TYPE + COMMA_SEP +
                COLUMN_INQUILINOS_NOMBRE + TEXT_TYPE + COMMA_SEP +
                COLUMN_INQUILINOS_APELLIDO + TEXT_TYPE + COMMA_SEP +
                COLUMN_INQUILINOS_CEDULA + " INTEGER NOT NULL," +
                COLUMN_INQUILINOS_CUARTO + " INTEGER NOT NULL," +
                COLUMN_INQUILINOS_MONTO + TEXT_TYPE + COMMA_SEP +
                COLUMN_TODO_DATA2 + TEXT_TYPE +
                " )";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

        private static final String SQL_DELETE_ENTRIES2 =
                "DROP TABLE IF EXISTS " + TABLE_NAME2;

        private Context context;

        /**
         * the null value passed is a CursorFactory object. CursorFactory is used when we wish
         * to pass in a custom sub-class of Cursor. When would we need to do that? I have no
         * ****ing idea...
         *
         * @param context - Self Explanatory
         */
        public TodoDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
            db.execSQL(SQL_CREATE_ENTRIES2);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            db.execSQL(SQL_DELETE_ENTRIES2);
            onCreate(db);
        }
    }
}
