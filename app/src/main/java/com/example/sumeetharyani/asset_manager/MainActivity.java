package com.example.sumeetharyani.asset_manager;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    Fragment selected = null;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selected = new FragmentHome();
                    break;
                case R.id.navigation_dashboard:
                    selected = new FragmentDash();
                    break;
                case R.id.navigation_notifications:
                    selected = new FragmentNotify();
                    break;
            }
            getFragmentManager().beginTransaction().replace(R.id.fragment_layout, selected).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getFragmentManager().beginTransaction().replace(R.id.fragment_layout, new FragmentHome()).commit();

    }

}
