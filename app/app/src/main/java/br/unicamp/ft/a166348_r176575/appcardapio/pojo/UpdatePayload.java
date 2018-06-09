package br.unicamp.ft.a166348_r176575.appcardapio.pojo;

/**
 * Created by andre on 08/06/2018.
 */

public class UpdatePayload{
    private String order_status;
    private String local_id;

    public UpdatePayload(String order_status, String local_id) {
        this.order_status = order_status;
        this.local_id = local_id;
    }

    public UpdatePayload() {
    }


    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getLocal_id() {
        return local_id;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }

    @Override
    public String toString() {
        return "UpdatePayload{" +
                "order_status='" + order_status + '\'' +
                ", local_id='" + local_id + '\'' +
                '}';
    }
}

