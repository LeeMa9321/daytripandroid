package leema.com.daytrip1;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by leema on 2017-10-22.
 */

public class GoalListAdapter extends ArrayAdapter<GoalObject> {

    private Activity mActivity;
    private List<GoalObject> goalList;
    private FirebaseUser user;

    public GoalListAdapter(Activity mActivity, List<GoalObject> goalList) {
        super(mActivity, R.layout.list_view_goal, goalList);
        this.mActivity = mActivity;
        this.goalList = goalList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = mActivity.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_view_goal, null, true);

        TextView goalName = listViewItem.findViewById(R.id.goal_box_name);
        TextView goalSummary = listViewItem.findViewById(R.id.goal_box_summary);
        Button deleteButton = listViewItem.findViewById(R.id.delete_button);

        final GoalObject goal = goalList.get(position);

        goalName.setText(goal.getGoalName());
        goalSummary.setText(goal.getGoalDescription());
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteGoal(goal.getKey());
            }
        });



        return listViewItem;
    }

    private void deleteGoal(String key) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(user.getUid()).child("goal").child(key);
        ref.removeValue();

    }

}
