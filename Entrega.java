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
      return false; // TO DO
    }

    /*
     * Comprovau si la relació `rel` definida sobre `a` és d'equivalència. Si ho és, retornau el
     * cardinal del conjunt quocient de `a` sobre `rel`. Si no, retornau -1.
     *
     * Podeu soposar que `a` està ordenat de menor a major.
     */
    static int exercici2(int[] a, int[][] rel) {
      return 0; // TO DO
    }

    /*
     * Comprovau si la relació `rel` definida entre `a` i `b` és una funció.
     *
     * Podeu soposar que `a` i `b` estan ordenats de menor a major.
     */
    static boolean exercici3(int[] a, int[] b, int[][] rel) {
      return false; // TO DO
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
   * Aquest mètode `main` conté alguns exemples de paràmetres i dels resultats que haurien de donar
   * els exercicis. Podeu utilitzar-los de guia i també en podeu afegir d'altres (no els tendrem en
   * compte, però és molt recomanable).
   *
   * Podeu aprofitar el mètode `assertThat` per comprovar fàcilment que un valor sigui `true`.
   */
  public static void main(String[] args) {
    Tema1.tests();
    Tema2.tests();
  }

  /// Si b és cert, no fa res. Si b és fals, llança una excepció (AssertionError).
  static void assertThat(boolean b) {
    if (!b)
      throw new AssertionError();
  }
}

// vim: set textwidth=100 shiftwidth=2 expandtab :
