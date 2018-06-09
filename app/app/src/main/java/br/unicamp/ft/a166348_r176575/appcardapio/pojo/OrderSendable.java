package br.unicamp.ft.a166348_r176575.appcardapio.pojo;

import java.util.Arrays;

/**
 * Created by andre on 06/06/2018.
 */

public class OrderSendable {

    private long visitId;
    private Long[] products;
    private Long[] localIds;
    private String orderStatus;


    public OrderSendable(long visitId, Long[] products, Long[] localIds, String orderStatus) {
        this.visitId = visitId;
        this.products = products;
        this.localIds = localIds;
        this.orderStatus = orderStatus;
    }

    public OrderSendable() {
    }

    public Long[] getLocalIds() {
        return localIds;
    }

    public void setLocalIds(Long[] localIds) {
        this.localIds = localIds;
    }

    public long getVisitId() {
        return visitId;
    }

    public void setVisitId(long visitId) {
        this.visitId = visitId;
    }

    public Long[] getProducts() {
        return products;
    }

    public void setProducts(Long[] products) {
        this.products = products;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "OrderSendable{" +
                "visitId=" + visitId +
                ", products=" + Arrays.toString( products ) +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}
