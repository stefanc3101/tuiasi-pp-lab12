package ro.tuiasi.pp.lab12

import ro.tuiasi.pp.lab12.TransformUtils.andThen
import ro.tuiasi.pp.lab12.TransformUtils.applyTransform
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotSame
import kotlin.test.assertTrue

class TransformUtilsTest {

    // ----- andThen -----

    @Test
    fun `andThen compune doua functii in ordine corecta`() {
        val dublu: Transform<Int> = { it * 2 }
        val plus10: Transform<Int> = { it + 10 }
        val compusa = dublu andThen plus10

        // dublu(5) = 10, plus10(10) = 20
        assertEquals(20, compusa(5))
    }

    @Test
    fun `andThen aplica intai this si apoi g`() {
        val rezultate = mutableListOf<String>()
        val f: Transform<Int> = { rezultate.add("f"); it + 1 }
        val g: Transform<Int> = { rezultate.add("g"); it * 3 }
        val fg = f andThen g

        fg(0)

        assertEquals(listOf("f", "g"), rezultate,
            "andThen trebuie să aplice mai întâi f, apoi g")
    }

    @Test
    fun `andThen cu identitate lasa valoarea neschimbata`() {
        val identitate: Transform<Int> = { it }
        val dublare: Transform<Int> = { it * 2 }

        val compusa1 = identitate andThen dublare
        val compusa2 = dublare andThen identitate

        assertEquals(dublare(7), compusa1(7))
        assertEquals(dublare(7), compusa2(7))
    }

    @Test
    fun `andThen poate fi inlantuit de mai multe ori`() {
        val plus1: Transform<Int> = { it + 1 }
        val ori2: Transform<Int> = { it * 2 }
        val minus3: Transform<Int> = { it - 3 }

        // (5 + 1) * 2 - 3 = 9
        val compusa = plus1 andThen ori2 andThen minus3
        assertEquals(9, compusa(5))
    }

    @Test
    fun `andThen functioneaza si pe String`() {
        val majuscule: Transform<String> = { it.uppercase() }
        val adaugaSuffix: Transform<String> = { "$it!" }

        val compusa = majuscule andThen adaugaSuffix
        assertEquals("KOTLIN!", compusa("kotlin"))
    }

    // ----- applyTransform -----

    @Test
    fun `applyTransform aplica transformarea pe toate valorile`() {
        val map = hashMapOf("a" to 3, "b" to 7, "c" to 1)
        val dublu: Transform<Int> = { it * 2 }

        val rezultat = applyTransform(map, dublu)

        assertEquals(6, rezultat["a"])
        assertEquals(14, rezultat["b"])
        assertEquals(2, rezultat["c"])
    }

    @Test
    fun `applyTransform nu modifica mapa originala`() {
        val original = hashMapOf("x" to 5)
        val plus100: Transform<Int> = { it + 100 }

        applyTransform(original, plus100)

        assertEquals(5, original["x"],
            "Map-ul original nu ar trebui modificat (funcție pură)")
    }

    @Test
    fun `applyTransform returneaza o noua mapa`() {
        val original = hashMapOf("k" to 1)
        val identitate: Transform<Int> = { it }

        val rezultat = applyTransform(original, identitate)

        assertNotSame(original, rezultat,
            "applyTransform ar trebui să returneze o nouă instanță")
    }

    @Test
    fun `applyTransform returneaza mapa goala pentru input gol`() {
        val map = HashMap<String, Int>()
        val dublu: Transform<Int> = { it * 2 }

        val rezultat = applyTransform(map, dublu)

        assertTrue(rezultat.isEmpty())
    }

    @Test
    fun `applyTransform pastreaza cheile`() {
        val map = hashMapOf("cheia1" to 10, "cheia2" to 20)
        val plus5: Transform<Int> = { it + 5 }

        val rezultat = applyTransform(map, plus5)

        assertTrue(rezultat.containsKey("cheia1"))
        assertTrue(rezultat.containsKey("cheia2"))
    }

    @Test
    fun `applyTransform cu transformare compusa andThen`() {
        val map = hashMapOf("n" to 4)
        val ori3: Transform<Int> = { it * 3 }
        val minus2: Transform<Int> = { it - 2 }
        val compusa = ori3 andThen minus2

        val rezultat = applyTransform(map, compusa)

        // 4 * 3 - 2 = 10
        assertEquals(10, rezultat["n"])
    }
}
