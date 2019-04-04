package com.meebu;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.meebu.adapter.MeebuTransAdapter;
import com.meebu.databinding.ActivityMeebuWalletBinding;
import com.meebu.model.PerDayTransaction;
import com.meebu.model.TransactionData;

import java.util.ArrayList;

public class MeebuWalletActivity extends AppCompatActivity {

    ArrayList<TransactionData> arrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMeebuWalletBinding activityMeebuWalletBinding= DataBindingUtil.setContentView(MeebuWalletActivity.this, R.layout.activity_meebu_wallet);

        activityMeebuWalletBinding.transactionRv.setLayoutManager(new LinearLayoutManager(MeebuWalletActivity.this,LinearLayoutManager.VERTICAL,false));
activityMeebuWalletBinding.addmoney.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Dialog dialog=new Dialog(MeebuWalletActivity.this, R.style.Theme_Dialog_Scratch);
        dialog.setContentView(R.layout.addmoney_dialog);
        dialog.show();
    }
});

activityMeebuWalletBinding.filteropen.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Dialog dialog=new Dialog(MeebuWalletActivity.this, R.style.Theme_Dialog_Scratch);
        dialog.setContentView(R.layout.filterdialog);
        dialog.show();
    }
});
    ArrayList<PerDayTransaction> perDayTransactionArrayList=new ArrayList<>();
    ArrayList<PerDayTransaction> perDayTransactionArrayList2=new ArrayList<>();

    PerDayTransaction perDayTransaction=new PerDayTransaction("9:45 AM","10000","200");
    PerDayTransaction perDayTransaction2=new PerDayTransaction("10:45 AM","10000","200");

    perDayTransactionArrayList.add(perDayTransaction);
    perDayTransactionArrayList.add(perDayTransaction);
    perDayTransactionArrayList2.add(perDayTransaction2);
    perDayTransactionArrayList2.add(perDayTransaction2);

        TransactionData transactionData=new TransactionData("12-Dec-2019",perDayTransactionArrayList);
        TransactionData transactionData1=new TransactionData("13-Dec-2019",perDayTransactionArrayList2);
        TransactionData transactionData2=new TransactionData("14-Dec-2019",perDayTransactionArrayList2);
        TransactionData transactionData3=new TransactionData("15-Dec-2019",perDayTransactionArrayList2);

        arrayList.add(transactionData);
        arrayList.add(transactionData1);
        arrayList.add(transactionData2);
        arrayList.add(transactionData3);
        activityMeebuWalletBinding.transactionRv.setAdapter(new MeebuTransAdapter(MeebuWalletActivity.this,arrayList));

        activityMeebuWalletBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
