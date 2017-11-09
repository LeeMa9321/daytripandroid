package leema.com.daytrip1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
public class GoalListPage extends AppCompatActivity {


    private ListView goalListView;
    private GoalListAdapter mAdapter;
    private DatabaseReference mDatabaseReference;
    private FirebaseUser user;
    private List<GoalObject> goalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_goal_list_page);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()).child("goal");

        goalListView = findViewById(R.id.goal_list_view);

        goalList = new ArrayList<>();

        ImageButton addGoal = (ImageButton) findViewById(R.id.add_new_goal);

        addGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNewGoal = new Intent(GoalListPage.this, AddNewGoal.class);
                startActivity(addNewGoal);
            }
        });

        ImageButton goback = findViewById(R.id.back_goal_list);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goback = new Intent(GoalListPage.this, FullscreenActivity.class);
                startActivity(goback);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                goalList.clear();
                for (DataSnapshot goalSnapshot : dataSnapshot.getChildren()) {
                    GoalObject goal = goalSnapshot.getValue(GoalObject.class);

                    goalList.add(goal);
                }

                GoalListAdapter goalListAdapter = new GoalListAdapter(GoalListPage.this, goalList);
                goalListView.setAdapter(goalListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


    }
}


