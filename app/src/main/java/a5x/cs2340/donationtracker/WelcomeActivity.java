package a5x.cs2340.donationtracker;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import a5x.cs2340.donationtracker.activities.login.LoginActivity;
import a5x.cs2340.donationtracker.activities.registration.RegistrationActivity;

/**
 * The default activtiy users will see when starting the app
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Button goToLoginButton = findViewById(R.id.welcomeLoginButton);
        goToLoginButton.setOnClickListener(view -> goToLogin());
        Button goToRegisterButton = findViewById(R.id.goToRegisterButton);
        goToRegisterButton.setOnClickListener(view -> goToRegister());

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.INTERNET)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
                                                                   PermissionToken token) {/* ...*/}
                }).check();
    }

    /**
     * Transitions from the welcome screen to the login screen
     */
    private void goToLogin() {
        Intent goToLoginIntent = new Intent(this, LoginActivity.class);
        startActivity(goToLoginIntent);
    }

    /**
     * Transitions from the welcome screen to the registration screen
     */
    private void goToRegister() {
        Intent goToRegisterIntent = new Intent(this, RegistrationActivity.class);
        startActivity(goToRegisterIntent);
    }
}
