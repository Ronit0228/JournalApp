package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, String> {
    JournalEntry findJournalByJournalTitle(String journalTitle);
    void deleteJournalByJournalTitle(String journalTitle);
}
