package inducesmile.com.androidnavigation.DetailFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import inducesmile.com.androidnavigation.Customer;
import inducesmile.com.androidnavigation.Issue;
import inducesmile.com.androidnavigation.ModelClass.StageChangeStatus;
import inducesmile.com.androidnavigation.R;
import inducesmile.com.androidnavigation.RestInterface;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class IssueDetailFragment extends Fragment{
    private Issue issue;
    private Customer customer;
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
            public void onResponse(Response<Issue> response, Retrofit retrofit) {
                issue = response.body();
                Log.i("test", issue.getName());
                TextView issue_name = (TextView) view.findViewById(R.id.issue_name);
                issue_name.setText(issue.getName());
                TextView issue_description = (TextView) view.findViewById(R.id.issue_description);
                issue_description.setText(issue.getDescription());
                TextView issue_deadline = (TextView) view.findViewById(R.id.issue_deadline);
                issue_deadline.setText(issue.date_deadline);
                Double customer_id = (Double) issue.partner_id.get(0);
                getCustomer(customer_id, view);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("test", "Error");
            }
        });


        //Handle Button
        //1 = Quotation
        //2 = In progress
        //3 = Done
        //4 = cancel
        //5 = Reported
        final Button rejectButton = (Button) view.findViewById(R.id.reject_button);
        rejectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                stageChange(issue.id, 4);
            }
        });
        final Button acceptButton = (Button) view.findViewById(R.id.accept_button);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                stageChange(issue.id, 2);
            }
        });




        return view;
    }

    public void getCustomer(Double customer_id, final View viewInCustomer) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://igc.kmodoo.com:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestInterface rest = retrofit.create(RestInterface.class);

        Log.i("TEST", "Address of customer_id: " + Integer.toString(customer_id.intValue()));
        Call<Customer> getCustomer = rest.getCustomer(customer_id.intValue());

        getCustomer.enqueue(new Callback<Customer>() {

            @Override
            public void onResponse(Response<Customer> response, Retrofit retrofit) {
                customer = response.body();
                TextView issue_name = (TextView) viewInCustomer.findViewById(R.id.customer_address);
                issue_name.setText(customer.street);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("test", "ERROR!!");
            }
        });
    }

    public void stageChange(int issue_id,int stage_id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://igc.kmodoo.com:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestInterface rest = retrofit.create(RestInterface.class);


        Call<StageChangeStatus> changeStage = rest.updateStage(issue_id, stage_id);

        changeStage.enqueue(new Callback<StageChangeStatus>() {

            @Override
            public void onResponse(Response<StageChangeStatus> response, Retrofit retrofit) {
                StageChangeStatus stageChangeStatus = response.body();
                if (stageChangeStatus.isSuccess)
                    Log.i("test", "update SUCCESS");
                else
                    Log.i("test", "update failed");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("test", "ERROR!!");
            }
        });

    }
}