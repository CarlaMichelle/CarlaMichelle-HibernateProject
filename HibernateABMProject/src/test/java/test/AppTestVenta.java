package test;

import java.util.List;

import hibernateDao.VentaDAO;

import hibernateDto.VentaEntity;
import junit.framework.TestCase;

public class AppTestVenta extends TestCase {

	VentaDAO venDao = new VentaDAO();

	public void testGetAllVenta() {

		List<VentaEntity> listaTest = venDao.getAllVenta();
		int registro = listaTest.size();
		assertTrue(registro != 0);

	}

}