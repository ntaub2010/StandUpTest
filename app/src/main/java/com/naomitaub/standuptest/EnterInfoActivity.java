package com.naomitaub.standuptest;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by Naomi on 5/3/2015.
 */
public class EnterInfoActivity extends MainActivity {

    EditText enterInfoFirstName, enterInfoLastName, enterInfoBirthdate, enterInfoAge;
    RadioButton femaleButton, maleButton, otherButton;
    Button enterInfoSaveButton;
    String enterInfoGender, EIFName, EILName, EIBDate;
    int EIAge;
    Context ctxEnterInfo = this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_enter_info);

        enterInfoFirstName = (EditText) findViewById(R.id.firstNameText);
        enterInfoLastName = (EditText) findViewById(R.id.lastNameText);
        enterInfoBirthdate = (EditText) findViewById(R.id.birthdateText);
        enterInfoAge = (EditText) findViewById(R.id.ageText);

        enterInfoSaveButton = (Button)findViewById(R.id.saveInfoButton);
        enterInfoSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EIFName = enterInfoFirstName.getText().toString();
                EILName = enterInfoLastName.getText().toString();
                EIBDate = enterInfoBirthdate.getText().toString();
                EIAge = Integer.parseInt(enterInfoAge.getText().toString());


                femaleButton = (RadioButton) findViewById(R.id.genderFemaleButton);
                maleButton = (RadioButton) findViewById(R.id.genderMaleButton);
                otherButton = (RadioButton) findViewById(R.id.genderOtherButton);

                if (femaleButton.isChecked())
                    enterInfoGender = "Female";

                else if (maleButton.isChecked())
                    enterInfoGender = "Male";

                else if (otherButton.isChecked())
                    enterInfoGender = "Other";

                DatabaseOperations DB = new DatabaseOperations(ctxEnterInfo);
                DB.putUserInfo(DB, EIFName, EILName, EIBDate, EIAge, enterInfoGender);

                Toast.makeText(getBaseContext(), "User info updating successful", Toast.LENGTH_LONG).show();
                launchTests(v);
                //finish();

            }
        });
    }
}
