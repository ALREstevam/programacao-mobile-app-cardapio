package br.unicamp.ft.a166348_r176575.appcardapio.pojo;

public class ProdStatusHelp{

    public static ProdStatus fromText(String text){

        for (ProdStatus prodStatus : ProdStatus.values()) {

            if(prodStatus.getStatusAsText().equals( text )){
                return prodStatus;
            }
        }
        return ProdStatus.SEM_STATUS;
    }

}
