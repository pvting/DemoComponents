package com.pvting.cppdemo;

public class Stu {
    private int id;
    private String name;
    Stu(int id,String name){
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Stu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    Stu(int id){
        this.id = id;
    }
    Stu(int id ,int id2){
        this.id=id+id2;
    }
    Stu(){

    }
}
