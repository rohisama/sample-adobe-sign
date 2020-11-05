package rohisama.sample.adobe.adobesign.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.client.model.agreements.AgreementCreationResponse;
import rohisama.sample.adobe.adobesign.model.CreateAgreements;
import rohisama.sample.adobe.adobesign.service.AgreementsApiService;

/**
 * AgreementsController
 */
@RestController
@CrossOrigin
@RequestMapping("/agreements")
public class AgreementsController {
    protected final static Logger logger = LoggerFactory.getLogger(AgreementsController.class);

    @Autowired
    private AgreementsApiService service;

    /**
     * Agreement作成API
     * @param authorization
     *        Authorization token
     * @param agreements
     *        Agreemant作成に必要な情報(名前、DocumentID、Emailアドレス)
     * @return　Http Response
     * @throws Exception
     */
    @ApiOperation(value = "agreementsのAPIをコールします")
    @RequestMapping(method = RequestMethod.POST)
    public AgreementCreationResponse createAgreement(
                                @RequestHeader("Authorization") String authorization,
                                @RequestBody CreateAgreements agreements) throws Exception {
        
        try {
            logger.debug("Call /agreements.");
            return service.createAgreement(authorization, agreements);
        } catch(Exception e) {
            logger.debug("Exception occured.", e);
            throw e;
        }
    }

    /**
     * 
     * @param authorization
     *        Authorization token
     * @param agreementId
     * @return Http Response(Document data)
     * @throws Exception
     */
    @ApiOperation(value = "/{agreementId}/documents/{documentId}のAPIをコールします")
    @RequestMapping(value = "/{agreementId}/documents", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] obtainAgreementsDocument(
                                @RequestHeader("Authorization") String authorization,
                                @PathVariable String agreementId) throws Exception {
        
        try {
            logger.debug("Call /agreements/{}", agreementId);
            return service.getDocument(authorization, agreementId);
        } catch(Exception e) {
            logger.debug("Exception occured.", e);
            throw e;
        }
    }
    

}