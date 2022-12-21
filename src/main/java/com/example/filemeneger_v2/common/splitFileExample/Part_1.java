package com.example.filemeneger_v2.common.splitFileExample;

public class Part_1 {
    public String name;
    private Part_2 part_2;

    public Part_1() {
        this.part_2 = new Part_2(this);
    }

    public static void main(String[] args) {
        Part_1 part_1 = new Part_1();
        System.out.println(part_1.getNamePart_2());
    }

    public String getNamePart_2() {
        return part_2.getName();
    }
}
