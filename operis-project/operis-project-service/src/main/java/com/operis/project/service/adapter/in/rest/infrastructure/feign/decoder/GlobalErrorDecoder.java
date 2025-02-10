package com.operis.project.service.adapter.in.rest.infrastructure.feign.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;

import static com.operis.project.service.adapter.out.http.UserProfileFeignClient.UserProfileApiError;

@RequiredArgsConstructor
public class GlobalErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        try (InputStream body = response.body().asInputStream()) {
            UserProfileApiError customError = objectMapper.readValue(body, UserProfileApiError.class);
            return new UserProfileApiException(customError.message(), customError);
        } catch (Exception e) {
            return defaultErrorDecoder.decode(methodKey, response);
        }
    }
}
