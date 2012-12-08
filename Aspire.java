/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aspire;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Kevin
 */


public class Aspire {
//    List<String>[] alphbetizedList = new LinkedList[26]; 
    List<String>[] measuredList = new LinkedList[30]; 
    HashMap<String, String> alphabetizedMap = new HashMap(230000); 
    
    /**
     * @param args the command line arguments
     */
    Aspire()
    {
//        for(int i=0; i < alphbetizedList.length; i++){ 
//            alphbetizedList[i] = new LinkedList<>(); 
//        }     
        for(int i=0; i < measuredList.length; i++){ 
            measuredList[i] = new LinkedList<>(); 
        }     
    }
    
    private int getMeasuredIndex(String inputWord)
    {
        return inputWord.length();    
    }
    
    private void printMeasuredList(){        
        for(int i= measuredList.length - 1; i >=0 ; i--){ 
            for (String s : measuredList[i]) {                
                System.out.print(i + ": ");
                System.out.println(s);
            }
        }  
    }  
    
    private boolean indexFiles(String inputfile) throws IOException
    {        
        boolean result = false;
         Scanner in = null;
         File f = new File(inputfile);
         f.getName(); 
         try {
             in = new Scanner(new FileReader(f));
             while(in.hasNextLine() && !result) {  
                 String entry = in.nextLine();
                if(!entry.isEmpty()){
               //     alphbetizedList[getAlphaIndex(entry.charAt(0))].add(entry); 
                    alphabetizedMap.put(entry, entry);
                    measuredList[getMeasuredIndex(entry)].add(entry); 
                }
             }
         }
         catch(IOException e) {
             throw e;
         }
         finally {
             try { in.close() ; } catch(Exception e) { /* ignore */ }  
         }
         return result;
    }

    
    private boolean isConcatenatedString(String longestSubString, int originalStringSize)    {    
    //    System.out.println("longestSubString: " + longestSubString);        
        
        for(int i = 2; i <= longestSubString.length(); i++) {
            String compareString = alphabetizedMap.get(longestSubString.substring(0, i));
            if(compareString != null) {

          //      System.out.println(longestSubString +":" + compareString);            
                if( compareString.equals(longestSubString) && (compareString.length() != originalStringSize)) {
           //         System.out.println(compareString);
                    return true;
                }
                else if (longestSubString.startsWith(compareString) && (compareString.length() < longestSubString.length())) {
           //         System.out.println(compareString);
                    //   isConcatenatedString(longestSubString.substring(compareString.length()), originalStringSize);
                     if( isConcatenatedString(longestSubString.substring(compareString.length()), originalStringSize) ) {
                         return true;
                     }
                } 
            }
        }
        return false;
    }

    
    private void findLongestConcatenableWord() {
        int concatenableWordsCount = 0;
        for(int i = measuredList.length - 1; i > 0 ; i--){ 
            for (String s : measuredList[i]) {  
           //     System.out.println("----------" + s +"-----------" );
                
                if(isConcatenatedString(s, s.length())) {     
                    if (concatenableWordsCount < 2) {
                        System.out.println("Longest concatenable word " + (concatenableWordsCount + 1) + ": " + s);
                    }
                    else {
               //         System.out.println("Concatenable word: " + s);
                    }
                    concatenableWordsCount++;
                }
            }
        }
        System.out.println();
        if (concatenableWordsCount == 0) {
            System.out.println("No match found...");
        }
        else {
            System.out.println("There are total " + concatenableWordsCount + " concatenable words");
        }
    }  
        
    public static void main(String[] args) {
        Aspire myAspire = new Aspire();
        try{
           myAspire.indexFiles("src/aspire/words\ for\ problem.txt");   
        //        myAspire.indexFiles("src/aspire/yipe.txt");    
           //        myAspire.printAlphabetizedList();
           //         myAspire.printMeasuredList();
         }
         catch(IOException e) {
             System.out.println("Unable to open : "+e.getMessage());
         }
        myAspire.findLongestConcatenableWord();
        
    }
}
