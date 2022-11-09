package top.homesoft.framework.idgenerator.support;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;
@Slf4j
public class ZookeeperMachineIdProvider implements MachineIdProvider{
    private static final String WORKER_ROOT_NODE = "/DataCenterId/WorkerId";
    private static final long ACQUIRE_TIMEOUT = 100L;


    private final CuratorFramework curatorFramework;

    public ZookeeperMachineIdProvider(CuratorFramework curatorFramework){
        this.curatorFramework = curatorFramework;
    }


    @Override
    public long getMachineId(String innerIpAddress) {
        long machineId = 0L;
        String path = WORKER_ROOT_NODE + "/" + innerIpAddress;
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, path);
        try {
            if (lock.acquire(ACQUIRE_TIMEOUT, TimeUnit.MILLISECONDS)) {
                byte[] data = curatorFramework.getData().forPath(path);
                String value = new String(data);
                if( StringUtils.isNotBlank(value)){
                    machineId = Integer.parseInt(value);
                } else {
                    machineId = curatorFramework.getChildren().forPath(WORKER_ROOT_NODE).size() - 1;
                    curatorFramework.setData().forPath(path, String.valueOf(machineId).getBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取ID失败：", e);
        } finally {
            try {
                lock.release();
            } catch (Exception e) {
                logger.error("释放锁失败：", e);
            }
        }
        return machineId;
    }
}
