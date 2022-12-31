package experiment;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface MapXAsStream<K, V> {

	Stream<Map.Entry<K, V>> stream();

	default <V2> MapX<K, V2> mapValues(Function<? super V, ? extends V2> mapper) {
		return MapX.fromStream(stream()
				.map(en -> Map.entry(en.getKey(), mapper.apply(en.getValue()))));
	}

	default <X extends Comparable<? super X>> MapX<K, V> sortBy(BiFunction<? super K, ? super V, ? extends X> f) {
		Comparator<Map.Entry<K, V>> comparator = Comparator.comparing(en -> f.apply(en.getKey(), en.getValue()));
		return MapX.fromStream(stream()
				.sorted(comparator));
	}
	default MapX<K, V> reverse() {
		List<Map.Entry<K, V>> list = stream().collect(Collectors.toList());
		Collections.reverse(list);
		return MapX.fromStream(list.stream());
	}

}
