package com.example.campus;

public class Stories {
    String name,company,pack,story,yop;
    int id;

    public Stories(String name, String company, String pack, String story, String yop, int id) {
        this.name = name;
        this.company = company;
        this.pack = pack;
        this.story = story;
        this.yop = yop;
        this.id = id;
    }
    public int getId() { return id; }

    public String getName() { return name; }

    public String getCompany() { return company; }

    public String getPack() { return pack; }

    public String getStory() { return story; }

    public String getYop() { return yop; }

}
