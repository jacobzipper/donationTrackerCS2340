package a5x.cs2340.donationTracker.webservice;

import a5x.cs2340.donationTracker.webservice.bodies.SearchDonationsMap;
import a5x.cs2340.donationTracker.webservice.responses.StandardResponse;
import a5x.cs2340.donationTracker.webservice.responses.responseobjects.Donation;
import a5x.cs2340.donationTracker.webservice.responses.responseobjects.GetDonationsResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Service for handling donation based server calls
 *
 * Suppress spellcheck because api routes are lowercase and case sensitive
 */
@SuppressWarnings("SpellCheckingInspection")
public interface
DonationService {
    /**
     * Get the donations based on the passed in jwt
     * @param apiType the type of the api corresponding to the logged in account
     * @param jwt the jwt to check and return donation based on
     * @return the server call with the proper response
     */
    @GET("{apiType}/getdonations")
    Call<GetDonationsResponse> getDonations(@Path("apiType") String apiType,
                                            @Header("Authorization") String jwt);

    /**
     * Get a filtered list of donations based on the query map
     * @param apiType the type of the api corresponding to the logged in account
     * @param jwt the jwt to check and return donation based on
     * @param queryMap the map to make the search based on
     * @return the server call with the proper response
     */
    @GET("{apiType}/searchdonations")
    Call<GetDonationsResponse> searchDonations(@Path("apiType") String apiType,
                                               @Header("Authorization") String jwt,
                                               @QueryMap SearchDonationsMap queryMap);

    /**
     * Add a passed in donation based on the jwt
     * @param apiType the type of the api corresponding to the logged in account
     * @param donation the donation to add
     * @param jwt the jwt of the user adding the donation
     * @return the server call with a standard response
     */
    @POST("{apiType}/adddonation")
    Call<StandardResponse> addDonation(@Path("apiType") String apiType, @Body Donation donation,
                                            @Header("Authorization") String jwt);
}
