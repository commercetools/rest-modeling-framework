package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api

class SpecTest extends RegressionTest {
    def "spec-example-33"() {
        when:
        writeFile("librarybooks.raml",
                '''\
                #%RAML 1.0
                # This file is located at librarybooks.raml
                title: Book Library API
                documentation:
                  - title: Introduction
                    content: Automated access to books
                  - title: Licensing
                    content: Please respect copyrights on our books.
                /books:
                    get:
                    description: The collection of library books

                ''')
        RamlModelResult<Api> ramlModelResult = constructApi(
                "extend.raml",
                Arrays.asList("librarybooks.raml"),
                '''\
                #%RAML 1.0 Extension
                
                # Objective: The following example illustrates an extension file that adds an additional post method with description to the '/books' resource.
                # Expected result: resulting AST contains an additional post with what is inside the librarybooks.raml
                
                # tags: extension
                
                usage: Add administrative functionality
                extends: librarybooks.raml
                /books:
                  post:
                    description: Add a new book to the collection
                ''')
        then:
        ramlModelResult.validationResults.size() == 0
    }
}
