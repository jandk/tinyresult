package be.twofold.tiny.result;

import java.util.*;
import java.util.function.*;

public abstract class Result<T> {

    Result() {
    }

    public static <T> Result<T> success(T value) {
        return new Success<>(value);
    }

    @SuppressWarnings("unchecked")
    public static <T> Result<T> failure(Throwable cause) {
        return (Result<T>) new Failure(cause);
    }

    public static <T> Result<T> of(Supplier<T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        try {
            return success(supplier.get());
        } catch (Throwable cause) {
            return failure(cause);
        }
    }

    public abstract T get();

    public abstract Throwable getCause();

    public final boolean isSuccess() {
        return this instanceof Success;
    }

    public final boolean isFailure() {
        return this instanceof Failure;
    }

    @SuppressWarnings("unchecked")
    public final <R> Result<R> map(Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        if (isSuccess()) {
            return of(() -> mapper.apply(get()));
        }
        return (Result<R>) this;
    }

    @SuppressWarnings("unchecked")
    public final <R> Result<R> flatMap(Function<? super T, ? extends Result<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        if (isSuccess()) {
            return of(() -> mapper.apply(get()).get());
        }
        return (Result<R>) this;
    }

    public final T orElse(T other) {
        if (isSuccess()) {
            return get();
        }
        return other;
    }

    public final T orElseGet(Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        if (isSuccess()) {
            return get();
        }
        return supplier.get();
    }

    static final class Success<T> extends Result<T> {
        private final T value;

        Success(T value) {
            this.value = Objects.requireNonNull(value, "value is null");
        }

        @Override
        public T get() {
            return value;
        }

        @Override
        public Throwable getCause() {
            throw new NoSuchElementException("No cause on Success");
        }

        @Override
        public boolean equals(Object obj) {
            return this == obj || obj instanceof Success
                && Objects.equals(value, ((Success<?>) obj).value);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }

        @Override
        public String toString() {
            return "Success(" + value + ")";
        }
    }

    static final class Failure extends Result<Void> {
        private final Throwable cause;

        @SuppressWarnings("RedundantTypeArguments")
        Failure(Throwable cause) {
            Objects.requireNonNull(cause, "cause is null");
            if (isFatal(cause)) {
                throw Failure.<RuntimeException>doSneakyThrow(cause);
            }
            this.cause = cause;
        }

        private static boolean isFatal(Throwable throwable) {
            return throwable instanceof InterruptedException
                || throwable instanceof LinkageError
                || throwable instanceof ThreadDeath
                || throwable instanceof VirtualMachineError;
        }

        @SuppressWarnings("unchecked")
        private static <E extends Throwable> E doSneakyThrow(Throwable throwable) throws E {
            throw (E) throwable;
        }

        @Override
        @SuppressWarnings("RedundantTypeArguments")
        public Void get() {
            throw Failure.<RuntimeException>doSneakyThrow(cause);
        }

        @Override
        public Throwable getCause() {
            return cause;
        }

        @Override
        public boolean equals(Object obj) {
            return this == obj || obj instanceof Failure
                && Objects.equals(cause, ((Failure) obj).cause);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(cause);
        }

        @Override
        public String toString() {
            return "Failure(" + cause + ")";
        }
    }

}
