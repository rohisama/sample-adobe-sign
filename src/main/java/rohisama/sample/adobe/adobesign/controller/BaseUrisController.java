package rohisama.sample.adobe.adobesign.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.client.model.baseUris.BaseUriInfo;
import rohisama.sample.adobe.adobesign.service.BaseUrisApiService;

@RestController
@CrossOrigin
@RequestMapping("/baseUris")
public class BaseUrisController {
    protected final static Logger logger = LoggerFactory.getLogger(BaseUrisController.class);

    @Autowired
    private BaseUrisApiService service;

    @ApiOperation(value = "baseUrisのAPIをコールします")
    @RequestMapping(method = RequestMethod.GET)
    public BaseUriInfo baseUris(
        @RequestHeader("Authorization") String authorization) throws Exception {
        
        try {
            return service.callApi(authorization);
        } catch(Exception e) {
            logger.debug("Exception occured.", e);
            throw e;
        }
    }

}