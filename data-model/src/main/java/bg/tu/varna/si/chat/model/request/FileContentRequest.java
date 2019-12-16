package bg.tu.varna.si.chat.model.request;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileContentRequest extends Request{

	private static final long serialVersionUID = 1L;

	private String fileName;
	
	private byte [] content;
	
	private String recipient;
	
	private String sender;
	
	public FileContentRequest (String fileName, String recipient, String sender) {
		super(RequestType.FILE_CONTENT_REQUEST);
		
		this.fileName = fileName;
		this.recipient = recipient;
		this.sender = sender;
		File fileToBeTransfered = new File(fileName);
		try {
			this.content = Files.readAllBytes(fileToBeTransfered.toPath());
		} catch (IOException e) {
			System.out.println("Can't read file with name: " + fileName);
			e.printStackTrace();
		}
	}

	public String getFileName() {
		return fileName;
	}

	public byte[] getContent() {
		return content;
	}

	public String getRecipient() {
		return recipient;
	}

	public String getSender() {
		return sender;
	}
	
	
	
}
