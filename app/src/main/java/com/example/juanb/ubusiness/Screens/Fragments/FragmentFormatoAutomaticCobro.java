package com.example.juanb.ubusiness.Screens.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.juanb.ubusiness.R;
import com.example.juanb.ubusiness.models.objects.Cobros;
import com.example.juanb.ubusiness.models.objects.Inquilinos;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link interface
 * to handle interaction events.
 * Use the {@link FragmentFormatoAutomaticCobro#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFormatoAutomaticCobro extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String INQUILINOS = "INQUILINOS";
    private static final String COBROS = "COBROS";

    // TODO: Rename and change types of parameters
    private Inquilinos inqui;
    private Cobros cobro;
    private EditText content,nombre,apellido,cedula,cuarto,monto;
    private TextView creationDate;
    private Button done;
    private FragmentFormatoAutomaticCobroCallback callback;

    private OnFragmentInteractionListener mListener;

    public FragmentFormatoAutomaticCobro() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static FragmentFormatoAutomaticCobro newInstance(Inquilinos inquilinos) {
        FragmentFormatoAutomaticCobro fragment = new FragmentFormatoAutomaticCobro();
        Bundle args = new Bundle();
        args.putParcelable(INQUILINOS, inquilinos);
        fragment.setArguments(args);
        return fragment;
    }


    public static FragmentFormatoAutomaticCobro newInstance() {
        FragmentFormatoAutomaticCobro fragment = new FragmentFormatoAutomaticCobro();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.inqui = getArguments().getParcelable(INQUILINOS);
            this.cobro = getArguments().getParcelable(COBROS);
            cobro = new Cobros("", getDate(), "","" , "","","");
        }else{
            cobro = new Cobros("", getDate(), "","" , "","","");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setRetainInstance(true);
        View v = inflater.inflate(R.layout.fragment_fragment_formato_automatic_cobro, container, false);
        content = (EditText) v.findViewById(R.id.edt_inquilinos_content);
        nombre = (EditText) v.findViewById(R.id.edt_inquilinos_nombre);
        apellido = (EditText) v.findViewById(R.id.edt_inquilinos_apellido);
        cedula = (EditText) v.findViewById(R.id.edt_inquilinos_cedula);
        cuarto = (EditText) v.findViewById(R.id.edt_inquilinos_cuarto);
        monto = (EditText) v.findViewById(R.id.edt_inquilinos_monto);
        //fechaRegistrado = (EditText) v.findViewById(R.id.edt_inquilinos_fechaRegist);
        creationDate = (TextView) v.findViewById(R.id.lbl_todo_creation_date);
        done = (Button) v.findViewById(R.id.btn_done);



        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabcreate);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Si estas seguro solo has click en (LISTO) ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (callback != null) {
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        try{
            content.setText(inqui.getTodoContent());
            creationDate.setText(getDate());
            nombre.setText(inqui.getNombre());
            apellido.setText(inqui.getApellido());
            cedula.setText(inqui.getCedula());
            cuarto.setText(inqui.getCuarto());
            monto.setText(inqui.getMonto());



            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        Integer cuar = Integer.parseInt(cuarto.getText().toString());
                        String nomb = nombre.getText().toString(), ape = apellido.getText().toString(), mont = monto.getText().toString(), ced = cedula.getText().toString() ;

                        if (cuar <= 12 ) {
                            if (nomb.matches("") || ape.matches("") || mont.matches("") || ced.matches("") || cuar.equals(null)) {
                                Log.e("ERROR AC", "NO PUEDES DEJAR CAMPOS EN BLANCO");
                                callback.ToastBOOM("LLENA TODAS LAS CASILLAS");
                            } else {
                                cobro.settodoContent(content.getText().toString());
                                cobro.setNombre(nombre.getText().toString());
                                cobro.setApellido(apellido.getText().toString());
                                cobro.setCedula(cedula.getText().toString());
                                cobro.setCuarto(cuarto.getText().toString());
                                cobro.setMonto(monto.getText().toString());
                                callback.onDoneButtonClickAC(cobro);

                            }
                        }else{
                            Log.e("ERROR AC 12","NO PUEDE SER MAYOR DE 12");
                            callback.ToastBOOM("NO PUEDE SER MAYOR DE 12");
                            cuarto.setText("");
                        }
                   }catch (Exception E){
                        Log.e("--ERROR DONE AC---",E.getMessage());
                        callback.ToastBOOM("ERROR DONE AC" + E.getMessage());
                    }
                }
            });

            super.onActivityCreated(savedInstanceState);
        }catch (Exception E){
            Log.e("---ERROR EDIT FC-----","YA EXISTE"+ E.getMessage());
            callback.ToastBOOM("ERROR GRAVE EN CREACION DE LA ACTIVITY");
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentFormatoAutomaticCobroCallback) {
            callback = (FragmentFormatoAutomaticCobroCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public String getDate() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd-HH-mm-ss");
        String formattedDate = format.format(date);
        return formattedDate;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public interface FragmentFormatoAutomaticCobroCallback {
        void onDoneButtonClickAC(Cobros cobro);
        void ToastBOOM(String texto);
    }
}
