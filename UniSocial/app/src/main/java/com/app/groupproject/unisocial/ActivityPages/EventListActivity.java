package com.app.groupproject.unisocial.ActivityPages;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.app.groupproject.unisocial.CustomAdapter;
import com.app.groupproject.unisocial.EventData;
import com.app.groupproject.unisocial.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventListActivity extends AppCompatActivity {



    //This will hold all our events
    private ArrayList<EventData> eventList = new ArrayList<>();
    private EventData pressedEvent;
    private AlertDialog.Builder builder;
    private ListView customList;
    private DatabaseReference databaseRef;
    private FirebaseDatabase fbDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);


        //This gets the url for our database because it already knows what it is
        fbDatabase = FirebaseDatabase.getInstance();
        //That's giving us the reference to the top level of our database
        databaseRef = fbDatabase.getReference();

        Log.v("E_VALUE", "database = " + databaseRef.toString());
        customList = (ListView) findViewById(R.id.list_id);
        builder = new AlertDialog.Builder(EventListActivity.this);

        customList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pressedEvent = (EventData) parent.getItemAtPosition(position);

                Intent eventInfo = new Intent(EventListActivity.this,EventInfoActivity.class);

                Log.v("E_VALUE--------------",pressedEvent.getImage());
                eventInfo.putExtra("eventData",pressedEvent);
                startActivity(eventInfo);

            }
        });



        //Instead of an inner class I call a anonymous interclass
        databaseRef.child("Events").addValueEventListener(new ValueEventListener() {


            /*
            This method will be invoked any time the data on the database changes.
            Additionally, it will be invoked as soon as we connect the listener,
            so that we can get an initial snapshot of the data on the database.
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //ctrl alt v Creates the correct data type to store it in
                //Iterable gets all of the children at this level
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                //shake hands with each of them and save it as a variable (Child)
                for(DataSnapshot child: children) {
                    EventData event = child.getValue(EventData.class);
                    eventList.add(event);
                }
                try {
                    Log.v("E_VALUE",eventList.get(0).getTitle());
                    Log.v("E_VALUE",eventList.get(1).getNoStudents());
                }catch (Exception e) {
                    Log.e("E_UniSocial", "GOT AN ERROR!",e);
                }
                CustomAdapter adapter = new CustomAdapter(EventListActivity.this,eventList);
                customList.setAdapter(adapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });








        ImageButton addBtn = (ImageButton) findViewById(R.id.addBtn_id);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(EventListActivity.this,CreateEventActivity.class);
                startActivity(addIntent);
                finish();
            }
        });








    }
}
