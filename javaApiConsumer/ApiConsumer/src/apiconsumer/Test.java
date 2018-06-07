/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apiconsumer;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import java.util.List;
import myapiconsumer.ApiConsumer;
import pojo.Person;
import pojo.Product;

/**
 *
 * @author andre
 */
public class Test {
    public static void main(String args[]){
        Genson genson = new Genson();
        String jsonstr = "{    \"description\": \"Descricao produto1\",     \"name\": \"Produto1\",     \"picture\": \"1.png\",     \"price\": \"10.00\",     \"produtctId\": 1,     \"type\": \"DOCE\"  }";
        Product product = genson.deserialize(jsonstr, Product.class);
        System.out.println(product);
        
        //jsonstr = "[  {    \"description\": \"Descricao produto1\",     \"name\": \"Produto1\",     \"picture\": \"1.png\",     \"price\": \"10.00\",     \"produtctId\": 1,     \"type\": \"DOCE\"  },   {    \"description\": \"Descricao produto 3\",     \"name\": \"Produto3\",     \"picture\": \"3.png\",     \"price\": \"5.00\",     \"produtctId\": 3,     \"type\": \"SALGADO\"  },   {    \"description\": \"Descricao produto 2\",     \"name\": \"Produto2\",     \"picture\": \"2.png\",     \"price\": \"55.90\",     \"produtctId\": 2,     \"type\": \"SALGADO\"  },   {    \"description\": \"Cachorro quente com batata palha e pur\\u00ea\",     \"name\": \"Cachorro Quente\",     \"picture\": \"cq.png\",     \"price\": \"6.00\",     \"produtctId\": 4,     \"type\": \"SALGADO\"  }]\n";
        jsonstr = "[{\"description\":\"Descricaoproduto1\",\"name\":\"Produto1\",\"picture\":\"1.png\",\"price\":\"10.00\",\"produtctId\":1,\"type\":\"DOCE\"},{\"description\":\"Descricaoproduto3\",\"name\":\"Produto3\",\"picture\":\"3.png\",\"price\":\"5.00\",\"produtctId\":3,\"type\":\"SALGADO\"},{\"description\":\"Descricaoproduto2\",\"name\":\"Produto2\",\"picture\":\"2.png\",\"price\":\"55.90\",\"produtctId\":2,\"type\":\"SALGADO\"},{\"description\":\"Cachorroquentecombatatapalhaepur\\u00ea\",\"name\":\"CachorroQuente\",\"picture\":\"cq.png\",\"price\":\"6.00\",\"produtctId\":4,\"type\":\"SALGADO\"}]";
        
        List<Product> lst = genson.deserialize(jsonstr, new GenericType<List<Product>>(){});
        
        for(Product prod: lst){
            System.out.println(prod);
        }
        
        String apiUrl = "http://127.0.0.1:5000";
       
        ApiConsumer cons = new ApiConsumer(apiUrl);
        String prodsJson = cons.get("/products");
        
        System.out.println(prodsJson);
        
        List<Product> lst1 = genson.deserialize(prodsJson, new GenericType<List<Product>>(){});
        
        
        for(Product prod: lst1){
            System.out.println(prod);
        }
        
        
    }
}
