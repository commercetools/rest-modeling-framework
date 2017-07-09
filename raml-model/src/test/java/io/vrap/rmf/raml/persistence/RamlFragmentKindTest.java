package io.vrap.rmf.raml.persistence;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class RamlFragmentKindTest {

    @Test
    public void of() {
        final Optional<RamlFragmentKind> fragmentKindOptional = RamlFragmentKind.of(RamlFragmentKind.LIBRARY.getIdentifier());
        assertThat(fragmentKindOptional.isPresent()).isTrue();
    }

    @Test
    public void fromHeader() {
        Optional<RamlFragmentKind> fragmentKind = RamlFragmentKind.fromHeader("#%RAML 1.0 Library");
        assertThat(fragmentKind.isPresent())
                .isTrue();
        assertThat(fragmentKind.get())
                .isEqualTo(RamlFragmentKind.LIBRARY);

        fragmentKind = RamlFragmentKind.fromHeader("#%RAML 1.0");
        assertThat(fragmentKind.isPresent())
                .isTrue();
        assertThat(fragmentKind.get())
                .isEqualTo(RamlFragmentKind.API);
    }
}
