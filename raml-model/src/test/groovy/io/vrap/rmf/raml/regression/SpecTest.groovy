package io.vrap.rmf.raml.regression

import com.google.common.collect.Lists
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

    def "spec-examples-0"() {
        when:
        writeFile(
                "oauth_2_0.raml",
                '''
                #%RAML 1.0 SecurityScheme
                
                description: |
                  Dropbox supports OAuth 2.0 for authenticating all API requests.
                type: OAuth 2.0
                describedBy:
                  headers:
                    Authorization:
                      description: |
                         Used to send a valid OAuth 2 access token. Do not use
                         with the "access_token" query string parameter.
                      type: string
                  queryParameters:
                    access_token:
                      description: |
                         Used to send a valid OAuth 2 access token. Do not use with
                         the "Authorization" header.
                      type: string
                  responses:
                    401:
                      description: |
                          Bad or expired token. This can happen if the user or Dropbox
                          revoked or expired an access token. To fix, re-authenticate
                          the user.
                    403:
                      description: |
                          Bad OAuth request (wrong consumer key, bad nonce, expired
                          timestamp...). Unfortunately, re-authenticating the user won't help here.
                settings:
                  authorizationUri: https://www.dropbox.com/1/oauth2/authorize
                  accessTokenUri: https://api.dropbox.com/1/oauth2/token
                  authorizationGrants: [ authorization_code, implicit, 'urn:ietf:params:oauth:grant-type:saml2-bearer' ]
                  scopes: [ "ADMINISTRATOR" ]
            ''')
        writeFile('gist.raml', '''
            #%RAML 1.0 DataType
            ''')
        writeFile('gists.raml', '''
            #%RAML 1.0 DataType
            ''')
        writeFile("collection.raml", '''
            #%RAML 1.0 ResourceType

            description: represents a collection
            ''')
        RamlModelResult<Api> ramlModelResult = constructApi(
                Lists.asList("oauth_2_0.raml", "gist.raml", "gists.raml", "collection.raml"),
                '''\
                #%RAML 1.0
                title: GitHub API
                version: v3
                baseUri: https://api.github.com
                mediaType:  application/json
                securitySchemes:
                  oauth_2_0: !include oauth_2_0.raml
                types:
                  Gist:  !include gist.raml
                  Gists: !include gists.raml
                resourceTypes:
                  collection: !include collection.raml
                traits:
                securedBy: [ oauth_2_0 ]
                /search:
                  /code:
                    type: collection
                    get:
                ''')
        then:
        ramlModelResult.validationResults.size() == 0
    }
}
