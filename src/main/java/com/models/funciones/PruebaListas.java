package com.models.funciones;

import java.util.*;


    /*
    De las siguientes colecciones cargandolas con millones de datos
List: ArrayList vs Vector vs Deque
Set: HashSet vs LinkedHashSet vs TreeSet
Map: HashMap vs LinkedHashMap vs TreeMap
 	se deberá comparar
  Tiempos de Carga
  Tiempo de búsqueda del último elemento
*/

public class PruebaListas<T> {
    private T ultimoElemento;
    private ArrayList<T> listArray = new ArrayList<>();
    private Vector<T> listVector = new Vector<>();
    private Deque<T> listDeque = new ArrayDeque<>();

    private HashSet<T> setHash = new HashSet<>();
    private LinkedHashSet<T> setLinkedHash = new LinkedHashSet<>();
    private TreeSet<T> setTree = new TreeSet<>();

    private HashMap<Integer, T> mapHash = new HashMap<>();
    private LinkedHashMap<Integer, T> mapLinkedHash = new LinkedHashMap<>();
    private TreeMap<Integer, T> mapTree = new TreeMap<>();

    public void pruebaListas(List<T> listas) {
        // Transformar lista a arreglo
        ultimoElemento = listas.get(listas.size() - 1);
        T[] arreglo = this.transformarListToArreglo(listas);

        System.out.println("Cargas ------");
        System.out.println("----- Listas ------");
        System.out.println(
                "ArrayList: " + this.tiempoCargarArrayList(arreglo) + "s, " +
                        "Vector: " + this.tiempoCargarVector(arreglo) + "s, " +
                        "Deque: " + this.tiempoCargarDeque(arreglo) + "s"
        );

        System.out.println("------ Set ----------");
        System.out.println(
                "HashSet: " + this.tiempoCargarHashSet(arreglo) + "s, " +
                        "LinkedHashSet: " + this.tiempoCargarLinkedHashSet(arreglo) + "s, " +
                        "TreeSet: " + this.tiempoCargarTreeSet(arreglo) + "s"
        );



        System.out.println("---------- Map --------------");
        System.out.println(
                "HashMap: " + this.tiempoCargarHashMap(arreglo) + "s, " +
                        "LinkedHashMap: " + this.tiempoCargarLinkedHashMap(arreglo) + "s, " +
                        "TreeMap: " + this.tiempoCargarTreeMap(arreglo) + "s"
        );

        System.out.println("Búsquedas ------");
        System.out.println("----- Listas ------");
        System.out.println(
                "ArrayList: " + this.tiempoBusquedaArrayList() + "s, " +
                        "Vector: " + this.tiempoBusquedaVector() + "s, " +
                        "Deque: " + this.tiempoBusquedaDeque() + "s"
        );

        System.out.println("------ Set ----------");
        System.out.println(
                "HashSet: " + this.tiempoBusquedaHashSet() + "s, " +
                        "LinkedHashSet: " + this.tiempoBusquedaLinkedHashSet() + "s, " +
                        "TreeSet: " + this.tiempoBusquedaTreeSet(arreglo) + "s"
        );

        System.out.println("---------- Map --------------");
        System.out.println(
                "HashMap: " + this.tiempoBusquedaHashMap() + "s, " +
                        "LinkedHashMap: " + this.tiempoBusquedaLinkedHashMap() + "s, " +
                        "TreeMap: " + this.tiempoBusquedaTreeMap() + "s"
        );
    }



    public  <T> T[] transformarListToArreglo(List<T> lista) {

        if (lista == null || lista.isEmpty()) {
            return null;
        }

        // Crear un array del mismo tipo que el primer elemento de la lista
        T[] arreglo = (T[]) java.lang.reflect.Array.newInstance(
                lista.get(0).getClass(), lista.size());

        return lista.toArray(arreglo);
    }



    public double tiempoCargarArrayList(T[] arreglo){
        listArray.clear();
        long inicio = System.nanoTime();
        for (int i = 0; i<arreglo.length; i++){
         listArray.add(arreglo[i]);
     }
        long fin = System.nanoTime();
        long tiempoTranscurrido = fin - inicio;
        double tiempoEnSegundos = tiempoTranscurrido / 1_000_000_000.0;
        return tiempoEnSegundos;

    }
    public double tiempoCargarVector(T[] arreglo) {
        listVector.clear();
        long inicio = System.nanoTime();
        for (int i = 0; i < arreglo.length; i++) {
            listVector.add(arreglo[i]);
        }
        long fin = System.nanoTime();
        long tiempoTranscurrido = fin - inicio;
        return tiempoTranscurrido / 1_000_000_000.0;
    }

    public double tiempoCargarDeque(T[] arreglo) {
        listDeque.clear();
        long inicio = System.nanoTime();
        for (int i = 0; i < arreglo.length; i++) {
            listDeque.add(arreglo[i]);
        }
        long fin = System.nanoTime();
        long tiempoTranscurrido = fin - inicio;
        return tiempoTranscurrido / 1_000_000_000.0;
    }

    public double tiempoCargarHashSet(T[] arreglo) {
        setHash.clear();
        long inicio = System.nanoTime();
        for (int i = 0; i < arreglo.length; i++) {
            setHash.add(arreglo[i]);
        }
        long fin = System.nanoTime();
        long tiempoTranscurrido = fin - inicio;
        return tiempoTranscurrido / 1_000_000_000.0;
    }

