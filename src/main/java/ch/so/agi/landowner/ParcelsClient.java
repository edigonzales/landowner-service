package ch.so.agi.landowner;

import java.io.IOException;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.xml.transform.StringSource;

import ch.admin.geo.schemas.bj.tgbv.gbdbs._2.BezugInhalt;
import ch.admin.geo.schemas.bj.tgbv.gbdbs._2.GetParcelsByIdRequestType;
import ch.admin.geo.schemas.bj.tgbv.gbdbs._2.GetParcelsByIdResponse;
import ch.admin.geo.schemas.bj.tgbv.gbdbs._2.ObjectFactory;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;


public class ParcelsClient extends WebServiceGatewaySupport {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Jaxb2Marshaller marshaller;
    
    public GetParcelsByIdResponse getParcelsById(String egrid) {
        
        /*
        <ns:GetParcelsByIdRequest>
            <ns:version>2.1</ns:version>
            <ns:transactionId>RAUM-14922-1</ns:transactionId>
            <ns:BezugInhalt>IndexMitEigentum</ns:BezugInhalt>
            <!--Optional:-->
            <ns:includeHistory>false</ns:includeHistory>
            <!--1 or more repetitions:-->
            <ns:Id>CH633284061534::::</ns:Id>
        </ns:GetParcelsByIdRequest>
        */
        
        ObjectFactory objectFactory = new ObjectFactory();
        GetParcelsByIdRequestType request = objectFactory.createGetParcelsByIdRequestType();
       
        request.setVersion("2.1");
        request.setTransactionId("RAUM-14922-1");
        //request.setBezugInhalt(BezugInhalt.INDEX_MIT_EIGENTUM);
        request.setBezugInhalt(BezugInhalt.OEFFENTLICH);
        request.setIncludeHistory(false);
        request.getIds().add(egrid+"::::");  // CH258032700664 // CH633284061534
        
        StringWriter sw1 = new StringWriter();
        Result result1 = new StreamResult(sw1);

        marshaller.marshal(request, result1);
        System.out.println("Request: " + sw1.toString());
        
        
        GetParcelsByIdResponse response = (GetParcelsByIdResponse) getWebServiceTemplate()
                .marshalSendAndReceive("https://terravis.so.ch/gbdbs/gbdbs-pub", request, new WebServiceMessageCallback() {
                    @Override
                    public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
                        try {
                            SoapHeader soapHeader = ((SoapMessage) message).getSoapHeader();
                            
                            System.out.println(soapHeader);
                            
                            String finalXMLRequest = """
    <enq:enquirer xmlns:enq="http://www.bedag.ch/raum/gdbds/Enquirer/1.0">
      <enq:id>BI</enq:id>
      <enq:name>Testsystem</enq:name>
    </enq:enquirer>
                                    """;
                            StringSource headerSource = new StringSource(finalXMLRequest);
                            Transformer transformer = TransformerFactory.newInstance().newTransformer();
                            transformer.transform(headerSource, soapHeader.getResult());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        
                    }
                    
                });

        StringWriter sw2 = new StringWriter();
        Result result2 = new StreamResult(sw2);

        marshaller.marshal(response, result2);
        //System.out.println("Response: " + sw2.toString());
        
        return response;
    }
    
    
//    public GetCountryResponse getCountry(String country) {
//
//        GetCountryRequest request = new GetCountryRequest();
//        request.setName(country);
//
//        log.info("Requesting location for " + country);
//
//        GetCountryResponse response = (GetCountryResponse) getWebServiceTemplate()
//            .marshalSendAndReceive("http://localhost:8080/ws/countries", request,
//                new SoapActionCallback(
//                    "http://spring.io/guides/gs-producing-web-service/GetCountryRequest"));
//
//        return response;
//      }

}
