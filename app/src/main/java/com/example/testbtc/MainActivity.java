package com.example.testbtc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    //Переменная для хранения значения progressBar
    private int progressStatus = 0;

    private int balance;

    //Переменная для хранения режима отображения баланса (true - Satoshi, false - BTC)
    private boolean modeBalance = true;
    private ProgressBar progressBar;
    private TextView txtView, txtBigBalance, txtSmallBalance,
            text_ping_server1, tv_value_ping_server1, tv_ms_server1,
            text_ping_server2, tv_value_ping_server2, tv_ms_server2,
            text_ping_server3, tv_value_ping_server3, tv_ms_server3,
            text_ping_server4, tv_value_ping_server4, tv_ms_server4;

    private ImageView imageViewServer1, imageViewServer2, imageViewServer3, imageViewServer4;

    private RelativeLayout relativeLayoutServer1, relativeLayoutServer2, relativeLayoutServer3, relativeLayoutServer4;

    private Button startBtn, btnTakeBTC, btn_boost;

    private int countActiveServers = 0;
    static Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Функция инициализации элементов интерфейсов
        initView();

        res = getResources();

        UserDatabase db = App.getInstance().getDatabase();

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(() -> handler.post(() -> balance = db.userDAO().getUserBalance(1)));

        startBtn.setOnClickListener(v -> {

            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            v.startAnimation(shake);

            //int balanceSatoshi = 15;
            double balanceBTC = balance / 100000000D;
            //смена режима
            modeBalance = !modeBalance;

            if(modeBalance){
                txtBigBalance.setText(res.getString(R.string.balance_satoshi,  balance));
                txtSmallBalance.setText(res.getString(R.string.balance_BTC,  balanceBTC));
            } else {
                txtBigBalance.setText(res.getString(R.string.balance_BTC,  balanceBTC));
                txtSmallBalance.setText(res.getString(R.string.balance_satoshi,  balance));
            }

            if(progressStatus < 100) {
                progressStatus += 5;
            } else {
                progressStatus = 0;
            }

            Point maxSizePoint = new Point();
            getWindowManager().getDefaultDisplay().getSize(maxSizePoint);
            final int maxX = maxSizePoint.x;

            progressBar.setProgress(progressStatus);
            int val = (progressStatus * (progressBar.getWidth() - 2 )) / progressBar.getMax();
            int textViewX = val - (txtView.getWidth() / 2);
            int finalX = txtView.getWidth() + textViewX > maxX ? (maxX - txtView.getWidth() - 30) : textViewX + 30;
            txtView.setX(finalX < 0 ? 30 : finalX);
            txtView.setText(res.getString(R.string.process_progress,  progressStatus));
        });

        btn_boost.setOnClickListener(v -> {
            Animation alpha = AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha);
            v.startAnimation(alpha);

            switch (countActiveServers) {
                case 0:
                    imageViewServer1.setBackgroundResource(R.drawable.metric_active);
                    relativeLayoutServer1.setBackgroundResource(R.drawable.gradient_active_card);
                    text_ping_server1.setTextColor(ContextCompat.getColor(this, R.color.color_text_ping));
                    tv_value_ping_server1.setTextColor(ContextCompat.getColor(this, R.color.white));
                    tv_ms_server1.setTextColor(ContextCompat.getColor(this, R.color.turquoise_blue));
                    countActiveServers++;
                    break;
                case 1:
                    imageViewServer2.setBackgroundResource(R.drawable.metric_active);
                    relativeLayoutServer2.setBackgroundResource(R.drawable.gradient_active_card);
                    text_ping_server2.setTextColor(ContextCompat.getColor(this, R.color.color_text_ping));
                    tv_value_ping_server2.setTextColor(ContextCompat.getColor(this, R.color.white));
                    tv_ms_server2.setTextColor(ContextCompat.getColor(this, R.color.turquoise_blue));
                    countActiveServers++;
                    break;
                case 2:
                    imageViewServer3.setBackgroundResource(R.drawable.metric_active);
                    relativeLayoutServer3.setBackgroundResource(R.drawable.gradient_active_card);
                    text_ping_server3.setTextColor(ContextCompat.getColor(this, R.color.color_text_ping));
                    tv_value_ping_server3.setTextColor(ContextCompat.getColor(this, R.color.white));
                    tv_ms_server3.setTextColor(ContextCompat.getColor(this, R.color.turquoise_blue));
                    countActiveServers++;
                    break;
                case 3:
                    imageViewServer4.setBackgroundResource(R.drawable.metric_active);
                    relativeLayoutServer4.setBackgroundResource(R.drawable.gradient_active_card);
                    text_ping_server4.setTextColor(ContextCompat.getColor(this, R.color.color_text_ping));
                    tv_value_ping_server4.setTextColor(ContextCompat.getColor(this, R.color.white));
                    tv_ms_server4.setTextColor(ContextCompat.getColor(this, R.color.turquoise_blue));
                    countActiveServers++;
                    break;
                default:
                    countActiveServers = 0;

                    imageViewServer1.setBackgroundResource(R.drawable.metric_disable);
                    relativeLayoutServer1.setBackgroundResource(R.drawable.gradient_card_server);
                    text_ping_server1.setTextColor(ContextCompat.getColor(this, R.color.black));
                    tv_value_ping_server1.setTextColor(ContextCompat.getColor(this, R.color.black));
                    tv_ms_server1.setTextColor(ContextCompat.getColor(this, R.color.black));

                    imageViewServer2.setBackgroundResource(R.drawable.metric_disable);
                    relativeLayoutServer2.setBackgroundResource(R.drawable.gradient_card_server);
                    text_ping_server2.setTextColor(ContextCompat.getColor(this, R.color.black));
                    tv_value_ping_server2.setTextColor(ContextCompat.getColor(this, R.color.black));
                    tv_ms_server2.setTextColor(ContextCompat.getColor(this, R.color.black));

                    imageViewServer3.setBackgroundResource(R.drawable.metric_disable);
                    relativeLayoutServer3.setBackgroundResource(R.drawable.gradient_card_server);
                    text_ping_server3.setTextColor(ContextCompat.getColor(this, R.color.black));
                    tv_value_ping_server3.setTextColor(ContextCompat.getColor(this, R.color.black));
                    tv_ms_server3.setTextColor(ContextCompat.getColor(this, R.color.black));

                    imageViewServer4.setBackgroundResource(R.drawable.metric_disable);
                    relativeLayoutServer4.setBackgroundResource(R.drawable.gradient_card_server);
                    text_ping_server4.setTextColor(ContextCompat.getColor(this, R.color.black));
                    tv_value_ping_server4.setTextColor(ContextCompat.getColor(this, R.color.black));
                    tv_ms_server4.setTextColor(ContextCompat.getColor(this, R.color.black));
                    break;
            }
        });

        btnTakeBTC.setOnClickListener(v -> {
            Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale);
            v.startAnimation(scale);

            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "testBTC");
                shareIntent.putExtra("balance", 15);
                String shareMessage= "\n" + res.getString(R.string.intent_share)+ "\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }
        });
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    private void initView(){
        progressBar = findViewById(R.id.progressBar);
        txtView = findViewById(R.id.tvProgress);
        txtBigBalance = findViewById(R.id.big_text_balance);
        txtSmallBalance = findViewById(R.id.small_text_balance);
        startBtn = findViewById(R.id.startBtn);
        btnTakeBTC = findViewById(R.id.btn_take_btc);
        btn_boost = findViewById(R.id.btn_boost);
        imageViewServer1 = findViewById(R.id.img_ping_server1);
        imageViewServer2 = findViewById(R.id.img_ping_server2);
        imageViewServer3 = findViewById(R.id.img_ping_server3);
        imageViewServer4 = findViewById(R.id.img_ping_server4);
        relativeLayoutServer1 = findViewById(R.id.relativeLayout_server1);
        relativeLayoutServer2 = findViewById(R.id.relativeLayout_server2);
        relativeLayoutServer3 = findViewById(R.id.relativeLayout_server3);
        relativeLayoutServer4 = findViewById(R.id.relativeLayout_server4);
        text_ping_server2 = findViewById(R.id.text_ping_server2);
        tv_value_ping_server2 = findViewById(R.id.tv_value_ping_server2);
        tv_ms_server2 = findViewById(R.id.tv_ms_server2);
        text_ping_server3 = findViewById(R.id.text_ping_server3);
        tv_value_ping_server3 = findViewById(R.id.tv_value_ping_server3);
        tv_ms_server3 = findViewById(R.id.tv_ms_server3);
        text_ping_server4 = findViewById(R.id.text_ping_server4);
        tv_value_ping_server4 = findViewById(R.id.tv_value_ping_server4);
        tv_ms_server4 = findViewById(R.id.tv_ms_server4);
        text_ping_server1 = findViewById(R.id.text_ping_server1);
        tv_value_ping_server1 = findViewById(R.id.tv_value_ping_server1);
        tv_ms_server1 = findViewById(R.id.tv_ms_server1);
    }
}