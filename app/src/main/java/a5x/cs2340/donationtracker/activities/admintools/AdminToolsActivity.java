package a5x.cs2340.donationtracker.activities.admintools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import a5x.cs2340.donationtracker.R;
import a5x.cs2340.donationtracker.activities.postlogin.PostLoginActivity;
import a5x.cs2340.donationtracker.models.users.UserType;
import a5x.cs2340.donationtracker.webservice.Webservice;

/**
 * Activity for all admin-related tools such as adding donations, modifying privileges, etc.
 */
public class AdminToolsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tools);
        Button backButton = findViewById(R.id.toolsBackButton);
        backButton.setOnClickListener(v -> backToPostLogin());
        Button addDonationButton = findViewById(R.id.toolsAddDonationButton);
        addDonationButton.setOnClickListener(v -> toAddDonation());
        Button viewDonationsButton = findViewById(R.id.toolsViewDonationsButton);
        viewDonationsButton.setOnClickListener(v -> toViewDonations());
        if (Webservice.getLoggedInUserType() == UserType.LOCATION_EMPLOYEE) {
            viewDonationsButton.setVisibility(View.VISIBLE);
            addDonationButton.setVisibility(View.VISIBLE);
        } else {
            addDonationButton.setVisibility(View.GONE);
            viewDonationsButton.setVisibility(View.GONE);
        }

    }
    private void toAddDonation() {
        Intent toAddDonationIntent = new Intent(this, AddDonationActivity.class);
        startActivity(toAddDonationIntent);
    }
    private void toViewDonations() {
        Intent toViewDonationsIntent = new Intent(this, ViewDonationsActivity.class);
        startActivity(toViewDonationsIntent);
    }
    private void backToPostLogin() {
        Intent backToPostLoginIntent = new Intent(this, PostLoginActivity.class);
        startActivity(backToPostLoginIntent);
    }
}
