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
    private static Call<StandardResponse> validCall;

    @Mock
    private static Call<StandardResponse> invalidCall;

    @Mock
    private static Response<StandardResponse> validResponse;

    @Mock
    private static Response<StandardResponse> invalidResponse;

    @Before
    public void setUp() throws IOException {
        when(validResponse.body()).thenReturn(successBody);
        when(validResponse.code()).thenReturn(200);
        when(invalidResponse.code()).thenReturn(400);
        when(validCall.execute()).thenReturn(validResponse);
        when(invalidCall.execute()).thenReturn(invalidResponse);
        when(accountService.register(any())).thenReturn(validCall);
    }

    @Test
    public void testInvalidRegistrationNullField() throws IOException {

        when(accountService.register(argThat(body -> body.getRole() == null || body.getPassword() == null || body.getUsername() == null))).thenReturn(invalidCall);
        when(invalidResponse.body()).thenReturn(failNullParam);

        Call<StandardResponse> call = accountService.register(invalidRegistrationNullRequired);

        Response<StandardResponse> res = call.execute();

        assertEquals(res.code(), 400);
        assertEquals(Errors.errorsMap.get(res.body().getError()), Errors.ERROR_NULL_FIELDS_REGISTRATION);

    }
}