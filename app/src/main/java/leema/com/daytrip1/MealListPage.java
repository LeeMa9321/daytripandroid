package leema.com.daytrip1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MealListPage extends AppCompatActivity {

    private List<Meal> mealList;
    private ListView mealListView;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list_page);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("meal");

        mealListView = findViewById(R.id.meal_list_view);

        mealList = new ArrayList<>();

        ImageButton goback = findViewById(R.id.back_meal_list);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gobackMealList = new Intent(MealListPage.this, FullscreenActivity.class);
                startActivity(gobackMealList);
            }
        });

        ImageButton addNewMeal = findViewById(R.id.add_new_meal);

        addNewMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNewMealPage = new Intent(MealListPage.this, AddNewMeal.class);
                startActivity(addNewMealPage);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mealList.clear();
                for(DataSnapshot mealSnapshot : dataSnapshot.getChildren()) {
                    Meal meal = mealSnapshot.getValue(Meal.class);
                    mealList.add(meal);
                }

                MealListAdapter mealListAdapter = new MealListAdapter(MealListPage.this, mealList);
                mealListView.setAdapter(mealListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
