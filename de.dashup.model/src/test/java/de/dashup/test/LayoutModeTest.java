package de.dashup.test;

import de.dashup.model.db.Database;
import de.dashup.model.service.DashupService;
import de.dashup.shared.layout.LayoutModeSectionDTO;
import de.dashup.shared.layout.LayoutModeStructureDTO;
import de.dashup.shared.layout.LayoutModeWidgetDTO;
import de.dashup.shared.User;
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

@SuppressWarnings("WeakerAccess")
public class LayoutModeTest {

    private static Database database;
    private static DashupService dashupService;
    private static User user;

    @BeforeAll
    public static void initializeConnections() throws SQLException {
        database = UnitTestUtil.getDBInstance();
        dashupService = DashupService.getInstance();


        String salt = "VQoX3kxwjX3gOOY1Jixk)Dc$0y$e4B!9";
        String hashedPassword = Hash.create("password", salt);
        user = new User(1, "nobody@test.com", "NobodyTest", "Nobody", "Test", hashedPassword, salt, null);

    }

    @BeforeEach
    public void cleanDB() throws SQLException {
        UnitTestUtil.setUpTestDataset(database);
    }

    @Test
    public void deleteAllPanelsTest() throws SQLException {

        List<LayoutModeSectionDTO> sectionPanelOrder = new ArrayList<>();
        List<LayoutModeWidgetDTO> layoutModeWidgetDTOS = new ArrayList<>();
        List<LayoutModeSectionDTO> sectionsToDelete = new ArrayList<>();

        LayoutModeSectionDTO layoutModeSectionDTO1 = new LayoutModeSectionDTO("s1", "section1", layoutModeWidgetDTOS);
        LayoutModeSectionDTO layoutModeSectionDTO2 = new LayoutModeSectionDTO("s2", "section2", layoutModeWidgetDTOS);
        sectionPanelOrder.add(layoutModeSectionDTO1);
        sectionPanelOrder.add(layoutModeSectionDTO2);

        LayoutModeStructureDTO layoutModeStructureDTO = new LayoutModeStructureDTO(sectionPanelOrder, sectionsToDelete);
        dashupService.processLayoutModeChanges(layoutModeStructureDTO, user);

        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", 1);

        JSONArray jsonObject = database.get(Database.Table.SECTIONS_PANELS, whereParameters);
        Assertions.assertTrue(jsonObject.isEmpty());
    }

    @Test
    public void renameSectionTest() throws SQLException {
        List<LayoutModeSectionDTO> sectionPanelOrder = new ArrayList<>();
        List<LayoutModeWidgetDTO> layoutModeWidgetDTOS = new ArrayList<>();
        List<LayoutModeSectionDTO> sectionsToDelete = new ArrayList<>();

        LayoutModeSectionDTO layoutModeSectionDTO1 = new LayoutModeSectionDTO("s1", "section1", layoutModeWidgetDTOS);
        LayoutModeSectionDTO layoutModeSectionDTO2 = new LayoutModeSectionDTO("s2", "section renamed", layoutModeWidgetDTOS);
        sectionPanelOrder.add(layoutModeSectionDTO1);
        sectionPanelOrder.add(layoutModeSectionDTO2);

        LayoutModeStructureDTO layoutModeStructureDTO = new LayoutModeStructureDTO(sectionPanelOrder, sectionsToDelete);
        dashupService.processLayoutModeChanges(layoutModeStructureDTO, user);

        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", 1);

        JSONObject jsonObject = database.get(Database.Table.USER_SECTIONS, whereParameters).getJSONObject(0);
        String sectionName = jsonObject.getString("section_name");
        Assertions.assertEquals("section1",sectionName);

        whereParameters.clear();
        whereParameters.put("id", 2);
        jsonObject = database.get(Database.Table.USER_SECTIONS, whereParameters).getJSONObject(0);
        sectionName = jsonObject.getString("section_name");
        Assertions.assertEquals("section renamed",sectionName);

    }

    @Test
    public void movePanelTest() throws SQLException {

        List<LayoutModeWidgetDTO> layoutModePanels1 = new ArrayList<>();
        List<LayoutModeWidgetDTO> layoutModePanels2 = new ArrayList<>();
        List<LayoutModeSectionDTO> sectionPanelOrder = new ArrayList<>();
        List<LayoutModeSectionDTO> sectionsToDelete = new ArrayList<>();

        LayoutModeWidgetDTO layoutModeWidgetDTO1 = new LayoutModeWidgetDTO("p1", "medium");
        LayoutModeWidgetDTO layoutModeWidgetDTO2 = new LayoutModeWidgetDTO("p2", "medium");

        layoutModePanels1.add(layoutModeWidgetDTO1);
        layoutModePanels2.add(layoutModeWidgetDTO2);

        LayoutModeSectionDTO layoutModeSectionDTO1 = new LayoutModeSectionDTO("s1", "section1", layoutModePanels1);
        LayoutModeSectionDTO layoutModeSectionDTO2 = new LayoutModeSectionDTO("s2", "section2", layoutModePanels2);

        sectionPanelOrder.add(layoutModeSectionDTO1);
        sectionPanelOrder.add(layoutModeSectionDTO2);

        LayoutModeStructureDTO layoutModeStructureDTO = new LayoutModeStructureDTO(sectionPanelOrder, sectionsToDelete);
        dashupService.processLayoutModeChanges(layoutModeStructureDTO, user);

        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", 1);

        JSONObject jsonObject = database.get(Database.Table.SECTIONS_PANELS, whereParameters).getJSONObject(0);
        Assertions.assertEquals(1, jsonObject.getInt("panel_id"));

        whereParameters.clear();
        whereParameters.put("id", 2);
        jsonObject = database.get(Database.Table.SECTIONS_PANELS, whereParameters).getJSONObject(0);
        Assertions.assertEquals(2, jsonObject.getInt("panel_id"));
    }

    @Test
    public void deleteSections() throws SQLException{

        List<LayoutModeSectionDTO> sectionPanelOrder = new ArrayList<>();
        List<LayoutModeSectionDTO> sectionsToDelete = new ArrayList<>();

        LayoutModeSectionDTO layoutModeSectionDTO1 = new LayoutModeSectionDTO("s1", "section1", null);
        LayoutModeSectionDTO layoutModeSectionDTO2 = new LayoutModeSectionDTO("s2", "section2", null);

        sectionsToDelete.add(layoutModeSectionDTO1);
        sectionsToDelete.add(layoutModeSectionDTO2);

        LayoutModeStructureDTO layoutModeStructureDTO = new LayoutModeStructureDTO(sectionPanelOrder, sectionsToDelete);
        dashupService.processLayoutModeChanges(layoutModeStructureDTO, user);

        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("user_id", 1);
        JSONArray jsonObject = database.get(Database.Table.USER_SECTIONS, whereParameters);

        Assertions.assertTrue(jsonObject.isEmpty());
    }


}
