/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Vector;

/**
 *
 * @author Daniel
 */
public class Entry implements Comparable<Entry>{
    
    public String key;
    public double value;
    public int count;
    public int index;
    public Vector<Boolean> code; //false == linkes child
    public BinaryTree<Entry> node;
    
    
    @Override
    public String toString() {
        String bitlist = "";
        String strKey = this.key;
        for (boolean b : code){
            bitlist += (b?"1":"0");
        }        
        strKey = strKey.replace("\n", "'\\n'");
        strKey = strKey.replace(" ", "' '");
        
        return "<- " + strKey + "\t(" + String.valueOf(value) + ")\t" + String.valueOf(getEntropy()) + "\t" + String.valueOf(value*code.size()) + "\t--> " + bitlist; //To change body of generated methods, choose Tools | Templates.
    }
    
    public double getEntropy(){        
        return value * (-1d*(Math.log(value)/Math.log(2d)));
    }
    
    public Entry( String key, Double value){
        this.key = key;
        this.value = value;
        this.code = new Vector<>();
    }
    
    public boolean isLT(Entry test){
        return this.value < test.value;
    }

    @Override
    public int compareTo(Entry test) {

        return (this.value*100000 - test.value*100000)<0?-1:1;
        
    }
    
}