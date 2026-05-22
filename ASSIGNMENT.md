# Lab 12 — Kotlin Programare funcțională

## Obiective

- Aplicarea conceptelor de programare funcțională în Kotlin
- Compoziția funcțiilor cu `andThen`
- Utilizarea type alias-urilor (`typealias`) pentru claritate
- Delegarea în Kotlin cu `by` — pattern-ul decorator fără boilerplate
- Transformarea valorilor unui `HashMap` prin funcții de ordin superior

---

## Temă — Transformări funcționale pe HashMap cu delegați

### Context

Pornind de la exemplul curry/uncurry din curs, realizați o prelucrare similară
asupra elementelor unui `HashMap` utilizând delegați Kotlin (`by`).

---

### Cerințe de implementat

#### 1. Tipul `Transform<V>`

```kotlin
typealias Transform<V> = (V) -> V
```

Reprezintă o funcție care transformă o valoare de tip `V` în altă valoare de același tip.

#### 2. Funcția `andThen` (extensie infix)

```kotlin
infix fun <V> Transform<V>.andThen(g: Transform<V>): Transform<V>
```

Compune două transformări: întâi aplică `this`, apoi aplică `g` pe rezultat.

Exemplu:
```kotlin
val dublare: Transform<Int> = { it * 2 }
val adaugare10: Transform<Int> = { it + 10 }
val transformare = dublare andThen adaugare10
transformare(5)  // → (5 * 2) + 10 = 20
```

#### 3. Funcția `applyTransform`

```kotlin
fun applyTransform(map: HashMap<String, Int>, transform: Transform<Int>): HashMap<String, Int>
```

Returnează un nou `HashMap` în care fiecare valoare a fost transformată.
**Nu modifică map-ul original** (funcție pură).

#### 4. Object `TransformUtils`

Grupează `andThen` și `applyTransform` într-un object companion pentru organizare.

```kotlin
object TransformUtils {
    infix fun <V> Transform<V>.andThen(g: Transform<V>): Transform<V>
    fun applyTransform(map: HashMap<String, Int>, transform: Transform<Int>): HashMap<String, Int>
}
```

#### 5. Clasa `ObservableMap<K, V>`

```kotlin
class ObservableMap<K, V>(
    private val inner: MutableMap<K, V>
) : MutableMap<K, V> by inner
```

Implementează `MutableMap<K, V>` prin delegare la `inner` cu `by`.
Suprascrie `put` pentru a loga fiecare modificare:

```kotlin
override fun put(key: K, value: V): V? {
    println("[ObservableMap] Cheie='$key', Valoare veche=${inner[key]}, Valoare nouă=$value")
    return inner.put(key, value)
}
```

Opțional: acceptă un callback `onPut: (K, V) -> Unit` în loc de `println`.

---

## Criterii de evaluare

| Criteriu | Puncte |
|---------|--------|
| `typealias Transform<V>` definit corect | 10p |
| `andThen` compune corect două funcții | 25p |
| `applyTransform` transformă toate valorile, map original nemodificat | 30p |
| `ObservableMap` delega corect și loghează `put` | 35p |

**Total: 100 puncte**

---

## Rulare teste

```bash
gradle test
```
