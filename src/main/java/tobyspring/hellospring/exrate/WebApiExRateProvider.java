package tobyspring.hellospring.exrate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;


public class WebApiExRateProvider  implements ExRateProvider {
    @Override
    public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        // 이 아래로는 변경 가능성이 거의 없는 고정된 코드
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String response;
        try{
            response = executeAPI(uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return parseExRate(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }

    // 변동 가능성이 있는 코드 메소드로 분리
    private static BigDecimal parseExRate(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ExRateData data = mapper.readValue(response, ExRateData.class);
        return data.rates().get("KRW");
    }

    private static String executeAPI(URI uri) throws IOException {
        String response;
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
            response = br.lines().collect(Collectors.joining());
        }
        ; // try()안에서 br을 생성하면 그 블럭을 빠져나가기 전에 br.close()을 자동으로 해줌
        return response;
    }
}
