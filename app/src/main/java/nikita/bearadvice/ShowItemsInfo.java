package nikita.bearadvice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import nikita.bearadvice.Logic.Drink;
import nikita.bearadvice.Logic.Food;
import nikita.bearadvice.Logic.Item;

public class ShowItemsInfo extends AppCompatActivity {


    boolean isDrinks;
    int itemID;
    String consumeWith;
    Item listItem;
    TextView twName;
    TextView twConsumeWith;
    TextView twDefenice;
    Button btnDefenice;
    LinearLayout laDefenice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_info);

        Intent intent = getIntent();
        isDrinks = intent.getBooleanExtra("isDrinks", true);
        itemID = intent.getIntExtra("itemNumber", -1);

        laDefenice = (LinearLayout) findViewById(R.id.laDefenice);
        twName  = (TextView)findViewById(R.id.twNameItem);
        btnDefenice  = (Button) findViewById(R.id.btnDefenice);
        twConsumeWith = (TextView)findViewById(R.id.textView6);
        twDefenice = (TextView)findViewById(R.id.textView7);

        if(itemID == -1) {
            new Exception("Плохоe значение ID элемента -1, ShowItemsInfo.java");        }

        if(isDrinks) {
//            listItem = Drink.ListItems.get(position);
            listItem = Drink.getItem(itemID);
        }
        else {
//            listItem = Food.ListItems.get(position);
            listItem = Food.getItem(itemID);
        }

        int corector = 0;
        int counter = 0;
        char secondLetter;
        //удаление null из String и добавление нумерации
//        consumeWith = "";
        String currentConsumeWith = null;
        try {
            for (int i = 0; i < listItem.ConsumeWith.length; i++) {
                currentConsumeWith = listItem.ConsumeWith[i];
                while(currentConsumeWith.charAt(currentConsumeWith.length()-1)== ' ') {
                    currentConsumeWith = currentConsumeWith.substring(0, currentConsumeWith.length()-1);
                }
                if (currentConsumeWith.contains("null")) {
                    currentConsumeWith.replace("null", "");
                }
                if (currentConsumeWith.equals("Аперитив") ||
                        currentConsumeWith.equals("Основное блюдо") ||
                        currentConsumeWith.equals("К десерту") ||
                        currentConsumeWith.equals("Дижестив")) {

                    if(i+corector> 0)
                        corector--;
                }
                else  {

                    try {
                        secondLetter = currentConsumeWith.charAt(1);
                    }catch (Exception e){
                        secondLetter =' ';
                    }
                    if(isCapitalLetter(secondLetter)) {
                        consumeWith += "\r\n";
                        consumeWith += "    "+ currentConsumeWith+"  ";
                        consumeWith += "\r\n";
                        counter = 0;
                    }
                     else {
                        if(currentConsumeWith.equals("")) {
                            continue;
                        }
                        consumeWith += (counter + 1 ) + ". " + currentConsumeWith;
                        if (consumeWith.contains("null")) {
                            consumeWith.replace("null", "");
                        }
                        consumeWith += "\r\n";
                        counter++;
                    }
                }

            }
            consumeWith = consumeWith.substring(4);
        } catch (Exception e) {  }

        if(consumeWith == null) {
            consumeWith = "Видимо без закуски";
        }
        twConsumeWith.setText(consumeWith);
        twName.setText(listItem.Name);
    }

    boolean isCapitalLetter(char letter) {
       if(Character.isUpperCase(letter))
           return true;
       else
           return false;
    }


    public void onInfoClick(View view) {
        Intent intent = new Intent(this, StoryActivity.class);
        intent.putExtra("isDrinks", isDrinks);
        intent.putExtra("itemNumber", itemID);
        startActivity(intent);
    }

    public void onDefeniceClick(View view) {
        twDefenice.setText(listItem.Definice);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.invizible_back);
        laDefenice.startAnimation(anim);
        laDefenice.setHovered(false);
        laDefenice.setVisibility(View.VISIBLE);
    }


    public void onDefeniceTextClick(View view) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.invizible);
        laDefenice.startAnimation(anim);
        laDefenice.setVisibility(View.INVISIBLE);
    }


}
