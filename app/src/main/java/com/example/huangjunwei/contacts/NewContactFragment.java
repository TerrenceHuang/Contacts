package com.example.huangjunwei.contacts;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewContactFragment extends Fragment {

    OnNewContactAddListener mCallback;

    public static final String NAME = "NewContactFragment";
    public static final String CONTACT_NAME = "CONTACT_NAME";
    public static final String CONTACT_PHONE = "CONTACT_PHONE";
    public static final String CONTACT_NOTE = "CONTACT_NOTE";

    private MyDatas.PersonalData personalData = new MyDatas.PersonalData();

    public NewContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_contact, container, false);
        Bundle bundle = this.getArguments();
        ((EditText)view.findViewById(R.id.contact_name_edittext)).setText(bundle.getString(CONTACT_NAME));
        ((EditText)view.findViewById(R.id.contact_phone_edittext)).setText(bundle.getString(CONTACT_PHONE));
        ((EditText)view.findViewById(R.id.contact_note_edittext)).setText(bundle.getString(CONTACT_NOTE));

        view.findViewById(R.id.add_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact(v);
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        savePersonalData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnNewContactAddListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnNewContactAddListener");
        }
    }

    public MyDatas.PersonalData getPersonalData() {
        return personalData;
    }

    public void addContact(View view) {

        savePersonalData();
        MyDatas.addPersonalData(personalData);
        Toast.makeText(getContext(), "Add new contact", Toast.LENGTH_LONG).show();
        mCallback.onNewContact();
    }

    private void savePersonalData() {

        if (getActivity().findViewById(R.id.contact_name_edittext) != null) {
            personalData.name = ((EditText) getActivity().findViewById(R.id.contact_name_edittext)).getText().toString();
            personalData.telephone = ((EditText) getActivity().findViewById(R.id.contact_phone_edittext)).getText().toString();
            personalData.note = ((EditText) getActivity().findViewById(R.id.contact_note_edittext)).getText().toString();
        }
    }

    public interface OnNewContactAddListener {

        void onNewContact();
    }
}
