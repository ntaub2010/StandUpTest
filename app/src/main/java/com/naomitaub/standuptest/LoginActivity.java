package com.naomitaub.standuptest;

import android.app.Activity;
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
    Button signInButton;
    boolean loginStatus;
    Cursor CR;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        signInButton = (Button) findViewById(R.id.email_sign_in_button);

        loginEmail = (EditText)findViewById(R.id.email);
        loginPassword = (EditText)findViewById(R.id.password);


        //String username = "";

        signInButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                login_email = loginEmail.getText().toString();
                login_password = loginPassword.getText().toString();

                DatabaseOperations DOP = new DatabaseOperations(ctxLogin);
                CR = DOP.getLoginInfo(DOP);
                //CR.moveToFirst();
                loginStatus = false;

                if( CR != null && CR.moveToFirst() ){
                    //num = cursor.getString(cursor.getColumnIndex("ContactNumber"));
                    do {

                        if(login_email.equals(CR.getString(0)) && login_password.equals(CR.getString(1))) {
                            loginStatus = true;
                            //username = CR.getString(0);
                        }
                    } while (CR.moveToNext());
                    CR.close();
                }

                if(loginStatus) {
                    Toast.makeText(getBaseContext(), "Login successful", Toast.LENGTH_LONG).show();
                    launchTests(v, login_email);
                    //finish();
                }
                else {
                    Toast.makeText(getBaseContext(), "Error: Login failed", Toast.LENGTH_LONG).show();
                    //finish();
                }
            }
        });

    }


}



