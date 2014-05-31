package dk.iha.itsmap.grp11662.telecare.app.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Measurement testMeasurement1 = new Measurement("75","38","34","2","1","BAM BAM Comments","4/3-2013");
        Measurement testMeasurement2 = new Measurement("65","28","24","1","0,5","BAM", "5/9 2012");
        ArrayList<Measurement> testList = new ArrayList<>();
        testList.add(testMeasurement1);
        testList.add(testMeasurement2);

        MyMeasurementArrayAdapter myMeasurementArrayAdapter = new MyMeasurementArrayAdapter(testList, getActivity());
        final ListView choiceMenuList = (ListView) getActivity().findViewById(R.id.lswMeasurements);
        choiceMenuList.setAdapter(myMeasurementArrayAdapter); //Der sker en fejl her
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
        return inflater.inflate(R.layout.fragment_my_measurements, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public View SetMeasurement(View view, Measurement measurementToDisplay){

        ((EditText)view.findViewById(R.id.etxWeight)).setText(measurementToDisplay.getWeight());
        ((EditText)view.findViewById(R.id.etxTemp)).setText(measurementToDisplay.getTemperature());
        ((EditText)view.findViewById(R.id.etxDBP)).setText(measurementToDisplay.getdBP());
        ((EditText)view.findViewById(R.id.etxSBP)).setText(measurementToDisplay.getsBP());
        ((EditText)view.findViewById(R.id.etxGlucose)).setText(measurementToDisplay.getBloodGlucose());
        ((EditText)view.findViewById(R.id.etxComments)).setText(measurementToDisplay.getComments());
        ((EditText)view.findViewById(R.id.etxDate)).setText(measurementToDisplay.getDate());
        return view;
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