package leema.com.daytrip1;

/**
 * Created by leema on 2017-11-05.
 */

public class Workout {
    private String workoutName;
    private String workoutReps;
    private String workoutWeight;
    private String workoutKey;

    public Workout(String workoutKey, String workoutName, String workoutReps, String workoutWeight) {
        this.workoutKey = workoutKey;
        this.workoutName = workoutName;
        this.workoutReps = workoutReps;
        this.workoutWeight = workoutWeight;
    }

    public Workout() {

    }

    public String getWorkoutName() {
        return workoutName;
    }

    public String getWorkoutReps() {
        return workoutReps;
    }

    public String getWorkoutWeight() {
        return workoutWeight;
    }

    public String getWorkoutKey() {
        return workoutKey;
    }
}
