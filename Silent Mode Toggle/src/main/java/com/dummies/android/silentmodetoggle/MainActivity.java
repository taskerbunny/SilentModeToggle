package com.dummies.android.silentmodetoggle;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

    //***** Private Members *****//
    private AudioManager mAudioManager;
    private boolean mPhoneIsSilent;

    //******** Methods  ***********//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        //
        mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);

        //Synch mPhoneIsSilent value to the phone's ringing mode.
        checkIfPhoneIsSilent();
    }

    /**
     * Checks to see if the phone is currently in Silent mode.
     */
    private void checkIfPhoneIsSilent() {
        int ringerMode = mAudioManager.getRingerMode();
        if (ringerMode == AudioManager.RINGER_MODE_SILENT) {
            mPhoneIsSilent = true;
        } else {
            mPhoneIsSilent = false;
        }
    }

    /**
     * Toggles the UI images from silent to normal and vice versa.
     */
    private void toggleUi() {
        ImageView imageView = (ImageView) findViewById(R.id.phone_icon);
        Drawable newPhoneImage;
        if (mPhoneIsSilent) {
            newPhoneImage = getResources().getDrawable(R.drawable.phone_silent);
        }
        else {
            newPhoneImage = getResources().getDrawable(R.drawable.phone_on);
        }
        imageView.setImageDrawable(newPhoneImage);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //
        checkIfPhoneIsSilent();
        toggleUi();

        //
        Log.w("myApp", "onResume()");
    }
    @Override
    protected void onPause() {
        super.onPause();

        //
        checkIfPhoneIsSilent();
        toggleUi();

        //
        Log.w("myApp", "onPause()");
    }
    @Override
    protected void onStop() {
        super.onStop();

        //
        Log.w("myApp", "onStop()");
    }



    /** XML event handler - Called when the user clicks the toggle button */
    public void hndlr_toggleButton(View view) {
        // Do something in response to button
        Log.w("myApp", "button pushed!");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            //**** Toggle button - attach handler in the fragment? ****//

            //Method handler
            setButtonClickListener(rootView);

            /*
            //Inline handler
            Button btnToggle = (Button) rootView.findViewById(R.id.toggleButton);
            btnToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.w("myApp", "pushed!");
                }
            });
            */

            //
            return rootView;
        }

        private void setButtonClickListener(View v) {
            Button toggleButton;
            toggleButton = (Button)v.findViewById(R.id.toggleButton);
            Log.w("myApp", toggleButton.toString());

            toggleButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.w("myApp", "pushed!");

                    //
                    if (mPhoneIsSilent) {
                        // Change back to normal mode
                        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                        mPhoneIsSilent = false;
                    } else {
                        // Change to silent mode
                        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        mPhoneIsSilent = true;
                    }

                    // Now toggle the UI again
                    toggleUi();

                    //Show ringer status
                    switch (mAudioManager.getRingerMode()) {
                        case AudioManager.RINGER_MODE_SILENT:
                            Log.i("MyApp","Silent mode");
                            break;
                        case AudioManager.RINGER_MODE_VIBRATE:
                            Log.i("MyApp","Vibrate mode");
                            break;
                        case AudioManager.RINGER_MODE_NORMAL:
                            Log.i("MyApp","Normal mode");
                            break;
                    }
                }
            });
        }



    }

}
