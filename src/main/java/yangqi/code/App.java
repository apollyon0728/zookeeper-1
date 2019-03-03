package yangqi.code;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zk = new ZooKeeper("localhost:2181", 3000, new Watcher() {

            @Override
            public void process(WatchedEvent event) {
                System.out.println(event);

            }

        });

        Stat e = zk.exists("/yangqi_test", null);

        if (e == null) {
            createAndSetTestNode(zk);
        }

        System.out.println("exists " + e);

    }

    public static void createAndSetTestNode(ZooKeeper zk){
        try {
            zk.create("/yangqi_test", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL);

            zk.setData("/yangqi_test", "Data of node 3".getBytes(), -1);
            for(;;) {
                if (!zk.getState().isAlive())
                    break;
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
