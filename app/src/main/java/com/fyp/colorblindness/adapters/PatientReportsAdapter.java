package com.fyp.colorblindness.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fyp.colorblindness.R;
import com.fyp.colorblindness.fragment.ReportDetailFragment;
import com.fyp.colorblindness.models.ReportsModel;
import com.fyp.colorblindness.utils.AppConstants;
import com.fyp.colorblindness.utils.VolleyRequestsent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientReportsAdapter extends RecyclerView.Adapter<PatientReportsAdapter.ViewHolder> {
    Context context;
    List<ReportsModel> modelList;
    ArrayList<ReportsModel> arrayList;
    String updateReportStatusurl="updateReportStatus";
    private ProgressDialog pDialog;
    public PatientReportsAdapter(Context context, List<ReportsModel> itemList) {
        this.context = context;
        this.modelList = itemList;
        this.arrayList=new ArrayList<ReportsModel>();
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
    }
    @NonNull
    @Override
    public PatientReportsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_reports_list_viewholder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientReportsAdapter.ViewHolder holder, int position) {
        final ReportsModel model = modelList.get(position);
        holder.txt_patient_name.setText(model.getUser_name());
        holder.txt_total_result.setText(model.getTotal_result());
        holder.txt_diagnose.setText("Diagnose Result: "+model.getDiagnose_result());
        holder.txt_date.setText("Date: "+model.getReport_date());
        holder.txt_reportstatus.setText("Status: "+model.getReport_status());
        if (model.getReport_status().equals("pending")){
            holder.buttonslayout.setVisibility(View.VISIBLE);
        }

        holder.call_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + model.getUser_mobile()));
                context.startActivity(intentDial);
            }
        });
        holder.btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateReportStatus(model.getReport_id(),"reject");
            }
        });
        holder.btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateReportStatus(model.getReport_id(),"accept");
            }
        });
    }



    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_patient_name, txt_total_result,txt_diagnose,txt_reportstatus,txt_date, call_patient,btn_reject,btn_accept;
        LinearLayout buttonslayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_patient_name=itemView.findViewById(R.id.txt_patient_name);
            txt_total_result=itemView.findViewById(R.id.txt_total_result);
            txt_diagnose=itemView.findViewById(R.id.txt_draddress);
            txt_reportstatus=itemView.findViewById(R.id.txt_report_status);
            txt_date=itemView.findViewById(R.id.txt_report_date);
            call_patient=itemView.findViewById(R.id.txt_call_patient);
            btn_accept=itemView.findViewById(R.id.btn_accept_report);
            btn_reject=itemView.findViewById(R.id.btn_reject_report);
            buttonslayout=itemView.findViewById(R.id.buttons_layout);
        }
    }

    private void UpdateReportStatus(String report_id, String reject) {
        pDialog.setMessage("Please Wait....");
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.mainurl+updateReportStatusurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response is", response.toString());
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        Log.d("status", "CHECK" + jsonObject.getString("status"));
                        if (jsonObject.getString("status").equals("true")) {
                            pDialog.dismiss();
                            Toast.makeText(context, "Status Updated", Toast.LENGTH_SHORT).show();
                            ((Activity)context).recreate();
                        } else {
                            pDialog.dismiss();
                            Toast.makeText(context, "No Post", Toast.LENGTH_SHORT).show();
                        }
                    }
                    pDialog.dismiss();
                } catch (JSONException e) {
                    pDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(context, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(context, "Some Error", Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("report_id",report_id );
                params.put("report_status",reject );
                return params;

            }
        };
        VolleyRequestsent.getInstance().addRequestQueue(stringRequest);
    }
}
