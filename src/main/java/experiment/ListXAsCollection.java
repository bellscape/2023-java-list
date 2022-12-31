package experiment;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

class ListXAsCollection<T> implements Collection<T> {

	protected final List<T> delegate;
	protected ListXAsCollection(List<T> delegate) { this.delegate = delegate; }

	@Override public Stream<T> stream() { return delegate.stream(); }

	@Override public int size() { return delegate.size(); }
	@Override public boolean isEmpty() { return delegate.isEmpty(); }
	@Override public boolean contains(Object o) { return delegate.contains(o); }
	@Override public Iterator<T> iterator() { return delegate.iterator(); }
	@Override public Object[] toArray() { return delegate.toArray(); }
	@Override public <T1> T1[] toArray(T1[] a) { return delegate.toArray(a); }
	@Override public boolean containsAll(Collection<?> c) { return delegate.containsAll(c); }

	@Override public boolean add(T t) { throw new UnsupportedOperationException(); }
	@Override public boolean remove(Object o) { throw new UnsupportedOperationException(); }
	@Override public boolean addAll(Collection<? extends T> c) { throw new UnsupportedOperationException(); }
	@Override public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
	@Override public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }
	@Override public void clear() { throw new UnsupportedOperationException(); }

	@Override public boolean equals(Object o) { return delegate.equals(o); }
	@Override public int hashCode() { return delegate.hashCode(); }
	@Override public String toString() { return delegate.toString(); }

}
