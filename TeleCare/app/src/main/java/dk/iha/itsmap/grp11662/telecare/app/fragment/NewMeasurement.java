package dk.iha.itsmap.grp11662.telecare.app.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import dk.iha.itsmap.grp11662.telecare.app.MainActivity;
import dk.iha.itsmap.grp11662.telecare.app.R;
import dk.iha.itsmap.grp11662.telecare.app.model.Measurement;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewMeasurement.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewMeasurement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewMeasurement extends Fragment {

    public NewMeasurement() {
    }//Constructor must remain empty because its a fragment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Measurement measurementToDisplay = new Measurement("75","38","34","2","1","BAM BAM Comments","Date");

        View rootView = inflater.inflate(R.layout.fragment_new_measurement, container, false);
        rootView = SetMeasurement(rootView, measurementToDisplay);

        return rootView;
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


