package a5x.cs2340.donationtracker.activities.postlogin;

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

public class GetDonationsTask extends WebserviceTask<ViewDonationsActivity, Object, GetDonationsResponse> {
    private List<Donation> donations;
    private List<String> donationNames;

    public GetDonationsTask(ViewDonationsActivity context, Object body) {
        super(context, body);
    }
    @Override
    public Response<GetDonationsResponse> doRequest(Object body) throws IOException {
        return Webservice.donationService.getDonations(Webservice.getJwtToken());
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void useResponse(GetDonationsResponse response) {
        donations = response.getDonations();
        donationNames = donations.stream().map(d -> d.getName()).collect(Collectors.toList());
    }

    @Override
    public void uiSuccess() {
        ((ListView) mContext.findViewById(R.id.locationlist)).setAdapter(new ArrayAdapter<String>(mContext,
                android.R.layout.simple_list_item_1, android.R.id.text1, donationNames));
        ((ListView) mContext.findViewById(R.id.locationlist)).setOnItemClickListener((parent, view, position, id) -> {
            Donation donation = donations.get(position);
            final Dialog dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.donation_view);
            dialog.setTitle("Donation Details");

            // set the custom dialog components - text, image and button
            ((TextView) dialog.findViewById(R.id.donationName)).setText(donation.getName());
            ((TextView) dialog.findViewById(R.id.donationDescription)).setText(donation.getDescription());
            ((TextView) dialog.findViewById(R.id.donationValue)).setText(Double.toString(donation.getValue()));
            ((TextView) dialog.findViewById(R.id.donationCategory)).setText(donation.getCategory().getName());
            ((TextView) dialog.findViewById(R.id.donationComments)).setText(donation.getComments());

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
