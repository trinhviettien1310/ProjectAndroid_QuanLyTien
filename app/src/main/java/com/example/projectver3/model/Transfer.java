package com.example.projectver3.model;

import java.io.Serializable;

public class Transfer implements Serializable {
    String nameFromAccount,nameToAccount,dateTransfer,noteTransfer;
    int idTransfer, moneyTransfer;

    public Transfer() {
    }

    public Transfer(int idTransfer, String nameFromAccount, String nameToAccount, String dateTransfer, String noteTransfer, int moneyTransfer) {
        this.idTransfer = idTransfer;
        this.nameFromAccount = nameFromAccount;
        this.nameToAccount = nameToAccount;
        this.dateTransfer = dateTransfer;
        this.noteTransfer = noteTransfer;
        this.moneyTransfer = moneyTransfer;
    }

    public int getIdTransfer() {
        return idTransfer;
    }

    public void setIdTransfer(int idTransfer) {
        this.idTransfer = idTransfer;
    }

    public String getNameFromAccount() {
        return nameFromAccount;
    }

    public void setNameFromAccount(String nameFromAccount) {
        this.nameFromAccount = nameFromAccount;
    }

    public String getNameToAccount() {
        return nameToAccount;
    }

    public void setNameToAccount(String nameToAccount) {
        this.nameToAccount = nameToAccount;
    }

    public String getDateTransfer() {
        return dateTransfer;
    }

    public void setDateTransfer(String dateTransfer) {
        this.dateTransfer = dateTransfer;
    }

    public String getNoteTransfer() {
        return noteTransfer;
    }

    public void setNoteTransfer(String noteTransfer) {
        this.noteTransfer = noteTransfer;
    }

    public int getMoneyTransfer() {
        return moneyTransfer;
    }

    public void setMoneyTransfer(int moneyTransfer) {
        this.moneyTransfer = moneyTransfer;
    }
}
