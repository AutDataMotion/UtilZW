package csuduc.platform.util;

public class ComOutMdl<T> {
	private final T mdl;

	public ComOutMdl(T mdl) {
		this.mdl = mdl;
	}

	public T get() {
		return (T) mdl;
	}
}
