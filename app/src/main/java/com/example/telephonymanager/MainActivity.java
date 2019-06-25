package com.example.telephonymanager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_view = (TextView) findViewById(R.id.textView);

        // Get the instance of Telephony Manager
        TelephonyManager TM = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

       // checking MarshMallow permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {

        }
        else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_PHONE_STATE}, 1);
        }

        // Calling the methods of TelephonyManager that returns the information
        String IMEINumber = TM.getDeviceId();
        String subscriberID = TM.getDeviceId();
        String SIMSerialNumber = TM.getSimSerialNumber();
        String networkCountryISO = TM.getNetworkCountryIso();
        String SIMCountryISO = TM.getSimCountryIso();
        String softwareVersion = TM.getDeviceSoftwareVersion();
        String voiceMailNumber = TM.getVoiceMailNumber();

        // Get the phone type
        String strPhoneType = "";

        int phoneType = TM.getPhoneType();

        switch (phoneType) {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                strPhoneType = "CDMA";
                break;

            case (TelephonyManager.PHONE_TYPE_GSM):
                strPhoneType = "GSM";
                break;

            case (TelephonyManager.PHONE_TYPE_NONE):
                strPhoneType = "NONE";
                break;
        }

        // gettin information if phone is in roaming
        boolean isRoaming = TM.isNetworkRoaming();

        String info = "Phone Details:\n";
        info += "\n IMEI Number: " + IMEINumber;
        info += "\n SubscriberID: " + subscriberID;
        info += "\n Sim Serial Number: " + SIMSerialNumber;
        info += "\n Network Country ISO: " + networkCountryISO;
        info += "\n SIM Country ISO: " + SIMCountryISO;
        info += "\n Software Version: " + softwareVersion;
        info += "\n Voice Mail Number: " + voiceMailNumber;
        info += "\n Phone Network Type: " + strPhoneType;
        info += "\n In Roaming? : " + isRoaming;

        // displaying the information in the textView
        text_view.setText(info);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // when "allow" is clicked, it reaches here and starts processing
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // permission granted, do your work

            Log.e("REQUEST", "REQUESTPERMISSION WAS GRANTED");
        }

        // if the user hits "DENY" for the first time
        else {
            // permission denied
            //  Log.e("ELSE", "PERMISSION DENIED");
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(this, "Rationale displayed", Toast.LENGTH_SHORT).show();
            }

            // this is when the user checks "Do not ask again box"
            else {
                Toast.makeText(this, "Never ask again selected",Toast.LENGTH_SHORT).show();
            }
        }
        return;

    }

}
