package br.unicamp.ft.a166348_r176575.appcardapio.apiconsumer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by andre on 05/06/2018.
 */

public class ApiConsumer {
    private String strUrl = "";

    public ApiConsumer(String strUrl) {
        this.strUrl = strUrl;
    }

    public String get(String route) {
        try {

            URL url = new URL(this.strUrl + route);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if ((conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) && (conn.getResponseCode() != HttpURLConnection.HTTP_OK)) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            String returnable = "";
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                //System.out.println(output);
                returnable += output;

            }
            conn.disconnect();
            return returnable;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String post(String route, String input){
        try {
            URL url = new URL(this.strUrl + route);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            //String input = "{\"qty\":100,\"name\":\"iPad 4\"}";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if ((conn.getResponseCode() != HttpURLConnection.HTTP_CREATED)
                    && (conn.getResponseCode() != HttpURLConnection.HTTP_OK)) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String returnable = "";
            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                returnable += output;
            }

            conn.disconnect();
            return returnable;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }





}
