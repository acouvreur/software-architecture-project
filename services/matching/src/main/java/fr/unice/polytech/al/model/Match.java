package fr.unice.polytech.al.model;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.LinkedList;

public class Match {

    private LinkedList<String> listMatch;

    public Match() {
        listMatch = new LinkedList<String>();
    }

    public void add(String announcementID) {
        listMatch.add(announcementID);
    }

    public int size() {
        return listMatch.size();
    }

    public String get(int i) {return listMatch.get(i);}

    public void empty() {
        listMatch.clear();
    }

    public JSONObject toJson() {
        JSONArray contents = new JSONArray();
        for(int i=0; i < listMatch.size(); i++) {
            contents.put(listMatch.get(i));
        }
        return new JSONObject().put("matchingAnnonceIDs", contents);
    }
}
