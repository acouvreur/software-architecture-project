package fr.unice.polytech.al.model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.LinkedList;


@Component
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


    public String toString() {
        String result = "";
        for(int i=0; i < listMatch.size() - 1; i++) {
            result += listMatch.get(i) + ";";
        }
        result += listMatch.get(listMatch.size() - 1);
        return result;
    }
}
