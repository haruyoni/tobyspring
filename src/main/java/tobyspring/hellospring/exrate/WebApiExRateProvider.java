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

        return runApiForExRate(url);
    }

    // 이 아래로는 변경 가능성이 거의 없는 고정된 코드 -> 템플릿
    // 템플릿 : 고정된 틀 안에 바꿀 수 있는 부분을 넣어서 사용하도록 만들어진 오브젝트
    // 템플릿 메소드 패턴 : 고정된 틀의 로직을 가진 템플릿 메소드를 슈퍼클래스에 두고, 바뀌는 부분을 서브클래스의 메소드에 두는 구조(이거 사용한 건 아님)
    private static BigDecimal runApiForExRate(String url) {
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
            return extractExRate(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // 변동 가능성이 있는 코드 메소드로 분리
    private static BigDecimal extractExRate(String response) throws JsonProcessingException {
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
