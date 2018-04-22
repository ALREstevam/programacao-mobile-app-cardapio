/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapiconsumer;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import java.util.List;

/**
 *
 * @author andre
 */
public class Deserializer<T> {
    private Genson genson;
    final Class<T> typeParameterClass;

    public Deserializer(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
        this.genson = new Genson();
    }
    
    public T deserialize(String json){
        return genson.deserialize(json, typeParameterClass);
    }
    
    public List<T> deserializeList(String json){
        return genson.deserialize(json, new GenericType<List<T>>(){});
    }
}
