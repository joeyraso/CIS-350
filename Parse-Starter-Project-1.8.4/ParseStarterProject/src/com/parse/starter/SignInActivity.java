package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class SignInActivity extends Activity {

    EditText usernameTextObject;
    EditText passwordTextObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_view);

        usernameTextObject = (EditText) findViewById(R.id.usernameSignIn);
        passwordTextObject = (EditText) findViewById(R.id.passwordSignIn);

        //Calls the signin method once the user presses the "Sign In" button
        Button signUpButton = (Button) findViewById(R.id.signinButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                validateLogIn();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //validate user credentials with CurrentUserActivity
    public void validateLogIn() {
        String username = usernameTextObject.getText().toString().trim();
        String password = passwordTextObject.getText().toString().trim();

        //all fields must be filled in for the login to work
        StringBuilder signinErrors = new StringBuilder("");
        boolean fieldError = false;
        if (username.length() == 0) {
            signinErrors.append("Username was not entered. ");
            fieldError = true;
        }
        if (password.length() == 0) {
            signinErrors.append("Password was not entered. ");
            fieldError = true;
        }

        //displays the fieldErrors using Toast
        if (fieldError) {
            Toast.makeText(SignInActivity.this, signinErrors.toString(),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (e == null) {
                    // Start an intent for the CurrentUserActivity, which routes user if logged in
                    Intent intent = new Intent(SignInActivity.this, CurrentUserActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    //If e is not null, there is an error which we display
                    Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //go to the sign up screen
    public void goToSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }


}
