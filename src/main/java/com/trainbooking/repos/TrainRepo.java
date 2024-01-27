package com.trainbooking.repos;

import com.trainbooking.models.TrainModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrainRepo extends MongoRepository<TrainModel, String> {
}
