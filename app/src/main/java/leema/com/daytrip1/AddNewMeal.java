package leema.com.daytrip1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewMeal extends AppCompatActivity {

    private EditText mealName;
    private EditText calories;
    private EditText protein;
    private EditText fat;
    private EditText sugar;
    private DatabaseReference mDatabaseReference;
    private ImageButton saveButton;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_meal);

        mealName = findViewById(R.id.meal_name_edit);
        calories = findViewById(R.id.calorie_count);
        protein = findViewById(R.id.protein_count);
        fat = findViewById(R.id.fat_count);
        sugar = findViewById(R.id.sugar_count);

        saveButton = findViewById(R.id.save_meal);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDesc();
            }
        });

        ImageButton gobackmeal = findViewById(R.id.back_meal);
        gobackmeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goback = new Intent(AddNewMeal.this, MealListPage.class);
                startActivity(goback);
            }
        });

    }

    private void setDesc() {

        //Getting UserID and then pushing for individual user id
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()).child("meal");

        final String mealNameInput = mealName.getText().toString();
        final String calorieInput = calories.getText().toString();
        final String proteinInput = protein.getText().toString();
        final String fatInput = fat.getText().toString();
        final String sugarInput = sugar.getText().toString();

        if (!TextUtils.isEmpty(mealNameInput) || !TextUtils.isEmpty(calorieInput) || !TextUtils.isEmpty(proteinInput) || !TextUtils.isEmpty(fatInput) || !TextUtils.isEmpty(sugarInput)) {
            String key = mDatabaseReference.push().getKey();

            final Meal meal = new Meal(key, mealNameInput, calorieInput, proteinInput, fatInput, sugarInput);

            mDatabaseReference.child(key).setValue(meal);
            mealName.setText("");
            calories.setText("");
            protein.setText("");
            fat.setText("");
            sugar.setText("");
            Toast.makeText(AddNewMeal.this, "Meal Added!" , Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(AddNewMeal.this, "Please input all text fields", Toast.LENGTH_LONG).show();
        }

    }
}
