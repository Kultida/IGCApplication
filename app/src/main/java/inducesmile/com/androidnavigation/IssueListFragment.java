package inducesmile.com.androidnavigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import inducesmile.com.androidnavigation.ModelClass.StageChangeStatus;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by gnomerock on 10/15/15.
 */
public class IssueListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Issue> issueList;

    public static final String PREF_NAME = "USER_ID";
    private Integer myUser_id;
    private SharedPreferences sharedPreferences;

    public static IssueListFragment newInstance() {
        IssueListFragment fragment = new IssueListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_issue, container, false);
        issueList = new ArrayList<>();
        sharedPreferences = getActivity().getSharedPreferences(PREF_NAME, 0);
        myUser_id = sharedPreferences.getInt("user_id",0);
        if(myUser_id == 0){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("user_id",10);
            myUser_id = 10;
        }

        Log.i("user_id","My user id = "+Integer.toString(myUser_id));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://igc.kmodoo.com:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestInterface issuelistapi = retrofit.create(RestInterface.class);


        Call<ArrayList<Issue>> issueListing = issuelistapi.getIssueWithUserID(myUser_id);

        issueListing.enqueue(new Callback<ArrayList<Issue>>() {

            @Override
            public void onResponse(Response<ArrayList<Issue>> response, Retrofit retrofit) {
                issueList = response.body();

//                Log.i("DATA", issueList.get(0).create_date);
//                Log.i("DATA",issueList.get(0).date_deadline);
                mAdapter = new myAdapter(issueList);
                mRecyclerView.setAdapter(mAdapter);


            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);


        testLogin();
        testCustomer();
        testChangeStage();

        return view;
    }

    private class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {

        private ArrayList<Issue> issueList;

        public myAdapter(ArrayList<Issue> issueList) {
            this.issueList = issueList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.result_issue, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Issue issue = issueList.get(position);
            holder.issue_name.setText(issue.name);
            holder.issue_id.setText(Integer.toString(issue.id));
            holder.issue_description.setText(issue.description);
            holder.issue_userid.setText((String) issue.user_id.get(1));



        }

        @Override
        public int getItemCount() {
            return issueList.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView issue_name;
            public TextView issue_id;
            public TextView issue_description;
            public TextView issue_userid;


            public ViewHolder(View itemView) {
                super(itemView);
                itemView.setClickable(true);
                itemView.setOnClickListener(this);
                issue_name = (TextView) itemView.findViewById(R.id.issue_name);
                issue_id = (TextView) itemView.findViewById(R.id.issue_id);
                issue_description = (TextView) itemView.findViewById(R.id.issue_description);
                issue_userid = (TextView) itemView.findViewById(R.id.issue_user_id);


            }

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailActivity.class);

                //Send issue id to new activity
                TextView textView = (TextView) v.findViewById(R.id.issue_id);
                Log.i("test","sending issue id to new Detail Act"+textView.getText().toString());
                intent.putExtra("issue_id",textView.getText().toString());

                startActivity(intent);
            }

        }
    }

    public void testLogin() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://igc.kmodoo.com:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestInterface rest = retrofit.create(RestInterface.class);


        Call<User> getUID = rest.login("admin", "qwer1234");

        getUID.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                User user = response.body();

                Log.i("test", "SUCCESS" + Integer.toString(user.uid));
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("test", "ERROR!!");
            }
        });
    }

    public void testChangeStage() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://igc.kmodoo.com:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestInterface rest = retrofit.create(RestInterface.class);


        Call<StageChangeStatus> changeStage = rest.updateStage(4863, 1);

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

    public void testCustomer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://igc.kmodoo.com:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestInterface rest = retrofit.create(RestInterface.class);


        Call<Customer> getCustomer = rest.getCustomer(15);

        getCustomer.enqueue(new Callback<Customer>() {

            @Override
            public void onResponse(Response<Customer> response, Retrofit retrofit) {
                Customer customer = response.body();
                Log.i("test", "SUCCESS" + customer.name);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("test", "ERROR!!");
            }
        });
    }
}
