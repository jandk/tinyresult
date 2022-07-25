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
