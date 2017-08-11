package io.vrap.rmf.raml.generic.generator.php

import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.types.AnyType
import io.vrap.rmf.raml.persistence.RamlResourceSet
import io.vrap.rmf.raml.persistence.antlr.RAMLCustomLexer
import io.vrap.rmf.raml.persistence.antlr.RAMLParser
import io.vrap.rmf.raml.persistence.constructor.ApiConstructor
import io.vrap.rmf.raml.persistence.constructor.Scope
import org.antlr.v4.runtime.CommonTokenFactory
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.TokenStream
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.URIConverter
import spock.lang.Shared
import spock.lang.Specification

class TypeGeneratorTest extends Specification {
    @Shared
    ResourceSet resourceSet = new RamlResourceSet()
    @Shared
    URI uri = URI.createURI("test.raml");

    def "generate simple interface"() {
        when:
        Api api = constructApi(
                '''\
        types:
            Person:
                properties:
                    name: string
        ''')
        then:
        String result = generate(TypesGenerator.TYPE_INTERFACE, api.types.get(0));
        result == '''<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Ctp\\Types;

interface Person {
    /**
     * @return string
     */
    public function getName();
}
'''
    }

    def "generate extended interface"() {
        when:
        Api api = constructApi(
                '''\
        types:
            Person:
                properties:
                    name: string
            User:
                type: Person
                properties:
                    role: string
        ''')
        then:
        String result = generate(TypesGenerator.TYPE_INTERFACE, api.types.get(1));
        result == '''<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Ctp\\Types;

interface User extends Person {
    /**
     * @return string
     */
    public function getRole();
}
'''
    }

    def "generate simple model"() {
        when:
        Api api = constructApi(
                '''\
        types:
            Person:
                properties:
                    name: string
            User:
                type: Person
                properties:
                    role: string
        ''')
        then:
        String result = generate(TypesGenerator.TYPE_MODEL, api.types.get(0));
        result == '''<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Ctp\\Types;

class PersonModel implements Person {
    /**
     * @var string
     */
    private $name;

    /**
     * @return string
     */
    public function getName() { return $this->name; }
}
'''
    }

    def "generate extended model"() {
        when:
        Api api = constructApi(
                '''\
        types:
            Person:
                properties:
                    name: string
            User:
                type: Person
                properties:
                    role: string
        ''')
        then:
        String result = generate(TypesGenerator.TYPE_MODEL, api.types.get(1));
        result == '''<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Ctp\\Types;

class UserModel extends PersonModel implements User {
    /**
     * @var string
     */
    private $role;

    /**
     * @return string
     */
    public function getRole() { return $this->role; }
}
'''
    }

    def "generate interface getter"() {
        when:
        Api api = constructApi(
                '''\
        types:
            Person:
                properties:
                    address: Address
            Address:
                properties:
                    street: string
        ''')
        then:
        String result = generate(TypesGenerator.TYPE_INTERFACE, api.types.get(0));
        result == '''<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Ctp\\Types;

interface Person {
    /**
     * @return Address
     */
    public function getAddress();
}
'''
    }

    def "generate model getter"() {
        when:
        Api api = constructApi(
                '''\
        types:
            Person:
                properties:
                    address: Address
            Address:
                properties:
                    street: string
        ''')
        then:
        String result = generate(TypesGenerator.TYPE_MODEL, api.types.get(0));
        result == '''<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Ctp\\Types;

class PersonModel implements Person {
    /**
     * @var Address
     */
    private $address;

    /**
     * @return Address
     */
    public function getAddress() { return $this->address; }
}
'''
    }

    String generate(String generateType, AnyType type) {
        TypesGenerator generator = new TypesGenerator()
        return generator.generateType(generator.createVisitor(TypesGenerator.PACKAGE_NAME, generateType), type);
    }

    Api constructApi(String input) {
        RAMLParser parser = parser(input)
        def apiConstructor = new ApiConstructor()
        Scope scope = Scope.of(resourceSet.createResource(uri))
        return (Api)apiConstructor.construct(parser, scope)
    }

    RAMLParser parser(String input) {
        final URIConverter uriConverter = resourceSet.getURIConverter();
        def strippedInput = input.stripIndent()
        final RAMLCustomLexer lexer = new RAMLCustomLexer(strippedInput, uri, uriConverter);
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        lexer.setTokenFactory(CommonTokenFactory.DEFAULT);
        new RAMLParser(tokenStream)
    }
}
