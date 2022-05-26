package com.fyp.colorblindness.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.colorblindness.R;
import com.fyp.colorblindness.activities.DoctorsList;
import com.fyp.colorblindness.activities.MainActivity;
import com.fyp.colorblindness.models.UserModelClass;

import java.util.ArrayList;
import java.util.List;

public class DoctorsListReportAdapter extends RecyclerView.Adapter<DoctorsListReportAdapter.ViewHolder> {
    Context context;
    List<UserModelClass> modelList;
    ArrayList<UserModelClass> arrayList;

    public DoctorsListReportAdapter(DoctorsList doctorsList, List<UserModelClass> itemList) {
        this.context = doctorsList;
        this.modelList = itemList;
        this.arrayList=new ArrayList<UserModelClass>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_report_doctors_list_viewholder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final UserModelClass model = modelList.get(position);
        holder.tv_dr_name.setText(model.getUser_name());
        holder.tv_dr_profession.setText(model.getSpecialization());
        holder.tv_dr_bio.setText(model.getCompany_address());
        holder.tv_send_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((DoctorsList) context).doctor_id(model.getUser_id());

            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_dr_name,tv_dr_bio,tv_dr_profession,tv_send_report;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_dr_bio=itemView.findViewById(R.id.txt_dr_bio);
            tv_dr_profession=itemView.findViewById(R.id.txt_dr_profession);
            tv_dr_name=itemView.findViewById(R.id.txt_dr_name);
            tv_send_report=itemView.findViewById(R.id.btn_send_report);
        }
    }
}
