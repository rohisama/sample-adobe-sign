# Adobe Sign API動作確認用サンプルアプリ
## 注意書き
- Adobe Sign APIの動作確認用のサンプルコード
- 動作確認目的のため常に固定のドキュメントが登録されるなど動作に制限があります
- エラーハンドリング等もノーケアです
## 環境
- Java version: 1.8.0_272
- Apache Maven 3.6.3  
※maven:3-openjdk-8のdockerイメージを使用
## 事前準備
### AdobeSignJavaSdkのインストール  
mavenのセントラルリポジトリにあるやつは古そうなので[GitHub](https://github.com/adobe-sign/AdobeSignJavaSdk)からcloneしてmaven installする  
```Shell
# git clone https://github.com/adobe-sign/AdobeSignJavaSdk.git
# cd AdobeSignJavaSdk
# mvn clean install
```  
ビルド後、/{UserRoot}/.m2/repository/io/swagger/swagger-java-client/1.0.0/swagger-java-client-1.0.0.jar があればOK(なにこのアーティファクト名。。。)
## アカウント作成  
[ここ](https://acrobat.adobe.com/jp/ja/sign/developer-form.html)からアカウント作成する。筆者所有の独自ドメインだと作成できなかったのでgmailのメアドで作成した
## OAuthToken取得  
取得するだけなら[ここ](https://secure.jp1.adobesign.com/public/docs/restapi/v6)から可能  
正規ルートで取得する場合は以下  
- Adobe SignのサイトにログインしてAPI Applicationを作成する。  
- Applicationが出来たらConfigure OAuth for Applicationのリンクをクリックする  
- リダイレクトURLや、権限設定のチェックを入れていく(お試し用なら全チェックでOK)  
→OAuth 2.0に従うため、認可コードを取得するためのリダイレクト先のWebページ等を用意する必要がある  
　細かい説明は[ここ](https://secure.jp1.adobesign.com/public/static/oauthDoc.jsp)に従ってAuthToken取得する  

### 動作確認
- リポジトリをクローンし、ビルドする
```Shell
# git clone https://github.com/rohisama/sample-adobe-sign.git
# cd sample-adobe-sign
# mvn clean install -Dmaven.test.skip=true
```
- targetディレクトリ配下にadobe-sign-0.0.1-SNAPSHOT.jarがあることを確認
- 以下コマンドで実行する
```Shell
# java -jar adobe-sign-0.0.1-SNAPSHOT.jar
```
- 起動が確認出来たらPostmanなどのRestClientからAPIコールを行いAdobeSignの一連の捜査を行う。  
筆者は[Talend API Tester](https://chrome.google.com/webstore/detail/talend-api-tester-free-ed/aejoelaoggembcahagimdiliamlcdmfm?hl=ja)を使用している
  - ドキュメント登録API(以下はcurlコマンドに置き換えたもの、トークンやURLは適宜変更すること)  
  動作確認目的の為、固定のパスに配置したPDFファイル(/work/src/main/resources/docment/sampledoc.pdf)が常に登録される。  
  パス上にpdfファイルが存在することを確認すること(実装は[ここ](src/main/java/rohisama/sample/adobe/adobesign/service/TranseientDocumentsApiService.java#L28-L29)なので適宜修正はOK)
  ```Shell
  curl -i -X POST \
     -H "Authorization:{取得したトークン}" \
     -H "Content-Type:application/json" \
     'https://localhost:8080/transientDocuments'
  ```
  - Agreement作成API
  ```Shell
  curl -i -X POST \
    -H "Authorization:{取得したトークン}" \
    -H "Content-Type:application/json" \
    -d \
    '{
      "name" : "Test",
      "documentId" : "{ドキュメント登録APIのレスポンスで取得したdocumentId}",
      "email" : "sample@example.com"
    }' \
    'https://localhost:8080/agreements'
  ```
  - この時点でsample@example.com宛に署名依頼のメールが届いているので、メールのリンクから署名を行う
  - ドキュメント取得API
  ```Shell
  curl -i -X GET \
     -H "Authorization:{取得したトークン}" \
     'https://localhost:8080/agreements/{Agreement作成APIのレスポンスで取得したagreementId}/documents'
  ```  
  ドキュメント取得APIでは、署名済みの場合は署名後のドキュメントがダウンロードされる。  
  また動作確認目的として/work/result/test.pdfへも保存される。エラーになる場合はディレクトリが存在するかを確認すること
