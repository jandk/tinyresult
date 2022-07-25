package be.twofold.tiny.result;

import nl.jqno.equalsverifier.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

class FailureTest {

    private final Throwable cause = new Throwable();
    private final Result<Object> failure = Result.failure(cause);

    @Test
    void testEqualsAndHashCode() {
        EqualsVerifier
            .forClass(Result.Failure.class)
            .suppress(Warning.NULL_FIELDS)
            .verify();
    }

    @Test
    void testGet() {
        assertThatThrownBy(failure::get)
            .isSameAs(cause);
    }

    @Test
    void testGetCause() {
        assertThat(failure.getCause()).isSameAs(cause);
    }

}
