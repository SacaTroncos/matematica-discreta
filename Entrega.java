package practicadiscreta;

import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.Set;

/*
 * Aquesta entrega consisteix en implementar tots els mètodes annotats amb el comentari "// TO DO".
 *
 * L'avaluació consistirà en:
 *
 * - Si el codi no compila, la nota del grup serà de 0.
 *
 * - Principalment, el correcte funcionament de cada mètode (provant amb diferents entrades). Teniu
 *   alguns exemples al mètode `main`.
 *
 * - Tendrem en compte la neteja i organització del codi. Un estandard que podeu seguir és la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html.  Algunes
 *   consideracions importants: indentació i espaiat consistent, bona nomenclatura de variables,
 *   declarar les variables el més aprop possible al primer ús (és a dir, evitau blocs de
 *   declaracions). També convé utilitzar el for-each (for (int x : ...)) enlloc del clàssic (for
 *   (int i = 0; ...)) sempre que no necessiteu l'índex del recorregut.
 *
 * Per com està plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni mètodes de classes que no estiguin ja importades. El que sí podeu fer és definir
 * tots els mètodes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a màxim 3 persones, i necessitareu com a minim Java 8.
 * Per entregar, posau a continuació els vostres noms i entregau únicament aquest fitxer.
 * - Nom 1: Sergi Oliver Juárez
 * - Nom 2:
 * - Nom 3:
 *
 * L'entrega es farà a través d'una tasca a l'Aula Digital que obrirem abans de la data que se us
 * hagui comunicat i vos recomanam que treballeu amb un fork d'aquest repositori per seguir més
 * fàcilment les actualitzacions amb enunciats nous. Si no podeu visualitzar bé algun enunciat,
 * assegurau-vos de que el vostre editor de texte estigui configurat amb codificació UTF-8.
 */
public class PracticaDiscreta {
  /*
   * Aquí teniu els exercicis del Tema 1 (Lògica).
   *
   * Els mètodes reben de paràmetre l'univers (representat com un array) i els predicats adients
   * (per exemple, `Predicate<Integer> p`). Per avaluar aquest predicat, si `x` és un element de
   * l'univers, podeu fer-ho com `p.test(x)`, que té com resultat un booleà (true si `P(x)` és
   * cert). Els predicats de dues variables són de tipus `BiPredicate<Integer, Integer>` i
   * similarment s'avaluen com `p.test(x, y)`.
   *
   * En cada un d'aquests exercicis us demanam que donat l'univers i els predicats retorneu `true`
   * o `false` segons si la proposició donada és certa (suposau que l'univers és suficientment
   * petit com per poder provar tots els casos que faci falta).
   */
  static class Tema1 {
    /*
     * És cert que ∀x ∃!y. P(x) -> Q(x,y) ?
     * Es cierto que para todo x, existe un unico y, que si P(x) entonces Q(x,y)
     */
    static boolean exercici1(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
        //∀x (Recorremos el universo x)
        for (int x : universe) {
        //Si se cumple P(x)  
            if (p.test(x)) {
                //∃!y (solo existe uno, asi que cuando lo encontremos lo ponemos a true
                //y si se encuentra otro y esta en true significa que no se cumple)
                boolean found = false;
                //Recorremos el universo y
                for (int y : universe) {
                    //Si se cumple la condicion Q(x,y)
                    if (q.test(x, y)) {
                        if (found) {
                            // Ya se ha encontrado otro valor de y que cumple con la condición Q(x,y)
                            //por lo tanto return false
                            return false;
                        } else {
                            //Se pone a true para comprobar que sea el unico y que lo cumple
                            found = true;
                        }
                    }
                }
                if (!found) {
                    // No se ha encontrado ningún valor de y que cumpla con la condición Q(x,y)
                    return false;
                }
            }
        }
        //Si llega hasta aqui es que todas las condiciones se han cumplido
        //por lo que ∀x ∃!y. P(x) -> Q(x,y) se cumple
        return true;
    }

