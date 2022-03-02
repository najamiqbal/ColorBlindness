package com.fyp.colorblindness.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.colorblindness.R;
import com.fyp.colorblindness.models.UserModelClass;

import java.util.ArrayList;
import java.util.List;

public class DoctorsListAdapter extends RecyclerView.Adapter<DoctorsListAdapter.ViewHolder> {
    Context context;
    List<UserModelClass> modelList;
    ArrayList<UserModelClass> arrayList;
    public DoctorsListAdapter(Context context, List<UserModelClass> itemList) {
        this.context = context;
        this.modelList = itemList;
        this.arrayList=new ArrayList<UserModelClass>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctors_list_viewholder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final UserModelClass model = modelList.get(position);
        holder.dr_name.setText(model.getUser_name());
        holder.dr_spec.setText(model.getSpecialization());
        holder.dr_address.setText(model.getCompany_address());
        holder.dr_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + model.getUser_email()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "ColorBlindness issue");
                context.startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });

        holder.dr_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + model.getUser_mobile()));
                context.startActivity(intentDial);
            }
        });
        holder.dr_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWhatsApp(model.getUser_mobile());
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dr_name,dr_spec,dr_address,dr_email,dr_mobile,dr_whatsapp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dr_name=itemView.findViewById(R.id.txt_report_id);
            dr_address=itemView.findViewById(R.id.txt_draddress);
            dr_spec=itemView.findViewById(R.id.txt_dr_specialization);
            dr_email=itemView.findViewById(R.id.btn_dr_email);
            dr_mobile=itemView.findViewById(R.id.btn_dr_call);
            dr_whatsapp=itemView.findViewById(R.id.btn_dr_whatsapp);
        }
    }

    private void openWhatsApp(String number) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+92"+number + "&text="+"hello"));
        context.startActivity(intent);
    }
}
