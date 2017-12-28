package com.example.huangjunwei.contacts;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    OnMenuSelectedListener mCallback;

    public static final String NAME = "MenuFragment";
    public static final String[] FUNCTIONS = {"ADD_CONTACT", "CONTACTS"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        view.findViewById(R.id.add_person_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPersonButtonClick(v);
            }
        });

        view.findViewById(R.id.contacts_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactsButtonClick(v);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnMenuSelectedListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnMenuSelectedListener");
        }
    }


    public void addPersonButtonClick(View view) {
        Log.d(NAME, "addPersonButtonClick");

        mCallback.onMenuSelected(0);
    }

    public void contactsButtonClick(View view) {
        Log.d(NAME, "contactsButtonClick");

        mCallback.onMenuSelected(1);
    }

    public interface OnMenuSelectedListener {
        void onMenuSelected(int position);
    }
}
