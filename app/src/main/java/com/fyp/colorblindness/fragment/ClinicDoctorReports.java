package com.fyp.colorblindness.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fyp.colorblindness.R;
import com.fyp.colorblindness.adapters.PatientReportsAdapter;
import com.fyp.colorblindness.models.ReportsModel;
import com.fyp.colorblindness.models.UserModelClass;
import com.fyp.colorblindness.genralclasses.Constants_values;
import com.fyp.colorblindness.genralclasses.SharedPreferenceClass;
import com.fyp.colorblindness.genralclasses.RequestsQueueVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClinicDoctorReports extends Fragment {
    View view;
    RecyclerView reports_recyclerView;
    List<ReportsModel> ItemList;
    private ProgressDialog pDialog;
    PatientReportsAdapter mAdapter;
    String user_id="";
    String getMyReportsUrl = "showReportsToDoctor";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.clinic_doctor_reports,container,false);
        initilization();
        return view;
    }

    private void initilization() {
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        reports_recyclerView = view.findViewById(R.id.recycler_view_patient_reports);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //GridLayoutManager gridLayoutManager=new GridLayoutManager(MainActivity.this,2);
        ItemList = new ArrayList<>();
        reports_recyclerView.setLayoutManager(linearLayoutManager);
        UserModelClass userModelClass= SharedPreferenceClass.getInstance(getContext()).getUser();
        if (userModelClass!=null){
            user_id=userModelClass.getUser_id();
        }
        GetReports(user_id);
    }

    private void GetReports(String user_id) {
        pDialog.setMessage("please Wait....");
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants_values.mainurl+getMyReportsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response is", response.toString());
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        Log.d("status", "CHECK" + jsonObject.getString("status"));
                        if (jsonObject.getString("status").equals("true")) {
                            ReportsModel model = new ReportsModel();
                            model.setAnswers(jsonObject.getString("answers"));
                            model.setDiagnose_result(jsonObject.getString("diagnose_result"));
                            model.setReport_id(jsonObject.getString("report_id"));
                            model.setReport_date(jsonObject.getString("report_date"));
                            model.setDoctor_id(jsonObject.getString("doctor_id"));
                            model.setReport_status(jsonObject.getString("report_status"));
                            model.setTotal_result(jsonObject.getString("total_result"));
                            model.setUsr_id(jsonObject.getString("user_id"));
                            model.setUser_mobile(jsonObject.getString("mobile"));
                            model.setUser_name(jsonObject.getString("username"));
                            ItemList.add(model);
                        } else {
                            pDialog.dismiss();
                            Toast.makeText(getContext(), "No Post", Toast.LENGTH_SHORT).show();
                        }
                    }
                    pDialog.dismiss();
                    if (ItemList != null) {
                        mAdapter = new PatientReportsAdapter(getContext(), ItemList);
                        reports_recyclerView.setAdapter(mAdapter);
                    } else {
                        Toast.makeText(getContext(), "NO DATA", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    pDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(getContext(), "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(getContext(), "Some Error", Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("doctor_id",user_id );
                return params;

            }
        };
        RequestsQueueVolley.getInstance().addRequestQueue(stringRequest);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Patient Reports");
        super.onViewCreated(view, savedInstanceState);
    }
}
