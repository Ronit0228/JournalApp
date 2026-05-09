package net.engineeringdigest.journalApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteResponse {

    @JsonProperty("quote")
    private String quote;

    @JsonProperty("author")
    private String author;

    @JsonProperty("work")
    private String work;

    @JsonProperty("categories")
    private List<String> categories;
}
