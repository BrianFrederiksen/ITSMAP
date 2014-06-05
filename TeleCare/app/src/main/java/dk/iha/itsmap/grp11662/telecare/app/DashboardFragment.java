package dk.iha.itsmap.grp11662.telecare.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import dk.iha.itsmap.grp11662.telecare.app.connectivity.SipHandler;
import dk.iha.itsmap.grp11662.telecare.app.fragment.CallFragment;
import dk.iha.itsmap.grp11662.telecare.app.fragment.MeasurementsFragment;

public class DashboardFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";


    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance(int sectionNumber) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber );
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CircularImageView callImageShortcut = (CircularImageView) getActivity().
                findViewById(R.id.imageview_dash_circular_dr);

        getFragmentManager().beginTransaction().add(R.id.dashMeasureContainer,
                MeasurementsFragment.newInstance(1)).commit();

        callImageShortcut.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                getFragmentManager().beginTransaction().replace(R.id.container,
                        CallFragment.newInstance(4)).commit();
                return false;
            }
        });
    }
}
