package bg.tu.varna.si.chat.server.db;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import bg.tu.varna.si.chat.model.request.FileContentRequest;
import bg.tu.varna.si.chat.server.db.entity.FileEntity;

public class FileDAO {

	
	private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();


	private static FileDAO INSTANCE_HOLDER;

	private FileDAO() {

	}

	public static FileDAO getInstanceHolder() {
		if (INSTANCE_HOLDER == null) {
			INSTANCE_HOLDER = new FileDAO();
		}
		return INSTANCE_HOLDER;
	}

	public Collection<FileEntity> getFile(String fileName) {

		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("from FileEntity where fileName = :fileName", FileEntity.class)
					.list();
		}
	}


	public void storeFile(FileContentRequest file) {

		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			FileEntity fileEntity = new FileEntity(); 
			fileEntity.setRecipient(file.getRecipient());
			fileEntity.setSender(file.getSender());
			fileEntity.setContent(file.getContent());
			fileEntity.setFileName(file.getFileName());

			transaction.commit();

		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
	}

}
