package experiment;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// ≈ UnmodifiableList<T>
public final class ListX<T> extends ListXAsCollection<T> {

	public static <T> ListX<T> fromList(List<T> list) { return new ListX<>(list); }
	public static <T> ListX<T> fromArray(T[] array) { return new ListX<>(List.of(array)); }
	public static <T> ListX<T> fromStream(Stream<T> stream) { return new ListX<>(stream.collect(Collectors.toList())); }
	protected ListX(List<T> delegate) { super(delegate); }


	public ListX<T> filter(Predicate<? super T> predicate) {
		return ListX.fromStream(stream()
				.filter(predicate));
	}
	public ListX<T> filterNot(Predicate<? super T> predicate) {
		return filter(predicate.negate());
	}

	public <R> ListX<R> map(Function<? super T, ? extends R> mapper) {
		return ListX.fromStream(stream()
				.map(mapper));
	}
	public <R> ListX<R> flatMap(Function<? super T, ? extends Collection<? extends R>> mapper) {
		return ListX.fromStream(stream()
				.flatMap(t -> mapper.apply(t).stream()));
	}

	public <K, V> MapX<K, V> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
		return MapX.fromStream(stream()
				.map(t -> Map.entry(keyMapper.apply(t), valueMapper.apply(t))));
	}
	public <K> MapX<K, ListX<T>> groupBy(Function<? super T, ? extends K> classifier) {
		return MapX.fromLinkedHashMap(stream()
				.collect(Collectors.groupingBy(classifier,
						LinkedHashMap::new,
						Collectors.collectingAndThen(Collectors.toList(), ListX::fromList))));
	}

	public ListX<T> distinct() {
		return ListX.fromStream(stream()
				.distinct());
	}
	public ListX<T> sorted() {
		return ListX.fromStream(stream()
				.sorted());
	}
	public <X extends Comparable<? super X>> ListX<T> sortBy(Function<? super T, ? extends X> f) {
		return ListX.fromStream(stream()
				.sorted(Comparator.comparing(f)));
	}
	public ListX<T> reverse() {
		List<T> list = new ArrayList<>(delegate);
		Collections.reverse(list);
		return ListX.fromList(list);
	}

	public ListX<T> take(int n) {
		return n >= delegate.size()
				? this
				: ListX.fromList(delegate.subList(0, n));
	}
	public ListX<T> takeRight(int n) {
		return n >= delegate.size()
				? this
				: ListX.fromList(delegate.subList(delegate.size() - n, delegate.size()));
	}
	public ListX<T> takeWhile(Predicate<? super T> predicate) {
		return ListX.fromStream(stream()
				.takeWhile(predicate));
	}
	public ListX<T> drop(int n) {
		return n >= delegate.size()
				? ListX.fromList(List.of())
				: ListX.fromList(delegate.subList(n, delegate.size()));
	}
	public ListX<T> dropRight(int n) {
		return n >= delegate.size()
				? ListX.fromList(List.of())
				: ListX.fromList(delegate.subList(0, delegate.size() - n));
	}
	public ListX<T> dropWhile(Predicate<? super T> predicate) {
		return ListX.fromStream(stream()
				.dropWhile(predicate));
	}

	public long sumLong() {
		return stream()
				.mapToLong(t -> ((Number) t).longValue())
				.sum();
	}
	public double sumDouble() {
		return stream()
				.mapToDouble(t -> ((Number) t).doubleValue())
				.sum();
	}

}
