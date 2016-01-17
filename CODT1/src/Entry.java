/* 
Diese Klasse ist fuer die Eintraege, mit denen der Huffmanbaum erstellt wird-
 */

import java.text.DecimalFormat;
import java.util.Vector;

/**
 *
 * @author Daniel
 *
 * erbt von Comparable, damit man Entryelemente innerhalb einer Collection
 * sortieren kann
 */
public class Entry implements Comparable<Entry> {

    public String key;              // Zeichen
    public double value;            // Relative haeufigkeit des Zeichens. Wird berechnet, sobald alle Zeichen eigelesen Wurden
    public int count;               // Counter, wie oft das Zeichen vorhanden ist, falls mittels eines eines Textes erstellt
    public int index;
    public Vector<Boolean> code;    // Gibt an, ob dieser Eintrag das rechte oder Linke Kind im Huffmanbaum ist false == linkes child
    public BinaryTree<Entry> node;  // 

    // Formatstring fuer die Ausgabe der Eintraege
    public static DecimalFormat decForm = new DecimalFormat("0.0000");

    @Override
    /* Ausgabe des Eintrags */
    public String toString() {

        String bitlist = "";        // Fuer die Ausgabe des Bitvektors
        String strKey = this.key;   // Ausgabe des Zeichens

        // Alle Elemente des Bitvektors auslesen und in String "Umwandeln"
        for (boolean b : code) {
            bitlist += (b ? "1" : "0");
        }

        // Zeilenumbruch und leerzeichen anpassen, damit diese Zeichen bei der Ausgabe einfach erkennt
        strKey = strKey.replace("\n", "'\\n'");
        strKey = strKey.replace(" ", "' '");

        //return "<- " + strKey + "\t% = " + decForm.format(value) + "\t ent = " + decForm.format(getEntropy()) + "\t ig = " + decForm.format(value*code.size()) + "\t code --> " + bitlist; //To change body of generated methods, choose Tools | Templates.
        return "<- " + strKey + "\t(" + decForm.format(this.value) + ")" + "\t" +Helper.convVectorToBinaryString(code);
    }

    /*  
        Entropie berechnen und zurueckgeben
     */
    public double getEntropy() {
        return value * (-1d * (Math.log(value) / Math.log(2d)));
    }

    /*
        Konstruktor, in dem das Zeichen und die Wahrschienlichkeit gesetzt wird
        FUER AUFGABE 2
     */
    public Entry(String key, Double value) {
        this.key = key;
        this.value = value;
        this.code = new Vector<>();
    }

    /*
    *   VERGLICHEN WIRD MIT DEN WAHRSCHEINLICHKEITEN
     */
    public boolean isLT(Entry test) {
        return this.value < test.value;
    }

    /*
    * Vergleichsfunktion damit man Entryelemente innerhalb einer Collection sortieren kann
    */
    @Override
    public int compareTo(Entry test) {

        return (this.value * 1000000 - test.value * 1000000) < 0 ? -1 : 1;

    }

}
