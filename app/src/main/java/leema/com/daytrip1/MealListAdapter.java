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
 * Created by leema on 2017-11-08.
 */

public class MealListAdapter extends ArrayAdapter<Meal> {

    private Activity mActivity;
    private List<Meal> mealList;

    public MealListAdapter(Activity mActivity, List<Meal> mealList) {
        super(mActivity, R.layout.list_view_meal, mealList);

        this.mActivity = mActivity;
        this.mealList = mealList;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = mActivity.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_view_meal, null, true);

        TextView mealName = listViewItem.findViewById(R.id.meal_box_name);
        TextView mealCalories = listViewItem.findViewById(R.id.meal_box_calories);
        TextView mealProtein = listViewItem.findViewById(R.id.meal_box_protein);
        TextView mealFat = listViewItem.findViewById(R.id.meal_fat);
        TextView mealSugar = listViewItem.findViewById(R.id.meal_sugar);
        Button deleteMealButton = listViewItem.findViewById(R.id.delete_button_meal);

        final Meal meal = mealList.get(position);

        mealName.setText(meal.getMealName());
        mealCalories.setText("Calories: " + meal.getMealCalories());
        mealProtein.setText("Protein: " + meal.getMealProtein() + " grams");
        mealFat.setText("Fat: " + meal.getMealFat() + " grams");
        mealSugar.setText("Sugar: " + meal.getMealSugar() + " grams");

        deleteMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMeal(meal.getKey());
            }
        });



        return listViewItem;
    }

    private void deleteMeal(String key) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("meal").child(key);
        ref.removeValue();

    }

}
