package com.fyp.colorblindness.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fyp.colorblindness.R;
import com.fyp.colorblindness.models.UserModelClass;
import com.fyp.colorblindness.genralclasses.Constants_values;
import com.fyp.colorblindness.genralclasses.SharedPreferenceClass;
import com.fyp.colorblindness.genralclasses.RequestsQueueVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResultFragment extends Fragment {
    View view;
    String SubmitReportUrl = "submitReport";
    TextView ans_1,ans_2,ans_3,ans_4,ans_5,ans_6,ans_7,ans_8,ans_9,ans_10,ans_11,ans_12,result_per,result_des;
    String ans1,ans2,ans3,ans4,ans5,ans6,ans7,ans8,ans9,ans10,ans11,ans12;
    int points=0;
    private ProgressDialog pDialog;
    Button btn_report_submit;
    int total_p;
    String doctor_id="101",report_status="pending",answers_string="",user_id="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.result_fragment,container,false);
        initialization();
        bindData();
        ShowResult();
        return view;
    }

    private void ShowResult() {
        if (ans1.equals("74")){
            points=points+1;
            ans_1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
        }
        else {
            ans_1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross, 0, 0, 0);
        }
        if (ans2.equals("6")){
            points=points+1;
            ans_2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
        }
        else {
            ans_2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross, 0, 0, 0);
        }
        if (ans3.equals("16")){
            points=points+1;
            ans_3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
        }
        else {
            ans_3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross, 0, 0, 0);
        }
        if (ans4.equals("2")){
            points=points+1;
            ans_4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
        }
        else {
            ans_4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross, 0, 0, 0);
        }
        if (ans5.equals("29")){
            points=points+1;
            ans_5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
        }
        else {
            ans_5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross, 0, 0, 0);
        }
        if (ans6.equals("7")){
            points=points+1;
            ans_6.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
        }
        else {
            ans_6.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross, 0, 0, 0);
        }
        if (ans7.equals("45")){
            points=points+1;
            ans_7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
        }
        else {
            ans_7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross, 0, 0, 0);
        }
        if (ans8.equals("5")){
            points=points+1;
            ans_8.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
        }
        else {
            ans_8.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross, 0, 0, 0);
        }
        if (ans9.equals("97")){
            points=points+1;
            ans_9.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
        }
        else {
            ans_9.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross, 0, 0, 0);
        }
        if (ans10.equals("8")){
            points=points+1;
            ans_10.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
        }
        else {
            ans_10.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross, 0, 0, 0);
        }
        if (ans11.equals("42")){
            points=points+1;
            ans_11.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
        }
        else {
            ans_11.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross, 0, 0, 0);
        }
        if (ans12.equals("3")){
            points=points+1;
            ans_12.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
        }
        else {
            ans_12.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross, 0, 0, 0);
        }

        total_p= points * 100 /12;
        //String subtotal= String.valueOf(total*100);
        Toast.makeText(getContext(), ""+points+"this is total"+total_p, Toast.LENGTH_SHORT).show();
        result_per.setText("total Result: "+total_p+"%");
        if (total_p<=32)
        {
             result_des.setText("Severe Color Vision Defieciency");
        }else if (total_p<=69){
             result_des.setText("Moderate Color Vision Defieciency");
        }else if (total_p<=89){
             result_des.setText("Mild Color Vision Defieciency");
        }else if (total_p<=100){
             result_des.setText("Normal Color Vision");
        }

    }

    private void bindData() {
        ans_1.setText(ans1);
        ans_2.setText(ans2);
        ans_3.setText(ans3);
        ans_4.setText(ans4);
        ans_5.setText(ans5);
        ans_6.setText(ans6);
        ans_7.setText(ans7);
        ans_8.setText(ans8);
        ans_9.setText(ans9);
        ans_10.setText(ans10);
        ans_11.setText(ans11);
        ans_12.setText(ans12);
        UserModelClass userModelClass= SharedPreferenceClass.getInstance(getContext()).getUser();
        if (userModelClass!=null){
            user_id=userModelClass.getUser_id();
        }
    }

    private void initialization() {
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        ans_1=view.findViewById(R.id.ans_1);
        ans_2=view.findViewById(R.id.ans_2);
        ans_3=view.findViewById(R.id.ans_3);
        ans_4=view.findViewById(R.id.ans_4);
        ans_5=view.findViewById(R.id.ans_5);
        ans_6=view.findViewById(R.id.ans_6);
        ans_7=view.findViewById(R.id.ans_7);
        ans_8=view.findViewById(R.id.ans_8);
        ans_9=view.findViewById(R.id.ans_9);
        ans_10=view.findViewById(R.id.ans_10);
        ans_11=view.findViewById(R.id.ans_11);
        ans_12=view.findViewById(R.id.ans_12);
        result_des=view.findViewById(R.id.result_diagnose);
        result_per=view.findViewById(R.id.result_total);
        btn_report_submit=view.findViewById(R.id.submit_report_btn);
        btn_report_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answers_string=ans1+","+ans2+","+ans3+","+ans4+","+ans5+","+ans6+","+ans7+","+ans8+","+ans9+","+ans10+","+ans11+","+ans12;
                if (total_p<70){
                    SubmitReport(doctor_id,user_id,answers_string,report_status,result_per.getText().toString(),result_des.getText().toString());

                }else {
                    Toast.makeText(getContext(), "Your Result is Fine", Toast.LENGTH_SHORT).show();
                }

            }
        });
        if (getArguments()!=null){
            ans1=getArguments().getString("ans1");
            ans2=getArguments().getString("ans2");
            ans3=getArguments().getString("ans3");
            ans4=getArguments().getString("ans4");
            ans5=getArguments().getString("ans5");
            ans6=getArguments().getString("ans6");
            ans7=getArguments().getString("ans7");
            ans8=getArguments().getString("ans8");
            ans9=getArguments().getString("ans9");
            ans10=getArguments().getString("ans10");
            ans11=getArguments().getString("ans11");
            ans12=getArguments().getString("ans12");
        }


    }

    private void SubmitReport(String doctor_id, String user_id, String answers_string, String report_status, String resutl_per, String result_des) {
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
                            Toast.makeText(getContext(), "Report Submitted to Doctor Successfully", Toast.LENGTH_SHORT).show();

                        } else {
                            pDialog.dismiss();
                            Toast.makeText(getContext(), " Sorry try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    pDialog.dismiss();
                    Toast.makeText(getContext(), "edit error catch"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Log.d("Edit company","Volley response errror is"+error.getMessage());
                Toast.makeText(getActivity(), "Error Please tyr again "+error.getMessage(), Toast.LENGTH_SHORT).show();

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
}
