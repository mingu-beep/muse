package min.project.muse.util;

import lombok.extern.slf4j.Slf4j;
import min.project.muse.domain.Track;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ApiResponseParser {

    public static int getTracks(String response, List<Track> trackList) {

        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject object = (JSONObject) jsonParser.parse(response);
            JSONObject result = (JSONObject) object.get("results");

            int numberOfItem = Integer.parseInt((String) result.get("opensearch:totalResults"));

            JSONArray trackMatches = (JSONArray) ((JSONObject) result.get("trackmatches")).get("track");

            for (int i = 0; i < trackMatches.size(); i++) {
                JSONObject track = (JSONObject) trackMatches.get(i);
                log.info("{} // {} : {}", i, (String) track.get("artist"), (String) track.get("name"));
                trackList.add(new Track((String) track.get("name"), (String) track.get("artist")));
            }

            return numberOfItem;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;


    }
}
