package com.app.groupproject.unisocial.ActivityPages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.groupproject.unisocial.EventData;
import com.app.groupproject.unisocial.R;
import com.squareup.picasso.Picasso;

public class



EventInfoActivity extends AppCompatActivity {



    private EventData eventData;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);


        Intent eventInfo = getIntent();
        eventData = (EventData) eventInfo.getExtras().get("eventData");

        Log.v("E_VALUE-------------1-",eventData.getImage());
        Log.v("E_VALUE--------------",eventData.getTitle());


        TextView title = (TextView) findViewById(R.id.eventinfo_title_id);
        TextView desc = (TextView) findViewById(R.id.eventinfo_description_id);
        TextView host = (TextView) findViewById(R.id.eventinfo_host_id);
        TextView location = (TextView) findViewById(R.id.eventinfo_location_id);
        TextView noStudents = (TextView) findViewById(R.id.eventinfo_noStudents_id);
        TextView date = (TextView) findViewById(R.id.eventinfo_date_id);
        TextView time = (TextView) findViewById(R.id.eventinfo_time_id);
        ImageView imageView = (ImageView) findViewById(R.id.eventinfo_image_id);
        Button chatroomBtn = (Button) findViewById(R.id.joinchatRoom_btn_id);

        title.setText(eventData.getTitle());
        desc.setText(eventData.getDescription());
        host.setText(eventData.getHost());
        location.setText(eventData.getLocation());
        noStudents.setText(eventData.getNoStudents());
        date.setText(eventData.getDate());
        time.setText(eventData.getTime());

        Picasso.with(this).load(eventData.getImage()).into(imageView);

        chatroomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventInfoActivity.this,ChatroomActivity.class);
                intent.putExtra("roomName",eventData.getTitle());
                startActivity(intent);

            }
        });




    }
}
