package nikita.bearadvice;

import android.media.Image;

import java.util.LinkedList;
import java.util.List;

public class Drink extends Item {



    static LinkedList<Item> ListItems = new LinkedList<>();



    public static int getID(String name) {
        for(int i=0; i<ListItems.size(); i++) {
            if(name == ListItems.get(i).Name) {
                return ListItems.get(i).ID;
            }
        }
        return  -1;
    }

    public static Item getItem(int id) {
        for(int i=0; i<ListItems.size(); i++) {
            if(ListItems.get(i).ID == id)
                return ListItems.get(i);
        }
        return null;
    }

    public Drink(String name, String type, String definice) {

        this.Name = name;
        this.Type = type;
        this.Definice = definice;
        ID = Item.MaxID++;
        ListItems.add(this);
    }

    public Drink(String name, String type, String definice, String[] consumeWith, String[] stories) {
        this.Name = name;
        this.Type = type;
        this.Definice = definice;
        this.ConsumeWith = new String[consumeWith.length];
        this.ConsumeWith = consumeWith;
        this.Stories = new String[stories.length];
        this.Stories = stories;
        ID = Item.MaxID++;
        ListItems.add(this);
    }

    public Drink(String name, String type, String definice, String[] consumeWith, String[] stories, String picture) {
        this.Name = name;
        this.Type = type;
        this.Definice = definice;
        this.ConsumeWith = new String[consumeWith.length];
        this.ConsumeWith = consumeWith;
        this.Stories = new String[stories.length];
        this.Stories = stories;
        this.Picture = picture;
        ID = Item.MaxID++;
        ListItems.add(this);
    }

    public static String[] getDrinksNames() {
        String[] output = new String[ListItems.size()];
        for(int i = 0; i< ListItems.size(); i++) {
            output[i] = ListItems.get(i).Name;
        }
        return output;
    }

    public static LinkedList<Item> getListDrinksToFood(String foodName) {
        LinkedList<Item> output = new LinkedList<>();
        for(int i=0; i<ListItems.size(); i++) {
            for(int j=0; j<ListItems.get(i).ConsumeWith.length; j++) {
                if(ListItems.get(i).ConsumeWith[j].equals(foodName)) {
                    output.add(ListItems.get(i));
                }
            }
        }
        return output;
    }

}
