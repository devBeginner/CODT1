/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author Daniel
 */
public class HuffmanCode {

    // Hashmap fuer das Codieren von Zeichen. Sie enthaelt saemtliche Zeichen und die 
    // dazugehoerigen Bitvektoren
    private HashMap<String, Vector<Boolean>> encodeMap;

    // Liste fuer das Zwischenspeichern der Knoten und um die Knoten der Grossen nach zu sortieren
    private ArrayList<Entry> entries;

    // Die Liste enthaelt alle Eintraege fuer die Zeichen
    private ArrayList<BinaryTree<Entry>> nodes;

    // Zeiger auf den Root Knoten des erstellten Huffman Baumes
    private BinaryTree<Entry> treeRoot;

    {
        encodeMap = new HashMap<>();
        entries = new ArrayList<>();
        nodes = new ArrayList<>();
    }

    /*
    *   Erstellt die Informationsquelle 
     */
    public void createInfSrcFromText(String strText) {

        // neue Liste erzeugen
        entries = new ArrayList<>();

        // fuer das einlesen der Zeichen wird eine Hasmap verwendet
        HashMap<Character, Entry> chars = new HashMap<>();

        // alle Zeichen des Übergebenen Textes durchlaufen und haefigkeit des Vorkommens ermitteln
        for (int i = 0; i < strText.length(); i++) {

            // aktuelles Zeichen ermitteln
            char chrCurr = strText.charAt(i);

            // Wenn das Zeichen einen Eintrag in der Hashmap Map hat
            if (chars.containsKey(chrCurr)) {

                // Den Eintrag ermitteln und dann die Haeufigkeit erhoehen
                chars.get(chrCurr).count++;

            } else { // Wenn das Zeichen noch nicht in der Hashmap enthalten ist

                // neuen Eintrag erstellen fuer das Zeichen
                Entry e = new Entry(String.valueOf(chrCurr), 0d);
                e.count = 1;

                // Eintrag fuer das Zeichen in der Hashmap erstellen und dann 
                // den Eintrag zur Liste mit saemtlichen Eintraegen hinzufuegen
                chars.put(chrCurr, e);
                entries.add(e);
            }
        }

        // Wahrscheinlichkeiten fuer die einzelnen Zeichen berechnen
        // dafuer alle Eintraege durhlaufen und mithilfe der Vorkommen die 
        // Wahrscheinlichkeit berechnen
        for (Entry e : entries) {
            e.value = (double) e.count / (double) strText.length();
        }

        // Liste der Eintraege anhand der Wahrscheinlichkeit sortieren
        Collections.sort(entries);

        // Huffmancode erstellen
        generateHuffmanCode();

        //TESTAUSGABE
        if (CODT1.bTestOutput) {
            for (Entry key : entries) {
                System.out.println(key);
            }
        }

    }

    // Eine Uebersicht ueber alle Eintraege erstellen
    public String getEncodeMap() {

        String strRet = "";

        for (Entry e : entries) {
            strRet += e.toString() + "\n";
        }

        return strRet;
    }

    // Einen uebergebenen Bitvektor mithilfe des Huffmanbaums
    // dekodieren und den resultierendem String zurueck geben.
    public String decodeBitVector(Vector<Boolean> vecCode) {

        String strRet = "";
        BinaryTree<Entry> node = treeRoot;  // Zeiger fuer das Durchlaufen des Huffmanbaumes auf die Wurzel setzen

        // Alle Bits des uebergebenen Binaervektors durchlaufen und den Huffmanbaum damit durchlaufen
        for (boolean b : vecCode) {

            // Wenn das bit 0 ist, zum linken Kindknoten wechseln 
            // wenn das Bit 1 ist, zum rechten Kindknoten wechseln
            if (b == false) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }

            // wenn ein Blatt erreicht wurde, also keine weiteren Kinder darunter existieren,
            // den Key zur rueckgabe hinzufügen 
            // und dann den Knotenzeiger wieder auf die Wurzel setzen
            if (node.isLeaf()) {
                strRet += node.getValue().key;
                node = treeRoot;
            }
        }

