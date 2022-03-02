package com.fyp.colorblindness.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.fyp.colorblindness.R;
import com.fyp.colorblindness.activities.LoginSignUpActivity;
import com.fyp.colorblindness.models.UserModelClass;
import com.fyp.colorblindness.utils.SharedPrefManager;
import com.fyp.colorblindness.utils.VolleyRequestsent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserProfile extends Fragment {
    View view;
    String updateProfile = "https://houseofsoftwares.com/color-blindness/Api.php?action=updateProfile";
    private ProgressDialog pDialog;
    EditText edit_name,edit_mobile,edit_addres,edit_email,edit_current_pass,new_pass;
    Button edit_info_btn;
    String edit_user_name="",UserId="",edit_user_email="",edit_user_address="",edit_user_mobile="",edit_user_photo="",edit_user_currentPass="",edit_user_newPass="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.user_profile,container,false);
        initialization();
        return view;
    }
    private void initialization() {
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        edit_addres=view.findViewById(R.id.user_address_edit);
        edit_name=view.findViewById(R.id.user_name_edit);
        edit_mobile=view.findViewById(R.id.user_mobile_number_edit);
        edit_email=view.findViewById(R.id.user_email_edit);
        edit_current_pass=view.findViewById(R.id.user_password_edit);
        new_pass=view.findViewById(R.id.user_confirm_password_edit);
        edit_info_btn=view.findViewById(R.id.edit_btn_user);
        edit_info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    UserUpdate(UserId,edit_user_name,edit_user_email,edit_user_mobile,edit_user_address,edit_user_newPass);
                }
            }
        });
        BindData();
    }

    private void BindData() {
        UserModelClass userModelClass= SharedPrefManager.getInstance(getContext()).getUser();
        if (userModelClass!=null){
            String number=userModelClass.getUser_mobile();
            edit_name.setText(userModelClass.getUser_name());
            edit_email.setText(userModelClass.getUser_email());
            edit_mobile.setText(number);
            edit_addres.setText(userModelClass.getUser_address());
        }
    }

    //Validating data
    private boolean validate() {
        boolean valid = true;
        edit_user_name = edit_name.getText().toString();
        edit_user_email = edit_email.getText().toString();
        edit_user_mobile = edit_mobile.getText().toString();
        edit_user_address = edit_addres.getText().toString();
        edit_user_currentPass = edit_current_pass.getText().toString();
        edit_user_newPass = new_pass.getText().toString();
        UserModelClass userModelClass = SharedPrefManager.getInstance(getContext()).getUser();
        UserId=userModelClass.getUser_id();

        Log.d("IMG", "THIS is image" + edit_user_photo);

        if (edit_user_name.isEmpty()){
            edit_name.setError("Plaese enter name");
            valid=false;
        }else {
            edit_name.setError(null);
        }
        if (UserId.isEmpty()){
            Toast.makeText(getContext(), "Error reload the activity", Toast.LENGTH_SHORT).show();
            valid=false;
        }
        if (edit_user_email.isEmpty()) {
            edit_email.setError("Pleaase enter email");
            valid = false;
        } else {
            edit_email.setError(null);
        }
        if (edit_user_mobile.isEmpty()) {
            edit_mobile.setError("Please enter mobile");
            valid = false;
        } else {
            edit_mobile.setError(null);
        }
        if (edit_user_address.isEmpty()) {
            edit_addres.setError("Please enter address");
            valid = false;
        } else {
            edit_addres.setError(null);
        }

        if (edit_user_currentPass.isEmpty()  || !edit_user_currentPass.equals(userModelClass.getUser_password())) {
            edit_current_pass.setError("Password don't Match");
            valid = false;
        } else {
            edit_current_pass.setError(null);
        }
        if (edit_user_newPass.isEmpty()){
            new_pass.setError("please enter password");
            valid=false;
        }else {
            new_pass.setError(null);
        }
        return valid;
    }


    private void UserUpdate(final String userId, final String edit_user_name, final String edit_user_email, final String edit_user_mobile, final String edit_user_address, final String edit_user_newPass) {
        pDialog.setMessage("Updating....");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, updateProfile, new Response.Listener<String>() {
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
                            Toast.makeText(getContext(), "Info Updated Successfully", Toast.LENGTH_SHORT).show();
                            SharedPrefManager.getInstance(getContext()).logOut();
                            // Goto Login Page
                            Intent intent=new Intent(getContext(), LoginSignUpActivity.class);
                            startActivity(intent);
                            getActivity().finish();
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
                params.put("username", edit_user_name);
                params.put("mobile", edit_user_mobile);
                params.put("email", edit_user_email);
                params.put("address", edit_user_address);
                params.put("user_id", userId);
                params.put("password", edit_user_newPass);
                return params;
            }
        };
        VolleyRequestsent.getInstance().addRequestQueue(stringRequest);
    }

}
