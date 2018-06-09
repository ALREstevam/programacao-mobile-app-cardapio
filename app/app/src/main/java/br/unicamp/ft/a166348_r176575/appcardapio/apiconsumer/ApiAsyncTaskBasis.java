package br.unicamp.ft.a166348_r176575.appcardapio.apiconsumer;

import android.os.AsyncTask;

/**
 * Created by andre on 06/06/2018.
 */

abstract public class ApiAsyncTaskBasis<Input, Output> extends AsyncTask<Input, Void, Output> {

    final private String apiUrl, apiRoute;
    private final Input in;

    public ApiAsyncTaskBasis(String apiUrl, String apiRoute) {
        this.apiUrl = apiUrl;
        this.apiRoute = apiRoute;
        this.in = null;
    }

    public ApiAsyncTaskBasis(String apiUrl, String apiRoute, Input in) {
        this.apiUrl = apiUrl;
        this.apiRoute = apiRoute;
        this.in = in;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getApiRoute() {
        return apiRoute;
    }

    public Input getIn() {
        return in;
    }
}
