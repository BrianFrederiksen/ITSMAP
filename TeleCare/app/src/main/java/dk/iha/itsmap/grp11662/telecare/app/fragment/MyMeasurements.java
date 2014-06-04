package dk.iha.itsmap.grp11662.telecare.app.fragment;

import android.app.Activity;
import android.app.Fragment;
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
import dk.iha.itsmap.grp11662.telecare.app.model.Measurement;


public class MyMeasurements extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int mSectionNumber;
    ArrayList<Measurement> measurements;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        super.onCreate(savedInstanceState);
    }

    public static MyMeasurements newInstance(int sectionNumber) {
        MyMeasurements fragment = new MyMeasurements();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MyMeasurements() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_measurements, null);

        //Should be done outside this class
        Measurement testMeasurement1 = new Measurement("75","38","34","2","1","BAM BAM Comments","4/3-2013");
        Measurement testMeasurement2 = new Measurement("65","28","24","1","0,5","BAM", "5/9 2012");

        ArrayList<Measurement> measurementsToShowInList = new ArrayList<>();
        measurementsToShowInList.add(testMeasurement1);
        measurementsToShowInList.add(testMeasurement2);
        //Endof Should be done outside tis class

        PutMeasurements(measurementsToShowInList);

        final ListView list =(ListView)v.findViewById(R.id.lswMeasurements);

        list.setAdapter(new MyMeasurementArrayAdapter(measurements, getActivity()));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Measurement measurement = (Measurement) list.getItemAtPosition(i);

                Bundle arguments = new Bundle();
                arguments.putParcelable("measurements", measurement);

                NewMeasurement newMeasurement = new NewMeasurement();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, newMeasurement).addToBackStack(null)
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

    public void PutMeasurements(ArrayList<Measurement> measurementItems){
        measurements = measurementItems;
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