package leema.com.daytrip1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by leema on 2017-10-22.
 */

public class GoalListAdapter extends BaseAdapter {

    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private ArrayList<DataSnapshot> mSnapshotList;
    private FirebaseUser user;
    private Button delete;

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            mSnapshotList.add(dataSnapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public GoalListAdapter(Activity activity, DatabaseReference ref) {
        mActivity = activity;
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseReference = ref.child(user.getUid()).child("goal");
        mDatabaseReference.addChildEventListener(mListener);
        mSnapshotList = new ArrayList<>();
    }

    static class ViewHolder {
        TextView goalName;
        TextView goalSummary;
        RelativeLayout.LayoutParams params;
    }

    @Override
    public int getCount() {
        return mSnapshotList.size();
    }

    @Override
    public GoalObject getItem(int position) {

        DataSnapshot snapshot = mSnapshotList.get(position);

        return snapshot.getValue(GoalObject.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_goal, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.goalName = (TextView) convertView.findViewById(R.id.goal_box_name);
            holder.goalSummary = (TextView) convertView.findViewById(R.id.goal_box_summary);
            convertView.setTag(holder);
        }

        final GoalObject goal = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        String goalName = goal.getGoalName();
        holder.goalName.setText(goalName);

        String goalSmry = goal.getGoalDescription();
        holder.goalSummary.setText(goalSmry);

        return convertView;
    }


    public void cleanUp() {
        mDatabaseReference.removeEventListener(mListener);
    }
}
