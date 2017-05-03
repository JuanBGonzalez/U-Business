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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link FragmentInquilinosDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentInquilinosDetail extends Fragment {
    private static final String INQUILINOS = "INQUILINOS";

    private FragmentEditItemCallback callback;
    private TextView content, creationDate,nombre,apellido,cedula,cuarto,monto,diapago;
    private Button editTodo;
    private Inquilinos inquilinos;

    public FragmentInquilinosDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment FragmentInquilinosDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentInquilinosDetail newInstance(Inquilinos inquilinos) {
        FragmentInquilinosDetail fragment = new FragmentInquilinosDetail();
        Bundle args = new Bundle();
        args.putParcelable(INQUILINOS, inquilinos);
        fragment.setArguments(args);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_inquilinos_detail, container, false);
        content = (TextView) v.findViewById(R.id.lbl_inquilino_content);
        creationDate = (TextView) v.findViewById(R.id.lbl_inquilino_creation_date);
        nombre = (TextView) v.findViewById(R.id.lbl_inquilinos_nombre);
        apellido = (TextView) v.findViewById(R.id.lbl_inquilinos_apellido);
        cedula = (TextView) v.findViewById(R.id.lbl_inquilinos_cedula);
        cuarto = (TextView) v.findViewById(R.id.lbl_inquilinos_cuarto);
        monto = (TextView) v.findViewById(R.id.lbl_inquilinos_monto);
        diapago = (TextView) v.findViewById(R.id.lbl_inquilinos_diapago);
        editTodo = (Button) v.findViewById(R.id.btn_edit_todo);

        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        content.setText(inquilinos.getTodoContent());
        creationDate.setText(inquilinos.getTodoCreationDate());
        nombre.setText(inquilinos.getNombre());
        apellido.setText(inquilinos.getApellido());
        cedula.setText(inquilinos.getCedula());
        cuarto.setText("# "+inquilinos.getCuarto());
        monto.setText("B/."+inquilinos.getMonto()+" $");
        diapago.setText("Los dias "+inquilinos.getDiapago()+" de cada mes");

        editTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onEditButtonClick(inquilinos);
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    // TODO: Rename method, update argument and hook method into UI event
  /*  public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

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
        void onEditButtonClick(Inquilinos inquilinos);
    }
}
