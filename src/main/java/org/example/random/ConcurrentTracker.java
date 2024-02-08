package org.example.random;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    synchronized public void allocate(String hostType) {
        Set<Integer> servers = serversMap.get(hostType);
        if (servers == null) {
            servers = new HashSet<>();
        }
        int num = next_server_number(servers);

        System.out.println("Allocating -- " + hostType + " --> " + (hostType + num));
        servers.add(num);
        serversMap.put(hostType, servers);
    }

    synchronized public void deallocate(String hostName) {
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

        List<String[]> queries = new ArrayList<>();
        queries.add(new String[]{"allocate", "apibox"});
        queries.add(new String[]{"allocate", "apibox"});
        queries.add(new String[]{"deallocate", "apibox3"});
        queries.add(new String[]{"allocate", "apibox"});
        queries.add(new String[]{"allocate", "apibox"});
        queries.add(new String[]{"allocate", "apibox"});
        queries.add(new String[]{"deallocate", "apibox1"});

        int NUM_THREADS = queries.size();
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; ++i) {
            String[] query = queries.get(i);
            if (Objects.equals(query[0], "allocate")) {
                executor.execute(() -> tracker.allocate(query[1]));
            } else {
                executor.execute(() -> tracker.deallocate(query[1]));
            }
        }
    }
}
