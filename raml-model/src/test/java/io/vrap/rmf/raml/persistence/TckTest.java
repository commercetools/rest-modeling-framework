package io.vrap.rmf.raml.persistence;

import java.util.List;

public class TckTest {
    private String description;
    private String input;
    private Boolean valid;
    private List<String> tags;

    public String getDescription() {
        return description;
    }

    public String getInput() {
        return input;
    }

    public Boolean getValid() {
        return valid;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
