package org.example.random;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConcurrentTracker {
    private static Map<String, Set<Integer>> serversMap;

    public ConcurrentTracker() {
        serversMap = new HashMap<>();
    }

    private static int next_server_number(Set<Integer> servers) {
        int num = 1;
        while (servers.contains(num)) {
            ++num;
        }
        return num;
    }

    public void allocate(String hostType) {
        Set<Integer> servers = serversMap.get(hostType);
        if (servers == null) {
            servers = new HashSet<>();
        }
        int num = next_server_number(servers);

        System.out.println("Allocating -- " + hostType + " --> " + (hostType + num));
        servers.add(num);
        serversMap.put(hostType, servers);
    }

    public void deallocate(String hostName) {
        int i = 0;
        while (i < hostName.length() && 'a' <= hostName.charAt(i) && hostName.charAt(i) <= 'z') {
            ++i;
        }
        String hostType = hostName.substring(0, i);
        int num = Integer.parseInt(hostName.substring(i));

        System.out.println("Deallocating -- " + hostName);
        Set<Integer> servers = serversMap.get(hostType);
        servers.remove(num);
        serversMap.put(hostType, servers);
    }

    public static void main(String[] args) {
        ConcurrentTracker tracker = new ConcurrentTracker();

        tracker.allocate("apibox");
        tracker.allocate("apibox");
        tracker.deallocate("apibox1");
        tracker.allocate("apibox");
        tracker.allocate("sitebox");
        tracker.allocate("#$@%");
    }
}
