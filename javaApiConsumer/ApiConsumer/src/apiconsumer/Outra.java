/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apiconsumer;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import pojo.Product;
import java.util.ArrayList;
import java.util.List;
import myapiconsumer.ApiConsumer;
import myapiconsumer.Deserializer;
import myapiconsumer.Serializer;

/**
 *
 * @author andre
 */
public class Outra {
    public static void main(String[] args) {
        //
        String apiUrl = "http://andreluiz342.pythonanywhere.com";
        //apiUrl = "http://127.0.0.1:5000";
        ApiConsumer cons = new ApiConsumer(apiUrl);
        String prodsJson = cons.get("/products");
        
        Genson genson = new Genson();
        List<Product> lst1 = genson.deserialize(prodsJson, new GenericType<List<Product>>(){});
        
        System.out.println(Product.class);
        
        System.out.println("String from server:");
        System.out.println(prodsJson);
        System.out.println("Java POJO objects:");
        
        for(Product prod : lst1){
            System.out.println(prod);
        }
        
       
        
                
    }
    
}
