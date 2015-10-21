package clusterSender;

import com.typesafe.config.ConfigFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UnhandledMessage;
import akka.testkit.JavaTestKit;
import clusterMassages.Pong;

public class SenderActorTest {

  static ActorSystem senderSystem;

  /** setup before every test case */
  @Before
  public void setup() {
    senderSystem = ActorSystem.create("ClusterSystem", ConfigFactory.load("sender"));
  }

  /** tear down after every test case */
  @After
  public void tearDown() {
    JavaTestKit.shutdownActorSystem(senderSystem);
    senderSystem = null;
  }

  /** unhandled message type */
  @Test
  public void testUnhandledMsg() {
    new JavaTestKit(senderSystem) {
      {
        final ActorRef sender = senderSystem.actorOf(Props.create(ClusterSender.class), "sender");
        final ActorRef probe = getRef();
        senderSystem.eventStream().subscribe(getRef(), UnhandledMessage.class);
        sender.tell(new Integer(33), probe);
        expectMsgClass(UnhandledMessage.class);
      }
    };
  }

  /** Normal flow, send a StringMessage, PingMessage and a ResultMessage */
  @Test
  public void testNormalFlow() {

    new JavaTestKit(senderSystem) {
      {
        final ActorRef service = senderSystem.actorOf(Props.create(ClusterSender.class), "sender");
        final ActorRef probe = getRef();
        service.tell("Ping", probe);
        expectNoMsg();
        service.tell(new Pong(1l, "Pong"), probe);
        expectNoMsg();
      }
    };
  }
}
