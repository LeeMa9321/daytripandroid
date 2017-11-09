package leema.com.daytrip1;

/**
 * Created by leema on 2017-11-08.
 */

public class Meal {
    private String mealName;
    private String mealCalories;
    private String mealProtein;
    private String mealFat;
    private String mealSugar;
    private String key;

    public Meal(String key, String mealName, String mealCalories, String mealProtein, String mealFat, String mealSugar) {
       this.key = key;
       this.mealCalories = mealCalories;
       this.mealFat = mealFat;
       this.mealProtein = mealProtein;
       this.mealSugar = mealSugar;
       this.mealName = mealName;
    }

    public Meal() {

    }

    public String getMealName() {
        return mealName;
    }

    public String getMealCalories() {
        return mealCalories;
    }

    public String getMealProtein() {
        return mealProtein;
    }

    public String getMealFat() {
        return mealFat;
    }

    public String getMealSugar() {
        return mealSugar;
    }

    public String getKey() {
        return key;
    }
}
