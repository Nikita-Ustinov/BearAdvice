package nikita.bearadvice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Random;

import nikita.bearadvice.Logic.Drink;
import nikita.bearadvice.Logic.Food;
import nikita.bearadvice.Logic.Item;

public class StoryActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    TextView twFirstStory;
    Item listItem;
    ImageView iwPicture;
    TextView twStoryNumber;
    TextView twSecondStory;
    int CurrentStoryNumber;
    int[] StoryNumber;
    int PointerOnStory;
//    int[] CountShowStory;
    boolean isFirstTWEmpty;
    LinearLayout laHover;
    ScrollView scrollView;

    //для детектированиея жестов
    private GestureDetectorCompat gestureDetector;
    private static final int SWIPE_DISTANCE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        twFirstStory = (TextView)findViewById(R.id.textView6);
        iwPicture = (ImageView) findViewById(R.id.imageView2);
        twStoryNumber = (TextView)findViewById(R.id.textView5);
        twSecondStory = (TextView)findViewById(R.id.twSecondStory);
        laHover = (LinearLayout)findViewById(R.id.laSwipeStory);
        scrollView = (ScrollView)findViewById(R.id.scrollView2);

        //выпис рандомного номера истории
        String storyRundomNumber = twStoryNumber.getText().toString();
        Random rand = new Random();
        CurrentStoryNumber = (rand.nextInt(250) + 400);
        storyRundomNumber += CurrentStoryNumber;
        twStoryNumber.setText(storyRundomNumber);


        Intent intent = getIntent();
        boolean isDrinks = intent.getBooleanExtra("isDrinks", true);
        int itemID = intent.getIntExtra("itemNumber", -1);

        if (itemID == -1) {
            new Exception("Плохое значение позиции элемента -1, StoryActivity.java");
        }
        if(isDrinks) {
            listItem = Drink.getItem(itemID);
        }
        else {
            listItem = Food.getItem(itemID);
        }
        StoryNumber = new int[listItem.Stories.length];
        generateRundomNubersForStoryQueue();
        String story;
        String way;
//        String packageName = getPackageName();
        try {
//            int storyNumber = rand.nextInt(listItem.Stories.length);
            story = listItem.Stories[StoryNumber[0]];
            PointerOnStory = 0;
            isFirstTWEmpty = false;
            story = story.substring(2);
            if(story.charAt(0) == '.') {
                story = story.substring(1);
            }
            way = "R.drawable." + listItem.Picture;
        }catch (Exception e) {
            story = "Пить вредно но...   ... это пока никого не останавливало";
            way = "R.drawable." + "chehov_vodka" ;
        }
        int resID = -1;
        try {
            resID = R.drawable.class.getField(listItem.Picture).getInt(getResources());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),  resID);
        if(icon == null) {
            icon = BitmapFactory.decodeResource(this.getResources(),  R.drawable.chehov_vodka);
        }

        iwPicture.setImageBitmap(icon);
        twFirstStory.setText(story);

        gestureDetector = new GestureDetectorCompat(laHover.getContext(), this);

