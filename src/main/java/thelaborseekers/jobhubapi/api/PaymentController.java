package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import thelaborseekers.jobhubapi.dto.PaymentDTO;
import thelaborseekers.jobhubapi.service.PaymentService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
@PreAuthorize("hasRole('OFERTANTE')")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> findAll() {
        List<PaymentDTO> payments = paymentService.findAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/from")
    public ResponseEntity<List<PaymentDTO>> findFrom(@RequestParam Integer ofertante) {
        List<PaymentDTO> payments = paymentService.findAllPaymentsFromOfertante(ofertante);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/page/from")
    public ResponseEntity<Page<PaymentDTO>> findPageFrom(@RequestParam Integer ofertante,@PageableDefault(size = 2, sort = "paymentDate") Pageable pageable) {
        Page<PaymentDTO> page = paymentService.paginatePaymentsFromOfertante(ofertante, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}
