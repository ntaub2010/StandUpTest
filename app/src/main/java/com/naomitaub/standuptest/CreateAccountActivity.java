package com.naomitaub.standuptest;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountActivity extends MainActivity {

    public EditText createAccountEmail, createAccountPassword;
    public Button createAccountButton;
    public String email, password;
    public Context ctxAccount = this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        createAccountEmail = (EditText) findViewById(R.id.createAccountEmail);
        createAccountPassword = (EditText) findViewById(R.id.createAccountPassword);
        createAccountButton = (Button)findViewById(R.id.createAccountButton);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = createAccountEmail.getText().toString();
                password = createAccountPassword.getText().toString();

                DatabaseOperations DB = new DatabaseOperations(ctxAccount);
                DB.putAccountInfo(DB, email, password);
                Toast.makeText(getBaseContext(), "Account creation successful", Toast.LENGTH_LONG).show();
                launchEnterInfo(v, email);
            }
        });
    }


    }




