package leema.com.daytrip1;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
 * Created by leema on 2017-11-08.
 */

public class IdeaListAdapter extends ArrayAdapter<Idea> {
    private Activity mActivity;
    private List<Idea> ideaList;
    private FirebaseUser user;

    public IdeaListAdapter(Activity mActivity, List<Idea> ideaList) {
        super(mActivity,R.layout.list_view_idea, ideaList);
        this.ideaList = ideaList;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = mActivity.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_view_idea, null, true);

        TextView ideaName = listViewItem.findViewById(R.id.idea_name_box);
        TextView ideaSummary = listViewItem.findViewById(R.id.idea_box_summary);
        Button deleteIdea = listViewItem.findViewById(R.id.delete_idea);

        final Idea idea = ideaList.get(position);

        ideaName.setText(idea.getIdeaName());
        ideaSummary.setText(idea.getIdeaSummary());
        deleteIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteIdea(idea.getKey());
            }
        });

        return listViewItem;

    }

    private void deleteIdea(String key) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(user.getUid()).child("idea").child(key);
        ref.removeValue();
    }
}
