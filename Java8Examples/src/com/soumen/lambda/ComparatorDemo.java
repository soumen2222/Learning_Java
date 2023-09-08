package com.soumen.lambda;



import java.util.function.Function;

/**
 *
 * @author José Paumard
 */
@FunctionalInterface
public interface ComparatorDemo<T> {

    public int compare(T t1, T t2);
    
    public default ComparatorDemo<T> thenComparing(ComparatorDemo<T> cmp) {
        
        return (p1, p2) -> compare(p1, p2) == 0 ? cmp.compare(p1, p2) : compare(p1, p2) ;
    }
    
    public default ComparatorDemo<T> thenComparing(Function<T, Comparable> f) {
        
        return thenComparing(comparing(f)) ;
    }
    
    public static <U> ComparatorDemo<U> comparing(Function<U, Comparable> f) {
        
        return (p1, p2) ->  f.apply(p1).compareTo(f.apply(p2));
    }
}
