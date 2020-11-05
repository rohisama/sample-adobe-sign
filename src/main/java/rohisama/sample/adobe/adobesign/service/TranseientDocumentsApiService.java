package rohisama.sample.adobe.adobesign.service;

import java.io.File;

import org.springframework.stereotype.Service;

import io.swagger.client.api.TransientDocumentsApi;
import io.swagger.client.model.transientDocuments.TransientDocumentResponse;

/**
 * Adobe Sign APIコール処理(transientDocuments)
 */
@Service
public class TranseientDocumentsApiService extends ApiService {
    
    /**
     * 文書アップロード処理
     * @param authorization
     *        Authorization token
     * @return
     *        Adobe Sign APIのレスポンスデータ。documentIDが格納されている
     * @throws Exception
     */
    public TransientDocumentResponse callApi(String authorization) throws Exception {

        // PDFファイルは固定パスから取得(暫定対応)
        //TODO : Provide path and name of file to be uploaded as transient document 
        String filePath = "/work/src/main/resources/docment/";
        String fileName = "sampledoc.pdf";
        File file = new File(filePath + fileName);
        String xApiUser = null;
        String xOnBehalfOfUser = null;
        String mimeType = "application/pdf";

        // Adobe sign API(transientDocuments)のクライアントを作成.
        TransientDocumentsApi transientDocumentsApi = new TransientDocumentsApi(this.apiClient);
        // transientDocumentsのAPIをコールし、レスポンスをそのままリターンする
        return transientDocumentsApi.createTransientDocument(authorization, file, xApiUser, xOnBehalfOfUser, fileName, mimeType);
    }
}