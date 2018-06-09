package br.unicamp.ft.a166348_r176575.appcardapio.apiconsumer;

import com.owlike.genson.Genson;

import java.util.List;
import java.util.Map;

/**
 * Created by andre on 05/06/2018.
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

    public String serialize(Map<String, Object> input) {
        return genson.serialize( input );
    }

    public String serialize(T elem) {
        return genson.serialize( elem );
    }

    public String serialize(List<T> elems) {
        return genson.serialize( elems );
    }
}