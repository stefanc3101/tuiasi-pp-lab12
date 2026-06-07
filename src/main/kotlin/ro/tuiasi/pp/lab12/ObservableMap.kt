package ro.tuiasi.pp.lab12

/**
 * Decorator pentru `MutableMap` care loghează fiecare operație `put`.
 *
 * Folosește delegarea Kotlin (`by`) pentru a implementa automat toate metodele
 * interfeței `MutableMap<K, V>` prin `inner`, suprascriind doar `put`.
 *
 * Exemplu de utilizare:
 * ```kotlin
 * val map = ObservableMap(mutableMapOf<String, Int>())
 * map["a"] = 10  // loghează modificarea
 * map["a"] = 20  // loghează modificarea cu valoarea veche
 * ```
 *
 * @param inner Map-ul intern care stochează efectiv datele
 * @param onPut Callback apelat la fiecare `put`. Primește (cheie, valoareVeche, valoareNouă).
 * Implicit: afișează la consolă.
 */
class ObservableMap<K, V>(
    private val inner: MutableMap<K, V>,
    private val onPut: (key: K, oldValue: V?, newValue: V) -> Unit = { cheie, vechi, nou ->
        println("[ObservableMap] Cheie='$cheie', Valoare veche=$vechi, Valoare nouă=$nou")
    }
) : MutableMap<K, V> by inner {

    /**
     * Adaugă sau actualizează o pereche cheie-valoare și loghează modificarea.
     *
     * Apelează callback-ul [onPut] cu cheia, valoarea veche (sau `null`) și valoarea nouă,
     * apoi delegă inserarea la [inner].
     *
     * @param key Cheia de inserat/actualizat
     * @param value Valoarea nouă
     * @return Valoarea anterioară asociată cheii, sau `null` dacă cheia era absentă
     */
    override fun put(key: K, value: V): V? {
        val valoareVeche = inner[key]
        onPut(key, valoareVeche, value)
        return inner.put(key, value)
    }
}