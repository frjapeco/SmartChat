package smartchat.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import smartchat.domains.Bootstrap;
import smartchat.domains.Message;
import smartchat.domains.SessionRepository;

@Controller
public class ChatController {
		
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private SessionRepository sessionRepository;

	@SubscribeMapping("/chat.bootstrap")
	public Bootstrap bootstrap(Principal principal) {
		return new Bootstrap(principal.getName(),sessionRepository.findAllOnlineUsers());
	}
	
	@MessageMapping("/chat.messages")
	public Message sendToAll(@Payload String content, Principal principal) throws Exception {
		return new Message(principal.getName(),content);
	}
	
	@MessageMapping("/chat.messages.{username}")
	public void sendToUser(@Payload String content, @DestinationVariable String username, Principal principal) throws Exception {
		String sender = principal.getName();

		simpMessagingTemplate.convertAndSendToUser(username,"/queue/chat.private",new Message(sender,content));
	}
	
}
