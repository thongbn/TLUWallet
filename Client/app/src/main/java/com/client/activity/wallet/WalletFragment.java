package com.client.activity.wallet;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.client.R;
import com.client.database.DataBaseHelper;
import com.client.database.Wallet.AddData;

import java.util.ArrayList;

public class WalletFragment extends Fragment {

    private DataBaseHelper mydb;
    private ArrayList<String> walletId = new ArrayList<String>();
    private ArrayList<String> w_waletName = new ArrayList<String>();
    private ArrayList<String> w_walletMoney = new ArrayList<String>();
    private ArrayList<String> w_walletType = new ArrayList<String>();

    private ListView walletList;
    private AlertDialog.Builder build;
	
	public WalletFragment(){}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.wallet_fragment, container, false);
        walletList = (ListView) rootView.findViewById(R.id.listView1);

        mydb = new DataBaseHelper(rootView.getContext());


//        //add new
//        rootView.findViewById(R.id.btAdd).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), AddData.class);
//                intent.putExtra("update", false);
//                startActivity(intent);
//            }
//        });

        //click to update
        walletList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(arg1.getContext(), AddData.class);
                intent.putExtra("WName", w_waletName.get(arg2));
                intent.putExtra("WMoney", w_walletMoney.get(arg2));
                intent.putExtra("WType", w_walletType.get(arg2));
                intent.putExtra("ID", walletId.get(arg2));
                intent.putExtra("update", true);
                startActivity(intent);
            }
        });

        //long click del
        walletList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int arg2, long arg3) {
                build = new AlertDialog.Builder(rootView.getContext());
                build.setTitle("Delete " + w_waletName.get(arg2));
                build.setMessage("Bạn có chắc chắn muốn xóa?");
                build.setPositiveButton("Xóa",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(rootView.getContext(),
                                        w_waletName.get(arg2) + " is deleted.", Toast.LENGTH_LONG).show();
                                mydb.getReadableDatabase().delete(
                                        DataBaseHelper.WALLET_TABLE,
                                        DataBaseHelper.WALLET_ID + "=" + walletId.get(arg2), null);
                                displayData();
                                dialog.cancel();
                            }
                        });
                build.setNegativeButton("Bỏ qua",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog aler = build.create();
                aler.show();

                return true;

            }
        });

        return rootView;
    }

    @Override
    public void onResume(){
        displayData();
        super.onResume();
    }
    private void displayData(){
        mydb.open();
        Cursor cursor = mydb.getReadableDatabase().rawQuery("Select * from " + DataBaseHelper.WALLET_TABLE, null);
        walletId.clear();
        w_waletName.clear();
        w_walletMoney.clear();
        w_walletType.clear();

        if(cursor.moveToFirst()){
            do {
                walletId.add(cursor.getString(cursor.getColumnIndex(DataBaseHelper.WALLET_ID)));
                w_waletName.add(cursor.getString(cursor.getColumnIndex(DataBaseHelper.WALLET_NAME)));
                w_walletMoney.add(cursor.getString(cursor.getColumnIndex(DataBaseHelper.WALLET_MONEY)));
                w_walletType.add(cursor.getString(cursor.getColumnIndex(DataBaseHelper.WALLET_TYPE_MONEY)));
            }
            while (cursor.moveToNext());
        }
        DisplayWallet dWallet = new DisplayWallet(getContext(), walletId, w_waletName, w_walletMoney);
        walletList.setAdapter(dWallet);
        cursor.close();
    }

}
