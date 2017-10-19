package com.example.kendric.contactlist;


import java.io.Serializable;
import java.util.ArrayList;

public class Contact implements Serializable {
    private String name;
    private String number;
    private Boolean selected;
    private ArrayList<Contact> relationshipList;

    public String getName(){
        return this.name;
    }
    public String getNumber(){
        return this.number;
    }

    public Boolean isSelected() {
        return selected;
    }
    public ArrayList<Contact> getRelationshipList() {
        return this.relationshipList;
    }

//    private void addRelationship(Contact contact) {
//        this.relationshipList.add(contact);
//    }

    private void setRelationshipList(ArrayList<Contact> relationshipList) {
        this.relationshipList = relationshipList;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setNumber(String number) {
        this.number = number;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    Contact(String name, String number, ArrayList<Contact> relationshipList) {
        this.setName(name);
        this.setNumber(number);
        this.setRelationshipList(relationshipList);
        this.setSelected(false);
    }
}
