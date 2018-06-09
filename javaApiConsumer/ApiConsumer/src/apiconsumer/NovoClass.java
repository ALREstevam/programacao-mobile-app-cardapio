/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apiconsumer;

import pojo.ProductType;
import pojo.Product;
import java.util.ArrayList;
import java.util.List;
import myapiconsumer.Deserializer;
import myapiconsumer.Serializer;

/**
 *
 * @author andre
 */
public class NovoClass {
    
    public static void main(String[] args) {
        
        List<Product> prods = new ArrayList<>();
        
        prods.add(new Product("Produto 1", "Descr 1", "1.png", ProductType.DOCE, 0.5, 0));
        prods.add(new Product("Produto 2", "Descr 2", "2.png", ProductType.BEBIDA_ALCOOLICA, 0, 1));
        prods.add(new Product("Produto 3", "Descr 3", "3.png", ProductType.SALGADO, 10.0, 2));
        prods.add(new Product("Produto 4", "Descr 4", "4.png", ProductType.SOBREMESA, 10.5, 3));
        
        Deserializer<Product> desc = new Deserializer<>(Product.class);
        Serializer<Product> ser = new Serializer<>(Product.class);
        
        System.out.println(ser.serialize(prods.get(0)));
        
        
    }

    
    

   
    
}
