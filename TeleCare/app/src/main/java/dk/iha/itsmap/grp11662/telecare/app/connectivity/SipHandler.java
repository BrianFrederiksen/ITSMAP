package dk.iha.itsmap.grp11662.telecare.app.connectivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;

import dk.iha.itsmap.grp11662.telecare.app.model.User;

public class SipHandler {

    public static final String LOG_TAG = "SipHandler";

    private SipManager mSipManager;
    private Boolean isOpen = false;

    private Context mContext;
    private SipProfile mSipProfile;
    private SipAudioCall.Listener mListener;

    public SipHandler(Context context, User user) {
        mContext = context;
        try {
            String userName = user.getUsername().split("@")[0];
            setupSipProfile(userName, user.getPassword(), user.getSipDomain());
            setupSipManager();
        } catch(Exception e) {
            Log.e(LOG_TAG, "Exception occured: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void setupSipManager() throws SipException{
        if(mSipManager == null) {
            mSipManager = SipManager.newInstance(mContext);
        }
        Boolean isRegistered = mSipManager.isRegistered(mSipProfile.getUriString());

        mSipManager.setRegistrationListener(mSipProfile.getUriString(), new SipRegistrationListener() {

            public void onRegistering(String localProfileUri) {
                Log.i(LOG_TAG, "Registering with SIP Server...");
            }

            public void onRegistrationDone(String localProfileUri, long expiryTime) {
                Log.i(LOG_TAG,"Ready");
            }

            public void onRegistrationFailed(String localProfileUri, int errorCode,
                                             String errorMessage) {
                Log.e(LOG_TAG,"Registration failed.  Please check settings. Message: " + errorMessage + " Code: " + errorCode);
            }
        });
    }

    private void setupSipProfile(String username, String password, String domain) throws ParseException{
        SipProfile.Builder builder = new SipProfile.Builder(username, domain);
        builder.setProfileName(username);
        builder.setPassword(password);
        mSipProfile = builder.build();
    }

    public void openSipProfile() throws SipException {
        if(mSipProfile == null) {
            throw new NullPointerException("SipProfile not setup");
        }
        if(mSipManager == null) {
            throw new NullPointerException("SipManager not setup");
        }

        Intent intent = new Intent();
        intent.setAction("android.SipDemo.INCOMING_CALL");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, Intent.FILL_IN_DATA);
        mSipManager.open(mSipProfile, pendingIntent, null);
        isOpen = true;
    }

    public void closeSipProfile() {
        if (mSipManager == null) {
            return;
        }
        try {
            if (mSipProfile != null) {
                mSipManager.close(mSipProfile.getUriString());
                isOpen = false;
            }
        } catch (Exception ee) {
            Log.d("WalkieTalkieActivity/onDestroy", "Failed to close local profile.", ee);
        }
    }

    public SipAudioCall MakeAudioCall(String peerUri, Integer timeOut, SipAudioCall.Listener listener) throws SipException {
        SipAudioCall call = mSipManager.makeAudioCall(mSipProfile.getUriString(), peerUri, listener, timeOut);
        return call;
    }
}
