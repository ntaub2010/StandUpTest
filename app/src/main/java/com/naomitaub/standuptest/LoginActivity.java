package com.naomitaub.standuptest;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends MainActivity {

    EditText loginEmail, loginPassword;
    String login_email, login_password;
    Context ctxLogin = this;
    Button signInButton, enterInfoButton;
    boolean loginStatus;
    Cursor CR;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        signInButton = (Button) findViewById(R.id.email_sign_in_button);
        enterInfoButton = (Button) findViewById(R.id.enterInfoButtonLogin);

        loginEmail = (EditText)findViewById(R.id.email);
        loginPassword = (EditText)findViewById(R.id.password);

        signInButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                login_email = loginEmail.getText().toString();
                login_password = loginPassword.getText().toString();

                DatabaseOperations DOP = new DatabaseOperations(ctxLogin);
                CR = DOP.getLoginInfo(DOP);
                loginStatus = false;

                if( CR != null && CR.moveToFirst() ){
                    do {

                        if(login_email.equals(CR.getString(0)) && login_password.equals(CR.getString(1))) {
                            loginStatus = true;
                        }
                    } while (CR.moveToNext());
                    CR.close();
                }

                if(loginStatus) {
                    Toast.makeText(getBaseContext(), "Login successful", Toast.LENGTH_LONG).show();
                    launchTests(v, login_email);
                }
                else {
                    Toast.makeText(getBaseContext(), "Error: Login failed", Toast.LENGTH_LONG).show();
                }
            }
        });

        enterInfoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //this isn't working as it should
                if(login_email == "" && login_password == "")
                    Toast.makeText(getBaseContext(), "No information entered", Toast.LENGTH_LONG).show();
                else
                    launchEnterInfo(view, login_email);
            }
        });

    }


}



