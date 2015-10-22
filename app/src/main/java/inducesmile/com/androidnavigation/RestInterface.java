package inducesmile.com.androidnavigation;

import java.util.ArrayList;

import inducesmile.com.androidnavigation.ModelClass.StageChangeStatus;
import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by User on 14/10/2558.
 */
public interface RestInterface {
    @GET("/issues")
    Call<ArrayList<Issue>> searchIssue();

    @FormUrlEncoded
    @POST("/issue")
    Call<Issue> getIssue(
            @Field("issue_id") Integer issue_id
    );

    @FormUrlEncoded
    @POST("/issueWithUid")
    Call<Issue> getIssueWithUserID(
            @Field("issue_id") Integer user_id
    );

    @FormUrlEncoded
    @POST("/login")
    Call<User> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/customer")
    Call<Customer> getCustomer(
            @Field("customer_id") Integer customer_id
    );

    @FormUrlEncoded
    @POST("/updateStage")
    Call<StageChangeStatus> updateStage(
            @Field("issue_id") Integer issue_id,
            @Field("stage_id") Integer stage_id
    );



}
