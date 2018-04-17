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
import java.util.HashMap;
import java.util.LinkedList;


public class Item {
    String Name;
    Image Icon;
    String Type;
    String Definice;
    String[] Stories;
    String[] ConsumeWith;
    static LinkedList<Item> Drinks = new LinkedList<>();
    static LinkedList<Item> Food = new LinkedList<>();

    public  Item(String name, String type, String definice) {
        this.Name = name;
        this.Type = type;
        this.Definice = definice;
        if (type == "drinks") {
            Drinks.add(this);
        }
        else {
            Food.add(this);
        }
    }

    public  Item(String name, String type, Image img) {
        this.Name = name;
        this.Type = type;
        this.Icon = img;
        if (type == "drinks") {
            Drinks.add(this);
        }
        else {
            Food.add(this);
        }
    }

    public  Item(String name, String type, String definice, String[] consumeWith, String[] stories) {
        this.Name = name;
        this.Type = type;
        this.Definice = definice;
        this.ConsumeWith = new String[consumeWith.length];
        this.ConsumeWith = consumeWith;
        this.Stories = new String[stories.length];
        this.Stories = stories;
        Drinks.add(this);
    }

    public Image getIcon() {
        return Icon;
    }

    public static int getCountDrinks() {
        return Drinks.size();
    }

    public static int getCountFoods() {
        return Food.size();
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }

    public void setName(String name) {
        this.Name = name;
    }


    public static String[] getDrinksNames() {
        String[] output = new String[Drinks.size()];
        for(int i=0; i<Drinks.size(); i++) {
            output[i] = Drinks.get(i).Name;
        }
        return output;
    }

    public static String[] getFoodNames() {
        String[] output = new String[Food.size()];
        for(int i = 0; i< Food.size(); i++) {
            output[i] = Food.get(i).Name;
        }
        return output;
    }

    public static void createDB(Activity activity) throws UnsupportedEncodingException {
        HashMap<String, String> info = getInfo(activity);
        HashMap<String, String[]> consumeWith = getConsumeWith(activity);
        HashMap<String, String[]> stories = getStories(activity);

        String[] keys = (String[]) info.keySet().toArray();
        String definice;
        String[] consumeWithList;
        String[] storiesList;
        for (int i=0; i<keys.length; i++) {
            definice = info.get(keys[i]);
            consumeWithList = consumeWith.get(keys[i]);
            storiesList = stories.get(keys[i]);
            Item templ = new Item(keys[i], "drinks", definice, consumeWithList, storiesList );
        }

        serializace(activity);
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
        for(int i=0; i<stringArray.length; i++) {
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
            if((!justExit)&&(stringArray[i]!=null)){
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
        HashMap<String, String[]> stories = new HashMap<>();
        String name = null;
        LinkedList<String> food = new LinkedList<>();;
        boolean justExit = false;
        for(int i=0; i<stringArray.length; i++) {
            if(stringArray[i].contains("*")) {
                stringArray[i] = stringArray[i].replace("null","");
                nameAndStories = stringArray[i].split("\\*");
                name = nameAndStories[1];
                food = new LinkedList<>();
                justExit = true;
            }
            if((!justExit)&&(stringArray[i]!=null)){
                food.add(stringArray[i]);
            }
            else {
                justExit = false;
            }
            String [] foodString = new String[food.size()];
            foodString = toStringArray(food);
            stories.put(name, foodString);
        }
        return stories;
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
        Food = output;
        try {
            String path = activity.getFilesDir()+"/DbDrinks.out";
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
        Drinks = output;
    }

    public static void serializace(Activity activity) {
        try {
            String path = activity.getFilesDir()+"/DbDrinks.out";
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(Item.Drinks);
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
            out.writeObject(Item.Food);
            out.close();
            fileOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