    public double tiempoCargarLinkedHashSet(T[] arreglo) {
        setLinkedHash.clear();
        long inicio = System.nanoTime();
        for (int i = 0; i < arreglo.length; i++) {
            setLinkedHash.add(arreglo[i]);
        }
        long fin = System.nanoTime();
        long tiempoTranscurrido = fin - inicio;
        return tiempoTranscurrido / 1_000_000_000.0;
    }

    public double tiempoCargarTreeSet(T[] arreglo) {
        setTree.clear();
        long inicio = System.nanoTime();
        for (int i = 0; i < arreglo.length; i++) {
            setTree.add(arreglo[i]);
        }
        long fin = System.nanoTime();
        long tiempoTranscurrido = fin - inicio;
        return tiempoTranscurrido / 1_000_000_000.0;
    }

    public double tiempoCargarHashMap(T[] arreglo) {
        mapHash.clear();
        long inicio = System.nanoTime();
        for (int i = 0; i < arreglo.length; i++) {
            mapHash.put(i, arreglo[i]);
        }
        long fin = System.nanoTime();
        long tiempoTranscurrido = fin - inicio;
        return tiempoTranscurrido / 1_000_000_000.0;
    }

    public double tiempoCargarLinkedHashMap(T[] arreglo) {
        mapLinkedHash.clear();
        long inicio = System.nanoTime();
        for (int i = 0; i < arreglo.length; i++) {
            mapLinkedHash.put(i, arreglo[i]);
        }
        long fin = System.nanoTime();
        long tiempoTranscurrido = fin - inicio;
        return tiempoTranscurrido / 1_000_000_000.0;
    }

    public double tiempoCargarTreeMap(T[] arreglo) {
        mapTree.clear();
        long inicio = System.nanoTime();
        for (int i = 0; i < arreglo.length; i++) {
            mapTree.put(i, arreglo[i]);
        }
        long fin = System.nanoTime();
        long tiempoTranscurrido = fin - inicio;
        return tiempoTranscurrido / 1_000_000_000.0;
    }


//********************************** busqueda

/*    List: ArrayList vs Vector vs Deque
    Set: HashSet vs LinkedHashSet vs TreeSet
    Map: HashMap vs LinkedHashMap vs TreeMap
    se deberá comparar
    Tiempos de Carga
    Tiempo de búsqueda del último elemento
  */
public double tiempoBusquedaArrayList(){
    long inicio = System.nanoTime();
    listArray.contains(ultimoElemento);
    long fin = System.nanoTime();
    long tiempoTranscurrido = fin - inicio;
    double tiempoEnSegundos = tiempoTranscurrido / 1_000_000_000.0;
    return tiempoEnSegundos;
}

    public double tiempoBusquedaVector() {
        long inicio = System.nanoTime();
        listVector.contains(ultimoElemento);
        long fin = System.nanoTime();
        long tiempoTranscurrido = fin - inicio;
        return tiempoTranscurrido / 1_000_000_000.0;
    }

    public double tiempoBusquedaDeque() {
        long inicio = System.nanoTime();
        listDeque.contains(ultimoElemento);
        long fin = System.nanoTime();
        long tiempoTranscurrido = fin - inicio;
        return tiempoTranscurrido / 1_000_000_000.0;
    }

    public double tiempoBusquedaHashSet() {
        long inicio = System.nanoTime();
        setHash.contains(ultimoElemento);
        long fin = System.nanoTime();
        long tiempoTranscurrido = fin - inicio;
        return tiempoTranscurrido / 1_000_000_000.0;
    }

    public double tiempoBusquedaLinkedHashSet() {
        long inicio = System.nanoTime();
        setLinkedHash.contains(ultimoElemento);
        long fin = System.nanoTime();
        long tiempoTranscurrido = fin - inicio;
        return tiempoTranscurrido / 1_000_000_000.0;
    }

    public double tiempoBusquedaTreeSet(T[] arreglo) {
        setTree.clear();
    for (int i = 0; i < arreglo.length; i++) {
            setTree.add(arreglo[i]);
        }
        long inicio = System.nanoTime();
        setTree.contains(ultimoElemento);
        long fin = System.nanoTime();
        long tiempoTranscurrido = fin - inicio;
        return tiempoTranscurrido / 1_000_000_000.0;
    }

    public double tiempoBusquedaHashMap() {
        long inicio = System.nanoTime();
        mapHash.containsValue(ultimoElemento);
        long fin = System.nanoTime();
        long tiempoTranscurrido = fin - inicio;
        return tiempoTranscurrido / 1_000_000_000.0;
    }

    public double tiempoBusquedaLinkedHashMap() {
        long inicio = System.nanoTime();
        mapLinkedHash.containsValue(ultimoElemento);
        long fin = System.nanoTime();
        long tiempoTranscurrido = fin - inicio;
        return tiempoTranscurrido / 1_000_000_000.0;
    }

    public double tiempoBusquedaTreeMap() {
        long inicio = System.nanoTime();
        mapTree.containsValue(ultimoElemento);
        long fin = System.nanoTime();
        long tiempoTranscurrido = fin - inicio;
        return tiempoTranscurrido / 1_000_000_000.0;
    }

}
