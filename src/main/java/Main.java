
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;
import scala.concurrent.duration.Duration;

public class Main {

  public static void main(String[] args) {
    try {
      final ActorSystem system = ActorSystem.create("helloakka");
      final ActorRef receiver = system.actorOf(Props.create(Receiver.class), "receiver");

      final Inbox inbox = Inbox.create(system);
      inbox.send(receiver, new Ping());
      Ping response = (Ping) inbox.receive(Duration.create(5, TimeUnit.SECONDS));
      System.out.println("Response: " + response.getMsgId() + " " + response.getMsg());

    } catch (TimeoutException e) {
      System.out.println("Got a timeout waiting for reply from an actor");
      e.printStackTrace();
    }
  }

}
