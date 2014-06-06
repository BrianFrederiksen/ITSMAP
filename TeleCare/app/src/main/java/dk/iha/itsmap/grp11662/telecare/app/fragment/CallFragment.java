package dk.iha.itsmap.grp11662.telecare.app.fragment;

import android.app.Activity;
import android.net.sip.SipAudioCall;
import android.net.sip.SipProfile;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import dk.iha.itsmap.grp11662.telecare.app.CircularImageView;
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
    private boolean onSpeakerPhone = false;

    private SipAudioCall.Listener mAudioCallListener= new SipAudioCall.Listener() {
        @Override
        public void onRinging(SipAudioCall call, SipProfile caller) {
            super.onRinging(call, caller);


        }

        @Override
        public void onCallEstablished(SipAudioCall call) {

            if(onSpeakerPhone) {
                call.setSpeakerMode(true);
            } else {
                call.setSpeakerMode(false);
            }

            call.startAudio();
            if(call.isMuted()) {
                call.toggleMute();
            }
        }

        @Override
        public void onCallEnded(SipAudioCall call) {
            super.onCallEnded(call);
            call.close();
        }
    };


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
        Switch speakerSwitch = (Switch) mainActivity.findViewById(R.id.speakerSwitch);

        speakerSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!onSpeakerPhone) {
                    onSpeakerPhone = true;
                    mAudioCall.setSpeakerMode(true);
                    Toast.makeText(mainActivity, "SpeakerPhone activated!", Toast.LENGTH_SHORT).show();
                } else {
                    onSpeakerPhone = false;
                    mAudioCall.setSpeakerMode(false);
                    Toast.makeText(mainActivity, "SpeakerPhone deactivated!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                        mAudioCall = sipHandler.MakeAudioCall(mainActivity.getUser().getDoctorUsername(), 30, mAudioCallListener);
                        isCallActive = true;

                        fadeInCall();
                    } catch (Exception e) {
                        Toast.makeText(mainActivity,"Unable to call doctor", Toast.LENGTH_LONG ).show();
                    }
                } else {
                    try {
                        mAudioCall.endCall();
                        isCallActive = false;
                        fadeOutCall();
                    } catch (Exception e) {
                        Toast.makeText(mainActivity,"Something went wrong with SIP", Toast.LENGTH_LONG ).show();
                    }

                }
            }
        });
    }

    public void fadeInCall() {
        Switch speakerSwitch = (Switch) mainActivity.findViewById(R.id.speakerSwitch);
        speakerSwitch.setAlpha(0f);
        speakerSwitch.setVisibility(View.VISIBLE);
        speakerSwitch.setEnabled(true);
        speakerSwitch.setActivated(true);
        speakerSwitch.setClickable(true);
        speakerSwitch.animate().alpha(1f).setDuration(2000);

        TextView textView = (TextView) mainActivity.findViewById(R.id.callPresentationText);
        textView.setText("Dr. Phil, is todays doctor!");

        CircularImageView imageView = (CircularImageView) mainActivity.findViewById(R.id.imageview_circular_dr);
        imageView.setImageResource(R.drawable.img_drphill);
        mainActivity.findViewById(R.id.call_button_placeholder).setBackgroundColor(getResources().getColor(R.color.Color_HangUp_CallButton));
    }

    public void fadeOutCall() {
        final Switch speakerSwitch = (Switch) mainActivity.findViewById(R.id.speakerSwitch);
        speakerSwitch.setAlpha(1f);
        speakerSwitch.setEnabled(false);
        speakerSwitch.animate().alpha(0f).setDuration(2000);

        TextView textView = (TextView) mainActivity.findViewById(R.id.callPresentationText);
        textView.setText(getResources().getString(R.string.text_presentation_call_before));

        CircularImageView imageView = (CircularImageView) mainActivity.findViewById(R.id.imageview_circular_dr);
        imageView.setImageResource(R.drawable.img_stethoscope);

        mainActivity.findViewById(R.id.call_button_placeholder).setBackgroundColor(getResources().getColor(R.color.Color_Call_CallButton));
    }
}
