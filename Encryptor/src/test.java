void main() {
    byte[] a = {1, 2, 3};
    byte[] b = {3, 1, 3};

    System.out.println(arraysEqualHash(a, b));
}

public boolean arraysEqualHash(byte[] a, byte[] b) {
    if (a.length != b.length) return false;
    HashMap<Byte, Integer> freq = new HashMap<>();
    for (byte x : a) freq.merge(x, 1, Integer::sum);
    for (byte x : b) {
        if (!freq.containsKey(x)) return false;
        int updated = freq.get(x) - 1;
        if (updated == 0) freq.remove(x);
        else freq.put(x, updated);
    }
    return freq.isEmpty();
}