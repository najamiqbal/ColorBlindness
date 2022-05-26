package com.fyp.colorblindness.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fyp.colorblindness.BuildConfig;
import com.fyp.colorblindness.R;
import com.fyp.colorblindness.genralclasses.Constants_values;
import com.fyp.colorblindness.genralclasses.RequestsQueueVolley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragmentDoctor extends Fragment {
    View view;
    String IsUserExist = "isUserExist";
    private ProgressDialog pDialog;
    ImageButton pmdc_image;
    private int GALLERY = 1, CAMERA = 2;
    EditText et_name_dr,et_comp_desc_dr,et_clinic_address_dr,et_specialization_dr ,et_mobile_dr, et_address_dr, et_email_dr, et_password_dr, et_confirm_password_dr;
    Button registration_btn_dr;
    String dr_type = "1",license_pmdc_image="", dr_name = "",dr_specialization="",dr_clinic_address="",
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
        et_mobile_dr = view.findViewById(R.id.dr_mobile_number);
        et_address_dr = view.findViewById(R.id.dr_address);
        et_password_dr = view.findViewById(R.id.dr_password);
        et_confirm_password_dr = view.findViewById(R.id.dr_confirm_password);
        et_clinic_address_dr = view.findViewById(R.id.dr_company_address);
        et_specialization_dr = view.findViewById(R.id.dr_specialization);
        et_comp_desc_dr = view.findViewById(R.id.dr_company_description);
        registration_btn_dr = view.findViewById(R.id.register_btn_dr);
        pmdc_image = view.findViewById(R.id.license_pmdc);
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        requestMultiplePermissions();
        pmdc_image.setOnClickListener(view1 -> {
            showPictureDialog();
        });
        registration_btn_dr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate()) {

                    IsUserExist(dr_email);
                }

            }
        });

    }


    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                openGalleryImage();
                                break;
                            case 1:
                                openCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }


    public void openGalleryImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY);
    }

    public void openCamera() {
        try {
            Intent takeVideoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(takeVideoIntent, CAMERA);
            }
        } catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "Your device doesn't support capturing images!";
            Toast toast = Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

                if (requestCode == GALLERY) {
                    if (data != null) {
                        Uri contentURI = data.getData();
                        try {

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentURI);
                            pmdc_image.setImageBitmap(bitmap);
                            license_pmdc_image=getStringImage(bitmap);

                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else if (requestCode == CAMERA) {
                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    pmdc_image.setImageBitmap(thumbnail);
                    license_pmdc_image=getStringImage(thumbnail);
                }


    }
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    //Validating data
    private boolean validate() {
        boolean valid = true;
        dr_name = et_name_dr.getText().toString();
        dr_email = et_email_dr.getText().toString();
        dr_mobile = et_mobile_dr.getText().toString();
        dr_address = et_address_dr.getText().toString();
        dr_password = et_password_dr.getText().toString();

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
        if (license_pmdc_image.isEmpty()) {
            Toast.makeText(getContext(), "Please upload license image", Toast.LENGTH_SHORT).show();
            valid = false;
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


    private void requestMultiplePermissions() {
        Dexter.withContext(getActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(getContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                            Log.d("permissions are granted","done");
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                            alertDialog.setTitle("Alert Dialog");
                            alertDialog.setMessage("Please Allow Permissions to use this App");
                            alertDialog.setCancelable(false);
                            alertDialog.setIcon(R.drawable.applogo);

                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.fromParts("package", BuildConfig.APPLICATION_ID, null));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            });

                            alertDialog.show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
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
                            args.putString("pmdc", license_pmdc_image);
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
