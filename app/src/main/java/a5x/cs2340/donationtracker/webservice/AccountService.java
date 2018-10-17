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

public interface AccountService {
    @POST("/login")
    Call<LoginResponse> login(@Body LoginBody body);

    @POST("/registration")
    Call<StandardResponse> register(@Body RegistrationBody body);

    @GET("/locations")
    Call<GetLocationsResponse> locations();
}
