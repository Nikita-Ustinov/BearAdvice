package nikita.bearadvice;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;


import nikita.bearadvice.Logic.Drink;
import nikita.bearadvice.Logic.Item;

public class MainActivity extends AppCompatActivity {

    Button btnDrinks;
    Button btnFood;
    Button btnSetting;
    public static MediaPlayer mPlayer;
    static Boolean WithMusic;
    Boolean getWithMusic;
    private Drawable buttonDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WithMusic = false;
        //музыка
        deserialization(this);
        mPlayer = MediaPlayer.create(this, R.raw.mix1);
        if(WithMusic) {
            mPlayer.start();
        }

        btnSetting = (Button) findViewById(R.id.btnSettings);
        btnDrinks = (Button)findViewById(R.id.btnDrinks);
        btnFood = (Button)findViewById(R.id.btnFood);
//        View.OnTouchListener listener = new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    ((Button) findViewById(v.getId())).setPressed(true);
//                    return true;
//                }
//                return false;
//            }
//        };
//        btnDrinks.setOnTouchListener(listener);
//        btnFood.setOnTouchListener(listener);
//        Item.deserialization(this);
        if(Drink.ListItems.isEmpty()){
            try {
                Item.createDB(this); //исправить создание датабазы
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public void onBtnFoodClick(View view) {
//        buttonEffect(view);
        Intent intent = new Intent(this, foodGroupsActivity.class);
        intent.putExtra("isDrinks", false);
        startActivity(intent);
    }

    public void onBtnDrinksClick(View view) {
//        buttonEffect(view);
        Intent intent = new Intent(this, ShowListItemsActivity.class);
        intent.putExtra("isDrinks", true);
        startActivity(intent);
    }

    public void onBtnaAdditionalClick(View view) {
//        buttonEffect(view);
        Intent intent = new Intent(this, additionActivity.class);
        intent.putExtra("isDrinks", true);
        startActivity(intent);
    }

//    static void buttonEffect(View button){
//        button.setOnTouchListener(new View.OnTouchListener() {
//            @SuppressLint("ResourceAsColor")
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN: {
////                        v.setBackgroundResource(R.drawable.papirus_dark);
//                        v.getBackground().setColorFilter(R.color.black_overlay, PorterDuff.Mode.SRC_ATOP);
//                        v.invalidate();
//                        break;
//                    }
//                    case MotionEvent.ACTION_UP: {
//                        v.getBackground().clearColorFilter();
//                        v.invalidate();
////                        v.setBackgroundResource(R.drawable.papirus);
//                        break;
//                    }
//                }
//                return false;
//            }
//        });
//    }

//    static void buttonEffectSetting(View button){
//        button.setOnTouchListener(new View.OnTouchListener() {
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//            @SuppressLint("ResourceAsColor")
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN: {
////                        v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
////                        v.getBackground().setColorFilter(R.color.black_overlay, PorterDuff.Mode.SRC_ATOP);
////                        v.invalidate();
//                        v.setBackgroundResource(R.drawable.settings);
//                        break;
//                    }
//                    case MotionEvent.ACTION_UP: {
////                        v.getBackground().clearColorFilter();
////                        v.invalidate();
//                        v.setBackgroundResource(R.drawable.settingslight);
//                        break;
//                    }
//                }
//                return false;
//            }
//        });
//    }

    public void onBtnSettingsClick(View view) {
//        buttonEffectSetting(view);
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("isDrinks", true);
        startActivity(intent);
    }

    public static void deserialization(Activity activity) {
//        LinkedList<Item> output = new LinkedList<>();
        try {
            String path = activity.getFilesDir()+"/AppDB.out";
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            WithMusic = (Boolean) in.readObject();
            in.close();
            fileIn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            WithMusic = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            WithMusic = true;
        } catch (IOException e) {
            e.printStackTrace();
            WithMusic = true;
        }

//        Food.ListItems = output;
//
//        LinkedList<Item> outputDrink = new LinkedList<>();
//        try {
//            String path = activity.getFilesDir()+"/DbDrinks.out";
//            FileInputStream fileIn = new FileInputStream(path);
//            ObjectInputStream in = new ObjectInputStream(fileIn);
//            outputDrink = (LinkedList<Item>) in.readObject();
//            in.close();
//            fileIn.close();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Drink.ListItems = outputDrink;
    }

    public static void serializace(Activity activity) {
        try {
            String path = activity.getFilesDir()+"/AppDB.out";
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(MainActivity.WithMusic);
            out.close();
            fileOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            String path = activity.getFilesDir()+"/DbFood.out";
//            FileOutputStream fileOut = new FileOutputStream(path);
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//            out.writeObject(Food.ListItems);
//            out.close();
//            fileOut.close();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}


