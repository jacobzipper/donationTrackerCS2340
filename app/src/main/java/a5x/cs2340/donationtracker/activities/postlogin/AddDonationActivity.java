package a5x.cs2340.donationtracker.activities.postlogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import a5x.cs2340.donationtracker.DonationCategory;
import a5x.cs2340.donationtracker.R;

public class AddDonationActivity extends AppCompatActivity {
    Button addDonationButton;
    Button cancelDonationButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donation);
        Spinner categorySpinner = findViewById(R.id.addDonationCategory);
        ArrayAdapter<DonationCategory> donationCategoryArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, DonationCategory.values());
        donationCategoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(donationCategoryArrayAdapter);
        addDonationButton = findViewById(R.id.addDonationConfirmButton);
        cancelDonationButton = findViewById(R.id.addDonationCancelButton);
    }

}
