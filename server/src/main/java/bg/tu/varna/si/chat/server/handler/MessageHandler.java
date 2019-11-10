package bg.tu.varna.si.chat.server.handler;

import bg.tu.varna.si.chat.model.request.MessageRequest;
import bg.tu.varna.si.chat.model.request.Request;
import bg.tu.varna.si.chat.model.response.AcknowledgeResponse;
import bg.tu.varna.si.chat.model.response.ErrorResponse;
import bg.tu.varna.si.chat.model.response.ErrorType;
import bg.tu.varna.si.chat.model.response.Response;
import bg.tu.varna.si.chat.server.ClientHandler;
import bg.tu.varna.si.chat.server.ClientRegistry;
import bg.tu.varna.si.chat.server.db.MessageDAO;
import bg.tu.varna.si.chat.server.db.UserDAO;
import bg.tu.varna.si.chat.server.db.entity.UserEntity;

public class MessageHandler extends RequestHandler {

	@Override
	public Response handle(Request request) {
		
		MessageRequest message = (MessageRequest) request;


		// 1. get recipient user (return error if recipient does not exist)
		UserEntity recipient = UserDAO.getInstance().getUserEntity(message.getRecieverName());

		if (recipient == null) {
			return new ErrorResponse(ErrorType.INEXISTENT_USER,
					"User with username [" + message.getRecieverName() + "] does not exist.");
		}

		// 2. get recipient ClientHanlder
		ClientHandler recipientClientHandler = ClientRegistry.getInstance().getClientHandler(recipient.getUserName());

		// 2.1. if recipient ClientHandler != null => sendMessage
		if (recipientClientHandler != null) {
			recipientClientHandler.sendRequest(message);
			MessageDAO.getInstance().storeMessage(message, true);

		} else { // 2.2. if recipient ClientHandler == null => store message in DB
			MessageDAO.getInstance().storeMessage(message, false);
		}

		// 3. return response
		return new AcknowledgeResponse("OK!");
	}

}
