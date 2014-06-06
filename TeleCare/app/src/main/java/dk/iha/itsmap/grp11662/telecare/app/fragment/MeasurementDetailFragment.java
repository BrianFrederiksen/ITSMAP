package dk.iha.itsmap.grp11662.telecare.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import dk.iha.itsmap.grp11662.telecare.app.R;
import dk.iha.itsmap.grp11662.telecare.app.model.Measurement;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MeasurementDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MeasurementDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeasurementDetailFragment extends Fragment {

    public MeasurementDetailFragment() {
    }//Constructor must remain empty because its a fragment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Measurement measurementToDisplay = (Measurement) getArguments().get("measurement");

        View rootView = inflater.inflate(R.layout.fragment_measurement_detail, container, false);
        rootView = SetMeasurement(rootView, measurementToDisplay);

        return rootView;
    }

    public View SetMeasurement(View view, Measurement measurementToDisplay){

        ((TextView)view.findViewById(R.id.detailWeightValue)).setText(measurementToDisplay.getWeight());
        ((TextView)view.findViewById(R.id.detailTempValue)).setText(measurementToDisplay.getTemperature());
        ((TextView)view.findViewById(R.id.detailDbpValue)).setText(measurementToDisplay.getdBP());
        ((TextView)view.findViewById(R.id.detailSbpValue)).setText(measurementToDisplay.getsBP());
        ((TextView)view.findViewById(R.id.detailGlucoseValue)).setText(measurementToDisplay.getBloodGlucose());
        ((TextView)view.findViewById(R.id.detailCommentsValue)).setText(measurementToDisplay.getComments());
        ((TextView)view.findViewById(R.id.detailDateValue)).setText(measurementToDisplay.getDate());
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


