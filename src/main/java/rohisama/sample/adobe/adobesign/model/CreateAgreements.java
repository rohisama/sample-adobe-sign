package rohisama.sample.adobe.adobesign.model;

import lombok.Data;

@Data
public class CreateAgreements {
    // Agreementの名前
    private String name;
    // DocumentID
    private String documentId;
    // 承認依頼者のEmailアドレス
    private String email;
}