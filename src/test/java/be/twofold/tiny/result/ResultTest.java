package be.twofold.tiny.result;

import nl.jqno.equalsverifier.*;
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
    void testSuccessIsSuccess() {
        assertThat(Result.success(value).isSuccess()).isTrue();
    }

    @Test
    void testSuccessIsNotFailure() {
        assertThat(Result.success(value).isFailure()).isFalse();
    }

    @Test
    void testFailureIsNotSuccess() {
        assertThat(Result.failure(cause).isSuccess()).isFalse();
    }

    @Test
    void testFailureIsFailure() {
        assertThat(Result.failure(cause).isFailure()).isTrue();
    }

    @Test
    void testSuccessEqualsAndHashCode() {
        EqualsVerifier
            .forClass(Result.Success.class)
            .suppress(Warning.NULL_FIELDS)
            .verify();
    }

    @Test
    void testFailureEqualsAndHashCode() {
        EqualsVerifier
            .forClass(Result.Failure.class)
            .suppress(Warning.NULL_FIELDS)
            .verify();
    }

}
