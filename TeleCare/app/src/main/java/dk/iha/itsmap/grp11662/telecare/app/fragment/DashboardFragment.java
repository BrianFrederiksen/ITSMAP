package dk.iha.itsmap.grp11662.telecare.app.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dk.iha.itsmap.grp11662.telecare.app.view.CircularImageView;
import dk.iha.itsmap.grp11662.telecare.app.MainActivity;
import dk.iha.itsmap.grp11662.telecare.app.R;
import dk.iha.itsmap.grp11662.telecare.app.model.User;

public class DashboardFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private User mUser;

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
        mUser = ((MainActivity) getActivity()).getUser();

        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textWelcome = (TextView) getActivity().findViewById(R.id.textWelcome);
        textWelcome.setText("Welcome " + mUser.getFirstname() + "!");

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
