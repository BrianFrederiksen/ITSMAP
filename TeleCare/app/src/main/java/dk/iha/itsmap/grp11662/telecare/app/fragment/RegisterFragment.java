package dk.iha.itsmap.grp11662.telecare.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import dk.iha.itsmap.grp11662.telecare.app.LoginActivity;
import dk.iha.itsmap.grp11662.telecare.app.MainActivity;
import dk.iha.itsmap.grp11662.telecare.app.R;
import dk.iha.itsmap.grp11662.telecare.app.database.TeleCareDataSource;
import dk.iha.itsmap.grp11662.telecare.app.model.Measurement;
import dk.iha.itsmap.grp11662.telecare.app.model.User;

public class RegisterFragment extends Fragment {
    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();

        return fragment;
    }
    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Button buttonRegisterCancel = (Button) getActivity().findViewById(R.id.button_register_cancel);
        buttonRegisterCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Button buttonRegisterSave = (Button) getActivity().findViewById(R.id.button_register_save);
        buttonRegisterSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) getActivity().findViewById(R.id.edit_register_email)).getText().toString();
                String password = ((EditText) getActivity().findViewById(R.id.edit_register_password)).getText().toString();
                String firstName = ((EditText) getActivity().findViewById(R.id.edit_register_firstname)).getText().toString();
                String surName = ((EditText) getActivity().findViewById(R.id.edit_register_surname)).getText().toString();
                String doctor = ((EditText) getActivity().findViewById(R.id.edit_register_doctor)).getText().toString();
                String sipAddress = ((EditText) getActivity().findViewById(R.id.edit_register_sipaddress)).getText().toString();

                if( email.trim().equals("") || password.trim().equals("") || firstName.trim().equals("") || surName.trim().equals("")
                        || doctor.trim().equals("") || sipAddress.trim().equals("")) {
                    Toast.makeText(getActivity(),"Fill out all fields!", Toast.LENGTH_LONG).show();
                } else {
                    TeleCareDataSource dataSource = new TeleCareDataSource(getActivity().getApplicationContext());
                    dataSource.Open();

                    User user = dataSource.createUser(new User(email, firstName, surName, password, new ArrayList<Measurement>(),
                            sipAddress, doctor));

                    Toast.makeText(getActivity(),"Welcome to Telecare, " + firstName + ".\n Registration complete.",
                            Toast.LENGTH_LONG).show();

                    Intent mainIntent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("user", user);
                    mainIntent.putExtras(bundle);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainIntent);

                }
            }
        });
    }
}
