package common;

public class Ping {
  public static long seqId = 0l;
  public String msg;
  public long msgId;

  public Ping(String message) {
    super();
    this.msg = message;
    this.msgId = seqId++;
  }

  public long getMsgId() {
    return msgId;
  }

  public void setMsgId(long msgId) {
    this.msgId = msgId;
  }

  @Override
  public String toString() {
    return "Ping [msg=" + msg + ", msgId=" + msgId + "]";
  }
}
