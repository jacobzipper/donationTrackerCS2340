package a5x.cs2340.donationtracker;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import a5x.cs2340.donationtracker.activities.registration.RegistrationActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PasswordCheckerUnitTest {
    RegistrationActivity ra;
    Class<?> inner;
    Constructor<?> pstConst;
    Method method;
    Object pst;

    @Before
    public void setUp() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ra = new RegistrationActivity();
        inner = Class.forName("a5x.cs2340.donationtracker.activities.registration.RegistrationActivity$PasswordStrengthTask");
        pstConst = inner.getDeclaredConstructors()[0];
        pstConst.setAccessible(true);
        method = inner.getDeclaredMethod("updatePasswordStrength", String.class);
        method.setAccessible(true);
        pst = pstConst.newInstance(ra, "this doesn't matter");
    }

    private RegistrationActivity.PasswordStrength getResult(String password) throws InvocationTargetException, IllegalAccessException {
        return (RegistrationActivity.PasswordStrength) method.invoke(pst, password);
    }

    @Test
    public void passwordStrengthVeryWeak() throws IllegalAccessException, InvocationTargetException {
        RegistrationActivity.PasswordStrength result = getResult("P4ssw0rd");
        assertEquals(RegistrationActivity.PasswordStrength.VERY_WEAK, result);
    }

    @Test
    public void passwordStrengthWeak() throws InvocationTargetException, IllegalAccessException {
        RegistrationActivity.PasswordStrength result = getResult("30fl3");
        assertEquals(RegistrationActivity.PasswordStrength.WEAK, result);
    }

    @Test
    public void passwordStrengthAverage() throws InvocationTargetException, IllegalAccessException {
        RegistrationActivity.PasswordStrength result = getResult("30flj3");
        assertEquals(RegistrationActivity.PasswordStrength.AVERAGE, result);
    }

    @Test
    public void passwordStrengthStrong() throws InvocationTargetException, IllegalAccessException {
        RegistrationActivity.PasswordStrength result = getResult("9fk93g3");
        assertEquals(RegistrationActivity.PasswordStrength.STRONG, result);
    }

    @Test
    public void passwordStrengthVeryStrong() throws InvocationTargetException, IllegalAccessException {
        RegistrationActivity.PasswordStrength result = getResult("s2412jv894j$@1092");
        assertEquals(RegistrationActivity.PasswordStrength.VERY_STRONG, result);
    }
}
