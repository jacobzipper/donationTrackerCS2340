package a5x.cs2340.donationTracker.activities.adminTools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import a5x.cs2340.donationTracker.DonationCategory;
import a5x.cs2340.donationTracker.R;
import a5x.cs2340.donationTracker.webservice.responses.responseobjects.Donation;

/**
 * Activity for employees to create new donations to add
 */
public class AddDonationActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText shortDescriptionEditText;
    private EditText descriptionEditText;
    private EditText valueEditText;
    private Spinner categorySpinner;
    private EditText commentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donation);
        categorySpinner = findViewById(R.id.addDonationCategory);
        ArrayAdapter<DonationCategory> donationCategoryArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, DonationCategory.values());
        donationCategoryArrayAdapter.
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(donationCategoryArrayAdapter);
        Button addDonationButton = findViewById(R.id.addDonationConfirmButton);
        Button cancelDonationButton = findViewById(R.id.addDonationCancelButton);
        nameEditText = findViewById(R.id.addDonationName);
        shortDescriptionEditText = findViewById(R.id.addDonationShortDescription);
        descriptionEditText = findViewById(R.id.addDonationDescription);
        valueEditText = findViewById(R.id.addDonationValue);
        commentEditText = findViewById(R.id.addDonationComments);
        addDonationButton.setOnClickListener(v -> addDonation());
        cancelDonationButton.setOnClickListener(v -> returnToAdminTools());
    }

    private void addDonation() {
        if(errorCheck(nameEditText) && errorCheck(descriptionEditText)
                && errorCheck(valueEditText)) {
            Donation donationToAdd = new Donation(nameEditText.getText(),
                    shortDescriptionEditText.getText(),
                    descriptionEditText.getText(),
                    (valueEditText.getText()),
                    DonationCategory.values()[categorySpinner.getSelectedItemPosition()]);
            if (!TextUtils.isEmpty(commentEditText.getText())) {
                donationToAdd.setComments(commentEditText.getText());
            }
            AddDonationTask donationTask = new AddDonationTask(this, donationToAdd);
            donationTask.execute((Void) null);
            returnToAdminTools();
        }
    }
    private boolean errorCheck(EditText editText) {
        editText.setError(null);
        if (TextUtils.isEmpty(editText.getText())) {
            editText.setError(getString(R.string.error_field_required));
            return false;
        }
        return true;
    }
    private void returnToAdminTools() {
        Intent backToAdminToolsIntent = new Intent(this, AdminToolsActivity.class);
        startActivity(backToAdminToolsIntent);
    }
}
