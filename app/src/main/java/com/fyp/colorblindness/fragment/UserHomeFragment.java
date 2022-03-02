package com.fyp.colorblindness.fragment;

import android.content.Intent;
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
import com.fyp.colorblindness.activities.DetectColorActivity;

public class UserHomeFragment extends Fragment implements View.OnClickListener{

    View view;
    CardView cardView_quiz, cardView_detectColor,cardView_doctors;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.user_home_fragment,container,false);
        initialization();
        return view;
    }

    private void initialization() {
        cardView_detectColor=view.findViewById(R.id.color_cardview);
        cardView_quiz=view.findViewById(R.id.quiz_cardview);
        cardView_doctors=view.findViewById(R.id.doctors_cardview);

        cardView_quiz.setOnClickListener(this);
        cardView_doctors.setOnClickListener(this);
        cardView_detectColor.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.quiz_cardview:
                QuizFragment quizFragment = new QuizFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.user_main_frame, quizFragment);
                fragmentTransaction.addToBackStack("forgetpass_fragment");
                fragmentTransaction.commit();

                break;
            case R.id.doctors_cardview:
                DoctorsListFragment doctorsListFragment = new DoctorsListFragment();
                FragmentTransaction fragmentTransaction2 = getFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.user_main_frame, doctorsListFragment);
                fragmentTransaction2.addToBackStack("forgetpass_fragment");
                fragmentTransaction2.commit();
                break;
            case R.id.color_cardview:

                //startActivity(new Intent(getContext(), DetectColorActivity.class));
                break;


        }
    }
}
