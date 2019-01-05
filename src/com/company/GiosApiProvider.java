package com.company;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class of GIOŚ Provider. It is fetching data from api shared by Polish government.
 */
public class GiosApiProvider implements ApiProvider {
    public class StationIndexDeserializer implements JsonDeserializer {
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
                    if (Parameter.contains(paramFormula))
                        params.add(paramFormula);
                }
            }

            for (String paramName : params) {
                String calcDate = "";
                String sourceDate = "";
                String indexLevelName = "";
                if (!indexObject.get(String.format("%sCalcDate", paramName)).isJsonNull())
                    calcDate = indexObject.get(String.format("%sCalcDate", paramName)).getAsString();

                if (!indexObject.get(String.format("%sSourceDataDate", paramName)).isJsonNull())
                    sourceDate = indexObject.get(String.format("%sSourceDataDate", paramName)).getAsString();

                if (!indexObject.get(String.format("%sIndexLevel", paramName)).isJsonNull()) {
                    if (!indexObject.get(String.format("%sIndexLevel", paramName)).getAsJsonObject().get("indexLevelName").isJsonNull())
                        indexLevelName = indexObject.get(String.format("%sIndexLevel", paramName)).getAsJsonObject().get("indexLevelName").getAsString();
                }

                Parameter param = Parameter.valueOf(paramName.toUpperCase());
                result.parameters.put(param, new StationIndexParameter(calcDate, sourceDate, indexLevelName));
            }
            return result;
        }
    }

    public class SensorDeserializer implements JsonDeserializer {
        @Override
        public Sensor deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject sensorObject = jsonElement.getAsJsonObject();
            Integer sensorId = sensorObject.get("id").getAsInt();
            Integer stationId = sensorObject.get("stationId").getAsInt();
            String paramName = sensorObject.get("param").getAsJsonObject().get("paramFormula").getAsString();

            paramName = paramName.replaceAll("\\.", "");
            Parameter parameter = Parameter.valueOf(paramName.toUpperCase());
            Sensor sensor = new Sensor(sensorId, stationId, parameter);
            return sensor;
        }
    }

    public class DataDeserializer implements JsonDeserializer {
        @Override
        public HashMap<Date, Double> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject dataObject = jsonElement.getAsJsonObject();
            JsonArray valuesArray = dataObject.get("values").getAsJsonArray();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            HashMap<Date, Double> data = new HashMap<>();
            for (JsonElement valuesElement : valuesArray) {
                JsonObject valuesObject = valuesElement.getAsJsonObject();
                String dateString = valuesObject.get("date").getAsString();
                Double value = valuesObject.get("value").getAsDouble();

                try {
                    Date date = sdf.parse(dateString);
                    data.put(date, value);
                } catch (ParseException ignored) {

                }
            }

            return data;
        }
    }

    @Override
    public LinkedList<Station> fetchStations() {
        String urlString = "http://api.gios.gov.pl/pjp-api/rest/station/findAll";

        ApiReader ar = new ApiReader();
        String stationsString = ar.readURL(urlString);

        Gson gson = new Gson();
        Type linkedListType = new TypeToken<LinkedList<Station>>() {
        }.getType();
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

    @Override
    public LinkedList<Sensor> fetchSensor(Integer stationId) {
        String urlString = String.format("http://api.gios.gov.pl/pjp-api/rest/station/sensors/%d", stationId);

        ApiReader ar = new ApiReader();
        String sensorString = ar.readURL(urlString);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Sensor.class, new SensorDeserializer());
        gsonBuilder.registerTypeAdapter(HashMap.class, new DataDeserializer());
        Gson gson = gsonBuilder.create();

        Type linkedListType = new TypeToken<LinkedList<Sensor>>() {
        }.getType();
        LinkedList<Sensor> sensors = gson.fromJson(sensorString, linkedListType);

        for (Sensor sensor : sensors) {
            urlString = String.format("http://api.gios.gov.pl/pjp-api/rest/data/getData/%d", sensor.getID());

            String dataString = ar.readURL(urlString);

            Type hashMapType = new TypeToken<HashMap<Date, Double>>(){}.getType();
            HashMap<Date, Double> values = gson.fromJson(dataString, hashMapType);

            sensor.setValues(values);
        }

        return sensors;
    }
}
