package tn.esprit.rh.achat.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.rh.achat.entities.Produit;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.ProduitRepository;
import tn.esprit.rh.achat.repositories.StockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProduitServiceImplTest {

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private ProduitServiceImpl produitService;

    @Test
    public void testRetrieveAllProduits() {
        List<Produit> list = new ArrayList<>();
        Produit p1 = new Produit();
        p1.setIdProduit(1L);
        p1.setLibelleProduit("Produit 1");

        Produit p2 = new Produit();
        p2.setIdProduit(2L);
        p2.setLibelleProduit("Produit 2");

        list.add(p1);
        list.add(p2);

        when(produitRepository.findAll()).thenReturn(list);

        List<Produit> result = produitService.retrieveAllProduits();
        Assertions.assertEquals(2, result.size());
        verify(produitRepository, times(1)).findAll();
    }

    @Test
    public void testAddProduit() {
        Produit p = new Produit();
        p.setLibelleProduit("Nouveau Produit");
        p.setPrix(50.0f);

        when(produitRepository.save(p)).thenReturn(p);

        Produit saved = produitService.addProduit(p);
        Assertions.assertNotNull(saved);
        Assertions.assertEquals("Nouveau Produit", saved.getLibelleProduit());
        verify(produitRepository, times(1)).save(p);
    }

    @Test
    public void testRetrieveProduit() {
        Produit p = new Produit();
        p.setIdProduit(1L);
        p.setLibelleProduit("Produit Test");

        when(produitRepository.findById(1L)).thenReturn(Optional.of(p));

        Produit found = produitService.retrieveProduit(1L);
        Assertions.assertNotNull(found);
        Assertions.assertEquals(1L, found.getIdProduit());
        verify(produitRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteProduit() {
        Long id = 1L;
        doNothing().when(produitRepository).deleteById(id);

        produitService.deleteProduit(id);
        verify(produitRepository, times(1)).deleteById(id);
    }

    @Test
    public void testAssignProduitToStock() {
        Produit p = new Produit();
        p.setIdProduit(1L);

        Stock s = new Stock();
        s.setIdStock(2L);

        when(produitRepository.findById(1L)).thenReturn(Optional.of(p));
        when(stockRepository.findById(2L)).thenReturn(Optional.of(s));
        when(produitRepository.save(p)).thenReturn(p);

        produitService.assignProduitToStock(1L, 2L);

        Assertions.assertEquals(s, p.getStock());
        verify(produitRepository, times(1)).save(p);
    }
}
