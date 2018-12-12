package pathfinder;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
/**
 * Tests Abilities
 */
public class AbilityTest {
    @Test
    public void testAbility() {
        MongoCollection<Document> collection = Pfdb.getDB().getCollection("ability");
		for (Document d : collection.find(new Document("name", "demo"))) {
            Ability demo = new Ability(d);
            assertEquals("demo", demo.getName());
            assertEquals("description", demo.getDescription());
            assertEquals(Ability.Type.EX, demo.getType());
        }
			
    }
}