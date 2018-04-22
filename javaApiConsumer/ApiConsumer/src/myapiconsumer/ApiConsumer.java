/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapiconsumer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 *
 * @author andre
 * Map<String, Object> map = genson.deserialize(singlePersonJson, Map.class);
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




/*

POST
http://127.0.0.1:5000/order
{
	"visitId": 5,
	"products": [1, 2, 3, 4]
      
}


POST
http://127.0.0.1:5000/visit
{
	"tableId":3,
	"ClientName":"Abraão",
	"ClientSex":"M",
	"PeopleOnTable":15
}




*/
