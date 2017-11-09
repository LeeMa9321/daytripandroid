package leema.com.daytrip1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class AddNewGoal extends AppCompatActivity {


    private EditText goalName;
    private EditText goalSummary;
    private ImageButton saveButton;
    private DatabaseReference mDatabaseReference;
    private GoalListAdapter mAdapter;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_new_goal);

        goalName = (EditText) findViewById(R.id.goal_name_edit);
        goalSummary = (EditText) findViewById(R.id.summary_body);
        saveButton = (ImageButton) findViewById(R.id.save_button);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGoalDesc();
            }
        });

        ImageButton goback = findViewById(R.id.back_add_goal);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnPage = new Intent(AddNewGoal.this, GoalListPage.class);
                startActivity(returnPage);
            }
        });


    }

    public void setGoalDesc() {
        Log.d("DayTrip", "Goal Name sent!");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()).child("goal");

        final String goalNameInput = goalName.getText().toString();
        final String goalSummaryInput = goalSummary.getText().toString();

        if(!TextUtils.isEmpty(goalNameInput) && !TextUtils.isEmpty(goalSummaryInput)) {
            String key = mDatabaseReference.push().getKey();
            GoalObject goal = new GoalObject(key, goalNameInput, goalSummaryInput);

            mDatabaseReference.child(key).setValue(goal);
            goalName.setText("");
            goalSummary.setText("");
            Toast.makeText(AddNewGoal.this, "Goal Added!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(AddNewGoal.this, "Please enter goal information", Toast.LENGTH_LONG).show();
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
