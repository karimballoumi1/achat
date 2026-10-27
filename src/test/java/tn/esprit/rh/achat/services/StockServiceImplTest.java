package tn.esprit.rh.achat.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    @Test
    public void testRetrieveAllStocks() {
        List<Stock> list = new ArrayList<>();
        Stock s1 = new Stock("stock1", 100, 10);
        Stock s2 = new Stock("stock2", 200, 20);
        list.add(s1);
        list.add(s2);

        when(stockRepository.findAll()).thenReturn(list);

        List<Stock> stockList = stockService.retrieveAllStocks();
        Assertions.assertEquals(2, stockList.size());
        verify(stockRepository, times(1)).findAll();
    }

    @Test
    public void testAddStock() {
        Stock s = new Stock("stock1", 100, 10);
        when(stockRepository.save(s)).thenReturn(s);

        Stock savedStock = stockService.addStock(s);
        Assertions.assertNotNull(savedStock);
        Assertions.assertEquals("stock1", savedStock.getLibelleStock());
        verify(stockRepository, times(1)).save(s);
    }

    @Test
    public void testRetrieveStock() {
        Stock s = new Stock("stock1", 100, 10);
        s.setIdStock(1L);
        when(stockRepository.findById(1L)).thenReturn(Optional.of(s));

        Stock stock = stockService.retrieveStock(1L);
        Assertions.assertNotNull(stock);
        Assertions.assertEquals(1L, stock.getIdStock());
        verify(stockRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteStock() {
        Long id = 1L;
        doNothing().when(stockRepository).deleteById(id);

        stockService.deleteStock(id);
        verify(stockRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateStock() {
        Stock s = new Stock("stockUpdate", 150, 15);
        s.setIdStock(1L);
        when(stockRepository.save(s)).thenReturn(s);

        Stock updatedStock = stockService.updateStock(s);
        Assertions.assertNotNull(updatedStock);
        Assertions.assertEquals("stockUpdate", updatedStock.getLibelleStock());
        verify(stockRepository, times(1)).save(s);
    }
}
