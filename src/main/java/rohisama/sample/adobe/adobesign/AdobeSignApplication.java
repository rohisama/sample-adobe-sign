package rohisama.sample.adobe.adobesign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdobeSignApplication {

	protected final static Logger logger = LoggerFactory.getLogger(AdobeSignApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AdobeSignApplication.class, args);
	}

}
