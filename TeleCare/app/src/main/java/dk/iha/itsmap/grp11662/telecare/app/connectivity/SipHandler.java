package dk.iha.itsmap.grp11662.telecare.app.connectivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.util.Log;

import java.text.ParseException;

public class SipHandler {

    public static final String LOG_TAG = "SipHandler";

    public SipManager mSipManager = null;

    private Context mContext = null;
    private SipProfile mSipProfile = null;

    public SipHandler(Context context) {
        mContext = context;


    }


    public void setupSipManager() throws SipException{
        if(mSipManager == null) {
            mSipManager = SipManager.newInstance(mContext);
        }

        mSipManager.setRegistrationListener(mSipProfile.getUriString(), new SipRegistrationListener() {

            public void onRegistering(String localProfileUri) {
                Log.i(LOG_TAG, "Registering with SIP Server...");
            }

            public void onRegistrationDone(String localProfileUri, long expiryTime) {
                Log.i(LOG_TAG,"Ready");
            }

            public void onRegistrationFailed(String localProfileUri, int errorCode,
                                             String errorMessage) {
                Log.i(LOG_TAG,"Registration failed.  Please check settings.");
            }
        });
    }

    public void setupSipProfile(String username, String password, String domain) throws ParseException{
        SipProfile.Builder builder = new SipProfile.Builder(username, domain);
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
    }

    public void closeSipProfile() {
        if (mSipManager == null) {
            return;
        }
        try {
            if (mSipProfile != null) {
                mSipManager.close(mSipProfile.getUriString());
            }
        } catch (Exception ee) {
            Log.d("WalkieTalkieActivity/onDestroy", "Failed to close local profile.", ee);
        }
    }
}
