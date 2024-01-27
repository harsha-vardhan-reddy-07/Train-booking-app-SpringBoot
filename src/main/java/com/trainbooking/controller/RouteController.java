package com.trainbooking.controller;

import com.trainbooking.models.BookingModel;
import com.trainbooking.models.TrainModel;
import com.trainbooking.models.UserModel;
import com.trainbooking.repos.BookingRepo;
import com.trainbooking.repos.TrainRepo;
import com.trainbooking.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@Controller
public class RouteController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    TrainRepo trainRepo;

    @Autowired
    BookingRepo bookingRepo;

    @RequestMapping("/")
    public ModelAndView landingPage(  ){
        List<Object> trains = new ArrayList<>();
        return new ModelAndView("landing", "trains", trains);
    }

    @RequestMapping("/fetch-trains")
    public ModelAndView fetchTrainsPage( @ModelAttribute TrainModel trainData ){

        List <TrainModel> allTrains = trainRepo.findAll();

        List<TrainModel> filteredTrains = trainRepo.findAll();

        if (Objects.equals(trainData.getArrivalTime(), "")){
            filteredTrains = allTrains.stream()
                    .filter(train -> trainData.getOrigin().equals(train.getOrigin()) && trainData.getDestination().equals(train.getDestination()))
                    .collect(Collectors.toList());
        }else{
            filteredTrains = allTrains.stream()
                    .filter(train -> ((trainData.getOrigin().equals(train.getOrigin()) && trainData.getDestination().equals(train.getDestination())) || (trainData.getOrigin().equals(train.getDestination()) && trainData.getDestination().equals(train.getOrigin()))) )
                    .collect(Collectors.toList());
        }


        return new ModelAndView("landing", "trains", filteredTrains);
    }

    @RequestMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    @PostMapping("/login-user")
    public UserModel loginUser(@ModelAttribute UserModel userData, Model model) {
        try {
            UserModel user = userRepo.findByEmail(userData.getEmail());

            if (user.getPassword().equals(userData.getPassword())) {
                // Return userData as JSON response
                return user;
            }
            return null;
        } catch (Exception e) {
            // Handle any exceptions and return an error response
            return null;
        }
    }

    @RequestMapping("/register")
    public ModelAndView registerPage() {
        return new ModelAndView("register");
    }

    @RequestMapping("/register-user")
    public UserModel registerUser(@ModelAttribute UserModel userData, Model model) {
        try {

            UserModel user = userRepo.save(userData);
            return user;

        } catch (Exception e) {

            return null;
        }
    }

    @RequestMapping("/book-train/{id}")
    public ModelAndView bookTrainPage(@PathVariable("id") String id) {

        Optional<TrainModel> trainInfo = trainRepo.findById(id);
        TrainModel train = trainInfo.get();
        return new ModelAndView("user/book-train", "train", train);
    }

    @RequestMapping("/book-ticket/{id}")
    public BookingModel ticketBooking(@PathVariable("id") String id, @ModelAttribute BookingModel bookingData, Model model ) {

        Optional<TrainModel> trainInfo = trainRepo.findById(id);

        TrainModel train = trainInfo.get();

        DateTimeFormatter currentDate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        bookingData.setTrain(id);
        bookingData.setTrainName(train.getTrainName());
        bookingData.setTrainNumber(train.getTrainNumber());
        bookingData.setStartStation(train.getOrigin());
        bookingData.setDestinationStation(train.getDestination());
        bookingData.setBookingDate(currentDate.format(now));
        bookingData.setBookingStatus("confirmed");

        String seat = "";

        Dictionary<String, String> seatType= new Hashtable<>();
        seatType.put("seater", "G");
        seatType.put("sleeper", "S");
        seatType.put("3ac", "C");
        seatType.put("2ac", "B");
        seatType.put("1ac", "A");



        for(int i = 0; i <= bookingData.getTicketsCount().intValue(); i++){
            seat  = seat + seatType.get(bookingData.getCoachClass()) + Integer.toString(i) + ',';
        }

        bookingData.setSeats(seat);

        BookingModel book =  bookingRepo.save(bookingData);

        return book;
    }



    @RequestMapping("/bookings/{id}")
    public ModelAndView userBookingPage(@PathVariable("id") String id) {

        List < BookingModel> bookings = bookingRepo.findAll();

        List <BookingModel> filteredBookings = bookings.stream()
                .filter(booking -> id.equals(booking.getUser()))
                .collect(Collectors.toList());

        return new ModelAndView("user/user-bookings", "bookings", filteredBookings);
    }


    @RequestMapping("/admin")
    public ModelAndView adminPage() {

        List <UserModel> users = userRepo.findAll();

        List <TrainModel> trains = trainRepo.findAll();

        List <BookingModel> bookings = bookingRepo.findAll();

        ModelMap modelMap = new ModelMap();

        modelMap.addAttribute("users", users);
        modelMap.addAttribute("trains", trains);
        modelMap.addAttribute("bookings", bookings);

        return new ModelAndView("admin/admin", modelMap);
    }


    @RequestMapping("/all-bookings")
    public ModelAndView allBookingsPage() {

        List < BookingModel> bookings = bookingRepo.findAll();

        return new ModelAndView("admin/all-bookings", "bookings", bookings);
    }

    @RequestMapping("/cancel-ticket/{id}")
    public ModelAndView cancelTicket(@PathVariable("id") String id) {

            Optional<BookingModel> booking = bookingRepo.findById(id);

            BookingModel bookingInfo = booking.get();

            System.out.println(bookingInfo);
            bookingInfo.setBookingStatus("cancelled");
            BookingModel book = bookingRepo.save(bookingInfo);
            return new ModelAndView("redirect:/all-bookings");

    }

    @RequestMapping("/cancel-user-ticket/{id}")
    public ModelAndView cancelUserTicket(@PathVariable("id") String id) {

        Optional<BookingModel> booking = bookingRepo.findById(id);

        BookingModel bookingInfo = booking.get();

        System.out.println(bookingInfo);
        bookingInfo.setBookingStatus("cancelled");
        BookingModel book = bookingRepo.save(bookingInfo);

        String data = "redirect:/bookings/" + bookingInfo.getUser();

        return new ModelAndView(data);

    }


    @RequestMapping("/all-trains")
    public ModelAndView allTrainsPage() {

        List < TrainModel> trains = trainRepo.findAll();

        return new ModelAndView("admin/all-trains", "trains", trains);
    }


    @RequestMapping("/all-users")
    public ModelAndView allUsersPage() {

        List <UserModel> users = userRepo.findAll();

        return new ModelAndView("admin/all-users", "users", users);
    }

    @RequestMapping("/new-train")
    public ModelAndView newTrainPage() {
        return new ModelAndView("admin/new-train");
    }

    @RequestMapping("/add-train")
    public TrainModel addTrainPage(@ModelAttribute TrainModel trainData) {

        try {

            TrainModel train = trainRepo.save(trainData);
            return train;

        } catch (Exception e) {

            return null;
        }
    }

    @RequestMapping("/edit-train/{id}")
    public ModelAndView editTrainPage(@PathVariable("id") String id) {

        Optional<TrainModel> trainData = trainRepo.findById(id);

        TrainModel train = trainData.get();

        return new ModelAndView("admin/edit-train", "train", train);
    }

    @RequestMapping("/update-train/{id}")
    public TrainModel updateTrainPage(@PathVariable("id") String id, @ModelAttribute TrainModel trainData, Model model ) {

        Optional<TrainModel> trainInfo = trainRepo.findById(id);

        TrainModel train = trainInfo.get();

        train.setTrainName(trainData.getTrainName());
        train.setTrainNumber(trainData.getTrainNumber());
        train.setOrigin(trainData.getOrigin());
        train.setDestination(trainData.getDestination());
        train.setDepartureTime(trainData.getDepartureTime());
        train.setArrivalTime(trainData.getArrivalTime());
        train.setBasePrice(trainData.getBasePrice());
        train.setTotalSeats(trainData.getTotalSeats());;

        TrainModel trainUpd = trainRepo.save(train);

        System.out.println(train);

        return trainUpd;
    }


}
