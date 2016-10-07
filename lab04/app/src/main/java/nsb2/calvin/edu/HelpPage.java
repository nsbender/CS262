/*
    Author: Nate Bender
    About Page activity for CS262 Lab04
    9/30/2016
 */

package nsb2.calvin.edu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HelpPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);
        setTitle("Lab04 - About");
    }

}
