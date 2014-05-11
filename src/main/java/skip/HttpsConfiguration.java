package skip;

import java.io.FileNotFoundException;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

@Configuration
public class HttpsConfiguration {

    /* Enable HTTPS protocol for the embeded Tomcat server. */
    final int serverPort = 8443;
    final String serverProtocol = "HTTP/1.1";
    final String keystoreFile = "src/main/resources/security/SSL/skip.keystore";
    final String keystoreAlias = "skip";
    // The password is stored in plaintext by default.
    // Some for of password encoding should be enabled.
    final String keystorePass = "sk!p$138F5800";
    
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
        return new EmbeddedServletContainerCustomizer() {
                
            @Override
            public void customize(ConfigurableEmbeddedServletContainerFactory factory) {
                if(factory instanceof TomcatEmbeddedServletContainerFactory){
                    TomcatEmbeddedServletContainerFactory containerFactory = (TomcatEmbeddedServletContainerFactory) factory;
                    containerFactory.addConnectorCustomizers(new TomcatConnectorCustomizer() {

                        @Override
                        public void customize(Connector connector) {
                            connector.setPort(serverPort);
                            connector.setProtocol(serverProtocol);
                            connector.setSecure(true);
                            connector.setScheme("https");
                            connector.setAttribute("keyAlias", keystoreAlias);
                            connector.setAttribute("keystorePass", keystorePass);
                            try {
                                connector.setAttribute("keystoreFile", ResourceUtils.getFile(keystoreFile).getAbsolutePath());
                            }
                            catch (FileNotFoundException exception) {
                                throw new IllegalStateException("Cannot load keystore", exception);
                            }
                            connector.setAttribute("clientAuth", "false");
                            connector.setAttribute("sslProtocol", "TLS");
                            connector.setAttribute("SSLEnabled", true);
                        }

                    });
                }
            }
            
        };
    }
        
}