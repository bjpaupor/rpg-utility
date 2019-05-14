package pathfinder;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
/**
 * Tests Abilities
 */
public class AfflictionTest {
    // @Test
    // public void test() {
    //     MongoCollection<Document> collection = Pfdb.getDB().getCollection("affliction");
	// 	for (Document d : collection.find(new Document("name", "Demo"))) {
    //         Affliction demo = new Affliction(d);
    //         assertEquals("Demo", demo.getName());
    //         assertEquals("Description.", demo.getDescription());
    //         assertArrayEquals(new Affliction.Type[] {Affliction.Type.CONTACT, 
    //             Affliction.Type.DISEASE}, demo.getTypes());
    //         assertEquals("Fortitude DC 17 negates, Will DC 17 partial", demo.getSave());
    //         assertEquals("1 minute", demo.getOnset());
    //         assertEquals("1/round for 6 rounds", demo.getFrequency());
    //         assertEquals("1 Str drain", demo.getInitialEffect());
    //     }
    // }
}