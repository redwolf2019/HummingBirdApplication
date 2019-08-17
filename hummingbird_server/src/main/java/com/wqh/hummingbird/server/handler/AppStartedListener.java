package com.wqh.hummingbird.server.handler;

import com.wqh.hummingbird.server.netty.IDataProcess;
import com.wqh.hummingbird.server.netty.Server;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;

import static com.wqh.hummingbird.server.generator.tables.Device.DEVICE;

@Slf4j
@Component
public class AppStartedListener implements ApplicationRunner {

    @Autowired
    private IDataProcess process;

    @Autowired
    private DSLContext dslContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        dslContext.update(DEVICE)
                .set(DEVICE.ONLINE,false)
                .execute();
        Server.getInstance().start(process);
    }

    @PreDestroy
    public void close() throws IOException {
        Server.getInstance().close();
    }

}
