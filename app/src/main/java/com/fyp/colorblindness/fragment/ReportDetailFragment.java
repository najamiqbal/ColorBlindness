package com.fyp.colorblindness.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fyp.colorblindness.R;
import com.fyp.colorblindness.genralclasses.Constants_values;
import com.fyp.colorblindness.genralclasses.RequestsQueueVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReportDetailFragment extends Fragment implements View.OnClickListener {
    View view;
    TextView tv_reportID, tv_report_result, tv_report_diagnose, tv_report_date, tv_report_status, tv_no_doctor, dr_name,dr_specialization,dr_address,tv_dr_email,tv_dr_call,tv_dr_whatsapp;
    String v_reportid, v_reportstatus, v_reportdate, v_reportdiagnose, v_result_total, v_answers,v_dr_email,v_dr_mobile;
    String ans1, ans2, ans3, ans4, ans5, ans6, ans7, ans8, ans9, ans10, ans11, ans12;
    TextView tv_ans1, tv_ans2, tv_ans3, tv_ans4, tv_ans5, tv_ans6, tv_ans7, tv_ans8, tv_ans9, tv_ans10, tv_ans11, tv_ans12;
    private ProgressDialog pDialog;
    CardView cardViewdoctor;
    String GetdoctorDetailURL = "getDoctorByReport";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.report_detail_fragment, container, false);
        initialization();
        BindData();
        if (v_reportstatus.equals("approved")) {
            cardViewdoctor.setVisibility(View.VISIBLE);
            GetdoctorDetail(v_reportid);
        }
        return view;
    }

    private void GetdoctorDetail(String v_reportid) {
        pDialog.setMessage("Wait....");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants_values.mainurl+GetdoctorDetailURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.hide();
                Log.d("EditCompany Fragment", "editCompany method call" + response.toString());
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        if (jsonObject.getString("status").equals("true")) {
                            pDialog.dismiss();
                            dr_address.setText(jsonObject.getString("doctor_city"));
                            dr_specialization.setText(jsonObject.getString("specialization"));
                            dr_name.setText(jsonObject.getString("username"));
                            v_dr_email=jsonObject.getString("email");
                            v_dr_mobile=jsonObject.getString("mobile");

                        } else {
                            pDialog.dismiss();
                            Toast.makeText(getContext(), " Sorry try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    pDialog.dismiss();
                    Toast.makeText(getContext(), "edit error catch" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Log.d("Edit company", "Volley response errror is" + error.getMessage());
                Toast.makeText(getActivity(), "Error Please tyr again " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("report_id", v_reportid);

                return params;
            }
        };
        RequestsQueueVolley.getInstance().addRequestQueue(stringRequest);

    }

    private void BindData() {
        tv_reportID.setText("Report ID: "+v_reportid);
        tv_report_date.setText("Date: "+v_reportdate);
        tv_report_status.setText("Status: "+v_reportstatus);
        tv_report_diagnose.setText(v_reportdiagnose);
        tv_report_result.setText(v_result_total);
        tv_ans1.setText(ans1);
        tv_ans2.setText(ans2);
        tv_ans3.setText(ans3);
        tv_ans4.setText(ans4);
        tv_ans5.setText(ans5);
        tv_ans6.setText(ans6);
        tv_ans7.setText(ans7);
        tv_ans8.setText(ans8);
        tv_ans9.setText(ans9);
        tv_ans10.setText(ans10);
        tv_ans11.setText(ans11);
        tv_ans12.setText(ans12);
    }

    private void initialization() {
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        cardViewdoctor=view.findViewById(R.id.CardViewDoctor);
        tv_no_doctor=view.findViewById(R.id.txt_no_dr);
        tv_reportID=view.findViewById(R.id.txt_report_id);
        tv_report_date=view.findViewById(R.id.report_date);
        tv_report_status=view.findViewById(R.id.report_status);
        tv_report_result=view.findViewById(R.id.result_total);
        dr_address=view.findViewById(R.id.txt_draddress);
        dr_name=view.findViewById(R.id.txt_drname);
        dr_specialization=view.findViewById(R.id.txt_dr_specialization);
        tv_dr_call=view.findViewById(R.id.btn_dr_call);
        tv_dr_email=view.findViewById(R.id.btn_dr_email);
        tv_dr_whatsapp=view.findViewById(R.id.btn_dr_whatsapp);
        tv_report_diagnose=view.findViewById(R.id.result_diagnose);
        tv_ans1=view.findViewById(R.id.ans_1);
        tv_ans2=view.findViewById(R.id.ans_2);
        tv_ans3=view.findViewById(R.id.ans_3);
        tv_ans4=view.findViewById(R.id.ans_4);
        tv_ans5=view.findViewById(R.id.ans_5);
        tv_ans6=view.findViewById(R.id.ans_6);
        tv_ans7=view.findViewById(R.id.ans_7);
        tv_ans8=view.findViewById(R.id.ans_8);
        tv_ans9=view.findViewById(R.id.ans_9);
        tv_ans10=view.findViewById(R.id.ans_10);
        tv_ans11=view.findViewById(R.id.ans_11);
        tv_ans12=view.findViewById(R.id.ans_12);
        if (getArguments() != null) {
            v_reportid = getArguments().getString("report_id");
            v_reportdate = getArguments().getString("report_date");
            v_reportstatus = getArguments().getString("report_status");
            v_reportdiagnose = getArguments().getString("report_diagnose");
            v_result_total = getArguments().getString("report_result");
            v_answers = getArguments().getString("report_answers");
            Log.d("singin", "LOVE" + v_answers);
        } else {
            Toast.makeText(getActivity(), "Basic info not save", Toast.LENGTH_SHORT).show();
        }
        String[] namesList = v_answers.split(",");
        ans1 = namesList [0];
        ans2 = namesList [1];
        ans3 = namesList [2];
        ans4 = namesList [3];
        ans5 = namesList [4];
        ans6 = namesList [5];
        ans7 = namesList [6];
        ans8 = namesList [7];
        ans9 = namesList [8];
        ans10 = namesList [9];
        ans11 = namesList [10];
        ans12 = namesList [11];
        Log.d("singin", "LOVE" + ans1+ans2+ans3+ans4+ans5+ans6+ans7+ans8+ans9+ans10+ans11+ans12);

        tv_dr_call.setOnClickListener(this);
        tv_dr_email.setOnClickListener(this);
        tv_dr_whatsapp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_dr_call:
                Intent intentDial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + v_dr_mobile));
                startActivity(intentDial);
                break;
            case R.id.btn_dr_email:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + v_dr_email));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "ColorBlindness issue");
                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
                break;
            case R.id.btn_dr_whatsapp:
                openWhatsApp(v_dr_mobile);
                break;
        }
    }

    private void openWhatsApp(String number) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+92"+number + "&text="+"hello"));
        startActivity(intent);
    }
}
