package nikita.bearadvice;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.LinkedList;

import nikita.bearadvice.Logic.Drink;

public class additionActivity extends AppCompatActivity {

    ConstraintLayout laHiddenButtons;
    boolean isHiddenButtonsVisible = false;
    boolean isSearchByDrinksButtonHid = false;
    boolean isSearchByFoodButtonHid = false;
    boolean isNeedToHid = false;
    EditText etSearchByDrinks;
    EditText etSearchByFood;
    Button btnSerachByDrinks;
    Button btnSerachByFood;
    Button btnFindByFood;
    Button btnFindByDrinks;
    Button btnSearchByStrong;
    Button btnSearchByCountry;
    Button btnMene;
    Button btnMezi;
    Button btnVice;
    ConstraintLayout laBackground;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        laHiddenButtons = (ConstraintLayout)findViewById(R.id.laHiddenSearchButtons);
        etSearchByDrinks = (EditText)findViewById(R.id.etSearchByDrinks);
        etSearchByFood = (EditText)findViewById(R.id.etSearchByFood);
        btnSerachByDrinks = (Button) findViewById(R.id.btnSearchByDrinks);
        btnSerachByFood = (Button) findViewById(R.id.btnSearchByFood);
        btnFindByFood = (Button) findViewById(R.id.btnFindByFood);
        btnFindByDrinks = (Button) findViewById(R.id.btnFindByDrinks);
        btnSearchByStrong = (Button) findViewById(R.id.btnKrepost);
        btnSearchByCountry = (Button) findViewById(R.id.btnCountry);
        btnMene = (Button) findViewById(R.id.btn22);
        btnMezi = (Button) findViewById(R.id.btn2355);
        btnVice = (Button) findViewById(R.id.btn56);
        laBackground = (ConstraintLayout) findViewById(R.id.La);
    }


    public void onTakeTimeClick(View view) {
        Intent intent = new Intent(this, chooseDishActivity.class);
        intent.putExtra("isDrinks", false);
        intent.putExtra("name", "По времени приема алкоголя");
        startActivity(intent);
    }

    public void onSearchGlobalClock(View view) {
        tougleForm();
        makeButtonsVisible();
    }

    void tougleForm() {
        if(!isHiddenButtonsVisible) {
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.invizible_back);
            Animation animScale = AnimationUtils.loadAnimation(this, R.anim.scale);
            laHiddenButtons.startAnimation(anim);
            laHiddenButtons.startAnimation(animScale);
            laHiddenButtons.setVisibility(View.VISIBLE);
            isHiddenButtonsVisible = true;
        }
        else {
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.invizible);
            Animation animScaleBack = AnimationUtils.loadAnimation(this, R.anim.scale_back);

            laHiddenButtons.startAnimation(anim);
            laHiddenButtons.startAnimation(animScaleBack);
            laHiddenButtons.setVisibility(View.INVISIBLE);
            isHiddenButtonsVisible = false;
        }
    }

    void makeButtonsVisible() {
        Animation animInvisible= AnimationUtils.loadAnimation(this, R.anim.invizible);
        Animation animVisible = AnimationUtils.loadAnimation(this, R.anim.invizible_back);

        if(isSearchByDrinksButtonHid) {
            btnFindByDrinks.startAnimation(animInvisible);
            btnSerachByDrinks.startAnimation(animVisible);
            etSearchByDrinks.startAnimation(animInvisible);
            btnSerachByDrinks.setVisibility(View.VISIBLE);
            etSearchByDrinks.setVisibility(View.INVISIBLE);
            btnFindByDrinks.setVisibility(View.INVISIBLE);
            isSearchByDrinksButtonHid = false;
        }

        if(isSearchByFoodButtonHid) {
            etSearchByFood.startAnimation(animInvisible);
            btnSerachByFood.startAnimation(animVisible);
            btnFindByFood.startAnimation(animInvisible);
            btnFindByFood.setVisibility(View.INVISIBLE);
            etSearchByFood.setVisibility(View.INVISIBLE);
            btnSerachByFood.setVisibility(View.VISIBLE);
            isSearchByFoodButtonHid = false;
        }
    }

    public void onSearchStrongClick(View view) {
        isNeedToHid = true;
        Animation animInvizible = AnimationUtils.loadAnimation(this, R.anim.invizible_button);
        Animation animScaleSmall = AnimationUtils.loadAnimation(this, R.anim.scale_to_small);
        Animation animMeneScale = AnimationUtils.loadAnimation(this, R.anim.scale_left_btn);
        Animation animBtnVisible = AnimationUtils.loadAnimation(this, R.anim.invizible_back_buttons);

//        btnSearchByStrong.startAnimation(animInvizible);
//        btnSearchByStrong.startAnimation(animScaleSmall);
        btnSearchByStrong.setVisibility(View.INVISIBLE);

        btnMene.startAnimation(animMeneScale);
//        btnMene.startAnimation(animBtnVisible);
        btnMene.setVisibility(View.VISIBLE);

        btnMezi.startAnimation(animMeneScale);
//        btnMezi.startAnimation(animBtnVisible);
        btnMezi.setVisibility(View.VISIBLE);

        btnVice.startAnimation(animMeneScale);
//        btnVice.startAnimation(animBtnVisible);
        btnVice.setVisibility(View.VISIBLE);

//
    }

    public void onFindMeneStrongDrinksClick(View view) {
        LinkedList<String> notStrongDrinks = Drink.STRONG_ABC.get("A");
        showDrinks(notStrongDrinks);
    }

    public void onFindStredniStrongDrinksClick(View view) {
        LinkedList<String> notStrongDrinks = Drink.STRONG_ABC.get("B");
        showDrinks(notStrongDrinks);
    }

    public void onFindDostStrongDrinksClick(View view) {
        LinkedList<String> notStrongDrinks = Drink.STRONG_ABC.get("C");
        showDrinks(notStrongDrinks);
    }

    public void onSearchDrinksClick(View view) {
        isSearchByDrinksButtonHid = true;
        Animation animScaleLeft = AnimationUtils.loadAnimation(this, R.anim.scale_left);
        Animation animVisible = AnimationUtils.loadAnimation(this, R.anim.invizible_back);
        Animation animInvisible= AnimationUtils.loadAnimation(this, R.anim.invizible);
        Animation animScaleRight= AnimationUtils.loadAnimation(this, R.anim.scale_right);

        btnFindByDrinks.startAnimation(animVisible);
        etSearchByDrinks.startAnimation(animScaleLeft);
        btnSerachByDrinks.startAnimation(animScaleRight);
        etSearchByDrinks.startAnimation(animVisible);
        etSearchByDrinks.setVisibility(View.VISIBLE);
        btnSerachByDrinks.setVisibility(View.INVISIBLE);
        btnFindByDrinks.setVisibility(View.VISIBLE);
    }

    public void onSearchFoodClick(View view) {
        isSearchByFoodButtonHid = true;
        Animation animScaleLeft = AnimationUtils.loadAnimation(this, R.anim.scale_left);
        Animation animVisible = AnimationUtils.loadAnimation(this, R.anim.invizible_back);
        Animation animInvisible= AnimationUtils.loadAnimation(this, R.anim.invizible);
        Animation animScaleRight= AnimationUtils.loadAnimation(this, R.anim.scale_right);

        btnFindByFood.startAnimation(animVisible);
        etSearchByFood.startAnimation(animScaleLeft);
        btnSerachByFood.startAnimation(animScaleRight);
        etSearchByFood.startAnimation(animVisible);
        etSearchByFood.setVisibility(View.VISIBLE);
        btnFindByFood.setVisibility(View.VISIBLE);
        btnSerachByFood.setVisibility(View.INVISIBLE);
    }

    public void onFindByDrinksClick(View view) {
        String input = etSearchByDrinks.getText().toString();
        try {
            String compearedString;
            while (input.charAt(input.length() - 1) == ' ') {
                input = input.substring(0, input.length() - 1);
            }
            input = input.toLowerCase();
            int couterFirstTry = 0;
            for (int i = 0; i < Drink.ListItems.size(); i++) {
                compearedString = Drink.ListItems.get(i).Name.toLowerCase();
                if (input.equals(compearedString)) {
                    showDrink(Drink.ListItems.get(i).Name);
                    couterFirstTry++;
                    break;
                }
            }
            LinkedList<String> itemsToShow = new LinkedList<>();
            int counterDrinksToShow = 0;
            if (couterFirstTry == 0) {
                for (int i = 0; i < Drink.ListItems.size(); i++) {
                    compearedString = Drink.ListItems.get(i).Name.toLowerCase();
                    if (compearedString.contains(input)) {
                        itemsToShow.add(Drink.ListItems.get(i).Name);
                        counterDrinksToShow++;
                    }
                }
            }
            if (counterDrinksToShow > 0) {
                showDrinks(itemsToShow);
            }
        } catch(Exception e){}
    }

    public void onFindByFoodClick(View view) {
        String input = etSearchByFood.getText().toString();
        try {
            String compearedString;
            while (input.charAt(input.length() - 1) == ' ') {
                input = input.substring(0, input.length() - 1);
            }
            input = input.toLowerCase();
            int counter = 0;
            LinkedList<String> itemsToShow = new LinkedList<>();
            for (int i = 0; i < Drink.ListItems.size(); i++) {
                for (int j = 0; j < Drink.ListItems.get(i).ConsumeWith.length; j++) {
                    compearedString = Drink.ListItems.get(i).ConsumeWith[j].toLowerCase();
                    if (compearedString != "") {
                        while (compearedString.charAt(compearedString.length() - 1) == ' ') {
                            compearedString = compearedString.substring(0, compearedString.length() - 1);
                        }
                        if (input.equals(compearedString)) {
                            itemsToShow.add(Drink.ListItems.get(i).Name);
                            counter++;
                        }
                    }
                }
            }

            if (counter > 0) {
                showDrinks(itemsToShow);
            }
        } catch(Exception e) {}
    }

    void showDrinks(LinkedList<String> itemsNameToShow) {
        Intent intent = new Intent(this, ShowListItemsActivity.class);
        intent.putExtra("isDrinks", true);
        intent.putExtra("drinksListToshow", itemsNameToShow);
        startActivity(intent);
    }

    void showDrink(String drinkName) {
        int drinkID = Drink.getID(drinkName);
        Intent intent = new Intent(this, ShowItemsInfo.class);
        intent.putExtra("isDrinks", true);
        intent.putExtra("itemNumber", drinkID);
        startActivity(intent);
    }

    public void onBackgroundClick(View view) {
        if(isNeedToHid) {
            Animation animInvizible = AnimationUtils.loadAnimation(this, R.anim.invizible_button);
            Animation animScaleSmall = AnimationUtils.loadAnimation(this, R.anim.scale_to_small);
            Animation animMeneScale = AnimationUtils.loadAnimation(this, R.anim.scale_left_btn_back);
            Animation animBtnVisible = AnimationUtils.loadAnimation(this, R.anim.invizible_back_buttons);

            btnSearchByStrong.startAnimation(animBtnVisible);
            //        btnSearchByStrong.startAnimation(animScaleSmall);
            btnSearchByStrong.setVisibility(View.VISIBLE);

            btnMene.startAnimation(animMeneScale);
            //        btnMene.startAnimation(animBtnVisible);
            btnMene.setVisibility(View.INVISIBLE);

            btnMezi.startAnimation(animMeneScale);
            //        btnMezi.startAnimation(animBtnVisible);
            btnMezi.setVisibility(View.INVISIBLE);

            btnVice.startAnimation(animMeneScale);
            //        btnVice.startAnimation(animBtnVisible);
            btnVice.setVisibility(View.INVISIBLE);
            isNeedToHid = false;
        }
    }
}
