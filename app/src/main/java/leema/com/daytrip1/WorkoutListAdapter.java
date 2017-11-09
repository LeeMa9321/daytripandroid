package leema.com.daytrip1;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by leema on 2017-11-05.
 */

public class WorkoutListAdapter extends ArrayAdapter<Workout> {

    private Activity mActivity;
    private List<Workout> workoutList;

    public WorkoutListAdapter(Activity mActivity, List<Workout> workoutList) {
        super(mActivity, R.layout.list_view_workout, workoutList);
        this.mActivity = mActivity;
        this.workoutList = workoutList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = mActivity.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_view_workout, null, true);

        TextView workoutName = listViewItem.findViewById(R.id.workout_box_name);
        TextView workoutReps = listViewItem.findViewById(R.id.workout_box_reps);
        TextView workoutWeight  = listViewItem.findViewById(R.id.workout_box_weight);
        Button deleteButton = listViewItem.findViewById(R.id.delete_button_workout);

        final Workout workout = workoutList.get(position);

        workoutName.setText(workout.getWorkoutName());
        workoutReps.setText("Number of reps: " + workout.getWorkoutReps());
        workoutWeight.setText("Weight: " + workout.getWorkoutWeight() + " lbs");
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWorkout(workout.getWorkoutKey());
            }
        });



        return listViewItem;
    }

    private void deleteWorkout(String key) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("workout").child(key);
        ref.removeValue();
    }
}
