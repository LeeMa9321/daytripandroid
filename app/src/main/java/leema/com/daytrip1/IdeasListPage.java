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

public class IdeasListPage extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private List<Idea> ideaList;
    private ListView ideaListView;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideas_list_page);


        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()).child("idea");

        ideaListView = findViewById(R.id.idea_list_view);

        ideaList = new ArrayList<>();

        ImageButton goBack = findViewById(R.id.back_idea_list);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goback = new Intent(IdeasListPage.this, FullscreenActivity.class);
                startActivity(goback);
            }
        });

        ImageButton addNewIdea = findViewById(R.id.add_new_idea);

        addNewIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addnew = new Intent(IdeasListPage.this, AddNewIdea.class);
                startActivity(addnew);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ideaList.clear();
                for(DataSnapshot ideaSnapshot : dataSnapshot.getChildren()) {
                    Idea ideaObj = ideaSnapshot.getValue(Idea.class);
                    ideaList.add(ideaObj);
                }

                IdeaListAdapter ideaListAdapter = new IdeaListAdapter(IdeasListPage.this, ideaList);
                ideaListView.setAdapter(ideaListAdapter);
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
