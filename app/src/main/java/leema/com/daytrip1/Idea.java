package leema.com.daytrip1;

/**
 * Created by leema on 2017-11-08.
 */

public class Idea {
    private String ideaName;
    private String key;
    private String ideaSummary;

    public Idea(String key, String ideaName, String ideaSummary) {
        this.key = key;
        this.ideaName = ideaName;
        this.ideaSummary = ideaSummary;
    }

    public Idea() {

    }

    public String getIdeaName() {
        return ideaName;
    }

    public String getKey() {
        return key;
    }

    public String getIdeaSummary() {
        return ideaSummary;
    }
}
