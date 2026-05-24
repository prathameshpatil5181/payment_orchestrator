package com.orbyte.tokenizer.services;

import com.orbyte.tokenizer.constants.TokenizerConstants;
import com.orbyte.tokenizer.dto.BinLookupResponse;
import com.orbyte.tokenizer.entity.BinLookup;
import com.orbyte.tokenizer.exceptions.BinException;
import com.orbyte.tokenizer.repository.BinLookupRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BinService {

    private final RestClient restClient;
    private final BinLookupRespository binLookupRespository;
    private final ObjectMapper objectMapper;

    @Transactional
    public BinLookupResponse getBinDetails(String binNumber){
        try{
            log.info("getting the bin details for {}",binNumber);
            String url = TokenizerConstants.BIN_LOOKUP_URI + "/" + binNumber;
            log.info("bin url is {}",url);

            Optional<BinLookup> binDataResponse = binLookupRespository.findByBin(binNumber);

            if(binDataResponse.isPresent()){
                log.info("returning value from db");
                return objectMapper.readValue(binDataResponse.get().getData(),BinLookupResponse.class);
            }

            log.info("Fetching value from URL");
           BinLookupResponse response = restClient.get().uri(url).retrieve().body(BinLookupResponse.class);



            String binResponse = objectMapper.writeValueAsString(response);
            BinLookup binLookup = BinLookup.builder().bin(binNumber).data(binResponse).build();

            binLookupRespository.save(binLookup);

           log.info("Bin lookup response is {}",response.getScheme());
           return  response;

        }
        catch(HttpClientErrorException ex){
            log.error("error while fetching bin details {} {}", binNumber, ex.getMessage());
            throw new BinException("Error while fetching bin details");
        }
    }

}
