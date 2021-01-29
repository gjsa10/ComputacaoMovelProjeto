package com.g18cm.pocketthief;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.g18cm.pocketthief.map.MapFragment;
import com.g18cm.pocketthief.profile.ProfileFragment;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button profileBtn;
    Button inventoryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        profileBtn = (Button) findViewById(R.id.profile_button);
        profileBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.profile_button){
            Fragment mFragment = new ProfileFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.displayed_fragment, mFragment).commit();
        }
        else if (v.getId() == R.id.inventory_button){

        }
    }
}