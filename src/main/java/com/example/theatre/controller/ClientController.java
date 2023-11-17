package com.example.theatre.controller;

import com.example.theatre.entity.Client;
import com.example.theatre.property.ClientProps;
import com.example.theatre.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.IntStream;

@Slf4j
@Controller
@RequestMapping("/clients")
public class ClientController {
    private ClientService clientService;

    private ClientProps props;

    @Autowired
    public ClientController(ClientService clientService, ClientProps props) {
        this.clientService = clientService;
        this.props = props;
    }


    @GetMapping
    public String getClientForm(Model model,
                                @RequestParam(value = "size", required = false, defaultValue = "2") Integer size,
                                @RequestParam(value = "page", required = false, defaultValue = "0") Integer page
    ) {
        if (page < 0) {
            return "redirect:/clients";
        }
        Pageable pageable = PageRequest.of(page, props.getSize());
        Page<Client> clientPage = clientService.findAllClients(getCalculatePage(page, pageable));

        int[] intRange = getPagesRange(page, clientPage);
        model.addAttribute("clients", clientPage);
//        model.addAttribute("pageNumbers", IntStream.range(0, clientPage.getTotalPages()).toArray());
        model.addAttribute("pageNumbers", intRange);
        model.addAttribute("pageTitle", "Страница пагинации");
        return "clients";
    }

    private int[] getPagesRange(Integer page, Page<Client> clientPage) {
        log.warn("getPagesRange clientPage.getTotalPages() = {}", clientPage.getTotalPages());
        log.warn("getPagesRange page + 1 = {}", page + 1);
        if (page + 1 > 5 && page + 1 <= clientPage.getTotalPages()) {
            return IntStream.range(page - 4, page + 1).toArray();
        } else if (page + 1 > clientPage.getTotalPages()) {
            if (clientPage.getTotalPages() > 5) {
                return IntStream.range(clientPage.getTotalPages() - 5, clientPage.getTotalPages()).toArray();
            } else {
                return IntStream.range(0, clientPage.getTotalPages()).toArray();
            }
        } else {
            if (clientPage.getTotalPages() < 5) {
                return IntStream.range(0, clientPage.getTotalPages()).toArray();
            } else {
                return IntStream.range(0, 5).toArray();
            }
        }
    }


    private Pageable getCalculatePage(Integer page, Pageable pageable) {
        log.warn("getCalculatePage page = {}", page);
        log.warn("getCalculatePage clientService.findAllClients(pageable).getTotalPages() = {}", clientService.findAllClients(pageable).getTotalPages());
        if (page <= 0) {
            return PageRequest.of(0, props.getSize());
        }
        if (page >= clientService.findAllClients(pageable).getTotalPages() - 1) {
            int maxPageNumber = clientService.findAllClients(pageable).getTotalPages() - 1;
            return PageRequest.of(maxPageNumber, props.getSize());

        }
        return PageRequest.of(page, props.getSize());
    }


}
