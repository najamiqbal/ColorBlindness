package com.fyp.colorblindness.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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

public class EditDrProfessionInfo extends Fragment implements AdapterView.OnItemSelectedListener {
    View view;
    EditText et_dr_city,et_dr_spec,et_bio_desc;
    String dr_profession="",dr_desc="", dr_spec="",dr_address="",UserId="";
    Button update_info_btn;
    private ProgressDialog pDialog;
    Spinner select;
    String updateCompany_url = "https://houseofsoftwares.com/color-blindness/Api.php?action=updateCompany";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.edit_drbio_info_fragment,container,false);
        initialization();
        return view;
    }

    private void initialization() {
        UserModelClass userModelClass= SharedPrefManager.getInstance(getContext()).getUser();
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        et_dr_city=view.findViewById(R.id.drcity_address_edit);
        et_dr_spec=view.findViewById(R.id.dr_spec_edit);
        et_bio_desc=view.findViewById(R.id.dr_bio_edit);
        update_info_btn=view.findViewById(R.id.professioninfo_update_btn);
        BindData();
        update_info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    UpdateProfessionInfo(dr_address,dr_desc, dr_spec,UserId);
                }
            }
        });
    }

    private void UpdateProfessionInfo(final String dr_address, final String dr_desc, final String dr_spec, final String userId) {
        pDialog.setMessage("Updating....");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, updateCompany_url, new Response.Listener<String>() {
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
                params.put("specialization", dr_spec);
                params.put("doctor_city", dr_address);
                params.put("description", dr_desc);
                params.put("user_id", userId);
                return params;
            }
        };
        VolleyRequestsent.getInstance().addRequestQueue(stringRequest);
    }

    private void BindData() {
        UserModelClass userModelClass=SharedPrefManager.getInstance(getContext()).getUser();
        if (userModelClass!=null){
            et_bio_desc.setText(userModelClass.getDoctor_bio());
            et_dr_city.setText(userModelClass.getCompany_address());
            et_dr_spec.setText(userModelClass.getSpecialization());
        }
    }

    //Validating data
    private boolean validate() {
        boolean valid = true;
        dr_spec = et_dr_spec.getText().toString();
        dr_desc = et_bio_desc.getText().toString();
        dr_address = et_dr_city.getText().toString();
        UserModelClass userModelClass= SharedPrefManager.getInstance(getContext()).getUser();
        if (userModelClass!=null){
            UserId = userModelClass.getUser_id();
            Log.d("editCompany info","Saller id"+UserId);
        }


        if (dr_spec.isEmpty()) {
            et_dr_spec.setError("Please Enter ntn no");
            valid = false;
        } else {
            et_dr_spec.setError(null);
        }
        if (dr_address.isEmpty()) {
            et_dr_city.setError("Please Enter company address");
            valid = false;
        } else {
            et_dr_city.setError(null);
        }
        if (dr_desc.isEmpty()) {
            et_bio_desc.setError("Please Enter Description");
            valid = false;
        } else {
            et_bio_desc.setError(null);
        }

        return valid;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Edit Company Profile");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        dr_profession=adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