    /*
     * És cert que ∃!x ∀y. P(y) -> Q(x,y) ?
     * Es cierto que existe un solo x tal que, para todo y, si P(y) entonces Q(x,y)
     */
    static boolean exercici2(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
        //∃!x (solo existe uno, asi que cuando lo encontremos lo ponemos a true
        //y si se encuentra otro y esta en true significa que no se cumple)
        boolean  found = false;
        //Variable auxiliar para guardar el valor de la x que se cumple
        int auxX = 0;
        
        //Recorremos el universo de las x para encontrar ese unico valor
        //de x que hace que se cumpla
        for(int x : universe){
            if(p.test(x)){
                //Si esta en false, es que es la primera x en cumplir la condicion
                if(!found){
                    //Se pone a true para comprobar que sea el unico
                    found = true;
                    //Guardamos el valor de x
                    auxX = x;
                }else{
                    //Si se cumple pero se habia encontrado otro anteriormente
                    //significa que existe mas de un x por lo tanto no se cumple
                    return false;
                }
            }   
        }
        if (!found) {
            //Si no se ha encontrado ninguno tampoco se cumple
            return false;
        }

        //Recorremos todos valores del universo y y para cada uno se comprueba
        // que se cumpla las condiciones
        for(int y: universe){
            //Si se cumple en P y Q 
            // (∃!x ∀y. P(y) -> Q(x,y)) se cumple la condicion
            if(p.test(y) && q.test(auxX, y)){
                return true;
            }else if(!p.test(y) && !q.test(auxX, y))//Si no se cumple en P, Q no tendria que poder cumplirse                                    
            {                                             //porque (P(y) -> Q(x,y)) -> !(P(y))) v (Q(x,y))
                return true;
            }
        }
        
        //Si llega hasta aqui es que todas las condiciones no se han cumplido
        //por lo que ∃!x ∀y. P(y) -> Q(x,y) es falso
        return false;
    }

    /*
     * És cert que ∃x,y ∀z. P(x,z) ⊕ Q(y,z) ?
     * Es cierto que para algún x e y y para todo z, la proposición P(x,z) ⊕ Q(y,z) es verdadera.
     */
    static boolean exercici3(int[] universe, BiPredicate<Integer, Integer> p, BiPredicate<Integer, Integer> q) {
        // Recorremos el universo de las x
        for (int x : universe) {
            // Para cada x recorremos todo el universo de y
            for (int y : universe) {
                boolean seCumpleP = true;
                boolean seCumpleQ = true;
                // Para cada x e y se recorre todo el universo z
                for (int z : universe) {
                    // Verificar P(x,z) ⊕ Q(y,z)
                    if (p.test(x, z) == q.test(y, z)) {
                        seCumpleP = false;
                        seCumpleQ = false;
                        break; // Si no se cumple la proposición para algún z, salir del bucle
                    }
                }
                // Si la proposición se cumple para todos los valores de z 
                if (seCumpleP || seCumpleQ) {
                    return true;
                }
            }
        }
        // Si no se encuentra ninguna combinación de x e y que cumpla 
        //∃x,y ∀z. P(x,z) ⊕ Q(y,z) entonces es falso
        return false; 
    }

