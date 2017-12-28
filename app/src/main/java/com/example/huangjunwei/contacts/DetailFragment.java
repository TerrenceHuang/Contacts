package com.example.huangjunwei.contacts;


import android.content.Intent;
import android.net.Uri;
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
public class DetailFragment extends Fragment {

    public static final String INDEX = "INDEX";

    private MyDatas.PersonalData personalData;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        int index = bundle.getInt(INDEX);
        personalData = MyDatas.PERSONAL_DATAS.get(index);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ((EditText)view.findViewById(R.id.contact_name_edittext)).setText(personalData.name);
        ((EditText)view.findViewById(R.id.contact_phone_edittext)).setText(personalData.telephone);
        ((EditText)view.findViewById(R.id.contact_note_edittext)).setText(personalData.note);
        view.findViewById(R.id.update_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContact(v);
            }
        });
        view.findViewById(R.id.call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(v);
            }
        });
        return view;
    }

    private void updateContact(View view) {

        personalData.name = ((EditText)getActivity().findViewById(R.id.contact_name_edittext)).getText().toString();
        personalData.telephone = ((EditText)getActivity().findViewById(R.id.contact_phone_edittext)).getText().toString();
        personalData.note = ((EditText)getActivity().findViewById(R.id.contact_note_edittext)).getText().toString();
        Toast.makeText(getContext(), "Contact update", Toast.LENGTH_LONG).show();
    }

    private void call(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + personalData.telephone));
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
