package com.example.CookBook.ListRecipe;

public class ListItem {
    //this is used to declare some properties which will  be shown in each card item in the recycler view.
    private String head;
    private String desc;
    private String id;
    private String difficulty;

    public ListItem(String id, String head, String desc, String difficulty)
    {
        this.id =id;
        this.head= head;
        this.desc = desc;
        this.difficulty = difficulty;
    }


    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }

    public String getId() {
        return id;
    }

    public String getDifficulty(){
        return difficulty;
    }
}
