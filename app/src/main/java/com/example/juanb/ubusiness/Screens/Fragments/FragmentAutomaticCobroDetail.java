package com.example.juanb.ubusiness.Screens.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.juanb.ubusiness.R;
import com.example.juanb.ubusiness.models.objects.Inquilinos;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.

 * to handle interaction events.
 * Use the {@link FragmentAutomaticCobroDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAutomaticCobroDetail extends Fragment {
    private static final String INQUILINOS = "INQUILINOS";


    private FragmentEditItemCallback callback;
    private TextView content,nombre,apellido,cedula,cuarto,monto,fecha;
    private Button EditAC;
    private Inquilinos inquilinos;


    public FragmentAutomaticCobroDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment FragmentAutomaticCobroDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAutomaticCobroDetail newInstance(Inquilinos inquilinos) {
        FragmentAutomaticCobroDetail fragment = new FragmentAutomaticCobroDetail();
        Bundle args = new Bundle();
        args.putParcelable(INQUILINOS, inquilinos);
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentAutomaticCobroDetail newInstance() {
        FragmentAutomaticCobroDetail fragment = new FragmentAutomaticCobroDetail();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.inquilinos = getArguments().getParcelable(INQUILINOS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setRetainInstance(true);
        View v = inflater.inflate(R.layout.fragment_fragment_automatic_cobro_detail, container, false);
        content = (TextView) v.findViewById(R.id.lbl_inquilino_content);
        nombre = (TextView) v.findViewById(R.id.lbl_inquilinos_nombre);
        apellido = (TextView) v.findViewById(R.id.lbl_inquilinos_apellido);
        cedula = (TextView) v.findViewById(R.id.lbl_inquilinos_cedula);
        cuarto = (TextView) v.findViewById(R.id.lbl_inquilinos_cuarto);
        monto = (TextView) v.findViewById(R.id.lbl_inquilinos_monto);
        fecha = (TextView) v.findViewById(R.id.lbl_inquilinos_fechapago);
        EditAC = (Button) v.findViewById(R.id.btn_edit_todo);


        return v;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        content.setText(inquilinos.getTodoContent());
        nombre.setText(inquilinos.getNombre());
        apellido.setText(inquilinos.getApellido());
        cedula.setText(inquilinos.getCedula());
        cuarto.setText(inquilinos.getCuarto());
        monto.setText(inquilinos.getMonto());
        fecha.setText(getDate());

        EditAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onEditButtonClickAC(inquilinos);
            }
        });


        super.onActivityCreated(savedInstanceState);
    }


    public String getDate() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd-HH-mm-ss");
        String formattedDate = format.format(date);
        return formattedDate;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentEditItemCallback) {
            callback = (FragmentEditItemCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
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
    public interface FragmentEditItemCallback {
        void onEditButtonClickAC(Inquilinos inquilinos);
        void ToastBOOM(String texto);
    }
}
