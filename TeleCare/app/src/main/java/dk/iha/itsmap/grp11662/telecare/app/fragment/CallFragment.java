package dk.iha.itsmap.grp11662.telecare.app.fragment;

import android.app.Activity;
import android.net.Uri;
import android.net.sip.SipAudioCall;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import dk.iha.itsmap.grp11662.telecare.app.MainActivity;
import dk.iha.itsmap.grp11662.telecare.app.R;
import dk.iha.itsmap.grp11662.telecare.app.connectivity.SipHandler;

public class CallFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION_NUMBER = "section_number";
    private SipHandler sipHandler;
    private MainActivity mainActivity;
    private Boolean isCallActive = false;
    private SipAudioCall mAudioCall;

    public static CallFragment newInstance(int sectionNumber) {
        CallFragment fragment = new CallFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber );
        fragment.setArguments(args);
        return fragment;
    }


    public CallFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainActivity = (MainActivity) this.getActivity();
        sipHandler = new SipHandler(mainActivity, mainActivity.getUser());
        try {
            sipHandler.openSipProfile();
        } catch (Exception e) {
            Toast.makeText(mainActivity,"Unable to open SIP profile", Toast.LENGTH_LONG ).show();
        }

        return inflater.inflate(R.layout.fragment_call, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupCallButton((Button) mainActivity.findViewById(R.id.call_button_placeholder));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            sipHandler.closeSipProfile();
        } catch (Exception e) {
            Toast.makeText(mainActivity,"Unable to close SIP profile", Toast.LENGTH_LONG ).show();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }


    /**
     * GUI Setup
     */
    private void setupCallButton(final Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isCallActive) {
                    try{
                        mAudioCall = sipHandler.MakeAudioCall(mainActivity.getUser().getDoctorUsername(), 30);
                        button.setBackgroundColor(getResources().getColor(R.color.Color_HangUp_CallButton));
                        isCallActive = true;
                    } catch (Exception e) {
                        Toast.makeText(mainActivity,"Unable to call doctor", Toast.LENGTH_LONG ).show();
                    }
                } else {
                    try {
                        mAudioCall.endCall();
                        mAudioCall.close();
                        isCallActive = false;
                    } catch (Exception e) {
                        Toast.makeText(mainActivity,"Something went wrong with SIP", Toast.LENGTH_LONG ).show();
                    }

                }
            }
        });
    }
}
