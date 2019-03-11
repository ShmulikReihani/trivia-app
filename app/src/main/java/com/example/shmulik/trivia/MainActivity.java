package com.example.shmulik.trivia;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {

    int score;

    MediaPlayer correctAnswerMP;
    MediaPlayer failAnswerMP;

    ImageView image;
    Button optionOneB;
    Button optionTwoB;
    Button optionThreeB;
    Button optionFourB;

    Animation slide_button_two_out, slide_button_two_in, slide_button_one_out, slide_button_one_in,
            slide_button_three_out, slide_button_three_in, slide_button_four_out, slide_button_four_in,slide_image_out,slide_image_in;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;

    TextView textViewScore;

    ArrayList<Images> images = new ArrayList<Images>();

    int i=0;
    Toolbar toolbar;
    SharedPreferences prefs;
    Gson gson;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correctAnswerMP = MediaPlayer.create(getApplicationContext(),R.raw.correct_answer);
        failAnswerMP = MediaPlayer.create(getApplicationContext(),R.raw.fail_answer);

        mAuth = FirebaseAuth.getInstance();
        prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        getName();


        textViewScore = (TextView) findViewById(R.id.textViewScore);
        image = (ImageView) findViewById(R.id.idImage);
        optionOneB = (Button) findViewById(R.id.optionOneID);
        optionTwoB = (Button) findViewById(R.id.optionTwoID);
        optionThreeB = (Button) findViewById(R.id.optionThreeID);
        optionFourB = (Button) findViewById(R.id.optionFourID);

        slide_button_one_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_button_one_out);
        slide_button_one_out.setAnimationListener(this);
        slide_button_one_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_button_one_in);
        slide_button_one_in.setAnimationListener(this);
        slide_button_two_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_button_two_out);
        slide_button_two_out.setAnimationListener(this);
        slide_button_two_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_button_two_in);
        slide_button_two_in.setAnimationListener(this);
        slide_button_three_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_button_three_out);
        slide_button_three_out.setAnimationListener(this);
        slide_button_three_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_button_three_in);
        slide_button_three_in.setAnimationListener(this);
        slide_button_four_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_button_four_out);
        slide_button_four_out.setAnimationListener(this);
        slide_button_four_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_button_four_in);
        slide_button_four_in.setAnimationListener(this);
        slide_image_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_image_out);
        slide_image_out.setAnimationListener(this);
        slide_image_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_image_in);
        slide_image_in.setAnimationListener(this);

        slide_button_one_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                optionOneB.setEnabled(true);
                optionOneB.setBackgroundColor(Color.parseColor("#f2fe71"));
                optionOneB.setTextColor(Color.BLACK);
                optionOneB.startAnimation(slide_button_one_in);
                fillImagesAndAnswers(i);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        slide_button_two_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                optionTwoB.setEnabled(true);
                optionTwoB.setBackgroundColor(Color.parseColor("#f2fe71"));
                optionTwoB.setTextColor(Color.BLACK);
                optionTwoB.startAnimation(slide_button_two_in);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        slide_button_three_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                optionThreeB.setEnabled(true);
                optionThreeB.setBackgroundColor(Color.parseColor("#f2fe71"));
                optionThreeB.setTextColor(Color.BLACK);
                optionThreeB.startAnimation(slide_button_three_in);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        slide_button_four_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                optionFourB.setEnabled(true);
                optionFourB.setBackgroundColor(Color.parseColor("#f2fe71"));
                optionFourB.setTextColor(Color.BLACK);
                optionFourB.startAnimation(slide_button_four_in);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        slide_image_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                image.startAnimation(slide_image_in);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        gson = new Gson();

        score = 0;

        textViewScore.append(" " + String.valueOf(score));

        firebaseFunc();
    }




    void setBtns()
    {
        optionOneB.setEnabled(false);
        optionTwoB.setEnabled(false);
        optionThreeB.setEnabled(false);
        optionFourB.setEnabled(false);

        next();
    }

    void correctAnswer(Button btnGreen)
    {
        correctAnswerMP.start();
        btnGreen.setBackgroundColor(Color.GREEN);
        btnGreen.setTextColor(Color.WHITE);
        score = score + 5;
        textViewScore.setText("תוצאה " + String.valueOf(score));
        setBtns();
    }

    void unCorrectAnswer(Button btnGreen,Button btnRed)
    {
        failAnswerMP.start();
        btnGreen.setBackgroundColor(Color.GREEN);
        btnGreen.setTextColor(Color.WHITE);
        btnRed.setBackgroundColor(Color.RED);
        btnRed.setTextColor(Color.WHITE);
        setBtns();
    }



    void fillImagesAndAnswers(int i)
    {
        String response=prefs.getString("array" , "");
        images = gson.fromJson(response, new TypeToken<List<Images>>(){}.getType());

        final String name = images.get(i).getName();
        String option1 = images.get(i).getOptionOne();
        String option2 = images.get(i).getOptionTwo();
        String option3 = images.get(i).getOptionThree();
        String option4 = images.get(i).getOptionFour();
        String url = images.get(i).getUrl();

        optionOneB.setText(option1);
        optionTwoB.setText(option2);
        optionThreeB.setText(option3);
        optionFourB.setText(option4);

        Glide.with(this).load(url).into(image);

        optionOneB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(optionOneB.getText().toString().equals(name))
                {
                        correctAnswer(optionOneB);
                }
                else{
                    if(optionTwoB.getText().toString().equals(name))
                    {
                        unCorrectAnswer(optionTwoB,optionOneB);

                    }else if(optionThreeB.getText().toString().equals(name))
                    {
                        unCorrectAnswer(optionThreeB,optionOneB);

                    }else if(optionFourB.getText().toString().equals(name))
                    {
                        unCorrectAnswer(optionFourB,optionOneB);
                    }
                }
            }
        });


        optionTwoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(optionTwoB.getText().toString().equals(name))
                {
                    correctAnswer(optionTwoB);
                }
                else{
                    if(optionOneB.getText().toString().equals(name))
                    {
                        unCorrectAnswer(optionOneB,optionTwoB);

                    }else if(optionThreeB.getText().toString().equals(name))
                    {
                        unCorrectAnswer(optionThreeB,optionTwoB);

                    }else if(optionFourB.getText().toString().equals(name))
                    {
                        unCorrectAnswer(optionFourB,optionTwoB);
                    }
                }
            }
        });

        optionThreeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(optionThreeB.getText().toString().equals(name))
                {
                    correctAnswer(optionThreeB);
                }
                else{
                    if(optionOneB.getText().toString().equals(name))
                    {
                        unCorrectAnswer(optionOneB,optionThreeB);

                    }else if(optionTwoB.getText().toString().equals(name))
                    {
                        unCorrectAnswer(optionTwoB,optionThreeB);

                    }else if(optionFourB.getText().toString().equals(name))
                    {
                        unCorrectAnswer(optionFourB,optionThreeB);

                    }
                }
            }
        });

        optionFourB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(optionFourB.getText().toString().equals(name))
                {
                    correctAnswer(optionFourB);
                }
                else{
                    if(optionOneB.getText().toString().equals(name))
                    {
                        unCorrectAnswer(optionOneB,optionFourB);

                    }else if(optionThreeB.getText().toString().equals(name))
                    {
                        unCorrectAnswer(optionThreeB,optionFourB);

                    }else if(optionTwoB.getText().toString().equals(name))
                    {
                        unCorrectAnswer(optionTwoB,optionFourB);
                    }
                }
            }
        });
    }





    void firebaseFunc()
    {
        DatabaseReference myRef = database.getReference("images");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Images temp = snapshot.getValue(Images.class);
                    images.add(temp);
                }

                String json = gson.toJson(images);
                SharedPreferences.Editor editoJ = prefs.edit();
                editoJ.putString("array",json);
                editoJ.apply();

                fillImagesAndAnswers(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    void nextQ()
    {
        image.startAnimation(slide_image_out);
        optionTwoB.startAnimation(slide_button_two_out);
        optionOneB.startAnimation(slide_button_one_out);
        optionThreeB.startAnimation(slide_button_three_out);
        optionFourB.startAnimation(slide_button_four_out);
    }


    public void next()
    {
        i++;
        if(i<images.size())
        {
            nextQ();
        }
        else{

            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("allScores").push();
            Map<String, Object> map = new HashMap<>();
            map.put("id", databaseReference.getKey());
            map.put("name",name);
            map.put("score", score);
            map.put("date", date);

            databaseReference.setValue(map);


            Intent i = new Intent(getApplicationContext(),DoneActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }



    void getName(){

        DatabaseReference myRef = database.getReference("users");
        String id = mAuth.getCurrentUser().getUid();

        myRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);
                String UserName = users.getName();
                name = UserName;
                toolbar = (Toolbar) findViewById(R.id.toolbar);
                toolbar.setTitle("hi " + name);
                setSupportActionBar(toolbar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                Intent i = new Intent(getApplicationContext(),ResetPasswordActivity.class);
                startActivity(i);
                return true;
            case R.id.item2:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
                                        FirebaseAuth.getInstance().signOut();
                                        Intent intent = new Intent(getApplicationContext(),SignIn.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(MainActivity.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                return true;
            case R.id.item3:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),SignIn.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


}
