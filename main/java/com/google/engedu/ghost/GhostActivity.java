package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    private SimpleDictionary simpleDict;
    private TextView text;
    private TextView status;
private int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
       text=(TextView)findViewById(R.id.ghostText);
        status=(TextView)findViewById(R.id.gameStatus);

        try {
            InputStream inputStream = assetManager.open("words.txt");
            InputStream inputStream1 = assetManager.open("words.txt");
            dictionary = new FastDictionary(inputStream);
            simpleDict=new SimpleDictionary(inputStream1);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        onStart(null);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
       if(userTurn)
       {
        char c=(char) event.getUnicodeChar();
        Log.d("foo",keyCode+"");
        if((c>='a'&&c<='z')||(c>='A'&&c<='Z')) {
            String s = text.getText().toString();
            s = s + c;
            text.setText(s);
            if (simpleDict.isWord(s)) {
                status.setText("complete word");
            }
            i++;
        }
        }
        userTurn=false;
        status.setText("computer turn");
        computerTurn();
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
     double b=Math.random();
        //text=null;
        if(b<0.5) {
            userTurn = true;
        }
        else{
            userTurn=false;
        }
        if (userTurn) {
            status.setText(USER_TURN);
        } else {
            status.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }
    private void computerTurn() {
        String wordFragment=text.getText().toString();
        // Do computer turn stuff then make it the user's turn again
        String s=simpleDict.getAnyWordStartingWith(wordFragment);

        if(wordFragment.length()>=4&&s!=null)
        {
            status.setText("victory to user");
            text.setText(s);
        }
        else
        {
            if(s!=null)
            {
                s=s.substring(0,wordFragment.length()+1);
                text.setText(s);
                userTurn = true;
                status.setText(USER_TURN);
            }
            else{
                status.setText("computer victory");
                userTurn = false;
                //status.setText(USER_TURN+i);
            }
        }

    }
    public void restart(View v)
    {
        text.setText("");
        onStart(null);
    }
    public void challenge(View v)
    {
        TextView tx=(TextView)findViewById(R.id.ghostText);
        String wordFragment=tx.getText().toString();
        TextView status=(TextView)findViewById(R.id.gameStatus);
        if(wordFragment.length()>=4&&simpleDict.getAnyWordStartingWith(wordFragment)!=null)
        {
            tx.setText(simpleDict.getAnyWordStartingWith(wordFragment));
            status.setText("user won");
            Toast.makeText(getApplicationContext(),"inside",Toast.LENGTH_LONG).show();
        }
        else{
            if(simpleDict.getAnyWordStartingWith(wordFragment)!=null)
            {
                tx.setText(simpleDict.getAnyWordStartingWith(wordFragment));
                status.setText("computer won");
            }
            else{
                if(userTurn) {
                    status.setText("user won");
                }
                else{
                    status.setText("computer won");
                }
            }
        }
    }

}
