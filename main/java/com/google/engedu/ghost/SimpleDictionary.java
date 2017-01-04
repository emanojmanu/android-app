package com.google.engedu.ghost;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {

        Log.d("array dictionary","visited"+words.contains(word));
      //  words.contains("we");
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        int index=0;
        boolean flag=false;
        if(prefix.length()==0) {
            index = (int) Math.random() * (words.size());
            return words.get(index);
        }
        else{
         index=BinaryPosition(prefix,0,words.size()-1);
        if (index==-1)
        {
            return null;
        }
        }
        return words.get(index);
    }

    public int BinaryPosition(String prefix,int start,int end)
    {
        //0int p=prefix.length();
        Log.d("debug", "binary start");
       if(end>=start) {
           int middle = (start + end) / 2;
           Log.d("debug",words.get(middle));
           String middleWord = words.get(middle).substring(0, prefix.length());
           if (middleWord.equalsIgnoreCase(prefix)) {

               return middle;
           } else {

               if (middleWord.compareTo(prefix) > 0) {
                   Log.d("debug","greater");
                   return BinaryPosition(prefix, start, middle - 1);
               }
               else{
                   Log.d("debug0", "smaller");
                   return BinaryPosition(prefix,middle + 1,end);
               }
           }
       }
           return -1;
    }
    @Override
    public String getGoodWordStartingWith(String prefix) {
        String selected = null;
        return selected;
    }
}
