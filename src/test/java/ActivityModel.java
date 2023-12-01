import java.util.ArrayList;
import java.util.Date;

public class ActivityModel {
    public String id;
    public String title;
    public Date date;
    public String description;
    public String category;
    public String city;
    public String venue;
    public ArrayList<AtendeeModel> attendees;
    public String hostUsername;
    public boolean isCancelled;
}
