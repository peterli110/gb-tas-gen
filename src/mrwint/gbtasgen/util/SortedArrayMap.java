package mrwint.gbtasgen.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortedArrayMap<K extends Comparable<K>, V> {
  ArrayList<K> keys = new ArrayList<>();
  ArrayList<V> values = new ArrayList<>();
  
  public void put(K key, V value) {
    int position = Collections.binarySearch(keys, key);

    if (position >= 0)
      values.set(position, value);
    else {
      position = -position - 1;
      keys.add(position, key);
      values.add(position, value);
    }
  }
  
  public boolean containsKey(K key) {
    return Collections.binarySearch(keys, key) >= 0;
  }
  public V get(K key) {
    int position = Collections.binarySearch(keys, key);
    return position >= 0 ? values.get(position) : null;
  }
  
  public void remove(K key) {
    int position = Collections.binarySearch(keys, key);
    if (position >= 0) {
      keys.remove(position);
      values.remove(position);
    }
  }
  public void trim() {
    keys.trimToSize();
    values.trimToSize();
  }
  public int size() {
    return keys.size();
  }
  public void clear() {
    keys.clear();
    values.clear();
  }

  public K floorKey(K key) {
    int position = Collections.binarySearch(keys, key);
    if (position >= 0)
      return keys.get(position);
    position = -position - 1;
    return position >= 1 ? keys.get(position - 1) : null;
  }
  public K lowerKey(K key) {
    int position = Collections.binarySearch(keys, key);
    if (position < 0)
      position = -position - 1;
    return position >= 1 ? keys.get(position - 1) : null;
  }
  
  public K ceilingKey(K key) {
    int position = Collections.binarySearch(keys, key);
    if (position >= 0)
      return keys.get(position);
    position = -position - 1;
    return position < keys.size() ? keys.get(position) : null;
  }
  public K higherKey(K key) {
    int position = Collections.binarySearch(keys, key);
    if (position >= 0)
      return position < keys.size() - 1 ? keys.get(position + 1) : null;
    position = -position - 1;
    return position < keys.size() ? keys.get(position) : null;
  }
  
  public K firstKey() {
    return keys.isEmpty() ? null : keys.get(0);
  }
  public K lastKey() {
    return keys.isEmpty() ? null : keys.get(keys.size() - 1);
  }

  public ArrayList<K> keySet() {
    return keys;
  }

  
  public String toStringAround(K key) {
    StringBuilder sb = new StringBuilder();
    int position = Collections.binarySearch(keys, key);
    if (position < 0) position = -position - 1;
    int from = Math.max(0, position - 10);
    int to = Math.min(keys.size(), position + 10);
    
    sb.append("[");
    if (from > 0)
      sb.append("<" + from + " omitted>");
    for (int i = from; i < to; i++) {
      if (i > 0) sb.append(",");
      sb.append("[" + keys.get(i) + "," + values.get(i) + "]");
    }
    if (to < keys.size())
      sb.append(",<" + (keys.size() - to) + " omitted>");
    sb.append("]");
    return sb.toString();
  }
}
