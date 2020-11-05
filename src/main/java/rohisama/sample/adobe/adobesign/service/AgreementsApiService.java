package rohisama.sample.adobe.adobesign.service;

import io.swagger.client.api.AgreementsApi;
import io.swagger.client.model.agreements.AgreementCreationResponse;
import io.swagger.client.model.agreements.AgreementCreationInfo;
import io.swagger.client.model.agreements.AgreementDocuments;
import io.swagger.client.model.agreements.FileInfo;
import io.swagger.client.model.agreements.ParticipantSetInfo;
import io.swagger.client.model.agreements.ParticipantSetMemberInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import rohisama.sample.adobe.adobesign.model.CreateAgreements;
import rohisama.sample.adobe.adobesign.util.FileUtil;

/**
 * Adobe Sign APIコール処理(agreements)
 */
@Service
public class AgreementsApiService extends ApiService {
    protected final static Logger logger = LoggerFactory.getLogger(AgreementsApiService.class);

    /**
     * Agreement作成処理.
     * @param authorization
     *        Authorization token
     * @param agreement
     *        Agreemant作成に必要な情報(名前、DocumentID、Emailアドレス)
     * @return
     *        Adobe Sign APIのレスポンスデータ。AgreemantIDが格納されている
     * @throws Exception
     */
    public AgreementCreationResponse createAgreement(String authorization, CreateAgreements agreement) throws Exception {

        logger.debug("Agreement name is {}", agreement.getName());
        logger.debug("documentId is {}", agreement.getDocumentId());
        logger.debug("email is {}", agreement.getEmail());
        // agreemantsのAPIをコールする際のBodyパラメータを設定
        // 本APIではタイトル、documentID、承認依頼先のEmailアドレスを設定できるようにしている
        // その他のパラメータは固定(暫定対応)
        // Adobe SginのAPI使用上複数のドキュメントを設定可能だが、本処理では1件のみ登録可能
        AgreementCreationInfo agreementInfo = new AgreementCreationInfo();
        agreementInfo.setName(agreement.getName());
        agreementInfo.setSignatureType(AgreementCreationInfo.SignatureTypeEnum.ESIGN);
        agreementInfo.setState(AgreementCreationInfo.StateEnum.IN_PROCESS);

        FileInfo fileInfo = new FileInfo();
        fileInfo.setTransientDocumentId(agreement.getDocumentId());
        agreementInfo.addFileInfosItem(fileInfo);
  
        ParticipantSetInfo participantSetInfo = new ParticipantSetInfo();
        ParticipantSetMemberInfo participantSetMemberInfo = new ParticipantSetMemberInfo();
  
        participantSetMemberInfo.setEmail(agreement.getEmail());
        participantSetInfo.addMemberInfosItem(participantSetMemberInfo);
        participantSetInfo.setOrder(1);
        participantSetInfo.setRole(ParticipantSetInfo.RoleEnum.SIGNER);
        agreementInfo.addParticipantSetsInfoItem(participantSetInfo);
  
        // Adobe sign API(agreements)のクライアントを作成.
        AgreementsApi agreementsApi = new AgreementsApi(apiClient);
        // agreementsのAPIをコールし、レスポンスをそのままリターンする
        return agreementsApi.createAgreement(authorization, agreementInfo, null, null);
    }

    /**
     * ドキュメント取得処理
     * @param authorization
     *        Authorization token
     * @param agreementId
     * @param documentId
     * @return
     * @throws Exception
     */
    public byte[] getDocument(String authorization, String agreementId) throws Exception {

        //Adobe sign API(agreements)のクライアントを作成.
        AgreementsApi agreementsApi = new AgreementsApi(apiClient);
        // 承認者の署名後はdocumentIdが更新されているため、/agreement/{agreementId}/documents のAPIをコールしてdocumentIdを取得する
        String documentId = this.getDocumentId(authorization, agreementId);
        // APIで取得したPDFデータはbyte[]型で格納される
        byte[] stream = agreementsApi.getDocument(authorization, agreementId, documentId, null, null, null);
        
        // ストレージに保存(暫定処理)
        // TODO! You should be change the method of file store.
        FileUtil.writeBytesToFile(stream, "/work/result/test.pdf");

        // APIのレスポンスとしても返すためリターン(暫定処理)
        return stream;
    }

    /**
     * documentId取得処理
     * @param authorization
     * @param agreementId
     * @return
     * @throws Exception
     */
    private String getDocumentId(String authorization, String agreementId) throws Exception{
        //Adobe sign API(agreements)のクライアントを作成.
        AgreementsApi agreementsApi = new AgreementsApi(apiClient);
        // agreementsのAPIをコールし、レスポンスデータからdocumentIdを取得する(暫定的に決め打ちで取得)
        AgreementDocuments documents = agreementsApi.getAllDocuments(authorization, agreementId, null, null, null, null, null, null);
        return documents.getDocuments().get(0).getId();
    }
}