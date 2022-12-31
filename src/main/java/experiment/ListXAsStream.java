package experiment;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface ListXAsStream<T> {

	Stream<T> stream();

	default ListX<T> filter(Predicate<? super T> predicate) {
		return ListX.fromList(stream()
				.filter(predicate)
				.collect(Collectors.toList()));
	}
	default ListX<T> filterNot(Predicate<? super T> predicate) {
		return filter(predicate.negate());
	}

	default <R> ListX<R> map(Function<? super T, ? extends R> mapper) {
		return ListX.fromList(stream()
				.map(mapper)
				.collect(Collectors.toList()));
	}
	default <R> ListX<R> flatMap(Function<? super T, ? extends Collection<? extends R>> mapper) {
		return ListX.fromList(stream()
				.flatMap(t -> mapper.apply(t).stream())
				.collect(Collectors.toList()));
	}

	default <K, V> MapX<K, V> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
		return MapX.fromLinkedHashMap(stream()
				.collect(Collectors.toMap(
						keyMapper, valueMapper,
						(u, v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); },
						LinkedHashMap::new)));
	}
	default <K> MapX<K, ListX<T>> groupBy(Function<? super T, ? extends K> classifier) {
		return MapX.fromLinkedHashMap(stream()
				.collect(Collectors.groupingBy(classifier,
						LinkedHashMap::new,
						Collectors.collectingAndThen(Collectors.toList(), ListX::fromList))));
	}

}
