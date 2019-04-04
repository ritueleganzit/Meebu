package com.meebu;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goibibo.libs.views.ScratchRelativeLayoutView;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class DialogActivity extends AppCompatActivity {

    KonfettiView viewKonfetti;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        final Dialog dialog = new Dialog(this);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount=0.0f;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setDimAmount(0.9f);
        dialog.setContentView(R.layout.dialog_scratch);

        String amount=getIntent().getStringExtra("amount");
        int foreground=getIntent().getIntExtra("foreground",0);

        Log.d("intentDataaaa",amount+"   "+foreground);

        ScratchRelativeLayoutView scratchRelativeLayoutView = dialog.findViewById(R.id.scratch_card);
        scratchRelativeLayoutView.setStrokeWidth(10);


        /**
         Using Inflated View
         */
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    final View scratchView = inflater.inflate(R.layout.lyt_scratch, scratchRelativeLayoutView, true);
    scratchRelativeLayoutView.setScratchView(scratchView, scratchRelativeLayoutView);
        TextView samount = dialog.findViewById(R.id.amount);

        ImageView image_foreground = scratchView.findViewById(R.id.image_foreground);
        viewKonfetti =dialog.findViewById(R.id.viewKonfetti);

        samount.setText(amount);

        Glide.with(this)
                .load(foreground)
                .apply(new RequestOptions().placeholder(foreground))
                .into(image_foreground);
        /**
         * Opening in already revealed state
         */
        //scratchRelativeLayoutView.setScratchView(ScratchRelativeLayoutView.ScratchedState.REVEALED);

        /**
         * Using Raw View
         */
    //    scratchRelativeLayoutView.setScratchView(R.layout.lyt_scratch);


        scratchRelativeLayoutView.setRevealListener(new ScratchRelativeLayoutView.IRevealListener() {
            @Override
            public void onRevealed(ScratchRelativeLayoutView tv) {
                // on reveal

            }

            @Override
            public void onRevealPercentChangedListener(ScratchRelativeLayoutView siv, float percent) {
                // on percent change
                if(percent>0.7 && percent < 0.8)
                {
                    viewKonfetti.build()
                            .addColors(Color.YELLOW, Color.GREEN, Color.DKGRAY, Color.BLUE, Color.CYAN, Color.RED)
                            .setDirection(0.0, 359.0)
                            .setSpeed(1f, 5f)
                            .setFadeOutEnabled(true)
                            .setTimeToLive(900L)
                            .addShapes(Shape.RECT, Shape.CIRCLE)
                            .addSizes(new Size(5, 2))
                            .setPosition(-50f, viewKonfetti.getWidth() + 50f, -50f, -50f)
                            .streamFor(50, 800L);
                   // Toast.makeText(DialogActivity.this, "TaDaaaaaa!!!!", Toast.LENGTH_SHORT).show();

                }
                if(percent>0.7)
                {
                    dialog.setCanceledOnTouchOutside(true);
                }
                else
                {
                    dialog.setCanceledOnTouchOutside(false);
                }


            }
        });

        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //image_foreground.setImageResource(rewardsData.getBackgroundId());
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2; //style id
        dialog.show();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                finish();
            }
        });
    }
}