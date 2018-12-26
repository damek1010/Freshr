package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Class helping reading data from external api. Commonly used in api providers.
 */
public class ApiReader {
    /**
     * @param urlString URL in string format of api endpoint
     * @return JSON in String format
     */
    public String readURL(String urlString) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(urlString).openStream()))) {
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
            return sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
