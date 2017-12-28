package com.example.huangjunwei.contacts;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnContactSelectedListener} interface
 * to handle interaction events.
 */
public class ContactsFragment extends ListFragment {

    private OnContactSelectedListener mListener;

    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        ArrayList<String> pokemonNames = new ArrayList<>();
        for (MyDatas.PersonalData personalData : MyDatas.PERSONAL_DATAS) {
            pokemonNames.add(personalData.name);
        }

        setListAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, pokemonNames));

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnContactSelectedListener) {
            mListener = (OnContactSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnContactSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Log.d("TitleFragment", "onItemClick in");
        mListener.onContactSelected(position);
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
    public interface OnContactSelectedListener {
        // TODO: Update argument type and name
        void onContactSelected(int position);
    }
}
