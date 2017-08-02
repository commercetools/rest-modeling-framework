package io.vrap.rmf.raml.persistence;


import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class TestConfig {
    private String section;

    @JsonAlias("ramlversion")
    private String ramlVersion;

    private List<TckTest> tests;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getRamlVersion() {
        return ramlVersion;
    }

    public void setRamlVersion(String ramlVersion) {
        this.ramlVersion = ramlVersion;
    }

    public List<TckTest> getTests() {
        return tests;
    }

    public void setTests(List<TckTest> tests) {
        this.tests = tests;
    }
}