    /*
     * És cert que (∀x. P(x)) -> (∀x. Q(x)) ?
     * Para todo x, si P(x) es verdadero, entonces Q(x) también es verdadero
     */
    static boolean exercici4(int[] universe, Predicate<Integer> p, Predicate<Integer> q) {
        boolean seCumpleP = true;
        boolean seCumpleQ = true;
        
        //Recorremos el univero de las x
        for(int x : universe){
            //Se comprueba si para algun x P(x) no se cumple
            if(!p.test(x)){
                seCumpleP = false;
            }
            //Se comprueba si para algun x P(x) no se cumple
            if(!q.test(x)){
                seCumpleQ = false;
            }
        }
        
        //Si se cumple en P y Q 
        // (∀x. P(x)) -> (∀x. Q(x)) se cumple la condicion
        if(seCumpleP && seCumpleQ){
            return true;
        }else if(!seCumpleP && !seCumpleQ)//Si no se cumple en P, Q no tendria que poder cumplirse                                    
        {                                 //porque ((∀x. P(x)) -> (∀x. Q(x)) -> !(∀x. P(x)) v (∀x. Q(x))
            return true;
        }
        //Si no se cumple las condiciones anteriores entonces
        //(∀x. P(x)) -> (∀x. Q(x)) no se cumple
        return false;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // ∀x ∃!y. P(x) -> Q(x,y) ?

      assertThat(
          exercici1(
              new int[] { 2, 3, 5, 6 },
              x -> x != 4,
              (x, y) -> x == y
          )
      );

      assertThat(
          !exercici1(
              new int[] { -2, -1, 0, 1, 2, 3 },
              x -> x != 0,
              (x, y) -> x * y == 1
          )
      );

      // Exercici 2
      // ∃!x ∀y. P(y) -> Q(x,y) ?

      assertThat(
          exercici2(
              new int[] { -1, 1, 2, 3, 4 },
              y -> y <= 0,
              (x, y) -> x == -y
          )
      );

      assertThat(
          !exercici2(
              new int[] { -2, -1, 1, 2, 3, 4 },
              y -> y < 0,
              (x, y) -> x * y == 1
          )
      );

      // Exercici 3
      // ∃x,y ∀z. P(x,z) ⊕ Q(y,z) ?

      assertThat(
          exercici3(
              new int[] { 2, 3, 4, 5, 6, 7, 8 },
              (x, z) -> z % x == 0,
              (y, z) -> z % y == 1
          )
      );

      assertThat(
          !exercici3(
              new int[] { 2, 3 },
              (x, z) -> z % x == 1,
              (y, z) -> z % y == 1
          )
      );

      // Exercici 4
      // (∀x. P(x)) -> (∀x. Q(x)) ?

      assertThat(
          exercici4(
              new int[] { 0, 1, 2, 3, 4, 5, 8, 9, 16 },
              x -> x % 2 == 0, // x és múltiple de 2
              x -> x % 4 == 0 // x és múltiple de 4
          )
      );

      assertThat(
          !exercici4(
              new int[] { 0, 2, 4, 6, 8, 16 },
              x -> x % 2 == 0, // x és múltiple de 2
              x -> x % 4 == 0 // x és múltiple de 4
          )
      );
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 2 (Conjunts).
   *
   * Per senzillesa tractarem els conjunts com arrays (sense elements repetits). Per tant, un
   * conjunt de conjunts d'enters tendrà tipus int[][].
   *
   * Les relacions també les representarem com arrays de dues dimensions, on la segona dimensió
   * només té dos elements. Per exemple
   *   int[][] rel = {{0,0}, {1,1}, {0,1}, {2,2}};
   * i també donarem el conjunt on està definida, per exemple
   *   int[] a = {0,1,2};
   *
   * Les funcions f : A -> B (on A i B son subconjunts dels enters) les representam donant el domini
   * int[] a, el codomini int[] b, i f un objecte de tipus Function<Integer, Integer> que podeu
   * avaluar com f.apply(x) (on x és d'a i el resultat f.apply(x) és de b).
   */
  static class Tema2 {
    /*
     * Comprovau si la relació `rel` definida sobre `a` és d'equivalència.
     *
     * Podeu soposar que `a` està ordenat de menor a major.
     */
    static boolean exercici1(int[] a, int[][] rel) {
       //Para que sea una relacion de equivalencia tiene que cumplir que sea
        // Reflexiva, simetrica y transitiva
        
        //Comprobamos si es reflexiva(aRa) para cada elemnto de tiene que cumplirse que
        // la relacion aRa se cumpla, es decir que haya un  subconjunto (a,a) para cada a
        
        //Recorremos el conjunto a
        for (int x : a) {
            //miramos que para cada uno de los elementos haya un subconjunto que 
            //haga que se cumpla la relacion
            boolean esReflexiva = false;
            //Recorre cada subconjunto
            for (int[] subConjunto : rel) {
              //Si se cumple la relacion aRa
              if (subConjunto[0] == x && subConjunto[1] == x) {
                esReflexiva = true;//cumple con la propiedad de reflexividad y pasa a comprobar al siguiente
                break;
              }
            }
            if (!esReflexiva) {
              return false; // No cumple con la propiedad de reflexividad
            }
        }
        
        //Comprobamos si es Simetrica, es decir si hay un subconjunto aRb tiene 
        // que haber otor subconjunto bRa
        
        //Recorremos cada subconjunto
        for (int[] subConjunto : rel) {
          boolean esSimetrico = false;
          //Para cada subconjunto recorremos todos los subconjuntos para comprobar si esta su simetrico
          for (int[] subConjuntoSimetrico : rel) {
            //Si se cumple la relacion
            if (subConjunto[0] == subConjuntoSimetrico[1] && subConjunto[1] == subConjuntoSimetrico[0]) {
              esSimetrico = true;//cumple con la propiedad simetrica y pasa a comprobar al siguiente subconjunto
              break;
            }
          }
          if (!esSimetrico) {
            return false; // No cumple con la propiedad de simetría
          }
        }
        
        //Comprobamos si se cumple la transitividad, aRb y bRc --> aRc
        
        //recorremos cada subconjunto
        for (int[] subConjunto1 : rel) {
          //Para cada subconjunto recorremos los subconjuntos otra vez
          for (int[] subConjunto2 : rel) {
            //Comprobamos que aRb y bRc
            if (subConjunto1[1] == subConjunto2[0]) {
              boolean esTransitivo = false;
              //Recorremos por tercera vez los subconjuntos
              for (int[] subConjunto3 : rel) {
                //Y vemos is se cumple aRc
                if (subConjunto1[0] == subConjunto3[0] && subConjunto2[1] == subConjunto3[1]) {
                  esTransitivo = true;//cumple con la propiedad transitiva y pasa a comprobar al siguiente subconjunto
                  break;
                }
              }
              if (!esTransitivo) {
                return false; // No cumple con la propiedad de transitividad
              }
            }
          }
        }
        
        return true; // Si llega hasta aqui cumple con todas las propiedades, es una relación de equivalencia
    }
    

    /*
     * Comprovau si la relació `rel` definida sobre `a` és d'equivalència. Si ho és, retornau el
     * cardinal del conjunt quocient de `a` sobre `rel`. Si no, retornau -1.
     *
     * Podeu soposar que `a` està ordenat de menor a major.
     */
    static int exercici2(int[] a, int[][] rel) {
        //Si es equivalente
       if (exercici1(a, rel)) {
        // Calcular cardinal del conjunto cociente
        int cardinal = 0;
        boolean[] comprobado = new boolean[a.length]; // Marcar los elementos ya visitados
        
        //Hace un bucle con tantas iteraciones como longitud del conjunto a
        for (int i = 0; i < a.length; i++) {
          //Si no hemos comprobado esa posicion
          if (!comprobado[i]) {
            comprobado[i] = true; // Marcar el elemento como visitado
            cardinal++;

            // Encontrar todos los elementos relacionados y marcarlos como visitados
            for (int j = i + 1; j < a.length; j++) {
              if (!comprobado[j] && hayRelacion(a[i], a[j], rel)) {
                comprobado[j] = true; // Marcar el elemento como visitado
              }
            }
          }
        }
        return cardinal;
      } else {
        return -1;
      }
    }
    
    private static boolean hayRelacion(int x, int y, int[][] rel) {
        //recorremos los subconjuntos
        for (int[] subConjunto : rel) {
          // Si encuentra un subconjunto donde x está relacionado con y o y está relacionado con x
          if (subConjunto[0] == x && subConjunto[1] == y) {
            return true;//indicando que x e y pertenecen a la misma clase de equivalencia
          }
        }
        return false;
    }
    
    /*
     * Comprovau si la relació `rel` definida entre `a` i `b` és una funció.
     *
     * Podeu soposar que `a` i `b` estan ordenats de menor a major.
     */
    
    static boolean exercici3(int[] a, int[] b, int[][] rel) {
        //Recorremos los subconjuntos de la Relacion
        for (int[] subConjunto : rel) {
            //Primero miramos si los elementos estan en el conjunto
            if (!estaEnElConjunto(subConjunto[0], a) || !estaEnElConjunto(subConjunto[1], b)) {
                return false; //si al menos uno no esta ya no se cumple
            }

            //para verificar si el primer elemento de cada par en rel es único. 
            //Si encuentra otro par con el mismo primer elemento, la relación no puede ser una función y se devuelve false.
            int contador = 0;
            //Recorremos los subconjuntos
            for (int[] otroSubConjunto : rel) {
                //Si el primer el elemento esta repetido no es una funcion
                if (subConjunto[0] == otroSubConjunto[0]) {
                    //Cuanta el numero de veces que se repiten los elementos en primera
                    contador++;
                    if (contador > 1) {
                        return false; // Hay otro par con el mismo primer elemento
                    }
                }
            }
        }

        return true; // La relación cumple con las propiedades de una función
      }

      static boolean estaEnElConjunto(int elemento, int[] conjunto) {
        //Recorremos cada elemento del conjunto
        for (int x : conjunto) {
            
            if (elemento == x) {
              return true; // El elemento está en el conjunto
            }
        }
        return false; // El elemento no está en el conjunto
      }


    /*
     * Suposau que `f` és una funció amb domini `dom` i codomini `codom`.  Retornau:
     * - Si és exhaustiva, el màxim cardinal de l'antiimatge de cada element de `codom`.
     * - Si no, si és injectiva, el cardinal de l'imatge de `f` menys el cardinal de `codom`.
     * - En qualsevol altre cas, retornau 0.
     *
     * Podeu suposar que `dom` i `codom` estàn ordenats de menor a major.
     */
    static int exercici4(int[] dom, int[] codom, Function<Integer, Integer> f) {
      return -1; // TO DO
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // `rel` és d'equivalencia?

      assertThat(
          exercici1(
              new int[] { 0, 1, 2, 3 },
              new int[][] { {0, 0}, {1, 1}, {2, 2}, {3, 3}, {1, 3}, {3, 1} }
          )
      );

      assertThat(
          !exercici1(
              new int[] { 0, 1, 2, 3 },
              new int[][] { {0, 0}, {1, 1}, {2, 2}, {3, 3}, {1, 2}, {1, 3}, {2, 1}, {3, 1} }
          )
      );

      // Exercici 2
      // si `rel` és d'equivalència, quants d'elements té el seu quocient?

      final int[] int09 = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

      assertThat(
          exercici2(
            int09,
            generateRel(int09, int09, (x, y) -> x % 3 == y % 3)
          )
          == 3
      );

      assertThat(
          exercici2(
              new int[] { 1, 2, 3 },
              new int[][] { {1, 1}, {2, 2} }
          )
          == -1
      );

      // Exercici 3
      // `rel` és una funció?

      final int[] int05 = { 0, 1, 2, 3, 4, 5 };

      assertThat(
          exercici3(
            int05,
            int09,
            generateRel(int05, int09, (x, y) -> x == y)
          )
      );

      assertThat(
          !exercici3(
            int05,
            int09,
            generateRel(int05, int09, (x, y) -> x == y/2)
          )
      );

      // Exercici 4
      // el major |f^-1(y)| de cada y de `codom` si f és exhaustiva
      // sino, |im f| - |codom| si és injectiva
      // sino, 0

      assertThat(
          exercici4(
            int09,
            int05,
            x -> x / 4
          )
          == 8
      );

      assertThat(
          exercici4(
            int05,
            int09,
            x -> x + 3
          )
          == int05.length - int09.length
      );

      assertThat(
          exercici4(
            int05,
            int05,
            x -> (x + 3) % 6
          )
          == 1
      );
    }

    /// Genera un array int[][] amb els elements {a, b} (a de as, b de bs) que satisfàn pred.test(a, b)
    static int[][] generateRel(int[] as, int[] bs, BiPredicate<Integer, Integer> pred) {
      ArrayList<int[]> rel = new ArrayList<>();

      for (int a : as) {
        for (int b : bs) {
          if (pred.test(a, b)) {
            rel.add(new int[] { a, b });
          }
        }
      }

      return rel.toArray(new int[][] {});
    }
  }
  
  /*
   * Aquí teniu els exercicis del Tema 3 (Grafs).
   *
   * Donarem els grafs en forma de diccionari d'adjacència, és a dir, un graf serà un array
   * on cada element i-èssim serà un array ordenat que contendrà els índexos dels vèrtexos adjacents
   * al i-èssim vèrtex. Per exemple, el graf cicle C_3 vendria donat per
   *
   *  int[][] g = {{1,2}, {0,2}, {0,1}}  (no dirigit: v0 -> {v1, v2}, v1 -> {v0, v2}, v2 -> {v0,v1})
   *  int[][] g = {{1}, {2}, {0}}        (dirigit: v0 -> {v1}, v1 -> {v2}, v2 -> {v0})
   *
   * Podeu suposar que cap dels grafs té llaços.
   */
  static class Tema3 {
    /*
     * Retornau l'ordre menys la mida del graf (no dirigit).
     */
    static int exercici1(int[][] g) {
        int numeroVertices = g.length; // Obtenemos el número de vértices (ORDEN)
        int tamaño = 0; // Inicializamos el tamaño del grafo
        
        //Recorremos el grafo
        for (int i = 0; i < g.length; i++) {
          tamaño += g[i].length; // Sumamos el número de vértices adyacentes a cada vértice
        }

        tamaño /= 2; // Dividimos entre 2 ya que cada arista se cuenta dos veces en un grafo no dirigido

        return numeroVertices - tamaño; // Retornamos la diferencia entre el orden y el tamaño
    }
    /*
     * Suposau que el graf (no dirigit) és connex. És bipartit?
     */
    static boolean exercici2(int[][] g) {
      return false; // TO DO
    }
    /*
     * Suposau que el graf és un DAG. Retornau el nombre de descendents amb grau de sortida 0 del
     * vèrtex i-èssim.
     */
    static int exercici3(int[][] g, int i) {
      return -1; // TO DO
    }

    /*
     * Donat un arbre arrelat (dirigit, suposau que l'arrel es el vèrtex 0), trobau-ne el diàmetre.
     * Suposau que totes les arestes tenen pes 1.
     * Donat un arbre arrelat (dirigit, suposau que l'arrel es el vèrtex 0), trobau-ne el diàmetre
     * del graf subjacent. Suposau que totes les arestes tenen pes 1.
     */
    static int exercici4(int[][] g) {
      return -1; // TO DO
    }
    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      final int[][] undirectedK6 = {
        { 1, 2, 3, 4, 5 },
        { 0, 2, 3, 4, 5 },
        { 0, 1, 3, 4, 5 },
        { 0, 1, 2, 4, 5 },
        { 0, 1, 2, 3, 5 },
        { 0, 1, 2, 3, 4 },
      };
      /*
         1
      4  0  2
         3
      */
      final int[][] undirectedW4 = {
        { 1, 2, 3, 4 },
        { 0, 2, 4 },
        { 0, 1, 3 },
        { 0, 2, 4 },
        { 0, 1, 3 },
      };
      // 0, 1, 2 | 3, 4
      final int[][] undirectedK23 = {
        { 3, 4 },
        { 3, 4 },
        { 3, 4 },
        { 0, 1, 2 },
        { 0, 1, 2 },
      };
      /*
             7
             0
           1   2
             3   8
             4
           5   6
      */
      final int[][] directedG1 = {
        { 1, 2 }, // 0
        { 3 },    // 1
        { 3, 8 }, // 2
        { 4 },    // 3
        { 5, 6 }, // 4
        {},       // 5
        {},       // 6
        { 0 },    // 7
        {},
      };
      /*
              0
         1    2     3
            4   5   6
           7 8
      */
      final int[][] directedRTree1 = {
        { 1, 2, 3 }, // 0 = r
        {},          // 1
        { 4, 5 },    // 2
        { 6 },       // 3
        { 7, 8 },    // 4
        {},          // 5
        {},          // 6
        {},          // 7
        {},          // 8
      };
      /*
            0
            1
         2     3
             4   5
                6  7
      */
      final int[][] directedRTree2 = {
        { 1 },
        { 2, 3 },
        {},
        { 4, 5 },
        {},
        { 6, 7 },
        {},
        {},
      };
      assertThat(exercici1(undirectedK6) == 6 - 5*6/2);
      assertThat(exercici1(undirectedW4) == 5 - 2*4);
      assertThat(exercici2(undirectedK23));
      assertThat(!exercici2(undirectedK6));
      assertThat(exercici3(directedG1, 0) == 3);
      assertThat(exercici3(directedRTree1, 2) == 3);
      assertThat(exercici4(directedRTree1) == 5);
      assertThat(exercici4(directedRTree2) == 4);
    }
  }

  /*
   * Aquest mètode `main` conté alguns exemples de paràmetres i dels resultats que haurien de donar
   * els exercicis. Podeu utilitzar-los de guia i també en podeu afegir d'altres (no els tendrem en
   * compte, però és molt recomanable).
   *
   * Podeu aprofitar el mètode `assertThat` per comprovar fàcilment que un valor sigui `true`.
   */
  public static void main(String[] args) {
    Tema1.tests();
    Tema2.tests();
    Tema3.tests();
  }

  /// Si b és cert, no fa res. Si b és fals, llança una excepció (AssertionError).
  static void assertThat(boolean b) {
    if (!b)
      throw new AssertionError();
  }
}

// vim: set textwidth=100 shiftwidth=2 expandtab :
