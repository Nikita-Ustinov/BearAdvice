package nikita.bearadvice;

import android.app.Activity;
import android.content.res.Resources;
import android.media.Image;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;


public class Item {
    String Name;
    String Type;
    String[] ConsumeWith;
    String Definice;
    String[] Stories;
    Image Icon;
    String Picture;
    public String Group;
    int ID;
    static int MaxID;

//    public  Item(String name, String type, String definice) {
//        this.Name = name;
//        this.Type = type;
//        this.Definice = definice;
//        ListItems.add(this);
//    }
//
//
//    public  Item(String name, String type, String definice, String[] consumeWith, String[] stories) {
//        this.Name = name;
//        this.Type = type;
//        this.Definice = definice;
//        this.ConsumeWith = new String[consumeWith.length];
//        this.ConsumeWith = consumeWith;
//        this.Stories = new String[stories.length];
//        this.Stories = stories;
//        ListItems.add(this);
//    }
//
//    public  Item(String name, String type, String definice, String[] consumeWith, String[] stories, String picture) {
//        this.Name = name;
//        this.Type = type;
//        this.Definice = definice;
//        this.ConsumeWith = new String[consumeWith.length];
//        this.ConsumeWith = consumeWith;
//        this.Stories = new String[stories.length];
//        this.Stories = stories;
//        this.Picture = picture;
//        ListItems.add(this);
//    }

    public Item() {
    }


//    public static int getCountList() {
//        return /ListItems.size();
//    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }

    public void setName(String name) {
        this.Name = name;
    }


//


    public static void createDB(Activity activity) throws UnsupportedEncodingException {
        createDrinkDB(activity);
        createFoodDB(activity);

        serializace(activity);
    }

    static void createDrinkDB(Activity activity) throws UnsupportedEncodingException {
        HashMap<String, String> info = getInfo(activity);
        HashMap<String, String[]> consumeWith = getConsumeWith(activity);
        HashMap<String, String[]> stories = getStories(activity);
        HashMap<String, String> pictures = getPictures(activity);

        Set keysSet = info.keySet();
        String pictureName;
        String[] keys = (String[])keysSet.toArray(new String[keysSet.size()]);
        String definice;
        String[] consumeWithList;
        String[] storiesList;
        for (int i=0; i<keys.length; i++) {
            definice = info.get(keys[i]);
            consumeWithList = consumeWith.get(keys[i]);
            storiesList = stories.get(keys[i]);
            pictureName = pictures.get(keys[i]);
            try {
                Drink templ = new Drink(keys[i], "drinks", definice, consumeWithList, storiesList, pictureName);
            }catch (Exception e) {}
        }
    }

    static void createFoodDB(Activity activity) throws UnsupportedEncodingException {
        LinkedList<String> complexFoodList = new LinkedList<String>();

        HashMap<String, String[]> consumeWith = getConsumeWith(activity);
        Set keysSet = consumeWith.keySet();
        String[] keys = (String[])keysSet.toArray(new String[keysSet.size()]);
        String[] consumeWithList;


        for (int i=0; i<keys.length; i++) {
            consumeWithList = consumeWith.get(keys[i]);
            for(int j=0; j<consumeWithList.length; j++) {
                complexFoodList.add(consumeWithList[j]);
            }
        }

        deleteDubleValue(complexFoodList);
        for (int i=0; i<complexFoodList.size(); i++) {
            Food templ = new Food(complexFoodList.get(i));
        }

        setFoodGroups(activity);

    }

    static void deleteDubleValue(LinkedList<String> input) {
        String first, second;
        for(int i=0 ; i<input.size()-1; i++) {
            first = input.get(i);
            for(int j=1; j<input.size(); j++) {
                second = input.get(j);
                if(first.equals(second)) {
                    input.remove(j);
                    j--;
                }
            }
        }
    }

