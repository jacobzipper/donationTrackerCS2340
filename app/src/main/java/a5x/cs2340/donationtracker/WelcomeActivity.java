package a5x.cs2340.donationtracker;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Button goToLoginButton = findViewById(R.id.welcomeLoginButton);
        goToLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });
        Button goToRegisterButton = findViewById(R.id.goToRegisterButton);
        goToRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.INTERNET)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();
    }

    /**
     * Transitions from the welcome screen to the login screen
     */
    protected void goToLogin() {
        Intent goToLoginIntent = new Intent(this, LoginActivity.class);
        startActivity(goToLoginIntent);
    }

    /**
     * Transitions from the welcome screen to the registration screen
     */
    protected void goToRegister() {
        Intent goToRegisterIntent = new Intent(this, RegistrationActivity.class);
        startActivity(goToRegisterIntent);
    }
}
