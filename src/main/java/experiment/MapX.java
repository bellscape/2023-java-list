package experiment;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// ≈ UnmodifiableMap<K,V> & UnmodifiableList<Entry<K,V>>
public class MapX<K, V> implements Map<K, V>, MapXAsStream<K, V> {

	static <K, V> MapX<K, V> fromLinkedHashMap(LinkedHashMap<K, V> map) { return new MapX<>(map); }
	static <K, V> MapX<K, V> fromStream(Stream<Map.Entry<K, V>> stream) {
		return new MapX<>(stream
				.collect(Collectors.toMap(
						e -> e.getKey(), e -> e.getValue(),
						(k, v) -> { throw new IllegalStateException(String.format("Duplicate key %s", k)); },
						LinkedHashMap::new)));
	}

	protected final LinkedHashMap<K, V> delegate;
	protected MapX(LinkedHashMap<K, V> delegate) { this.delegate = delegate; }
	public Stream<Map.Entry<K, V>> stream() { return delegate.entrySet().stream(); }

	@Override public int size() { return delegate.size(); }
	@Override public boolean isEmpty() { return delegate.isEmpty(); }
	@Override public boolean containsKey(Object key) { return delegate.containsKey(key); }
	@Override public boolean containsValue(Object value) { return delegate.containsValue(value); }
	@Override public V get(Object key) { return delegate.get(key); }

	@Override public V put(K key, V value) { throw new UnsupportedOperationException(); }
	@Override public V remove(Object key) { throw new UnsupportedOperationException(); }
	@Override public void putAll(Map<? extends K, ? extends V> m) { throw new UnsupportedOperationException(); }
	@Override public void clear() { throw new UnsupportedOperationException(); }

	@Override public Set<K> keySet() { return delegate.keySet(); }
	@Override public Collection<V> values() { return delegate.values(); }
	@Override public Set<Entry<K, V>> entrySet() { return delegate.entrySet(); }

}
