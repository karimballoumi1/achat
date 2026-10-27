package tn.esprit.rh.achat.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.rh.achat.entities.Operateur;
import tn.esprit.rh.achat.repositories.OperateurRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OperateurServiceImplTest {

    @Mock
    private OperateurRepository operateurRepository;

    @InjectMocks
    private OperateurServiceImpl operateurService;

    @Test
    public void testRetrieveAllOperateurs() {
        List<Operateur> list = new ArrayList<>();
        Operateur op1 = new Operateur();
        op1.setIdOperateur(1L);
        op1.setNom("Dupont");
        op1.setPrenom("Jean");

        Operateur op2 = new Operateur();
        op2.setIdOperateur(2L);
        op2.setNom("Durand");
        op2.setPrenom("Pierre");

        list.add(op1);
        list.add(op2);

        when(operateurRepository.findAll()).thenReturn(list);

        List<Operateur> result = operateurService.retrieveAllOperateurs();
        Assertions.assertEquals(2, result.size());
        verify(operateurRepository, times(1)).findAll();
    }

    @Test
    public void testAddOperateur() {
        Operateur op = new Operateur();
        op.setNom("Martin");
        op.setPrenom("Paul");

        when(operateurRepository.save(op)).thenReturn(op);

        Operateur saved = operateurService.addOperateur(op);
        Assertions.assertNotNull(saved);
        Assertions.assertEquals("Martin", saved.getNom());
        verify(operateurRepository, times(1)).save(op);
    }

    @Test
    public void testRetrieveOperateur() {
        Operateur op = new Operateur();
        op.setIdOperateur(1L);
        op.setNom("Martin");

        when(operateurRepository.findById(1L)).thenReturn(Optional.of(op));

        Operateur found = operateurService.retrieveOperateur(1L);
        Assertions.assertNotNull(found);
        Assertions.assertEquals(1L, found.getIdOperateur());
        verify(operateurRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteOperateur() {
        Long id = 1L;
        doNothing().when(operateurRepository).deleteById(id);

        operateurService.deleteOperateur(id);
        verify(operateurRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateOperateur() {
        Operateur op = new Operateur();
        op.setIdOperateur(1L);
        op.setNom("UpdatedName");

        when(operateurRepository.save(op)).thenReturn(op);

        Operateur updated = operateurService.updateOperateur(op);
        Assertions.assertNotNull(updated);
        Assertions.assertEquals("UpdatedName", updated.getNom());
        verify(operateurRepository, times(1)).save(op);
    }
}
