/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apiconsumer;

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
        
        ApiConsumer cons = new ApiConsumer(" http://127.0.0.1:5000");
        
        String prodsJson = cons.get("/products");
        Deserializer<Product> productDeserializer = new Deserializer<>(Product.class);
        List<Product> products = productDeserializer.deserializeList(prodsJson);
        System.out.println("String from server:");
        System.out.println(prodsJson);
        System.out.println("Java POJO objects:");
        
        for(Product p : products){
            System.out.println((Product) p);
        }
    }
    
}
