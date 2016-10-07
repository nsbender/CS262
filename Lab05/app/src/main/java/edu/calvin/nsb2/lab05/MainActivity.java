package edu.calvin.nsb2.lab05;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private boolean preference = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Setup the preference manager and load the default values from the XML settings sheet
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        //Create an instance of the preferences
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Call the method that updates the TextView with the current settings
        showPreferences();
    }

    private void showPreferences(){
        TextView preferenceTextView = (TextView) findViewById(R.id.preferenceTextView);
        preferenceTextView.setText("Preference: " + prefs.getBoolean("preference", false));
    }

    @Override
    public void onPause(){
        //Commit the changes when the app loses focus
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("preference", preference);
        editor.commit();
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        preference = prefs.getBoolean("preference",false);
        showPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection in the inflatable menu
        switch (item.getItemId()) {
            case R.id.settings_menu:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
