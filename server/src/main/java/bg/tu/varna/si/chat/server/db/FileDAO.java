package bg.tu.varna.si.chat.server.db;

import java.util.LinkedList;
import java.util.List;

import bg.tu.varna.si.chat.model.request.FileTransferRequest;
import bg.tu.varna.si.chat.server.db.entity.FileEntity;

public class FileDAO {
	private List <FileEntity> fileTransfer = new LinkedList <FileEntity> ();

	private static FileDAO INSTANCE_HOLDER;

	private FileDAO() {

	}

	public static FileDAO getInstanceHolder() {
		if (INSTANCE_HOLDER == null) {
			INSTANCE_HOLDER = new FileDAO();
		}
		return INSTANCE_HOLDER;
	}
	
	public void storeFile(FileTransferRequest file, boolean delivered) {
		FileEntity fileEntity = new FileEntity ();
		
		
		fileEntity.setRecipient(file.getRecipientName());
		fileEntity.setSender(file.getSenderName());
		fileEntity.setSize(file.getSize());
		fileEntity.setTimeStamp(file.getTimeStamp());
		
		fileTransfer.add(fileEntity);
	}
	 
	public List <FileTransferRequest> getUndeliveredFiles (String username){
		List <FileTransferRequest> undeliveredFiles = new LinkedList <FileTransferRequest>();
		
		for (FileEntity entity : fileTransfer) {
			if (entity.getRecipient().equals(username) && !entity.isDelivered()) {
				new FileTransferRequest(entity.getRecipient(), entity.getSender(),
						entity.getFileName(), entity.getSize());
				
			}

		}
		
		return undeliveredFiles;
		
	}
	

}
