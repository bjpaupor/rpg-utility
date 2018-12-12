package pathfinder;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class Pfdb {
    private static MongoClient client = MongoClients.create();

    public static MongoDatabase getDB() {
        return client.getDatabase("pfdb");
    }
}