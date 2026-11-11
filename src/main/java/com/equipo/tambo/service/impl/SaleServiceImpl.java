package com.equipo.tambo.service.impl;

import com.equipo.tambo.dto.SaleDetailRequest;
import com.equipo.tambo.dto.SaleDetailResponse;
import com.equipo.tambo.dto.SaleRequest;
import com.equipo.tambo.dto.SaleResponse;
import com.equipo.tambo.entity.*;
import com.equipo.tambo.repository.ClientRepository;
import com.equipo.tambo.repository.SaleRepository;
import com.equipo.tambo.repository.UserRepository;
import com.equipo.tambo.service.SaleDetailService;
import com.equipo.tambo.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final SaleDetailService saleDetailService;

    @Override
    public List<SaleResponse> listSales() {
        return saleRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SaleResponse getById(Long id) {
        return toResponse(getSale(id));
    }

    private Sale getSale(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontró la venta con ID " + id
                ));
    }

    @Override
    @Transactional
    public SaleResponse createSale(SaleRequest request) {
        User user = userRepository.findById(request.user())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontró el usuario con ID " + request.user()
                ));

        Client client = null;

        if(request.client() != null) {
            client = clientRepository.findById(request.client())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "No se encontró el cliente con ID " + request.client()
                    ));
        }

        Sale sale = new Sale();

        sale.setClient(client);
        sale.setUser(user);
        sale.setDate(LocalDateTime.now());

        request.details().forEach(d -> saleDetailService.createSaleDetail(sale, d));

        recalculateTotal(sale);

        return toResponse(saleRepository.save(sale));
    }

    @Override
    @Transactional
    public SaleResponse updateSale(Long id, SaleRequest request) {
        Sale sale = getSale(id);

        User user = userRepository.findById(request.user())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontró el usuario con ID " + request.user()
                ));

        Client client = null;

        if(request.client() != null) {
            client = clientRepository.findById(request.client())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "No se encontró el cliente con ID " + request.client()
                    ));
        }

        sale.setClient(client);
        sale.setUser(user);

        saleDetailService.replaceDetails(sale, request.details());

        recalculateTotal(sale);

        return toResponse(saleRepository.save(sale));
    }

    @Override
    @Transactional
    public void deleteSale(Long id) {
        Sale sale = getSale(id);
        saleRepository.delete(sale);
    }

    @Override
    @Transactional
    public SaleResponse updateSaleDetail(Long id, Long detailId, SaleDetailRequest request) {
        Sale sale = getSale(id);

        saleDetailService.updateSaleDetail(sale, detailId, request);

        recalculateTotal(sale);

        return toResponse(sale);
    }

    @Override
    @Transactional
    public void deleteSaleDetail(Long id, Long detailId) {
        Sale sale = getSale(id);

        saleDetailService.deleteSaleDetail(sale, detailId);

        recalculateTotal(sale);
    }

    private void recalculateTotal(Sale sale) {
        BigDecimal total = sale.getDetails().stream()
                .map(SaleDetail::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        sale.setTotal(total);
    }

    private SaleResponse toResponse(Sale sale) {
        List<SaleDetailResponse> detailResponses = sale.getDetails().stream()
                .map(detail -> new SaleDetailResponse(
                        detail.getId(),
                        detail.getProduct(),
                        detail.getQuantity(),
                        detail.getSubtotal()
                )).toList();

        return new SaleResponse(
                sale.getId(),
                sale.getClient(),
                sale.getUser(),
                sale.getDate(),
                sale.getTotal(),
                detailResponses
        );
    }
}