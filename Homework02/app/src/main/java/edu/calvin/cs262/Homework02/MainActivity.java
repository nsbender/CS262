package edu.calvin.cs262.Homework02;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Reads Monopoly player info from http://cs262.cs.calvin.edu:8089/monopoly/
 * Homework 2
 * Nate Bender
 */
public class MainActivity extends AppCompatActivity {

    private EditText cityText;
    private Button fetchButton;

    private List<Weather> plist = new ArrayList<>();
    private ListView plistview;

    /* This formater can be used as follows to format temperatures for display.
     *     numberFormat.format(SOME_DOUBLE_VALUE)
     */
    //private NumberFormat numberFormat = NumberFormat.getInstance();

    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityText = (EditText) findViewById(R.id.userText);
        fetchButton = (Button) findViewById(R.id.fetchButton);
        plistview = (ListView) findViewById(R.id.playersListView);

        // See comments on this formatter above.
        //numberFormat.setMaximumFractionDigits(0);

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissKeyboard(cityText);
                new GetWeatherTask().execute(createURL(cityText.getText().toString()));
            }
        });
    }

    /**
     * Formats a URL for the webservice specified in the string resources.
     *
     * @param entry the target entry
     * @return URL formatted for "http://cs262.cs.calvin.edu:8089/monopoly/players/
     */
    private URL createURL(String entry) {
        try {
            String urlString;
            if (entry.isEmpty()) {
                urlString = "http://153.106.75.63:9998/monopoly/players/";
            } else {
                urlString = "http://153.106.75.63:9998/monopoly/player/" + entry;
            }
            return new URL(urlString);
        } catch (Exception e) {
            Toast.makeText(this, "Failed to create URL", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    /**
     * Deitel's method for programmatically dismissing the keyboard.
     *
     * @param view the TextView currently being edited
     */
    private void dismissKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Inner class for GETing the current player data from "http://cs262.cs.calvin.edu:8089/monopoly/players/ asynchronously
     */
    private class GetWeatherTask extends AsyncTask<URL, Void, JSONObject> {

        @Override
        protected void onPostExecute(JSONObject weather) {
            if (weather != null) {
                convertJSONtoArrayList(weather);
                MainActivity.this.updateDisplay();
            } else {
                Toast.makeText(MainActivity.this, getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected JSONObject doInBackground(URL... params) {
            HttpURLConnection connection = null;
            StringBuilder result = new StringBuilder();
            try {
                connection = (HttpURLConnection) params[0].openConnection();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    if (result.toString().startsWith("[")) {
                        return new JSONObject("{\"plist\" : " + result.toString() + "}");
                    } //Wrap Array as Object
                    else {
                        return new JSONObject(result.toString());
                    } //Data already an Object

                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return null;
        }
    }


    /**
     * Converts the JSON player data to an arraylist suitable for a listview adapter
     *
     * @param playerobj
     */
    private void convertJSONtoArrayList(JSONObject playerobj) {
        plist.clear(); // clear old data
        try {
            //If object is not a JSONArray stored as plist, it's already an Object
            if (!playerobj.has("plist")) {
                String name, email;
                //Get the values from the list or assign defaults...
                if (playerobj.has("name")) {
                    name = playerobj.getString("name");
                } else {
                    name = " ";
                }

                if (playerobj.has("emailaddress")) {
                    email = playerobj.getString("emailaddress");
                } else {
                    email = "_";
                }

                Integer id = playerobj.getInt("id");
                plist.add(new Weather(id, name, email));
            } else { //If it is a list, must iterate through JSONArray for all player info.
                JSONArray list = playerobj.getJSONArray("plist");
                //For each player...
                for (int i = 0; i < list.length(); i++) {
                    JSONObject aplayer = list.getJSONObject(i);
                    String name, email;
                    //Get values, or assign default spaces...
                    if (aplayer.has("name")) {
                        name = aplayer.getString("name");
                    } else {
                        name = " ";
                    }

                    if (aplayer.has("emailaddress")) {
                        email = aplayer.getString("emailaddress");
                    } else {
                        email = "_";
                    }

                    Integer id = aplayer.getInt("id");
                    plist.add(new Weather(id, name, email));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Refresh the player data on the  ListView through a simple adapter
     */
    private void updateDisplay() {
        if (plist == null) {
            Toast.makeText(MainActivity.this, getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
        }
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        for (Weather item : plist) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", item.getId().toString());
            map.put("name", item.getName());
            map.put("email", item.getEmail());
            data.add(map);
        }

        int resource = R.layout.weather_item;
        String[] from = {"id", "name", "email"};
        int[] to = {R.id.idTextView, R.id.nameTextView, R.id.emailTextView};

        SimpleAdapter adapter = new SimpleAdapter(this, data, resource, from, to);
        plistview.setAdapter(adapter);
    }

}
