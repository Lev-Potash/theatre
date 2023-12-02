package com.example.theatre.controller;

import com.example.theatre.entity.*;
import com.example.theatre.service.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRSaver;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Data
@Controller
@SessionAttributes({"ticket", "theatreModel", "performanceModel",
        "theatrePerformanceModel", "scheduleModel",
        "simplePlaceObjModel", "simpleCountIntObjModel", "simpleTicketObjModel", "simpleTotalSumModel"})
@RequestMapping("/registration")
public class ClientsRegistrationController {

    private TheatreServiceImpl theatreService;
    private PerformanceService performanceService;

    private PlaceService placeService;

    private ClientService clientService;

    private TheatrePerformanceService theatrePerformanceService;

    private ScheduleService scheduleService;

    private PlaceTheatreService placeTheatreService;

    private SeatingService seatingService;

    private TicketService ticketService;

    @Autowired
//    @Qualifier("ticketService")
    @Lazy
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Autowired
//    @Qualifier("placeTheatreService")
    @Lazy
    public void setPlaceTheatreService(PlaceTheatreService placeTheatreService) {
        this.placeTheatreService = placeTheatreService;
    }

    @Autowired
//    @Qualifier("seatingService")
    @Lazy
    public void setSeatingService(SeatingService seatingService) {
        this.seatingService = seatingService;
    }

    @Autowired
//    @Qualifier("scheduleService")
    @Lazy
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Autowired
//    @Qualifier("theatrePerformanceService")
    @Lazy
    public void setTheatrePerformanceService(TheatrePerformanceService theatrePerformanceService) {
        this.theatrePerformanceService = theatrePerformanceService;
    }

    @Autowired
//    @Qualifier("placeService")
    @Lazy
    public void setPlaceService(PlaceService placeService) {
        this.placeService = placeService;
    }

    @Autowired
//    @Qualifier("theatreService")
    @Lazy
    public void setTheatreService(TheatreServiceImpl theatreService) {
        this.theatreService = theatreService;
    }

