package com.fyp.colorblindness.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.colorblindness.R;
import com.fyp.colorblindness.fragment.ReportDetailFragment;
import com.fyp.colorblindness.models.ReportsModel;

import java.util.ArrayList;
import java.util.List;

public class MyReportsAdapter extends RecyclerView.Adapter<MyReportsAdapter.ViewHolder> {
    Context context;
    List<ReportsModel> modelList;
    ArrayList<ReportsModel> arrayList;
    public MyReportsAdapter(Context context, List<ReportsModel> itemList) {
        this.context = context;
        this.modelList = itemList;
        this.arrayList=new ArrayList<ReportsModel>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reports_list_viewholder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ReportsModel model = modelList.get(position);
        holder.txt_reportid.setText("Report ID: "+model.getReport_id());
        holder.txt_total_result.setText(model.getTotal_result());
        holder.txt_diagnose.setText("Diagnose Result: "+model.getDiagnose_result());
        holder.txt_date.setText("Date: "+model.getReport_date());
        holder.txt_reportstatus.setText("Status: "+model.getReport_status());
        holder.detail_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                ReportDetailFragment fragment=new ReportDetailFragment();
                Bundle args = new Bundle();
                args.putString("report_id", model.getReport_id());
                args.putString("report_result", model.getTotal_result());
                args.putString("report_diagnose", model.getDiagnose_result());
                args.putString("report_date", model.getReport_date());
                args.putString("report_status", model.getReport_status());
                args.putString("report_answers", model.getAnswers());
                fragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.user_main_frame, fragment).addToBackStack("fragment").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_reportid, txt_total_result,txt_diagnose,txt_reportstatus,txt_date, detail_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_reportid=itemView.findViewById(R.id.txt_report_id);
            txt_total_result=itemView.findViewById(R.id.txt_total_result);
            txt_diagnose=itemView.findViewById(R.id.txt_draddress);
            txt_reportstatus=itemView.findViewById(R.id.txt_report_status);
            txt_date=itemView.findViewById(R.id.txt_report_date);
            detail_view=itemView.findViewById(R.id.txt_detai_view);
        }
    }
}
