public class Connector {
    boolean messageBufferFull;
    boolean responseBufferFull;
    Object buffer;

    public Connector() {
        this.messageBufferFull = false;
        this.responseBufferFull = false;
    }

    synchronized Object send(Object in_message) {
        buffer = in_message;
        messageBufferFull = true;
        notify();
        while (responseBufferFull == false) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.print("interruptedException caught!!!");
            }
        }
        responseBufferFull = false;
        return buffer;
    }

    synchronized Object receive() {
        while (messageBufferFull == false) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.print("interruptedException caught!!!");
            }
        }
        messageBufferFull = false;
        return buffer;
    }

    synchronized void reply(Object in_message) {
        buffer = in_message;
        responseBufferFull = true;
        notify();
    }

    synchronized Object get_reply() {
        messageBufferFull = true;
        return buffer;
    }
}