//        scrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                float downX = 0;
//                float downY = 0;
//                float upX;
//                float upY;
//                int eventTime = (int) event.getEventTime();
//                int lastEventTime = 0;
//                switch(event.getAction()){
//                    case MotionEvent.ACTION_DOWN:{
//                        downX = event.getX();
//                        downY = event.getY();
//                        eventTime = (int) event.getEventTime();
//                        lastEventTime = eventTime;
//                    }
//                    case MotionEvent.ACTION_UP:{
//                        upX = event.getX();
//                        upY = event.getX();
//
//                        float deltaX = downX - upX;
//                        float deltaY = downY - upY;
//
//                        if(eventTime - lastEventTime > 100) {
//
//                            if(Math.abs(deltaY)>deltaX && Math.abs(deltaY) > SWIPE_DISTANCE_THRESHOLD){
//                                if(downY<upY){
//                                    onSwipeLeft();
//                                    return true;
//                                }else{
//                                    onSwipeRight();
//                                    return  true;
//                                }
//                            }
//                        }
//                    }
//                }
//
//                return false;
//            }
//        });
    }


    protected final static int getResourceID (final String resName, final String resType, final Context ctx)  {
        final int ResourceID =
                ctx.getResources().getIdentifier(resName, resType,
                        ctx.getApplicationInfo().packageName);
        if (ResourceID == 0)
        {
            throw new IllegalArgumentException
                    (
                            "No resource string found with name " + resName
                    );
        }
        else
        {
            return ResourceID;
        }
    }

    void generateRundomNubersForStoryQueue() {
        Random rand = new Random();
        int storyNumber = rand.nextInt(listItem.Stories.length);
//        StoryNumber[0] = storyNumber;
        for(int i=0; i<StoryNumber.length; i++) {
            StoryNumber[i] = i;
//            do {
//                storyNumber = rand.nextInt(listItem.Stories.length);
//            } while (isContain(StoryNumber, storyNumber));
        }
        StoryNumber = shuffleList(StoryNumber);
    }

    int [] shuffleList(int[]  list) {
        int elementNumber;
        int templ;
        for(int i=0; i<list.length-1; i++) {
            elementNumber = (int)(Math.random()*(list.length-i-1));
            templ = list[list.length-i-1];
            list[list.length-i-1] = list[elementNumber];
            list[elementNumber] =  templ;
        }
        return list;
    }

    boolean isContain(int[] StoryNumber, int storyNumber) {
        for(int i=0; i<StoryNumber.length; i++) {
            if(StoryNumber[i] == storyNumber)
                return true;
        }
        return false;
    }


    // работа с жестами
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return super.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float distanceX = e2.getX() - e1.getX();
        float distanceY = e2.getY() - e1.getY();
        if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
            if (distanceX > 0)
                onSwipeRight();
            else
                onSwipeLeft();
            return true;
        }
        return false;
    }

    void onSwipeLeft() {
        nextStory();
    }

    void onSwipeRight() {
        previosStory();
    }

    void nextStory() {
        String randomNumberStoryLabel = twStoryNumber.getText().toString();
        randomNumberStoryLabel = randomNumberStoryLabel.substring(0, randomNumberStoryLabel.length()-3);
        Animation forSecondStory;
        Animation forFirstStory;

        if(!isFirstTWEmpty) {
            if(PointerOnStory+1 == StoryNumber.length){
                PointerOnStory = 0;
                CurrentStoryNumber -= StoryNumber.length-1;
            }
            else {
                PointerOnStory++;
                CurrentStoryNumber++;
            }
            String text = listItem.Stories[StoryNumber[PointerOnStory]];
            try {
                while (isNumber(text.charAt(0)) || (text.charAt(0) == '.')) {
                    text = text.substring(1);
                }
            } catch (Exception e) {
                String a = "";
            }
            twFirstStory.setText(text);
            randomNumberStoryLabel += CurrentStoryNumber;
            twStoryNumber.setText(randomNumberStoryLabel);
//            forSecondStory = AnimationUtils.loadAnimation(this, R.anim.invizible);
//            forFirstStory = AnimationUtils.loadAnimation(this, R.anim.invizible);

        }
//        twFirstStory.startAnimation(forFirstStory);
//        twSecondStory.setAnimation(forSecondStory);
    }

    void previosStory() {
        String randomNumberStoryLabel = twStoryNumber.getText().toString();
        randomNumberStoryLabel = randomNumberStoryLabel.substring(0, randomNumberStoryLabel.length()-3);

        if(PointerOnStory < 1){
            PointerOnStory = StoryNumber.length-1;
            CurrentStoryNumber += StoryNumber.length-1;
        }
        else {
            PointerOnStory--;
            CurrentStoryNumber--;
        }
        String text = listItem.Stories[StoryNumber[PointerOnStory]];
        while(isNumber(text.charAt(0))||(text.charAt(0) == '.')) {
            text = text.substring(1);
        }

        randomNumberStoryLabel += CurrentStoryNumber;
        twFirstStory.setText(text);
        twStoryNumber.setText(randomNumberStoryLabel);
//        forSecondStory = AnimationUtils.loadAnimation(this, R.anim.invizible);
//        forFirstStory = AnimationUtils.loadAnimation(this, R.anim.invizible);
    }

    static boolean isNumber(char input) {
        boolean output = false;
        output = Character.isDigit(input);
        return output;
    }

}