    static HashMap<String, String> getPictures(Activity activity) throws UnsupportedEncodingException {
        String input = null;
        Resources r = activity.getResources();
        InputStream is = r.openRawResource(R.raw.pictures);
        BufferedReader imBR = new BufferedReader(new InputStreamReader(is,"UTF-16LE"));
        try {
            input = convertStreamToString(imBR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String [] stringArray = input.split("\n");
        String [] nameAndPicture = new String[10];
        HashMap<String, String> nameAndPictures = new HashMap<>();
        String name = null;
//        LinkedList<String> food = new LinkedList<>();
        boolean justExit = false;
        String pictureName = null;
        for(int i=0; i<stringArray.length; i++) {

            if(stringArray[i].contains("*")) {
                stringArray[i] = stringArray[i].replace("null","");
                nameAndPicture = stringArray[i].split("\\*");
                name = nameAndPicture[1];
//                food = new LinkedList<>();
                justExit = true;
            }
            if((!justExit)&&(!stringArray[i].contains("null"))){
                pictureName = stringArray[i];
            }
            else {
                justExit = false;
            }

            nameAndPictures.put(name, pictureName);
        }


        return nameAndPictures;
    }

    static HashMap<String, String>  getInfo(Activity activity) throws UnsupportedEncodingException {
        String input = null;
        Resources r = activity.getResources();
        InputStream is = r.openRawResource(R.raw.alcohol_info);
        BufferedReader imBR = new BufferedReader(new InputStreamReader(is,"UTF-16LE"));
        try {
            input = convertStreamToString(imBR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String [] stringArray = input.split("\n");
        String [] nameAndInfo = new String[10];
        HashMap<String, String> info = new HashMap<>();
        String name, defenice;
        for(int i=0; i<stringArray.length-1; i++) {
            if(stringArray[i].contains("*")) {
                stringArray[i] = stringArray[i].replace("null","");
                nameAndInfo = stringArray[i].split("\\*");
                name = nameAndInfo[1];
                defenice = nameAndInfo[2];
                info.put(name, defenice);
            }
        }
        return info;
    }

    static HashMap<String, String[]> getConsumeWith(Activity activity) throws UnsupportedEncodingException {
        String input = null;
        Resources r = activity.getResources();
        InputStream is = r.openRawResource(R.raw.alcohol_food);
        BufferedReader imBR = new BufferedReader(new InputStreamReader(is,"UTF-16LE"));
        try {
            input = convertStreamToString(imBR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String [] stringArray = input.split("\n");
        String [] nameAndFood = new String[10];
        HashMap<String, String[]> consumeWith = new HashMap<>();
        String name = null;
        LinkedList<String> food = new LinkedList<>();;
        boolean justExit = false;
        for(int i=0; i<stringArray.length; i++) {
            if(stringArray[i].contains("*")) {
                stringArray[i] = stringArray[i].replace("null","");
                nameAndFood = stringArray[i].split("\\*");
                name = nameAndFood[1];
                food = new LinkedList<>();
                justExit = true;
            }
            if((!justExit)&&(!stringArray[i].contains("null"))){
                food.add(stringArray[i]);
            }
            else {
                justExit = false;
            }
            String [] foodString = new String[food.size()];
            foodString = toStringArray(food);
            consumeWith.put(name, foodString);
        }


        return consumeWith;
    }

    static HashMap<String, String[]> getStories(Activity activity) throws UnsupportedEncodingException {
        String input = null;
        Resources r = activity.getResources();
        InputStream is = r.openRawResource(R.raw.story);
        BufferedReader imBR = new BufferedReader(new InputStreamReader(is,"UTF-16LE"));
        try {
            input = convertStreamToString(imBR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String [] stringArray = input.split("\n");
        String [] nameAndStories = new String[10];;
        HashMap<String, String[]> namesAndStories = new HashMap<>();
        String name = null;
        LinkedList<String> stories = new LinkedList<>();;
        boolean justExit = false;
        String story = null;
        for(int i=0; i<stringArray.length; i++) {
            if(stringArray[i].contains("*")) {
                stringArray[i] = stringArray[i].replace("null","");
                nameAndStories = stringArray[i].split("\\*");
                name = nameAndStories[1];
                stories = new LinkedList<>();
                justExit = true;
            }
            if((!justExit)&&(!stringArray[i].contains("null"))){
                try {
                    if(!isNumber(stringArray[i].charAt(0))) {
                        String firstPartStory = stories.getLast();
                        String secondPartStory = stringArray[i];
                        String comleateStory = firstPartStory+secondPartStory;
                        stories.set(stories.size()-1, comleateStory);
                    }
                    stories.add(stringArray[i]);
                }catch (Exception e) { }
            }
            else {
                justExit = false;
            }
            stories = joinCloseStories(stories);
            String [] foodString = new String[stories.size()];
            foodString = toStringArray(stories);
            namesAndStories.put(name, foodString);
        }
        return namesAndStories;
    }

    static void setFoodGroups(Activity activity) throws UnsupportedEncodingException {
        String input = null;
        Resources r = activity.getResources();
        InputStream is = r.openRawResource(R.raw.food_groups);
        BufferedReader imBR = new BufferedReader(new InputStreamReader(is,"UTF-16LE"));
        try {
            input = convertStreamToString(imBR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String [] stringArray = input.split("\n");
        String [] nameAndFood = new String[10];
        HashMap<String, String[]> consumeWith = new HashMap<>();
        String groupName = null;
        LinkedList<String> food = new LinkedList<>();;
        boolean justExit = false;
        for(int i=0; i<stringArray.length; i++) {
            if(stringArray[i].contains("*")) {
                stringArray[i] = stringArray[i].replace("null","");
                nameAndFood = stringArray[i].split("\\*");
                groupName = nameAndFood[1];
//                food = new LinkedList<>();
                justExit = true;
            }
            if((!justExit)&&(!stringArray[i].contains("null"))){
//                food.add(stringArray[i]);
                Food.assignGroupName(groupName, stringArray[i]);
            }
            else {
                justExit = false;
            }
//            String [] foodString = new String[food.size()];
//            foodString = toStringArray(food);
//            consumeWith.put(groupName, foodString);
        }


//        return consumeWith;
    }

    static boolean isNumber(char input) {
        boolean output = false;
        output = Character.isDigit(input);
        return output;
    }

    static LinkedList<String> joinCloseStories(LinkedList<String> input) {
        for(int i=0; i<input.size(); i++) {
            if(!isNumber(input.get(i).charAt(0))) {
                String firstPart = input.get(i-1);
                String secondPart = input.get(i);
                if(!firstPart.contains(secondPart))
                    input.set(i - 1, firstPart + secondPart);
                input.remove(i);
            }
        }
        return input;
    }

    static String convertStreamToString(BufferedReader inBR) throws IOException {

        String endFlag;
        String output = null;
        while((endFlag=inBR.readLine()) !=null) {
            output += endFlag+"\n";
            output += inBR.readLine()+"\n";
        }
        return output;
    }

    static String[] toStringArray(LinkedList<String> input) {
        String[] output = new String[input.size()];
        for(int i=0; i<output.length; i++) {
            output[i] = input.get(i);
        }
        return output;
    }

    public static void deserialization(Activity activity) {
        LinkedList<Item> output = new LinkedList<>();
        try {
            String path = activity.getFilesDir()+"/DbFood.out";
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            output = (LinkedList<Item>) in.readObject();
            in.close();
            fileIn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Food.ListItems = output;

        LinkedList<Item> outputDrink = new LinkedList<>();
        try {
            String path = activity.getFilesDir()+"/DbDrinks.out";
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            outputDrink = (LinkedList<Item>) in.readObject();
            in.close();
            fileIn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Drink.ListItems = outputDrink;
    }

    public static void serializace(Activity activity) {
        try {
            String path = activity.getFilesDir()+"/DbDrinks.out";
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(Drink.ListItems);
            out.close();
            fileOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String path = activity.getFilesDir()+"/DbFood.out";
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(Food.ListItems);
            out.close();
            fileOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
