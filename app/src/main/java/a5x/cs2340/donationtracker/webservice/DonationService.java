package a5x.cs2340.donationtracker.webservice;

import a5x.cs2340.donationtracker.webservice.responses.StandardResponse;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Donation;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.GetDonationsResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface DonationService {
    @GET("/employee/donations")
    Call<GetDonationsResponse> getDonations(@Header("Authorization") String jwt);

    @POST("employee/adddonation")
    Call<StandardResponse> addDonation(@Body Donation donation, @Header("Authorization") String jwt);
}
