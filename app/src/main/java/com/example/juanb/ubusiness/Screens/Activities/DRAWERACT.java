package com.example.juanb.ubusiness.Screens.Activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.juanb.ubusiness.R;
import com.example.juanb.ubusiness.Screens.Fragments.FragmentAutomaticCobro;
import com.example.juanb.ubusiness.Screens.Fragments.FragmentAutomaticCobroDetail;
import com.example.juanb.ubusiness.Screens.Fragments.FragmentCobrosDetail;
import com.example.juanb.ubusiness.Screens.Fragments.FragmentCreateOrUpdate;
import com.example.juanb.ubusiness.Screens.Fragments.FragmentFormatoAutomaticCobro;
import com.example.juanb.ubusiness.Screens.Fragments.FragmentFormatoDeCobro;
import com.example.juanb.ubusiness.Screens.Fragments.FragmentInquilinosDetail;
import com.example.juanb.ubusiness.Screens.Fragments.FragmentInquilinosList;
import com.example.juanb.ubusiness.Screens.Fragments.FragmentInsertarCobroList;
import com.example.juanb.ubusiness.models.objects.Cobros;
import com.example.juanb.ubusiness.models.objects.Inquilinos;
import com.example.juanb.ubusiness.models.objects.Myglobalvar;
import com.example.juanb.ubusiness.tasks.DeleteFromDatabase;
import com.example.juanb.ubusiness.tasks.LoadInitialInquilinosDatabase;
import com.example.juanb.ubusiness.tasks.ReadCobros;
import com.example.juanb.ubusiness.tasks.ReadFromDatabase;
import com.example.juanb.ubusiness.tasks.WriteCobros;
import com.example.juanb.ubusiness.tasks.WriteToDatabase;

import java.io.File;
import java.util.ArrayList;

public class DRAWERACT extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentCreateOrUpdate.FragmentUpdateOrCreateTodoCallback , FragmentInquilinosList.FragmentItemClickCallback, FragmentInquilinosDetail.FragmentEditItemCallback , FragmentInsertarCobroList.FragmentItemClickCallback2, FragmentFormatoDeCobro.FragmentFormatoDeCobroCallback, FragmentCobrosDetail.FragmentEditItemCallback, FragmentAutomaticCobro.FragmentItemClickCallback, FragmentAutomaticCobroDetail.FragmentEditItemCallback, FragmentFormatoAutomaticCobro.FragmentFormatoAutomaticCobroCallback{

    private ArrayList<Inquilinos> listData;
    private ArrayList<Cobros> listData2;
    private FragmentManager manager;
    //private Button button1;
    private static final String FRAG_CREATE_OR_UPDATE = "FRAG_CREATE_OR_UPDATE";
    private static final String FRAG_INQUILINOS_LIST = "FRAG_INQUILINOS_LIST";
    private static final String FRAG_INQUILINOS_DETAIL = "FRAG_INQUILINOS_DETAIL";
    private static final String FRAG_INSERTAR_COBRO = "FRAG_INSERTAR_COBRO";
    private static final String FRAG_FORMATO_DE_COBRO = "FRAG_FORMATO_DE_COBRO";
    private static final String FRAG_COBRO_DETAIL = "FRAG_COBRO_DETAIL";
    private static final String FRAG_AUTOMATIC_COBRO = "FRAG_AUTOMATIC_COBRO";
    private static final String FRAG_AUTOMATIC_COBRO_DETAIL = "FRAG_AUTOMATIC_COBRO_DETAIL";



