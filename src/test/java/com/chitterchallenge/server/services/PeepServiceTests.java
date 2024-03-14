package com.chitterchallenge.server.services;

import com.chitterchallenge.server.models.Peep;
import com.chitterchallenge.server.repositories.PeepRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PeepServiceTests {
    @Mock
    PeepRepository peepRepository;

    @InjectMocks
    PeepService peepService;

    Peep newPeep1;
    Peep newPeep2;

    @BeforeEach
    void createNewPeep() {
        newPeep1 = new Peep();
        newPeep1.setPeep("Test peep");
        newPeep1.setName("John test");
        newPeep1.setUsername("JT");
        newPeep1.setDateCreated(LocalDateTime.parse("2024-03-04T23:04:00.675"));

        newPeep2 = new Peep();
        newPeep2.setPeep("Test peep 222");
        newPeep2.setName("John test");
        newPeep2.setUsername("JT");
        newPeep2.setDateCreated(LocalDateTime.parse("2024-03-04T22:04:00.675"));
    }

    @Test
    @DisplayName("Should return list of peeps in reverse chronological order")
    void getAllPeepsTest() {
        when(peepRepository.findAllByOrderByDateCreatedDesc())
            .thenReturn(
                Arrays.asList(newPeep1, newPeep2)
            );

        List<Peep> peepsResult = peepService.getAllPeeps();

        assertEquals(peepsResult.get(0).getPeep(), "Test peep");
        assertEquals(peepsResult.get(0).getDateCreated(), newPeep1.getDateCreated());
        assertEquals(peepsResult.get(1).getPeep(), "Test peep 222");
        assertEquals(peepsResult.get(1).getDateCreated(), newPeep2.getDateCreated());
    }

    @Test
    @DisplayName("Should return peep when save peep success")
    void shouldReturnPeepWhenSavePeepSuccess() {
        when(peepRepository.save(any(Peep.class))).then(returnsFirstArg());
        Peep peepResult = peepService.addPeep(newPeep1);

        assertEquals(peepResult.getPeep(), newPeep1.getPeep());
        assertEquals(peepResult.getUsername(), newPeep1.getUsername());
    }


}
