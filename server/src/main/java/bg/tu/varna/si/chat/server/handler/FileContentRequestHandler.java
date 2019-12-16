package bg.tu.varna.si.chat.server.handler;

import bg.tu.varna.si.chat.model.request.FileContentRequest;
import bg.tu.varna.si.chat.model.request.Request;
import bg.tu.varna.si.chat.model.response.AcknowledgeResponse;
import bg.tu.varna.si.chat.model.response.ErrorResponse;
import bg.tu.varna.si.chat.model.response.ErrorType;
import bg.tu.varna.si.chat.model.response.Response;
import bg.tu.varna.si.chat.server.ClientHandler;
import bg.tu.varna.si.chat.server.ClientRegistry;
import bg.tu.varna.si.chat.server.db.FileDAO;
import bg.tu.varna.si.chat.server.db.UserDAO;
import bg.tu.varna.si.chat.server.db.entity.UserEntity;

public class FileContentRequestHandler extends RequestHandler {

	@Override
	public Response handle(Request request) {

		FileContentRequest file = (FileContentRequest) request;

		UserEntity recipient = UserDAO.getInstance().getUserEntity(file.getRecipient());
		if (recipient == null) {
			return new ErrorResponse(ErrorType.INEXISTENT_USER,
					"User with username [" + file.getRecipient() + "] does not exist.");
		}

		ClientHandler recipientClientHandler = ClientRegistry.getInstance().getClientHandler(recipient.getUserName());
		if (recipientClientHandler != null) {
			recipientClientHandler.sendRequest(file);
			FileDAO.getInstanceHolder().storeFile(file);

		}

		return new AcknowledgeResponse("OK!");
	}

}
