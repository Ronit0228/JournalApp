package net.engineeringdigest.journalApp.controller;

import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/journal")
@RequiredArgsConstructor
public class JournalEntryController {

    private final JournalEntryService journalEntryService;

    @PostMapping("/create-journal")
    public ResponseEntity<JournalEntry> createJournal(@RequestBody JournalEntry journalEntry) {
        try{
            journalEntryService.saveJournal(journalEntry);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all-journals")
    public ResponseEntity<List<JournalEntry>> allJournals(){
        List<JournalEntry> allJournalsOfUser = journalEntryService.allJournals();

        return allJournalsOfUser.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(allJournalsOfUser);
    }

    @GetMapping("/journal-name/{title}")
    public ResponseEntity<JournalEntry> findJournalByName(@PathVariable String title){
        JournalEntry journalEntry =  journalEntryService.findJournalByName(title);

        return journalEntry != null
                ? ResponseEntity.ok(journalEntry)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/journal-id/{myId}")
    public ResponseEntity<JournalEntry> searchEntryByID(@PathVariable String myId){
        JournalEntry journalEntry = journalEntryService.journalFindById(myId);

        return journalEntry != null
                ? ResponseEntity.ok(journalEntry)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete-journal/{journalTitle}")
    public ResponseEntity<String> deleteEntryByTitle(@PathVariable String journalTitle){
        boolean deleted = journalEntryService.deleteEntryByTitle(journalTitle);

        if(deleted) return ResponseEntity.ok("Journal deleted successfully");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal not found");

    }

    @PutMapping("/update-journal/{journalTitle}")
    public ResponseEntity<JournalEntry> updateJournal(@PathVariable String journalTitle, @RequestBody JournalEntry journalEntryNew){
        JournalEntry journalEntry = journalEntryService.updateJournal(journalTitle, journalEntryNew);

        return journalEntry != null ? ResponseEntity.ok(journalEntry) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
