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
    private SearchDonationsTask searchDonationsTask;
    private Toolbar donationViewToolbar;
    private String[] locationsSearchableList;
    private String[] locationsShowableList;
    private final String[] SEARCH_DONATION_CATEGORIES = {"Any", "Clothing", "Hat", "Kitchen",
            "Electronics", "Household", "Other"};
    private final String[] NO_SEARCH_RESULTS = {"No Results"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_donations);
        Button backButton = findViewById(R.id.donationListBackButton);
        backButton.setOnClickListener(v -> backToAdminTools());
        setDonationListToDefault();
        donationViewToolbar = findViewById(R.id.donationsViewToolbar);
        donationViewToolbar.setTitle(R.string.donation_view_toolbar_text);
        setSupportActionBar(donationViewToolbar);
        GetSearchableLocationsTask getSearchableLocationsTask =
                new GetSearchableLocationsTask(this);
        getSearchableLocationsTask.execute((Void) null);
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
                android.R.layout.simple_spinner_item, SEARCH_DONATION_CATEGORIES);
        donationCategoryArrayAdapter.
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(donationCategoryArrayAdapter);
        Spinner locationSpinner = dialog.findViewById(R.id.donationSearchLocationSpinner);
        ArrayAdapter<String> locationArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, locationsShowableList);
        locationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationArrayAdapter);
        Button donationSearchButton = dialog.findViewById(R.id.donationSearchGoButton);
        Button closeButton = dialog.findViewById(R.id.donationSearchCancelButton);
        closeButton.setOnClickListener(v->dialog.dismiss());

        donationSearchButton.setOnClickListener(view -> {

            EditText nameEntry = dialog.findViewById(R.id.donationSearchNameInput);
            String selectedCategory = ((categorySpinner.getSelectedItemPosition() == 0) ?
                    null : DonationCategory.values()
                    [categorySpinner.getSelectedItemPosition() - 1].toString());
            String selectedLocation = locationsSearchableList
                    [(locationSpinner.getSelectedItemPosition())];
            SearchDonationsMap searchQuery = new SearchDonationsMap(nameEntry.getText().toString(),
                    selectedCategory, selectedLocation);
            searchDonationsTask = new SearchDonationsTask(ViewDonationsActivity.this,
                    searchQuery);
            searchDonationsTask.execute((Void) null);
            dialog.dismiss();

        });

        dialog.show();
    }
    private void setDonationListToDefault() {
        GetDonationsTask getDonationsTask = new GetDonationsTask(this);
        getDonationsTask.execute((Void) null);
    }
    void updateListView(Donation[] donationList, List<String> donationSDescriptions) {
        if (donationList.length == 0) {
            ((ListView) this.findViewById(R.id.donationsList)).
                    setAdapter(new ArrayAdapter<>(this,
                            android.R.layout.simple_list_item_1, android.R.id.text1,
                            NO_SEARCH_RESULTS));
            ((ListView) findViewById(R.id.donationsList)).
                    setOnItemClickListener((parent, view, position, id) -> {} );
            return;
        }
        ((ListView) this.findViewById(R.id.donationsList)).
                setAdapter(new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, android.R.id.text1,
                        donationSDescriptions));
        ((ListView) findViewById(R.id.donationsList)).
                setOnItemClickListener((parent, view, position, id) -> {
                    Donation donation = donationList[(position)];
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
    void setLocationsLists(List<String> searchableList, List<String> showableList) {
        locationsSearchableList = new String[searchableList.size()];
        locationsSearchableList = searchableList.toArray(locationsSearchableList);
        locationsShowableList = new String[showableList.size()];
        locationsShowableList = showableList.toArray(locationsShowableList);
        switchToMakingSearch();
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
