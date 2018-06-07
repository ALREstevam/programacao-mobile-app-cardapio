package br.unicamp.ft.a166348_r176575.appcardapio.pojo;

/**
 * Created by andre on 06/06/2018.
 */

public class OrderSendable {

    /*"visitId": 6,
	"products": [1, 2, 3, 4]*/

    private long visitId;
    private Long[] products;

    public OrderSendable(long visitId, Long[] products) {
        this.visitId = visitId;
        this.products = products;
    }

    public OrderSendable() {
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
}
