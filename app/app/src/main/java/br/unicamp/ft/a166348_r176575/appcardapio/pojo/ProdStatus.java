package br.unicamp.ft.a166348_r176575.appcardapio.pojo;

/**
 * Created by andre on 07/06/2018.
 */

public enum ProdStatus{
        SEM_STATUS("****"),
        NAO_PEDIDO("Não pedido"),
        ENVIANDO("Enviando..."),
        ENVIADO_PARA_A_COZINHA("Enviado para a cozinha"),
        PREPARANDO("Preparando"),
        PRONTO("Pronto!"),
        ENTREGUE("Entregue"),
        PEDIDO_ANTIGO("Pedido antigo (não considerado no pagamento)");

    private String statusAsText;
    ProdStatus(String text){
        this.statusAsText = text;
    }

    public String getStatusAsText(){
        return this.statusAsText;
    }
}

