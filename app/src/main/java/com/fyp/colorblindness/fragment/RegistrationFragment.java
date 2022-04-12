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

public class RegistrationFragment extends Fragment {
View view;


    String IsUserExist = "isUserExist";
    private ProgressDialog pDialog;
    EditText et_name_user, et_mobile_user, et_address_user, et_email_user, et_password_user, et_confirm_password_user;
    Button registration_btn_user;
    String user_type = "2", user_name = "",user_phone="", user_email = "", user_mobile = "", user_address = "", user_password = "", user_confirm_password = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.singup_fragment_user, container, false);
        initialization();
        return view;
    }

    private void initialization() {
        et_name_user = view.findViewById(R.id.user_name);
        et_email_user = view.findViewById(R.id.user_email);
        et_mobile_user = view.findViewById(R.id.user_mobile_number);
        et_address_user = view.findViewById(R.id.user_address);
        et_password_user = view.findViewById(R.id.user_password);
        et_confirm_password_user = view.findViewById(R.id.user_confirm_password);
        registration_btn_user = view.findViewById(R.id.register_btn_buyer);
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);

        registration_btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   if (validate()) {
                    IsUserExist(user_email);
                }

            /*    VerifyCodeFragment fragment = new VerifyCodeFragment();
                Bundle args = new Bundle();
                args.putString("Mobile", user_phone);
                args.putString("Name", user_name);
                args.putString("Address", user_address);
                args.putString("Email", user_email);
                args.putString("Status", user_type);
                args.putString("Password", user_password);
                args.putString("code", "12345");
                fragment.setArguments(args);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();*/

            }
        });


    }

    //Validating data
    private boolean validate() {
        boolean valid = true;
        user_name = et_name_user.getText().toString();
        user_email = et_email_user.getText().toString();
        user_mobile = et_mobile_user.getText().toString();
        user_address = et_address_user.getText().toString();
        user_password = et_password_user.getText().toString();
        user_confirm_password = et_confirm_password_user.getText().toString();



        if (user_name.isEmpty()) {
            et_name_user.setError("Pleaase enter name");
            valid = false;
        } else {
            et_name_user.setError(null);
        }
        if (user_email.isEmpty()) {
            et_email_user.setError("Please enter email");
            valid = false;
        } else {
            et_email_user.setError(null);
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(user_email).matches()) {
            et_email_user.setError("Email formate is wrong");
            valid = false;
        } else {
            et_email_user.setError(null);
        }
        if (user_mobile.isEmpty()) {
            et_mobile_user.setError("Please enter mobile");
            valid = false;
        } else {
            et_mobile_user.setError(null);
        }
        if (user_address.isEmpty()) {
            et_address_user.setError("Please enter address");
            valid = false;
        } else {
            et_address_user.setError(null);
        }
        if (user_password.isEmpty() || user_confirm_password.isEmpty() || !user_confirm_password.equals(user_password)) {
            et_password_user.setError("Password don't Match");
            et_confirm_password_user.setError("Password don't Match");
            valid = false;
        } else {
            et_password_user.setError(null);
            et_confirm_password_user.setError(null);
        }

        if (user_type.isEmpty()) {
            valid = false;
        }

        if (!isValidPassword(user_password)) {
            et_password_user.setError("Use characters, numbers and symbols. minimum length 8");
            et_confirm_password_user.setError("Use characters, numbers and symbols.");
            valid = false;
            Log.d("SignUp","validation");

        } else {
            et_password_user.setError(null);
            et_confirm_password_user.setError(null);
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

    private void IsUserExist(final String user_email) {
        Log.e("check1122", "mobile number" + user_email);
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
                            //Toast.makeText(getContext(), "HELLO", Toast.LENGTH_SHORT).show();
                            VerifyCodeFragment fragment = new VerifyCodeFragment();
                            Bundle args = new Bundle();
                            args.putString("Mobile", user_mobile);
                            args.putString("Name", user_name);
                            args.putString("Address", user_address);
                            args.putString("Email", user_email);
                            args.putString("user_type", user_type);
                            args.putString("Password", user_password);
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
                    Toast.makeText(getContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
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
                params.put("email", user_email);
                return params;

            }

        };

        RequestsQueueVolley.getInstance().addRequestQueue(stringRequest);
    }

}
