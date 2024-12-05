package com.example.Spring.Security.Repostry;
import com.example.Spring.Security.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepostry extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);
}
