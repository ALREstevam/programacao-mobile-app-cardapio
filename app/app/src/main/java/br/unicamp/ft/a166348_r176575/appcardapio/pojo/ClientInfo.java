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
    private String firebaseToken;

    public ClientInfo(int tableId, String clientName, char clientSex, byte peopleOnTable, String firebaseToken) {
        this.tableId = tableId;
        ClientName = clientName;
        ClientSex = clientSex;
        PeopleOnTable = peopleOnTable;
        this.firebaseToken = firebaseToken;
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

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }
}
