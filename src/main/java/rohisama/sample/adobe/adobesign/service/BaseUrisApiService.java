package rohisama.sample.adobe.adobesign.service;

import org.springframework.stereotype.Service;

import io.swagger.client.api.BaseUrisApi;
import io.swagger.client.model.baseUris.BaseUriInfo;

@Service
public class BaseUrisApiService extends ApiService {
    
    public BaseUriInfo callApi(String authorization) throws Exception {
        //Get the BaseUris.
        BaseUrisApi baseUrisApi = new BaseUrisApi(this.apiClient);
        return baseUrisApi.getBaseUris(authorization);
    }
}