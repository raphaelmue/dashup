package de.dashup.test;

import de.dashup.model.db.Database;
import de.dashup.model.service.DashupService;
import de.dashup.shared.layout.Widget;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
@Tag("unit")
public class FilterUnitTest {
    private static Database database;
    private static DashupService dashupService;

    //---------------Setup---------------\\
    @BeforeAll
    public static void setupEnvironment() throws SQLException {
        database = UnitTestUtil.getDBInstance();
        dashupService = UnitTestUtil.getServiceInstance();
    }

    @BeforeEach
    public void cleanDB() throws SQLException {
        UnitTestUtil.setUpTestDataset(database);
    }

    @Test
    public void testFilterWidgetsByName() throws SQLException{
        List<Widget> result = dashupService.findWidgetByName("panel1", null, null, null, null);
        Assertions.assertEquals(1,result.size());
        Assertions.assertEquals("panel1",result.get(0).getName());
        Assertions.assertEquals(75,result.get(0).getAverageRating());
    }

    @Test
    public void testFilterWidgetsByDate() throws SQLException{
        List<Widget> result = dashupService.findWidgetByName("", "2019-04-20", null, null, null);
        Assertions.assertEquals(1,result.size());
        Assertions.assertEquals("panel1",result.get(0).getName());
        Assertions.assertEquals(75,result.get(0).getAverageRating());
    }

    @Test
    public void testFilterWidgetsByRating() throws SQLException{
        List<Widget> result = dashupService.findWidgetByName("", null, "51", null, null);
        Assertions.assertEquals(2,result.size());
        Assertions.assertEquals("panel1",result.get(0).getName());
        Assertions.assertEquals(75,result.get(0).getAverageRating());
        Assertions.assertEquals("panel3",result.get(1).getName());
        Assertions.assertEquals(51,result.get(1).getAverageRating());
    }

    @Test
    public void testFilterWidgetsByCategory() throws SQLException{
        ArrayList<String> categories = new ArrayList<>();
        categories.add("productivity");
        List<Widget> result = dashupService.findWidgetByName("", null, null, categories, null);
        Assertions.assertEquals(1,result.size());
        Assertions.assertEquals("panel3",result.get(0).getName());
        Assertions.assertEquals(51,result.get(0).getAverageRating());
    }

    @Test
    public void testFilterWidgetsByPublisher() throws SQLException{
        ArrayList<String> publisher = new ArrayList<>();
        publisher.add("Nobody Test");
        List<Widget> result = dashupService.findWidgetByName("", null, null, null, publisher);
        Assertions.assertEquals(3,result.size());
    }
}