        return strRet;
    }

    // encoded den übergebenen String
    // Wandelt den uebergebenen String mithilfe der Hashmap in einen Binaervektor um
    public Vector<Boolean> encodeString(String strMsg) {

        // Rueckgabevektor
        Vector<Boolean> vecMsgCode = new Vector<>();

        // Alle Zeichen des Strings durchlaufen
        for (int i = 0; i < strMsg.length(); i++) {

            // aktuelles Zeichen ermittlen
            String chr = String.valueOf(strMsg.charAt(i));

            // Aus der Hashmap den zugehoerigen Binaervektor fuer das Zeichen ermitteln
            // und zur Rueckgabe hinzufuegen
            for (boolean b : encodeMap.get(chr)) {
                vecMsgCode.add(b);
            }
        }
        return vecMsgCode;
    }

    /* Interpretiert einen uebergebenen String als Char -> Wahrscheinlichkeit
    * 
     */
    public void parseInfSourceFromInput(String strInput) {

        // Map für die einzelnen Chars
        entries = new ArrayList<>();
        HashMap<String, Double> mapInfSource = new HashMap<>();

        // Alle Eintraege durchlaufen und parsen, getrennt wird an einem ;
        for (String strEntry : strInput.split(";")) {

            // Key und Value ermitteln
            String strKey = strEntry.split("->")[0];
            double dblVal = Double.valueOf(strEntry.split("->")[1]);

            // Wenn der Key noch nicht eingefuegt wurde und key und value 
            // gueltige Werte besitzen
            if (mapInfSource.get(strKey) == null && !strKey.equals("") && dblVal > 0) {

                // Eintrag zur Map hinzufuegen
                mapInfSource.put(strKey, dblVal);
                entries.add(new Entry(strKey, dblVal));

            }
        }

        // sortieren nach Wahrscheinlichkeit
        Collections.sort(entries);

        // Huffmancode erstellen aus den gefundenen Eintraegen
        generateHuffmanCode();

        //TESTAUSGABE
        if (CODT1.bTestOutput) {
            for (Entry key : entries) {
                System.out.println(key);
            }
        }
    }

    // Entropie fuer alle Zeichen ermitteln und zurueck geben
    public double getEntropy() {

        double dblRet = 0;

        for (Entry e : entries) {
            dblRet += e.getEntropy();
        }

        return dblRet;
    }

    // Durchschnittliche Laenge der Eintrage berechnen
    public double getAvgLength() {
        double dblRet = 0;

        // Alle Eintraege durchlaufen und die Wahrscheinlichkeiten mit der Laenge
        // des jeweilige Binaervektors multiplizieren
        for (Entry e : entries) {
            dblRet += e.value * e.code.size();
        }

        return dblRet;
    }

    /*
    * Erstellung des Huffmancodes
     */
    private void generateHuffmanCode() {

        // KnotenListe fuer das Zwischenspeichern der Elemente bei der Erstellung des Huffmancodes
        ArrayList<Entry> copy = new ArrayList<>();

        // Hashmap fuer fuer die Knoten anlegen 
        HashMap<String, BinaryTree<Entry>> treeMap = new HashMap<>();

        // Einträge durchlaufen
        for (Entry e : entries) {

            // Neuen Knoten fuer den Binaerbaum erstellen und den Zeiger im 
            // Element hinzufuegen
            e.node = new BinaryTree<>(e);

            // Den erstellten Knoten mit dem Key in die Hashmap einfuegen
            treeMap.put(e.key, e.node);

            // Dummyeintrag aus dem aktuellen erstellen, der nur fuer die Erstellung
            // des Baumes verwendet wird
            Entry cpy = new Entry(e.key, e.value);
            cpy.node = e.node;
            copy.add(cpy);
        }

        // Die Schleife arbeitet alle erstellten Knoten ab und verknuepft immer diejenigen 
        // mit der niedrigsten Wahrscheinlichkeit zu einem neuen Knoten, bis nur noch ein Knoten 
        // uebrig ist. Der Huffmanbaum wird von unten nach oben aufgebaut
        while (copy.size() > 1) {

            // Neuen Knoten und neues Element erstellen, dass die addierte Wahrscheinlichkeit besitzt
            treeRoot = new BinaryTree<>(
                    new Entry(copy.get(0).key + copy.get(1).key, copy.get(0).value + copy.get(1).value));

            // als Linkes Kind des neu erstellten Knotens wird der Eintrag mit der niedrigsten Wahrscheinlichkeit
            // das zweit niedrigste Element ist dann das rechte Kind
            treeRoot.setLeft(treeMap.get(copy.get(0).key));
            treeRoot.setRight(treeMap.get(copy.get(1).key));

            // Den verknuepften Knoten zur Knotenliste hinzufuegen
            treeMap.put(copy.get(0).key + copy.get(1).key, treeRoot);

            // Die beiden Eintraege fuer die Verknüpfung aus der temporaeren Liste
            // entfernen. Dann den Eintrag des verknüpften Knotens hinzufuegen
            copy.remove(1);
            copy.remove(0);
            copy.add(treeRoot.getValue());

            // Nach der Wahrscheinlichkeit sortieren
            Collections.sort(copy);

        }

        // Die Codewörter ermitteln und in den Entries eintragen
        calculateCode(treeRoot);
        generateEncodingMap(treeRoot);

        //TESTAUSGABE
        if (CODT1.bTestOutput) {
            System.out.println(treeRoot.toString());
        }
    }

    // Funktion, die rekursiv alle Knoten durchläuft und die Keys und zugehörifgen
    // Codes (bool-Vektoren) zu einer Hashmap hinzufügt, die für das codieren verwendet wird.
    private void generateEncodingMap(BinaryTree<Entry> root) {

        // Bei einem Blattknoten den key und bool Vektor in der Hashmap speichern
        // und dann die Funktion beenden
        if (root.isLeaf()) {
            encodeMap.put(root.getValue().key, root.getValue().code);
            return;
        }

        // Wenn ein Linker knoten existiert, hierfür die Funktion rekursiv aufrufen
        if (root.hasLeft()) {
            generateEncodingMap(root.getLeft());
        }

        // Wenn ein rechter knoten existiert, hierfür die Funktion rekursiv aufrufen
        if (root.hasRight()) {
            generateEncodingMap(root.getRight());
        }
    }

    // Startfunktion für das Rekursive durchlaufen der Blätter und das zusammensetzen der Codes
    private static BinaryTree<Entry> calculateCode(BinaryTree<Entry> root) {

        // Wenn es kein Blatt ist
        if (root.isInner()) {

            // Wenn es ein Linkes Blatt gibt
            if (root.hasLeft()) {

                // In allen Blattknoten unterhalb des linken Blattes ein false (0) hinzufügen
                // Sodass deren erstes Bit eine 0 enthält
                for (boolean b : root.getValue().code) {
                    root.getLeft().getValue().code.add(b);
                }
                root.getLeft().getValue().code.add(false);

                // Dann diese funktion für das Linke Blatt aufrufen
                calculateCode(root.getLeft());
            }

            // Analog zum linken Blatt, falls ein rechter Knoten existiert
            if (root.hasRight()) {
                for (boolean b : root.getValue().code) {
                    root.getRight().getValue().code.add(b);
                }
                root.getRight().getValue().code.add(true);

                calculateCode(root.getRight());
            }
        }
        return root;
    }

    // Funktion, die das übergebene bit in allen Codes der darunterliegenden Blattknoten 
    // hinzufügt. 
    private static void calculateCode(BinaryTree<Entry> root, boolean bAddBit) {

        // Wenn es kein Blatt ist, dann das Bit an die Kinder weitergeben
        // sofern sie existieren
        if (root.isInner()) {
            if (root.hasLeft()) {
                calculateCode(root.getLeft(), bAddBit);
            }
            if (root.hasRight()) {
                calculateCode(root.getRight(), bAddBit);
            }

            // Wenn es ein Blattknoten ist, dann das übergebene Bit dem eintrag hinzufügen
        } else {
            // Bit vorne an den Vektor anhängen
            root.getValue().code.add(bAddBit);
        }
    }

}
