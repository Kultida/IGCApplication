package inducesmile.com.androidnavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

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

    public static IssueListFragment newInstance() {
        IssueListFragment fragment = new IssueListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_issue, container, false);
        issueList = new ArrayList<>();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://igc.kmodoo.com:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestInterface issuelistapi = retrofit.create(RestInterface.class);


        Call<ArrayList<Issue>> issueListing = issuelistapi.searchIssue();

        issueListing.enqueue(new Callback<ArrayList<Issue>>() {
            @Override
            public void onResponse(Response<ArrayList<Issue>> response) {
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

        return view;
    }

    private class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {

        private ArrayList<Issue> issueList;
        public myAdapter(ArrayList<Issue> issueList) {
            this.issueList = issueList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view  = LayoutInflater
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



        public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
                Intent intent = new Intent(getContext(),DetailActivity.class);
                startActivity(intent);
            }

        }
    }
}
