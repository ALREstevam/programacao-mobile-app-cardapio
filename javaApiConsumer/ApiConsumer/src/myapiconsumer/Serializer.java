/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapiconsumer;

import com.owlike.genson.Genson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author andre
 */
public class Serializer<T> {
    private Genson genson;
    final Class<T> typeParameterClass;

    public Serializer(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
        this.genson = new Genson();
    }

    public Serializer() {
        this.genson = new Genson();
        this.typeParameterClass = (Class<T>) Object.class;
    }
    
    /**
     * Map<String, Object> person = new HashMap<String, Object>() {{
        put("name", "Foo");
        put("age", 28);
      }};
     * @param input
     * @return 
     */
    public String serialize(Map<String, Object> input){    
        return genson.serialize(input);
    }
    
    public String serialize(T elem){
        return genson.serialize(elem);
    }
    
    public String serialize(List<T> elems){
        return genson.serialize(elems);
    }
    
}
