package com.google.engedu.ghost;

import java.util.HashMap;


public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord;


    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
      // children.put(".",this);
    }

    public void add(String s) {

        int count=s.length();
        TrieNode insertpos=null;
        while(count>0)
        {
          if(children.containsKey(s.substring(0,count-1)))
          {
              insertpos=children.get(s.substring(0,count-1));
          }
            count--;
        }
        if(insertpos==null){
            int count2=0;
            boolean firstflag=true;
            TrieNode child=new TrieNode();
            while(count2<s.length()) {
                if(count2==s.length()-1)
                {
                    child.isWord=true;
                }
                if(firstflag) {
                    this.children.put(s.substring(0), child);
                firstflag=false;
                    child=this.children.get(s.substring(0));
                }
                else{
                    child.children.put(s.substring(0,count2),child);
                    child= child.children.put(s.substring(0,count2),child);
                }
                count2++;

            }
        }
        else{


        }
    }

    public boolean isWord(String s) {
      return false;
    }

    public String getAnyWordStartingWith(String s) {
        return null;
    }

    public String getGoodWordStartingWith(String s) {
        return null;
    }
}
