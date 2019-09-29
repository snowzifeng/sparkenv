package util;

public class TupleUtil {

    public static <A, B> TwoTuple<A, B> emptyTwo() {
        return new TwoTuple<>(null, null);
    }

}
