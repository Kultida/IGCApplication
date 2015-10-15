package inducesmile.com.androidnavigation;

import java.util.List;

/**
 * Created by User on 13/10/2558.
 */
public class Issue {
    int id;
    String name;
    String description;
    List<Object> user_id;
    List<Object> stage_id;
    List<Object> partner_id;
    String create_date;
    String date_deadline;
    int priority;

    public Issue(String n,int i,String d,List<Object> u){
        name = n;
        id = i;
        description = d;
        user_id = u;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Object> getUser_id() {
        return user_id;
    }
}

