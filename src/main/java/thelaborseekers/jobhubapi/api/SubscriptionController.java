package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.dto.SubscriptionCreateDTO;
import thelaborseekers.jobhubapi.dto.SubscriptionDTO;
import thelaborseekers.jobhubapi.dto.SubscriptionUpdateDTO;
import thelaborseekers.jobhubapi.service.SubscriptionService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/subscription")
@PreAuthorize("hasRole('OFERTANTE')")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/create")
    public ResponseEntity<SubscriptionDTO> createSubscription(@RequestBody SubscriptionCreateDTO subscriptionCreateDTO){
        SubscriptionDTO subscription = subscriptionService.createSubscription(subscriptionCreateDTO);
        return new ResponseEntity<>(subscription, HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<SubscriptionDTO> updateSubscription(@PathVariable("id") Integer id, @RequestBody SubscriptionUpdateDTO subscriptionUpdateDTO){
        SubscriptionDTO subscription = subscriptionService.updateSubscription(id, subscriptionUpdateDTO);
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> getSubscription(@PathVariable("id") Integer id){
        SubscriptionDTO subscriptionDTO = subscriptionService.findById(id);
        return new ResponseEntity<>(subscriptionDTO, HttpStatus.OK);
    }

    @GetMapping("/ofertante/{id}")
    public ResponseEntity<SubscriptionDTO> getSubscriptionByOfertante(@PathVariable("id") Integer id){
        SubscriptionDTO subscriptionDTO = subscriptionService.findByOfertanteId(id);
        return new ResponseEntity<>(subscriptionDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable("id") Integer id){
        subscriptionService.deleteSubscription(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelSubscription(@PathVariable("id") Integer id){
        subscriptionService.cancelSubscription(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/cancel-payment")
    public ResponseEntity<Void> subscriptionCancelPayment(@RequestParam("id") Integer id){
        subscriptionService.paymentDecline(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
