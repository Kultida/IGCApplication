package inducesmile.com.androidnavigation;

/**
 * Created by User on 13/10/2558.
 */
public class Issue {
    String id;
    String name;
    String description;
    String[] user_id;



    public Issue(String n,String i,String d,String[] u){
        name = n;
        id = i;
        description = d;
        user_id = u;
    }



    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String[] getUser_id() {
        return user_id;
    }
}

