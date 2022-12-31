package experiment;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

// ≈ UnmodifiableMap<K,V> & UnmodifiableList<Entry<K,V>>
class MapXAsMap<K, V> implements Map<K, V> {

	protected final LinkedHashMap<K, V> delegate;
	protected MapXAsMap(LinkedHashMap<K, V> delegate) { this.delegate = delegate; }

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
