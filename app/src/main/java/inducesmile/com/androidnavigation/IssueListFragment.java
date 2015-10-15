package inducesmile.com.androidnavigation;

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



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_issue, container, false);

        issueList = new ArrayList<>();

        loadData();


        return view;
    }

    public void loadData(){
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
                mAdapter = new myAdapter(issueList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
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
                    .inflate(R.layout.listissues,parent,false);

            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Issue issue = issueList.get(position);
            holder.issue_name.setText(issue.name);

        }

        @Override
        public int getItemCount() {
            return issueList.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder{
            public TextView issue_name;


            public ViewHolder(View itemView) {
                super(itemView);
                //issue_name = (TextView) itemView.findViewById(R.id.issue_name);
            }
        }
    }
}
