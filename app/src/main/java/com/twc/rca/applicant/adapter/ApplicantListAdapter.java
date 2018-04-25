package com.twc.rca.applicant.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twc.rca.R;
import com.twc.rca.applicant.model.ApplicantModel;
import com.twc.rca.product.activities.ApplicantActivity;

import java.util.List;

/**
 * Created by Sushil on 15-03-2018.
 */

public class ApplicantListAdapter extends RecyclerView.Adapter<ApplicantListAdapter.MyViewHolder> {
    Context context;

    private List<ApplicantModel> applicantModelList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_applicant_name, tv_applicant_type;

        public MyViewHolder(View view) {
            super(view);
            tv_applicant_name = (TextView) view.findViewById(R.id.tv_applicant_name);
            tv_applicant_type = (TextView) view.findViewById(R.id.tv_applicant_type);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ApplicantActivity.class);
                    intent.putExtra("applicant_type", applicantModelList.get(getAdapterPosition()).getApplicantType());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    public ApplicantListAdapter(Context context, List<ApplicantModel> applicantModelList) {
        this.context = context;
        this.applicantModelList = applicantModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_applicant_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ApplicantModel applicantModel = applicantModelList.get(position);
        holder.tv_applicant_name.setText(applicantModel.getApplicantName());
        holder.tv_applicant_type.setText(applicantModel.getApplicantType());
    }

    @Override
    public int getItemCount() {
        return applicantModelList.size();
    }
}
