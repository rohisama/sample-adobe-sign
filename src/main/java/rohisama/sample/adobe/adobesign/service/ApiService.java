package rohisama.sample.adobe.adobesign.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.client.model.ApiClient;



public class ApiService {
    protected final static Logger logger = LoggerFactory.getLogger(ApiService.class);


    ApiClient apiClient;
    
    @PostConstruct
    public void createApiClient(){
        if (null == this.apiClient) {
            this.apiClient = new ApiClient();
            this.apiClient.setBasePath("https://api.jp1.adobesign.com" + "/api/rest/v6");
        }
    }
}