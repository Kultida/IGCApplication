package inducesmile.com.androidnavigation.DetailFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import inducesmile.com.androidnavigation.Issue;
import inducesmile.com.androidnavigation.R;
import inducesmile.com.androidnavigation.RestInterface;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class IssueDetailFragment extends Fragment{
    private ArrayList<Issue> issues;

    public static IssueDetailFragment newInstance() {
        IssueDetailFragment fragment = new IssueDetailFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_issue_detail, container, false);

        issues = new ArrayList<>();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://igc.kmodoo.com:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestInterface issuelistapi = retrofit.create(RestInterface.class);


        Call<ArrayList<Issue>> issueListing = issuelistapi.searchIssue();

        issueListing.enqueue(new Callback<ArrayList<Issue>>() {
            @Override
            public void onResponse(Response<ArrayList<Issue>> response) {
                issues = response.body();

                TextView issue_name = (TextView) view.findViewById(R.id.issue_name);
                issue_name.setText(issues.get(0).getName());
                TextView issue_description = (TextView) view.findViewById(R.id.issue_name);
                issue_description.setText(issues.get(0).getDescription());

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });



        return view;
    }
}