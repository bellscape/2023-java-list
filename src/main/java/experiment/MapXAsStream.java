package experiment;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface MapXAsStream<K, V> {

	Stream<Map.Entry<K, V>> stream();

	default <R> MapX<K, R> mapValues(Function<? super V, ? extends R> mapper) {
		return MapX.fromLinkedHashMap(stream()
				.collect(Collectors.toMap(
						e -> e.getKey(),
						e -> mapper.apply(e.getValue()),
						(u, v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); },
						LinkedHashMap::new)));
	}

}
