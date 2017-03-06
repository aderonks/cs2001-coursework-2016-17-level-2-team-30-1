package com.app.groupproject.officialunisocial.ActivityPages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

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

        //retrieved the buttons from the layout activity page
        Button registerBtn = (Button) findViewById(R.id.register_button_id);
        Button browserImg = (Button) findViewById(R.id.BrowseProfileImage_button_id);

        //Added a spinner to give users an option between male and female
        Spinner genderOptions = (Spinner) findViewById(R.id.gender_options_id);


        //added array adapter which gets the string array values which the user choses from
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.gender_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }
}
