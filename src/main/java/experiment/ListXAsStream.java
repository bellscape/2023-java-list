package experiment;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface ListXAsStream<T> {

	Stream<T> stream();

	default ListX<T> filter(Predicate<? super T> predicate) {
		return ListX.fromStream(stream()
				.filter(predicate));
	}
	default ListX<T> filterNot(Predicate<? super T> predicate) {
		return filter(predicate.negate());
	}

	default <T2> ListX<T2> map(Function<? super T, ? extends T2> mapper) {
		return ListX.fromStream(stream()
				.map(mapper));
	}
	default <T2> ListX<T2> flatMap(Function<? super T, ? extends Collection<? extends T2>> mapper) {
		return ListX.fromStream(stream()
				.flatMap(t -> mapper.apply(t).stream()));
	}

	default <K, V> MapX<K, V> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
		return MapX.fromStream(stream()
				.map(t -> Map.entry(keyMapper.apply(t), valueMapper.apply(t))));
	}
	default <K> MapX<K, ListX<T>> groupBy(Function<? super T, ? extends K> classifier) {
		return MapX.fromLinkedHashMap(stream()
				.collect(Collectors.groupingBy(classifier,
						LinkedHashMap::new,
						Collectors.collectingAndThen(Collectors.toList(), ListX::fromList))));
	}

	default ListX<T> distinct() {
		return ListX.fromStream(stream()
				.distinct());
	}
	default ListX<T> sorted() {
		return ListX.fromStream(stream()
				.sorted());
	}
	default <X extends Comparable<? super X>> ListX<T> sortBy(Function<? super T, ? extends X> f) {
		return ListX.fromStream(stream()
				.sorted(Comparator.comparing(f)));
	}
	default ListX<T> reverse() {
		List<T> list = stream().collect(Collectors.toList());
		Collections.reverse(list);
		return ListX.fromList(list);
	}

}
