package com.example.projectver3;


import static com.example.projectver3.Add_Update_TransferActivity.data_userTransfer;
import static com.example.projectver3.TransferHistoryActivity.DocDLTransfer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectver3.fragment.AcountFragment;
import com.example.projectver3.model.Transfer;

public class TransferDetailsActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;
    TextView tvTransferID, tvTransferFromAccount,tvTransferToAccount,tvDateTransfer,tvMoney,tvNoteATransfer;
    Button btnDeleteTransfer,btnEditTransfer,btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_details);
        setControl();
        setEvent();
    }

    private void setControl() {
        tvTransferID = findViewById(R.id.tvTransferID);
        tvTransferFromAccount = findViewById(R.id.tvTransferFromAccount);
        tvTransferToAccount = findViewById(R.id.tvTransferToAccount);
        tvDateTransfer = findViewById(R.id.tvDateTransfer);
        tvMoney = findViewById(R.id.tvMoney);
        tvNoteATransfer = findViewById(R.id.tvNoteTransfer);
        btnDeleteTransfer = findViewById(R.id.btnDeleteTransfer);
//        btnEditTransfer = findViewById(R.id.btnEditTransfer);
        btnBack = findViewById(R.id.btnBack);
    }
    private void setEvent() {
        btnDeleteTransfer.setText(R.string.delete);
        if (getIntent().getExtras() != null){
            Transfer t = (Transfer) getIntent().getExtras().get("detailsTransfer");
            tvTransferID.setText(String.valueOf(t.getIdTransfer()));
            tvTransferFromAccount.setText(t.getNameFromAccount());
            tvTransferToAccount.setText(t.getNameToAccount());
            tvMoney.setText(String.valueOf(t.getMoneyTransfer()));
            tvDateTransfer.setText(t.getDateTransfer());
            tvNoteATransfer.setText(t.getNoteTransfer());
        }
        btnDeleteTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data_userTransfer.child(tvTransferID.getText().toString()).removeValue();
                DocDLTransfer();
//                Intent intent = new Intent(TransferDetailsActivity.this, AcountFragment.class);
//                startActivity(intent);
                finish();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        btnEditTransfer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Transfer t = (Transfer) getIntent().getExtras().get("detailsTransfer");
//                Intent intent = new Intent(TransferDetailsActivity.this, Add_Update_TransferActivity.class);
//                intent.putExtra("isEditTransfer",true);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("editTransfer",t);
//                intent.putExtras(bundle);
//                startActivityForResult(intent,MY_REQUEST_CODE);
//            }
//        });

    }
}