package pathfinder;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
/**
 * Tests Abilities
 */
public class FeatureTest {
    @Test
    public void test() {
        MongoCollection<Document> collection = Pfdb.getDB().getCollection("feature");
		for (Document d : collection.find(new Document("name", "Demo"))) {
            Feature demo = new Feature(d);
            assertEquals("Demo", demo.getName());
            assertEquals("Description.", demo.getDescription());
        }
    }
}