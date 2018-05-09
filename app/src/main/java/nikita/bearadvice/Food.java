package nikita.bearadvice;

import java.util.LinkedList;

public class Food extends Item {

    String group;

    public String Group;
    public static LinkedList<Item> ListItems = new LinkedList<>();



    public Food(String name, String group) {
        this.Name = name;
        this.Group = group;
        ID = Item.MaxID++;
    }

    public Food(String name) {
        Name = name;
        ListItems.add(this);
        ID = Item.MaxID++;
    }

    public static Item getItem(int id) {
        for(int i=0; i<ListItems.size(); i++) {
            if(ListItems.get(i).ID == id)
                return ListItems.get(i);
        }
        return null;
    }

    public static String getFoodName(int position) {
        Item output = ListItems.get(position);
        String out = output.getName();
        return out;
    }


    public static String[] getFoodNames() {
        String[] output = new String[ListItems.size()];
        for(int i = 0; i< ListItems.size(); i++) {
            output[i] = ListItems.get(i).Name;
        }
        return output;
    }
}