    public void empezar(){
        String PhoneModel = android.os.Build.MODEL;
        String AndroidVersion = android.os.Build.VERSION.RELEASE;
        String PhoneID = Build.ID;
        ((Myglobalvar) this.getApplication()).setPhoneModel(PhoneModel);
        ((Myglobalvar) this.getApplication()).setAndroidVersion(AndroidVersion);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draweract);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        manager = getSupportFragmentManager();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try{

                File database = getApplicationContext().getDatabasePath("todos.db");
                if (!database.exists()) {
                    LoadInitialInquilinosDatabase loader = new LoadInitialInquilinosDatabase(getApplicationContext());
                    loader.setOnDatabaseBuilt(new LoadInitialInquilinosDatabase.OnDatabaseBuilt() {
                        @Override
                        public void buildComplete() {
                            loadInquilinosList();
                        }
                    });
                    loader.execute();

                } else {
                    loadInquilinosList();
                }


        }catch (Exception E){
            Log.e("ERRORRRRRRRRR","Cargar BASEDEDATOS INQUILINOS");
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.draweract, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Inquilinos) {
            loadInquilinosList();
        } else if (id == R.id.nav_Insertar_Inquilinos) {
            onAddInquilinosButtonClickedUOC();
        } else if (id == R.id.nav_Cobros_Realizados) {
            LoadInsertarCobros();
        } else if (id == R.id.nav_Insertar_Cobro) {
            LoadAutomaticCobro();
        } else if (id == R.id.nav_manage) {
            Something();
        } else if (id == R.id.nav_share) {
            ToastBOOM("No estas conectado a ninguna cuenta, no podras compartir");
        } else if (id == R.id.nav_send) {
            ToastBOOM("No estas conectado a ninguna cuenta, no podras enviar");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

public void LoadInsertarCobroslst(){

    try{
        Fragment createFrag = FragmentInsertarCobroList.newInstance(listData2);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

        transaction.replace(R.id.cont_todo_fragments1, createFrag, FRAG_INSERTAR_COBRO);
        transaction.commit();
    }catch (Exception E){
        Log.e("HEYYY ERRORRRRRRR", E.getMessage());
    }
}


    public void LoadInsertarCobros(){
        try{
            ReadCobros reader = new ReadCobros(getApplicationContext());
            reader.setQueryCompleteListener(new ReadCobros.OnQueryComplete() {
                @Override
                public void setQueryComplete(ArrayList result) {
                    listData2 = result;
                    LoadInsertarCobroslst();
                }
            });
            reader.execute();
        }catch (Exception E){
            Log.e("ERRORRRRRRRRR","Cargar LISTA INQUILINOS");
        }

    }






    public void onAddInquilinosButtonClickedUOC() {
        try {
            Fragment createFrag = FragmentCreateOrUpdate.newInstance();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

            transaction.replace(R.id.cont_todo_fragments1, createFrag, FRAG_CREATE_OR_UPDATE);
            transaction.commit();
        }catch (Exception E){
            Log.e("HEYYY ERRORRRRRRR", E.getMessage());
        }
    }



    @Override
    public void onDoneButtonClickUOC(Inquilinos inqui) {
        try{
        WriteToDatabase writer = new WriteToDatabase(getApplicationContext(), inqui);
        writer.setWriteCompleteListener(new WriteToDatabase.OnWriteComplete() {
            @Override
            public void setWriteComplete(long result) {
                ToastBOOM("LISTO");
                loadInquilinosList();
            }
        });
        writer.execute();
        }catch (Exception E){
            ToastBOOM("Error al Insertar");
            Log.e("ERROR LISTO","YA EXISTE");
        }
    }

    private void loadInquilinosList() {
        try{
        ReadFromDatabase reader = new ReadFromDatabase(getApplicationContext());
        reader.setQueryCompleteListener(new ReadFromDatabase.OnQueryComplete() {
            @Override
            public void setQueryComplete(ArrayList result) {
                listData = result;
                loadListFragment();
            }
        });
        reader.execute();
        }catch (Exception E){
            Log.e("ERRORRRRRRRRR","Cargar LISTA INQUILINOS");
        }
    }

    private void loadListFragment() {
        try{
        Fragment listFrag = FragmentInquilinosList.newInstance(listData);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

        transaction.replace(R.id.cont_todo_fragments1, listFrag, FRAG_INQUILINOS_LIST);
        transaction.commit();
        }catch (Exception E){
            Log.e("ERRORRRRRRRRR","FRAGMENTOS INQUILINOS");
        }

    }

    @Override
    public void onEditButtonClick(Inquilinos inquilinos) {
        try{
            Fragment createFrag = FragmentCreateOrUpdate.newInstance(inquilinos);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

            transaction.replace(R.id.cont_todo_fragments1, createFrag, FRAG_CREATE_OR_UPDATE);
            transaction.commit();
        }catch (Exception E){
            Log.e("ERRORRRRRR","EDITANDO INQUILINOS");
        }
    }

    @Override
    public void onListItemSwipedIL(int position) {
        try{
        DeleteFromDatabase delete = new DeleteFromDatabase(getApplicationContext(),
                listData.get(position));
        delete.setDeleteCompleteListener(new DeleteFromDatabase.OnDeleteComplete() {
            @Override
            public void setQueryComplete(Long result) {

            }
        });
        delete.execute();
        }catch (Exception E){
            Log.e("ERRORRRRRRRRR","BORRANDO INQUILINOS");
        }
    }
    @Override
    public void onListItemClickedIL(int position) {
        try{
        Fragment detailFrag = FragmentInquilinosDetail.newInstance(listData.get(position));
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

        transaction.replace(R.id.cont_todo_fragments1, detailFrag, FRAG_COBRO_DETAIL);
        transaction.commit();
        }catch (Exception E){
            Log.e("ERRORRRRRRRRR","CLIKEANDO INQUILINOS");
        }
    }







    @Override
    public void onListItemSwipedCL(int position) {
        try{
            DeleteFromDatabase delete = new DeleteFromDatabase(getApplicationContext(),
                    listData.get(position));
            delete.setDeleteCompleteListener(new DeleteFromDatabase.OnDeleteComplete() {
                @Override
                public void setQueryComplete(Long result) {

                }
            });
            delete.execute();
        }catch (Exception E){
            Log.e("ERRORRRR CL","BORRANDO INQUILINOS");
        }
    }

    @Override
    public void onListItemClickedCL(int position) {
        try{
            Fragment detailFrag = FragmentCobrosDetail.newInstance(listData2.get(position));
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

            transaction.replace(R.id.cont_todo_fragments1, detailFrag, FRAG_INQUILINOS_DETAIL);
            transaction.commit();
        }catch (Exception E){
            Log.e("ERRORRRRR  CL ","CLIKEANDO COBROS");
        }

    }

    @Override
    public void onAddCobrosButtonClickedCL() {
        try {
            Fragment createFrag = FragmentFormatoDeCobro.newInstance();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

            transaction.replace(R.id.cont_todo_fragments1, createFrag, FRAG_FORMATO_DE_COBRO);
            transaction.commit();
        }catch (Exception E){
            Log.e("HEYYY CL", E.getMessage());
        }


    }


    @Override
    public void onDoneButtonClickFC(Cobros cobro) {
        try{
            WriteCobros writer = new WriteCobros(getApplicationContext(), cobro);
            writer.setWriteCompleteListener(new WriteCobros.OnWriteComplete() {
                @Override
                public void setWriteComplete(long result) {
                    ToastBOOM("LISTO");
                    LoadInsertarCobros();
                }
            });
            writer.execute();
        }catch (Exception E){
            Log.e("ERROR LISTO","YA EXISTE");
        }

    }


    @Override
    public void onEditButtonClickCD(Cobros cobros) {
        try{
            Fragment createFrag = FragmentFormatoDeCobro.newInstance(cobros);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

            transaction.replace(R.id.cont_todo_fragments1, createFrag, FRAG_COBRO_DETAIL);
            transaction.commit();
        }catch (Exception E){
            Log.e("ERRORRRRRR","EDITANDO INQUILINOS");
        }

    }



    public void LoadAutomaticCobro(){
        try{
            ReadFromDatabase reader = new ReadFromDatabase(getApplicationContext());
            reader.setQueryCompleteListener(new ReadFromDatabase.OnQueryComplete() {
                @Override
                public void setQueryComplete(ArrayList result) {
                    listData = result;
                    LoadListAutoCobro();
                }
            });
            reader.execute();
        }catch (Exception E){
            Log.e("ERRORRRRRRRRR","Cargar LISTA INQUILINOS");
        }

    }

    public void LoadListAutoCobro(){
        try{
            Fragment listFrag = FragmentAutomaticCobro.newInstance(listData);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

            transaction.replace(R.id.cont_todo_fragments1, listFrag, FRAG_AUTOMATIC_COBRO);
            transaction.commit();
        }catch (Exception E){
            Log.e("ERRORRRRRRRRR","FRAGMENTOS INQUILINOS");
        }
    }


    @Override
    public void onListItemClickedAC(int position) {
        try{
            Fragment detailFrag = FragmentAutomaticCobroDetail.newInstance(listData.get(position));
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

            transaction.replace(R.id.cont_todo_fragments1, detailFrag, FRAG_AUTOMATIC_COBRO_DETAIL);
            transaction.commit();
        }catch (Exception E){
            Log.e("ERRORRRRRRRRR","CLIKEANDO INQUILINOS");
        }
    }





    @Override
    public void onEditButtonClickAC(Inquilinos inquilinos) {
        try{
            Fragment createFrag = FragmentFormatoAutomaticCobro.newInstance(inquilinos);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

            transaction.replace(R.id.cont_todo_fragments1, createFrag, FRAG_CREATE_OR_UPDATE);
            transaction.commit();
        }catch (Exception E){
            Log.e("ERRORRRRRR","EDITANDO INQUILINOS");
        }
    }




    @Override
    public void onDoneButtonClickAC(Cobros cobro) {
        try{
            WriteCobros writer = new WriteCobros(getApplicationContext(), cobro);
            writer.setWriteCompleteListener(new WriteCobros.OnWriteComplete() {
                @Override
                public void setWriteComplete(long result) {
                    ToastBOOM("LISTO");
                    LoadInsertarCobros();
                }
            });
            writer.execute();
        }catch (Exception E){
            Log.e("ERROR LISTO","YA EXISTE");
        }

    }




    public void ToastBOOM(String texto) {
        Toast.makeText(getApplicationContext(), " "+texto+" ", Toast.LENGTH_SHORT).show();
    }

    public void ToastBOOMLARGE(String texto) {
        Toast.makeText(getApplicationContext(), " "+texto+" ", Toast.LENGTH_LONG).show();
    }

    public void Something() {
        String PhoneModel = android.os.Build.MODEL;
        String AndroidVersion = android.os.Build.VERSION.RELEASE;
        String PhoneID = Build.ID;
        int Apilvl = Build.VERSION.SDK_INT;
        Toast.makeText(getApplicationContext(), " Phone: "+PhoneModel+" | OS: "+AndroidVersion + " | PID: "+PhoneID+ " | API lvl: "+Apilvl, Toast.LENGTH_SHORT).show();
    }

}
