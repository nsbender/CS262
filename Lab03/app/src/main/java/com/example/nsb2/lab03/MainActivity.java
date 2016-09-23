package com.example.nsb2.lab03;

import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class MainActivity extends AppCompatActivity
implements OnEditorActionListener{

    //Variable declarations for widgets
    private TextView passwordStatus;
    private EditText passwordField;
    private ImageView secretImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Associate the widgets with layout items
        passwordField = (EditText) findViewById(R.id.passwordField);
        passwordStatus = (TextView) findViewById(R.id.passwordStatus);
        secretImage = (ImageView) findViewById(R.id.secretImage);

        //Create the listener for the password field
        passwordField.setOnEditorActionListener(this);
    }

    public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
        //Listens for the Done key to be pressed, hides the keyboard; If the password is correct,
        // the secret image will be made visible. Otherwise it will remain hidden.
        if (actionId == EditorInfo.IME_ACTION_DONE ||
        actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
            if ((passwordField.getText().toString()).equals( this.getString(R.string.secret_password))){
                passwordStatus.setText ("Correct Password");
                secretImage.setAlpha((float)1);
            }
            else{
                passwordStatus.setText ("Incorrect Password");
                secretImage.setAlpha((float)0);
            }
        }
        return false;
    }
}
