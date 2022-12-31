package experiment;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// ≈ UnmodifiableMap<K,V> & UnmodifiableList<Entry<K,V>>
public final class MapX<K, V> extends MapXAsMap<K, V> {

	static <K, V> MapX<K, V> fromLinkedHashMap(LinkedHashMap<K, V> map) { return new MapX<>(map); }
	static <K, V> MapX<K, V> fromStream(Stream<Map.Entry<K, V>> stream) {
		return new MapX<>(stream.collect(Collectors.toMap(
				e -> e.getKey(), e -> e.getValue(),
				(k, v) -> { throw new IllegalStateException(String.format("Duplicate key %s", k)); },
				LinkedHashMap::new)));
	}

	protected MapX(LinkedHashMap<K, V> delegate) { super(delegate); }
	protected Stream<Map.Entry<K, V>> stream() { return delegate.entrySet().stream(); }


	public <W> MapX<K, W> mapValues(Function<? super V, ? extends W> mapper) {
		return MapX.fromStream(stream()
				.map(en -> Map.entry(en.getKey(), mapper.apply(en.getValue()))));
	}

	public <U extends Comparable<? super U>> MapX<K, V> sortBy(BiFunction<? super K, ? super V, ? extends U> f) {
		Comparator<Entry<K, V>> comparator = Comparator.comparing(en -> f.apply(en.getKey(), en.getValue()));
		return MapX.fromStream(stream()
				.sorted(comparator));
	}
	public MapX<K, V> reverse() {
		List<Entry<K, V>> list = stream().collect(Collectors.toList());
		Collections.reverse(list);
		return MapX.fromStream(list.stream());
	}

	public MapX<K, V> take(int n) {
		return n >= size()
				? this
				: MapX.fromStream(stream().limit(n));
	}

}
