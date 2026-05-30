package com.resume.resume.Service;



import com.resume.resume.model.ContactMessage;
import com.resume.resume.Repository.ContactMessageRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    private final ContactMessageRepository repository;

    public ContactService(ContactMessageRepository repository) {
        this.repository = repository;
    }

    public void saveMessage(ContactMessage message) {
        repository.save(message);
    }
}
