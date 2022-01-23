package com.example.pay_fee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.tasks.OnCompleteListener;
import android.text.TextUtils;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.security.AccessController;


public class HomeActivity extends AppCompatActivity {

    private ActionBar toolbar;
    private Button buttonFP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            logOut();
        }
        else {
            toolbar.setTitle("Profile");
            Fragment fragment = new ProfileFragment();
            loadFragment(fragment);
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    toolbar.setTitle("Profile");
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_history:
                    toolbar.setTitle("History");
                    fragment = new HistoryFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_due:
                    toolbar.setTitle("Due");
                    fragment = new DueFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_transaction:
                    toolbar.setTitle("Transaction");
                    fragment = new TransactionFragment();
                    loadFragment(fragment);
                    return true;

            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onStart() {

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            logOut();

        }

        super.onStart();
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();


        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.logoutButton){
            logOut();
            return true;
        }
        else if (id == R.id.updatePB)
    {

       Intent intent = new Intent(HomeActivity.this, SignupActivity.class);
        intent.putExtra("id", "18220200**");
        intent.putExtra("name", "Mr X");
        intent.putExtra("department", "CSE");
        intent.putExtra("email", "xyz@gmail.com");

        startActivity(intent);

        return true;
        }

        else if (item.getItemId() == R.id.deletAccount) {
            toolbar.setTitle("Delete Account");

            startActivity(new Intent(HomeActivity.this, DeleteActivity.class));
            return true;

        }

        else if (item.getItemId() == R.id.facebook) {
            String url = "https://www.facebook.com/leadinguniversity2001/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            return true;

        }

        else if (item.getItemId() == R.id.instagram) {
            String url = "http://www.instagram.com/leadinguniversity";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            return true;

        }

        else if (item.getItemId() == R.id.youtube) {
            String url = "https://www.youtube.com/channel/UC3UkVH9fhmQQCKSzQAMRwWA";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            return true;

        }

        else if (item.getItemId() == R.id.dProfile) {
            toolbar.setTitle("Developer Profile");

            startActivity(new Intent(HomeActivity.this, DeveloperProfile.class));
            return true;

        }


        return true;

    }

}
