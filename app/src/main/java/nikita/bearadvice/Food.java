package nikita.bearadvice;

import java.util.LinkedList;

public class Food extends Item {



    public static LinkedList<Item> ListItems = new LinkedList<>();
    public static LinkedList<String> GroupList = new LinkedList<>();


    public Food(String name, String group) {
        this.Name = name;
        this.Group = group;
        ID = Item.MaxID++;
    }

    public Food(String name) {
        Name = name;
        Group = "";
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

    public static void assignGroupName(String groupName, String foodName) {
        if(!GroupList.contains(groupName)) {
            GroupList.add(groupName);
        }
        for(int i=0; i< ListItems.size(); i++) {
            if(ListItems.get(i).Name.equals(foodName)) {
                ListItems.get(i).Group = groupName;
                break;
            }
        }
    }

    public static LinkedList<Item> getFoodByGroupPosition(int position) {
        String groupName = GroupList.get(position);
        LinkedList<Item> output = new LinkedList<>();
        for(int i=0; i<ListItems.size(); i++) {
            if (ListItems.get(i).Group.equals(groupName)) {
                output.add(ListItems.get(i));
            }
        }
        return output;
    }
}

