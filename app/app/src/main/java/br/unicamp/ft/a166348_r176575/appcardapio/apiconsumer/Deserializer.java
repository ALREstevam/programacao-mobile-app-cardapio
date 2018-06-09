package br.unicamp.ft.a166348_r176575.appcardapio.apiconsumer;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.util.List;


/**
 * Created by andre on 05/06/2018.
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
