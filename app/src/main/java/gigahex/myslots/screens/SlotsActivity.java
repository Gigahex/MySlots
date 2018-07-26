package gigahex.myslots.screens;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brouding.simpledialog.SimpleDialog;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gigahex.myslots.R;
import gigahex.myslots.database.ResultPoints;
import gigahex.myslots.slots.Callback;
import gigahex.myslots.slots.SlotsBuilder;


public class SlotsActivity extends AppCompatActivity {

    private int profit = 0;
    private int rate = 100;
    private int deposit = 1000;
    Button saveButton;

    @BindView(R.id.deposit)
    TextView depositTextView;

    @BindView(R.id.textViewRate)
    TextView rateTextView;

    @OnClick({R.id.buttonIncrease, R.id.buttonDecrease})
    void onClickChangeRate(View view) {
        switch (view.getId()) {
            case R.id.buttonIncrease:
                rate +=100;
                setTextRate(rate);
                break;
            case R.id.buttonDecrease:
                rate-=100;
                if(rate<=0){
                    rate =100;
                }
                setTextRate(rate);
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slots);
        ButterKnife.bind(this);

        setTextRate(rate);
        depositTextView.setText("Ваш депозит:" + deposit);

        Button buttonBack = (Button) findViewById(R.id.back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(SlotsActivity.this, "back", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SlotsActivity.this, TargetActivity.class);
                startActivity(i);
            }
        });




        saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(getBaseContext());

                View view = layoutInflater.inflate(R.layout.dialog, null);

                EditText nameBox = (EditText) view.findViewById(R.id.name);
                SimpleDialog mCustomDialog = new SimpleDialog.Builder(SlotsActivity.this)
                        .setCustomView(view)
                        .setBtnConfirmText("OK")
                        .setBtnCancelText("Cancel", false)
                        .setBtnCancelTextColor("#777777")


                        // Customizing (You can find more in Wiki)

                        .setBtnConfirmTextColor("#de413e")
                        .setTitle("Сохранение результата")
                        .setBtnConfirmTextSizeDp(15)
                        .onConfirm(new SimpleDialog.BtnCallback() {
                            @Override
                            public void onClick(@NonNull SimpleDialog dialog, @NonNull SimpleDialog.BtnAction which) {
                                String name = nameBox.getText().toString();
                                ContentResolver contentResolver = SlotsActivity.this.getContentResolver();
                                ContentValues values = new ContentValues();
                                values.put(ResultPoints.Columns.COLUMN_NAME, name);
                                values.put(ResultPoints.Columns.COLUMN_POINTS, deposit);
                                Uri uri = contentResolver.insert(ResultPoints.CONTENT_URI, values);
                            }
                        })
                        .show();



//                CustomDialog dialog = new CustomDialog();
//                Bundle args = new Bundle();
//                args.putInt("points", deposit);
//                dialog.setArguments(args);
//                dialog.show(getSupportFragmentManager(), "custom");
            }
        });


        final SlotsBuilder.Builder builder = SlotsBuilder.builder(this);
        final SlotsBuilder slots = builder
                .addSlots(R.id.slot_one, R.id.slot_two, R.id.slot_three, R.id.slot_four, R.id.slot_five)
                .addDrawables(R.drawable.padlock_1, R.drawable.padlock_2, R.drawable.padlock_3, R.drawable.padlock_4, R.drawable.padlock_5)
                .setScrollTimePerInch(1f)
                .setDockingTimePerInch(0f)
                .setScrollTime(2500 + new SecureRandom().nextInt(1500))
                .setChildIncTime(1000)
                .setOnFinishListener(new Callback() {
                    @Override
                    public void OnFinishListener() {
                        List<LinearLayoutManager> layoutManagers = getLayoutManagers();
                        List<Drawable> drawables = new ArrayList<>();
                        int coefficient;
                        for (int i = 0; i < 5; i++) {
                            drawables.add(((ImageView) layoutManagers.get(i).findViewByPosition(
                                    layoutManagers.get(i).findFirstVisibleItemPosition()+1))
                                    .getDrawable()
                                    .getCurrent());
                        }
                        if ((drawables.get(0) == drawables.get(1)) &&
                                (drawables.get(1) == drawables.get(2)) &&
                                (drawables.get(2) == drawables.get(3)) &&
                                (drawables.get(3) == drawables.get(4))) {
                            coefficient = 5;
                            resultGame(coefficient);
                        } else if (((drawables.get(0) == drawables.get(1)) &&
                                (drawables.get(1) == drawables.get(2)) &&
                                (drawables.get(2) == drawables.get(3)))
                                || ((drawables.get(1) == drawables.get(2)) &&
                                (drawables.get(2) == drawables.get(3)) &&
                                (drawables.get(3) == drawables.get(4)) &&
                                (drawables.get(3) == drawables.get(4)))) {
                            coefficient = 4;
                            resultGame(coefficient);
                        } else if (((drawables.get(0) == drawables.get(1)) &&
                                (drawables.get(1) == drawables.get(2)))
                                || ((drawables.get(1) == drawables.get(2)) &&
                                (drawables.get(2) == drawables.get(3))) ||
                                ((drawables.get(2) == drawables.get(3)) &&
                                        (drawables.get(3) == drawables.get(4)))) {
                            coefficient = 3;
                            resultGame(coefficient);
                        } else if ((drawables.get(0) == drawables.get(1)) ||
                                (drawables.get(1) == drawables.get(2)) ||
                                (drawables.get(2) == drawables.get(3)) ||
                                (drawables.get(3) == drawables.get(4))) {
                            coefficient = 2;
                            resultGame(coefficient);
                        } else {
                            coefficient = 1;
                            resultGame(coefficient);
                        }
                    }
                })
                .build();

        Button startGame = (Button) findViewById(R.id.btn);
        startGame.setOnClickListener(view -> {
            if ((deposit - rate) < 0) {
                Toast.makeText(SlotsActivity.this,
                        "Ставка выше депозита!",
                        Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(SlotsActivity.this,
                        "Нормальная ставка",
                        Toast.LENGTH_SHORT).show();
                slots.start();
            }
        });
    }



    void resultGame(int coefficient) {
            switch (coefficient){
                case 5:
                    //Джекпот!!!
                    deposit = 100000;
                    Toast.makeText(SlotsActivity.this,
                            "Джекпот!!! 1 000 000!!!",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    //Если четыре подряд уможаем ставку на 100
                    profit = rate*100;
                    Toast.makeText(SlotsActivity.this,
                            "Выйгрыш:" + profit,
                            Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    profit = rate * 10;
                    Toast.makeText(SlotsActivity.this,
                            "Выйгрыш:" + profit,
                            Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    profit = rate * 2;
                    Toast.makeText(SlotsActivity.this,
                            "Выйгрыш:" + profit,
                            Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    profit = -rate;
                    Toast.makeText(SlotsActivity.this,
                            "Вы проиграли:" + rate,
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        deposit = deposit + profit;
            depositTextView.setText("Ваш депозит: " + deposit);

            if (deposit == 0) {
                depositTextView.setText("Вы проиграли всё");

            }
        }
        void setTextRate(int text_rate){
        rateTextView.setText(String.valueOf(text_rate));
        }
}


