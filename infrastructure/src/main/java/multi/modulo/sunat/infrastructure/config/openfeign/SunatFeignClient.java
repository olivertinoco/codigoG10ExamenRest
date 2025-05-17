package multi.modulo.sunat.infrastructure.config.openfeign;

import multi.modulo.sunat.infrastructure.client.dto.SunatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "sunatFeignApiClient",
        url = "${api.reniec.base-url}"
)
public interface SunatFeignClient {

    @GetMapping("/v2/sunat/ruc/full")
    ResponseEntity<SunatResponse> getDatosPorRuc(
            @RequestParam("numero") String ruc,
            @RequestHeader("Authorization") String token
    );

}
