package fr.unice.polytech.al.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.al.model.Announcement;
import fr.unice.polytech.al.model.AnnouncementType;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.DataInput;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class AnnouncementSerializerTest {
    private ObjectMapper mapper;
    private Announcement announcement;

    @Before
    public void initJson() throws ParseException {
        announcement = new Announcement(1, "toto", "Nice",
                "Marseille", new SimpleDateFormat("yyyy-mm-dd").parse("2018-02-18"), new SimpleDateFormat("yyyy-mm-dd").parse("2018-02-19"), AnnouncementType.COURSE);
        mapper = new ObjectMapper();
    }

    @Test
    public void testSerializer() throws IOException {
        String serialized = mapper.writeValueAsString(announcement);
        JSONObject json = new JSONObject(serialized);
        assertEquals(1, json.getInt("idTransmitter"));
        assertEquals("toto", json.getString("nameTransmitter"));
        assertEquals("Nice", json.getString("startPoint"));
        assertEquals("Marseille", json.getString("endPoint"));
        assertEquals("2018-02-18", json.getString("startDate"));
        assertEquals("2018-02-19", json.getString("endDate"));
        assertEquals("COURSE", json.getString("type"));
    }
}

