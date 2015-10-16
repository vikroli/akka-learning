package clusterMassages;

public class Pong {
  public long msgId;
  public String message;

  public Pong(long msgId, String message) {
    super();
    this.msgId = msgId;
    this.message = message;
  }

  public long getMsgId() {
    return msgId;
  }

  public void setMsgId(long msgId) {
    this.msgId = msgId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "Pong [msgId=" + msgId + ", message=" + message + "]";
  }

}
