package store.utils;

@FunctionalInterface
public interface ClickMenuInterface<Int1> { // (In1,In2,In3) -> Out
    public Void apply(Int1 int1);
}



