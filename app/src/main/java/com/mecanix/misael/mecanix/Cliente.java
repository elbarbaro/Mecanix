package com.mecanix.misael.mecanix;

/**
 * Created by MISAEL on 15/11/2015.
 */
public class Cliente {

    private String name;
    private String number;

    public Cliente(String name, String number){

        this.name = name;
        this.number = number;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setNumber(String number){
        this.number = number;
    }
    public String getName(){
        return name;
    }
    public String getNumber(){
        return number;
    }
    @Override
    public String toString(){
        return name + "," + number;
    }
}
