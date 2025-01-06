package tobyspring.hellospring.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.stream.Collectors;

public class SimpleApiExecutor implements ApiExecutor {
    @Override
    public String execute(URI uri) throws IOException {
        String response;
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
            response = br.lines().collect(Collectors.joining());
        }
        ; // try()안에서 br을 생성하면 그 블럭을 빠져나가기 전에 br.close()을 자동으로 해줌
        return response;
    }
}
