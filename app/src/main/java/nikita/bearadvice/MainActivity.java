package nikita.bearadvice;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Item.deserialization(this);
        if(Drink.ListItems.isEmpty()){
            try {
                Item.createDB(this); //исправить создание датабазы
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public void onBtnFoodClick(View view) {
        buttonEffect(view);
        Intent intent = new Intent(this, foodGroupsActivity.class);
        intent.putExtra("isDrinks", false);
        startActivity(intent);
    }

    public void onBtnDrinksClick(View view) {
        buttonEffect(view);
        Intent intent = new Intent(this, ShowListItemsActivity.class);
        intent.putExtra("isDrinks", true);
        startActivity(intent);
    }

    public static void buttonEffect(View button){
        button.setOnTouchListener(new View.OnTouchListener() {

            @SuppressLint("ResourceAsColor")
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
//                        v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                        v.getBackground().setColorFilter(R.color.black_overlay, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }
}


