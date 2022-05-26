package com.fyp.colorblindness.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.fyp.colorblindness.R;
import com.fyp.colorblindness.activities.LoginSignUpActivity;
import com.fyp.colorblindness.models.UserModelClass;
import com.fyp.colorblindness.genralclasses.Constants_values;
import com.fyp.colorblindness.genralclasses.SharedPreferenceClass;
import com.fyp.colorblindness.genralclasses.RequestsQueueVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditDrProfessionInfo extends Fragment implements AdapterView.OnItemSelectedListener {
    View view;
    EditText et_dr_city,et_dr_spec,et_bio_desc;
    String dr_profession="",dr_desc="",license_pmdc_image="", dr_spec="",dr_address="",UserId="";
    Button update_info_btn;
    private ProgressDialog pDialog;
    private int GALLERY = 1, CAMERA = 2;
    Spinner select;
    ImageView pmdc_img;
    TextView update_pmdc_img;
    String updateCompany_url = "updateCompany";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.edit_drbio_info_fragment,container,false);
        initialization();
        return view;
    }

    private void initialization() {
        UserModelClass userModelClass= SharedPreferenceClass.getInstance(getContext()).getUser();
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        et_dr_city=view.findViewById(R.id.drcity_address_edit);
        et_dr_spec=view.findViewById(R.id.dr_spec_edit);
        et_bio_desc=view.findViewById(R.id.dr_bio_edit);
        update_info_btn=view.findViewById(R.id.professioninfo_update_btn);
        update_pmdc_img=view.findViewById(R.id.FrontSide);
        pmdc_img=view.findViewById(R.id.license_pmdc);
        pmdc_img.setOnClickListener(view1 -> {
            showImage(userModelClass.getPmdc_no());
        });
        BindData();
        update_info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    UpdateProfessionInfo(license_pmdc_image,dr_address,dr_desc, dr_spec,UserId);
                }
            }
        });
        update_pmdc_img.setOnClickListener(view1 -> {
            showPictureDialog();
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
                    pmdc_img.setImageBitmap(bitmap);
                    license_pmdc_image=getStringImage(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            pmdc_img.setImageBitmap(thumbnail);
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

    private void UpdateProfessionInfo(final String pmdc_no,final String dr_address, final String dr_desc, final String dr_spec, final String userId) {
        pDialog.setMessage("Updating....");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants_values.mainurl+updateCompany_url, new Response.Listener<String>() {
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
                            SharedPreferenceClass.getInstance(getContext()).logOut();
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
                params.put("pmdc_no", pmdc_no);
                params.put("user_id", userId);
                return params;
            }
        };
        RequestsQueueVolley.getInstance().addRequestQueue(stringRequest);
    }

    private void BindData() {
        UserModelClass userModelClass= SharedPreferenceClass.getInstance(getContext()).getUser();
        if (userModelClass!=null){
            et_bio_desc.setText(userModelClass.getDoctor_bio());
            et_dr_city.setText(userModelClass.getCompany_address());
            et_dr_spec.setText(userModelClass.getSpecialization());
            Glide.with(getContext()).load(userModelClass.getPmdc_no()).into(pmdc_img);
        }
    }

    //Validating data
    private boolean validate() {
        boolean valid = true;
        dr_spec = et_dr_spec.getText().toString();
        dr_desc = et_bio_desc.getText().toString();
        dr_address = et_dr_city.getText().toString();
        UserModelClass userModelClass= SharedPreferenceClass.getInstance(getContext()).getUser();
        if (userModelClass!=null){
            UserId = userModelClass.getUser_id();
            Log.d("editCompany info","Saller id"+UserId);
        }


        if (dr_spec.isEmpty()) {
            et_dr_spec.setError("Please Enter specialization");
            valid = false;
        } else {
            et_dr_spec.setError(null);
        }
        if (license_pmdc_image.isEmpty()) {
            license_pmdc_image=userModelClass.getPmdc_no();
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

    public void showImage(String imageUri) {
        Dialog builder = new Dialog(getContext());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(getContext());
        //imageView.setImageURI(imageUri);
        if (imageUri != null) {
            Glide.with(getContext()).load(imageUri).dontAnimate().fitCenter().placeholder(R.drawable.applogo).into(imageView);
        }
        //Picasso.get().load(imageUri).placeholder(R.drawable.logo).into(imageView);
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

}
