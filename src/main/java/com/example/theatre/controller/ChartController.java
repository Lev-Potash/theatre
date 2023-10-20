package com.example.theatre.controller;

import com.alibaba.fastjson.JSON;
import com.example.theatre.entity.*;
import com.example.theatre.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@SessionAttributes({"ticket", "theatreModel", "theatrePerformanceModel"})
@RequestMapping("/")
@Controller
public class ChartController {

    private TheatreService theatreService;
    private PerformanceService performanceService;
    private TheatrePerformanceService theatrePerformanceService;
    private ScheduleService scheduleService;
    private TicketService ticketService;

    @Autowired
    public void setTheatreService(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @Autowired
    public void setPerformanceService(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    @Autowired
    public void setTheatrePerformanceService(TheatrePerformanceService theatrePerformanceService) {
        this.theatrePerformanceService = theatrePerformanceService;
    }

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // переменные сессии
    @ModelAttribute("ticket")
    public Ticket ticket() {
        return new Ticket();
    }

    @ModelAttribute("theatreModel")
    public Theatre theatre() {
        return new Theatre();
    }

    @ModelAttribute("theatrePerformanceModel")
    public TheatrePerformance theatrePerformance() {
        return new TheatrePerformance();
    }


    @ModelAttribute("theatres")
    public List<Theatre> theatres() {
        return theatreService.getTheatres();
    }

    @ModelAttribute("performances")
    public List<Performance> performances() {
        return performanceService.getPerformanceList();
    }

    @GetMapping(value = "/theatre-performance-chart")
    public String showTheatrePerformanceForm(Model model,
                                             @ModelAttribute Theatre theatre,
                                             @ModelAttribute Ticket ticket) {


        TheatrePerformance theatre_performance = new TheatrePerformance();
        log.info("---theatres is:" + theatreService.getTheatres());
//        model.addAttribute("theatres", theatreService.getTheatres());
//        model.addAttribute("performances", performanceService.getPerformanceList());
        model.addAttribute("theatrePerformance", theatre_performance);
        model.addAttribute("theatre", new Theatre());
        model.addAttribute("performance", new Performance());


        return "theatre_performance_chart";
    }

    @PostMapping(value = "theatre-performance-chart")
    public String processTheatrePerformanceRegistration(@Valid @ModelAttribute("theatrePerformance") TheatrePerformance theatre_performance,
                                                        Errors errors,
                                                        @ModelAttribute("theatrePerformanceModel") TheatrePerformance theatrePerformanceModel,
                                                        @ModelAttribute("theatre") Theatre theatre,
                                                        Performance performance,
                                                        @ModelAttribute("theatreModel") Theatre theatreModel,
                                                        @ModelAttribute Ticket ticket) {
        if (errors.hasErrors()) {
            log.error("Theatre Performance form has errors");
            return "theatre_performance";
        }

        log.info("Registration theatre: {}", theatre);
        theatreModel.setId(theatre.getId());
        theatreModel.setTheatreName(theatre.getTheatreName());
        log.info("@ModelAttribute theatre_: {}", theatreModel);
        log.info("Registration performance: {}", performance);
        TheatrePerformance theatrePerformance = theatrePerformanceService.findTheatrePerformance(theatre, performance);
        log.info("registration TheatrePerformance: {}", theatrePerformance);
        theatrePerformanceModel.setId(theatrePerformance.getId());
        theatrePerformanceModel.setTheatre(theatrePerformance.getTheatre());
        theatrePerformanceModel.setPerformance(theatrePerformance.getPerformance());
        log.info("TheatrePerformanceModel: {}", theatrePerformanceModel);


        return "redirect:/line_chart";
    }


    @GetMapping(value = "/line_chart")
    public String showLineChartForm(Model model,
                                    @ModelAttribute("theatrePerformanceModel") TheatrePerformance theatrePerformanceModel,
                                    @ModelAttribute Ticket ticket) {


        List<SimplePerformanceDateAndCost> performanceDateAndCostList = new ArrayList<>();
        for (Date performanceDate : scheduleService.getPerformanceDatesByTheatrePerformance(theatrePerformanceModel)) {
            Integer sumCosts = 0;
            Schedule schedule = scheduleService.getScheduleByTheatrePerformanceAndPerformanceDate(
                    theatrePerformanceModel, performanceDate);
            for (Ticket ticketForEjectCost : ticketService.getTicketBySchedule(schedule)) {
                sumCosts += ticketForEjectCost.getCost();
            }
            SimplePerformanceDateAndCost performanceDateAndCost = new SimplePerformanceDateAndCost();
            performanceDateAndCost.setPerformanceDate(performanceDate);
            performanceDateAndCost.setCost(sumCosts);
            performanceDateAndCostList.add(performanceDateAndCost);
        }


        String slist = JSON.toJSONString(performanceDateAndCostList);
        model.addAttribute("slist",slist);

        return "line_chart";
    }


}
