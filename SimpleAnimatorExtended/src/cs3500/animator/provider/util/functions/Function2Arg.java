package cs3500.animator.provider.util.functions;

/**
 * A function object that accepts two arguments in its function.
 *
 * @param <A> The type of argument 1
 * @param <B> The type of argument 2
 * @param <C> The return type
 */
public interface Function2Arg<A, B, C> {

  /**
   * Applies the two argument function.
   *
   * @return an object of type C
   */
  C apply(A shape, B motion);
}
