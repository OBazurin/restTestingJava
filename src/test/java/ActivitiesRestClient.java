import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.ArrayList;

public class ActivitiesRestClient {
    RestClient client;

    public ActivitiesRestClient(LoginDto user)
    {
        client = new RestClient(user);
    }

    public ArrayList<ActivityModel> getActivities() {
        ArrayList<ActivityModel> activities = null;
            var type = TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, ActivityModel.class);
        try {
            return client.getWithCollectionResult("/activities", type);
        } catch (JsonProcessingException e) {
            return null;
        }

    }
}
