package com.fyp.colorblindness.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fyp.colorblindness.R;
import com.fyp.colorblindness.genralclasses.Constants_values;
import com.fyp.colorblindness.genralclasses.RequestsQueueVolley;
import com.fyp.colorblindness.genralclasses.SharedPreferenceClass;
import com.fyp.colorblindness.models.UserModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FeedBackFragment extends Fragment {
    View view;
    EditText textArea;
    String complainuUrl = "addFeedback";
    Button submit;
    String User_id="",UserMobile="";
    private ProgressDialog pDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.feedback_fragment, container, false);
        intilization();
        return view;
    }

    private void intilization() {
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        final UserModelClass Model = SharedPreferenceClass.getInstance(getActivity()).getUser();
        User_id=Model.getUser_id();
        UserMobile= Model.getUser_mobile();
        textArea=view.findViewById(R.id.textArea_information);
        submit=view.findViewById(R.id.submitComplain);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!textArea.getText().toString().isEmpty() && !User_id.isEmpty() && !UserMobile.isEmpty()){
                    SendComplain(textArea.getText().toString(),User_id,UserMobile);
                }else {
                    Toast.makeText(getContext(), "Enter Your Complain", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void SendComplain(String toString, String user_id, String userMobile) {
        pDialog.setMessage("Sending....");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants_values.mainurl+complainuUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        if (jsonObject.getString("status").equals("true")) {
                            pDialog.dismiss();
                            Toast.makeText(getContext(), "Feedback added successfully", Toast.LENGTH_SHORT).show();
                            // Goto Login Page
                            getActivity().onBackPressed();
                        } else {
                            pDialog.dismiss();
                            Toast.makeText(getContext(), " Sorry try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    pDialog.dismiss();
                    Toast.makeText(getContext(), "error catch"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Log.d("Response error","Volley response errror is"+error.getMessage());
                Toast.makeText(getActivity(), "Error Please tyr again "+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", user_id);
                params.put("feedback", toString);
                params.put("mobile", userMobile);
                return params;
            }
        };
        RequestsQueueVolley.getInstance().addRequestQueue(stringRequest);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("FeedBack");
        super.onViewCreated(view, savedInstanceState);
    }

}
