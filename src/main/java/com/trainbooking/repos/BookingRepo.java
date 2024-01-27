package com.trainbooking.repos;

import com.trainbooking.models.BookingModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepo extends MongoRepository<BookingModel, String> {
}
