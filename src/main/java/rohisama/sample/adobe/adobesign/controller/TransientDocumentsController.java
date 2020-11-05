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
import io.swagger.client.model.transientDocuments.TransientDocumentResponse;
import rohisama.sample.adobe.adobesign.service.TranseientDocumentsApiService;


/**
 * TransientDocumentsController
 */
@RestController
@CrossOrigin
@RequestMapping("/transientDocuments")
public class TransientDocumentsController {
    protected final static Logger logger = LoggerFactory.getLogger(TransientDocumentsController.class);

    @Autowired
    private TranseientDocumentsApiService service;

    /**
     * ドキュメントアップロード用API
     * @param authorization
     *        Authorization token
     * @return Http Response
     * @throws Exception
     */
    @ApiOperation(value = "transientDocumentsのAPIをコールします")
    @RequestMapping(method = RequestMethod.POST)
    public TransientDocumentResponse transientDocuments(
        @RequestHeader("Authorization") String authorization) throws Exception {
        
        try {
            logger.debug("Call /transientDocuments.");
            return service.callApi(authorization);
        } catch(Exception e) {
            logger.debug("Exception occured.", e);
            throw e;
        }
    }

}