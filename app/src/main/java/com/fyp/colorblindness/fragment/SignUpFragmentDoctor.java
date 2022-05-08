package com.fyp.colorblindness.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.fyp.colorblindness.genralclasses.Constants_values;
import com.fyp.colorblindness.genralclasses.RequestsQueueVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragmentDoctor extends Fragment {
    View view;
    String IsUserExist = "isUserExist";
    private ProgressDialog pDialog;
    EditText et_name_dr,et_pmdc,et_comp_desc_dr,et_clinic_address_dr,et_specialization_dr ,et_mobile_dr, et_address_dr, et_email_dr, et_password_dr, et_confirm_password_dr;
    Button registration_btn_dr;
    String dr_type = "1", dr_name = "",dr_specialization="",dr_clinic_address="",dr_pmdc="",
            dr_comp_desc="", dr_email = "", dr_mobile = "", dr_address = "", dr_password = "", dr_confirm_password = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.signup_docotr_fragment,container,false);
        initialization();
        return view;
    }
    private void initialization() {
        et_name_dr = view.findViewById(R.id.dr_name);
        et_email_dr = view.findViewById(R.id.dr_email);
        et_pmdc = view.findViewById(R.id.dr_pmdc);
        et_mobile_dr = view.findViewById(R.id.dr_mobile_number);
        et_address_dr = view.findViewById(R.id.dr_address);
        et_password_dr = view.findViewById(R.id.dr_password);
        et_confirm_password_dr = view.findViewById(R.id.dr_confirm_password);
        et_clinic_address_dr = view.findViewById(R.id.dr_company_address);
        et_specialization_dr = view.findViewById(R.id.dr_specialization);
        et_comp_desc_dr = view.findViewById(R.id.dr_company_description);
        registration_btn_dr = view.findViewById(R.id.register_btn_dr);
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);

        registration_btn_dr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate()) {

                    IsUserExist(dr_email);
                }

            }
        });

    }

    //Validating data
    private boolean validate() {
        boolean valid = true;
        dr_name = et_name_dr.getText().toString();
        dr_email = et_email_dr.getText().toString();
        dr_mobile = et_mobile_dr.getText().toString();
        dr_address = et_address_dr.getText().toString();
        dr_password = et_password_dr.getText().toString();
        dr_pmdc = et_pmdc.getText().toString();
        dr_confirm_password = et_confirm_password_dr.getText().toString();
        dr_clinic_address=et_clinic_address_dr.getText().toString();
        dr_comp_desc=et_comp_desc_dr.getText().toString();
        dr_specialization=et_specialization_dr.getText().toString();



        if (dr_name.isEmpty()) {
            et_name_dr.setError("Pleaase enter name");
            valid = false;
        } else {
            et_name_dr.setError(null);
        }
        if (dr_pmdc.isEmpty()) {
            et_pmdc.setError("Pleaase enter pmdc number");
            valid = false;
        } else {
            et_pmdc.setError(null);
        }
        if (dr_specialization.isEmpty()) {
            Toast.makeText(getContext(), "Please enter specialization", Toast.LENGTH_SHORT).show();
            // et_comp_name_dr.setError("Pleaase enter company name");
            valid = false;
        }
        if (dr_clinic_address.isEmpty()) {
            et_clinic_address_dr.setError("Pleaase enter company address");
            valid = false;
        } else {
            et_clinic_address_dr.setError(null);
        }
        if (dr_comp_desc.isEmpty()) {
            et_comp_desc_dr.setError("Pleaase enter company detail");
            valid = false;
        } else {
            et_comp_desc_dr.setError(null);
        }
        if (dr_email.isEmpty()) {
            et_email_dr.setError("Please enter email");
            valid = false;
        } else {
            et_email_dr.setError(null);
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(dr_email).matches()) {
            et_email_dr.setError("Email formate is wrong");
            valid = false;
        } else {
            et_email_dr.setError(null);
        }
        if (dr_mobile.isEmpty()) {
            et_mobile_dr.setError("Please enter mobile");
            valid = false;
        } else {
            et_mobile_dr.setError(null);
        }
        if (dr_address.isEmpty()) {
            et_address_dr.setError("Please enter address");
            valid = false;
        } else {
            et_address_dr.setError(null);
        }
        if (dr_password.isEmpty() || dr_confirm_password.isEmpty() || !dr_confirm_password.equals(dr_password)) {
            et_password_dr.setError("Password don't Match");
            et_confirm_password_dr.setError("Password don't Match");
            valid = false;
        } else {
            et_password_dr.setError(null);
            et_confirm_password_dr.setError(null);
        }
        if (!isValidPassword(dr_password)) {
            et_password_dr.setError("Use characters, numbers and symbols. minimum length 8");
            et_confirm_password_dr.setError("Use characters, numbers and symbols.");
            valid = false;
            Log.d("SignUp","validation");

        } else {
            et_password_dr.setError(null);
            et_confirm_password_dr.setError(null);
            Log.d("SignUp","Pass validation");
        }



        return valid;
    }


    //*****************************************************************
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[A-Za-z\\d][A-Za-z\\d!@#$%^&*()_+]{7,19}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }


    private void IsUserExist(final String dr_email) {
        Log.e("check1122", "mobile number" + dr_email);
        pDialog.setMessage("Registring ...");
        pDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants_values.mainurl+IsUserExist, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        if (jsonObject.getString("status").equals("false")) {
                            pDialog.dismiss();
                            Toast.makeText(getContext(), "Email Already Registered", Toast.LENGTH_SHORT).show();

                        } else {
                            pDialog.dismiss();
                            VerifyCodeFragment fragment=new VerifyCodeFragment();
                            Bundle args = new Bundle();
                            args.putString("Mobile", dr_mobile);
                            args.putString("Name", dr_name);
                            args.putString("Address", dr_address);
                            args.putString("pmdc", dr_pmdc);
                            args.putString("Email", dr_email);
                            args.putString("user_type", dr_type);
                            args.putString("Password", dr_password);
                            args.putString("drspe", dr_specialization);
                            args.putString("ClinicAddress", dr_clinic_address);
                            args.putString("CompDesc", dr_comp_desc);
                            args.putString("code", jsonObject.getString("code"));
                            fragment.setArguments(args);
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            fragmentTransaction.commit();

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    pDialog.dismiss();
                    Toast.makeText(getContext(), "erro catch "+e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(getContext(), "Error Please tyr again"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", dr_email);
                return params;
            }

        };
        RequestsQueueVolley.getInstance().addRequestQueue(stringRequest);
    }
}
