package a5x.cs2340.donationtracker.activities.admintools;

import android.app.Dialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import a5x.cs2340.donationtracker.R;
import a5x.cs2340.donationtracker.webservice.Webservice;
import a5x.cs2340.donationtracker.webservice.WebserviceTask;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Donation;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.GetDonationsResponse;
import retrofit2.Response;

/**
 * Webservice task for getting the list of donations
 */
public class GetDonationsTask extends WebserviceTask<ViewDonationsActivity,
        Object, GetDonationsResponse> {
    private List<Donation> donations;
    private List<String> donationSDescriptions;

    /**
     * Constructor for GetDonationsTask
     * @param context the Activity to use as context
     * @param body the body for the task (usually null)
     */
    public GetDonationsTask(ViewDonationsActivity context, Object body) {
        super(context, body);
    }
    @Override
    public Response<GetDonationsResponse> doRequest(Object body) throws IOException {
        return Webservice.donationService.getDonations(
                Webservice.getLoggedInUserType().getAPIType(),
                "Bearer " + Webservice.getJwtToken()).execute();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void useResponse(GetDonationsResponse response) {
        donations = response.getDonations();
        donationSDescriptions = donations.stream().map(Donation::getShortdescription).collect(Collectors.toList());
    }

    @Override
    public void uiSuccess() {
        ((ListView) mContext.findViewById(R.id.donationsList)).
                setAdapter(new ArrayAdapter<>(mContext,
                android.R.layout.simple_list_item_1, android.R.id.text1, donationSDescriptions));
        ((ListView) mContext.findViewById(R.id.donationsList)).
                setOnItemClickListener((parent, view, position, id) -> {
                Donation donation = donations.get(position);
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.donation_view);
                dialog.setTitle("Donation Details");

                // set the custom dialog components - text, image and button
                ((TextView) dialog.findViewById(R.id.donationName)).
                        setText(mContext.getString(R.string.donation_view_name,donation.getName()));
                ((TextView) dialog.findViewById(R.id.donationShortDescription)).
                        setText(mContext.getString(R.string.donation_view_short_description,
                                donation.getShortdescription()));
                ((TextView) dialog.findViewById(R.id.donationDescription)).
                        setText(mContext.getString(R.string.donation_view_description,
                                donation.getDescription()));
                ((TextView) dialog.findViewById(R.id.donationValue)).
                        setText(mContext.getString(R.string.donation_view_value,donation.getValue()));
                ((TextView) dialog.findViewById(R.id.donationCategory)).
                        setText(mContext.getString(R.string.donation_view_category,
                                donation.getCategory().getName()));
                ((TextView) dialog.findViewById(R.id.donationComments)).
                        setText(mContext.getString(R.string.donation_view_comments,
                                donation.getComments()));
                ((TextView) dialog.findViewById(R.id.donationTimeStamp)).
                        setText(mContext.getString(R.string.donation_view_timestamp,
                                donation.getTstamp()));

                Button dialogCloseButton = dialog.findViewById(R.id.donationBackButton);
                // if button is clicked, close the custom dialog
                dialogCloseButton.setOnClickListener(v -> dialog.dismiss());

                dialog.show();
            });
    }

    @Override
    public void uiFailure() {

    }
}
