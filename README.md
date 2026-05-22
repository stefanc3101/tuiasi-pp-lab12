# Lab 12 — Kotlin Programare funcțională

Laborator de programare în Kotlin cu accent pe funcții de ordin superior, compoziție și delegați.

## Structura proiectului

```
lab12/
├── src/
│   ├── main/kotlin/ro/tuiasi/pp/lab12/
│   │   ├── TransformUtils.kt    # typealias, andThen, applyTransform
│   │   └── ObservableMap.kt     # Delegat cu logging
│   └── test/kotlin/ro/tuiasi/pp/lab12/
│       ├── TransformUtilsTest.kt
│       └── ObservableMapTest.kt
├── .github/workflows/classroom.yml
├── build.gradle.kts
├── settings.gradle.kts
├── ASSIGNMENT.md
└── README.md
```

## Concepte cheie

- `typealias` — alias de tip pentru claritate în cod
- Funcții de ordin superior — funcții care primesc sau returnează funcții
- `infix fun` — notație infix pentru lizibilitate (`f andThen g`)
- Delegați cu `by` — implementare automată a unei interfețe prin delegare
- `MutableMap<K, V> by inner` — decorator fără boilerplate

## Exemplu rapid

```kotlin
import ro.tuiasi.pp.lab12.Transform
import ro.tuiasi.pp.lab12.TransformUtils.andThen
import ro.tuiasi.pp.lab12.TransformUtils.applyTransform

val dublu: Transform<Int> = { it * 2 }
val plus5: Transform<Int> = { it + 5 }
val combinat = dublu andThen plus5  // x -> x*2 + 5

val map = hashMapOf("a" to 3, "b" to 7)
val rezultat = applyTransform(map, combinat)
// rezultat: {"a" to 11, "b" to 19}
```

## Cum rulezi

```bash
gradle test
```

## Cerințe sistem

- JDK 21 (Temurin recomandat)
- Gradle 8.11+ (sau IntelliJ cu suport Gradle)

## Citește mai mult

Vezi [ASSIGNMENT.md](ASSIGNMENT.md) pentru cerințele complete.
