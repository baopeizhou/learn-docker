package org.bob.learn.zk.watcher;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.zookeeper.ClientCnxn;
import org.apache.zookeeper.ClientWatchManager;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WatchManager implements ClientWatchManager {

	private final Map<String, Set<Watcher>> dataWatches =
        new HashMap<String, Set<Watcher>>();
    private final Map<String, Set<Watcher>> existWatches =
        new HashMap<String, Set<Watcher>>();
    private final Map<String, Set<Watcher>> childWatches =
        new HashMap<String, Set<Watcher>>();
    
    private volatile Watcher defaultWatcher;

    final private void addTo(Set<Watcher> from, Set<Watcher> to) {
        if (from != null) {
            to.addAll(from);
        }
    }
@Override
public Set<Watcher> materialize(KeeperState state, EventType type, String clientPath) {
	Set<Watcher> result = new HashSet<Watcher>();

    switch (type) {
    case None:
        result.add(defaultWatcher);
        /** 
        * 涓嬮潰鏄庣‘鐨勮鏄庝簡锛屽湪榛樿閰嶇疆涓嬶紝SyncConnected閲嶈繛鍚庝笉闇�瑕侀噸鏂版敞鍐學atcher
        * Expired鏃犳硶鑷姩閲嶈繛锛岄渶瑕佽嚜宸遍噸寤簔ookeeper锛屼篃灏辫閲嶆柊娉ㄥ唽Watcher浜�
        **/
        boolean clear = ClientCnxn.getDisableAutoResetWatch() &&
                state != Watcher.Event.KeeperState.SyncConnected;

        synchronized(dataWatches) {
            for(Set<Watcher> ws: dataWatches.values()) {
                result.addAll(ws);
            }
            if (clear) {
                dataWatches.clear();
            }
        }

        synchronized(existWatches) {
            for(Set<Watcher> ws: existWatches.values()) {
                result.addAll(ws);
            }
            if (clear) {
                existWatches.clear();
            }
        }

        synchronized(childWatches) {
            for(Set<Watcher> ws: childWatches.values()) {
                result.addAll(ws);
            }
            if (clear) {
                childWatches.clear();
            }
        }

        return result;
    /** 
    * xxxWatches.remove锛屼互涓嬪ぇ瀹跺彲浠ョ湅鍑轰负浠�涔堝搷搴斾竴娆″悗瑕佸啀娆℃敞鍐屼簡
    * 鍚屾椂鍙互鐪嬪埌锛孨odeDataChanged鍜孨odeCreated瑙﹀彂dataWatche鍜宔xistWatche
    * NodeChildrenChanged瑙﹀彂chiledWatche
    * NodeDeleted瑙﹀彂dataWatche銆乧hiledWatche銆乪xistWatche
    * */
    case NodeDataChanged:
    case NodeCreated:
        synchronized (dataWatches) {
            addTo(dataWatches.remove(clientPath), result);
        }
        synchronized (existWatches) {
            addTo(existWatches.remove(clientPath), result);
        }
        break;
    case NodeChildrenChanged:
        synchronized (childWatches) {
            addTo(childWatches.remove(clientPath), result);
        }
        break;
    case NodeDeleted:
        synchronized (dataWatches) {
            addTo(dataWatches.remove(clientPath), result);
        }
        // XXX This shouldn't be needed, but just in case
        synchronized (existWatches) {
            Set<Watcher> list = existWatches.remove(clientPath);
            if (list != null) {
                addTo(existWatches.remove(clientPath), result);
                log.warn("We are triggering an exists watch for delete! Shouldn't happen!");
            }
        }
        synchronized (childWatches) {
            addTo(childWatches.remove(clientPath), result);
        }
        break;
    default:
        String msg = "Unhandled watch event type " + type
            + " with state " + state + " on path " + clientPath;
        log.error(msg);
        throw new RuntimeException(msg);
    }

    return result;
}


}
