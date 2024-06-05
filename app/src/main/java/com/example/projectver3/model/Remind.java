package com.example.projectver3.model;

import java.io.Serializable;

public class Remind implements Serializable {
    String idRemind, nameRemind, spRemind, noteRemind, dateRemind, timeRemind , mailRemind;
    Boolean flag;

    public Remind() {
    }

    public Remind(String idRemind, String nameRemind, String spRemind, String noteRemind, String dateRemind, String timeRemind, String mailRemind, Boolean flag) {
        this.idRemind = idRemind;
        this.nameRemind = nameRemind;
        this.spRemind = spRemind;
        this.noteRemind = noteRemind;
        this.dateRemind = dateRemind;
        this.timeRemind = timeRemind;
        this.mailRemind = mailRemind;
        this.flag = flag;
    }

    public String getMailRemind() {
        return mailRemind;
    }

    public void setMailRemind(String mailRemind) {
        this.mailRemind = mailRemind;
    }

    public String getIdRemind() {
        return idRemind;
    }

    public void setIdRemind(String idRemind) {
        this.idRemind = idRemind;
    }

    public String getNameRemind() {
        return nameRemind;
    }

    public void setNameRemind(String nameRemind) {
        this.nameRemind = nameRemind;
    }

    public String getSpRemind() {
        return spRemind;
    }

    public void setSpRemind(String spRemind) {
        this.spRemind = spRemind;
    }

    public String getNoteRemind() {
        return noteRemind;
    }

    public void setNoteRemind(String noteRemind) {
        this.noteRemind = noteRemind;
    }

    public String getDateRemind() {
        return dateRemind;
    }

    public void setDateRemind(String dateRemind) {
        this.dateRemind = dateRemind;
    }

    public String getTimeRemind() {
        return timeRemind;
    }

    public void setTimeRemind(String timeRemind) {
        this.timeRemind = timeRemind;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
