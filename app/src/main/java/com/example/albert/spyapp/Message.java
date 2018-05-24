package com.example.albert.spyapp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class Message implements Serializable {
    private StringBuilder messageBuilder;

    private User sender;

    private Date dateOfSending;
    private LinkedList<String> listOfAll=null;
    private HashMap<String,String> insertValues;
    private HashMap<String,String> selectValues;
    private ResultSet set;

    final private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public LinkedList<String> getListOfAll() {
        return listOfAll;
    }
    Message(User sender,String text, String header){

        messageBuilder=new StringBuilder();
        messageBuilder.append(text);
        this.sender=sender;
        dateOfSending=new Date();
        this.header = header;
        this.listOfAll =new LinkedList<>();

        this.insertValues=new HashMap<>();
        this.selectValues=new HashMap<>();

    }

    public void add(String toAdd){
        this.listOfAll.add(toAdd);
    }
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    private String header = "";


    public User getSender() {
        return sender;
    }

    public Date getDateOfSending() {
        return dateOfSending;
    }

    public void setDateOfSending(Date dateOfSending) {
        this.dateOfSending = dateOfSending;
    }
    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void appendText(String text){
        messageBuilder.append(text);
    }

    public void appendDateOfSending(){
        messageBuilder.append(dateFormat.format(dateOfSending));
    }

    @Override
    public String toString() {
        return messageBuilder.toString();
    }

    public HashMap<String, String> getInsertValues() {
        return insertValues;
    }

    public HashMap<String, String> getSelectValues() {
        return selectValues;
    }

    public ResultSet getSet() {
        return set;
    }

    public void setSet(ResultSet set) {
        this.set = set;
    }

}
