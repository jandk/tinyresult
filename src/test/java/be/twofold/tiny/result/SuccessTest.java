package be.twofold.tiny.result;

import nl.jqno.equalsverifier.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

class SuccessTest {

    private final Object value = "value";
    private final Result<Object> success = new Result.Success<>(value);

    @Test
    void testEqualsAndHashCode() {
        EqualsVerifier
            .forClass(Result.Success.class)
            .suppress(Warning.NULL_FIELDS)
            .verify();
    }

    @Test
    void testToString() {
        assertThat(success)
            .hasToString("Success(value)");
    }

    @Test
    void testGet() {
        assertThat(success.get()).isSameAs(value);
    }

    @Test
    void testGetCause() {
        assertThatExceptionOfType(NoSuchElementException.class)
            .isThrownBy(success::getCause)
            .withMessage("No cause on Success");
    }

}
