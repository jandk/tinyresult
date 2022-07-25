package be.twofold.tiny.result;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

class ResultTest {

    private final Object value = new Object();
    private final Throwable cause = new Throwable();

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

        assertThat(result).isEqualTo(Result.success(value));
    }

    @Test
    void testOfReturnsFailure() {
        RuntimeException cause = new RuntimeException();
        Result<Object> result = Result.of(() -> {
            throw cause;
        });

        assertThat(result).isEqualTo(Result.failure(cause));
    }

    @Test
    void testIsSuccess() {
        assertThat(Result.success(value).isSuccess()).isTrue();
        assertThat(Result.failure(cause).isSuccess()).isFalse();
    }

    @Test
    void testIsFailure() {
        assertThat(Result.success(value).isFailure()).isFalse();
        assertThat(Result.failure(cause).isFailure()).isTrue();
    }

}
