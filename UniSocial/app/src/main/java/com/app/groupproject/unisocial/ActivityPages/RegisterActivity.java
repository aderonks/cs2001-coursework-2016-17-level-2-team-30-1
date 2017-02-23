package com.app.groupproject.unisocial.ActivityPages;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.groupproject.unisocial.R;
import com.app.groupproject.unisocial.RegisterData;
import com.app.groupproject.unisocial.SCMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegisterActivity extends AppCompatActivity {



    private RegisterData registerData = new RegisterData();
    private StorageReference storageRef;
    private String STRdownloadURI;
    private ImageView profileImg;
    private static final int PICK_IMAGE = 11;
    private EditText fullname,username,email,password,reenteredPassword,age,university,number;
    private EditText[] textField = new EditText[8];
    private FirebaseAuth fAuth;
    private DatabaseReference dbRef;
    private boolean fieldEmpty = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Spinner genderOptions = (Spinner) findViewById(R.id.gender_options_id);
        textField[0] = (EditText) findViewById(R.id.fullName_field_id);
        textField[1] = (EditText) findViewById(R.id.username_field_id);
        textField[2] = (EditText) findViewById(R.id.Email_field_id);
        textField[3] = (EditText) findViewById(R.id.password_field_id);
        textField[4] = (EditText) findViewById(R.id.reenter_password_field_id);
        textField[5] = (EditText) findViewById(R.id.age_field_id);
        textField[6] = (EditText) findViewById(R.id.university_field_id);
        textField[7] = (EditText) findViewById(R.id.number_field_id);

        profileImg = (ImageView) findViewById(R.id.profileimage_id);
        Button registerBtn = (Button) findViewById(R.id.register_button_id);
        Button browserImg = (Button) findViewById(R.id.BrowseProfileImage_button_id);


        dbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        fAuth = FirebaseAuth.getInstance();

        storageRef = FirebaseStorage.getInstance().getReference();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.gender_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        browserImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(SCMethods.textToString(textField[1]).equals("")) {
                    Toast.makeText(RegisterActivity.this, "Fill in Username Field", Toast.LENGTH_SHORT).show();
                }
                else {
                    openGallery();
                }
            }
        });

        genderOptions.setAdapter(adapter);
        genderOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                registerData.setGender(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for (EditText aTextField : textField) {

                    fieldEmpty = aTextField.getText().toString().equals("");
                }

                if(fieldEmpty) {
                    Toast.makeText(RegisterActivity.this, "Fill in All the Details", Toast.LENGTH_SHORT).show();
                }
                else {

                    String strEmail = SCMethods.textToString(textField[2]);
                    String strPassword = SCMethods.textToString(textField[3]);
                    String strRePassword = SCMethods.textToString(textField[4]);
                    if(!strPassword.equals(strRePassword)){
                        Toast.makeText(RegisterActivity.this, "Password Doesn't Match", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        fAuth.createUserWithEmailAndPassword(strEmail,strPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isComplete()) {
                                    Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();

                                    registerData.setUniqueID(fAuth.getCurrentUser().getUid());

                                    registerData.setFullname(SCMethods.textToString(textField[0]));
                                    registerData.setUsername(SCMethods.textToString(textField[1]));
                                    registerData.setAge(SCMethods.textToString(textField[5]));
                                    registerData.setUniversity(SCMethods.textToString(textField[6]));
                                    registerData.setNumber(SCMethods.textToString(textField[7]));
                                    registerData.setImageref(STRdownloadURI);

                                    SCMethods.addChildAndValue(dbRef.child(registerData.getUniqueID()), "uniqueID", registerData.getUniqueID());
                                    SCMethods.addChildAndValue(dbRef.child(registerData.getUniqueID()), "username", registerData.getUsername());
                                    SCMethods.addChildAndValue(dbRef.child(registerData.getUniqueID()), "fullname", registerData.getFullname());
                                    SCMethods.addChildAndValue(dbRef.child(registerData.getUniqueID()), "age", registerData.getAge());
                                    SCMethods.addChildAndValue(dbRef.child(registerData.getUniqueID()), "gender", registerData.getGender());
                                    SCMethods.addChildAndValue(dbRef.child(registerData.getUniqueID()), "number", registerData.getNumber());
                                    SCMethods.addChildAndValue(dbRef.child(registerData.getUniqueID()), "university", registerData.getUniversity());
                                    SCMethods.addChildAndValue(dbRef.child(registerData.getUniqueID()), "imageref", registerData.getImageref());


                                    Intent intent = new Intent(RegisterActivity.this,EventListActivity.class);
                                    intent.putExtra("profileData",registerData);
                                    startActivity(intent);

                                }


                            }
                        });


                    }
                }


            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            //gets Uri of the chosen image
            Uri imageFileUri = data.getData();
            profileImg.setImageURI(imageFileUri);

            StorageReference filePath = storageRef.child("Profile Image").child(SCMethods.textToString(textField[1]) + imageFileUri.getLastPathSegment());
            filePath.putFile(imageFileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    STRdownloadURI = downloadUri.toString();

                    Log.d("D_Value---------------",STRdownloadURI);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }
    }
}
