package ro.tuiasi.pp.lab12

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class ObservableMapTest {

    /**
     * Creează un ObservableMap cu un callback care înregistrează toate modificările
     * pentru verificare în teste (fără a depinde de println).
     */
    private fun creeazaMapObservabil(): Pair<ObservableMap<String, Int>, MutableList<Triple<String, Int?, Int>>> {
        val log = mutableListOf<Triple<String, Int?, Int>>()
        val map = ObservableMap(mutableMapOf()) { cheie, vechi, nou ->
            log.add(Triple(cheie, vechi, nou))
        }
        return Pair(map, log)
    }

    @Test
    fun `put adauga valoarea corect`() {
        val (map, _) = creeazaMapObservabil()
        map["cheie1"] = 42

        assertEquals(42, map["cheie1"])
    }

    @Test
    fun `put apeleaza callback-ul`() {
        val (map, log) = creeazaMapObservabil()
        map["a"] = 10

        assertEquals(1, log.size, "Callback-ul ar trebui apelat o dată")
    }

    @Test
    fun `put loghează cheia corecta`() {
        val (map, log) = creeazaMapObservabil()
        map["cheie_test"] = 99

        assertEquals("cheie_test", log[0].first)
    }

    @Test
    fun `put loghează valoarea noua corecta`() {
        val (map, log) = creeazaMapObservabil()
        map["x"] = 77

        assertEquals(77, log[0].third)
    }

    @Test
    fun `put loghează valoarea veche null la prima inserare`() {
        val (map, log) = creeazaMapObservabil()
        map["nou"] = 5

        assertNull(log[0].second, "Valoarea veche ar trebui să fie null la prima inserare")
    }

    @Test
    fun `put loghează valoarea veche corecta la actualizare`() {
        val (map, log) = creeazaMapObservabil()
        map["k"] = 10
        map["k"] = 20

        assertEquals(10, log[1].second,
            "La a doua inserare, valoarea veche ar trebui să fie 10")
    }

    @Test
    fun `put suprascrie valoarea existenta`() {
        val (map, _) = creeazaMapObservabil()
        map["k"] = 10
        map["k"] = 20

        assertEquals(20, map["k"])
    }

    @Test
    fun `delegare get functioneaza`() {
        val (map, _) = creeazaMapObservabil()
        map["a"] = 5

        assertEquals(5, map["a"])
    }

    @Test
    fun `delegare containsKey functioneaza`() {
        val (map, _) = creeazaMapObservabil()
        map["prezent"] = 1

        assertTrue(map.containsKey("prezent"))
        assertFalse(map.containsKey("absent"))
    }

    @Test
    fun `delegare size functioneaza`() {
        val (map, _) = creeazaMapObservabil()
        assertEquals(0, map.size)
        map["a"] = 1
        map["b"] = 2
        assertEquals(2, map.size)
    }

    @Test
    fun `delegare remove functioneaza`() {
        val (map, _) = creeazaMapObservabil()
        map["a"] = 10
        map.remove("a")
        assertFalse(map.containsKey("a"))
    }

    @Test
    fun `multiple inserari sunt toate logate`() {
        val (map, log) = creeazaMapObservabil()
        map["a"] = 1
        map["b"] = 2
        map["c"] = 3

        assertEquals(3, log.size, "Toate cele 3 inserări ar trebui logate")
    }
}
