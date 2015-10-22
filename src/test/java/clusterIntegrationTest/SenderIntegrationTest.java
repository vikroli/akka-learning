package clusterIntegrationTest;

import static org.junit.Assert.assertTrue;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import akka.actor.ActorSystem;
import akka.testkit.JavaTestKit;
import clusterListener.ClusterListenerApp;
import clusterSender.ClusterSenderApp;

public class SenderIntegrationTest {
  protected static ActorSystem system;

  @BeforeClass
  public static void setupSystem() {
    Config config = ConfigFactory.load("aplication_test");
    system = ActorSystem.create("ClusterSystem", config);
    ClusterSenderApp.main(new String[] {});
    ClusterListenerApp.main(new String[] {});
  }

  @Test
  public void testSenderLocally() throws InterruptedException {
    new JavaTestKit(system) {
      {
        assertTrue("Error: Ping command not received by Sender",
            TestUtils.waitLog("Ping command received, msgId=1", 3000));

        assertTrue("Error: Ping message is not received by Listener",
            TestUtils.waitLog("Received a Ping with msgId=1", 3000));

        assertTrue("Error: Pong message is not received by Sender",
            TestUtils.waitLog("Recieved a Pong with msgId=1", 3000));
      }
    };
  }

  @AfterClass
  public static void shutdownSystem() {
    JavaTestKit.shutdownActorSystem(system);
  }
}
