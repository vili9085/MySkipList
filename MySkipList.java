import java.util.Random;

public class MySkipList<E extends Comparable<? super E>> {
	private static Random rand = new Random();
	private final int maxLevels;
	private int size = 0;
	private Node<E> sentinel;

	public MySkipList() {
		this(4);
	}

	public MySkipList(int maxLevels) {
		if (maxLevels < 2) {
			throw new IllegalArgumentException();
		}
		this.maxLevels = maxLevels;
		sentinel = new Node<E>(null, maxLevels);

	}

	public int size() {
		return size;
	}

	public void clear() {
		for (int i = 0; i < sentinel.levels.length; i++) {
			sentinel.levels[i] = null;
		}
		size = 0;
	}

	public boolean insert(E element) {
		if (element == null) {
			throw new IllegalArgumentException();
		}
		if (!contains(element)) {
			Node<E> insertNode = new Node<E>(element, getRandomNoOfLevels(maxLevels));
			Node<E> current = sentinel;

			for (int i = sentinel.levels.length - 1; i >= 0; i--) {
				while (current.levels[i] != null && current.levels[i].data.compareTo(element) < 0) {
					current = current.levels[i];
				}
				if (insertNode.levels.length - 1 >= i) {
					insertNode.levels[i] = current.levels[i];
					current.levels[i] = insertNode;
				}
			}

			size++;
			return true;
		}
		return false;
	}

	public boolean contains(E element) {

		Node<E> current = sentinel;
		for (int i = sentinel.levels.length - 1; i >= 0; i--) {

			while (current.levels[i] != null && current.levels[i].data.compareTo(element) <= 0) {

				current = current.levels[i];
				if (current.data == element || current.data.equals(element)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean remove(E element) {
		Node<E> current = sentinel;
		boolean found = false;
		for (int i = sentinel.levels.length - 1; i >= 0; i--) {
			while (current.levels[i] != null && current.levels[i].data.compareTo(element) <= 0) {
				if (current.levels[i].data == element || current.levels[i].data.equals(element)) {
					Node<E> tmp = current.levels[i].levels[i];
					current.levels[i].levels[i] = null;
					current.levels[i] = tmp;

					found = true;

				} else {
					current = current.levels[i];
				}
			}
		}
		if (found) {
			size--;
		}
		return found;
	}

	public String toString() {
		if (size > 0) {
			Node<E> current = sentinel.levels[0];
			StringBuilder strBuild = new StringBuilder();
			int count = 1;
			strBuild.append("[");
			while (count++ < size) {

				strBuild.append(current.data + ", ");
				current = current.levels[0];

			}
			strBuild.append(current.data + "]");
			return strBuild.toString();
		}
		else {
			return "[]";
		}
	}

	private int getRandomNoOfLevels(int maxNoOfLevels) {
		int count = 1;

		while (rand.nextBoolean() && count < maxNoOfLevels) {
			count++;
		}
		return count;
	}

	private static class Node<E> {

		private E data;
		private Node<E>[] levels;

		public Node(E data, int noOfLevels) {
			this.data = data;
			levels = new Node[noOfLevels];
		}

	}

    // For testing
	public static void main(String[] args) {
		Random rand = new Random();
		MySkipList<String> mySkip = new MySkipList<>(8);

		for (int i = 100; i >= 0; i--) {
			mySkip.insert("" + (char) (rand.nextInt(30) + 65));
			// mySkip.insert(i);
		}

		System.out.println(mySkip.size());
		System.out.println(mySkip.contains("A"));
		System.out.println(mySkip.toString());
		System.out.println(mySkip.remove("A"));
		for (int i = 10; i >= 0; i--) {
			// mySkip.insert("" + (char)(rand.nextInt(30)+65));
			mySkip.remove("" + (char) (rand.nextInt(30) + 65));
		}

		System.out.println(mySkip.toString());
		System.out.println(mySkip.contains("A"));
		System.out.println(mySkip.size());

		MySkipList<Integer> mySkip2 = new MySkipList<>();

		for (int i = 100; i >= 0; i--) {
			// mySkip.insert("" + (char)(rand.nextInt(30)+65));
			mySkip2.insert(rand.nextInt(100));
		}

		System.out.println(mySkip2.size());
		System.out.println(mySkip2.contains(3));
		System.out.println(mySkip2.remove(3));

		for (int i = 10; i >= 0; i--) {
			// mySkip.insert("" + (char)(rand.nextInt(30)+65));
			mySkip2.remove(rand.nextInt(4));
		}

		mySkip2.clear();
		System.out.println(mySkip2.toString());
		System.out.println(mySkip2.size());
		System.out.println(mySkip2.contains(1));

	}

}
