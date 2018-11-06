package a5x.cs2340.donationtracker.webservice;

import a5x.cs2340.donationtracker.webservice.bodies.LoginBody;
import a5x.cs2340.donationtracker.webservice.bodies.RegistrationBody;
import a5x.cs2340.donationtracker.webservice.responses.GetLocationsResponse;
import a5x.cs2340.donationtracker.webservice.responses.LoginResponse;
import a5x.cs2340.donationtracker.webservice.responses.StandardResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Represents service calls based on login and registration
 */
public interface AccountService {
    /**
     * Attempts to login based on passed in body
     *
     * @param body the login body to login with
     * @return the server call
     */
    @POST("/login")
    Call<LoginResponse> login(@Body LoginBody body);

    /**
     * Attempts to register based on passed in body
     *
     * @param body the registration body to register with
     * @return the server call
     */
    @POST("/registration")
    Call<StandardResponse> register(@Body RegistrationBody body);

    /**
     * Gets the list of locations stored on the server
     *
     * @return the server call
     */
    @GET("/locations")
    Call<GetLocationsResponse> locations();
}
