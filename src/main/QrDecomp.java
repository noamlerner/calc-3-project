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
}
