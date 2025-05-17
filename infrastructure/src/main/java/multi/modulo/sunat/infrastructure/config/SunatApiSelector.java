package multi.modulo.sunat.infrastructure.config;


import multi.modulo.sunat.infrastructure.client.dto.SunatResponse;
import multi.modulo.sunat.infrastructure.config.openfeign.SunatFeignClient;
import multi.modulo.sunat.infrastructure.config.resttemplate.SunatRestClient;
import multi.modulo.sunat.infrastructure.config.retrofit.SunatApiClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import java.io.IOException;


@Component
public class SunatApiSelector {

    private final SunatFeignClient feignClient;
    private final SunatRestClient restTemplateClient;
    private final SunatApiClient retrofitClient;

    public SunatApiSelector( SunatFeignClient feignClient,
                             SunatRestClient restTemplateClient,
                             SunatApiClient retrofitClient) {
        this.feignClient = feignClient;
        this.restTemplateClient = restTemplateClient;
        this.retrofitClient = retrofitClient;
    }

    // OpenFeign
    public ResponseEntity<SunatResponse> getDatosPorRucFeign(String ruc, String token) {
        return feignClient.getDatosPorRuc(ruc, token);
    }

    // RestTemplate
    public ResponseEntity<SunatResponse> getDatosPorRucRestTemplate(String ruc, String token) {
        return restTemplateClient.getDatosPorRucResponse(ruc, "Bearer " + token);
    }

    // Retrofit
    public Response<SunatResponse> getDatosPorRucRetrofit(String ruc, String token) throws IOException {
        return retrofitClient.getDatosPorRuc(ruc, "Bearer " + token).execute();
    }
}
