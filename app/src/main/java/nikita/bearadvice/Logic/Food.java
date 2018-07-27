package nikita.bearadvice.Logic;

import java.util.HashMap;
import java.util.LinkedList;

public class Food extends Item {



    public static LinkedList<Item> ListItems = new LinkedList<>();
    public static LinkedList<String> GroupList = new LinkedList<>();
    public static HashMap<String, String[]> GroupsFoodUser;


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

    public static LinkedList<String> getFoodByGroupName(String groupName) {
        String[] outputArray = GroupsFoodUser.get(groupName);
        LinkedList<String> outputList = StringArrayToLinkedList(outputArray);
        return outputList;
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

    static void sortList() {
        Item templ;
        int ii = 0, jj = 0;
        try {
            for (int i = 0; i < ListItems.size() - 1; i++) {
                ii=i;
                for (int j = i + 1; j < ListItems.size(); j++) {
                    jj=j;
                    if (ListItems.get(i).Name.charAt(0) > ListItems.get(j).Name.charAt(0)) {
                        changeItems(ListItems, i, j);
                    }
                    else if ((ListItems.get(i).Name.charAt(1) > ListItems.get(j).Name.charAt(1))&&(ListItems.get(i).Name.charAt(0) > ListItems.get(j).Name.charAt(0))) {
                        changeItems(ListItems, i, j);
                    }
//                    else if (ListItems.get(i).Name.charAt(2) > ListItems.get(j).Name.charAt(2)) {
//                        changeItems(ListItems, i, j);
//                    }
                }
            }
        }catch (Exception e) {
            String nu = "i="+ ii + "  j="+jj
                    ;
        }
    }

    static void changeItems(LinkedList<Item> list, int first, int second) {
        Item templ;
        templ = list.get(second);
        list.set(second, list.get(first));
        list.set(first, templ);
    }

    static void clear() {
        int coreector =0;
        Item templ;
        for(int i=0; i<ListItems.size() + coreector; i++) {
            templ = ListItems.get(i);
            if(isEmpty(templ)) {

            }
        }
    }

    static boolean isEmpty(Item item) {
        boolean answer = false;

        if(item.Name.equals("")&&(item.ConsumeWith == null)&&(item.Picture==null)&&(item.Stories == null))
            answer = true;

       return answer;
    }

    public static LinkedList<String> StringArrayToLinkedList(String[] input) {
        LinkedList<String> output = new LinkedList<>();
        for(int i=0; i<input.length; i++) {
            output.addLast(input[i]);
        }
        return output;
    }
}

