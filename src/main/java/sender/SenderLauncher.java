package sender;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class SenderLauncher {
  final ActorSystem system = ActorSystem.create("helloakka");
  final ActorRef sender = system.actorOf(Props.create(Sender.class), "sender");
  
  system.scheduler().schedule(Duration.Zero(), Duration.create(1, TimeUnit.SECONDS), sender, "Ping", system.dispatcher(), sender);

}
