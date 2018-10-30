package a5x.cs2340.donationtracker.webservice;

import a5x.cs2340.donationtracker.webservice.bodies.SearchDonationsMap;
import a5x.cs2340.donationtracker.webservice.responses.StandardResponse;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Donation;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.GetDonationsResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Service for handling donation based server calls
 */
public interface
DonationService {
    /**
     * Get the donations based on the passed in jwt
     * @param jwt the jwt to check and return donation based on
     * @return the server call with the proper response
     */
    @GET("{apitype}/getdonations")
    Call<GetDonationsResponse> getDonations(@Path("apitype") String apiType, @Header("Authorization") String jwt);

    /**
     * Get the donations based on the passed in jwt
     * @param jwt the jwt to check and return donation based on
     * @return the server call with the proper response
     */
    @GET("{apitype}/searchdonations")
    Call<GetDonationsResponse> searchDonations(@Path("apitype") String apiType, @Header("Authorization") String jwt, @QueryMap SearchDonationsMap queryMap);

    /**
     * Add a passed in donation based on the jwt
     * @param donation the donation to add
     * @param jwt the jwt of the user adding the donation
     * @return the server call with a standard response
     */
    @POST("{apitype}/adddonation")
    Call<StandardResponse> addDonation(@Path("apitype") String apiType, @Body Donation donation,
                                            @Header("Authorization") String jwt);
}
