package com.orbyte.gateway.controller;

import com.orbyte.dto.PaymentRequest;
import com.orbyte.gateway.dto.dtoimpl.PaymentDetailResponseDto;
import com.orbyte.gateway.dto.dtoimpl.PaymentSessionResponse;
import com.orbyte.gateway.exception.PaymentSessionCreationException;
import com.orbyte.gateway.exception.PaymentSessionDoesNotExist;
import com.orbyte.gateway.service.createPayment.CreateOrbPaymentService;
import com.orbyte.gateway.dto.dtoimpl.PaymentInfoDTO;
import com.orbyte.gateway.service.createPayment.PaymentHandlerService;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/gateway")
@RequiredArgsConstructor
@Slf4j
public class GatewayController {

    private final CreateOrbPaymentService createOrbPaymentService;

    private final PaymentHandlerService paymentHandlerService;

    @PostMapping("/create_payment_session")
    public ResponseEntity<String> createPaymentSessionHandler(@RequestBody  PaymentInfoDTO paymentInfoDTO){

        if(paymentInfoDTO!=null){
            String url = createOrbPaymentService.createPaymentSession(paymentInfoDTO);
            return new ResponseEntity<>(url, HttpStatus.OK);
        }
        else throw new PaymentSessionCreationException("Invalid Parameters");

    }

    @GetMapping("/get_session")
    public ResponseEntity<PaymentSessionResponse> getPaymentDetailHandler(@RequestParam  String sessionId){
       PaymentSessionResponse response = createOrbPaymentService.getPaymentSessionDetails(sessionId);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @ExceptionHandler(PaymentSessionDoesNotExist.class)
    public ResponseEntity<Map<String,Object>> PaymentSessionDoesNotExistExceptionHandler(PaymentSessionDoesNotExist ex){
        Map<String, Object>res = new HashMap<>();
        res.put("Error", ex.getMessage());
        res.put("Code",400);
        return  new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentSessionCreationException.class)
    public ResponseEntity<Object> PaymentSessionCreationExceptionHandler(PaymentSessionCreationException ex){
        return  new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/initiate_payment")
    public ResponseEntity<?> initiatePayment(@RequestBody PaymentRequest paymentRequest,@RequestHeader HttpHeaders headers){
        String sessionId = headers.getFirst("sessionid");
        String transactionId = headers.getFirst("transactionid");

        log.info("Session ID: {}", sessionId);
        log.info("Transaction ID: {} ", transactionId);

        if(StringUtil.isNullOrEmpty(sessionId) || StringUtil.isNullOrEmpty(transactionId)){
            throw new PaymentSessionDoesNotExist();
        }

        Object object = paymentHandlerService.initiatePaymentHandler(sessionId,transactionId,paymentRequest);

        return ResponseEntity.ok().body(object);
    }


}
