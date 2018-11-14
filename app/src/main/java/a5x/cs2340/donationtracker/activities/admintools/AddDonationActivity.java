package a5x.cs2340.donationtracker.activities.admintools;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;

import a5x.cs2340.donationtracker.DonationCategory;
import a5x.cs2340.donationtracker.R;
import a5x.cs2340.donationtracker.webservice.DonationService;
import a5x.cs2340.donationtracker.webservice.Webservice;
import a5x.cs2340.donationtracker.webservice.WebserviceTask;
import a5x.cs2340.donationtracker.webservice.responses.StandardResponse;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Donation;
import retrofit2.Call;
import retrofit2.Response;

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
        if (errorCheck(nameEditText) && errorCheck(descriptionEditText)
                && errorCheck(valueEditText)) {
            Donation donationToAdd = new Donation(nameEditText.getText(),
                    shortDescriptionEditText.getText(),
                    descriptionEditText.getText(),
                    (valueEditText.getText()),
                    DonationCategory.values()[categorySpinner.getSelectedItemPosition()]);
            if (!TextUtils.isEmpty(commentEditText.getText())) {
                donationToAdd.setComments(commentEditText.getText());
            }
            AddDonationTask donationTask = new AddDonationTask();
            donationTask.execute(donationToAdd);
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

    /**
     * Suppressed because need access to UI elements and can't make this static
     */
    @SuppressLint("StaticFieldLeak")
    private class AddDonationTask extends WebserviceTask<Donation, Void, StandardResponse> {
        @Override
        public Response<StandardResponse> doRequest(Donation body) throws IOException {
            Webservice webservice = Webservice.getInstance();
            DonationService donationService = webservice.getDonationService();
            Call<StandardResponse> standardResponseCall = donationService.
                    addDonation(webservice.getCurrentUserAPIType(),
                            body, "Bearer " + webservice.getJwtToken());
            return standardResponseCall.execute();
        }

    }
}
