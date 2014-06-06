package dk.iha.itsmap.grp11662.telecare.app.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import dk.iha.itsmap.grp11662.telecare.app.MainActivity;
import dk.iha.itsmap.grp11662.telecare.app.MyMeasurementArrayAdapter;
import dk.iha.itsmap.grp11662.telecare.app.R;
import dk.iha.itsmap.grp11662.telecare.app.database.TeleCareDataSource;
import dk.iha.itsmap.grp11662.telecare.app.database.TeleCareDbOpenHelper;
import dk.iha.itsmap.grp11662.telecare.app.model.Measurement;
import dk.iha.itsmap.grp11662.telecare.app.model.User;


public class MeasurementsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int mSectionNumber;
    ArrayList<Measurement> measurements;
    TeleCareDbOpenHelper dbHelper;
    TeleCareDataSource datasource;
    User currentUser;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        super.onCreate(savedInstanceState);
    }

    public static MeasurementsFragment newInstance(int sectionNumber) {
        MeasurementsFragment fragment = new MeasurementsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MeasurementsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_measurements, null);

        // Getting user measurements
        context = getActivity().getApplicationContext();
        dbHelper = new TeleCareDbOpenHelper(context);
        datasource = new TeleCareDataSource(context);
        datasource.Open();
        MainActivity mainActivity = (MainActivity) getActivity();
        currentUser = mainActivity.getUser();
        measurements = datasource.findAllUserMeasurements(currentUser.getId());

        final ListView list =(ListView)v.findViewById(R.id.lswMeasurements);

        list.setAdapter(new MyMeasurementArrayAdapter(measurements, getActivity()));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Measurement measurement = (Measurement) list.getItemAtPosition(i);

                Bundle arguments = new Bundle();
                arguments.putParcelable("measurement", measurement);
                MeasurementDetailFragment detailMeasurement = new MeasurementDetailFragment();
                detailMeasurement.setArguments(arguments);

                getFragmentManager().beginTransaction()
                        .replace(R.id.container, detailMeasurement).addToBackStack(null)
                        .commit();
                /*MyMeasurements myMeasurements = new MyMeasurements();
                //getFragmentManager().findFragmentById()
                getFragmentManager().beginTransaction().remove(myMeasurements);
                getFragmentManager().beginTransaction().add(R.id.container, newMeasurement);*/
            }
        });
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public LinearLayout CreateLinearLayout(){
        LinearLayout measurementList = new LinearLayout(getActivity());
        LinearLayout measurement = new LinearLayout(getActivity());

        LinearLayout.LayoutParams layoutList = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.FILL_PARENT);
        LinearLayout.LayoutParams layoutMeasurement = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        measurementList.setLayoutParams(layoutList);
        measurement.setLayoutParams(layoutMeasurement);

        EditText editText = new EditText(getActivity());
        TextView textView = new TextView(getActivity());

        editText.setText("Type Weight");
        editText.setLayoutParams(layoutMeasurement);

        textView.setText("kg");
        textView.setLayoutParams(layoutMeasurement);

        measurement.addView(editText);
        measurement.addView(textView);

        measurementList.addView(measurement);
        measurementList.addView(measurement,0);

        return measurementList;
    }
}