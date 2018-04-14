package csuduc.platform.util;

public class ComOutMdl<T> {
	private T mdl;

	public void set(T mdl) {
		this.mdl = mdl;
	}

	public T get() {
		return (T) mdl;
	}
}
