package de.dashup.test;

import de.dashup.model.db.Database;
import de.dashup.model.service.DashupService;
import de.dashup.shared.*;
import de.dashup.util.string.Hash;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class LayoutModeTest {

    private static Database database;
    private static DashupService dashupService;
    private static User user;

    @BeforeAll
    static void createDatabaseConnection() throws SQLException {
        database = UnitTestUtil.getDBInstance();
        dashupService = DashupService.getInstance();


        String salt = "VQoX3kxwjX3gOOY1Jixk)Dc$0y$e4B!9";
        String hashedPassword = Hash.create("password", salt);
        user = new User(1, "nobody@test.com", "NobodyTest", "Nobody", "Test", hashedPassword, salt, null);

    }

    @BeforeEach
    void cleanDB() throws SQLException {
        UnitTestUtil.setUpTestDataset(database);
        UnitTestUtil.setUpTestDashup(database);
    }

    @Test
    void deleteAllPanelsTest() throws SQLException {

        List<LayoutModeSection> sectionPanelOrder = new ArrayList<>();
        List<LayoutModePanel> layoutModePanels = new ArrayList<>();
        List<LayoutModeSection> sectionsToDelete = new ArrayList<>();
        List<String> panelsToDelete = new ArrayList<>();

        LayoutModeSection layoutModeSection1 = new LayoutModeSection("s1", "section1", layoutModePanels);
        LayoutModeSection layoutModeSection2 = new LayoutModeSection("s2", "section2", layoutModePanels);
        sectionPanelOrder.add(layoutModeSection1);
        sectionPanelOrder.add(layoutModeSection2);

        LayoutModeStructure layoutModeStructure = new LayoutModeStructure(panelsToDelete, sectionPanelOrder, sectionsToDelete);
        dashupService.processLayoutModeChanges(layoutModeStructure, user);

        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("section_id", 1);

        JSONArray jsonObject = database.get(Database.Table.SECTIONS_PANELS, whereParameters);
        Assertions.assertTrue(jsonObject.isEmpty());
    }

    @Test
    void renameSectionTest() throws SQLException {
        List<LayoutModeSection> sectionPanelOrder = new ArrayList<>();
        List<LayoutModePanel> layoutModePanels = new ArrayList<>();
        List<LayoutModeSection> sectionsToDelete = new ArrayList<>();
        List<String> panelsToDelete = new ArrayList<>();

        LayoutModeSection layoutModeSection1 = new LayoutModeSection("s1", "section1", layoutModePanels);
        LayoutModeSection layoutModeSection2 = new LayoutModeSection("s2", "section renamed", layoutModePanels);
        sectionPanelOrder.add(layoutModeSection1);
        sectionPanelOrder.add(layoutModeSection2);

        LayoutModeStructure layoutModeStructure = new LayoutModeStructure(panelsToDelete, sectionPanelOrder, sectionsToDelete);
        dashupService.processLayoutModeChanges(layoutModeStructure, user);

        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("section_id", 1);

        JSONObject jsonObject = database.get(Database.Table.USER_SECTIONS, whereParameters).getJSONObject(0);
        String sectionName = jsonObject.getString("section_name");
        Assertions.assertEquals("section1",sectionName);

        whereParameters.clear();
        whereParameters.put("section_id", 2);
        jsonObject = database.get(Database.Table.USER_SECTIONS, whereParameters).getJSONObject(0);
        sectionName = jsonObject.getString("section_name");
        Assertions.assertEquals("section renamed",sectionName);

    }

    @Test
    void movePanelTest() throws SQLException {

        List<LayoutModePanel> layoutModePanels1 = new ArrayList<>();
        List<LayoutModePanel> layoutModePanels2 = new ArrayList<>();
        List<LayoutModeSection> sectionPanelOrder = new ArrayList<>();
        List<LayoutModeSection> sectionsToDelete = new ArrayList<>();
        List<String> panelsToDelete = new ArrayList<>();


        LayoutModePanel layoutModePanel1 = new LayoutModePanel("p1", "medium");
        LayoutModePanel layoutModePanel2 = new LayoutModePanel("p2", "medium");

        layoutModePanels1.add(layoutModePanel1);
        layoutModePanels2.add(layoutModePanel2);

        LayoutModeSection layoutModeSection1 = new LayoutModeSection("s1", "section1", layoutModePanels1);
        LayoutModeSection layoutModeSection2 = new LayoutModeSection("s2", "section2", layoutModePanels2);

        sectionPanelOrder.add(layoutModeSection1);
        sectionPanelOrder.add(layoutModeSection2);

        LayoutModeStructure layoutModeStructure = new LayoutModeStructure(panelsToDelete, sectionPanelOrder, sectionsToDelete);
        dashupService.processLayoutModeChanges(layoutModeStructure, user);

        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("section_id", 1);

        JSONObject jsonObject = database.get(Database.Table.SECTIONS_PANELS, whereParameters).getJSONObject(0);
        Assertions.assertEquals(1, jsonObject.getInt("panel_id"));

        whereParameters.clear();
        whereParameters.put("section_id", 2);
        jsonObject = database.get(Database.Table.SECTIONS_PANELS, whereParameters).getJSONObject(0);
        Assertions.assertEquals(2, jsonObject.getInt("panel_id"));
    }

    @Test
    void deleteSections() throws SQLException{

        List<LayoutModeSection> sectionPanelOrder = new ArrayList<>();
        List<LayoutModeSection> sectionsToDelete = new ArrayList<>();
        List<String> panelsToDelete = new ArrayList<>();

        LayoutModeSection layoutModeSection1 = new LayoutModeSection("s1", "section1", null);
        LayoutModeSection layoutModeSection2 = new LayoutModeSection("s2", "section2", null);

        sectionsToDelete.add(layoutModeSection1);
        sectionsToDelete.add(layoutModeSection2);

        LayoutModeStructure layoutModeStructure = new LayoutModeStructure(panelsToDelete, sectionPanelOrder, sectionsToDelete);
        dashupService.processLayoutModeChanges(layoutModeStructure, user);

        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("user_id", 1);
        JSONArray jsonObject = database.get(Database.Table.USER_SECTIONS, whereParameters);

        Assertions.assertTrue(jsonObject.isEmpty());
    }


}
