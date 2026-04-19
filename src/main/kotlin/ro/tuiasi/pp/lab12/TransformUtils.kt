package ro.tuiasi.pp.lab12

/**
 * Tip alias pentru o funcție de transformare care mapează un tip în același tip.
 *
 * Exemplu de utilizare:
 * ```kotlin
 * val dublu: Transform<Int> = { it * 2 }
 * val minus1: Transform<Int> = { it - 1 }
 * ```
 */
typealias Transform<V> = (V) -> V

/**
 * Utilitar pentru operații funcționale pe transformări și HashMap-uri.
 *
 * Oferă:
 * - [andThen] — compoziție de transformări (infix)
 * - [applyTransform] — aplicarea unei transformări pe valorile unui HashMap
 */
object TransformUtils {

    /**
     * Compune două transformări: `this` urmată de [g].
     *
     * Rezultatul este o nouă transformare care aplică întâi `this` pe valoare,
     * apoi aplică [g] pe rezultat.
     *
     * Exemplu:
     * ```kotlin
     * val dublu: Transform<Int> = { it * 2 }
     * val plus10: Transform<Int> = { it + 10 }
     * val dubluPlus10 = dublu andThen plus10
     * dubluPlus10(5)  // → (5 * 2) + 10 = 20
     * ```
     *
     * @param g Transformarea de aplicat după `this`
     * @return O nouă transformare compusă
     */
    infix fun <V> Transform<V>.andThen(g: Transform<V>): Transform<V> {
        // TODO("De implementat")
        // Returnați o lambda care:
        // 1. Aplică this pe argumentul primit: val intermediar = this(it)
        // 2. Aplică g pe intermediar: g(intermediar)
        // Sau mai concis: return { g(this(it)) }
        TODO("De implementat: returnează o funcție care aplică this și apoi g")
    }

    /**
     * Aplică [transform] pe fiecare valoare din [map] și returnează un HashMap nou.
     *
     * **Nu modifică map-ul original** — funcție pură.
     *
     * Exemplu:
     * ```kotlin
     * val map = hashMapOf("x" to 3, "y" to 5)
     * val dublu: Transform<Int> = { it * 2 }
     * applyTransform(map, dublu)  // → {"x" to 6, "y" to 10}
     * ```
     *
     * @param map HashMap-ul de intrare (nu este modificat)
     * @param transform Transformarea de aplicat pe valorile map-ului
     * @return Un nou HashMap cu valorile transformate
     */
    fun applyTransform(map: HashMap<String, Int>, transform: Transform<Int>): HashMap<String, Int> {
        // TODO("De implementat")
        // Creați un HashMap nou (nu modificați map-ul original!)
        // Iterați prin intrările map-ului: map.entries sau map.forEach
        // Pentru fiecare intrare (cheie, valoare), adăugați în noul map: cheie → transform(valoare)
        // Indiciu: puteți folosi map.mapValues { transform(it.value) } și HashMap(...)
        TODO("De implementat: returnează un nou HashMap cu fiecare valoare transformată")
    }
}
