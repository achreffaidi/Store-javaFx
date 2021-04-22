package store.utils;

@FunctionalInterface
public interface DoPaymentInterface<Int1,Int2> { // (In1,In2,In3) -> Out
    public Void apply(Int1 int1,Int2 int2);
}



