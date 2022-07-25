package be.twofold.tiny.result;

import java.util.*;

public abstract class Result<T> {

    Result() {
    }

    public static <T> Result<T> success(T value) {
        return new Success<>(value);
    }

    @SuppressWarnings("unchecked")
    public static <T> Result<T> failure(Throwable cause) {
        return (Result<T>) new Failure(cause); // Covariant cast
    }

    public final boolean isSuccess() {
        return this instanceof Success;
    }

    public final boolean isFailure() {
        return this instanceof Failure;
    }

    static final class Success<T> extends Result<T> {
        private final T value;

        Success(T value) {
            this.value = Objects.requireNonNull(value, "value is null");
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

        Failure(Throwable cause) {
            this.cause = Objects.requireNonNull(cause, "cause is null");
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
