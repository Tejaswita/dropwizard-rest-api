package dao;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.IOException;

/**
 * Created by Tejaswita on 11-08-2015.
 */
public class WithMongoServer {
    private static MongodExecutable mongodExecutable;
    protected static int PORT = 12233;

    @BeforeClass
    public static void setup() throws IOException {
        MongodStarter mongodStarter = MongodStarter.getDefaultInstance();
        mongodExecutable = mongodStarter.prepare(new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(PORT, Network.localhostIsIPv6()))
                .build());
        mongodExecutable.start();

    }

    @AfterClass
    public static void tearDown(){
        mongodExecutable.stop();
    }
}
