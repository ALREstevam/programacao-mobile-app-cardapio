package br.unicamp.ft.a166348_r176575.appcardapio.pojo;

/**
 * Created by andre on 06/06/2018.
 */

/*
* {
	"tableId":3,
	"ClientName":"Abraão",
	"ClientSex":"M",
	"PeopleOnTable":15
}
* */

public class ClientInfo {

    private int tableId;
    private String ClientName;
    private char ClientSex;
    private byte PeopleOnTable;

    public ClientInfo(int tableId, String clientName, char clientSex, byte peopleOnTable) {
        this.tableId = tableId;
        ClientName = clientName;
        ClientSex = clientSex;
        PeopleOnTable = peopleOnTable;
    }

    public ClientInfo() {
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public char getClientSex() {
        return ClientSex;
    }

    public void setClientSex(char clientSex) {
        ClientSex = clientSex;
    }

    public byte getPeopleOnTable() {
        return PeopleOnTable;
    }

    public void setPeopleOnTable(byte peopleOnTable) {
        PeopleOnTable = peopleOnTable;
    }
}
