package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.model.modules.Library;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.model.types.StringType;
import io.vrap.rmf.raml.persistence.ResourceFixtures;
import io.vrap.rmf.raml.persistence.constructor.Scope;
import org.eclipse.emf.common.util.EList;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link LibraryConstructor}.
 */
public class LibraryConstructorTest implements RAMLParserFixtures, ResourceFixtures {

    @Test
    public void library() throws IOException {
        final RAMLParser.LibraryContext libraryContext = parseFromClasspath("/libraries/library.raml").library();
        final Scope scope = Scope.of(fromUri(BuiltinType.RESOURCE_URI));
        final Library library = (Library) new LibraryConstructor(scope)
                .visitLibrary(libraryContext);

        assertThat(library.getUsage()).isEqualTo("Test");
        final EList<AnyType> types = library.getTypes();
        assertThat(types).hasSize(8);

        assertThat(types.get(0).getName()).isEqualTo("StringType");
        assertThat(types.get(0)).isInstanceOf(StringType.class);
        final StringType stringType = (StringType) types.get(0);
        assertThat(stringType.getMinLength()).isEqualTo(10);

        assertThat(types.get(7).getName()).isEqualTo("Enum");
        assertThat(types.get(7)).isInstanceOf(StringType.class);
        final StringType enumType = (StringType) types.get(7);
        assertThat(enumType.getEnum()).containsExactly("v1", "v2");
    }
}
