package com.company;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Class of GIOŚ Provider. It is fetching data from api shared by Polish government.
 */
public class GiosApiProvider implements ApiProvider {
    public static class StationIndexDeserializer implements JsonDeserializer {
        @Override
        public StationIndex deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject indexObject = jsonElement.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entrySet = indexObject.entrySet();

            LinkedList<String> params = new LinkedList<>();

            StationIndex result = new StationIndex();

            for (Map.Entry<String, JsonElement> entry : entrySet) {
                String key = entry.getKey();
                if (key.contains("CalcDate")) {
                    String paramFormula = key.replaceAll("CalcDate", "");
                    params.add(paramFormula);
                }
            }

            for (String param : params) {
                String calcDate = "";
                String sourceDate = "";
                String indexLevelName = "";
                if (!indexObject.get(String.format("%sCalcDate", param)).isJsonNull())
                    calcDate = indexObject.get(String.format("%sCalcDate", param)).getAsString();

                if (!indexObject.get(String.format("%sSourceDataDate", param)).isJsonNull())
                    sourceDate = indexObject.get(String.format("%sSourceDataDate", param)).getAsString();

                if (!indexObject.get(String.format("%sIndexLevel", param)).isJsonNull()) {
                    if (!indexObject.get(String.format("%sIndexLevel", param)).getAsJsonObject().get("indexLevelName").isJsonNull())
                        indexLevelName = indexObject.get(String.format("%sIndexLevel", param)).getAsJsonObject().get("indexLevelName").getAsString();
                }


                result.parameters.put(param, new StationIndexParameter(calcDate, sourceDate, indexLevelName));
            }
            return result;
        }
    }

    @Override
    public LinkedList<Station> fetchStations() {
        String urlString = "http://api.gios.gov.pl/pjp-api/rest/station/findAll";

        ApiReader ar = new ApiReader();
        String stationsString = ar.readURL(urlString);

        Gson gson = new Gson();
        Type linkedListType = new TypeToken<LinkedList<Station>>(){}.getType();
        LinkedList<Station> stations = gson.fromJson(stationsString, linkedListType);

        return stations;
    }

    @Override
    public StationIndex fetchStationIndex(Integer stationId) {
        String urlString = String.format("http://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/%d", stationId);

        ApiReader ar = new ApiReader();
        String stationIndexString = ar.readURL(urlString);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(StationIndex.class, new StationIndexDeserializer());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(stationIndexString, StationIndex.class);
    }
}
