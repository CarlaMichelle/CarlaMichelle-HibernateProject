package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import hibernateDao.PersonaDAO;
import hibernateDto.PersonaEntity;
import junit.framework.TestCase;

public class AppTestPersona extends TestCase {

	PersonaDAO perDao = new PersonaDAO();

	public void testListPersona() {

		List<PersonaEntity> listaTest = perDao.getAllPersona();
		int registro = listaTest.size();
		assertTrue(registro != 0);

	}

	public void testInsertDeletePersona() {

		List<PersonaEntity> listaTest = perDao.getAllPersona();
		int totalLista1 = listaTest.size();
		PersonaEntity per = new PersonaEntity();

		try {
			per.setEdad(20);
			per.setNombre("Maria");
			String fechaN = "2000-12-18";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date fechaNacimiento = sdf.parse(fechaN);

			per.setFechaNacimiento(fechaNacimiento);

			perDao.UpdatePersona(per);

			List<PersonaEntity> listaTest2 = perDao.getAllPersona();
			int totalLista2 = listaTest2.size();

			assertTrue(totalLista1 != totalLista2);

			perDao.deletePersona(per);

			List<PersonaEntity> listaTest3 = perDao.getAllPersona();
			int totalLista3 = listaTest3.size();

			assertFalse(totalLista1 != totalLista3);
		}

		catch (ParseException e1) {
			e1.printStackTrace();

		}

	}

}
