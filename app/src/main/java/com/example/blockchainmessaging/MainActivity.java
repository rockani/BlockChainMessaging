package com.example.blockchainmessaging;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blockchainmessaging.Fragments.PowFragment;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    BlockChainManager blockChainManager;
    EditText Message;
    ImageButton send;
    TextView messageView; ScrollView scroll;
    private RecyclerView mRecyclerView; private BlockChainAdapter mAdapter;
    private SharedPreferencesManager mPrefs;
    public int pow;
    private boolean isDarkActivated;
    private static  final String TAG_POW_DIALOG = "proof_of_work_dialog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        Message = findViewById(R.id.edit_message);
        send = findViewById(R.id.btn_send_data);

        mPrefs = new SharedPreferencesManager(getApplicationContext());

        isDarkActivated = mPrefs.getDarkTheme();
        pow = mPrefs.getPowValue();

        if(isDarkActivated)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);


        messageView = findViewById(R.id.messageView);
        //scroll = findViewById(R.id.scrollView3);
        new Thread(() -> runOnUiThread(() -> {
            blockChainManager= new BlockChainManager(pow,this);
            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_content);
            mAdapter = new BlockChainAdapter(blockChainManager.Blockchain,this);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
            send.setOnClickListener(v -> {
                if (!(Message.getText().toString().isEmpty())) {
                    Block newBlock = blockChainManager.generateNextBlock(Message.getText().toString());
                    blockChainManager.addBlock(newBlock);

                    Message.setText("");

                    int size = blockChainManager.Blockchain.size();
                    blockChainManager.addBlock(newBlock);
                    mRecyclerView.getAdapter().notifyItemInserted(size);
                    mRecyclerView.smoothScrollToPosition(size);
                    Log.d("CheckingIt",String.valueOf(blockChainManager.Blockchain.size()));

                }
            });
        })).start();

    }


    @Override
    protected void onPause() {
        super.onPause();
        //mPrefs.setPowValue(pow);
        mPrefs.setDarkTheme(isDarkActivated);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES){
            menu.findItem(R.id.action_dark).setTitle("Day Mode");
        } else{
            menu.findItem(R.id.action_dark).setTitle("Night Mode");
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_pow:
                PowFragment powFragment = new PowFragment();
                powFragment.show(this.getSupportFragmentManager(),TAG_POW_DIALOG);
                break;

            case R.id.action_dark:
                int nightMode = AppCompatDelegate.getDefaultNightMode();
                if(nightMode == AppCompatDelegate.MODE_NIGHT_YES){

                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        isDarkActivated=false;

                }
                else{

                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        isDarkActivated=true;

                }
                break;
            case R.id.action_exit:
                finish();
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    private void displayMessage(Block newBlock) {
        Date date = new Date(newBlock.timestamp);
        messageView.append("\n\n "+ "Index:"+newBlock.index+"\n "+"Previous Hash:"+newBlock.previousHash+"\n "+
                "TimeStamp:"+date.toString()+"\n "+
                "Data:"+newBlock.data+"\n "+"Hash:"+newBlock.Hash+"Size:"+blockChainManager.Blockchain.size());
    }

}