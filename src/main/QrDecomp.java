package main;

/**
 * Created by michael on 4/11/16.
 */
public class QrDecomp {
    public final Matrix q;
    public final Matrix r;

    public QrDecomp(Matrix q, Matrix r) {
        this.q = q;
        this.r = r;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Q:\n");
        builder.append(q);
        builder.append("\nR:\n");
        builder.append(r);
        return builder.toString();
    }
}
