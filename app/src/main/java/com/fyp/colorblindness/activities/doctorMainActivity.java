package com.fyp.colorblindness.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.fyp.colorblindness.R;
import com.fyp.colorblindness.fragment.AboutUsFragment;
import com.fyp.colorblindness.fragment.ContactUsFragment;
import com.fyp.colorblindness.fragment.DoctorHomeFragment;
import com.fyp.colorblindness.fragment.EditDrProfessionInfo;
import com.fyp.colorblindness.fragment.MyReportsFragment;
import com.fyp.colorblindness.fragment.QuizFragment;
import com.fyp.colorblindness.fragment.UserHomeFragment;
import com.fyp.colorblindness.fragment.UserProfile;
import com.fyp.colorblindness.models.UserModelClass;
import com.fyp.colorblindness.utils.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;

public class doctorMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView ownername, ownermail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_main_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        ownername = navigationView.getHeaderView(0).findViewById(R.id.username);
        ownermail = navigationView.getHeaderView(0).findViewById(R.id.useremail);
        final UserModelClass userModelClass = SharedPrefManager.getInstance(doctorMainActivity.this).getUser();
        if (userModelClass != null) {
            ownername.setText(userModelClass.getUser_name());
            ownermail.setText(userModelClass.getUser_email());
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.user_main_frame,
                    new DoctorHomeFragment()).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

       /*     getSupportFragmentManager().beginTransaction().replace(R.id.user_main_frame,
                    new PatientHomeFragment()).commit();*/

        }else if (id==R.id.nav_professioninfo){
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction().replace(R.id.user_main_frame,
                    new EditDrProfessionInfo()).addToBackStack("fragment").commit();
        }

        else if (id == R.id.nav_profile) {

            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction().replace(R.id.user_main_frame,
                    new UserProfile()).addToBackStack("fragment").commit();

        }
        else if (id == R.id.nav_about) {

            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction().replace(R.id.user_main_frame,
                    new AboutUsFragment()).addToBackStack("fragment").commit();

        }else if (id == R.id.nav_contact) {

            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction().replace(R.id.user_main_frame,
                    new ContactUsFragment()).addToBackStack("fragment").commit();

        }
        else if (id == R.id.nav_logout) {

            SharedPrefManager.getInstance(doctorMainActivity.this).logOut();
            startActivity(new Intent(this, LoginSignUpActivity.class));
            this.finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
