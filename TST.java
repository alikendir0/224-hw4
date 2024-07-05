import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TST<Value> {
  private Node root;
  private TST<Value> reversedTST;

  private class Node {
    char c;
    Node left, mid, right, parent;
    Value val;
  }

  public void createReversedTST() {
    if (reversedTST == null) {
      reversedTST = new TST<>();
    }
  }

  public Iterable<String> getAllKeys() {
    return keysWithPrefix("");
  }

  private Iterable<String> keysWithPrefix(String prefix) {
    List<String> results = new ArrayList<>();
    collect(get(root, prefix, 0), new StringBuilder(prefix), results);
    return results;
  }

  public Value get(String key) {
    Node x = get(root, key, 0);
    if (x == null)
      return null;
    return (Value) x.val;
  }

  private Node get(Node x, String key, int d) {
    if (x == null)
      return null;
    if (key.isEmpty())
      return x;
    char c = key.charAt(d);
    if (c < x.c)
      return get(x.left, key, d);
    else if (c > x.c)
      return get(x.right, key, d);
    else if (d < key.length() - 1)
      return get(x.mid, key, d + 1);
    else
      return x;
  }

  public void put(String key, Value val) {
    key = key.replaceAll("[,:;.]", "");
    root = put(root, key, val, 0, null);
    reversedTST.putReversed(key, val);
  }

  private void putReversed(String key, Value val) {
    String reversedKey = new StringBuilder(key).reverse().toString();
    root = put(root, reversedKey, val, 0, null);
  }

  private Node put(Node x, String key, Value val, int d, Node parent) {
    char c = key.charAt(d);
    if (x == null) {
      x = new Node();
      x.c = c;
      x.parent = parent;
    }
    if (c < x.c)
      x.left = put(x.left, key, val, d, x);
    else if (c > x.c)
      x.right = put(x.right, key, val, d, x);
    else if (d < key.length() - 1)
      x.mid = put(x.mid, key, val, d + 1, x);
    else
      x.val = val;
    return x;
  }

  public boolean search(String key) {
    return get(key) != null;
  }

  public void autoComplete(String prefix) {
    Iterable<String> results = autocomplete(prefix);
    LinkedList<String> finalResults = new LinkedList<>();
    for (String result : results) {
      finalResults.add(result);
    }
    if (finalResults.size() == 0) {
      System.out.println("No words");
      return;
    }
    System.out.println(finalResults);
  }

  private Iterable<String> autocomplete(String prefix) {
    LinkedList<String> results = new LinkedList<>();
    Node x = get(root, prefix, 0);
    if (x == null) {
      return results;
    }
    if (x.val != null)
      results.add(prefix);
    collect(x.mid, new StringBuilder(prefix), results);
    return results;
  }

  private void collect(Node x, StringBuilder prefix, List<String> results) {
    if (x == null)
      return;
    collect(x.left, prefix, results);
    if (x.val != null)
      results.add(prefix.toString() + x.c);
    collect(x.mid, prefix.append(x.c), results);
    prefix.deleteCharAt(prefix.length() - 1);
    collect(x.right, prefix, results);
  }

  public void reverseAutoComplete(String suffix) {
    Iterable<String> results = reversedTST.reverseAutoComplete(new StringBuilder(suffix).reverse().toString(),
        suffix);
    ;
    LinkedList<String> finalResults = new LinkedList<>();
    for (String result : results) {
      finalResults.add(result);
    }
    if (finalResults.size() == 0) {
      System.out.println("No words");
      return;
    }
    System.out.println(finalResults);
  }

  private Iterable<String> reverseAutoComplete(String reversedPrefix, String originalSuffix) {
    LinkedList<String> results = new LinkedList<>();
    Node x = get(root, reversedPrefix, 0);
    if (x == null) {
      return results;
    }
    if (x.val != null)
      results.add(new StringBuilder(reversedPrefix).reverse().toString());
    collect(x.mid, new StringBuilder(reversedPrefix), results);
    List<String> finalResults = new ArrayList<>();
    for (String result : results) {
      finalResults.add(new StringBuilder(result).reverse().toString());
    }
    Collections.sort(finalResults);
    return finalResults;
  }

  public void FullAutoComplete(String prefix, String suffix) {
    Iterable<String> start = autocomplete(prefix);
    Iterable<String> end = reversedTST.reverseAutoComplete(new StringBuilder(suffix).reverse().toString(),
        suffix);
    ;
    LinkedList<String> results = new LinkedList<>();
    for (String s : start) {
      for (String e : end) {
        if (s.equals(e))
          results.add(s);
      }
    }
    if (results.size() == 0) {
      System.out.println("No words");
      return;
    }
    System.out.println(results);
  }

  public void findTopK(int k) {
    LinkedList<String> words = new LinkedList<>();
    LinkedList<Integer> values = new LinkedList<>();
    for (String key : getAllKeys()) {
      words.add(key);
      values.add((int) get(key));
    }
    LinkedList<String> list = new LinkedList<>();
    for (int j = k; j > 0; j--) {
      int maxValue = Integer.MIN_VALUE;
      int maxIndex = -1;
      for (int i = 0; i < values.size(); i++) {
        if (values.get(i) > maxValue) {
          maxValue = values.get(i);
          maxIndex = i;
        }
      }
      list.add(words.get(maxIndex));
      words.remove(maxIndex);
      values.remove(maxIndex);
    }
    Collections.sort(list);
    System.out.println(list);
  }

}
