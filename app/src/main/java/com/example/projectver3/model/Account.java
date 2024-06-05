package com.example.projectver3.model;

import java.io.Serializable;

public class Account implements Serializable {
    int idAccount,moneyAccount,totalMoney;
    String nameAccount, iconAccount, colorAccount, eMail;
    public Account() {
    }

    public Account(int idAccount, int moneyAccount, int totalMoney, String nameAccount, String iconAccount, String colorAccount, String eMail) {
        this.idAccount = idAccount;
        this.moneyAccount = moneyAccount;
        this.totalMoney = totalMoney;
        this.nameAccount = nameAccount;
        this.iconAccount = iconAccount;
        this.colorAccount = colorAccount;
        this.eMail = eMail;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public int getMoneyAccount() {
        return moneyAccount;
    }

    public void setMoneyAccount(int moneyAccount) {
        this.moneyAccount = moneyAccount;
    }

    public String getNameAccount() {
        return nameAccount;
    }

    public void setNameAccount(String nameAccount) {
        this.nameAccount = nameAccount;
    }

    public String getIconAccount() {
        return iconAccount;
    }

    public void setIconAccount(String iconAccount) {
        this.iconAccount = iconAccount;
    }

    public String getColorAccount() {
        return colorAccount;
    }

    public void setColorAccount(String colorAccount) {
        this.colorAccount = colorAccount;
    }

}
