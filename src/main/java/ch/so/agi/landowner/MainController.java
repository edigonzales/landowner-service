package ch.so.agi.landowner;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import ch.admin.geo.schemas.bj.tgbv.gbdbs._2.GetParcelsByIdResponse;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class MainController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ParcelsClient parcelsClient;
    
    public MainController(ParcelsClient parcelsClient) {
        this.parcelsClient = parcelsClient;
    }
    
    @GetMapping("/ping")
    public ResponseEntity<String> ping(@RequestHeader Map<String, String> headers, HttpServletRequest request) {
        
        headers.forEach((key, value) -> {
            logger.info(String.format("Header '%s' = %s", key, value));
        });
        
        logger.info("server name: " + request.getServerName());
        logger.info("context path: " + request.getContextPath());
        
        logger.info("ping"); 
        
        return new ResponseEntity<String>("landowner-service", HttpStatus.OK);
    }
    
    @GetMapping("/pong")
    public ResponseEntity<GetParcelsByIdResponse> pong(
            @RequestParam(name = "egrid") String egrid
           ) {
        
        
//        System.out.println("egrid " + egrid);
        
        
        GetParcelsByIdResponse response = parcelsClient.getParcelsById(egrid);
        
        
//        return new ResponseEntity<GetEGRIDResponse>(new GetEGRIDResponse(ret),gsList.size()>0?HttpStatus.OK:HttpStatus.NO_CONTENT);

        return new ResponseEntity<GetParcelsByIdResponse>(response, HttpStatus.OK);
    }
   

}
