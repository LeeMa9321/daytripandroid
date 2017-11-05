package leema.com.daytrip1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class AddNewWorkout extends AppCompatActivity {


    private EditText workoutNameEdit;
    private EditText workoutWeightEdit;
    private EditText workoutRepsEdit;
    private ImageButton saveButton2;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_new_workout);


        workoutNameEdit = findViewById(R.id.workout_name_edit);
        workoutWeightEdit = findViewById(R.id.weight_count);
        workoutRepsEdit = findViewById(R.id.rep_count);
        saveButton2 = findViewById(R.id.save_button_2);

        saveButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWorkoutDesc();
            }
        });

        ImageButton goBack = findViewById(R.id.back_addwork);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goback = new Intent(AddNewWorkout.this, WorkoutListPage.class);
                startActivity(goback);
            }
        });

    }

    private void setWorkoutDesc() {
        final String workoutName = workoutNameEdit.getText().toString();
        final String workoutReps = workoutRepsEdit.getText().toString();
        final String workoutWeight = workoutWeightEdit.getText().toString();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("workout");

        if (!TextUtils.isEmpty(workoutName) && !TextUtils.isEmpty(workoutReps) && !TextUtils.isEmpty(workoutWeight)) {
            String key = mDatabaseReference.push().getKey();
            Workout workout = new Workout(key, workoutName, workoutReps, workoutWeight);

            mDatabaseReference.child(key).setValue(workout);
            workoutNameEdit.setText("");
            workoutRepsEdit.setText("");
            workoutWeightEdit.setText("");

            Toast.makeText(AddNewWorkout.this, "Workout added!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(AddNewWorkout.this, "Please enter information above", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
    }
}

