package com.fyp.colorblindness.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fyp.colorblindness.R;
import com.fyp.colorblindness.utils.AppConstants;

public class AboutUsFragment extends Fragment {
    View view;
    TextView textView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.about_us_fragment,container,false);
        textView=view.findViewById(R.id.tv_company);
        textView.setText(Html.fromHtml(AppConstants.company));
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("About Us");
        super.onViewCreated(view, savedInstanceState);
    }
}
