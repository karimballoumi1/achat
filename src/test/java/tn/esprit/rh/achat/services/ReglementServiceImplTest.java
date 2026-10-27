package tn.esprit.rh.achat.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.rh.achat.entities.Reglement;
import tn.esprit.rh.achat.repositories.ReglementRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReglementServiceImplTest {

    @Mock
    private ReglementRepository reglementRepository;

    @InjectMocks
    private ReglementServiceImpl reglementService;

    @Test
    public void testRetrieveAllReglements() {
        List<Reglement> list = new ArrayList<>();
        Reglement r1 = new Reglement();
        r1.setIdReglement(1L);
        r1.setMontantPaye(500.0f);

        Reglement r2 = new Reglement();
        r2.setIdReglement(2L);
        r2.setMontantPaye(1000.0f);

        list.add(r1);
        list.add(r2);

        when(reglementRepository.findAll()).thenReturn(list);

        List<Reglement> result = reglementService.retrieveAllReglements();
        Assertions.assertEquals(2, result.size());
        verify(reglementRepository, times(1)).findAll();
    }

    @Test
    public void testAddReglement() {
        Reglement r = new Reglement();
        r.setMontantPaye(750.0f);

        when(reglementRepository.save(r)).thenReturn(r);

        Reglement saved = reglementService.addReglement(r);
        Assertions.assertNotNull(saved);
        Assertions.assertEquals(750.0f, saved.getMontantPaye());
        verify(reglementRepository, times(1)).save(r);
    }

    @Test
    public void testRetrieveReglement() {
        Reglement r = new Reglement();
        r.setIdReglement(1L);
        r.setMontantPaye(300.0f);

        when(reglementRepository.findById(1L)).thenReturn(Optional.of(r));

        Reglement found = reglementService.retrieveReglement(1L);
        Assertions.assertNotNull(found);
        Assertions.assertEquals(1L, found.getIdReglement());
        verify(reglementRepository, times(1)).findById(1L);
    }

    @Test
    public void testRetrieveReglementByFacture() {
        Long idFacture = 10L;
        List<Reglement> list = new ArrayList<>();
        Reglement r = new Reglement();
        r.setIdReglement(1L);
        list.add(r);

        when(reglementRepository.retrieveReglementByFacture(idFacture)).thenReturn(list);

        List<Reglement> result = reglementService.retrieveReglementByFacture(idFacture);
        Assertions.assertEquals(1, result.size());
        verify(reglementRepository, times(1)).retrieveReglementByFacture(idFacture);
    }

    @Test
    public void testGetChiffreAffaireEntreDeuxDate() {
        Date start = new Date();
        Date end = new Date();

        when(reglementRepository.getChiffreAffaireEntreDeuxDate(start, end)).thenReturn(5000.0f);

        float ca = reglementService.getChiffreAffaireEntreDeuxDate(start, end);
        Assertions.assertEquals(5000.0f, ca);
        verify(reglementRepository, times(1)).getChiffreAffaireEntreDeuxDate(start, end);
    }
}
