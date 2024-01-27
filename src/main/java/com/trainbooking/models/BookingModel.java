package com.trainbooking.models;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Array;

@Getter
@Document(collection = "bookings")
public class BookingModel {

    public String _id;
    public String user;
    public String train;
    public String trainName;
    public String trainNumber;
    public String startStation;
    public String destinationStation;
    public String email;
    public String mobile;
    public String seats;
    public Number ticketsCount;
    public Integer totalPrice;
    public String bookingDate;
    public String journeyDate;
    public String coachClass;
    public String bookingStatus;


    public BookingModel() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public Number getTicketsCount() {
        return ticketsCount;
    }

    public void setTicketsCount(Number ticketsCount) {
        this.ticketsCount = ticketsCount;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(String journeyDate) {
        this.journeyDate = journeyDate;
    }

    public String getCoachClass() {
        return coachClass;
    }

    public void setCoachClass(String coachClass) {
        this.coachClass = coachClass;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    @Override
    public String toString() {
        return "BookingModel{" +
                "_id='" + _id + '\'' +
                ", user='" + user + '\'' +
                ", train='" + train + '\'' +
                ", trainName='" + trainName + '\'' +
                ", trainNumber='" + trainNumber + '\'' +
                ", startStation='" + startStation + '\'' +
                ", destinationStation='" + destinationStation + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", seats='" + seats + '\'' +
                ", bookingDate='" + bookingDate + '\'' +
                ", journeyDate='" + journeyDate + '\'' +
                ", coachClass='" + coachClass + '\'' +
                ", bookingStatus='" + bookingStatus + '\'' +
                '}';
    }
}
