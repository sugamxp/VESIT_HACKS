package com.example.sumeetharyani.asset_manager.Activity;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.sumeetharyani.asset_manager.Fragment.FragmentDash;
import com.example.sumeetharyani.asset_manager.Fragment.FragmentHome;
import com.example.sumeetharyani.asset_manager.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static com.google.firebase.auth.FirebaseAuth.*;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    Fragment selected = null;
    private String keyval;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle bundle = new Bundle();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selected = new FragmentHome();
                    break;
                case R.id.navigation_dashboard:
                    selected = new FragmentDash();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case (R.id.action_signOut):

                getInstance().signOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent i = new Intent(getApplicationContext(), SignIn.class);
                                startActivity(i);
                                finish();

                            }
                        });

                SharedPreferences spreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor spreferencesEditor = spreferences.edit();
                spreferencesEditor.clear();
                spreferencesEditor.apply();
                return true;
            case (R.id.action_settings):

                Intent i = new Intent(this, ChatAct.class);
                startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

