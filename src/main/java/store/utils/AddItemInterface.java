package store.utils;

@FunctionalInterface
public interface AddItemInterface<In1,Int2,Int3> { // (In1,In2,In3) -> Out
    public Void apply(In1 in1,Int2 int2,Int3 int3);
}



