package leema.com.daytrip1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WorkoutListPage extends AppCompatActivity {

    private ListView workoutListView;
    private DatabaseReference mDatabaseReference;
    private List<Workout> workoutList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_workout_list_page);


        mDatabaseReference = FirebaseDatabase.getInstance().getReference("workout");

        workoutListView = findViewById(R.id.workout_list_view);
        workoutList = new ArrayList<>();

        ImageButton addWorkout = (ImageButton) findViewById(R.id.add_new_workout);

        addWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNewWorkout = new Intent(WorkoutListPage.this, AddNewWorkout.class);
                startActivity(addNewWorkout);
            }
        });

        ImageButton goBack = findViewById(R.id.back_workoutlist);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutListPage.this, FullscreenActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                workoutList.clear();
                for (DataSnapshot workoutSnapshot : dataSnapshot.getChildren()) {
                    Workout workout = workoutSnapshot.getValue(Workout.class);

                    workoutList.add(workout);
                }

                WorkoutListAdapter adapter = new WorkoutListAdapter(WorkoutListPage.this, workoutList);
                workoutListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
    }
}