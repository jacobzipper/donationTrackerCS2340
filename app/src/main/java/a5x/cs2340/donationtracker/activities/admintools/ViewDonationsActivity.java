package a5x.cs2340.donationtracker.activities.admintools;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import a5x.cs2340.donationtracker.DonationCategory;
import a5x.cs2340.donationtracker.R;
import a5x.cs2340.donationtracker.webservice.bodies.SearchDonationsMap;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Donation;

/**
 * Activity for employees to view the list of all donations for the location
 */
public class ViewDonationsActivity extends AppCompatActivity {
    private GetDonationsTask getDonationsTask = null;
    private SearchDonationsTask searchDonationsTask = null;
    private Toolbar donationViewToolbar;
    private Button backButton;
    private final String[] searchDonationCategories = {"Any", "Clothing", "Hat", "Kitchen",
            "Electronics", "Household", "Other"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_donations);
        backButton = findViewById(R.id.donationListBackButton);
        backButton.setOnClickListener(v -> backToAdminTools());
        setDonationListToDefault();
        donationViewToolbar = findViewById(R.id.donationsViewToolbar);
        donationViewToolbar.setTitle(R.string.donation_view_toolbar_text);
        setSupportActionBar(donationViewToolbar);
        switchToMakingSearch();
    }

    private void backToAdminTools() {
        Intent backToAdminToolsIntent = new Intent(this, AdminToolsActivity.class);
        startActivity(backToAdminToolsIntent);
    }

    private void displaySearch() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.donation_search_view);
        Spinner categorySpinner = dialog.findViewById(R.id.donationSearchCategorySpinner);
        ArrayAdapter<String> donationCategoryArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, searchDonationCategories);
        donationCategoryArrayAdapter.
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(donationCategoryArrayAdapter);
        Button donationSearchButton = dialog.findViewById(R.id.donationSearchGoButton);
        Button closeButton = dialog.findViewById(R.id.donationSearchCancelButton);
        closeButton.setOnClickListener(v->dialog.dismiss());

        donationSearchButton.setOnClickListener(view -> {

            EditText nameEntry = dialog.findViewById(R.id.donationSearchNameInput);
            String selectedCategory = (categorySpinner.getSelectedItemPosition() == 0 ?
                    null : DonationCategory.values()
                    [categorySpinner.getSelectedItemPosition() - 1].toString());
            SearchDonationsMap searchQuery = new SearchDonationsMap(nameEntry.getText().toString(),
                    selectedCategory, null);
            searchDonationsTask = new SearchDonationsTask(ViewDonationsActivity.this,
                    searchQuery);
            searchDonationsTask.execute((Void) null);
            dialog.dismiss();

        });

        dialog.show();
    }
    private void setDonationListToDefault() {
        getDonationsTask = new GetDonationsTask(this, null);
        getDonationsTask.execute((Void) null);
    }
    void updateListView(List<Donation> donationList, List<String> donationSDescriptions) {
        ((ListView) this.findViewById(R.id.donationsList)).
                setAdapter(new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, donationSDescriptions));
        ((ListView) findViewById(R.id.donationsList)).
                setOnItemClickListener((parent, view, position, id) -> {
                    Donation donation = donationList.get(position);
                    final Dialog dialog = new Dialog(this);
                    dialog.setContentView(R.layout.donation_view);
                    dialog.setTitle("Donation Details");

                    // set the custom dialog components - text, image and button
                    ((TextView) dialog.findViewById(R.id.donationName)).
                            setText(getString(R.string.donation_view_name,donation.getName()));
                    ((TextView) dialog.findViewById(R.id.donationShortDescription)).
                            setText(getString(R.string.donation_view_short_description,
                                    donation.getShortdescription()));
                    ((TextView) dialog.findViewById(R.id.donationDescription)).
                            setText(getString(R.string.donation_view_description,
                                    donation.getDescription()));
                    ((TextView) dialog.findViewById(R.id.donationValue)).
                            setText(getString(R.string.donation_view_value,donation.getValue()));
                    ((TextView) dialog.findViewById(R.id.donationCategory)).
                            setText(getString(R.string.donation_view_category,
                                    donation.getCategory().getName()));
                    ((TextView) dialog.findViewById(R.id.donationComments)).
                            setText(getString(R.string.donation_view_comments,
                                    donation.getComments()));
                    ((TextView) dialog.findViewById(R.id.donationTimeStamp)).
                            setText(getString(R.string.donation_view_timestamp,
                                    donation.getTstamp()));

                    Button dialogCloseButton = dialog.findViewById(R.id.donationBackButton);
                    // if button is clicked, close the custom dialog
                    dialogCloseButton.setOnClickListener(v -> dialog.dismiss());

                    dialog.show();
                });

    }
    void switchToClearingSearch() {
        donationViewToolbar.setNavigationIcon(android.R.drawable.ic_input_delete);
        donationViewToolbar.setNavigationOnClickListener(v -> setDonationListToDefault());
    }
    void switchToMakingSearch() {
        donationViewToolbar.setNavigationIcon(android.R.drawable.ic_menu_search);
        donationViewToolbar.setNavigationOnClickListener(v -> displaySearch());
    }
}
