package common;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;
import receiver.Receiver;
import scala.concurrent.duration.Duration;

public class Main {

  public static void main(String[] args) {
    try {
      final ActorSystem system = ActorSystem.create("helloakka");
      final ActorRef receiver = system.actorOf(Props.create(Receiver.class), "receiver");

      final Inbox inbox = Inbox.create(system);
      inbox.send(receiver, new Ping("Ping"));
      Pong response = (Pong) inbox.receive(Duration.create(5, TimeUnit.SECONDS));
      System.out.println("Response: " + response.getMsgId() + " " + response.getMessage());

    } catch (TimeoutException e) {
      System.out.println("Got a timeout waiting for reply from an actor");
      e.printStackTrace();
    }
  }

}
