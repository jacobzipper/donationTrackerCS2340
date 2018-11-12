package a5x.cs2340.donationtracker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import a5x.cs2340.donationtracker.models.users.UserType;
import a5x.cs2340.donationtracker.webservice.AccountService;
import a5x.cs2340.donationtracker.webservice.Errors;
import a5x.cs2340.donationtracker.webservice.bodies.RegistrationBody;
import a5x.cs2340.donationtracker.webservice.responses.StandardResponse;
import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RegistrationApiUnitTest {

    private List<String> validRoles = Arrays.asList(UserType.values()).stream()
            .map(userType -> userType.getAPIType()).collect(Collectors.toList());

    private RegistrationBody validRegistration = new RegistrationBody("user", "password",
            "users", "Person", "Person");

    private RegistrationBody validRegistrationNullName = new RegistrationBody("user", "password",
            "users", null, null);

    private RegistrationBody invalidRegistrationRole = new RegistrationBody("user", "password",
            "definitelynotarole", "Person", "Person");

    private RegistrationBody invalidRegistrationNullRequired = new RegistrationBody("user", "password",
            null, "Person", "Person");

    private RegistrationBody invalidRegistrationUsernameTaken = new RegistrationBody("user2", "newpassword",
            "employees", "PersonNew", "NewPerson");

    private static StandardResponse successBody = new StandardResponse(0, "Good!");

    private static StandardResponse failNullParam = new StandardResponse(1000, "Necessary param null");

    private static StandardResponse failInvalidRole = new StandardResponse(1001, "Role not valid");

    private static StandardResponse failUsernameTaken = new StandardResponse(1002, "Username taken");

    @Mock
    private static AccountService accountService;

    @Mock
    private static Call<StandardResponse> correctCall;

    @Mock
    private static Call<StandardResponse> incorrectCall;

    @Mock
    private static Response<StandardResponse> correctResponse;

    @Mock
    private static Response<StandardResponse> incorrectResponse;

    @Before
    public void setUp() throws IOException {
        when(incorrectCall.execute()).thenReturn(incorrectResponse);
        when(accountService.register(any())).thenReturn(incorrectCall);
        when(correctCall.execute()).thenReturn(correctResponse);
    }

    @Test
    public void testInvalidRegistrationNullField() throws IOException {



        when(accountService.register(argThat(body -> body.getRole() == null || body.getPassword() == null || body.getUsername() == null))).thenReturn(correctCall);
        when(correctResponse.body()).thenReturn(failNullParam);

        Call<StandardResponse> call = accountService.register(invalidRegistrationNullRequired);

        Response<StandardResponse> res = call.execute();

        assertEquals(Errors.errorsMap.get(res.body().getError()), Errors.ERROR_NULL_FIELDS_REGISTRATION);

    }

    @Test
    public void testInvalidRegistrationInvalidRole() throws IOException {

        when(accountService.register(argThat(body -> !validRoles.contains(body.getRole())))).thenReturn(correctCall);
        when(correctResponse.body()).thenReturn(failInvalidRole);

        Call<StandardResponse> call = accountService.register(invalidRegistrationRole);

        Response<StandardResponse> res = call.execute();

        assertEquals(Errors.errorsMap.get(res.body().getError()), Errors.ERROR_INVALID_ROLE);

    }

    @Test
    public void testValidRegistration() throws IOException {

        when(accountService.register(argThat(body -> body.getRole() != null && body.getPassword() != null && body.getUsername() != null && validRoles.contains(body.getRole())))).thenReturn(correctCall);
        when(correctResponse.body()).thenReturn(successBody);

        Call<StandardResponse> call = accountService.register(validRegistration);

        Response<StandardResponse> res = call.execute();

        assertEquals(Errors.errorsMap.get(res.body().getError()), Errors.ERROR_OK);

    }

    @Test
    public void testValidRegistrationNullNames() throws IOException {

        when(accountService.register(argThat(body -> body.getRole() != null && body.getPassword() != null && body.getUsername() != null && validRoles.contains(body.getRole())))).thenReturn(correctCall);
        when(correctResponse.body()).thenReturn(successBody);

        Call<StandardResponse> call = accountService.register(validRegistrationNullName);

        Response<StandardResponse> res = call.execute();

        assertEquals(Errors.errorsMap.get(res.body().getError()), Errors.ERROR_OK);
    }

    @Test
    public void testInvalidRegistrationUsernameUsed() throws IOException {

        when(accountService.register(argThat(body -> body.getRole() != null && body.getPassword() != null && body.getUsername() != null && validRoles.contains(body.getRole()) && body.getUsername() == validRegistration.getUsername()))).thenReturn(correctCall);
        when(correctResponse.body()).thenReturn(successBody).thenReturn(failUsernameTaken);

        Call<StandardResponse> call = accountService.register(validRegistration);

        Response<StandardResponse> res = call.execute();

        assertEquals(Errors.errorsMap.get(res.body().getError()), Errors.ERROR_OK);

        call = accountService.register(validRegistration);

        res = call.execute();

        assertEquals(Errors.errorsMap.get(res.body().getError()), Errors.ERROR_USERNAME_TAKEN);

    }
}