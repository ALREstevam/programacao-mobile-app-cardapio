package br.unicamp.ft.a166348_r176575.appcardapio.api;

/**
 * Created by andre on 02/04/2018.
 */

abstract public class GeneralService {
    private String apiUrl;

    public GeneralService(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    abstract public String post(String json, String route);
    abstract public String get(String route);
}
