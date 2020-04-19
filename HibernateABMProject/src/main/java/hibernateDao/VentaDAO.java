
package hibernateDao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.classic.Session;

import hibernate.HibernateUtil;
import hibernateDto.PersonaEntity;
import hibernateDto.VentaEntity;

public class VentaDAO {
	
public void insertVenta (VentaEntity ven) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(ven);
		session.getTransaction().commit();
		HibernateUtil.shutdown();

	
	}
	
	public List<VentaEntity> getAllVenta() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<VentaEntity> venta = new ArrayList<VentaEntity>();
		try {
			venta = session.createQuery("From VentaEntity").list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		HibernateUtil.shutdown();
		return venta;
	}
	
	public VentaEntity getVentaPERSONA (int ID_PERSONA ) {
		Session sesn = HibernateUtil.getSessionFactory().openSession();
		VentaEntity venta = null;
		List<VentaEntity> venta1 = new ArrayList<VentaEntity>();
		try {
			
			venta1 = sesn.createQuery ("From VentaEntity WHERE ID_PERSONA=" + ID_PERSONA ) .list();
			
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			sesn.close();
		}

		HibernateUtil.shutdown();
		return venta;
		
		}
	
}
