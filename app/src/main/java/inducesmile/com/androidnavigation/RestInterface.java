package inducesmile.com.androidnavigation;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by User on 14/10/2558.
 */
public interface RestInterface {
    @GET("/issue")
    Call<ArrayList<Issue>> searchIssue();
    //@GET("/issue")
    //Call<Issue> getUsersNamedTom(@Query("q") String name);
}
