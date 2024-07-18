package ch.so.agi.landowner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class WsConfig {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setPackagesToScan("ch.admin.geo.schemas", "ch.ech.xmlns", "ch.terravis");
        marshaller.setSupportJaxbElementClass(true);
        return marshaller;
    }
    
    @Bean
    public ParcelsClient parcelsClient(Jaxb2Marshaller marshaller) {
        ParcelsClient client = new ParcelsClient();
        client.setDefaultUri("https://terravis.so.ch/gbdbs/gbdbs");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }


}
