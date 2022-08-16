package com.airjnc.common.service;

import static java.nio.charset.StandardCharsets.UTF_8;
import com.airjnc.common.properties.NcpCredentialProperties;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(NcpCredentialProperties.class)
public class CommonNcpService {

  private final NcpCredentialProperties ncpCredentialProperties;

  public HttpHeaders createHeaders(String url) {
    // https://api.ncloud-docs.com/docs/common-ncpapi
    // 주의! 요청 헤더의 `x-ncp-apigw-timestamp` 값과 getSignature함수 안의 `StringToSign`의 timestamp는 반드시 같은 값이어야 합니다.
    HttpHeaders headers = new HttpHeaders();
    String time = Long.toString(System.currentTimeMillis());

    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("x-ncp-apigw-timestamp", time);
    headers.set("x-ncp-iam-access-key", ncpCredentialProperties.getAccessKey());
    headers.set("x-ncp-apigw-signature-v2", getSignature(time, url));

    return headers;
  }

  private String getSignature(String time, String url) {
    try {
      String method = "POST";
      String space = " ";
      String newLine = "\n";
      String accessKey = ncpCredentialProperties.getAccessKey();
      String secretKey = ncpCredentialProperties.getSecretKey();

      String stringToSign = method + space + url + newLine + time + newLine + accessKey;

      SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(UTF_8), "HmacSHA256");

      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(signingKey);
      byte[] rawHmac = mac.doFinal(stringToSign.getBytes(UTF_8));

      return Base64.getEncoder().encodeToString(rawHmac);
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      throw new RuntimeException(e);
    }
  }
}