    @Autowired
//    @Qualifier("performanceService")
    @Lazy
    public void setPerformanceService(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    @Autowired
//    @Qualifier("clientService")
    @Lazy
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(produces = "text/html")
    public String showRegistrationForm(Model model) {
        Client client = new Client("Alex", "Frolov1", "lex1@ru.ru");
        model.addAttribute("client", client);
        model.addAttribute("pageTitle", "Регистрация клиента");
        return "registration-client";
    }

    @PostMapping
    public String processRegistration(@Valid Client client, Errors errors,
                                      @ModelAttribute Ticket ticket) {
        if (errors.hasErrors()) {
            log.error("has errors");
            return "registration-client";
        }
        log.info("Registration client: {}", client);
        clientService.save(client);
        ticket.setClient(client);
        return "redirect:/registration/theatre-performance";
    }

    @ModelAttribute("theatres")
    public List<Theatre> theatres() {
        return theatreService.getTheatres();
    }

    @ModelAttribute("performances")
    public List<Performance> performances() {
        return performanceService.getPerformanceList();
    }

    @GetMapping(value = "/theatre-performance")
    public String showTheatrePerformanceForm(Model model, Client client,
                                             @ModelAttribute Theatre theatre,
                                             @ModelAttribute Ticket ticket) {

        if (ticket.getClient() == null) {
            log.info("session is complete");
            return "redirect:/registration";
        }


        TheatrePerformance theatre_performance = new TheatrePerformance();
        Client client_ = ticket.getClient();
        log.info("---Client is:" + client_);
        log.info("---theatres is:" + theatreService.getTheatres());
        model.addAttribute("client", client_);
//        model.addAttribute("theatres", theatreService.getTheatres());
//        model.addAttribute("performances", performanceService.getPerformanceList());
        model.addAttribute("theatrePerformance", theatre_performance);
        model.addAttribute("theatre", new Theatre());
        model.addAttribute("performance", new Performance());
        model.addAttribute("pageTitle", "Театр-спектакль");


        return "theatre_performance";
    }

    @PostMapping(value = "theatre-performance")
    public String processTheatrePerformanceRegistration(@Valid @ModelAttribute("theatrePerformance") TheatrePerformance theatre_performance,
                                                        Errors errors,
                                                        @ModelAttribute("theatrePerformanceModel") TheatrePerformance theatrePerformanceModel,
                                                        @ModelAttribute("theatre") Theatre theatre,
                                                        Performance performance,
                                                        @ModelAttribute("theatreModel") Theatre theatreModel,
                                                        @ModelAttribute("performanceModel") Performance performanceModel,
                                                        @ModelAttribute Ticket ticket) {
        if (errors.hasErrors()) {
            log.error("Theatre Performance form has errors");
            return "theatre_performance";
        }

        log.info("Registration theatre: {}", theatre);
        theatreModel.setId(theatre.getId());
        theatreModel.setTheatreName(theatre.getTheatreName());

        performanceModel.setPerformanceName(performance.getPerformanceName());

        log.info("@ModelAttribute theatre_: {}", theatreModel);
        log.info("Registration performance: {}", performance);
        TheatrePerformance theatrePerformance = theatrePerformanceService.findTheatrePerformance(theatre, performance);
        log.info("registration TheatrePerformance: {}", theatrePerformance);
        theatrePerformanceModel.setId(theatrePerformance.getId());
        theatrePerformanceModel.setTheatre(theatrePerformance.getTheatre());
        theatrePerformanceModel.setPerformance(theatrePerformance.getPerformance());
        log.info("TheatrePerformanceModel: {}", theatrePerformanceModel);


        return "redirect:/registration/performance-date";
    }

    @ModelAttribute("performanceDates")
    public List<Date> performanceDates(
            @ModelAttribute("theatrePerformanceModel") TheatrePerformance theatrePerformance) {
        return scheduleService.getPerformanceDatesByTheatrePerformance(theatrePerformance);
    }

    @GetMapping(value = "/performance-date")
    public String showPerformanceDateForm(Model model, @ModelAttribute("theatreModel") Theatre theatre,
                                          @ModelAttribute("theatrePerformanceModel") TheatrePerformance theatrePerformance,
                                          @ModelAttribute Ticket ticket) {


        log.info("---");
        log.info("--- theatrePerformance: {}", theatrePerformance);
        if (theatrePerformance.getId() == null) {
            log.info("session is complete");
            return "redirect:/registration";
        }
        log.info("--- performanceDates: {}", scheduleService.getPerformanceDatesByTheatrePerformance(theatrePerformance));

//        model.addAttribute("performanceDates",
//                scheduleService.getPerformanceDatesByTheatrePerformance(theatrePerformance));
        model.addAttribute("schedule", new Schedule());
        model.addAttribute("pageTitle", "Дата спектакля");

        return "performance-date";
    }

    @PostMapping(value = "/performance-date")
    public String processPerformanceDateRegistration(@Valid Schedule receivedWithPerformanceDateSchedule,
                                                     Errors errors,
                                                     @ModelAttribute("scheduleModel") Schedule scheduleModel,
                                                     @ModelAttribute("theatrePerformanceModel") TheatrePerformance theatrePerformanceModel,
                                                     @ModelAttribute("theatreModel") Theatre theatre,
                                                     @ModelAttribute Ticket ticket) {
        log.info("Schedule performance date: {}", receivedWithPerformanceDateSchedule.getPerformanceDate());

        if (errors.hasErrors()) {
            log.error("performanceDate has error");
            return "performance-date";
        }

        Schedule schedule = scheduleService.getScheduleByTheatrePerformanceAndPerformanceDate(theatrePerformanceModel,
                receivedWithPerformanceDateSchedule.getPerformanceDate());

        // add performance date to scheduleModel for use to generated report
        scheduleModel.setPerformanceDate(receivedWithPerformanceDateSchedule.getPerformanceDate());
        scheduleModel.setPerformanceTime(
                scheduleService.getScheduleByTheatrePerformanceAndPerformanceDate(theatrePerformanceModel,
                        receivedWithPerformanceDateSchedule.getPerformanceDate()).getPerformanceTime());


        log.info("registration Schedule: {}", schedule);

        ticket.setSchedule(schedule);
        log.info("Ticket with Schedule: {}", ticket);

        return "redirect:/registration/places";
    }


    @GetMapping(value = "/places")
    public String showPlacesForm(Model model, @ModelAttribute("theatreModel") Theatre theatre,
                                 @ModelAttribute("theatrePerformanceModel") TheatrePerformance theatrePerformanceModel,
                                 @ModelAttribute Ticket ticket) {

        log.info("---");
        log.info("--- theatre: {}", theatre);
        if (theatre.getId() == null) {
            log.info("session is complete");
            return "redirect:/registration";
        }
        log.info("---");
        log.info("--- theatre: {}", theatre);
        log.info("--- Sorted places: " + getAndSortedPlaceById(theatrePerformanceModel,
                ticket.getSchedule()));

        model.addAttribute("rows", createRowsList(theatrePerformanceModel,
                ticket.getSchedule()));
        model.addAttribute("allPlaces", getAndSortedPlaceById(theatrePerformanceModel,
                ticket.getSchedule()));
//        model.addAttribute("occupiedPlaces", placeService.getOccupiedPlacesByTheatreNameAndPerformanceDate("Вестник", new Date(new GregorianCalendar(2023, 5, 10).getTime().getTime()))); /*String.valueOf(new GregorianCalendar(2023,5,11))*/
        model.addAttribute("simplePlaceObj", new SimplePlaceObj());
        model.addAttribute("pageTitle", "Бронирование мест");

        return "places";
    }

    private List<Place> getAndSortedPlaceById(TheatrePerformance theatrePerformance, Schedule schedule) {
        List<Place> result = placeService.getAllPlacesByTheatrePerformanceAndSchedule(theatrePerformance, schedule)
                .stream().sorted((id1, id2) ->
                        id1.getId().compareTo(id2.getId())
                ).collect(Collectors.toList());
        return result;
    }

    @PostMapping(value = "/places")
    public String processPlacesRegistration(SimplePlaceObj simplePlaceObj,
                                            @ModelAttribute("simplePlaceObjModel") SimplePlaceObj simplePlaceObjModel,
                                            @ModelAttribute("simpleCountIntObjModel") SimpleCountIntObj simpleCountIntObjModel,
                                            @ModelAttribute("theatreModel") Theatre theatre,
                                            @ModelAttribute Ticket ticket,
                                            SessionStatus sessionStatus) {
        if (!(simplePlaceObj.getPlaceList().isEmpty())) {
            log.info("place ids: {}", simplePlaceObj);
            // передаем simplePlaceObj simplePlaceObjModel
            simplePlaceObjModel.setPlaceList(simplePlaceObj.getPlaceList());
            // вычисляем выбранные места
            int countChooseOccupiedPlaces = getCountChoosePlacesByList(simplePlaceObjModel);
            simpleCountIntObjModel.setCount(countChooseOccupiedPlaces);
            return "redirect:/registration/ticket-cost";
        } else {
            return "redirect:/registration/places";

        }

  /*      if (!(simplePlaceObj.getPlaceList() == null)) {
            log.info("place ids: {}", simplePlaceObj);
            for (Place place :
                    simplePlaceObj.getPlaceList()) {
                log.info("Place: {}", place);
                log.info("Theatre: {}", theatre);
                PlaceTheatre placeTheatre = placeTheatreService.getPlaceTheatreByPlaceAndTheatre(place, theatre);
                log.info("PlaceTheatre: {}", placeTheatre);
                Schedule schedule = ticket.getSchedule();
                log.info("Schedule by ticket: {}", schedule);
                Seating seating = seatingService.getSeatingByPlaceTheatreAndSchedule(placeTheatre, schedule);
                if (!(seating == null)) {
                    Ticket ticketForSaving = new Ticket();
                    log.info("Seating: {}", seating);
                    seating.setOccupied(true);
                    log.info("Seating occupied set true: {}", seating);
                    seatingService.saveSeating(seating);
                    ticketForSaving.setSchedule(ticket.getSchedule());
                    ticketForSaving.setClient(ticket.getClient());
                    ticketForSaving.setPlace_theatre(seating.getPlaceTheatre());
                    ticketForSaving.setCost(350);
                    log.info("Ticket for saving: {}", ticketForSaving);
                    Ticket savedTicket = ticketService.save(ticketForSaving);
                    log.info("Saved ticket: {}", savedTicket);
                    sessionStatus.setComplete(); // сеанс будет очищен
                } else {
                    log.error("Seating is not found in database. " +
                            "Add information about Schedule and PlaceTheatre.");
                    break;
                }
            }
        } else {
            log.error("Don't choose places by place list.");
        }
*/

//        return "redirect:/registration";
    }

    private int getCountChoosePlacesByList(SimplePlaceObj simplePlaceObjModel) {
        int count = 0;
        for (Place place :
                simplePlaceObjModel.getPlaceList()) {
            count++;
        }
        return count;
    }


    private List<Integer> createRowsList(TheatrePerformance theatrePerformance, Schedule schedule) {
        Set<Integer> rows = new HashSet<>();

        for (Place placeRow :
                placeService.getAllPlacesByTheatrePerformanceAndSchedule(theatrePerformance, schedule)
        ) {
            rows.add(placeRow.getRow());
        }

        log.info("rows: {}", rows.stream().sorted().collect(Collectors.toList()));

        return rows.stream().sorted().collect(Collectors.toList());
    }


    @GetMapping(value = "/ticket-cost")
    public String showTicketCostForm(Model model, @ModelAttribute("theatreModel") Theatre theatre,
                                     @ModelAttribute("theatrePerformanceModel") TheatrePerformance theatrePerformanceModel,
                                     @ModelAttribute Ticket ticket) {

        log.info("---");
        log.info("--- theatre: {}", theatre);
        if (theatre.getId() == null) {
            log.info("session is complete");
            return "redirect:/registration";
        }
        log.info("---");
        log.info("--- theatre: {}", theatre);
        log.info("--- Sorted places: " + getAndSortedPlaceById(theatrePerformanceModel,
                ticket.getSchedule()));

//        model.addAttribute("rows", createRowsList(theatrePerformanceModel,
//                ticket.getSchedule()));
//        model.addAttribute("allPlaces", getAndSortedPlaceById(theatrePerformanceModel,
//                ticket.getSchedule()));
////        model.addAttribute("occupiedPlaces", placeService.getOccupiedPlacesByTheatreNameAndPerformanceDate("Вестник", new Date(new GregorianCalendar(2023, 5, 10).getTime().getTime()))); /*String.valueOf(new GregorianCalendar(2023,5,11))*/
//        model.addAttribute("simplePlaceObj", new SimplePlaceObj());
        model.addAttribute("pageTitle", "Стоимость билета");

        return "ticket-cost";
    }


    @PostMapping(value = "/ticket-cost")
    public String processTicketCostRegistration(@ModelAttribute("simplePlaceObjModel") SimplePlaceObj simplePlaceObjModel,
                                                @ModelAttribute("simpleCountIntObjModel") SimpleCountIntObj simpleCountIntObjModel,
                                                @ModelAttribute("theatreModel") Theatre theatre,
                                                @ModelAttribute Ticket ticket,
                                                @ModelAttribute("simpleTicketObjModel") SimpleTicketObj simpleTicketObjModel,
                                                @ModelAttribute("simpleTotalSumModel") SimpleTotalSum simpleTotalSumModel,
                                                SessionStatus sessionStatus) {

        List<Ticket> ticketListForSavedTicket = new ArrayList<>();

        if (!(simplePlaceObjModel.getPlaceList() == null)) {
            log.info("place ids: {}", simplePlaceObjModel);
            for (Place place :
                    simplePlaceObjModel.getPlaceList()) {
                log.info("Place: {}", place);
                log.info("Theatre: {}", theatre);
                PlaceTheatre placeTheatre = placeTheatreService.getPlaceTheatreByPlaceAndTheatre(place, theatre);
                log.info("PlaceTheatre: {}", placeTheatre);
                Schedule schedule = ticket.getSchedule();
                log.info("Schedule by ticket: {}", schedule);
                Seating seating = seatingService.getSeatingByPlaceTheatreAndSchedule(placeTheatre, schedule);
                if (!(seating == null)) {
                    Ticket ticketForSaving = new Ticket();
                    log.info("Seating: {}", seating);
                    seating.setOccupied(true);
                    log.info("Seating occupied set true: {}", seating);
                    seatingService.saveSeating(seating);
                    ticketForSaving.setSchedule(ticket.getSchedule());
                    ticketForSaving.setClient(ticket.getClient());
                    ticketForSaving.setPlace_theatre(seating.getPlaceTheatre());
                    ticketForSaving.setCost(350);
                    log.info("Ticket for saving: {}", ticketForSaving);
                    Ticket savedTicket = ticketService.save(ticketForSaving);
                    // добавляем все наши сохраненные билеты в список для дальнейшего их использования в переменных сессии
                    ticketListForSavedTicket.add(savedTicket);
                    log.info("Saved ticket: {}", savedTicket);
//                    sessionStatus.setComplete(); // сеанс будет очищен
                } else {
                    log.error("Seating is not found in database. " +
                            "Add information about Schedule and PlaceTheatre.");
                    break;
                }
            }
            // вычисляем сумму всех билетов
            Integer totalSum = 0;
            for (Ticket ticketForСalculatedSum : ticketListForSavedTicket) {
                totalSum += ticketForСalculatedSum.getCost();
            }
            simpleTotalSumModel.setTotalSum(totalSum);

            // добавляем все сохраненные билеты в переменную сессии для формирования отчета
            simpleTicketObjModel.setTicketList(ticketListForSavedTicket);

        } else {
            log.error("Don't choose places by place list.");
        }

        return "redirect:/registration/finish_booking";
    }


    @GetMapping(value = "/finish_booking")
    public String showFinishBookingForm(Model model) {

        model.addAttribute("pageTitle", "Окончание бронирования");

        return "finish-booking";
    }


    @PostMapping(value = "/report")
    public String processGetReportForFinishBookingForm(@RequestParam("fileFormat") String fileFormat,
                                                       @ModelAttribute("theatreModel") Theatre theatreModel,
                                                       @ModelAttribute("simplePlaceObjModel") SimplePlaceObj simplePlaceObjModel,
                                                       @ModelAttribute("performanceModel") Performance performanceModel,
                                                       @ModelAttribute("scheduleModel") Schedule scheduleModel,
                                                       @ModelAttribute("simpleTicketObjModel") SimpleTicketObj simpleTicketObjModel,
                                                       @ModelAttribute("simpleTotalSumModel") SimpleTotalSum simpleTotalSumModel,
                                                       @ModelAttribute Ticket ticket) throws JRException, IOException {

//        List<CommonTheatreReportObject> commonTheatreReportObjects = new ArrayList<>();
//        List<Client> clients = clientService.findAllClients();
//        List<Performance> performances = performanceService.getPerformanceList();
//        List<Place> places = placeService.getAllPlaces();
//        List<PlaceTheatre> placeTheatres = placeTheatreService.getAllPlaceTheatres();
//        List<Schedule> schedules = scheduleService.getAllSchedules();
//        List<Theatre> theatres = theatreService.getTheatres();
//        List<TheatrePerformance> theatrePerformances = theatrePerformanceService.getAllTheatrePerformance();
//        List<Ticket> tickets = ticketService.getAllTickets();

        System.out.println("Создаем commonTheatreReportObjects");
        List<CommonTheatreReportObject> commonTheatreReportObjects = new ArrayList<>();
//        List<Client> clients = clientService.findAllClients();
        List<Client> clients = new ArrayList<>();
        clients.add(ticket.getClient());
//        List<Performance> performances = performanceService.getPerformanceList();
        List<Performance> performances = new ArrayList<>();

//        List<Place> places = placeService.getAllPlaces();
        List<Schedule> schedules = new ArrayList<>();

        List<Place> places = new ArrayList<>();
        for (Place place : simplePlaceObjModel.getPlaceList()) {
            places.add(new Place(place.getRow(), place.getPlace()));
            performances.add(new Performance(performanceModel.getPerformanceName()));
            schedules.add(new Schedule(scheduleModel.getTheatrePerformance(), scheduleModel.getPerformanceDate(),
                    scheduleModel.getPerformanceTime()));
        }


        List<PlaceTheatre> placeTheatres = placeTheatreService.getAllPlaceTheatres();
//        List<Schedule> schedules = scheduleService.getAllSchedules();


        List<Theatre> theatres = new ArrayList<>();
        theatres.add(new Theatre(theatreModel.getTheatreName()));

        List<TheatrePerformance> theatrePerformances = theatrePerformanceService.getAllTheatrePerformance();
//        List<Ticket> tickets = ticketService.getAllTickets();
        List<Ticket> tickets = new ArrayList<>();
        for (Ticket ticketForInsert : simpleTicketObjModel.getTicketList()) {
            tickets.add(ticketForInsert);
        }

        List<SimpleTotalSum> simpleTotalSumList = new ArrayList<>();
        simpleTotalSumList.add(simpleTotalSumModel);

        System.out.println("Добавляем данные в объект commonTheatreReportObjects");

        commonTheatreReportObjects.add(new CommonTheatreReportObject());

        commonTheatreReportObjects.get(0).setClients(clients);
        commonTheatreReportObjects.get(0).setPerformances(performances);
        commonTheatreReportObjects.get(0).setPlaces(places);
//        commonTheatreReportObjects.get(0).setPlaceTheatres(placeTheatres);
        commonTheatreReportObjects.get(0).setSchedules(schedules);
        commonTheatreReportObjects.get(0).setTheatres(theatres);
//        commonTheatreReportObjects.get(0).setTheatrePerformances(theatrePerformances);
        commonTheatreReportObjects.get(0).setTickets(tickets);
        commonTheatreReportObjects.get(0).setSimpleTotalSumList(simpleTotalSumList);

        System.out.println("Добавили данные в объект commonTheatreReportObjects");

        System.out.println("Создаем параметры для использования в отчете");

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("theatre_name", theatreModel.getTheatreName());
        parameters.put("performance_name", performanceModel.getPerformanceName());
        parameters.put("performance_date", scheduleModel.getPerformanceDate());
        parameters.put("name", ticket.getClient().getName());
        parameters.put("surname", ticket.getClient().getSurname());
        parameters.put("email", ticket.getClient().getEmail());

        System.out.println("****Процесс получения ссылки на файл. generateReport(commonTheatreReportObjects, parameters, fileFormat)");
        String fileLink = generateReport(commonTheatreReportObjects, parameters, fileFormat);
        System.out.println("Выполняем редирект на localhost:9090/generated-reports/theatre.pdf");
        return "redirect:/" + fileLink;
    }


    private JasperPrint getJasperPrint(List<CommonTheatreReportObject> commonTheatreReportObjects,
                                       Map<String, Object> parameters,
                                       String resourceLocationMasterReport, String resourceLocationTheatresSubreport) throws IOException, JRException {


        /*String masterReportFileName = "C://tools/jasperreports-5.0.1/test"
                + "/jasper_report_template.jrxml";
        String subReportFileName = "C://tools/jasperreports-5.0.1/test"
                + "/AddressReport.jrxml";
        String destFileName = "C://tools/jasperreports-5.0.1/test"
                + "/jasper_report_template.JRprint";

        DataBeanList DataBeanList = new DataBeanList();
        ArrayList<DataBean> dataList = DataBeanList.getDataBeanList();
        JRBeanCollectionDataSource beanColDataSource = new
                JRBeanCollectionDataSource(dataList);

        try {
            *//* Compile the master and sub report *//*
            JasperReport jasperMasterReport = JasperCompileManager
                    .compileReport(masterReportFileName);
            JasperReport jasperSubReport = JasperCompileManager
                    .compileReport(subReportFileName);

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("subreportParameter", jasperSubReport);
            JasperFillManager.fillReportToFile(jasperMasterReport,
                    destFileName, parameters, beanColDataSource);*/


        System.out.println("Получаем файл classpath:theatre.jrxml");
        System.out.println("Копируем файл classpath:theatre.jrxml в файл D:\\theatre.jrxml");
        //Get inu template file
        Resource classPathResourceTheatre = new ClassPathResource("theatre.jrxml");
//        Path theatrePath = Paths.get("/theatre.jrxml");
        File fileMasterReport = Paths.get("/theatre.jrxml").toFile();
//        File fileMasterReport = new File("D:\\theatre.jrxml");
        FileUtils.copyInputStreamToFile(classPathResourceTheatre.getInputStream(), fileMasterReport);

   // classpath:theatre.jrxml
        //File fileMasterReport = ResourceUtils.getFile(resourceLocationMasterReport);
        System.out.println("Компилируем файл classpath:theatre.jrxml");
        JasperReport jasperReport = JasperCompileManager
                .compileReport(fileMasterReport.getAbsolutePath());



        System.out.println("Получаем файл classpath:theatre_subreport.jrxml");
//        File fileTheatresSubReport = ResourceUtils.getFile(resourceLocationTheatresSubreport);
        System.out.println("Копируем файл classpath:Theatres_subreport.jrxml в файл D:\\Theatres_subreport.jrxml");
        //Get inu template file
        Resource classPathResourceTheatreSubreport = new ClassPathResource("Theatres_subreport.jrxml");
//        Path theatrePath = Paths.get("/theatre.jrxml");
        File fileTheatresSubReport = Paths.get("/Theatres_subreport.jrxml").toFile();
//        File fileTheatresSubReport = new File("D:\\theatre_subreport.jrxml");
        FileUtils.copyInputStreamToFile(classPathResourceTheatreSubreport.getInputStream(), fileTheatresSubReport);

        System.out.println("Компилируем файл classpath:Theatres_subreport.jrxml");
        JasperReport jasperSubReport = JasperCompileManager
                .compileReport(fileTheatresSubReport.getAbsolutePath());
        System.out.println("Сохраняем подотчет в файл Theatres_subreport.jasper");
        JRSaver.saveObject(jasperSubReport, "Theatres_subreport.jasper");



        System.out.println("Получаем файл classpath:Places_subreport.jrxml");
//        File filePlacesSubReport = ResourceUtils.getFile("classpath:Places_subreport.jrxml");
        System.out.println("Копируем файл classpath:Places_subreport.jrxml в файл D:\\Places_subreport.jrxml");
        Resource classPathResourcePlacesSubreport = new ClassPathResource("Places_subreport.jrxml");
        File filePlacesSubReport = Paths.get("/Places_subreport.jrxml").toFile();
        FileUtils.copyInputStreamToFile(classPathResourcePlacesSubreport.getInputStream(), filePlacesSubReport);

        System.out.println("Компилируем файл Places_subreport.jrxml");
        JasperReport jasperSubReportPlaces = JasperCompileManager
                .compileReport(filePlacesSubReport.getAbsolutePath());
        System.out.println("Сохраняем подотчет в файл Places_subreport.jasper");
        JRSaver.saveObject(jasperSubReportPlaces, "Places_subreport.jasper");



        System.out.println("Получаем файл classpath:Performances_subreport.jrxml");
//        File filePerformancesSubReport = ResourceUtils.getFile("classpath:Performances_subreport.jrxml");
        System.out.println("Копируем файл classpath:Performances_subreport.jrxml в файл D:\\Performances_subreport.jrxml");
        Resource classPathResourcePerformancesSubreport = new ClassPathResource("Performances_subreport.jrxml");
        File filePerformancesSubReport = Paths.get("/Performances_subreport.jrxml").toFile();
        FileUtils.copyInputStreamToFile(classPathResourcePerformancesSubreport.getInputStream(), filePerformancesSubReport);

        System.out.println("Компилируем файл Performances_subreport.jrxml");
        JasperReport jasperSubReportPerformances = JasperCompileManager
                .compileReport(filePerformancesSubReport.getAbsolutePath());
        System.out.println("Сохраняем подотчет в файл Performances_subreport.jasper");
        JRSaver.saveObject(jasperSubReportPerformances, "Performances_subreport.jasper");



        System.out.println("Получаем файл classpath:Schedules_subreport.jrxml");
//        File fileSchedulesSubReport = ResourceUtils.getFile("classpath:Schedules_subreport.jrxml");
        System.out.println("Копируем файл classpath:Schedules_subreport.jrxml в файл D:\\Schedules_subreport.jrxml");
        Resource classPathResourceSchedulesSubreport = new ClassPathResource("Schedules_subreport.jrxml");
        File fileSchedulesSubReport = Paths.get("/Schedules_subreport.jrxml").toFile();
        FileUtils.copyInputStreamToFile(classPathResourceSchedulesSubreport.getInputStream(), fileSchedulesSubReport);

        System.out.println("Компилируем файл Schedules_subreport.jrxml");
        JasperReport jasperSubReportSchedules = JasperCompileManager
                .compileReport(fileSchedulesSubReport.getAbsolutePath());
        System.out.println("Сохраняем подотчет в файл Schedules_subreport.jasper");
        JRSaver.saveObject(jasperSubReportSchedules, "Schedules_subreport.jasper");



        System.out.println("Получаем файл classpath:Tickets_subreport.jrxml");
//        File fileTicketsSubReport = ResourceUtils.getFile("classpath:Tickets_subreport.jrxml");
        System.out.println("Копируем файл classpath:Tickets_subreport.jrxml в файл D:\\Tickets_subreport.jrxml");
        Resource classPathResourceTicketsSubreport = new ClassPathResource("Tickets_subreport.jrxml");
        File fileTicketsSubReport = Paths.get("/Tickets_subreport.jrxml").toFile();
        FileUtils.copyInputStreamToFile(classPathResourceTicketsSubreport.getInputStream(), fileTicketsSubReport);

        System.out.println("Компилируем файл Tickets_subreport.jrxml");
        JasperReport jasperSubReportTickets = JasperCompileManager
                .compileReport(fileTicketsSubReport.getAbsolutePath());
        System.out.println("Сохраняем подотчет в файл Tickets_subreport.jasper");
        JRSaver.saveObject(jasperSubReportTickets, "Tickets_subreport.jasper");



        System.out.println("Получаем файл classpath:TotalSumList_subreport.jrxml");
//        File fileTotalSumListSubReport = ResourceUtils.getFile("classpath:TotalSumList_subreport.jrxml");
        System.out.println("Копируем файл classpath:TotalSumList_subreport.jrxml в файл D:\\TotalSumList_subreport.jrxml");
        Resource classPathResourceTotalSumListSubreport = new ClassPathResource("TotalSumList_subreport.jrxml");
        File fileTotalSumListSubReport = Paths.get("/TotalSumList_subreport.jrxml").toFile();
        FileUtils.copyInputStreamToFile(classPathResourceTotalSumListSubreport.getInputStream(), fileTotalSumListSubReport);

        System.out.println("Компилируем файл TotalSumList_subreport.jrxml");
        JasperReport jasperSubReportTotalSumList = JasperCompileManager
                .compileReport(fileTotalSumListSubReport.getAbsolutePath());
        System.out.println("Сохраняем подотчет в файл TotalSumList_subreport.jasper");
        JRSaver.saveObject(jasperSubReportTotalSumList, "TotalSumList_subreport.jasper");



        System.out.println("Получаем файл classpath:Clients_subreport.jrxml");
//        File fileClientsSubReport = ResourceUtils.getFile("classpath:Clients_subreport.jrxml");
        System.out.println("Копируем файл classpath:Clients_subreport.jrxml в файл D:\\Clients_subreport.jrxml");
        Resource classPathResourceClientsSubreport = new ClassPathResource("Clients_subreport.jrxml");
        File fileClientsSubReport = Paths.get("/Clients_subreport.jrxml").toFile();
        FileUtils.copyInputStreamToFile(classPathResourceClientsSubreport.getInputStream(), fileClientsSubReport);

        System.out.println("Компилируем файл Clients_subreport.jrxml");
        JasperReport jasperSubReportClients = JasperCompileManager
                .compileReport(fileClientsSubReport.getAbsolutePath());
        System.out.println("Сохраняем подотчет в файл Clients_subreport.jasper");
        JRSaver.saveObject(jasperSubReportClients, "Clients_subreport.jasper");


        //todo: написать объект общий для всех сущностей, кроме Seating
        //todo: записать в commonTheatreReportObjects значения из других сущностей по видео Dynamic Parameters Multiple List Bean Collection Jasper Report | Using JRBeanCollectionDataSource

        System.out.println("Составляем JRBeanCollectionDataSource datasource для отчета");
        // составляем datasource для отчета
        JRBeanCollectionDataSource dataSource = new
                JRBeanCollectionDataSource(commonTheatreReportObjects);
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("createdBy","David");


        System.out.println("Копируем fonts.xml");
        Resource classPathResourceFontsXml = new ClassPathResource("/fonts/fonts.xml");
        File fileFontsXml = Paths.get("/etc/fonts/fonts.xml").toFile();

        FileUtils.copyInputStreamToFile(classPathResourceFontsXml.getInputStream(), fileFontsXml);
        System.out.println ("Path to file fileFontsXml: " + fileFontsXml.getAbsolutePath());

        System.out.println("Копируем шрифты .ttf");
        Resource classPathResourceDejaVuSerifTtf = new ClassPathResource("/fonts/dejavuserif/DejaVuSerif.ttf");
        File fileDejaVuSerifTtf = Paths.get("/etc/fonts/dejavuserif/DejaVuSerif.ttf").toFile();
        FileUtils.copyInputStreamToFile(classPathResourceDejaVuSerifTtf.getInputStream(), fileDejaVuSerifTtf);

        Resource classPathResourceDejaVuSerifBoldTtf = new ClassPathResource("/fonts/dejavuserif/DejaVuSerif-Bold.ttf");
        File fileDejaVuSerifBoldTtf = Paths.get("/etc/fonts/dejavuserif/DejaVuSerif-Bold.ttf").toFile();
        FileUtils.copyInputStreamToFile(classPathResourceDejaVuSerifBoldTtf.getInputStream(), fileDejaVuSerifBoldTtf);

        Resource classPathResourceDejaVuSerifBoldItalicTtf = new ClassPathResource("/fonts/dejavuserif/DejaVuSerif-BoldItalic.ttf");
        File fileDejaVuSerifBoldItalicTtf = Paths.get("/etc/fonts/dejavuserif/DejaVuSerif-BoldItalic.ttf").toFile();
        FileUtils.copyInputStreamToFile(classPathResourceDejaVuSerifBoldItalicTtf.getInputStream(), fileDejaVuSerifBoldItalicTtf);

        Resource classPathResourceDejaVuSerifItalicTtf = new ClassPathResource("/fonts/dejavuserif/DejaVuSerif-Italic.ttf");
        File fileDejaVuSerifItalicTtf = Paths.get("/etc/fonts/dejavuserif/DejaVuSerif-Italic.ttf").toFile();
        FileUtils.copyInputStreamToFile(classPathResourceDejaVuSerifItalicTtf.getInputStream(), fileDejaVuSerifItalicTtf);


        System.out.println("Формируем отчет");
        // формируем отчет
        JasperPrint jasperPrint = JasperFillManager
                .fillReport(jasperReport, /*null*/ parameters, dataSource);
        System.out.println("Возвращаем сформированный отчет");
        return jasperPrint;
    }


    /*
     * Этот метод создает указанный каталог, если он не существует.
     * Он также создает сгенерированный PDF-файл в папке с переданным ему именем файла.
     * */
    private Path getUploadPath(String fileFormat, JasperPrint jasperPrint, String fileName) throws IOException, JRException {
        //StringUtils.cleanPath - нормализует путь, подставив такие последовательности,
        // как «путь/..» и внутренние простые точки.
        System.out.println("Задаем путь ./generated-reports по которому будет искаться файл");
        String uploadDirURL = StringUtils.cleanPath("./generated-reports");
        System.out.println("Получаем путь в Path методом Paths.get(uploadDirURL)");
        Path uploadURLPath = Paths.get(uploadDirURL);
//        String uploadDir = StringUtils.cleanPath("D:/generated-reports");

        System.out.println("Задаем путь /generated-reports для создания и генерации pdf файла");
        String uploadDir = "/generated-reports";
        System.out.println("Получаем путь в Path методом Paths.get(uploadDir)");
        Path uploadPath = Paths.get(uploadDir); // создание объекта Path
        System.out.println("Создаем папку /generated-reports если ее нет");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        //generate the report and save it in the just created folder
        if (fileFormat.equalsIgnoreCase("pdf")) {
//            JasperExportManager.exportReportToPdfFile(jasperPrint, "./" + uploadPath + fileName);
        System.out.println("Генерируем отчет в pdf файл и сохраняем его в созданнной папке");
            JasperExportManager.exportReportToPdfFile(jasperPrint, uploadPath + fileName);

        }

        System.out.println("Возвращаем URL cсылку пути по которому ищем сгенерированный файл");
//        return uploadPath;
        return uploadURLPath;
    }


    /*
     * Метод с именем getPdfFileLink() возвращает ссылку на наш сгенерированный отчет.
     * Этот метод примет путь к файлу, возвращаемый методом getUploadPath(), в качестве строкового аргумента.
     * */
    private String getPdfFileLink(String uploadPath) {
        return uploadPath + "/" + "theatre.pdf";
    }


    public String generateReport(/*LocalDate localDate,*/
            List<CommonTheatreReportObject> commonTheatreReportObjects,
            Map<String, Object> parameters, String fileFormat) throws JRException, IOException {
//        List<Product> phoneCollection = productRepository.findAllByCreatedAt(localDate);

        System.out.println("Создаем файлы classpath:theatre.jrxml и classpath:Theatres_subreport.jrxml");
        //load the file and compile it
        String resourceLocationMasterReport = "classpath:theatre.jrxml";
        String resourceLocationTheatresSubreport = "classpath:Theatres_subreport.jrxml";


        System.out.println("----Формируем отчет через JasperPrint. getJasperPrint(commonTheatreReportObjects, parameters,\n" +
                "                resourceLocationMasterReport, resourceLocationTheatresSubreport)");
        // получает сформированный отчет
        JasperPrint jasperPrint = getJasperPrint(commonTheatreReportObjects, parameters,
                resourceLocationMasterReport, resourceLocationTheatresSubreport);
        System.out.println("----Создаем папку для хранения отчета /theatre.pdf. getUploadPath(fileFormat, jasperPrint, fileName)");
        //create a folder to store the report
        String fileName = "/" + "theatre.pdf";
        Path uploadPath = getUploadPath(fileFormat, jasperPrint, fileName);

        //create a private method that returns the link to the specific pdf file
        System.out.println("создает метод getPdfFileLink(uploadPath.toString()) котрый возвращает ссылку указывающую на pdf файл /generated-reports/theatre.pdf ");

        return getPdfFileLink(uploadPath.toString());
    }


    @ModelAttribute("ticket")
    public Ticket ticket() {
        return new Ticket();
    }

    @ModelAttribute("theatreModel")
    public Theatre theatre() {
        return new Theatre();
    }

    @ModelAttribute("performanceModel")
    public Performance performance() {
        return new Performance();
    }

    @ModelAttribute("scheduleModel")
    public Schedule schedule() {
        return new Schedule();
    }

    //    @ModelAttribute("performanceDateModel")
//    public Date performanceDate() {
//        return new Date();
//    }
//
    @ModelAttribute("theatrePerformanceModel")
    public TheatrePerformance theatrePerformance() {
        return new TheatrePerformance();
    }

    @ModelAttribute("simplePlaceObjModel")
    public SimplePlaceObj simplePlaceObj() {
        return new SimplePlaceObj();
    }

    @ModelAttribute("simpleCountIntObjModel")
    public SimpleCountIntObj simpleCountIntObj() {
        return new SimpleCountIntObj();
    }

    @ModelAttribute("simpleTicketObjModel")
    public SimpleTicketObj simpleTicketObj() {
        return new SimpleTicketObj();
    }

    @ModelAttribute("simpleTotalSumModel")
    public SimpleTotalSum simpleTotalSum() {
        return new SimpleTotalSum();
    }

}
