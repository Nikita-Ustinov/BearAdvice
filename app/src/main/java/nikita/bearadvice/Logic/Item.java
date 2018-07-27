package nikita.bearadvice.Logic;

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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import nikita.bearadvice.R;


public class Item {
    public String Name;
    String Type;
    public String[] ConsumeWith;
    public String Definice;
    public String[] Stories;
    Image Icon;
    public String Picture;
    public String Group;
    int ID;
    static int MaxID;
    static LinkedList<String> NullStories;
    public LinkedList<String> GroupsFoodComp;

    public Item() {}

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }

    public void setName(String name) {
        this.Name = name;
    }


    public static void createDB(Activity activity) throws UnsupportedEncodingException {
        NullStories = new LinkedList<>();
        createDrinkDB(activity);
        createFoodDB(activity);

        Food.clear();

        Drink.sortList();
        Food.sortList();

//        serializace(activity);
    }

    static void createDrinkDB(Activity activity) throws UnsupportedEncodingException {
        HashMap<String, String> info = getInfo(activity);
        HashMap<String, String[]> consumeWith = getConsumeWith(activity);
        HashMap<String, String[]> stories = getStories(activity);
        HashMap<String, String> pictures = getPictures(activity);
        getDrinksStrong(activity);

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
            if(storiesList == null){
                String[] storyDefault = new String[1];
                storyDefault[0] = "Пить вредно но...   ... это пока никого не останавливало";
            }
            if(pictureName == null) {
                pictureName = "chehov_vodka";
            }
            try {
                Drink templ = new Drink(keys[i], "drinks", definice, consumeWithList, storiesList, pictureName);
            }catch (Exception e) {}
        }
    }

    static void createFoodDB(Activity activity) throws UnsupportedEncodingException {
        LinkedList<String> allFoodGroups = createFoodGroups(activity);
        HashMap<String, LinkedList<String>> auxiliaryList = createAuxiliaryList(activity);
        Drink.setFoodGroups(allFoodGroups, auxiliaryList);

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

    static void getDrinksStrong(Activity activity) throws UnsupportedEncodingException {
        String input = null;
        Resources r = activity.getResources();
        InputStream is = r.openRawResource(R.raw.strong);
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
        HashMap<String, LinkedList<String>> drinkAndStrong = new HashMap<>();
        String name = null;
        boolean justExit = false;
        boolean firstEnter = true;
        String templ;
        LinkedList<String> drinsWithSameStrong = null;
        for(int i=0; i<stringArray.length; i++) {

            if(stringArray[i].contains("*")) {
                if(!firstEnter) {
                    drinkAndStrong.put(name, drinsWithSameStrong);
                }
                stringArray[i] = stringArray[i].replace("null","");
                nameAndPicture = stringArray[i].split("\\*");
                name = nameAndPicture[1];
                justExit = true;
                firstEnter = false;
                drinsWithSameStrong = new LinkedList<>();
            }
            if((!justExit)&&(!stringArray[i].contains("null"))){
                templ = stringArray[i];
                while(templ.charAt(templ.length()-1)== ' ') {
                    templ = templ.substring(0, templ.length()-1);
                }
                drinsWithSameStrong.add(templ);
            }
            else {
                justExit = false;
            }
        }

        drinkAndStrong.put(name, drinsWithSameStrong);
        Drink.STRONG_ABC = drinkAndStrong;
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
                if(i == 122) {
                    String a = "";
                }
//                food = new LinkedList<>();
                justExit = true;
            }
            if((!justExit)&&(!stringArray[i].contains("null"))){
                pictureName = stringArray[i];
                if(!pictureName.equals("")) {
                    while(pictureName.charAt(pictureName.length()-1)== ' ') {
                        pictureName = pictureName.substring(0, pictureName.length()-1);
                    }
                    nameAndPictures.put(name, pictureName);
                }
            }
            else {
                justExit = false;
            }
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
                defenice = defenice.substring(1);
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
                        String compleateStory = firstPartStory+secondPartStory;
                        stories.set(stories.size()-1, compleateStory);
                    }
                    stories.add(stringArray[i]);
                }catch (Exception e) { }
            }
            else {
                justExit = false;
            }
            if(stories.size() > 1)
                stories = joinCloseStories(stories);
            String [] foodString = new String[stories.size()];
            foodString = toStringArray(stories);
            namesAndStories.put(name, foodString);
        }
        return namesAndStories;
    }

    static LinkedList<String> createFoodGroups(Activity activity) throws UnsupportedEncodingException {
        LinkedList<String> allFoodGroups = new LinkedList<>();
        String input = null;
        Resources r = activity.getResources();
        InputStream is = r.openRawResource(R.raw.food_user_groups);
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
        HashMap<String, String[]> output = new HashMap<>();
        String [] stringArray = input.split("\n");
        String [] nameAndFood = new String[10];
        String groupName = null;
        boolean justExit = false;
        boolean isFirstEnter = true;

        String[] innerArrayFood = null;  // Спискок для создания второго уровня меню Фрукты -> аппельсины мандарины ...
        LinkedList<String> innerListFood = new LinkedList<>();  // Такой же список только сырой(в формате linkedlist)
        for(int i=0; i<stringArray.length; i++) {
            if(stringArray[i].contains("*")) {
                if(!isFirstEnter) {
                    innerArrayFood = listToStringArray(innerListFood);
                    output.put(groupName, innerArrayFood);
                    innerListFood = new LinkedList<>();
                }
                isFirstEnter = false;
                stringArray[i] = stringArray[i].replace("null","");
                nameAndFood = stringArray[i].split("\\*");
                groupName = nameAndFood[1];
                groupName = firstLeterUpperCase(groupName);
                justExit = true;
            }
            if((!justExit)&&(!stringArray[i].contains("null"))){
                innerListFood.add(firstLeterUpperCase(stringArray[i]));
                allFoodGroups.addLast(stringArray[i]);
            }
            else {
                justExit = false;
            }
        }
        innerArrayFood = listToStringArray(innerListFood);
        output.put(groupName, innerArrayFood);
        Food.GroupsFoodUser = output;
        return allFoodGroups;
    }

    static HashMap<String, LinkedList<String>> createAuxiliaryList(Activity activity) throws UnsupportedEncodingException {
        String input = null;
        Resources r = activity.getResources();
        InputStream is = r.openRawResource(R.raw.food_user_group_special2);
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
        HashMap<String, LinkedList<String>> output = new HashMap<>();
        String [] stringArray = input.split("\n");
        String [] nameAndFood = new String[10];
        String groupName = null;
        boolean justExit = false;
        boolean isFirstEnter = true;
        String smallFood; //для записи в масив синономов еды

        LinkedList<String> synonymsList = null;  // Спискок синонимов
        LinkedList<String> innerListFood = new LinkedList<>();  // Такой же список только сырой(в формате linkedlist)
        for(int i=0; i<stringArray.length; i++) {
            if(stringArray[i].contains("*")) {
                if(!isFirstEnter) {
                    synonymsList = innerListFood;
                    output.put(groupName, synonymsList);
                    innerListFood = new LinkedList<>();
                }
                if(i==130) {
                    int asd = 2;
                }
                isFirstEnter = false;
                stringArray[i] = stringArray[i].replace("null","");
                nameAndFood = stringArray[i].split("\\*");
                groupName = nameAndFood[1];
                groupName = groupName.toLowerCase();
                justExit = true;
            }
            if((!justExit)&&(!stringArray[i].contains("null"))){
                smallFood = stringArray[i];
                smallFood = smallFood.toLowerCase();
                innerListFood.add(stringArray[i]);
            }
            else {
                justExit = false;
            }
        }
        synonymsList = innerListFood;
        output.put(groupName, synonymsList);
        return  output;
    }

    static String[] listToStringArray (LinkedList<String> input) {
        String [] output = new String[input.size()];
        for(int i=0; i<input.size(); i++) {
            output[i] = input.get(i);
        }
        return output;
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

    public static String convertStreamToString(BufferedReader inBR) throws IOException {

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

    static String firstLeterUpperCase(String input) {
        String firstLetter = input.substring(0,1);
        firstLetter = firstLetter.toUpperCase();
        input = input.substring(1);
        String output = firstLetter + input;
        return output;
    }

}
