package leema.com.daytrip1;

/**
 * Created by leema on 2017-10-22.
 */

public class GoalObject {
    private String goalName;
    private String goalDescription;

    public GoalObject(String goalName, String goalDescription) {
        this.goalName = goalName;
        this.goalDescription = goalDescription;
    }
    public GoalObject() {

    }

    public String getGoalName() {
        return goalName;
    }

    public String getGoalDescription() {
        return goalDescription;
    }

}
