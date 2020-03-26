
package hibernateDao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;

import hibernate.HibernateUtil;
import hibernateDto.PersonaEntity;
import hibernateDto.VentaEntity;

public class PersonaDAO {
	
	
	public void insertPersona (PersonaEntity per) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(per);
		session.getTransaction().commit();
		HibernateUtil.shutdown();
		
	}
	
	public List<PersonaEntity> getAllPersona() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<PersonaEntity> per = new ArrayList<PersonaEntity>();
		try {
			per = session.createQuery("From PersonaEntity").list();
			for (PersonaEntity pers : per) {
				System.out.println(pers.getPersonaId() + " " + pers.getNombre());
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		HibernateUtil.shutdown();
		return per;
	}
	
	


	
	public void UpdatePersona (PersonaEntity per) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(per);
		session.getTransaction().commit();
		HibernateUtil.shutdown();
		
		
		
	}
	
	
	public PersonaEntity getPersona (int personaId) {
		Session sesn = HibernateUtil.getSessionFactory().openSession();
		PersonaEntity persona = null;
		List<PersonaEntity> persona1 = new ArrayList<PersonaEntity>();
		try {
			
			persona1 = sesn.createQuery ("From PersonaEntity WHERE ID=" + personaId) .list();
			if (!persona1.isEmpty()) {
				persona = persona1.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			sesn.close();
		}

		HibernateUtil.shutdown();
		return persona;
			

		}
		
		
	public void deletePersona (PersonaEntity per) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(per);
		session.getTransaction().commit();
		HibernateUtil.shutdown();
		
	}
	
	

}
