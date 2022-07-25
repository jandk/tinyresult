package be.twofold.tiny.result;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

class ResultTest {

    private final Object value = new Object();
    private final Result<Object> success = Result.success(value);
    private final Throwable cause = new Throwable();
    private final Result<Object> failure = Result.failure(cause);

    @Test
    void testSuccessThrowsOnNull() {
        assertThatNullPointerException()
            .isThrownBy(() -> Result.success(null));
    }

    @Test
    void testFailureThrowsOnNull() {
        assertThatNullPointerException()
            .isThrownBy(() -> Result.failure(null));
    }

    @Test
    void testOfThrowsOnNull() {
        assertThatNullPointerException()
            .isThrownBy(() -> Result.of(null));
    }

    @Test
    void testOfReturnsSuccess() {
        Result<Object> result = Result.of(() -> value);
        assertSuccess(result, value);
    }

    @Test
    void testOfReturnsFailure() {
        RuntimeException exception = new RuntimeException();
        Result<Object> result = Result.of(() -> {
            throw exception;
        });

        assertFailure(result, exception);
    }

    @Test
    void testIsSuccess() {
        assertThat(success.isSuccess()).isTrue();
        assertThat(failure.isSuccess()).isFalse();
    }

    @Test
    void testIsFailure() {
        assertThat(success.isFailure()).isFalse();
        assertThat(failure.isFailure()).isTrue();
    }

    @Test
    void testMapThrowsOnNull() {
        assertThatNullPointerException()
            .isThrownBy(() -> success.map(null));
    }

    @Test
    void testMapOnSuccess() {
        Result<String> result = success.map(Object::toString);
        assertSuccess(result, value.toString());
    }

    @Test
    void testMapOnFailure() {
        Result<String> result = failure.map(Object::toString);
        assertFailure(result, cause);
    }

    @Test
    void testMapCatchesException() {
        RuntimeException exception = new RuntimeException();
        Result<String> result = success.map(o -> {
            throw exception;
        });
        assertFailure(result, exception);
    }

    @Test
    void testFlatMapThrowsOnNull() {
        assertThatNullPointerException()
            .isThrownBy(() -> success.flatMap(null));
    }

    @Test
    void testFlatMapOnSuccess() {
        Result<String> result = success.flatMap(o -> Result.success(o.toString()));
        assertSuccess(result, value.toString());
    }

    @Test
    void testFlatMapOnFailure() {
        Result<String> result = failure.flatMap(o -> Result.success(o.toString()));
        assertFailure(result, cause);
    }

    @Test
    void testFlatMapCatchesException() {
        RuntimeException exception = new RuntimeException();
        Result<String> result = success.flatMap(o -> {
            throw exception;
        });
        assertFailure(result, exception);
    }

    @Test
    void testOrElseOnSuccess() {
        Object result = success.orElse("fallback");
        assertThat(result).isEqualTo(value);
    }

    @Test
    void testOrElseOnFailure() {
        Object result = failure.orElse("fallback");
        assertThat(result).isEqualTo("fallback");
    }

    @Test
    void testOrElseGetThrowsOnNull() {
        assertThatNullPointerException()
            .isThrownBy(() -> success.orElseGet(null));
    }

    @Test
    void testOrElseGetOnSuccess() {
        Object result = success.orElseGet(() -> "fallback");
        assertThat(result).isEqualTo(value);
    }

    @Test
    void testOrElseGetOnFailure() {
        Object result = failure.orElseGet(() -> "fallback");
        assertThat(result).isEqualTo("fallback");
    }

    private <T> void assertSuccess(Result<T> result, T value) {
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.isFailure()).isFalse();
        assertThat(result.get()).isEqualTo(value);
    }

    private void assertFailure(Result<?> result, Throwable cause) {
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.isFailure()).isTrue();
        assertThat(result.getCause()).isEqualTo(cause);
    }

}
