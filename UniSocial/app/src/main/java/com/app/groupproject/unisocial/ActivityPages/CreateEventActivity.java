package com.app.groupproject.unisocial.ActivityPages;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.app.groupproject.unisocial.EventData;
import com.app.groupproject.unisocial.R;
import com.app.groupproject.unisocial.SCMethods;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

public class CreateEventActivity extends AppCompatActivity {


    private static final int PICK_IMAGE = 10;
    private StorageReference storageRef;
    private EditText[] eventDetails = new EditText[7];
    private String STRdownloadURI;
    private ImageView eventImage;
    private Bitmap bmImage;



    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        storageRef = FirebaseStorage.getInstance().getReference();

        //This gets the database
        database = FirebaseDatabase.getInstance();

        eventImage = (ImageView) findViewById(R.id.imageview_id);
        Button browserImageBtn = (Button) findViewById(R.id.BrowseImage_button_id);
        eventDetails[0] = (EditText) findViewById(R.id.Title_field_id);
        browserImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(SCMethods.textToString(eventDetails[0]).equals("")) {
                    Toast.makeText(CreateEventActivity.this, "Fill in Title Field", Toast.LENGTH_SHORT).show();
                }
                else {
                    openGallery();
                }
            }
        });


        Button submitButton = (Button) findViewById(R.id.Submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                eventDetails[1] = (EditText) findViewById(R.id.Description_field_id);
                eventDetails[2] = (EditText) findViewById(R.id.Host_field_id);
                eventDetails[3] = (EditText) findViewById(R.id.Location_field_id);
                eventDetails[4] = (EditText) findViewById(R.id.No_of_students_field_id);
                eventDetails[5] = (EditText) findViewById(R.id.Date_field_id);
                eventDetails[6] = (EditText) findViewById(R.id.Time_field_id);
                ImageView image = (ImageView) findViewById(R.id.imageview_id);

                //This for checking if there are any blanks
                boolean blanks = true;

                for (EditText eventDetail : eventDetails) {
                    blanks = eventDetail.getText().toString().equals("") || image.getDrawable() == null;
                }

                if (blanks) {
                    Toast.makeText(CreateEventActivity.this, "Fill in all details", Toast.LENGTH_SHORT).show();
                } else {

                    DatabaseReference titleRef = database.getReference("Events/").push();

                    SCMethods.addChildAndValue(titleRef, "title", SCMethods.textToString(eventDetails[0]));
                    SCMethods.addChildAndValue(titleRef, "description", SCMethods.textToString(eventDetails[1]));
                    SCMethods.addChildAndValue(titleRef, "host", SCMethods.textToString(eventDetails[2]));
                    SCMethods.addChildAndValue(titleRef, "location", SCMethods.textToString(eventDetails[3]));
                    SCMethods.addChildAndValue(titleRef, "noStudents", SCMethods.textToString(eventDetails[4]));
                    SCMethods.addChildAndValue(titleRef, "date", SCMethods.textToString(eventDetails[5]));
                    SCMethods.addChildAndValue(titleRef, "time", SCMethods.textToString(eventDetails[6]));
                    SCMethods.addChildAndValue(titleRef, "image",STRdownloadURI);
                    Log.d("D_Value--------------", "2"+STRdownloadURI);

                    Intent intent = new Intent(CreateEventActivity.this,EventListActivity.class);
                    startActivity(intent);
                    finish();
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
            eventImage.setImageURI(imageFileUri);


            /*
            TRY UPLOAD IT WHEN SUMBIT is PRESSED
             */
            StorageReference filePath = storageRef.child("Event Image").child(SCMethods.textToString(eventDetails[0]) + imageFileUri.getLastPathSegment());
            filePath.putFile(imageFileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    STRdownloadURI = downloadUri.toString();

                    Log.d("D_Value---------------",STRdownloadURI);


                    Toast.makeText(CreateEventActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }
    }
}
