package leema.com.daytrip1;

/**
 * Created by leema on 2017-10-22.
 */

public class GoalObject {
    private String key;
    private String goalName;
    private String goalDescription;

    public GoalObject(String key, String goalName, String goalDescription) {
        this.goalName = goalName;
        this.goalDescription = goalDescription;
        this.key = key;
    }
    public GoalObject() {

    }

    public String getGoalName() {
        return goalName;
    }

    public String getGoalDescription() {
        return goalDescription;
    }

    public String getKey() {
        return key;
    }
}
