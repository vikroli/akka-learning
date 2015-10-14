
public class Ping {
  public String msg;
  public long msgId;

  public Ping() {
    super();
    this.msg = "PING";
    this.msgId = System.currentTimeMillis();
  }

  public Ping(String msg) {
    super();
    this.msg = msg;
    this.msgId = System.currentTimeMillis();
  }

  public String getMsg() {
    return this.msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((msg == null) ? 0 : msg.hashCode());
    result = prime * result + (int) (msgId ^ (msgId >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Ping other = (Ping) obj;
    if (msg == null) {
      if (other.msg != null)
        return false;
    } else if (!msg.equals(other.msg))
      return false;
    if (msgId != other.msgId)
      return false;
    return true;
  }
}
