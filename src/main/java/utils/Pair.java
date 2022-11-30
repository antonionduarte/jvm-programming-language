package utils;

public record Pair<T, E>(T a, E b) {
	public Pair {
		if (a == null || b == null) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}
	}
}
