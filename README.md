experimental Java List API:

```java
String sentence = "The quick brown fox jumps over the lazy dog.";
String[] words = sentence.split("\\W+");

ListX.fromArray(words)
        .filterNot(w -> w.isEmpty())
        .map(w -> w.toLowerCase())
        .groupBy(w -> w)
        .mapValues(list -> list.size())
        .sortBy((w, count) -> count).reverse()
        .take(5)
        .forEach((k, v) -> System.out.println(v + "\t" + k));
```


similar projects:
[jOOλ](https://github.com/jOOQ/jOOL)
[Cyclops](https://github.com/aol/cyclops)

