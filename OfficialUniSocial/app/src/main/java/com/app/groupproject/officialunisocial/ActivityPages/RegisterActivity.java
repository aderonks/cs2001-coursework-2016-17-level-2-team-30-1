package com.app.groupproject.officialunisocial.ActivityPages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.app.groupproject.officialunisocial.R;

public class RegisterActivity extends AppCompatActivity {

    private ImageView profileImg;
    private EditText[] textField = new EditText[8];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //TextField for user information
        textField[0] = (EditText) findViewById(R.id.fullName_field_id);
        textField[1] = (EditText) findViewById(R.id.username_field_id);
        textField[2] = (EditText) findViewById(R.id.Email_field_id);
        textField[3] = (EditText) findViewById(R.id.password_field_id);
        textField[4] = (EditText) findViewById(R.id.reenter_password_field_id);
        textField[5] = (EditText) findViewById(R.id.age_field_id);
        textField[6] = (EditText) findViewById(R.id.university_field_id);
        textField[7] = (EditText) findViewById(R.id.number_field_id);

        //ImageView for profile pic
        profileImg = (ImageView) findViewById(R.id.profileimage_id);




    }
}
