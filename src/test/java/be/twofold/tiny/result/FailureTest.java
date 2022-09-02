package be.twofold.tiny.result;

import nl.jqno.equalsverifier.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

class FailureTest {

    private final Throwable cause = new Throwable("cause");
    private final Result<?> failure = new Result.Failure(cause);

    @Test
    void testEqualsAndHashCode() {
        EqualsVerifier
            .forClass(Result.Failure.class)
            .suppress(Warning.NULL_FIELDS)
            .verify();
    }

    @Test
    void testToString() {
        assertThat(failure)
            .hasToString("Failure(java.lang.Throwable: cause)");
    }

    @Test
    void testGet() {
        assertThatThrownBy(failure::get)
            .isSameAs(cause);
    }

    @Test
    void testGetCause() {
        assertThat(failure.getCause())
            .isSameAs(cause);
    }

    @Test
    void testFailureThrowsInterruptedException() {
        assertThatExceptionOfType(InterruptedException.class)
            .isThrownBy(() -> Result.failure(new InterruptedException()));
    }

    @Test
    void testFailureThrowsLinkageError() {
        assertThatExceptionOfType(LinkageError.class)
            .isThrownBy(() -> Result.failure(new LinkageError()));
    }

    @Test
    void testFailureThrowsThreadDeath() {
        assertThatExceptionOfType(ThreadDeath.class)
            .isThrownBy(() -> Result.failure(new ThreadDeath()));
    }

    @Test
    void testFailureThrowsVirtualMachineError() {
        assertThatExceptionOfType(VirtualMachineError.class)
            .isThrownBy(() -> Result.failure(new InternalError()));
    }

}
