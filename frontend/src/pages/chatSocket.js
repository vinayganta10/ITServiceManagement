import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

let stompClient = null;

export const connectChatSocket = (token, ticketId, onMessageReceived) => {
  stompClient = new Client({
    webSocketFactory: () => new SockJS("http://localhost:8080/ws"),
    debug: () => {},
    connectHeaders: {
      Authorization: `Bearer ${token}`,
    },
    onConnect: () => {
      stompClient.subscribe(`/ticket/${ticketId}`, (message) => {
        const body = JSON.parse(message.body);
        onMessageReceived(body);
      });
    },
  });

  stompClient.activate();
};

export const sendChatMessage = (ticketId, message) => {
  if (stompClient && stompClient.connected) {
    stompClient.publish({
      destination: `/app/ticket/${ticketId}/send`,
      body: JSON.stringify({ message }),
    });
  }
};

export const disconnectChatSocket = () => {
  if (stompClient) stompClient.deactivate();
};
