package clusterListener;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;
import akka.actor.Props;

public class ClusterListenerApp {

  public static void main(String[] args) {
    // final ActorSystem system = ActorSystem.create("helloakka");
    // final ActorRef listener = system.actorOf(Props.create(ClusterListener.class), "listener");

    Config config = ConfigFactory.load("listener");
    ActorSystem system = ActorSystem.create("ClusterSystem", config);

    system.actorOf(Props.create(ClusterListener.class), "listener");

  }
}
