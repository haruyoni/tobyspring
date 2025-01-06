package tobyspring.hellospring.exrate;

import tobyspring.hellospring.api.*;

import java.math.BigDecimal;


public class WebApiExRateProvider  implements ExRateProvider {
    ApiTemplate apiTemplate = new ApiTemplate();

    @Override
    public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        return apiTemplate.getForExRate(url);
    }
}
