package net.engineeringdigest.journalApp.service;

import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.dto.QuoteResponse;
import net.engineeringdigest.journalApp.dto.QuoteWithGreetingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class QuotesService {

    private final RestTemplate restTemplate;

    @Value("${api.ninjas.key}")
    private String apiKey;

    @Value("${api.ninjas.quotes.url}")
    private String quotesUrl;

    public QuoteWithGreetingResponse getQuote(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String greeting = "Hi " + authentication.getName();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<QuoteResponse[]> response = restTemplate.exchange(
                quotesUrl,
                HttpMethod.GET,
                httpEntity,
                QuoteResponse[].class   // ← back to array
        );

        return new QuoteWithGreetingResponse(greeting, response.getBody()[0]);
    }
}
