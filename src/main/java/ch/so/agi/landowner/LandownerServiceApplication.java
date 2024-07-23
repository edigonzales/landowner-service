package ch.so.agi.landowner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ch.admin.geo.schemas.bj.tgbv.gbdbs._2.GetParcelsByIdResponse;
import ch.admin.geo.schemas.bj.tgbv.gbdbs._2.GetParcelsByIdResponse.Person;
import jakarta.xml.bind.JAXBElement;
import ch.admin.geo.schemas.bj.tgbv.gbbasistypen._2.LiegenschaftType;

@SpringBootApplication
public class LandownerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LandownerServiceApplication.class, args);
	}
	
	@Bean
	CommandLineRunner lookup(ParcelsClient parcelsClient) {
        return args -> {
            String country = "Spain";

            if (args.length > 0) {
                country = args[0];
            }
//            GetParcelsByIdResponse response = parcelsClient.getParcelsById(country);
//            System.err.println(response.getGrundstuecks().getFirst().getGrundstueck().getValue());
//            
//            
//            JAXBElement<LiegenschaftType> foo = (JAXBElement<LiegenschaftType>) response.getGrundstuecks().getFirst().getGrundstueck();
//            System.err.println(foo.getValue());
//            
//            Person person = response.getPersons().getFirst();
        };
    }


}
