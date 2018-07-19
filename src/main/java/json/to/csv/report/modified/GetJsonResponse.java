package json.to.csv.report.modified;

//import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class GetJsonResponse {

public static String getJsonString()
{
   RestTemplate restTemplate=new RestTemplate();
    //RestTemplate restTemplate=TestRestTemp.getRestTemplate();
    String url="http://apitest.giddh.com/company/newsncindore15000172022770ygv88/trial-balance?refresh=true";
    HttpHeaders headers=new HttpHeaders();
    headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Auth-Key","WBYPV0XQMaYnUc3RldcJnkDhJhE42JHB7q-bQ942xlMpt0qc72ClEI7q6oxr0rzNMLUIarqftZ55TlKZkpp2yEE7sMAmgM5s3R8h4bYUKbM=");

    HttpEntity<String> entity = new HttpEntity(headers);

    ResponseEntity<String> response =restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

    System.out.println(response.getBody());
    return response.getBody();

}

}
