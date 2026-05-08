package net.engineeringdigest.journalApp.service;

import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;
    private final UserRepository userRepository;

    // Save journal in the Database
    @Transactional
    public void saveJournal(JournalEntry journalEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findUserByUsername(username);

        journalEntry.setDate(LocalDateTime.now());
        JournalEntry savedJournalEntry = journalEntryRepository.save(journalEntry);

        user.getJournalEntry().add(savedJournalEntry);
        userRepository.save(user);
    }

    // Fetch all the journals form the Database
    public List<JournalEntry> allJournals(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findUserByUsername(username);

        List<JournalEntry> journalEntryListOfLoginUser = user.getJournalEntry();

        return journalEntryListOfLoginUser != null
                ? journalEntryListOfLoginUser
                : Collections.emptyList();

    }

    // find journal by name from the Database
    public JournalEntry findJournalByName(String journalTitle){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findUserByUsername(username);

        return user.getJournalEntry()
                .stream()
                .filter(
                        entry -> entry.getJournalTitle().equals(journalTitle)
                )
                .findFirst().orElse(null);
    }

    // find journal by id from the Database Which user is logged In
    public JournalEntry journalFindById(String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findUserByUsername(username);

        return user.getJournalEntry()
                .stream()
                .filter(
                        entry -> entry.getJournalID().equals(id)
                )
                .findFirst()
                .orElse(null);
    }

    // delete journal by id from the Database
    @Transactional
    public boolean deleteEntryByTitle(String journalTitle){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findUserByUsername(username);

        try {
            boolean removedOrNot = user.getJournalEntry()
                    .removeIf(entry ->
                            entry.getJournalTitle().equals(journalTitle));

            if (removedOrNot) {
                userRepository.save(user);
                journalEntryRepository
                        .deleteJournalByJournalTitle(journalTitle);

                return true;
            }
            return false;

        } catch (RuntimeException e) {
            throw new RuntimeException("Something went wrong while delete the Entry with " + journalTitle, e);
        }
    }

    // Update the journal
    public JournalEntry updateJournal(String journalTitle, JournalEntry journalEntryNew){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findUserByUsername(username);

        try {
            JournalEntry journalEntryOld = user.getJournalEntry()
                    .stream()
                    .filter(journalEntry -> journalEntry.getJournalTitle().equals(journalTitle))
                    .findFirst()
                    .orElse(null);

            if(journalEntryOld != null) {
                journalEntryOld.setJournalTitle(
                        !journalEntryNew.getJournalTitle().isEmpty()
                                ? journalEntryNew.getJournalTitle()
                                : journalEntryOld.getJournalTitle()
                );

                journalEntryOld.setJournalContent(
                        journalEntryNew.getJournalContent() != null && !journalEntryNew.getJournalContent().isEmpty()
                                ? journalEntryNew.getJournalContent()
                                : journalEntryOld.getJournalContent()
                );
                journalEntryOld.setDate(LocalDateTime.now());
                journalEntryRepository.save(journalEntryOld);

                return journalEntryOld;
            }
            return null;

        } catch (RuntimeException e) {
            throw new RuntimeException("Journal not found with title " + journalTitle);
        }
    }
}
