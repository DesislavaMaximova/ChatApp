package bg.tu.varna.si.chat.server.handler;

import bg.tu.varna.si.chat.model.request.FileTransferRequest;
import bg.tu.varna.si.chat.model.request.Request;
import bg.tu.varna.si.chat.model.response.ErrorResponse;
import bg.tu.varna.si.chat.model.response.ErrorType;
import bg.tu.varna.si.chat.model.response.FileTransferResponse;
import bg.tu.varna.si.chat.model.response.Response;
import bg.tu.varna.si.chat.server.ClientHandler;
import bg.tu.varna.si.chat.server.ClientRegistry;
import bg.tu.varna.si.chat.server.db.UserDAO;
import bg.tu.varna.si.chat.server.db.entity.UserEntity;

public class FileTransferRequestHandler extends RequestHandler {


	@Override
	public Response handle(Request request) {

		FileTransferRequest file = (FileTransferRequest) request;
		
		UserEntity recipient = UserDAO.getInstance().getUserEntity(file.getRecipient());
		if (recipient == null) {
			return new ErrorResponse(ErrorType.INEXISTENT_USER,
					"User with username [" + file.getRecipient() + "] does not exist.");
		}
		
		ClientHandler recipientClientHandler = ClientRegistry.getInstance().getClientHandler(recipient.getUserName());
		if (recipientClientHandler != null) {
			recipientClientHandler.sendRequest(file);
			FileTransferResponse response = (FileTransferResponse) recipientClientHandler.readResponse();
			return response;

		} else { 
			FileTransferResponse response = new FileTransferResponse();
				response.setApproved(false);
				response.setMessage("You can send the file when user is online");
				return response;
		}
	
	}
}