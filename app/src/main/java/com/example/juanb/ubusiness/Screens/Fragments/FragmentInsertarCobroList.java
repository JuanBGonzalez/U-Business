package com.example.juanb.ubusiness.Screens.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.juanb.ubusiness.R;
import com.example.juanb.ubusiness.adapters.CobrosAdapter;
import com.example.juanb.ubusiness.models.objects.Cobros;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 } interface
 * to handle interaction events.
 * Use the {@link FragmentInsertarCobroList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentInsertarCobroList extends Fragment {
    private static final String LIST_DATA2 = "LIST_DATA2";

    private ArrayList<Cobros> listData2;
    private FragmentItemClickCallback2 callback;
    private RecyclerView todoList;
    private Button btn_add_inquilinos;

    public FragmentInsertarCobroList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment FragmentInquilinosList.
     * @param listData
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentInsertarCobroList newInstance(ArrayList<Cobros> listData) {
        FragmentInsertarCobroList fragment = new FragmentInsertarCobroList();
        Bundle args = new Bundle();
        args.putParcelableArrayList(LIST_DATA2, listData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.listData2 = getArguments().getParcelableArrayList(LIST_DATA2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_insertar_cobro_list, container, false);
        btn_add_inquilinos = (Button) v.findViewById(R.id.btn_add_inqui);
        todoList = (RecyclerView)v.findViewById(R.id.lst_todos2);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fablist);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Puedes BORRAR los Inquilinos moviendolos al lado derecho o izquierdo", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return v;
    }
/*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            CobrosAdapter adapter = new CobrosAdapter(listData2, getActivity());
            todoList.setAdapter(adapter);
            adapter.setItemClickCallback(new CobrosAdapter.ItemClickCallback() {
                @Override
                public void onItemClick(int p) {
                    callback.onListItemClickedCL(p);
                }
            });
            todoList.setLayoutManager(new LinearLayoutManager(getActivity()));

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper((createHelperCallback()));
            itemTouchHelper.attachToRecyclerView(todoList);

            btn_add_inquilinos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callback != null) {
                        callback.onAddCobrosButtonClickedCL();
                    }
                }
            });


            super.onActivityCreated(savedInstanceState);
        }catch(Exception E){
            Log.e("ERRORRRRRRRRR------",E.getMessage());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentItemClickCallback2) {
            callback = (FragmentItemClickCallback2) context;
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
  /*  public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            //not used, as the first parameter above is 0
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                callback.onListItemSwipedCL(viewHolder.getAdapterPosition());
                callback.ToastBOOM("BORRADO");
            }
        };
        return simpleItemTouchCallback;
    }


    public interface FragmentItemClickCallback2 {
        // TODO: Update argument type and name
        void onListItemSwipedCL(int position);
        void onListItemClickedCL(int position);
        void onAddCobrosButtonClickedCL();
        void ToastBOOM(String texto);
        void ToastBOOMLARGE(String texto);


    }
}
