package umt;

import java.util.*;

public class Main {
    /**
     *
     * prima data verificam daca se poate imparti lista in alte 2 liste,
     * astfel incat, cele 2 liste sa aiba aceeasi medie
     *
     * daca nu se poate, atunci reducem complexitatea d.p.d.v. al timpului de la O(sizeInitialList^3) la O(sizeInitialList)
     *
     * fie k,
     * trebuie sa existe un numar k ,astfel incat sizeInitialList%k=0, pentru a exista posibilitatea de a partitiona
     * numerele in 2 liste care sa aiba aceeasi medie
     *
     * exemplu 1:   fie sum=15, deci suma elementelor din A e 15, pt 15 exista k=3 si k=5
     *              pentru k=3 inseamna ca exista posibilitatea sa impartim lista corespunzator, prima lista avand suma 5, a doua lista avand suma 10, si de 2 ori mai multe elemente (nu stim sigur daca se poate, pentru ca trebuie sa verificam elementele din lista)
     *              pentru k=5 inseamna ca putem avea 2 liste unde sumele sa fie:   a) 3 12 (lista a doua va trebui sa aiba de 4 ori mai multe elemente)
     *                                                                              b) 6 6 (cele 2 liste ar trebui sa aiba aceeasi dimensiune)
     *
     * dupa ce impartim elementele in 2 liste:
     * mediaElementelor(lista1) == mediaElementelor(lista2)
     *
     * se observa ca: suma elementelor din lista initiala va avea aceeasi medie ca oricare din primele 2 liste
     *  (fie p numar real, lista initiala va avea de p ori mai multe elemente decat lista2 (sau 1) si suma va fi de p ori mai mare)
     *
     * deci avem: mediaElementelor(lista1)==mediaElementelor(listaInitiala)
     * ==>
     * sumaElem(lista1)/nrElem(list1)==sumaElem(listaInitiala)/nrElem(listaInitiala)    <==>
     * sumaElem(lista1)==sumaElem(listaInitiala)*nrElem(list1)/nrElem(listaInitiala)
     *
     * observam ca sumaElem(lista1) va fi intotdeauna, numar natural, iar membrul drept numar real,
     * asa ca in functia solutionExists, verificam daca: exista un caz in care membrul drept sa fie numar intreg,
     * daca nu exista, atunci returnam false, pentru ca problema nu are solutie (ajuta la complexitatea pentru timpul mediu)
     *
     * @param sum -> suma tuturor elementelor din A
     * @param sizeInitialList -> dimensiunea lui A
     * @return true     ,if we can solve the problem
     *         false    ,else
     */
    public static boolean solutionExists(int sum, int sizeInitialList) {
        for (int k = 1; k <= sizeInitialList / 2; k++)    // dimensiunea maxima a partitiei din stanga va fi sizeInitialList/2
            if (sum * k % sizeInitialList == 0)
                return true;
        return false;
    }

    /**
     *
     * facem o lista cu toate sumele posibile pentru 0,1,2,..,list.size()/2 elemente luate din list
     * si o notam:  List<Set<Integer>> sume;
     *
     * de exemplu : sume[i] va fi o lista care contine (o singura data) toate sumele posibile folosind oricare i elemente din list
     *
     * folosind formula : sumaElem(lista1)==sumaElem(listaInitiala)*nrElem(list1)/nrElem(listaInitiala)
     * stim ca: sume[i] contine toate valorile pentru sumaElem(lista1)
     *          sum contine sumaElem(listaInitiala)
     *          list.size reprezinta nrElem(listaInitiala)
     *          i reprezinta nrElem(list1) (unde i este linia de pe care luam sumele din i)
     *
     * timp :   O(n^3)
     * spatiu:  O(n^2)
     *
     * @param list  - lista initiala
     * @return  true    ,if problem can be solved
     *          false   ,else
     */
    public static boolean splitArraySameAverage(List<Integer> list) {
        int sum = list.stream().mapToInt(x -> x).sum();

        if (!solutionExists(sum, list.size()))
            return false;

        List<Set<Integer>> sume = new ArrayList<>();
        for (int i = 0; i <= list.size() / 2; i++)          // initializam sume
            sume.add(new HashSet<>());
        sume.get(0).add(0);

        for (Integer number : list)
            for (int i = list.size() / 2; i >= 1; i--) {    //populam sume
                for (Integer t : sume.get(i - 1)) {
                    sume.get(i).add(t + number);
                }
            }

        for (int i = 1; i <= list.size() / 2; i++) {        // aplicam formula
            int finalI = i;
            if (sum * i % list.size() == 0 && sume.get(i).stream().anyMatch(x -> x == (sum * finalI / list.size())))
                return true;
        }

        return false;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4,5));
        System.out.println(splitArraySameAverage(list));

    }
}