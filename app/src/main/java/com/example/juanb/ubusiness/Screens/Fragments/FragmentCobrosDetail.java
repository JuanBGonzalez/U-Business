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
import com.example.juanb.ubusiness.models.objects.Cobros;

/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link FragmentCobrosDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCobrosDetail extends Fragment {
    private static final String COBROS = "COBROS";

    private FragmentEditItemCallback callback;
    private TextView content, creationDate,nombre,apellido,cedula,cuarto,monto,fecha;
    private Button editTodo;
    private Cobros cobro;

    public FragmentCobrosDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentCobrosDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCobrosDetail newInstance(Cobros cobro) {
        FragmentCobrosDetail fragment = new FragmentCobrosDetail();
        Bundle args = new Bundle();
        args.putParcelable(COBROS, cobro);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.cobro = getArguments().getParcelable(COBROS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_cobros_detail, container, false);
        content = (TextView) v.findViewById(R.id.lbl_inquilino_content);
        //creationDate = (TextView) v.findViewById(R.id.lbl_inquilino_creation_date);
        nombre = (TextView) v.findViewById(R.id.lbl_inquilinos_nombre);
        apellido = (TextView) v.findViewById(R.id.lbl_inquilinos_apellido);
        cedula = (TextView) v.findViewById(R.id.lbl_inquilinos_cedula);
        cuarto = (TextView) v.findViewById(R.id.lbl_inquilinos_cuarto);
        monto = (TextView) v.findViewById(R.id.lbl_inquilinos_monto);
        fecha = (TextView) v.findViewById(R.id.lbl_inquilinos_diapago);
        editTodo = (Button) v.findViewById(R.id.btn_edit_todo);

        return v;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        content.setText(cobro.gettodoContent());
        //creationDate.setText(cobro.getTodoCreationDate());
        nombre.setText(cobro.getNombre());
        apellido.setText(cobro.getApellido());
        cedula.setText(cobro.getCedula());
        cuarto.setText("# "+cobro.getCuarto());
        monto.setText("B/."+cobro.getMonto());
        fecha.setText(cobro.getTodoCreationDate());
        editTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onEditButtonClickCD(cobro);
            }
        });
        super.onActivityCreated(savedInstanceState);
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
        void onEditButtonClickCD(Cobros cobros);
    }
}
