/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.score;

/**
 *
 * @author jakub
 */
public class Record {
    private String name;
    private int count;

    public Record(String name, int pocet) {
        this.name = name;
        this.count = pocet;
    }
    
    public String getName(){
        return name;
    }
    
    public int getCount(){
        return count;
    }
}
