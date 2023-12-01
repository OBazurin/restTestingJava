import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class GetActivitiesTests {
    @Test
    public void getActivitiesAsBob()
    {
        LoginDto bob = new LoginDto();
        bob.email = "bob@test.com";
        bob.password = "Pa$$w0rd";

        var cl = new ActivitiesRestClient(bob);
        var activities = cl.getActivities();
        Assert.assertNotNull(activities);
    }
}
