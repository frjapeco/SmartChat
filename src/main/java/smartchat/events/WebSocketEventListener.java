package smartchat.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import smartchat.domains.Session;
import smartchat.domains.SessionRepository;

@Component
public class WebSocketEventListener {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private SessionRepository sessionRepository;
	
	@EventListener
	private void handleSessionConnected(SessionConnectedEvent event) {
		SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
		String sessionId = headers.getSessionId();
		String username = headers.getUser().getName();
		
		sessionRepository.add(new Session(sessionId,username));
		if (sessionRepository.findAllByUsername(username).size() == 1)
			simpMessagingTemplate.convertAndSend("/topic/chat.login",username);
	}
	
	@EventListener
	private void handleSessionDisconnect(SessionDisconnectEvent event) {
		SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
		String sessionId = headers.getSessionId();
		String username = headers.getUser().getName();
		Session currentSession = sessionRepository.findById(sessionId);
	
		sessionRepository.remove(currentSession);
		if (sessionRepository.findAllByUsername(username).size() == 0)
			simpMessagingTemplate.convertAndSend("/topic/chat.logout",currentSession.getUsername());
	}
	
}
