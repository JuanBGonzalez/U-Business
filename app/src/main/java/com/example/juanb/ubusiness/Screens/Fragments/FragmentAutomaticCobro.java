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
import com.example.juanb.ubusiness.adapters.InquilinosAdapter;
import com.example.juanb.ubusiness.models.objects.Inquilinos;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link FragmentAutomaticCobro#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAutomaticCobro extends Fragment {
    private static final String LIST_DATA = "LIST_DATA";

    private ArrayList<Inquilinos> listData;
    private FragmentItemClickCallback callback;
    private RecyclerView todoList;
    private Button btn_add_inquilinos;

    public FragmentAutomaticCobro() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment FragmentAutomaticCobro.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAutomaticCobro newInstance(ArrayList<Inquilinos> listData) {
        FragmentAutomaticCobro fragment = new FragmentAutomaticCobro();
        Bundle args = new Bundle();
        args.putParcelableArrayList(LIST_DATA, listData);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.listData = getArguments().getParcelableArrayList(LIST_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_automatic_cobro, container, false);
        btn_add_inquilinos = (Button) v.findViewById(R.id.btn_add_inqui);
        todoList = (RecyclerView)v.findViewById(R.id.lst_todos);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fablist);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Aqui solamente tienes que hacer un click en la persona que ya pago", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return v;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            InquilinosAdapter adapter = new InquilinosAdapter(listData, getActivity());
            todoList.setAdapter(adapter);
            adapter.setItemClickCallback(new InquilinosAdapter.ItemClickCallback() {
                @Override
                public void onItemClick(int p) {
                    callback.onListItemClickedAC(p);
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
        if (context instanceof FragmentItemClickCallback) {
            callback = (FragmentItemClickCallback) context;
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
                //callback.onListItemSwipedAC(viewHolder.getAdapterPosition());
                callback.ToastBOOM("BORRADO");
            }
        };
        return simpleItemTouchCallback;
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

    public interface FragmentItemClickCallback {
        void onListItemClickedAC(int position);
        void onAddCobrosButtonClickedCL();
        void ToastBOOM(String texto);
        void ToastBOOMLARGE(String texto);
    }
}
