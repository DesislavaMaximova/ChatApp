package bg.tu.varna.si.chat.server.handler;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import bg.tu.varna.si.chat.model.request.FileTransferRequest;
import bg.tu.varna.si.chat.model.request.Request;
import bg.tu.varna.si.chat.model.response.ErrorResponse;
import bg.tu.varna.si.chat.model.response.ErrorType;
import bg.tu.varna.si.chat.model.response.Response;
import bg.tu.varna.si.chat.server.ClientHandler;
import bg.tu.varna.si.chat.server.ClientRegistry;
import bg.tu.varna.si.chat.server.db.FileDAO;
import bg.tu.varna.si.chat.server.db.UserDAO;
import bg.tu.varna.si.chat.server.db.entity.UserEntity;

public class FileHandler extends RequestHandler {
public static File fileToBeTransfered;

	@Override
	public Response handle(Request request) {
		FileTransferRequest file = (FileTransferRequest) request;

		// 1. get recipient user (return error if recipient does not exist)
		UserEntity recipient = UserDAO.getInstance().getUserEntity(file.getRecipientName());

		if (recipient == null) {
			return new ErrorResponse(ErrorType.INEXISTENT_USER,
					"User with username [" + file.getRecipientName() + "] does not exist.");
		}
		// 2. get recipient ClientHanlder
		ClientHandler recipientClientHandler = ClientRegistry.getInstance().getClientHandler(recipient.getUserName());

		// 3. if recipient ClientHandler == null => write file to the db

		if (recipientClientHandler == null) {
		
		
			FileDAO.getInstanceHolder().storeFile(file, false);
			
		}
		// 4. if recipient ClientHandler !=null => send recipient ask for permission to receive file
		if (recipientClientHandler !=null)
			return null;
		// 4.1. if recipient agree  send him file and write it to db
		// 4.2 if disagree

		return null;
	}
	

		public void proccessFileTransfer (FileTransferRequest request)  {
			Socket sock = new Socket();  
			   
	        //Send file  
	        File myFile = new File(request.getFileName());  
	        byte[] mybytearray = new byte[(int) myFile.length()];  
	           
	        FileInputStream fis;
			try {
				fis = new FileInputStream(myFile);
			
	        BufferedInputStream bis = new BufferedInputStream(fis);  
	        //bis.read(mybytearray, 0, mybytearray.length);  
	           
	        DataInputStream dis = new DataInputStream(bis);     
	        dis.readFully(mybytearray, 0, mybytearray.length);  
	           
	        OutputStream os = sock.getOutputStream();  
	           
	        //Sending file name and file size to the server  
	        DataOutputStream dos = new DataOutputStream(os);     
	        dos.writeUTF(myFile.getName());     
	        dos.writeLong(mybytearray.length);     
	        dos.write(mybytearray, 0, mybytearray.length);     
	        dos.flush();  
	           
	        //Sending file data to the server  
	        os.write(mybytearray, 0, mybytearray.length);  
	        os.flush();  
			       
	        //Closing socket
	        os.close();
	        dos.close();  
	        sock.close();  
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    }  
	
		}

		
	


