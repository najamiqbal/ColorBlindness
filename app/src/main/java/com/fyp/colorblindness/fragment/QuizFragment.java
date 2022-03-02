package com.fyp.colorblindness.fragment;

import android.os.Bundle;
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
import androidx.fragment.app.FragmentTransaction;

import com.fyp.colorblindness.R;

public class QuizFragment extends Fragment {

    View view;
    ImageView test_img;
    TextView tv_count,txt_first,txt_2nd,txt_guide;
    EditText et_first_number, et_second_number;
    Button btn_next,btn_finish;
    int step=0;
    String plate1="",plate2="",plate3="",plate4="",plate5="",plate6="",plate7="",plate8="",plate9="",plate10="",plate11="",plate12="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.quiz_fragment,container,false);
        initialization();
        setLayout(step);
        return view;
    }

    private void setLayout(int step) {
        if (step==0){
            txt_first.setVisibility(View.GONE);
            txt_2nd.setVisibility(View.GONE);
            et_first_number.setVisibility(View.GONE);
            et_second_number.setVisibility(View.GONE);
            tv_count.setText(step+"/12");

        }else if (step==1){
            txt_guide.setVisibility(View.GONE);
            txt_first.setVisibility(View.VISIBLE);
            txt_2nd.setVisibility(View.VISIBLE);
            et_first_number.setVisibility(View.VISIBLE);
            et_second_number.setVisibility(View.VISIBLE);
            test_img.setImageResource(R.drawable.ishihara_01);
            et_first_number.getText().clear();
            et_second_number.getText().clear();
            tv_count.setText(step+"/12");
        }
        else if (step==2){
            txt_guide.setVisibility(View.GONE);
            txt_first.setVisibility(View.VISIBLE);
            txt_2nd.setVisibility(View.GONE);
            et_first_number.setVisibility(View.VISIBLE);
            et_second_number.setVisibility(View.GONE);
            test_img.setImageResource(R.drawable.ishihara_02);
            et_first_number.getText().clear();
            et_second_number.getText().clear();
            tv_count.setText(step+"/12");
        }
        else if (step==3){
            txt_guide.setVisibility(View.GONE);
            txt_first.setVisibility(View.VISIBLE);
            txt_2nd.setVisibility(View.VISIBLE);
            et_first_number.setVisibility(View.VISIBLE);
            et_second_number.setVisibility(View.VISIBLE);
            test_img.setImageResource(R.drawable.ishihara_03);
            et_first_number.getText().clear();
            et_second_number.getText().clear();
            tv_count.setText(step+"/12");
        }
        else if (step==4){
            txt_guide.setVisibility(View.GONE);
            txt_first.setVisibility(View.VISIBLE);
            txt_2nd.setVisibility(View.GONE);
            et_first_number.setVisibility(View.VISIBLE);
            et_second_number.setVisibility(View.GONE);
            test_img.setImageResource(R.drawable.ishihara_04);
            et_first_number.getText().clear();
            et_second_number.getText().clear();
            tv_count.setText(step+"/12");
        }
        else if (step==5){
            txt_guide.setVisibility(View.GONE);
            txt_first.setVisibility(View.VISIBLE);
            txt_2nd.setVisibility(View.VISIBLE);
            et_first_number.setVisibility(View.VISIBLE);
            et_second_number.setVisibility(View.VISIBLE);
            test_img.setImageResource(R.drawable.ishihara_05);
            et_first_number.getText().clear();
            et_second_number.getText().clear();
            tv_count.setText(step+"/12");
        }else if (step==6){
            txt_guide.setVisibility(View.GONE);
            txt_first.setVisibility(View.VISIBLE);
            txt_2nd.setVisibility(View.GONE);
            et_first_number.setVisibility(View.VISIBLE);
            et_second_number.setVisibility(View.GONE);
            test_img.setImageResource(R.drawable.ishihara_06);
            et_first_number.getText().clear();
            et_second_number.getText().clear();
            tv_count.setText(step+"/12");
        }
        else if (step==7){
            txt_guide.setVisibility(View.GONE);
            txt_first.setVisibility(View.VISIBLE);
            txt_2nd.setVisibility(View.VISIBLE);
            et_first_number.setVisibility(View.VISIBLE);
            et_second_number.setVisibility(View.VISIBLE);
            test_img.setImageResource(R.drawable.ishihara_07);
            et_first_number.getText().clear();
            et_second_number.getText().clear();
            tv_count.setText(step+"/12");
        }
        else if (step==8){
            txt_guide.setVisibility(View.GONE);
            txt_first.setVisibility(View.VISIBLE);
            txt_2nd.setVisibility(View.GONE);
            et_first_number.setVisibility(View.VISIBLE);
            et_second_number.setVisibility(View.GONE);
            test_img.setImageResource(R.drawable.ishihara_08);
            et_first_number.getText().clear();
            et_second_number.getText().clear();
            tv_count.setText(step+"/12");
        }
        else if (step==9){
            txt_guide.setVisibility(View.GONE);
            txt_first.setVisibility(View.VISIBLE);
            txt_2nd.setVisibility(View.VISIBLE);
            et_first_number.setVisibility(View.VISIBLE);
            et_second_number.setVisibility(View.VISIBLE);
            test_img.setImageResource(R.drawable.ishihara_09);
            et_first_number.getText().clear();
            et_second_number.getText().clear();
            tv_count.setText(step+"/12");
        }
        else if (step==10){
            txt_guide.setVisibility(View.GONE);
            txt_first.setVisibility(View.VISIBLE);
            txt_2nd.setVisibility(View.GONE);
            et_first_number.setVisibility(View.VISIBLE);
            et_second_number.setVisibility(View.GONE);
            test_img.setImageResource(R.drawable.ishihara_10);
            et_first_number.getText().clear();
            et_second_number.getText().clear();
            tv_count.setText(step+"/12");
        }
        else if (step==11){
            txt_guide.setVisibility(View.GONE);
            txt_first.setVisibility(View.VISIBLE);
            txt_2nd.setVisibility(View.VISIBLE);
            et_first_number.setVisibility(View.VISIBLE);
            et_second_number.setVisibility(View.VISIBLE);
            test_img.setImageResource(R.drawable.ishihara_11);
            et_first_number.getText().clear();
            et_second_number.getText().clear();
            tv_count.setText(step+"/12");
        }
        else if (step==12){
            txt_guide.setVisibility(View.GONE);
            txt_first.setVisibility(View.VISIBLE);
            txt_2nd.setVisibility(View.GONE);
            et_first_number.setVisibility(View.VISIBLE);
            et_second_number.setVisibility(View.GONE);
            test_img.setImageResource(R.drawable.ishihara_12);
            et_first_number.getText().clear();
            et_second_number.getText().clear();
            tv_count.setText(step+"/12");
        }

    }

    private void initialization() {
        tv_count=view.findViewById(R.id.count);
        txt_guide=view.findViewById(R.id.txt_guide);
        txt_first=view.findViewById(R.id.txt_first);
        test_img=view.findViewById(R.id.img_test);
        txt_2nd=view.findViewById(R.id.txt_2nd);
        et_first_number=view.findViewById(R.id.et_first_number);
        et_second_number=view.findViewById(R.id.et_2nd_number);
        btn_finish=view.findViewById(R.id.btn_finish);
        btn_next=view.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (step==0){
                    step=step+1;
                    setLayout(step);
                }else {
                    StoreAnswer();
                }
            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResultFragment fragment=new ResultFragment();
                Bundle args = new Bundle();
                args.putString("ans1", plate1);
                args.putString("ans2", plate2);
                args.putString("ans3", plate3);
                args.putString("ans4", plate4);
                args.putString("ans5", plate5);
                args.putString("ans6", plate6);
                args.putString("ans7", plate7);
                args.putString("ans8", plate8);
                args.putString("ans9", plate9);
                args.putString("ans10", plate10);
                args.putString("ans11", plate11);
                args.putString("ans12", plate12);
                fragment.setArguments(args);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.user_main_frame, fragment);
                fragmentTransaction.addToBackStack("added");
                fragmentTransaction.commit();
            }
        });
    }

    private void StoreAnswer() {
        if (step==1){
           if (!et_first_number.getText().toString().isEmpty() && !et_second_number.getText().toString().isEmpty()){
               plate1=et_first_number.getText().toString()+et_second_number.getText().toString();
               step=step+1;
               setLayout(step);
           }else {
               Toast.makeText(getContext(), "Please Enter 0 if you not see anything", Toast.LENGTH_LONG).show();
           }
        }
        else if (step==2)
        {
            if (!et_first_number.getText().toString().isEmpty() ){
                plate2=et_first_number.getText().toString();
                step=step+1;
                setLayout(step);
            }else {
                Toast.makeText(getContext(), "Please Enter 0 if you not see anything", Toast.LENGTH_LONG).show();
            }
        }
        else if (step==3)
        {
            if (!et_first_number.getText().toString().isEmpty() && !et_second_number.getText().toString().isEmpty()){
                plate3=et_first_number.getText().toString()+et_second_number.getText().toString();
                step=step+1;
                setLayout(step);
            }else {
                Toast.makeText(getContext(), "Please Enter 0 if you not see anything", Toast.LENGTH_LONG).show();
            }
        }
        else if (step==4)
        {
            if (!et_first_number.getText().toString().isEmpty() ){
                plate4=et_first_number.getText().toString();
                step=step+1;
                setLayout(step);
            }else {
                Toast.makeText(getContext(), "Please Enter 0 if you not see anything", Toast.LENGTH_LONG).show();
            }
        }
        else if (step==5)
        {
            if (!et_first_number.getText().toString().isEmpty() && !et_second_number.getText().toString().isEmpty()){
                plate5=et_first_number.getText().toString()+et_second_number.getText().toString();
                step=step+1;
                setLayout(step);
            }else {
                Toast.makeText(getContext(), "Please Enter 0 if you not see anything", Toast.LENGTH_LONG).show();
            }
        }
        else if (step==6)
        {
            if (!et_first_number.getText().toString().isEmpty() ){
                plate6=et_first_number.getText().toString();
                step=step+1;
                setLayout(step);
            }else {
                Toast.makeText(getContext(), "Please Enter 0 if you not see anything", Toast.LENGTH_LONG).show();
            }
        }
        else if (step==7)
        {
            if (!et_first_number.getText().toString().isEmpty() && !et_second_number.getText().toString().isEmpty()){
                plate7=et_first_number.getText().toString()+et_second_number.getText().toString();
                step=step+1;
                setLayout(step);
            }else {
                Toast.makeText(getContext(), "Please Enter 0 if you not see anything", Toast.LENGTH_LONG).show();
            }
        }
        else if (step==8)
        {
            if (!et_first_number.getText().toString().isEmpty() ){
                plate8=et_first_number.getText().toString();
                step=step+1;
                setLayout(step);
            }else {
                Toast.makeText(getContext(), "Please Enter 0 if you not see anything", Toast.LENGTH_LONG).show();
            }
        }
        else if (step==9)
        {
            if (!et_first_number.getText().toString().isEmpty() && !et_second_number.getText().toString().isEmpty()){
                plate9=et_first_number.getText().toString()+et_second_number.getText().toString();
                step=step+1;
                setLayout(step);
            }else {
                Toast.makeText(getContext(), "Please Enter 0 if you not see anything", Toast.LENGTH_LONG).show();
            }
        }
        else if (step==10)
        {
            if (!et_first_number.getText().toString().isEmpty()){
                plate10=et_first_number.getText().toString();
                step=step+1;
                setLayout(step);
            }else {
                Toast.makeText(getContext(), "Please Enter 0 if you not see anything", Toast.LENGTH_LONG).show();
            }
        }
        else if (step==11)
        {
            if (!et_first_number.getText().toString().isEmpty() && !et_second_number.getText().toString().isEmpty()){
                plate11=et_first_number.getText().toString()+et_second_number.getText().toString();
                step=step+1;
                setLayout(step);
            }else {
                Toast.makeText(getContext(), "Please Enter 0 if you not see anything", Toast.LENGTH_LONG).show();
            }
        }
        else if (step==12)
        {
            if (!et_first_number.getText().toString().isEmpty()){
                plate12=et_first_number.getText().toString();
                btn_finish.setVisibility(View.VISIBLE);
                btn_next.setVisibility(View.GONE);
            }else {
                Toast.makeText(getContext(), "Please Enter 0 if you not see anything", Toast.LENGTH_LONG).show();
            }
        }

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Color Blind Test");
        super.onViewCreated(view, savedInstanceState);
    }
}
