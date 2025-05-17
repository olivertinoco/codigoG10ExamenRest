package multi.modulo.sunat.infrastructure.config.resttemplate;

import multi.modulo.sunat.infrastructure.client.dto.SunatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class SunatRestClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public SunatRestClient(RestTemplate restTemplate,
                            @Value("${api.reniec.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public ResponseEntity<SunatResponse> getDatosPorRucResponse(String ruc, String token) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/v2/sunat/ruc/full")
                .queryParam("numero", ruc)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> request = new HttpEntity<>(headers);

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                SunatResponse.class
        );
    }
}
