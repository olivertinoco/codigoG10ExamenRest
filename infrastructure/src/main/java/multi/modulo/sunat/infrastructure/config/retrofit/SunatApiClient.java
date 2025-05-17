package multi.modulo.sunat.infrastructure.config.retrofit;

import multi.modulo.sunat.infrastructure.client.dto.SunatResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SunatApiClient {

    @GET("/v2/sunat/ruc/full")
    Call<SunatResponse> getDatosPorRuc(
            @Query("numero") String ruc,
            @Header("Authorization") String token
    );
}
