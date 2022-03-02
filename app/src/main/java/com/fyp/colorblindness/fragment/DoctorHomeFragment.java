package com.fyp.colorblindness.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.fyp.colorblindness.R;
import com.fyp.colorblindness.models.UserModelClass;
import com.fyp.colorblindness.utils.SharedPrefManager;

public class DoctorHomeFragment extends Fragment {
    View view;
    CardView dr_profile,dr_patients;
    String clinc_dr="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.doctor_home_fragment,container,false);
        initialization();
        return view;
    }

    private void initialization() {
        dr_profile=view.findViewById(R.id.dr_profile_cardview);
        dr_patients=view.findViewById(R.id.patient_reports_cardview);
        dr_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserProfile userProfile = new UserProfile();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.user_main_frame, userProfile);
                fragmentTransaction.addToBackStack("forgetpass_fragment");
                fragmentTransaction.commit();
            }
        });
        UserModelClass userModelClass= SharedPrefManager.getInstance(getContext()).getUser();
        if (userModelClass!=null){
            String dr_id=userModelClass.getUser_id();
            if (dr_id.equals("95")){
                dr_patients.setVisibility(View.VISIBLE);
            }
        }
        dr_patients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClinicDoctorReports reports = new ClinicDoctorReports();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.user_main_frame, reports);
                fragmentTransaction.addToBackStack("forgetpass_fragment");
                fragmentTransaction.commit();
            }
        });
    }

}
