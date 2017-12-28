package com.example.huangjunwei.contacts;

import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ContactsActivity extends AppCompatActivity
        implements MenuFragment.OnMenuSelectedListener, NewContactFragment.OnNewContactAddListener,
        ContactsFragment.OnContactSelectedListener {

    public static final String NAME = "ContactsActivity";

    private DetailEnum detailEnum = DetailEnum.empty;;
    private MyDatas.PersonalData addContactPersonalData = new MyDatas.PersonalData();
    private int selectedContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            switch (savedInstanceState.getString("detailEnum")) {
                case "empty":
                    detailEnum = DetailEnum.empty;
                    break;
                case "addContact":
                    detailEnum = DetailEnum.addContact;
                    break;
                case "contacts":
                    detailEnum = DetailEnum.contacts;
                    break;
                case "detail":
                    detailEnum = DetailEnum.detail;
                    break;
            }

            addContactPersonalData.name = savedInstanceState.getString(NewContactFragment.CONTACT_NAME);
            addContactPersonalData.telephone = savedInstanceState.getString(NewContactFragment.CONTACT_PHONE);
            addContactPersonalData.note = savedInstanceState.getString(NewContactFragment.CONTACT_NOTE);
        }

        setContentView(R.layout.activity_contacts);
        Log.d(NAME, "onCreate detailEnum: " + detailEnum);

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            showPortrait();
        }else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            if (getSupportFragmentManager().findFragmentById(R.id.menu_container) == null) {

                MenuFragment newFragment = new MenuFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.menu_container, newFragment).commit();
            }

            showLandscapeDetail();
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // mTextView.setText(savedInstanceState.getString(TEXT_VIEW_KEY));

        if (savedInstanceState == null)
            return;

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        outState.putString(GAME_STATE_KEY, mGameState);
//        outState.putString(TEXT_VIEW_KEY, mTextView.getText());

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);

        String myDetailEnum = "";
        switch (detailEnum) {
            case empty:
                myDetailEnum = "empty";
                break;
            case addContact:
                myDetailEnum = "addContact";
                break;
            case contacts:
                myDetailEnum = "contacts";
                break;
            case detail:
                myDetailEnum = "detail";
                break;
        }
        outState.putString("detailEnum", myDetailEnum);

        saveFragmentData();

        outState.putString(NewContactFragment.CONTACT_NAME, addContactPersonalData.name);
        outState.putString(NewContactFragment.CONTACT_PHONE, addContactPersonalData.telephone);
        outState.putString(NewContactFragment.CONTACT_NOTE, addContactPersonalData.note);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(NAME, "onDestroy detailEnum: ");
    }

    @Override
    public void onBackPressed() {

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            backPortrait();
        }else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            backLandscape();
        }
    }

    private void backLandscape() {

        switch (detailEnum) {
            case empty:

                finish();
                break;
            case addContact:

                showBackToEndToast();
                detailEnum = DetailEnum.empty;
                break;
            case contacts:

                showBackToEndToast();
                detailEnum = DetailEnum.empty;
                break;
            case detail:

                detailEnum = DetailEnum.contacts;
                break;
        }
        showLandscapeDetail();
    }

    private void backPortrait() {

        switch (detailEnum) {
            case empty:

                finish();
                break;
            case addContact:

                addContactPersonalData = new MyDatas.PersonalData();
                detailEnum = DetailEnum.empty;
                break;
            case contacts:

                detailEnum = DetailEnum.empty;
                break;
            case detail:

                detailEnum = DetailEnum.contacts;
                break;
        }
        showPortrait();
    }

    // fragments callback
    public void onMenuSelected(int position) {
        Log.d(NAME, "onMenuSelected position: " + MenuFragment.FUNCTIONS[position]);

        switch (position) {
            case 0:
                detailEnum = DetailEnum.addContact;
                break;
            case 1:
                detailEnum = DetailEnum.contacts;
                break;
        }

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            showPortrait();
        }else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            showLandscapeDetail();
        }
    }

    public void onNewContact() {

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            addContactPersonalData = new MyDatas.PersonalData();
            detailEnum = DetailEnum.empty;
            showPortrait();
        }else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            addContactPersonalData = new MyDatas.PersonalData();
            showLandscapeDetail();
        }
    }

    public void onContactSelected(int position) {

        selectedContact = position;
        detailEnum = DetailEnum.detail;
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            showPortrait();
        }else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            showLandscapeDetail();
        }
    }

    //

    private void saveFragmentData() {

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

            if (currentFragment instanceof NewContactFragment) {

                addContactPersonalData = ((NewContactFragment)currentFragment).getPersonalData();
            }
        }else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.detail_container);

            if (currentFragment instanceof NewContactFragment) {

                addContactPersonalData = ((NewContactFragment)currentFragment).getPersonalData();
            }
        }
    }

    private void showBackToEndToast() {

        Toast.makeText(this, "Press back again to end the app", Toast.LENGTH_LONG).show();
    }

    private void showLandscapeDetail() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.detail_container);
        Bundle bundle;
        switch (detailEnum) {
            case empty:

                if (currentFragment != null) {

                    getSupportFragmentManager().beginTransaction()
                            .remove(currentFragment).commit();
                }
                break;
            case addContact:

                bundle = new Bundle();
                bundle.putString(NewContactFragment.CONTACT_NAME, addContactPersonalData.name);
                bundle.putString(NewContactFragment.CONTACT_PHONE, addContactPersonalData.telephone);
                bundle.putString(NewContactFragment.CONTACT_NOTE, addContactPersonalData.note);
                NewContactFragment newContactFragment = new NewContactFragment();
                newContactFragment.setArguments(bundle);

                if (currentFragment == null) {

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.detail_container, newContactFragment).commit();
                }else {

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.detail_container, newContactFragment).commit();
                }
                break;
            case contacts:

                ContactsFragment contactsFragment = new ContactsFragment();
                if (getSupportFragmentManager().findFragmentById(R.id.detail_container) == null) {

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.detail_container, contactsFragment).commit();
                }else {
                    if (!(currentFragment instanceof ContactsFragment)) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.detail_container, contactsFragment).commit();
                    }
                }
                break;
            case detail:

                bundle = new Bundle();
                bundle.putInt(DetailFragment.INDEX, selectedContact);
                DetailFragment detailFragment = new DetailFragment();
                detailFragment.setArguments(bundle);

                if (currentFragment == null) {

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.detail_container, detailFragment).commit();
                }else {

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.detail_container, detailFragment).commit();
                }
                break;
        }
    }

    private void showPortrait() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        switch (detailEnum) {
            case empty:

                MenuFragment menuFragment = new MenuFragment();
                if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, menuFragment).commit();
                }else {
                    if (!(currentFragment instanceof MenuFragment)) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, menuFragment).commit();
                    }
                }
                break;
            case addContact:

                Bundle bundle = new Bundle();
                bundle.putString(NewContactFragment.CONTACT_NAME, addContactPersonalData.name);
                bundle.putString(NewContactFragment.CONTACT_PHONE, addContactPersonalData.telephone);
                bundle.putString(NewContactFragment.CONTACT_NOTE, addContactPersonalData.note);
                NewContactFragment newContactFragment = new NewContactFragment();
                newContactFragment.setArguments(bundle);

                if (currentFragment == null) {
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, newContactFragment).commit();
                }else {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, newContactFragment).commit();
                }
                break;
            case contacts:

                ContactsFragment contactsFragment = new ContactsFragment();
                if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, contactsFragment).commit();
                }else {
                    if (!(currentFragment instanceof ContactsFragment)) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, contactsFragment).commit();
                    }
                }
                break;
            case detail:

                bundle = new Bundle();
                bundle.putInt(DetailFragment.INDEX, selectedContact);
                DetailFragment detailFragment = new DetailFragment();
                detailFragment.setArguments(bundle);

                if (currentFragment == null) {

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, detailFragment).commit();
                }else {

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, detailFragment).commit();
                }
                break;
        }
    }

    private enum DetailEnum { empty, addContact, contacts, detail };
}
