package com.example.filemeneger_v2.common.splitFileExample;

public class Part_2 {
    private String name;
    private Part_1 part_1;
    public Part_2(Part_1 part_1){
        this.part_1 = part_1;
        this.name = "Иван";
    }

    public String getName(){
        return name;
    }
}
