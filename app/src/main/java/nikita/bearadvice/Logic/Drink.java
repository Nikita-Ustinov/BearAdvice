package nikita.bearadvice.Logic;

import android.widget.ListView;

import java.util.HashMap;
import java.util.LinkedList;

public class Drink extends Item {

    public static LinkedList<Item> ListItems = new LinkedList<>();
    public static HashMap<String, LinkedList<String>> STRONG_ABC = new HashMap<>(); //A <22, B 22-55, C -55<


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
        while(foodName.charAt(foodName.length()-1)== ' ') {
            foodName = foodName.substring(0, foodName.length()-1);
        }
        foodName = foodName.toLowerCase();
        for(int i=0; i<ListItems.size(); i++) {
            if(ListItems.get(i).GroupsFoodComp == null) {
                String a = "";
                if(ListItems.get(i).Name.equals("Вино")) {
                    a = a + "";
                }
                ListItems.get(i).GroupsFoodComp = new LinkedList<>();
            }
//            for(int j=0; j<ListItems.get(i).GroupsFoodComp.size(); j++) {
                if(ListItems.get(i).GroupsFoodComp.contains(foodName)) {
                    output.add(ListItems.get(i));
//                }
            }
        }
        return output;
    }

//    static void sortList() {
//        Item templ;
//        for(int i=0; i< ListItems.size()-1; i++) {
//            for(int j=i+1; j< ListItems.size(); j++) {
//                if(ListItems.get(i).Name.charAt(0) > ListItems.get(j).Name.charAt(0) ) {
//                    templ = ListItems.get(j);
//                    ListItems.set(j,ListItems.get(i));
//                    ListItems.set(i, templ);
//                }
//            }
//        }
//    }
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
        }catch (Exception e) {}
    }

    static void changeItems(LinkedList<Item> list, int first, int second) {
            Item templ;
            templ = list.get(second);
            list.set(second, list.get(first));
            list.set(first, templ);
        }

    public static void setFoodGroups(LinkedList<String> allFoodGroups, HashMap<String, LinkedList<String>> auxiliaryList) {
        String consumeWith;
        Item templ;
        String foodName;
        for(int i=0; i<allFoodGroups.size(); i++) {
             foodName = allFoodGroups.get(i);
             //Обрезка пробела вконце строки
             while(foodName.charAt(foodName.length()-1)== ' ') {
                foodName = foodName.substring(0, foodName.length()-1);
             }
             foodName = foodName.toLowerCase();
             if(foodName.contains("сыр")) {
                 foodName = foodName;
             }
             if(auxiliaryList.containsKey(foodName)){
                 checkSinonimus(foodName, auxiliaryList);
                 continue;
             }
             for(int j=0; j<ListItems.size(); j++) {
                 templ = ListItems.get(j);
                 for(int k=0; k<templ.ConsumeWith.length; k++) {
                     consumeWith = templ.ConsumeWith[k];
                     consumeWith = consumeWith.toLowerCase();
                     if(consumeWith.contains(foodName)){
                         if(templ.GroupsFoodComp==null) {
                            templ.GroupsFoodComp = new LinkedList<>();
                         }
                         templ.GroupsFoodComp.addLast(foodName);

                     }
                 }
             }
        }
    }

    static void checkSinonimus(String foodName,  HashMap<String, LinkedList<String>> auxiliaryList) {
        LinkedList<String> sinonimusList = auxiliaryList.get(foodName);
        String consumeWith;
        Item templ;
        String nameToCompare;
        for(int i=0; i<sinonimusList.size(); i++) {
            nameToCompare = sinonimusList.get(i);
            while(nameToCompare.charAt(nameToCompare.length()-1)== ' ') {
                nameToCompare = nameToCompare.substring(0, nameToCompare.length()-1);
            }
            nameToCompare = nameToCompare.toLowerCase();
            for(int j=0; j<ListItems.size(); j++) {
                templ = ListItems.get(j);
                for(int k=0; k<templ.ConsumeWith.length; k++) {
                    consumeWith = templ.ConsumeWith[k];
                    consumeWith = consumeWith.toLowerCase();
                    if(consumeWith.contains(nameToCompare)){
                        if(templ.GroupsFoodComp==null) {
                            templ.GroupsFoodComp = new LinkedList<>();
                        }
                        templ.GroupsFoodComp.addLast(foodName);
                    }
                }
            }
        }
    }
}
