package nsb2.calvin.edu;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class CalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        // Get handles to UI elements
        final Spinner spinner = (Spinner) findViewById(R.id.opsSpinner);
        final TextView val1 = (TextView) findViewById(R.id.val1);
        final TextView val2 = (TextView) findViewById(R.id.val2);
        final TextView res_box = (TextView) findViewById(R.id.out_view);
        final Button calc_button = (Button) findViewById(R.id.calc_button);

        // Set up a spinner to allow the user to chose an operation
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.operations_array, android.R.layout.simple_spinner_item);
        // Set up each item in the spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        // Define the listener for the 'calculate' button
        calc_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the two input variables and define the result
                float result;
                float value1 = Float.parseFloat(val1.getText().toString());
                float value2 = Float.parseFloat(val2.getText().toString());


                if (spinner.getSelectedItemPosition() == 0)
                {
                    // Addition
                    result = value1 + value2;
                }
                else if (spinner.getSelectedItemPosition() == 1)
                {
                    // Subtract
                    result = value1 - value2;
                }
                else if (spinner.getSelectedItemPosition() == 2)
                {
                    // Multiplication
                    result = value1 * value2;
                }
                else
                {
                    // Divide
                    result = value1 / value2;
                }

                // Display the output in the result text field
                res_box.setText(Float.toString(result));
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();

        // Get handles to UI elements
        final Spinner spinner = (Spinner) findViewById(R.id.opsSpinner);
        final TextView val1 = (TextView) findViewById(R.id.val1);
        final TextView val2 = (TextView) findViewById(R.id.val2);
        final TextView res_box = (TextView) findViewById(R.id.out_view);
        final Button calc_button = (Button) findViewById(R.id.calc_button);

        // Save the preferences from the UI into the strings file
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("saved_v1", val1.getText().toString());
        editor.putString("saved_v2", val2.getText().toString());
        editor.putString("saved_op", Integer.toString(spinner.getSelectedItemPosition()));
        editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Get handles to UI elements
        final Spinner spinner = (Spinner) findViewById(R.id.opsSpinner);
        final TextView val1 = (TextView) findViewById(R.id.val1);
        final TextView val2 = (TextView) findViewById(R.id.val2);
        final TextView res_box = (TextView) findViewById(R.id.out_view);
        final Button calc_button = (Button) findViewById(R.id.calc_button);

        // Get the preferences from the strings file and put them back into the UI
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        val1.setText(sharedPref.getString("saved_v1", "0"));
        val2.setText(sharedPref.getString("saved_v2", "0"));
        spinner.setSelection(Integer.parseInt(sharedPref.getString("saved_op", "0")));
    }
}