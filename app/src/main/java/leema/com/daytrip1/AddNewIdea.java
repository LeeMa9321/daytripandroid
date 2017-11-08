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

public class AddNewIdea extends AppCompatActivity {

    private EditText ideaName;
    private EditText ideaSummary;
    private ImageButton saveButton;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_idea);

        ideaName = findViewById(R.id.idea_name_edit);
        ideaSummary = findViewById(R.id.idea_summ_body);
        saveButton = findViewById(R.id.idea_save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDesc();
            }
        });

        ImageButton goBack = findViewById(R.id.back_add_idea);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gobacktolist = new Intent(AddNewIdea.this, IdeasListPage.class);
                startActivity(gobacktolist);
            }
        });
    }

    private void setDesc() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("idea");

        final String ideaNameInput = ideaName.getText().toString();
        final String ideaSummaryInput = ideaSummary.getText().toString();

        if(!TextUtils.isEmpty(ideaNameInput) && !TextUtils.isEmpty(ideaSummaryInput)) {
            String key = mDatabaseReference.push().getKey();
            Idea idea = new Idea(key, ideaNameInput, ideaSummaryInput);

            mDatabaseReference.child(key).setValue(idea);
            ideaName.setText("");
            ideaSummary.setText("");
            Toast.makeText(AddNewIdea.this, "Idea Added!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(AddNewIdea.this, "Please input all text fields" , Toast.LENGTH_LONG).show();
        }
    }
}
