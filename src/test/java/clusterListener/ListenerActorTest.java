package clusterListener;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import clusterListener.ClusterListener;
import clusterMassages.Ping;
import clusterMassages.Pong;

public class ListenerActorTest {

  static ActorSystem system;

  /** setup before every testcase */
  @BeforeClass
  public static void setup() {
    Config config = ConfigFactory.load("listener");
    system = ActorSystem.create("clusterSystem", config);
  }

  /** tear down after every testcase */
  @AfterClass
  public static void tearDown() {
    JavaTestKit.shutdownActorSystem(system);
    system = null;
  }

  /** send a PingMessage get a PongMessage */
  @Test
  public void PingPong() {
    new JavaTestKit(system) {
      {
        final ActorRef service = system.actorOf(Props.create(ClusterListener.class), "listener");
        final ActorRef probe = getRef();
        Ping msg = new Ping("Ping", 1l);
        service.tell(msg, probe);
        Pong p = expectMsgClass(Pong.class);
        Assert.assertEquals(new Pong(1l, "Pong"), p);
      }
    };
  }

  /** unhandled message type-> no response */
  @Test
  public void UnhandledMsg() {
    new JavaTestKit(system) {
      {
        final ActorRef service = system.actorOf(Props.create(ClusterListener.class));
        final ActorRef probe = getRef();
        service.tell(1, probe);
        expectNoMsg();
      }
    };
  }
}
