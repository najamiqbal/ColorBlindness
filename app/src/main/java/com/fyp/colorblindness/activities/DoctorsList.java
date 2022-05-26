package com.fyp.colorblindness.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fyp.colorblindness.R;
import com.fyp.colorblindness.adapters.DoctorsListAdapter;
import com.fyp.colorblindness.adapters.DoctorsListReportAdapter;
import com.fyp.colorblindness.genralclasses.Constants_values;
import com.fyp.colorblindness.genralclasses.RequestsQueueVolley;
import com.fyp.colorblindness.models.UserModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorsList extends AppCompatActivity {
    RecyclerView dr_recyclerView;
    List<UserModelClass> ItemList;
    private ProgressDialog pDialog;
    DoctorsListReportAdapter mAdapter;
    String getDoctorsUrl = "getDoctors";
    String SubmitReportUrl = "submitReport";
    String doctor_id="",report_status="",answers_string="",user_id="", resutl_per="", result_des="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctors_list_reportsend);
        initialization();
    }

    private void initialization() {
        Intent intent = getIntent();
        report_status=intent.getStringExtra("report_status");
        answers_string=intent.getStringExtra("ans");
        user_id=intent.getStringExtra("user_id");
        resutl_per=intent.getStringExtra("result_per");
        result_des=intent.getStringExtra("result_des");

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        dr_recyclerView = findViewById(R.id.recycler_view_doctorsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //GridLayoutManager gridLayoutManager=new GridLayoutManager(MainActivity.this,2);
        ItemList = new ArrayList<>();
        dr_recyclerView.setLayoutManager(linearLayoutManager);
        GetDoctors();
    }


    public void doctor_id(String doctor_id){
        //Toast.makeText(DoctorsList.this, ""+doctor_id, Toast.LENGTH_SHORT).show();
        SubmitReport(doctor_id,user_id,answers_string,report_status,resutl_per,result_des);
    }


    public void SubmitReport(String doctor_id, String user_id, String answers_string, String report_status, String resutl_per, String result_des) {
        pDialog.setMessage("Updating....");
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants_values.mainurl+SubmitReportUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.hide();
                Log.d("EditCompany Fragment","editCompany method call"+response.toString());
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        if (jsonObject.getString("status").equals("true")) {
                            pDialog.dismiss();
                            Toast.makeText(DoctorsList.this, "Report Submitted to Doctor Successfully", Toast.LENGTH_SHORT).show();

                        } else {
                            pDialog.dismiss();
                            Toast.makeText(DoctorsList.this, " Sorry try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    pDialog.dismiss();
                    Toast.makeText(DoctorsList.this, "edit error catch"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Log.d("Edit company","Volley response errror is"+error.getMessage());
                Toast.makeText(DoctorsList.this, "Error Please tyr again "+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("doctor_id", doctor_id);
                params.put("answers", answers_string);
                params.put("total_result", resutl_per);
                params.put("diagnose_result", result_des);
                params.put("report_status", report_status);
                params.put("user_id", user_id);
                return params;
            }
        };
        RequestsQueueVolley.getInstance().addRequestQueue(stringRequest);
    }




    private void GetDoctors() {
        pDialog.setMessage("please Wait....");
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants_values.mainurl+getDoctorsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response is", response.toString());
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        Log.d("status", "CHECK" + jsonObject.getString("status"));
                        if (jsonObject.getString("status").equals("true")) {
                            UserModelClass model = new UserModelClass();
                            model.setUser_name(jsonObject.getString("username"));
                            model.setUser_email(jsonObject.getString("email"));
                            model.setUser_mobile(jsonObject.getString("mobile"));
                            model.setUser_address(jsonObject.getString("address"));
                            model.setUser_id(jsonObject.getString("doctor_id"));
                            model.setDoctor_bio(jsonObject.getString("description"));
                            model.setSpecialization(jsonObject.getString("specialization"));
                            model.setCompany_address(jsonObject.getString("doctor_city"));
                            ItemList.add(model);
                        } else {
                            pDialog.dismiss();
                            Toast.makeText(DoctorsList.this, "No Post", Toast.LENGTH_SHORT).show();
                        }
                    }
                    pDialog.dismiss();
                    if (ItemList != null) {
                        mAdapter = new DoctorsListReportAdapter(DoctorsList.this, ItemList);
                        dr_recyclerView.setAdapter(mAdapter);
                    } else {
                        Toast.makeText(DoctorsList.this, "NO DATA", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    pDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(DoctorsList.this, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(DoctorsList.this, "Some Error", Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("specialization","category" );
                return params;

            }
        };
        RequestsQueueVolley.getInstance().addRequestQueue(stringRequest);
    }
}
