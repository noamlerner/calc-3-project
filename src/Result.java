public class Result<V, E> {
    private E failure;
    private V success;

    private Result(V success, E failure) {
        this.success = success;
        this.failure = failure;
    }

    @SuppressWarnings("unchecked")
    public static <G, B> Result<G, B> fail(B failure) {
        return new Result(null, failure);
    }

    @SuppressWarnings("unchecked")
    public static <G, B> Result<G, B> succeed(G success) {
        return new Result(success, null);
    }

    public boolean is_ok() {
        return this.success != null;
    }

    public boolean is_err() {
        return this.failure != null;
    }

    public V unwrap() {
        assert this.is_ok();
        return this.success;
    }

    public E unwrap_err() {
        assert this.is_err();
        return this.failure;
    }
}
