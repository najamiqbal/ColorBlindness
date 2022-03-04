package com.fyp.colorblindness.fragment;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fyp.colorblindness.R;
import com.fyp.colorblindness.utils.AppConstants;
import com.fyp.colorblindness.utils.VolleyRequestsent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VerifyCodeFragment extends Fragment {
    View view;
    private ProgressDialog pDialog;
    Button verify;
    TextView timer;
    EditText Code;
    Handler handler;
    int count = 120;
    String user_mobile="",user_Name="",user_Password="",user_Email="",user_Address="",verification_code="",user_type="",dr_bio="",clinic_address="",dr_specialization="";
    String registration_url = "register";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.verify_code_fragment,container,false);
        initialization();
        return view;
    }

    private void initialization() {
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        verify = view.findViewById(R.id.btn_verify);
        timer = view.findViewById(R.id.resettimer);
        handler = new Handler(getActivity().getMainLooper());
        Code = view.findViewById(R.id.verificationcode);
        if (getArguments()!=null){
            user_type = getArguments().getString("user_type");
            if (user_type.equals("2")){
                user_mobile = getArguments().getString("Mobile");
                user_Name = getArguments().getString("Name");
                user_Email = getArguments().getString("Email");
                user_Address = getArguments().getString("Address");
                user_Password = getArguments().getString("Password");
                verification_code = getArguments().getString("code");
            }else {

                user_mobile = getArguments().getString("Mobile");
                user_Name = getArguments().getString("Name");
                user_Email = getArguments().getString("Email");
                user_Address = getArguments().getString("Address");
                user_Password = getArguments().getString("Password");
                verification_code = getArguments().getString("code");
                dr_specialization = getArguments().getString("drspe");
                dr_bio = getArguments().getString("CompDesc");
                clinic_address = getArguments().getString("ClinicAddress");

            }


             Log.d("VerifyCode","user Data  "+user_type+user_Name+user_mobile+user_Email+user_Address+user_Password+verification_code+dr_specialization+dr_bio+clinic_address);

        }

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Code.getText().toString().isEmpty()) {

                    if(Code.getText().toString().equals(verification_code)){

                        // call registration method
                        if (user_type.equals("2")) {
                            UserRegistration(user_Name, user_Email, user_mobile, user_Address, user_Password, user_type);
                        }
                        else {
                            if (!user_Password.isEmpty() &&!user_Name.isEmpty() && !user_Email.isEmpty() && !user_type.isEmpty() && !user_mobile.isEmpty() && !user_Address.isEmpty() && !dr_specialization.isEmpty() && !dr_bio.isEmpty() && !clinic_address.isEmpty())
                            {
                                DrRegistration(user_Name, user_Email, user_mobile, user_Address, user_Password, user_type,dr_bio,dr_specialization,clinic_address);
                            }else {
                                Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
                            }

                        }

                       // Toast.makeText(getContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();


                    }else {
                        Toast.makeText(getContext(), "Please enter Valid code", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "Please Enter Verification Code", Toast.LENGTH_SHORT).show();
                }

            }
        });
        thread();

    }

    private void DrRegistration(String user_name, String user_email, String user_mobile, String user_address, String user_password, String user_type, String dr_bio, String dr_specialization, String clinic_address) {
        pDialog.setMessage("Registring User....");
        pDialog.show();

        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AppConstants.mainurl+registration_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.hide();
                Log.d("VerifyActivity","user method call"+response.toString());
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        if (jsonObject.getString("status").equals("true")) {
                            pDialog.dismiss();
                            Toast.makeText(getContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                            // Goto Login Page
                            LoginFragment loginFragment = new LoginFragment();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            fragmentTransaction.replace(R.id.fragment_container, loginFragment);
                        } else {

                            pDialog.dismiss();
                            Toast.makeText(getContext(), " Sorry try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    pDialog.dismiss();
                    Toast.makeText(getContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Log.d("Response error","Volley response errror is"+error.getMessage());
                Toast.makeText(getActivity(), "Please ty again", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", user_name);
                params.put("mobile", user_mobile);
                params.put("password", user_password);
                params.put("address", user_address);
                params.put("email", user_email);
                params.put("user_type", user_type);
                params.put("specialization", dr_specialization);
                params.put("doctor_city", clinic_address);
                params.put("description", dr_bio);
                return params;

            }
        };
        VolleyRequestsent.getInstance().addRequestQueue(stringRequest2);
    }

    private void thread() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (count >= 0) {
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        Log.i("TAG", e.getMessage());
                    }
                    Log.i("TAG", "Thread id in while loop: " + Thread.currentThread().getId() + ", Count : " + count);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            timer.setText("Seconds Left " + count);
                        }
                    });
                    if (count == 0) {
                        // getActivity().onBackPressed();
                        // save the changes
                    }
                    count--;
                }
            }
        }).start();
    }


    private void UserRegistration(final String user_name, final String user_email, final String user_mobile, final String user_address, final String user_password,final String user_type) {
        pDialog.setMessage("Registring User....");
        pDialog.show();

        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AppConstants.mainurl+registration_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.hide();
                Log.d("VerifyActivity","user method call"+response.toString());
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        if (jsonObject.getString("status").equals("true")) {
                            pDialog.dismiss();
                            Toast.makeText(getContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                            // Goto Login Page
                            LoginFragment loginFragment = new LoginFragment();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            fragmentTransaction.replace(R.id.fragment_container, loginFragment);
                        } else {

                            pDialog.dismiss();
                            Toast.makeText(getContext(), " Sorry try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    pDialog.dismiss();
                    Toast.makeText(getContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Log.d("Response error","Volley response errror is"+error.getMessage());
                Toast.makeText(getActivity(), "Please ty again", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", user_name);
                params.put("mobile", user_mobile);
                params.put("password", user_password);
                params.put("address", user_address);
                params.put("email", user_email);
                params.put("user_type", user_type);
                return params;

            }
        };
        VolleyRequestsent.getInstance().addRequestQueue(stringRequest2);
    }


}
