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
import com.example.juanb.ubusiness.models.objects.Inquilinos;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentCreateOrUpdate.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentCreateOrUpdate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCreateOrUpdate extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String INQUILINOS = "INQUILINOS";

    private Inquilinos inqui;
    private EditText content, nombre, apellido,cedula,cuarto,monto,diapago;
    private TextView creationDate;
    private Button done;
    private FragmentUpdateOrCreateTodoCallback callback;

    // TODO: Rename and change types of parameters


    private OnFragmentInteractionListener mListener;

    public FragmentCreateOrUpdate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment FragmentCreateOrUpdate.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCreateOrUpdate newInstance(Inquilinos inqui) {
        FragmentCreateOrUpdate fragment = new FragmentCreateOrUpdate();
        Bundle args = new Bundle();
        args.putParcelable(INQUILINOS, inqui);
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentCreateOrUpdate newInstance() {
        FragmentCreateOrUpdate fragment = new FragmentCreateOrUpdate();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.inqui = getArguments().getParcelable(INQUILINOS);
        } else {
            inqui = new Inquilinos("", getDate(), "","" , "","","","");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setRetainInstance(true);
        View v = inflater.inflate(R.layout.fragment_fragment_create_or_update, container, false);
        content = (EditText) v.findViewById(R.id.edt_inquilinos_content);
        nombre = (EditText) v.findViewById(R.id.edt_inquilinos_nombre);
        apellido = (EditText) v.findViewById(R.id.edt_inquilinos_apellido);
        cedula = (EditText) v.findViewById(R.id.edt_inquilinos_cedula);
        cuarto = (EditText) v.findViewById(R.id.edt_inquilinos_cuarto);
        monto = (EditText) v.findViewById(R.id.edt_inquilinos_monto);
        diapago = (EditText) v.findViewById(R.id.edt_inquilinos_diapago);
        creationDate = (TextView) v.findViewById(R.id.lbl_todo_creation_date);
        done = (Button) v.findViewById(R.id.btn_done);



        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabcreate);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "No podras insertar: Si esta ocupado el cuarto, o cedulas clonadas.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }


    }



    public void onActivityCreated(Bundle savedInstanceState) {
        try{
        content.setText(inqui.getTodoContent());
        creationDate.setText(inqui.getTodoCreationDate());
        nombre.setText(inqui.getNombre());
        apellido.setText(inqui.getApellido());
        cedula.setText(inqui.getCedula());
        cuarto.setText(inqui.getCuarto());
        monto.setText(inqui.getMonto());
        diapago.setText(inqui.getDiapago());

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                Integer cuar = Integer.parseInt(cuarto.getText().toString());
                String nomb = nombre.getText().toString(), ape = apellido.getText().toString(), diap =diapago.getText().toString(), mont = monto.getText().toString(), ced = cedula.getText().toString() ;

                if (cuar <= 12 ) {
                    if (nomb.matches("")  || ape.matches("") || diap.matches("") || mont.matches("") || ced.matches("") || cuar.equals(null)) {
                        Log.d("ERROR","NO PUEDES DEJAR CAMPOS EN BLANCO");
                        callback.ToastBOOM("LLENA TODAS LAS CASILLAS");
                    }else {
                        inqui.setTodoContent(content.getText().toString());
                        inqui.setNombre(nombre.getText().toString());
                        inqui.setApellido(apellido.getText().toString());
                        inqui.setCedula(cedula.getText().toString());
                        inqui.setCuarto(cuarto.getText().toString());
                        inqui.setMonto(monto.getText().toString());
                        inqui.setDiapago(diapago.getText().toString());
                        callback.onDoneButtonClickUOC(inqui);
                }
                }else{
                    Log.d("ERROR","NO PUEDE SER MAYOR DE 12");
                    callback.ToastBOOM("NO PUEDE SER MAYOR DE 12");
                    cuarto.setText("");
                }
                }catch (Exception E){
                    Log.d("ERROR LISTO","ALGO ESTAS HACIENDO MAL INSERTANDO");
                    callback.ToastBOOM("ERROR INSERTANDO INQUILINO" + E.getMessage());
                }
            }
        });

        super.onActivityCreated(savedInstanceState);
        }catch (Exception E){
            Log.d("ERROR ACTIVITY INSERTAR","YA EXISTE");
            callback.ToastBOOM("ERROR ACTIVITY"+ E.getMessage());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentUpdateOrCreateTodoCallback) {
            callback = (FragmentUpdateOrCreateTodoCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement callback");
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


    public interface FragmentUpdateOrCreateTodoCallback {
        void onDoneButtonClickUOC(Inquilinos inquilinos);
        void ToastBOOM(String texto);
    }
}
