package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Api {
    public static String fetch(String serviceUrl, String method) {
        String resp = null;
        try {
            URL url = new URL(serviceUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(method.toUpperCase());
            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                resp = response.toString();
            } else {
                System.out.println("La requête a échoué avec le code de réponse : " + responseCode);
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }
}
