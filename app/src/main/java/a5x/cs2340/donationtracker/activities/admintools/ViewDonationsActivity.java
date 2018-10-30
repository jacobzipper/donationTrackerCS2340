package a5x.cs2340.donationtracker.activities.admintools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import a5x.cs2340.donationtracker.R;

/**
 * Activity for employees to view the list of all donations for the location
 */
public class ViewDonationsActivity extends AppCompatActivity {
    private GetDonationsTask getDonationsTask = null;

    private Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_donations);
        backButton = findViewById(R.id.donationListBackButton);
        backButton.setOnClickListener(v -> backToAdminTools());
        getDonationsTask = new GetDonationsTask(this, null);
        getDonationsTask.execute((Void) null);
        Toolbar donationViewToolbar = findViewById(R.id.donationsViewToolbar);
        donationViewToolbar.setTitle(R.string.donation_view_toolbar_text);
        setSupportActionBar(donationViewToolbar);
        donationViewToolbar.setNavigationIcon(android.R.drawable.ic_menu_search);
        donationViewToolbar.setNavigationOnClickListener(v -> onSearchRequested());
    }

    private void backToAdminTools() {
        Intent backToAdminToolsIntent = new Intent(this, AdminToolsActivity.class);
        startActivity(backToAdminToolsIntent);
    }
}
