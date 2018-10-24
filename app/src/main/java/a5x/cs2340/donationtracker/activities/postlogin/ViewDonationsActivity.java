package a5x.cs2340.donationtracker.activities.postlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import a5x.cs2340.donationtracker.R;

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
    }

    private void backToAdminTools() {
        Intent backToAdminToolsIntent = new Intent(this, AdminToolsActivity.class);
        startActivity(backToAdminToolsIntent);
    }
}
