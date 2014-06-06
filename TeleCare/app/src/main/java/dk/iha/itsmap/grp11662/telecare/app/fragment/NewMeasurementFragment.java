package dk.iha.itsmap.grp11662.telecare.app.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import dk.iha.itsmap.grp11662.telecare.app.MainActivity;
import dk.iha.itsmap.grp11662.telecare.app.R;
import dk.iha.itsmap.grp11662.telecare.app.database.TeleCareDataSource;
import dk.iha.itsmap.grp11662.telecare.app.database.TeleCareDbOpenHelper;
import dk.iha.itsmap.grp11662.telecare.app.model.Measurement;
import dk.iha.itsmap.grp11662.telecare.app.model.User;

public class NewMeasurementFragment extends Fragment {

    Measurement measurement;
    TeleCareDbOpenHelper dbHelper;
    TeleCareDataSource datasource;
    User currentUser;
    Context context;
    Date currentDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_new_measurement, container, false);
        // Getting current user
        context = getActivity().getApplicationContext();
        dbHelper = new TeleCareDbOpenHelper(context);
        datasource = new TeleCareDataSource(context);
        datasource.Open();
        MainActivity mainActivity = (MainActivity) getActivity();
        currentUser = mainActivity.getUser();
        currentDay = new Date();
        ((TextView)rootView.findViewById(R.id.newDateValue)).setText(currentDay.toString());

        Button buttonSaveMeasurement = (Button) rootView.findViewById(R.id.saveNewMeasurement);
        buttonSaveMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                measurement = new Measurement();
                measurement.setWeight( ((EditText)getActivity().findViewById(R.id.newWeightValue)).getText().toString());
                measurement.setTemperature(((EditText) getActivity().findViewById(R.id.newTempValue)).getText().toString());
                measurement.setBloodGlucose(((EditText) getActivity().findViewById(R.id.newGlucoseValue)).getText().toString());
                measurement.setdBP(((EditText) getActivity().findViewById(R.id.newDbpValue)).getText().toString()) ;
                measurement.setsBP(((EditText) getActivity().findViewById(R.id.newSbpValue)).getText().toString());
                measurement.setComments(((EditText) getActivity().findViewById(R.id.newCommentsValue)).getText().toString());
                measurement.setUserId(currentUser.getId());
                measurement.setDate(currentDay.toString());
                measurement = datasource.createMeasurement(measurement, currentUser);

                MeasurementsFragment measurementsFragment = new MeasurementsFragment();

                getFragmentManager().beginTransaction()
                        .replace(R.id.container, measurementsFragment).commit();

                Toast toast = Toast.makeText(context, "Measurement created", Toast.LENGTH_SHORT);
                toast.show();

                getFragmentManager().beginTransaction().replace(R.id.container,new MeasurementsFragment()).commit();
            }
        });

        Button buttonDiscardButton = (Button) rootView.findViewById(R.id.discardNewMeasurement);
        buttonDiscardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container,new MeasurementsFragment()).commit();
            }
        });

        return  rootView;
    }


}
