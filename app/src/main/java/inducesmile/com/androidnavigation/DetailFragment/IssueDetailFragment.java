package inducesmile.com.androidnavigation.DetailFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import inducesmile.com.androidnavigation.Issue;
import inducesmile.com.androidnavigation.R;
import inducesmile.com.androidnavigation.RestInterface;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class IssueDetailFragment extends Fragment{
    private Issue issue;
    public int issue_id;

    public static IssueDetailFragment newInstance(int issue_id) {
        IssueDetailFragment fragment = new IssueDetailFragment();
        fragment.issue_id = issue_id;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_issue_detail, container, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://igc.kmodoo.com:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestInterface issueDetail = retrofit.create(RestInterface.class);

        Log.i("TEST","Showing detail of issue_id: "+Integer.toString(issue_id));

        Call<Issue> issueListing = issueDetail.getIssue(issue_id);

        issueListing.enqueue(new Callback<Issue>() {
            @Override
            public void onResponse(Response<Issue> response) {
                issue = response.body();
                Log.i("test",issue.getName());
                TextView issue_name = (TextView) view.findViewById(R.id.issue_name);
                issue_name.setText(issue.getName());
                TextView issue_description = (TextView) view.findViewById(R.id.issue_description);
                issue_description.setText(issue.getDescription());



            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("test","Error");
            }
        });



        return view;
    }
}